/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aservio.domain.journal.interfaces.implementors;

import aservio.domain.journal.Note;
import aservio.domain.journal.NoteList;
import aservio.domain.platform.Repository;
import aservio.domain.platform.user.UserInfo;
import aservio.presentation.journal.interfaces.contracts.IJournalOverview;
import java.util.List;

/**
 *
 * @author victo
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
    public List<UserInfo> getUsersFromInstitution(int institutionid) {
        return repository.getUsersFromInstitution(institutionid);
    }

    @Override
    public NoteList getNoteList(UserInfo userInfo) {
        return repository.getNoteList(userInfo.getId());
    }

}
