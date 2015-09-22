package tests;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.junit.Assert;
import org.junit.Test;

import entities.bidir.CompanyBD;
import entities.bidir.EmployeeBD;
import entities.unidir.CompanyUD;
import entities.unidir.EmployeeUD;

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

}
