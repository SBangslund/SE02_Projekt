package aservio.platform.user.roles;

import aservio.platform.user.Permissions;

public class Admin extends Role {

    public Admin() {
        createDefaultPermissions();
    }

    @Override
    protected void createDefaultPermissions() {
        Permissions permissions = new Permissions();
        permissions.setRedigerPlanlægning(false);
        permissions.setSePlanlægning(true);
        permissions.setTest1(true);

        super.defaultPermissions = permissions;
    }
}
