package aservio.domain.platform.user.roles;

import aservio.domain.platform.user.Permissions;
import java.io.Serializable;

public abstract class Role implements Serializable {

    protected Permissions defaultPermissions;
    
    /**
     * @return Returns the default DEFAULT_PERMISSIONS of a role (FX citizen or relative)
     */
    public Permissions getDefaultPermissions(){
        return defaultPermissions;
    }
    
    /**
     * Sets the default values for a role when a user is created. Can also be used to reset values to default values.
     */
    protected abstract void createDefaultPermissions();
}
