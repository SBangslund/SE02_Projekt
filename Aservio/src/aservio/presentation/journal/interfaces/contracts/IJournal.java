package aservio.presentation.journal.interfaces.contracts;

import aservio.domain.journal.NoteList;
import aservio.domain.platform.user.UserInfo;

/**
 *
 * @author Rene_
 */
public interface IJournal {
/**
 * Gets the note list based on the userinfo
 * @param userInfo
 * @return 
 */
 NoteList getNoteList(UserInfo userInfo);   
}
