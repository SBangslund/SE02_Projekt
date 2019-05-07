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
    boolean addUser(String username, String password, UUID userid);
    boolean addUserInfo(String mail, String firstname, String lastname, int phone, String picture, UUID userid, String institutionName);
    boolean addUserAddress(String roadname, String country, int postcode, String city, String housenumber, String level, UUID userid);
}
