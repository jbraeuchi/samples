package nestedEmbeddables.test;

import nestedEmbeddables.entity.EmEmbeddableOne;
import nestedEmbeddables.entity.EmEmbeddableThree;
import nestedEmbeddables.entity.EmEmbeddableTwo;
import nestedEmbeddables.entity.EmEntity;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDate;

/**
 * Created by jakob on 24.03.2016.
 */
public class EmEntityTest {

    @Test
    public void testEmbedded() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TEST");
        EntityManager em = emf.createEntityManager();

        EmEmbeddableThree three = new EmEmbeddableThree();
        three.setName("Embeddable Three");
        three.setDate(LocalDate.now());

        EmEmbeddableTwo two = new EmEmbeddableTwo();
        two.setName("Embeddable Two");
        two.setDate(LocalDate.now());
        two.setThree(three);

        EmEmbeddableOne one = new EmEmbeddableOne();
        one.setName("Embeddable One");
        one.setDate(LocalDate.now());
        one.setTwo(two);

        EmEntity e1 = new EmEntity();
        e1.setName("Entity 1");
        e1.setBirthdate(LocalDate.now());
        e1.setOne(one);
        e1.getManyOnes().add(one);

        EntityTransaction tx1 = em.getTransaction();
        tx1.begin();
        em.persist(e1);
        tx1.commit();
    }

    public void testEmbedded_udate() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TEST");
        EntityManager em = emf.createEntityManager();

        EmEmbeddableOne o1 = new EmEmbeddableOne();
        o1.setName("Embeddable One");
        o1.setDate(LocalDate.now());
        EmEmbeddableOne o2 = new EmEmbeddableOne();
        o2.setName("Embeddable Two");
        o2.setDate(LocalDate.now());

        EmEntity e1 = new EmEntity();
        e1.setName("Entity 1");
        e1.setBirthdate(LocalDate.now());
        e1.getManyOnes().add(o1);
        e1.getManyOnes().add(o2);

        EntityTransaction tx1 = em.getTransaction();
        tx1.begin();
        em.persist(e1);
        tx1.commit();

        em.clear();

        EntityTransaction tx2 = em.getTransaction();
        tx2.begin();
        EmEntity e1db = em.find(EmEntity.class, e1.getId());
        EmEmbeddableOne o3 = new EmEmbeddableOne();
        o3.setName("Embeddable Three");
        o3.setDate(LocalDate.now());
        e1db.getManyOnes().add(o3);
        tx2.commit();
    }
}
