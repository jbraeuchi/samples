package nestedEmbeddables.entity;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

/**
 * Created by jakob on 24.03.2016.
 */
@Embeddable
public class EmEmbeddableOne {
    @Column(name = "NAME_ONE")
    private String name;

    @Column(name = "DATE_ONE")
    @Type(type = "org.joda.time.contrib.hibernate.PersistentLocalDate")
    private LocalDate date;

    @Embedded
    private EmEmbeddableTwo two;

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

    public EmEmbeddableTwo getTwo() {
        return two;
    }

    public void setTwo(EmEmbeddableTwo two) {
        this.two = two;
    }
}
