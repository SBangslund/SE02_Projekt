package aservio.domain.platform.interfaces.contracts;

import java.util.UUID;

public interface IDataPipe {
    String verifyUser(String username, String password);
    String getUser(String username, String password);
    String[] getUserActivities(UUID userid);
    String[] getUserInfo(UUID userid);
    String[] getUsers(UUID activityid);
    String[] getActivity(UUID activityid);
    String[] getUserAddress(UUID userid);
    String[] getInstitution(UUID institutionid);
    boolean addUser(String username, String password, String userid, String mail, String firstname, String lastname, int phone, String picture, String instituionname);
}
