package elementcollection;

import junit.framework.TestCase;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Test extends TestCase {

    public void testElementCollection_udate() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TEST");
        EntityManager em = emf.createEntityManager();

        Element el1 = new Element();
        el1.setName("Element One");
        el1.setDate(System.currentTimeMillis());

        Element el2 = new Element();
        el2.setName("Element Two");
        el2.setDate(System.currentTimeMillis());

        Entity e1 = new Entity();
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
        Entity e1db = em.find(Entity.class, e1.getId());

        Element eldb = e1db.getElements().iterator().next();
        eldb.setName("Element UPDATED");

        Element el3 = new Element();
        el3.setName("Element Three");
        el3.setDate(System.currentTimeMillis());
        e1db.getElements().add(el3);
        tx2.commit();
    }
}
