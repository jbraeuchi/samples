package elementcollection;

import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "EC_ENTITY")
@Audited
public class EcEntity {
    @Id
    @GeneratedValue
    private long id;
    private String name;

    @ElementCollection
    @CollectionTable(name = "EC_ELEMENT")
    private Set<Element> elements = new HashSet<>();

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Set<Element> getElements() {
        return elements;
    }

    public void setElements(Set<Element> elements) {
        this.elements = elements;
    }
}
