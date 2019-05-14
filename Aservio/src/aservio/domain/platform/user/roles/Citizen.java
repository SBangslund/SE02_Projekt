package aservio.domain.platform.user.roles;

import aservio.domain.platform.user.Permissions;

public class Citizen extends Role {

    

    public Citizen() {
        createDefaultPermissions();
    }

    @Override
    protected void createDefaultPermissions() {
        Permissions permissions = new Permissions();
        permissions.setSeeUserList(false);
        permissions.setSeeCreateNote(false);
        permissions.setSeeModifyNote(false);
        super.defaultPermissions = permissions;
    }

    @Override
    public String toString() {
        return "Citizen";
    }
}
