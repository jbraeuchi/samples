package elementcollection;

import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Tests {

    @Test
    public void testElementCollection_update() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TEST");
        EntityManager em = emf.createEntityManager();

//        long now = System.currentTimeMillis();
        Element el1 = new Element();
        el1.setName("Element One");
        el1.setDate(1000);

        Element el2 = new Element();
        el2.setName("Element Two");
        el2.setDate(2000);

        EcEntity e1 = new EcEntity();
        e1.setName("Entity 1");
        e1.getElements().add(el1);
        e1.getElements().add(el2);

        EntityTransaction tx1 = em.getTransaction();
        tx1.begin();
        em.persist(e1);
        tx1.commit();

        em.clear();

        EntityTransaction tx2 = em.getTransaction();
        tx2.begin();
        EcEntity e1db = em.find(EcEntity.class, e1.getId());

        Element eldb = e1db.getElements().iterator().next();
        eldb.setName("Element UPDATED");

        Element el3 = new Element();
        el3.setName("Element Three");
        el3.setDate(3000);
        e1db.getElements().add(el3);
        tx2.commit();
    }
}
