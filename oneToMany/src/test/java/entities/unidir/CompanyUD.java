package entities.unidir;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.envers.AuditJoinTable;
import org.hibernate.envers.Audited;

/**
 * OneToMany with JoinColumn unidirectional
 */
@Entity
@Audited
@Table(name = "O2M_CompanyUD")
public class CompanyUD {

	@Id
	@GeneratedValue
	private long id;

	@Version
	private long version;

	@Column(name = "Name")
	private String name;

	// Envers erstell Jointable dazu !
	@AuditJoinTable(name = "O2M_COMP_EMP_UD_AUD")
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "Id_Company")
	private Set<EmployeeUD> employees = new HashSet<EmployeeUD>();

	@OneToOne(cascade=CascadeType.ALL)
	private EmployeeUD manager;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public Set<EmployeeUD> getEmployees() {
		return employees;
	}

	public void setEmployees(Set<EmployeeUD> employees) {
		this.employees = employees;
	}

	public EmployeeUD getManager() {
		return manager;
	}

	public void setManager(EmployeeUD manager) {
		this.manager = manager;
	}

}
