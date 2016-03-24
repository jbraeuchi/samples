package nestedEmbeddables.entity;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Created by jakob on 24.03.2016.
 */
@Embeddable
public class EmEmbeddableFour {
    @Column(name = "NAME_FOUR")
    private String name;

    @Column(name = "DATE_FOUR")
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
