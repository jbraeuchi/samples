package entities.unidirNonPk;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * OneToMany with JoinColumn unidirectional
 */
@Entity
@Table(name = "O2M_CompanyUD_NPK")
public class CompanyUDNPK implements Serializable {

	@Id
	@GeneratedValue
	private long id;

	@Version
	private long version;

	@Column(name = "Name")
	private String name;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "CompanyName" ,referencedColumnName = "Name")
	private Set<EmployeeUDNPK> employees = new HashSet<EmployeeUDNPK>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public Set<EmployeeUDNPK> getEmployees() {
		return employees;
	}

	public void setEmployees(Set<EmployeeUDNPK> employees) {
		this.employees = employees;
	}

}
