package entities.bidir;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.envers.Audited;

/**
 * OneToMany with mappedBy bidirectional
 */
@Entity
@Audited
@Table(name = "O2M_CompanyBD")
public class CompanyBD {

	@Id
	@GeneratedValue
	private long id;

	@Version
	private long version;

	@Column(name = "Name")
	private String name;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "company")
	private Set<EmployeeBD> employees = new HashSet<EmployeeBD>();

	@OneToOne(cascade=CascadeType.ALL)
	private EmployeeBD manager;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public Set<EmployeeBD> getEmployees() {
		return employees;
	}

	public void setEmployees(Set<EmployeeBD> employees) {
		this.employees = employees;
	}

	public EmployeeBD getManager() {
		return manager;
	}

	public void setManager(EmployeeBD manager) {
		this.manager = manager;
	}

}
