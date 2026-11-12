package entities.bidir2;

import org.hibernate.envers.Audited;

import javax.persistence.*;

@Entity
@Audited
@Table(name = "O2M_EmployeeBD2")
public class EmployeeBD2 {

	@Id
	@GeneratedValue
	private long id;
	
	@Version
	private long version;
	
	@Column(name="Name")
	private String name;

	@ManyToOne
	@JoinColumn(name = "Id_Company_Emp")
	private CompanyBD2 companyEmployee;

	@ManyToOne
	@JoinColumn(name = "Id_Company_Mgr")
	private CompanyBD2 companyManager;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public CompanyBD2 getCompanyEmployee() {
		return companyEmployee;
	}

	public void setCompanyEmployee(CompanyBD2 companyEmployee) {
		this.companyEmployee = companyEmployee;
	}

    public CompanyBD2 getCompanyManager() {
        return companyManager;
    }

    public void setCompanyManager(CompanyBD2 companyManager) {
        this.companyManager = companyManager;
    }
}
