package detach.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by jakob on 30.06.2016.
 */
@Entity
@Table(name = "DETACH_A")
public class DetachA {
    @Id
    @GeneratedValue
    private long id;
    private String name;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<DetachB> bSet = new HashSet<>();

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<DetachB> getbSet() {
        return bSet;
    }

    public void setbSet(Set<DetachB> bSet) {
        this.bSet = bSet;
    }
}
