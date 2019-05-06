package aservio.domain.platform;

import aservio.domain.DomainInterfaceManager;
import aservio.domain.management.activities.Activity;
import aservio.domain.management.activities.ActivityList;
import aservio.domain.platform.interfaces.contracts.IDataPipe;
import aservio.domain.platform.user.User;
import aservio.domain.platform.user.UserInfo;

import java.util.List;
import java.util.UUID;

public class DataPipe {
    private IDataPipe interFace = DomainInterfaceManager.getIDataPipe();

    public String verifyUser(String username, String password) {
        String verificationMessage = interFace.verifyUser(username, password);
        //TODO convert message to valid user message
        return null;
    }

    public UserInfo getUserInfo(UUID userId) {
        String[] userInfoStrings = interFace.getUserInfo(userId);
        //TODO convert String[] to UserInfo
        return null;
    }

    public User getUser(String username, String password) {
        String userString = interFace.getUser(username, password);
        //TODO convert String[] to User
        return null;
    }

    public List<User> getUsers(Activity activity) {
        String[] usersString = interFace.getUsers(activity.getId());
        //TODO convert String[] to list of users.
        return null;
    }

    public ActivityList getUserActivities(UUID userId) {
        String[] userActivityStrings = interFace.getUserActivities(userId);
        //TODO convert String[] to ActivityList
        return null;
    }
}
