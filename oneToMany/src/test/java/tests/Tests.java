package tests;

import entities.bidir.CompanyBD;
import entities.bidir.EmployeeBD;
import entities.bidir2.CompanyBD2;
import entities.bidir2.EmployeeBD2;
import entities.bidir3.CompanyBD3;
import entities.bidir3.EmployeeBD3;
import entities.embed.CompanyEB;
import entities.embed.EmployeeEB;
import entities.embed2.CompanyEB2;
import entities.embed2.EmployeeEB2;
import entities.unidir.CompanyUD;
import entities.unidir.EmployeeUD;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.*;
import java.util.List;

public class Tests {
    @Test
    public void testBidir() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TEST_O2M");
        EntityManager em = emf.createEntityManager();

        CompanyBD company = new CompanyBD();
        company.setName("IBM");

        EmployeeBD emp1 = new EmployeeBD();
        emp1.setName("Employee 1");
        emp1.setCompany(company);
        company.getEmployees().add(emp1);

        EmployeeBD emp2 = new EmployeeBD();
        emp2.setName("Employee 2");
        emp2.setCompany(company);
        company.getEmployees().add(emp2);

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(company);
        tx.commit();
        em.close();

        em = emf.createEntityManager();
        TypedQuery<CompanyBD> qp = em.createQuery("select c from CompanyBD c", CompanyBD.class);
        List<CompanyBD> companies = qp.getResultList();
        CompanyBD cmp = companies.get(0);

        Assert.assertEquals(1, companies.size());
        Assert.assertEquals(2, cmp.getEmployees().size());
        Assert.assertEquals(null, companies.get(0).getManager());

        // Add The Boss
        tx = em.getTransaction();
        tx.begin();

        EmployeeBD manager = new EmployeeBD();
        manager.setName("The Boss");
        manager.setCompany(company);
        cmp.getEmployees().add(manager);
        cmp.setManager(manager);
        tx.commit();
        em.close();

        em = emf.createEntityManager();
        qp = em.createQuery("select c from CompanyBD c", CompanyBD.class);
        companies = qp.getResultList();
        cmp = companies.get(0);

        Assert.assertEquals(1, companies.size());
        Assert.assertEquals(3, cmp.getEmployees().size());
        Assert.assertEquals("The Boss", companies.get(0).getManager().getName());

    }

    @Test
    public void testUnidir() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TEST_O2M");
        EntityManager em = emf.createEntityManager();

        CompanyUD company = new CompanyUD();
        company.setName("IBM");

        EmployeeUD emp1 = new EmployeeUD();
        emp1.setName("Employee 1");
        company.getEmployees().add(emp1);

        EmployeeUD emp2 = new EmployeeUD();
        emp2.setName("Employee 2");
        company.getEmployees().add(emp2);

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(company);
        tx.commit();
        em.close();

        em = emf.createEntityManager();
        TypedQuery<CompanyUD> qp = em.createQuery("select c from CompanyUD c", CompanyUD.class);
        List<CompanyUD> companies = qp.getResultList();
        CompanyUD cmp = companies.get(0);

        Assert.assertEquals(1, companies.size());
        Assert.assertEquals(2, cmp.getEmployees().size());
        Assert.assertEquals(null, companies.get(0).getManager());

        // Add The Boss
        tx = em.getTransaction();
        tx.begin();

        EmployeeUD manager = new EmployeeUD();
        manager.setName("The Boss");
        cmp.getEmployees().add(manager);
        cmp.setManager(manager);
        tx.commit();
        em.close();

        em = emf.createEntityManager();
        qp = em.createQuery("select c from CompanyUD c", CompanyUD.class);
        companies = qp.getResultList();
        cmp = companies.get(0);

        Assert.assertEquals(1, companies.size());
        Assert.assertEquals(3, cmp.getEmployees().size());
        Assert.assertEquals("The Boss", companies.get(0).getManager().getName());
    }

    @Test
    public void testEmbed2() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TEST_O2M");
        EntityManager em = emf.createEntityManager();

        CompanyEB2 company = new CompanyEB2();
        company.setName("IBM");

        EmployeeEB2 emp1 = new EmployeeEB2();
        emp1.setName("Employee 1");
        company.getEmployees().add(emp1);

        EmployeeEB2 emp2 = new EmployeeEB2();
        emp2.setName("Employee 2");
        company.getEmployees().add(emp2);

        EmployeeEB2 manager = new EmployeeEB2();
        manager.setName("Manager 1");
        company.getManagers().add(manager);

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(company);
        tx.commit();
        em.close();

        em = emf.createEntityManager();
        TypedQuery<CompanyEB2> qp = em.createQuery("select c from CompanyEB2 c", CompanyEB2.class);
        List<CompanyEB2> companies = qp.getResultList();
        CompanyEB2 cmp = companies.get(0);

        Assert.assertEquals(1, companies.size());
        Assert.assertEquals(2, cmp.getEmployees().size());
        Assert.assertEquals(1, cmp.getManagers().size());
    }

    @Test
    public void testEmbed() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TEST_O2M");
        EntityManager em = emf.createEntityManager();

        CompanyEB company = new CompanyEB();
        company.setName("IBM");

        EmployeeEB emp1 = new EmployeeEB();
        emp1.setName("Employee 1");
        company.getEmployees().add(emp1);

        EmployeeEB emp2 = new EmployeeEB();
        emp2.setName("Employee 2");
        company.getEmployees().add(emp2);

        EmployeeEB manager = new EmployeeEB();
        manager.setName("Manager 1");
        company.getManagers().add(manager);

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(company);
        tx.commit();
        em.close();

        em = emf.createEntityManager();
        TypedQuery<CompanyEB> qp = em.createQuery("select c from CompanyEB c", CompanyEB.class);
        List<CompanyEB> companies = qp.getResultList();
        CompanyEB cmp = companies.get(0);

        Assert.assertEquals(1, companies.size());
        Assert.assertEquals(2, cmp.getEmployees().size());
        Assert.assertEquals(1, cmp.getManagers().size());
    }

    @Test
    public void testBidir3() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TEST_O2M");
        EntityManager em = emf.createEntityManager();

        CompanyBD3 company = new CompanyBD3();
        company.setName("IBM");

        EmployeeBD3 emp1 = new EmployeeBD3();
        emp1.setName("Employee 1");
        emp1.setCompany(company);
        company.getEmployees().add(emp1);

        EmployeeBD3 emp2 = new EmployeeBD3();
        emp2.setName("Employee 2");
        emp2.setCompany(company);
        company.getEmployees().add(emp2);

        EmployeeBD3 manager = new EmployeeBD3();
        manager.setName("Manager 1");
        manager.setCompany(company);
        company.getManagers().add(manager);

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(company);
        tx.commit();
        em.close();

        em = emf.createEntityManager();
        TypedQuery<CompanyBD3> qp = em.createQuery("select c from CompanyBD3 c", CompanyBD3.class);
        List<CompanyBD3> companies = qp.getResultList();
        CompanyBD3 cmp = companies.get(0);

        Assert.assertEquals(1, companies.size());
        Assert.assertEquals(2, cmp.getEmployees().size());
        Assert.assertEquals(1, cmp.getManagers().size());
    }

    @Test
    public void testBidir2() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TEST_O2M");
        EntityManager em = emf.createEntityManager();

        CompanyBD2 company = new CompanyBD2();
        company.setName("IBM");

        EmployeeBD2 emp1 = new EmployeeBD2();
        emp1.setName("Employee 1");
        emp1.setCompanyEmployee(company);
        company.getEmployees().add(emp1);

        EmployeeBD2 emp2 = new EmployeeBD2();
        emp2.setName("Employee 2");
        emp2.setCompanyEmployee(company);
        company.getEmployees().add(emp2);

        EmployeeBD2 manager = new EmployeeBD2();
        manager.setName("Manager 1");
        manager.setCompanyManager(company);
        company.getManagers().add(manager);

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(company);
        tx.commit();
        em.close();

        em = emf.createEntityManager();
        TypedQuery<CompanyBD2> qp = em.createQuery("select c from CompanyBD2 c", CompanyBD2.class);
        List<CompanyBD2> companies = qp.getResultList();
        CompanyBD2 cmp = companies.get(0);

        Assert.assertEquals(1, companies.size());
        Assert.assertEquals(2, cmp.getEmployees().size());
        Assert.assertEquals(1, cmp.getManagers().size());
    }

}
