package entities.unidir;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

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
