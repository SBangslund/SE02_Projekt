package aservio.domain.platform.user.roles;

import aservio.domain.platform.user.Permissions;

public class Relative extends Role {

    public Relative() {
        createDefaultPermissions();
    }

    @Override
    protected void createDefaultPermissions() {
        Permissions permissions = new Permissions();
        permissions.setSeeUserList(false);
        permissions.setCanAddUser(false);

        super.defaultPermissions = permissions;
    }

    @Override
    public String toString() {
        return "Relative";
    }
}
