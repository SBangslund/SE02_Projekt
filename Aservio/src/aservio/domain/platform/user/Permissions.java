package aservio.domain.platform.user;

import java.io.Serializable;


public class Permissions implements Serializable {

    private boolean seeUserList;
    private boolean canAddUser;
    private boolean redigerPlanlægning;
    private boolean canEditActivities;

    public boolean canSeeUserList() {
        return seeUserList;
    }

    public boolean canAddUser() {
        return canAddUser;
    }

    public boolean isRedigerPlanlægning() {
        return redigerPlanlægning;
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
}
