package envers.entities;

import org.hibernate.envers.Audited;

import javax.persistence.*;

@Entity
@Audited
@Access(AccessType.FIELD)
@Table(name = "ENV_PERSON")
public class EnvPersonMinimal {

    @Id
    @GeneratedValue
    private long id;
    @Column(updatable = false)
    private String name;
    private int status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
