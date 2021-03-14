package detach.test;

import detach.entity.DetachA;
import detach.entity.DetachB;
import detach.entity.DetachC;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * Created by jakob on 30.06.2016.
 */
public class DetachEntityTest {

    @Test
    public void testDetach() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TEST");
        EntityManager em = emf.createEntityManager();

        DetachA a = new DetachA();
        a.setName("Detach A");

        DetachB b1 = new DetachB();
        b1.setName("Detach B1");
        a.getbSet().add(b1);

        DetachB b2 = new DetachB();
        b2.setName("Detach B2");
        a.getbSet().add(b2);

        DetachC c1 = new DetachC();
        c1.setName("Detach C1");
        b1.setC(c1);

        EntityTransaction tx1 = em.getTransaction();
        tx1.begin();
        em.persist(a);
        tx1.commit();
        em.clear();

        // Detach B and modify C
        EntityTransaction tx2 = em.getTransaction();
        tx2.begin();
        DetachA aLoaded = em.find(DetachA.class, a.getId());
        DetachB b1Loaded = a.getbSet().stream().filter(b -> b.getName().equals("Detach B1")).findFirst().get();
        em.detach(b1Loaded);

        DetachC c1Loaded = em.find(DetachC.class, c1.getId());
        c1Loaded.setName("Detach C1 modified");

        DetachA aLoaded2 = em.find(DetachA.class, a.getId());
        System.out.println(aLoaded2);
        tx2.commit();
    }
}
