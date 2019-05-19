package aservio.domain.platform.user;

import java.io.Serializable;


public class Permissions implements Serializable {

    private boolean seeUserList;
    private boolean canAddUser;
    private boolean redigerPlanlægning;
    private boolean canEditActivities;
    private boolean seeCreateNote;
    private boolean seeModifyNote;

    public boolean canSeeUserList() {
        return seeUserList;
    }
    
    public boolean canCreateNote() {
        return seeCreateNote;
    }

    public boolean canAddUser() {
        return canAddUser;
    }

    public boolean isRedigerPlanlægning() {
        return redigerPlanlægning;
    }
    
    public boolean canModifyNote(){
        return seeModifyNote;
    }
    
    public void setSeeUserList(boolean seeUserList) {
        this.seeUserList = seeUserList;
    }
    public boolean canEditActivities() {
        return canEditActivities;
    }
    public void setCanEditActivities(boolean canEditActivities){
        this.canEditActivities = canEditActivities;
    }

    public void setCanAddUser(boolean canAddUser) {
        this.canAddUser = canAddUser;
    }
    

    public void setSeeCreateNote(boolean seeCreateNote) {
        this.seeCreateNote = seeCreateNote;
    }
    
    public void setSeeModifyNote(boolean seeModifyNote){
        this.seeModifyNote = seeModifyNote;
    }
    
}
