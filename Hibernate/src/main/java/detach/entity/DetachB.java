package detach.entity;

import javax.persistence.*;

/**
 * Created by jakob on 30.06.2016.
 */
@Entity
@Table(name = "DETACH_B")
public class DetachB {
    @Id
    @GeneratedValue
    private long id;
    private String name;

    @OneToOne (fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private DetachC c;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DetachC getC() {
        return c;
    }

    public void setC(DetachC c) {
        this.c = c;
    }
}
