package envers.entities;

import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Audited
@Access(AccessType.FIELD)
@Table(name = "ENV_PERSON")
public class EnvPerson {

    @Id
    @GeneratedValue
    private long id;
    private String vorname;
    private String name;
    private String adresse;
    private LocalDate geburtstag;
    private int status;

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public long getId() {
        return id;
    }

    public LocalDate getGeburtstag() {
        return geburtstag;
    }

    public void setGeburtstag(LocalDate geburtstag) {
        this.geburtstag = geburtstag;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "EnvPerson [id=" + id + ", vorname=" + vorname + ", name=" + name + ", adresse=" + adresse + "]";
    }


}
