package aservio.domain.platform.user;

import aservio.domain.journal.NoteList;
import aservio.domain.management.activities.ActivityList;
import java.io.Serializable;
import java.util.UUID;

public class User implements Serializable {

    private boolean usesDefaultPermissions;

    private UUID id;
    private String username;
    private String password;
    private static User currentUser;
    private ActivityList activityList = new ActivityList();
    private UserInfo userInfo;
    private NoteList noteList = new NoteList();

    /**
     * upon creation of a user, the username, password and role is constructed.
     *
     * @param username
     * @param password
     * @param userId
     * @param userInfo
     */
    public User(String username, String password, UUID userId, UserInfo userInfo) {
        this.username = username;
        this.password = password;
        this.id = userId;
        this.userInfo = userInfo;
        //Create ID
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

    public NoteList getNoteList() {
        return noteList;
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

    public UUID getId() {
        return id;
    }
}
