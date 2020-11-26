package entities.unidirNonPk;

import org.hibernate.envers.Audited;

import javax.persistence.*;

@Entity
@Table(name = "O2M_EmployeeUD_NPK")
public class EmployeeUDNPK {

	@Id
	@GeneratedValue
	private long id;
	
	@Version
	private long version;

	@Column(name="CompanyName", nullable = false)
	private String companyName;

	@Column(name="Name")
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
}
