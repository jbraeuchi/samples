package nestedEmbeddables.entity;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

import javax.persistence.*;
import java.util.HashSet;
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

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    private LocalDate birthdate;

    @Embedded
    private EmEmbeddableOne one;

    @ElementCollection
    @CollectionTable(name = "EM_ENTITY_ONES")
    @AttributeOverrides({
            @AttributeOverride(name = "date", column = @Column(name = "OV_DATE_ONE")),
            @AttributeOverride(name = "two.date", column = @Column(name = "OV_DATE_TWO")),
            @AttributeOverride(name = "two.three.date", column = @Column(name = "OV_DATE_THREE")),

            @AttributeOverride(name = "name", column = @Column(name = "OV_NAME_ONE")),
            @AttributeOverride(name = "two.name", column = @Column(name = "OV_NAME_TWO")),
            @AttributeOverride(name = "two.three.name", column = @Column(name = "OV_NAME_THREE")),
    })
    private Set<EmEmbeddableOne> manyOnes = new HashSet<>();

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

    public Set<EmEmbeddableOne> getManyOnes() {
        return manyOnes;
    }

    public void setManyOnes(Set<EmEmbeddableOne> manyOnes) {
        this.manyOnes = manyOnes;
    }
}
