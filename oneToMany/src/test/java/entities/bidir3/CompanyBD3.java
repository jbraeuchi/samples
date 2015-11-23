package entities.bidir3;

import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Multiple OneToMany with bidirectional with JoinTable
 */
@Entity
@Audited
@Table(name = "O2M_CompanyBD3")
public class CompanyBD3 {

    @Id
    @GeneratedValue
    private long id;

    @Version
    private long version;

    @Column(name = "Name")
    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name="O2M_CompanyBD3_Emp")
    private Set<EmployeeBD3> employees = new HashSet<EmployeeBD3>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name="O2M_CompanyBD3_Mgr")
    private Set<EmployeeBD3> managers = new HashSet<EmployeeBD3>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public Set<EmployeeBD3> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<EmployeeBD3> employees) {
        this.employees = employees;
    }

    public Set<EmployeeBD3> getManagers() {
        return managers;
    }

    public void setManagers(Set<EmployeeBD3> managers) {
        this.managers = managers;
    }
}
