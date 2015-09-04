package envers.entities;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

@Entity
@Audited
@Access(AccessType.FIELD)
@Table(name="ENV_PERSON")
public class EnvPerson {

    @Id
    @GeneratedValue
    private long id;
    private String vorname;
    private String name;
    private String adresse;

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

    @Override
    public String toString() {
        return "EnvPerson [id=" + id + ", vorname=" + vorname + ", name=" + name + ", adresse=" + adresse + "]";
    }

        
}
