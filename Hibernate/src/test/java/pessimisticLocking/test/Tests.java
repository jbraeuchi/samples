package pessimisticLocking.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pessimisticLocking.entity.PlEntity;

import javax.persistence.*;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;


public class Tests {

    EntityManager em;
    EntityManager em2;

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
            System.out.println("Starting 1 ...");
            doInTransaction(em , (e) -> {
                System.out.println("Reading 1 ...");
                PlEntity entity = e.find(PlEntity.class, entity1.getId(), LockModeType.PESSIMISTIC_WRITE);
                System.out.println("Reading 1 finished");

                entity.setName1("E1 name runnable 1");
                sleep(3000);
                System.out.println("Tx 1 finished");
            });
            System.out.println("Finished 1");
        };

        Runnable r2 = () -> {
            System.out.println("Starting 2 ...");
            doInTransaction(em2 , (e) -> {
                sleep(500);

                System.out.println("Reading 2 ...");
                PlEntity entity = e.find(PlEntity.class, entity1.getId(), LockModeType.PESSIMISTIC_WRITE);
                System.out.println("Reading 2 finished, after Tx1 finished");

                entity.setName1("E1 name runnable 2");
                System.out.println("Tx 2 finished");
            });
            System.out.println("Finished 2");
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
}
