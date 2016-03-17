package envers.tests;

import envers.entities.*;
import junit.framework.Assert;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditQuery;
import org.hibernate.envers.query.criteria.internal.IlikeAuditExpression;
import org.hibernate.envers.query.internal.property.EntityPropertyName;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.List;

public class Tests {

    @Test
    public void test() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TEST");
        EntityManager em = emf.createEntityManager();

        // Mr. Bond wird erfasst
        EnvPerson p1 = new EnvPerson();
        p1.setName("Bond");
        p1.setVorname("James");
        p1.setAdresse("Somewhere in the Bahamas");
        p1.setGeburtstag(LocalDate.of(1920, 11, 11));

        EntityTransaction tx1 = em.getTransaction();
        tx1.begin();
        em.persist(p1);
        tx1.commit();           // A: erste Revision

        long id = p1.getId();

        // Mr. Bond reist weiter
        EnvPerson p2 = em.find(EnvPerson.class, id);
        p2.setAdresse("Somewhere in the Fijis");

        EntityTransaction tx2 = em.getTransaction();
        tx2.begin();
        em.merge(p2);
        tx2.commit();           // B: zweite Revision

        // Mr. Bond wird gelöscht (Sicherheit geht vor)      
        EnvPerson p3 = em.find(EnvPerson.class, id);
        EntityTransaction tx3 = em.getTransaction();
        tx3.begin();
        em.remove(p3);
        tx3.commit();           // C: dritte Revision

        AuditReader ar = AuditReaderFactory.get(em);
        List<Number> revisions = ar.getRevisions(EnvPerson.class, id);  // alle Revisions
        EnvPerson p4 = ar.find(EnvPerson.class, id, revisions.get(0));
        System.out.println(p4.getAdresse());

        em.close();
    }

    @Test
    public void testAllEntities() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TEST");
        EntityManager em = emf.createEntityManager();

        // Mr. Bond wird erfasst
        EnvPerson p1 = new EnvPerson();
        p1.setName("Bond");
        p1.setVorname("James");
        p1.setAdresse("Somewhere in the Bahamas");
        p1.setGeburtstag(LocalDate.of(1920, 11, 11));

        EntityTransaction tx1 = em.getTransaction();
        tx1.begin();
        em.persist(p1);
        tx1.commit();           // A: erste Revision

        long id = p1.getId();

        // Mr. Bond reist weiter, Mr. Holmes wird erfasst, IBM wird erfasst
        EnvPerson p2 = em.find(EnvPerson.class, id);
        p2.setAdresse("Somewhere in the Fijis");

        EnvPerson px = new EnvPerson();
        px.setName("Holmes");
        px.setVorname("Sherlock");
        px.setAdresse("221b Baker Street, London");
        //      px.setGeburtstag(LocalDate.of(1914, 1, 6));

        EnvCompany ibm = new EnvCompany();
        ibm.setName("IBM");

        EntityTransaction tx2 = em.getTransaction();
        tx2.begin();
        em.persist(p2);
        em.persist(px);
        em.persist(ibm);
        tx2.commit();           // B: zweite Revision

        AuditReader ar = AuditReaderFactory.get(em);
        List<Number> revisions = ar.getRevisions(EnvPerson.class, id);  // alle Revisions

        List<Object> entities1 = ar.getCrossTypeRevisionChangesReader().findEntities(revisions.get(0));
        System.out.println("*** REV1: " + entities1);

        List<Object> entities2 = ar.getCrossTypeRevisionChangesReader().findEntities(revisions.get(1));
        System.out.println("*** REV2: " + entities2);

        // Liest ein Revision mit bestimmter Adresse
        AuditQuery aq = ar.createQuery().forEntitiesAtRevision(EnvPerson.class, revisions.get(1));
        aq.add(new IlikeAuditExpression(new EntityPropertyName("adresse"), "%London%"));
        List<EnvPerson> res = aq.getResultList();
        System.out.println(res);

        em.close();
    }

    @Test
    public void testOneToMany() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TEST");
        EntityManager em = emf.createEntityManager();

        // Initial Version of P1 and C1
        EnvParent p1 = new EnvParent();
        p1.setName("Parent 1");

        EnvChild c1 = new EnvChild();
        c1.setName("Child 1");
        c1.setParent(p1);
        p1.getChildren().add(c1);

        EntityTransaction tx1 = em.getTransaction();
        tx1.begin();
        em.persist(p1);
        tx1.commit();           // A: erste Revision

        long idParent = p1.getId();
        long idChild = c1.getId();

        // neuer Name, neues Child C2
        EnvParent p2 = em.find(EnvParent.class, idParent);
        p2.setName("Parent 1 update");

        EnvChild c2 = new EnvChild();
        c2.setName("Child 2");
        c2.setParent(p2);
        p2.getChildren().add(c2);

        EntityTransaction tx2 = em.getTransaction();
        tx2.begin();
        em.persist(p2);
        tx2.commit();           // B: zweite Revision

        AuditReader ar = AuditReaderFactory.get(em);

        // Liest beide Revisionen von EnvParent, die erste hat nur ein Child, die zweite zwei
        List<Number> revParent = ar.getRevisions(EnvParent.class, idParent);  // alle Revisions Parent
        EnvParent envParent1 = ar.find(EnvParent.class, idParent, revParent.get(0));
        EnvParent envParent2 = ar.find(EnvParent.class, idParent, revParent.get(1));

        System.out.println(envParent1);
        System.out.println(envParent2);


        // Liest beide Revisionen von EnvChild
        List<Number> revChild = ar.getRevisions(EnvChild.class, idChild);  // alle Revisions Child
        EnvChild envChild1 = ar.find(EnvChild.class, idChild, revParent.get(0));
        EnvChild envChild2 = ar.find(EnvChild.class, idChild, revParent.get(1));

        System.out.println(envChild1);
        System.out.println(envChild2);

        Assert.assertEquals(1, envParent1.getChildren().size());
        Assert.assertEquals(2, envParent2.getChildren().size());

        Assert.assertTrue(envParent1.getChildren().contains(c1));

        Assert.assertTrue(envParent2.getChildren().contains(c1));
        Assert.assertTrue(envParent2.getChildren().contains(c2));

        em.close();
    }

    @Test
    public void testEmbedded() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TEST");
        EntityManager em = emf.createEntityManager();

        // Initial Version of P1 and C1
        EnvParent p1 = new EnvParent();
        p1.setName("Parent 1");

        EnvChildEmbed c1 = new EnvChildEmbed();
        c1.setName("Child 1");
        p1.getEmbeddedChildren().add(c1);

        EntityTransaction tx1 = em.getTransaction();
        tx1.begin();
        em.persist(p1);
        tx1.commit();           // A: erste Revision

        long idParent = p1.getId();

        // neuer Name, neues Child C2
        EnvParent p2 = em.find(EnvParent.class, idParent);
        p2.setName("Parent 1 update");

        EnvChildEmbed c2 = new EnvChildEmbed();
        c2.setName("Child 2");
        p2.getEmbeddedChildren().add(c2);

        EntityTransaction tx2 = em.getTransaction();
        tx2.begin();
        em.persist(p2);
        tx2.commit();           // B: zweite Revision

        // C1 löschen, neues Child C3
        EnvParent p3 = em.find(EnvParent.class, idParent);
        p3.getEmbeddedChildren().remove(c1);

        EnvChildEmbed c3 = new EnvChildEmbed();
        c3.setName("Child 3");
        p3.getEmbeddedChildren().add(c3);

        EntityTransaction tx3 = em.getTransaction();
        tx3.begin();
        em.persist(p3);
        tx3.commit();           // C: dritte Revision

        AuditReader ar = AuditReaderFactory.get(em);

        // Liest drei Revisionen von EnvParent, die erste hat nur ein Child, die zweite und dritte zwei
        List<Number> revParent = ar.getRevisions(EnvParent.class, idParent);  // alle Revisions Parent
        EnvParent envParent1 = ar.find(EnvParent.class, idParent, revParent.get(0));
        EnvParent envParent2 = ar.find(EnvParent.class, idParent, revParent.get(1));
        EnvParent envParent3 = ar.find(EnvParent.class, idParent, revParent.get(2));

        System.out.println(envParent1);
        System.out.println(envParent2);
        System.out.println(envParent3);

        Assert.assertEquals(1, envParent1.getEmbeddedChildren().size());
        Assert.assertEquals(2, envParent2.getEmbeddedChildren().size());
        Assert.assertEquals(2, envParent3.getEmbeddedChildren().size());

        Assert.assertTrue(envParent1.getEmbeddedChildren().contains(c1));

        Assert.assertTrue(envParent2.getEmbeddedChildren().contains(c1));
        Assert.assertTrue(envParent2.getEmbeddedChildren().contains(c2));

        Assert.assertFalse(envParent3.getEmbeddedChildren().contains(c1));
        Assert.assertTrue(envParent3.getEmbeddedChildren().contains(c2));
        Assert.assertTrue(envParent3.getEmbeddedChildren().contains(c3));

        em.close();
    }

}
