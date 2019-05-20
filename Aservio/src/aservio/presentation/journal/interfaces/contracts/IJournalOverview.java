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

    NoteList getNoteList(UserInfo userInfo);

    void addNote(Note note);

    List<UserInfo> getUsersFromInstitution(int institutionid);

}
