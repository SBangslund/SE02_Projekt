package aservio.presentation.journal.interfaces.contracts;

import aservio.domain.journal.NoteList;
import aservio.domain.platform.user.UserInfo;

/**
 *
 * @author Rene_
 */
public interface IJournal {
 NoteList getNoteList(UserInfo userInfo);   
}
