package aservio.domain.platform.user.roles;

import aservio.domain.platform.user.Permissions;

public class Admin extends Role {

    public Admin() {
        createDefaultPermissions();
    }

    @Override
    protected void createDefaultPermissions() {
        Permissions permissions = new Permissions();
        permissions.setSeeUserList(true);
        permissions.setCanEditActivities(true);
        permissions.setCanAddUser(true);

        permissions.setSeeCreateNote(true);
        permissions.setSeeModifyNote(true);
        super.defaultPermissions = permissions;
    }

    @Override
    public String toString() {
        return "Admin";
    }
}
