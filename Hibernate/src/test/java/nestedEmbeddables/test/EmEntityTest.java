package nestedEmbeddables.test;

import junit.framework.TestCase;
import nestedEmbeddables.entity.EmEmbeddableOne;
import nestedEmbeddables.entity.EmEmbeddableThree;
import nestedEmbeddables.entity.EmEmbeddableTwo;
import nestedEmbeddables.entity.EmEntity;
import org.joda.time.LocalDate;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * Created by jakob on 24.03.2016.
 */
public class EmEntityTest extends TestCase {

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
}