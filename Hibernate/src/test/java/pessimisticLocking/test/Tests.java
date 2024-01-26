package pessimisticLocking.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pessimisticLocking.entity.PlEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;


public class Tests {

    EntityManager em;
    EntityManager em2;

    List<String> log = new ArrayList();

    @BeforeEach
    public void init() {
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
    public void testUpdate() throws Exception {
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

        Runnable r1 = () -> {
            log("Starting 1 ...");
            doInTransaction(em, (e) -> {
                log("Reading 1 ...");
                PlEntity entity = e.find(PlEntity.class, entity1.getId(), LockModeType.PESSIMISTIC_WRITE);
                log("Reading 1 finished");

                entity.setName1("E1 name runnable 1");
                sleep(3000);
                log("Tx 1 finished");
            });
            log("Finished 1");
        };

        Runnable r2 = () -> {
            log("Starting 2 ...");
            doInTransaction(em2, (e) -> {
                sleep(500);

                log("Reading 2 ...");
                PlEntity entity = e.find(PlEntity.class, entity1.getId(), LockModeType.PESSIMISTIC_WRITE);
                log("Reading 2 finished"); // should come after Tx1 finished because of LOCK

                entity.setName1("E1 name runnable 2");
                log("Tx 2 finished");
            });
            log("Finished 2");
        };

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
