package entities.entitygraph;

import org.hibernate.envers.Audited;

import javax.persistence.*;

@Entity
@Audited
@Table(name = "O2M_EmployeeEG")
public class EmployeeEG {

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
