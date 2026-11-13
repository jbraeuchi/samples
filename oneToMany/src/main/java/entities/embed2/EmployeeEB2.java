package entities.embed2;

import jakarta.persistence.*;

/**
 * Created by jakob on 23.11.2015.
 */
@Embeddable
public class EmployeeEB2 {
    @Column(name="Name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
