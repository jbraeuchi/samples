package envers.entities;

import javax.persistence.Embeddable;

/**
 * Created by jakob on 17.03.2016.
 */
@Embeddable
public class EnvChildEmbed {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "EnvChildEmbed [name=" + name + "]";
    }
}
