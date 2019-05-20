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
    /**
     * Adds a note based on noteinfo, noteDate and a noteid
     * @param noteInfo
     * @param noteDate
     * @param noteid
     * @return 
     */
    boolean addUserNote(String noteInfo, Long noteDate, UUID noteid);
    
    /**
     * adds a note to a user so they are connected
     * @param userid
     * @param noteid
     * @return 
     */
    boolean addNoteToUser(UUID userid, UUID noteid);
    
    /**
     * Fetches the note from a use by the user id
     * @param userid
     * @return 
     */
    String[] getNotesFromUser(UUID userid);
    
    /**
     * Gets a note by the noteid
     * @param noteid
     * @return 
     */
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
