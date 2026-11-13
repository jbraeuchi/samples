package entities.unidir;

import jakarta.persistence.*;

import org.hibernate.envers.Audited;

@Entity
@Audited
@Table(name = "O2M_EmployeeUD")
public class EmployeeUD {

	@Id
	@GeneratedValue
	private long id;
	
	@Version
	private long version;
	
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
	
}
