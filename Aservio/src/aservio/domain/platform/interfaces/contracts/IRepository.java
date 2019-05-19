package aservio.domain.platform.interfaces.contracts;

import java.util.Date;
import java.util.UUID;

public interface IRepository {
    boolean addUser(String username, String password, UUID userID);
    boolean addUserInfo(String mail, String firstname, String lastname, int phone, String picture, UUID userID, int institutionid, String role);
    boolean addUserAddress(String roadname, String country, int postcode, String city, String housenumber, String level, UUID userid);
    String verifyUser(String username, String password);
    String getUser(String username, String password);
    String[] getUserInfo(UUID userID);
    String[] getUserAddress(UUID userID);
    String getUserRole(String userID);
    String[] getCitizensFromCaretaker(String caretakerID);
    /**
     * Brilliaaaaaaaaant
     * @param userID the userID of the user that no longer exists
     * @return
     */
    boolean deleteUser(UUID userID);

    boolean addUserNote(String noteInfo, Long noteDate, UUID noteid);
    boolean addNoteToUser(UUID userid, UUID noteid);
    String[] getNotesFromUser(UUID userid);
    String[] getNote(UUID noteid);
    boolean deleteNote(UUID noteID);

    boolean addActivity(String name, String type, Date starttime, Date endtime, UUID activityid, String description);
    boolean addUserToActivity(UUID activityid, UUID userID);
    String[] getActivity(UUID activityid);
    String[] getUserActivities(UUID userID);
    String[] getUsersFromActivity(UUID activityid);
    boolean deleteActivity(UUID activityid);

    boolean addInstitution(String institutionName, int institutionid);
    String[] getInstitution(int institutionid);
    String[] getUsersFromInstitution(int institution);
    boolean deleteInstitution(int institutionid);




}
