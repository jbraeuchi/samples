package envers.entities;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

@Entity
@Audited
@Access(AccessType.FIELD)
@Table(name = "ENV_COMPANY")
public class EnvCompany {

    @Id
    @GeneratedValue
    private long id;
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
    
    @Override
    public String toString() {
        return "EnvCompany [id=" + id + ", name=" + name + "]";
    }
    
}
