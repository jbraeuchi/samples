package entities.entitygraph;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * OneToMany with JoinColumn unidirectional
 */
@Entity
@Table(name = "O2M_CompanyEG")
@NamedEntityGraph(
        name = "Company.employees",
        attributeNodes = @NamedAttributeNode("employees"))
public class CompanyEG {

    @Id
    @GeneratedValue
    private long id;

    @Version
    private long version;

    @Column(name = "Name")
    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "Id_Company")
    private Set<EmployeeEG> employees = new HashSet<EmployeeEG>();

    @OneToOne(cascade = CascadeType.ALL)
    private EmployeeEG manager;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public Set<EmployeeEG> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<EmployeeEG> employees) {
        this.employees = employees;
    }

    public EmployeeEG getManager() {
        return manager;
    }

    public void setManager(EmployeeEG manager) {
        this.manager = manager;
    }

}
