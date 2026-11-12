package entities.bidir3;

import org.hibernate.envers.Audited;

import javax.persistence.*;

@Entity
@Audited
@Table(name = "O2M_EmployeeBD3")
public class EmployeeBD3 {

	@Id
	@GeneratedValue
	private long id;
	
	@Version
	private long version;
	
	@Column(name="Name")
	private String name;

	@ManyToOne
	private CompanyBD3 company;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public CompanyBD3 getCompany() {
		return company;
	}

	public void setCompany(CompanyBD3 company) {
		this.company = company;
	}

}
