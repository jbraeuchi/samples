package pessimisticLocking.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pessimisticLocking.entity.PlEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
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

    @BeforeEach
    public void init() {
        log = new CopyOnWriteArrayList();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TEST");
        em = emf.createEntityManager();
        em2 = emf.createEntityManager();

        doInTransaction(em, (e) -> {
            Query q = e.createQuery("delete from PlEntity");
            q.executeUpdate();
        });
    }

    @Test
    public void testCreate() {
        PlEntity entity1 = new PlEntity();
        entity1.setName1("E1 name1");
        entity1.setName2("E1 name2");

        PlEntity entity2 = new PlEntity();
        entity2.setName1("E2 name1");
        entity2.setName2("E2 name2");

        doInTransaction(em, (e) -> {
            e.persist(entity1);
            e.persist(entity2);
        });

        List<PlEntity> result = doInTransaction(em, (e) -> {
            return e.createQuery("select p from PlEntity p", PlEntity.class)
                    .setLockMode(LockModeType.PESSIMISTIC_READ)
                    .getResultList();
        });

        System.out.println(result);
    }

    @Test
    public void testConcurrentUpdate() throws Exception {
        PlEntity entity1 = new PlEntity();
        entity1.setName1("E1 name1");
        entity1.setName2("E1 name2");

        PlEntity entity2 = new PlEntity();
        entity2.setName1("E2 name1");
        entity2.setName2("E2 name2");

        doInTransaction(em, (e) -> {
            e.persist(entity1);
            e.persist(entity2);
        });

        // Tx 1 locks the entity
        Runnable r1 = () -> doInTransaction(em, (e) -> {
            updateEntity(e, entity1.getId(), "Tx 1", 0, 3000, LockModeType.PESSIMISTIC_WRITE);
        });

        // Tx 2 reading waits for end of Tx 1, use different EntityManager !
        Runnable r2 = () -> doInTransaction(em2, (e) -> {
            updateEntity(e, entity1.getId(), "Tx 2", 500, 0, LockModeType.PESSIMISTIC_WRITE);
        });

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

    void updateEntity(EntityManager entityManager, long entityId, String txName, int delayBefore, int delayAfter, LockModeType lockMode) {
        log(txName + " starting ...");
        sleep(delayBefore);  // Wait before reading

        log(txName + " start reading ...");
        PlEntity entity = entityManager.find(PlEntity.class, entityId, lockMode);
        log(txName + " reading finished");

        entity.setName1(txName + "name updated");

        sleep(delayAfter);  // Simulate long Tx
        log(txName + " finished");
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
