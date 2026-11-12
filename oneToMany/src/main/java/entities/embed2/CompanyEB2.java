package entities.embed2;

import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by jakob on 23.11.2015.
 */
@Entity
@Audited
@Table(name = "O2M_CompanyEB2")
public class CompanyEB2 {

    @Id
    @GeneratedValue
    private long id;

    @Version
    private long version;

    @Column(name = "Name")
    private String name;

    @ElementCollection
    @CollectionTable(name = "O2M_CompanyEmployees_All")
    Set<EmployeeEB2> allEmployees = new HashSet<>();

    @Transient
    Set<EmployeeEB2> employees = new HashSet<>();

    @Transient
    Set<EmployeeEB2> managers = new HashSet<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public Set<EmployeeEB2> getEmployees() {
        return employees;
    }

    public Set<EmployeeEB2> getManagers() {
        return managers;
    }

    @PrePersist
    @PreUpdate
    @PreRemove
    private void fillAll() {
        System.out.println("filling allEmployees");
        allEmployees.clear();

        allEmployees.addAll(employees);
        allEmployees.addAll(managers);
    }

    @PostLoad
    private void fillSets() {
        System.out.println("filling sets");

        employees.clear();
        managers.clear();

        for (EmployeeEB2 employee : allEmployees) {
            if (employee.getName().startsWith("Emp")) {
                employees.add(employee);
            }
            if (employee.getName().startsWith("Man")) {
                managers.add(employee);
            }
        }

    }
}
