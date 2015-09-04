package envers.audit;

import java.util.UUID;

import org.hibernate.envers.RevisionListener;

/**
 * The Listener to populate createdBy
 * 
 * @author jakob
 */
public class RevisionInfoListener implements RevisionListener {

    public void newRevision(Object revisionEntity) {
        RevisionInfo revInfo = (RevisionInfo) revisionEntity;
        revInfo.setCreatedBy("Mr. Ian Fleming");
        revInfo.setCreatedCallcontexId(UUID.randomUUID());
    }

}
