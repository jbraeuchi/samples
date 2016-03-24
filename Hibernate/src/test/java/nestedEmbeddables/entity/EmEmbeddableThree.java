package nestedEmbeddables.entity;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

import javax.persistence.*;

/**
 * Created by jakob on 24.03.2016.
 */
@Embeddable
public class EmEmbeddableThree {
    @Column(name = "NAME_THREE")
    private String name;

    @Column(name = "DATE_THREE")
    @Type(type = "org.joda.time.contrib.hibernate.PersistentLocalDate")
    private LocalDate date;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }


}
