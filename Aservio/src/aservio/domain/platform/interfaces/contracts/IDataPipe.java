package aservio.domain.platform.interfaces.contracts;

import aservio.domain.management.activities.Activity;

import java.util.UUID;

public interface IDataPipe {
    String verifyUser(String username, String password);
    String getUser(String username, String password);
    String[] getUserActivities(UUID userid);
    String[] getUserInfo(UUID userid);
    String[] getUsers(UUID activityid);
    String[] getActivity(UUID activityid);
    boolean addUser(String username, String password, String userid, String mail, String firstname, String lastname, int phone, String picture, String instituionname);
}
