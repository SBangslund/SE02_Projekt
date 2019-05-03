package aservio.domain.platform.user.roles;

import aservio.domain.platform.user.Permissions;

public class Caretaker extends Role {

    public Caretaker() {

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
