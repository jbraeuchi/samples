package entities.embed;

import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by jakob on 23.11.2015.
 */
@Entity
@Audited
@Table(name = "O2M_CompanyEB")
public class CompanyEB {

    @Id
    @GeneratedValue
    private long id;

    @Version
    private long version;

    @Column(name = "Name")
    private String name;

    @ElementCollection
    @CollectionTable(name = "O2M_CompanyEmployees_1")
    Set<EmployeeEB> employees = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "O2M_CompanyEmployees_2")
    Set<EmployeeEB> managers = new HashSet<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public Set<EmployeeEB> getEmployees() {
        return employees;
    }

    public Set<EmployeeEB> getManagers() {
        return managers;
    }
}
