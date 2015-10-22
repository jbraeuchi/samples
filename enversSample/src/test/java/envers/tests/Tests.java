package envers.tests;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditQuery;
import org.hibernate.envers.query.criteria.internal.IlikeAuditExpression;
import org.hibernate.envers.query.internal.property.EntityPropertyName;
import org.junit.Test;

import envers.entities.EnvCompany;
import envers.entities.EnvPerson;

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
}
