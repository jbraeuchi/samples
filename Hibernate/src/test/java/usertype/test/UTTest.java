package usertype.test;

import org.junit.Test;
import usertype.entity.UtEntity;

import javax.persistence.*;
import java.util.List;
import java.util.function.Consumer;

import static org.junit.Assert.*;

public class UTTest {

    @Test
    public void persist_null() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TEST");
        EntityManager em = emf.createEntityManager();

        UtEntity entity = new UtEntity();
        entity.setName("ut-null");
        entity.setBool1(null);
        entity.setBool2(null);

        System.out.println(entity);
        doInTransaction(em, e -> e.persist(entity));

        UtEntity fromdb = em.find(UtEntity.class, entity.getId());
        System.out.println(fromdb);

        assertEquals(entity, fromdb);
        assertEquals("ut-null", fromdb.getName());
        assertNull(fromdb.getBool1());
        assertFalse(fromdb.getBool2());
    }

    @Test
    public void persist_true() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TEST");
        EntityManager em = emf.createEntityManager();

        UtEntity entity = new UtEntity();
        entity.setName("ut-true");
        entity.setBool1(true);
        entity.setBool2(true);

        System.out.println(entity);
        doInTransaction(em, e -> e.persist(entity));

        UtEntity fromdb = em.find(UtEntity.class, entity.getId());
        System.out.println(fromdb);

        assertEquals(entity, fromdb);
        assertEquals("ut-true", fromdb.getName());
        assertTrue(fromdb.getBool1());
        assertTrue(fromdb.getBool2());
    }

    @Test
    public void persist_true_query() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TEST");
        EntityManager em = emf.createEntityManager();

        UtEntity entity = new UtEntity();
        entity.setName("ut-true-q");
        entity.setBool1(null);
        entity.setBool2(true);

        System.out.println(entity);
        doInTransaction(em, e -> e.persist(entity));

        TypedQuery<UtEntity> q = em.createNamedQuery("findBybool2true", UtEntity.class);
        List<UtEntity> results = q.getResultList();
        System.out.println(results);
        UtEntity fromdb = results.get(0);

        assertEquals(entity, fromdb);
        assertEquals("ut-true-q", fromdb.getName());
        assertNull(fromdb.getBool1());
        assertTrue(fromdb.getBool2());
    }

    @Test
    public void persist_false() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TEST");
        EntityManager em = emf.createEntityManager();

        UtEntity entity = new UtEntity();
        entity.setName("ut-false");
        entity.setBool1(false);
        entity.setBool2(false);

        System.out.println(entity);
        doInTransaction(em, e -> e.persist(entity));

        UtEntity fromdb = em.find(UtEntity.class, entity.getId());
        System.out.println(fromdb);

        assertEquals(entity, fromdb);
        assertEquals("ut-false", fromdb.getName());
        assertFalse(fromdb.getBool1());
        assertFalse(fromdb.getBool2());
    }

    private void doInTransaction(EntityManager em, Consumer<EntityManager> consumer) {
        EntityTransaction tx1 = em.getTransaction();
        tx1.begin();

        consumer.accept(em);

        tx1.commit();
        em.clear();
    }
}
