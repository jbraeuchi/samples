package envers.entities;

import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Audited
@Access(AccessType.FIELD)
@Table(name = "ENV_PARENT")
public class EnvParent {

    @Id
    @GeneratedValue
    private long id;
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parent")
    private Set<EnvChild> children = new HashSet<EnvChild>();

    @ElementCollection
    @CollectionTable(name = "ENV_CHILD_EMBED")
    private Set<EnvChildEmbed> embeddedChildren = new HashSet<EnvChildEmbed>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public Set<EnvChild> getChildren() {
        return children;
    }

    public Set<EnvChildEmbed> getEmbeddedChildren() {
        return embeddedChildren;
    }

    @Override
    public String toString() {
        return "EnvParent [id=" + id + ", name=" + name + ", children=" + children + ",embeddeChildren=" + embeddedChildren + "]";
    }


}
