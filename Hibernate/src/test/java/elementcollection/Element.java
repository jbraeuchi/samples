package elementcollection;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Element {
    // https://stackoverflow.com/questions/3742897/hibernate-elementcollection-strange-delete-insert-behavior
    // nullable = false prevents delete/insert of whole collection
    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "DATE")
    private long date;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Element element = (Element) o;
        return date == element.date &&
                Objects.equals(name, element.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, date);
    }
}

