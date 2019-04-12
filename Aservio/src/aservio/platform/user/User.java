package aservio.platform.user;

import aservio.platform.user.roles.Role;

public class User {
    
    private boolean usesDefaultPermissions;
    
    private String username;
    private String password;
    private Role role;
    
    public User (String username, String password, Role preselectedRole){
        this.username = username;
        this.password = password;
        this.role = preselectedRole;
        
        //Generate ID
    }
    
    public Role getRole (){
        return role;
    }
    
    
}
