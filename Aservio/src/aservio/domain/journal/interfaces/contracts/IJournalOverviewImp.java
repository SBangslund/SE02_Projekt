/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aservio.domain.journal.interfaces.contracts;

import aservio.domain.journal.Note;
import aservio.domain.journal.NoteList;
import aservio.domain.platform.Repository;
import aservio.domain.platform.user.UserInfo;
import aservio.presentation.journal.interfaces.contracts.IJournalOverview;

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
    public NoteList getNoteList(UserInfo userInfo) {
//        return repository.getNotes()
        return null;
    }

    @Override
    public void addNote(Note note) {
        repository.addUserNote(note);
    }

}
