/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aservio.domain.journal.interfaces.implementors;

import aservio.domain.journal.NoteList;
import aservio.domain.platform.Repository;
import aservio.domain.platform.user.UserInfo;
import aservio.presentation.journal.interfaces.contracts.IJournal;

/**
 *
 * @author Rene_
 */
public class IJournalImp implements IJournal {

    private Repository repository;

    public IJournalImp(Repository repository) {
        this.repository = repository;
    }

    @Override
    public NoteList getNoteList(UserInfo userInfo) {
        return repository.getNoteList(userInfo.getId());
    }

}
