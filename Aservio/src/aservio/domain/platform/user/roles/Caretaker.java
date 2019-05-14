package aservio.domain.platform.user.roles;

import aservio.domain.platform.user.Permissions;

public class Caretaker extends Role {

    public Caretaker() {

        createDefaultPermissions();
    }

    @Override
    protected void createDefaultPermissions() {
        Permissions permissions = new Permissions();
        permissions.setSeeUserList(true);

        super.defaultPermissions = permissions;
    }

    @Override
    public String toString() {
        return "Caretaker";
    }
}
