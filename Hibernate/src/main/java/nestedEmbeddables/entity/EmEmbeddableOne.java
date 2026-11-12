package nestedEmbeddables.entity;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Created by jakob on 24.03.2016.
 */
@Embeddable
public class EmEmbeddableOne {
    @Column(name = "NAME_ONE")
    private String name;

    @Column(name = "DATE_ONE")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmEmbeddableOne that = (EmEmbeddableOne) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, date);
    }
}
