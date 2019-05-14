package aservio.domain.platform.user;

import java.io.Serializable;


public class Permissions implements Serializable {
    
    private boolean seeUserList;
    private boolean seeCreateNote;
    private boolean seeModifyNote;

    public boolean canSeeUserList() {
        return seeUserList;
    }
    
    public boolean canCreateNote() {
        return seeCreateNote;
    }
    
    public boolean canModifyNote(){
        return seeModifyNote;
    }
    
    public void setSeeUserList(boolean seeUserList) {
        this.seeUserList = seeUserList;
    }
    

    public void setSeeCreateNote(boolean seeCreateNote) {
        this.seeCreateNote = seeCreateNote;
    }
    
    public void setSeeModifyNote(boolean seeModifyNote){
        this.seeModifyNote = seeModifyNote;
    }
    
}
