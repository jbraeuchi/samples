package envers.entities;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

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
