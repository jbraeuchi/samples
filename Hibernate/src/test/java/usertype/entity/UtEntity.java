package usertype.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "UT_ENTITY")
@NamedQueries(
        @NamedQuery(name = "findBybool2true", query = "select u from UtEntity u  where bool2 = true")
)
public class UtEntity {
    @Id
    @GeneratedValue
    private long id;
    private String name;

    private Boolean bool1;

    //    @Type(type="usertype.type.NonNullBooleanType")
    @Type(type = "usertype.type.NonNullBooleanCustomType")
    private Boolean bool2;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getBool1() {
        return bool1;
    }

    public void setBool1(Boolean bool1) {
        this.bool1 = bool1;
    }

    public Boolean getBool2() {
        return bool2;
    }

    public void setBool2(Boolean bool2) {
        this.bool2 = bool2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UtEntity utEntity = (UtEntity) o;

        return id == utEntity.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "UtEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", bool1=" + bool1 +
                ", bool2=" + bool2 +
                '}';
    }
}
