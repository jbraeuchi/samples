package nestedEmbeddables.entity;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

import javax.persistence.*;
import java.util.Set;


/**
 * Created by jakob on 24.03.2016.
 */
@Entity
@Table(name = "EM_ENTITY")
public class EmEntity {
    @Id
    @GeneratedValue
    private long id;
    private String name;

    @Type(type = "org.joda.time.contrib.hibernate.PersistentLocalDate")
    private LocalDate birthdate;

    @Embedded
    private EmEmbeddableOne one;

    @ElementCollection
    @CollectionTable(name = "EM_ENTITY_TWOS")
    private Set<EmEmbeddableTwo> manyTwos;

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

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public EmEmbeddableOne getOne() {
        return one;
    }

    public void setOne(EmEmbeddableOne one) {
        this.one = one;
    }

    public Set<EmEmbeddableTwo> getManyTwos() {
        return manyTwos;
    }

    public void setManyTwos(Set<EmEmbeddableTwo> manyTwos) {
        this.manyTwos = manyTwos;
    }
}
