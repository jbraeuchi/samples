package pessimisticLocking.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pessimisticLocking.entity.PlEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class Tests {

    EntityManager em;
    EntityManager em2;

    List<String> log;

    PlEntity entity1;
    PlEntity entity2;
    PlEntity entity3;

    @BeforeEach
    public void init() {
        entity1 = new PlEntity();
        entity1.setName("E1 name");

        entity2 = new PlEntity();
        entity2.setName("E2 name");

        entity3 = new PlEntity();
        entity3.setName("E3 name");

        log = new CopyOnWriteArrayList();

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TEST");
        em = emf.createEntityManager();
        em2 = emf.createEntityManager();

        doInTransaction(em, (e) -> {
            Query q = e.createQuery("delete from PlEntity");
            q.executeUpdate();

            e.persist(entity1);
            e.persist(entity2);
            e.persist(entity3);
        });
    }

    @Test
    public void testRead() {

        List<PlEntity> result = doInTransaction(em, (e) -> {
            return e.createQuery("select p from PlEntity p", PlEntity.class)
                    .setLockMode(LockModeType.PESSIMISTIC_READ)
                    .getResultList();
        });

        assertEquals(3, result.size());
        System.out.println(result);
    }

    @Test
    public void testConcurrentUpdate_pessimistic_write() throws Exception {
        // Tx 1 locks the entity, force serialization
        Runnable r1 = () ->
                updateEntityInTx(em, entity1.getId(), "Tx 1", 0, 3000, LockModeType.PESSIMISTIC_WRITE);

        // Tx 2 reading waits for end of Tx 1, use different EntityManager !
        Runnable r2 = () ->
                updateEntityInTx(em2, entity1.getId(), "Tx 2", 500, 0, LockModeType.PESSIMISTIC_WRITE);

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        executorService.submit(r1);
        executorService.submit(r2);
        executorService.awaitTermination(6, TimeUnit.SECONDS);

        List<PlEntity> result = doInTransaction(em, (e) -> {
            return e.createQuery("select p from PlEntity p", PlEntity.class)
                    .getResultList();
        });

        System.out.println(result);

        String logString = log.stream().collect(Collectors.joining("\n"));
        System.out.println(logString);

        assertEquals(8, log.size());
        assertTrue(log.get(2).contains("Tx 1 start reading ..."));
        assertTrue(log.get(3).contains("Tx 1 reading finished"));
        assertTrue(log.get(4).contains("Tx 2 start reading ..."));
        assertTrue(log.get(5).contains("Tx 1 finished"));
        assertTrue(log.get(6).contains("Tx 2 reading finished")); // Reading 2 waits for end of Tx 1
        assertTrue(log.get(7).contains("Tx 2 finished"));
    }

    @Test
    public void testConcurrentRead_pessimistic_write() throws Exception {
        // Tx 1 locks the entity, force serialization
        Runnable r1 = () ->
                readEntityInTx(em, entity1.getId(), "Tx 1", 0, 3000, LockModeType.PESSIMISTIC_WRITE);

        // Tx 2 reading waits for end of Tx 1, use different EntityManager !
        Runnable r2 = () ->
                readEntityInTx(em2, entity1.getId(), "Tx 2", 500, 0, LockModeType.PESSIMISTIC_WRITE);

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        executorService.submit(r1);
        executorService.submit(r2);
        executorService.awaitTermination(6, TimeUnit.SECONDS);

        List<PlEntity> result = doInTransaction(em, (e) -> {
            return e.createQuery("select p from PlEntity p", PlEntity.class)
                    .getResultList();
        });

        System.out.println(result);

        String logString = log.stream().collect(Collectors.joining("\n"));
        System.out.println(logString);

        assertEquals(8, log.size());
        assertTrue(log.get(2).contains("Tx 1 start reading ..."));
        assertTrue(log.get(3).contains("Tx 1 reading finished"));
        assertTrue(log.get(4).contains("Tx 2 start reading ..."));
        assertTrue(log.get(5).contains("Tx 1 finished"));
        assertTrue(log.get(6).contains("Tx 2 reading finished")); // Reading 2 waits for end of Tx 1
        assertTrue(log.get(7).contains("Tx 2 finished"));
    }

    @Test
    public void testConcurrentUpdate_pessimistic_read() throws Exception {
        // Tx 1 locks the entity, no serialization
        Runnable r1 = () ->
                updateEntityInTx(em, entity1.getId(), "Tx 1", 0, 3000, LockModeType.PESSIMISTIC_READ);

        // Tx 2 locks the same entity, use different EntityManager !
        Runnable r2 = () ->
                updateEntityInTx(em2, entity1.getId(), "Tx 2", 500, 0, LockModeType.PESSIMISTIC_READ);

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        executorService.submit(r1);
        executorService.submit(r2);
        executorService.awaitTermination(6, TimeUnit.SECONDS);

        List<PlEntity> result = doInTransaction(em, (e) -> {
            return e.createQuery("select p from PlEntity p", PlEntity.class)
                    .getResultList();
        });

        System.out.println(result);

        String logString = log.stream().collect(Collectors.joining("\n"));
        System.out.println(logString);

        assertEquals(8, log.size());
        assertTrue(log.get(2).contains("Tx 1 start reading ..."));
        assertTrue(log.get(3).contains("Tx 1 reading finished"));
        assertTrue(log.get(4).contains("Tx 2 start reading ..."));
        assertTrue(log.get(5).contains("Tx 2 reading finished"));  // starts befor Tx 1 has finished
        assertTrue(log.get(6).contains("Tx 2 finished"));
        assertTrue(log.get(7).contains("Tx 1 finished"));
    }

    void updateEntityInTx(EntityManager entityManager, long entityId, String txName, int delayBefore, int delayAfter, LockModeType lockMode) {
        doInTransaction(entityManager, (e) -> {
            log(txName + " starting ...");
            sleep(delayBefore);  // Wait before reading

            log(txName + " start reading ...");
            PlEntity entity = e.find(PlEntity.class, entityId, lockMode);
            log(txName + " reading finished");

            entity.setName("name updated by " + txName);

            sleep(delayAfter);  // Simulate long Tx
            log(txName + " finished");
        });
    }

    void readEntityInTx(EntityManager entityManager, long entityId, String txName, int delayBefore, int delayAfter, LockModeType lockMode) {
        Map<String, Object> hints = new HashMap<>();
        hints.put("javax.persistence.lock.timeout", 100);
        hints.put("javax.persistence.query.timeout", 100);
        hints.put("jakarta.persistence.lock.timeout", 100);
        hints.put("jakarta.persistence.query.timeout", 100);

        doInTransaction(entityManager, (e) -> {
            log(txName + " starting ...");
            sleep(delayBefore);  // Wait before reading

            log(txName + " start reading ...");
            PlEntity entity = e.find(PlEntity.class, entityId, lockMode, hints);
            log(txName + " reading finished");

            sleep(delayAfter);  // Simulate long Tx
            log(txName + " finished");
        });
    }

    void doInTransaction(EntityManager em, Consumer<EntityManager> consumer) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        consumer.accept(em);
        tx.commit();
        em.clear();
    }

    List doInTransaction(EntityManager em, Function<EntityManager, List> function) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        List result = function.apply(em);
        tx.commit();
        em.clear();
        return result;
    }

    void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    void log(String msg) {
        String threadName = Thread.currentThread().getName();
        LocalDateTime now = LocalDateTime.now();
        this.log.add(threadName + ": " + now + ": " + msg);
    }
}
