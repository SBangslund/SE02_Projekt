package aservio.domain.journal.interfaces.implementors;

import aservio.domain.journal.Note;
import aservio.domain.journal.NoteList;
import aservio.domain.platform.Repository;
import aservio.domain.platform.user.UserInfo;
import aservio.presentation.journal.interfaces.contracts.IJournalOverview;
import java.util.List;

/**
 * Related to {@link IJournalImp}, used to access {@link Repository} and thereby the Database
 */
public class IJournalOverviewImp implements IJournalOverview {

    private Repository repository;

    public IJournalOverviewImp(Repository repository) {
        this.repository = repository;
    }

    @Override
    public void addNote(Note note) {
        repository.addUserNote(note);
    }

    @Override
    public void deleteNote(Note note) {
        repository.deleteNote(note);
    }

    @Override
    public List<UserInfo> getUsersFromInstitution(int institutionid) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
