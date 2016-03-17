package envers.entities;

import org.hibernate.envers.Audited;

import javax.persistence.*;

/**
 * Created by jakob on 24.11.2015.
 */
@Entity
@Audited
@Access(AccessType.FIELD)
@Table(name = "ENV_CHILD")
public class EnvChild {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    @ManyToOne
    private EnvParent parent;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public EnvParent getParent() {
        return parent;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setParent(EnvParent parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "EnvChild [id=" + id + ", name=" + name + ", parentName" + parent.getName() + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EnvChild envChild = (EnvChild) o;

        return id == envChild.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
