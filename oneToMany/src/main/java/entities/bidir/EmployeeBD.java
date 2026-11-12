package entities.bidir;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.envers.Audited;

@Entity
@Audited
@Table(name = "O2M_EmployeeBD")
public class EmployeeBD {

	@Id
	@GeneratedValue
	private long id;
	
	@Version
	private long version;
	
	@Column(name="Name")
	private String name;

	@ManyToOne
	@JoinColumn(name = "Id_Company")
	private CompanyBD company;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public CompanyBD getCompany() {
		return company;
	}

	public void setCompany(CompanyBD company) {
		this.company = company;
	}
	
}
