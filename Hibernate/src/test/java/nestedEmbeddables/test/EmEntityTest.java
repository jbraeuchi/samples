package nestedEmbeddables.test;

import junit.framework.TestCase;
import nestedEmbeddables.entity.*;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.dialect.MySQL57InnoDBDialect;
import org.hibernate.tool.hbm2ddl.SchemaExport;
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
    public void testExportSchema() {
//        AnnotationConfiguration configuration = new AnnotationConfiguration();
//        configuration.setProperty(Environment.DIALECT, MySQL57InnoDBDialect.class.getName());
//        SchemaExport schemaExport = new SchemaExport(configuration);
//        schemaExport.setFormat(true);
//        schemaExport.create(true, false);
    }

    @Test
    public void testEmbedded() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TEST");
        EntityManager em = emf.createEntityManager();

        EmEmbeddableFour four = new EmEmbeddableFour();
        four.setName("Embeddable Four");
        four.setDate(LocalDate.now());

        EmEmbeddableThree three = new EmEmbeddableThree();
        three.setName("Embeddable Three");
        three.setDate(LocalDate.now());
        three.setFour(four);

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


//        EntityTransaction tx1 = em.getTransaction();
//        tx1.begin();
//        em.persist(e1);
//        tx1.commit();
    }
}