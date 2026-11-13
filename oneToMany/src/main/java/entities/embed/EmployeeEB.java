package entities.embed;

import jakarta.persistence.*;

/**
 * Created by jakob on 23.11.2015.
 */
@Embeddable
public class EmployeeEB {
    @Column(name="Name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
