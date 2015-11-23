package entities.bidir2;

import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * OneToMany with mappedBy bidirectiional
 */
@Entity
@Audited
@Table(name = "O2M_CompanyBD2")
public class CompanyBD2 {

    @Id
    @GeneratedValue
    private long id;

    @Version
    private long version;

    @Column(name = "Name")
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "companyEmployee")
    private Set<EmployeeBD2> employees = new HashSet<EmployeeBD2>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "companyManager")
    private Set<EmployeeBD2> managers = new HashSet<EmployeeBD2>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public Set<EmployeeBD2> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<EmployeeBD2> employees) {
        this.employees = employees;
    }

    public Set<EmployeeBD2> getManagers() {
        return managers;
    }

    public void setManagers(Set<EmployeeBD2> managers) {
        this.managers = managers;
    }
}
