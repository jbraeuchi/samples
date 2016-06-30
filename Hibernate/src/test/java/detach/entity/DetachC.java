package detach.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by jakob on 30.06.2016.
 */
@Entity
@Table(name = "DETACH_C")
public class DetachC {
    @Id
    @GeneratedValue
    private long id;
    private String name;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
