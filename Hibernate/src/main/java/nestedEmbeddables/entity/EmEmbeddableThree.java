package nestedEmbeddables.entity;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDate;

/**
 * Created by jakob on 24.03.2016.
 */
@Embeddable
public class EmEmbeddableThree {
    @Column(name = "NAME_THREE")
    private String name;

    @Column(name = "DATE_THREE")
    private LocalDate date;

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


}
