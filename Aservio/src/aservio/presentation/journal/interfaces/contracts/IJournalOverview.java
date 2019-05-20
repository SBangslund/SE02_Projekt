package aservio.presentation.journal.interfaces.contracts;

import aservio.domain.journal.Note;
import aservio.domain.journal.NoteList;
import aservio.domain.platform.user.UserInfo;
import java.util.List;

/**
 *
 * @author victo
 */
public interface IJournalOverview {

    /**
     * Ensures that classes who implements IJournalOverview must handel how to add notes
     * @param note 
     */
    void addNote(Note note);
    
    /**
     * Fetches a user from a given institutionid
     * @param institutionid
     * @return 
     */
    List<UserInfo> getUsersFromInstitution(int institutionid);

}
