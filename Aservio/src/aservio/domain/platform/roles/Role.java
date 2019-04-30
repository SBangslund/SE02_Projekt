package aservio.domain.platform.roles;

import aservio.domain.platform.user.Permissions;

public abstract class Role {

    protected Permissions defaultPermissions;
    
    /**
     * @return Returns the default permissions of a role (FX citizen or relative)
     */
    public Permissions getDefaultPermissions(){
        return defaultPermissions;
    }
    
    /**
     * Sets the default values for a role when a user is created. Can also be used to reset values to default values.
     */
    protected abstract void createDefaultPermissions();
}
