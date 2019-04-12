package aservio.platform.user.roles;

import aservio.platform.user.Permissions;

public abstract class Role {

    protected Permissions defaultPermissions;
    
    public Permissions getDefaultPermissions(){
        return defaultPermissions;
    }
    
    protected abstract void createDefaultPermissions();
}
