package aservio.platform.user;

import aservio.platform.user.roles.Role;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User implements Serializable {

    private boolean usesDefaultPermissions;

    private UUID id;
    private String username;
    private String password;
    private List<Role> roles = new ArrayList<>();
    private static User currentUser;

    /**
     * upon creation of a user, the username, password and role is constructed.
     *
     * @param username
     * @param password
     * @param preselectedRole
     */
    
    //Without a role
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        id = UUID.randomUUID();
        //Create ID
    }

    public User(String username, String password, Role preselectedRole) {
        this.username = username;
        this.password = password;
        this.roles.add(preselectedRole);
        id = UUID.randomUUID();
        //Create ID
    }

    public List<Role> getRole() {
        return roles;
    }

    /**
     * @return the currentUser
     */
    public static User getCurrentUser() {
        return currentUser;
    }

    /**
     * @param user the currentUser to set
     */
    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

}
