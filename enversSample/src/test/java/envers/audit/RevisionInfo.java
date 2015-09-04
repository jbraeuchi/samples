package envers.audit;

import java.util.Set;
import java.util.UUID;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;

@Entity
@RevisionEntity
//@RevisionEntity(RevisionInfoListener.class)
// or set org.hibernate.envers.revision_listener
@Table(name = "ENV_REVINFO")
public class RevisionInfo extends DefaultRevisionEntity {
    private String createdBy;

    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID createdCallcontexId;

    public String getCreatedBy() {
        return createdBy;
    }

    // Additional Property to Track all canged Entities
    // org.hibernate.envers.global_with_modified_flag
    @ElementCollection
    @CollectionTable(name="ENV_REVINFO_ENTITIES")
    private Set<String> modifiedEntityNames;
    
    public Set<String> getModifiedEntityNames() {
        return modifiedEntityNames;
    }
    
    public void setModifiedEntityNames(Set<String> modifiedEntityNames) {
        this.modifiedEntityNames = modifiedEntityNames;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public UUID getCreatedCallcontexId() {
        return createdCallcontexId;
    }

    public void setCreatedCallcontexId(UUID createdCallcontexId) {
        this.createdCallcontexId = createdCallcontexId;
    }





}
