package aservio.presentation.platform.interfaces;

import aservio.domain.platform.user.Permissions;
import aservio.domain.platform.user.User;
import aservio.domain.platform.user.roles.Role;

public interface PermissionLimited {
    Permissions DEFAULT_PERMISSIONS = User.getCurrentUser().getUserInfo().getRole().getDefaultPermissions();

    /**
     * Sets the limitations for the user based on their {@link Role}
     */
    void applyPermissionLimitations();
}
