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
public class EmEmbeddableTwo {
    @Column(name = "NAME_TWO")
    private String name;

    @Column(name = "DATE_TWO")
    @Type(type = "org.joda.time.contrib.hibernate.PersistentLocalDate")
    private LocalDate date;

    @Embedded
    private EmEmbeddableThree three;

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

    public EmEmbeddableThree getThree() {
        return three;
    }

    public void setThree(EmEmbeddableThree three) {
        this.three = three;
    }
}
