package aservio.domain.platform.user;

import aservio.domain.management.activities.ActivityList;
import aservio.domain.platform.user.roles.Role;

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
    private ActivityList activityList = new ActivityList();
    private UserInfo userInfo;

    /**
     * upon creation of a user, the username, password and role is constructed.
     *
     * @param username
     * @param password
     * @param preselectedRole
     * @param userInfo
     */
    public User(String username, String password, UUID userId, Role preselectedRole, UserInfo userInfo) {
        this.username = username;
        this.password = password;
        this.id = userId;
        this.userInfo = userInfo;
        this.roles.add(preselectedRole);
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

    public ActivityList getActivityList() {
        return activityList;
    }

    /**
     * @param user the currentUser to set
     */
    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public UserInfo getUserInfo() {
        return userInfo;
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
