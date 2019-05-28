package aservio.domain.platform;

import aservio.domain.DomainInterfaceManager;
import aservio.domain.journal.Note;
import aservio.domain.journal.NoteList;
import aservio.domain.management.activities.Activity;
import aservio.domain.management.activities.ActivityList;
import aservio.domain.management.activities.ActivityType;
import aservio.domain.platform.interfaces.contracts.IRepository;
import aservio.domain.platform.user.Address;
import aservio.domain.platform.user.User;
import aservio.domain.platform.user.UserInfo;
import aservio.domain.platform.user.roles.Admin;
import aservio.domain.platform.user.roles.Caretaker;
import aservio.domain.platform.user.roles.Citizen;
import aservio.domain.platform.user.roles.Role;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Repository {

    private final IRepository interFace = DomainInterfaceManager.getIRepository();

    public String verifyUser(String username, String password) {
        return interFace.verifyUser(username, password);
    }

    public boolean deleteNote(Note note){
        return interFace.deleteNote(note.getId());
    }

    public boolean addUser(User user) {
        return interFace.addUser(
                user.getUsername(),
                user.getPassword(),
                user.getId()
        );
    }

    public boolean addUserInfo(UserInfo userInfo) {
        return interFace.addUserInfo(
                userInfo.getMail(),
                userInfo.getFirstName(),
                userInfo.getLastName(),
                userInfo.getMobileNumber(),
                null,
                userInfo.getId(),
                userInfo.getInstitution(),
                userInfo.getRole().toString()
        );
    }

    public boolean addUserAddress(Address address) {
        return interFace.addUserAddress(
                address.getRoad(),
                address.getCountry(),
                address.getPostcode(),
                address.getCity(),
                address.getHouseNumber(),
                address.getLevel(),
                address.getUserId()
        );
    }
    
    /**
     * Gathers all the information which is needed to create a note. 
     * If everything is as it should be it will return true
     * @param note
     * @return 
     */
    public boolean addUserNote(Note note) {
        addNoteToUser(note, note.getCitizenInfo().getId());
        addNoteToUser(note, User.getCurrentUser().getId());
        return interFace.addUserNote(
                note.getNoteText(),
                note.getDate().getTime(),
                note.getId()
        );
    }
    /**
     * Connects the note to the user
     * @param note
     * @param userid
     * @return true if connection is successfull
     */
    public boolean addNoteToUser(Note note, UUID userid) {
        return interFace.addNoteToUser(userid, note.getId());
    }
    
    public UserInfo getUserInfo(UUID userId) {
        String[] userInfoStrings = interFace.getUserInfo(userId);
        UserInfo userInfo = null;
        if (userInfoStrings != null && userInfoStrings.length == 7) {
            String mail = userInfoStrings[0],
                    firstname = userInfoStrings[1],
                    lastname = userInfoStrings[2],
                    picture = "null";
            int phone = Integer.parseInt(userInfoStrings[3]);
            int institutionid = Integer.valueOf(userInfoStrings[6]);
            Role role = getUserRole(userId);
            Address userAddress = getUserAddress(userId);

            userInfo = new UserInfo(userAddress, null, phone, firstname, lastname, mail, role, institutionid, userId);
        } else {
            System.err.println("[DATA_ERROR](DatePipe.getUserInfo()): String[].length != 7.");
        }
        return userInfo;
    }

    public User getUser(String username, String password) {
        UUID userID = UUID.fromString(interFace.getUser(username, password));
        User user = new User(username, password, userID, getUserInfo(userID));
        System.err.println("OBS: Role not implemented in repository/getUser");
        return user;
    }

    public Role getUserRole(UUID userid) {
        String roleString = interFace.getUserRole(userid.toString());
        switch (roleString) {
            case "Caretaker":
                return new Caretaker();
            case "Citizen":
                return new Citizen();
            case "SysAdmin":
                return new Admin();
            case "Relative":
                return new Admin();
            default:
                return null;
        }
    }

    public List<UserInfo> getCitizensFromCaretaker(UUID caretakerID) {
        String[] citizens = interFace.getCitizensFromCaretaker(caretakerID.toString());
        List<UserInfo> citizenList = new ArrayList<>();
        if (citizens != null) {
            for (String s : citizens) {
                citizenList.add(getUserInfo(UUID.fromString(s)));
            }
        }
        return citizenList;
    }

    public List<UserInfo> getUsersFromInstitution(int institutionID) {
        String[] usersString = interFace.getUsersFromInstitution(institutionID);
        List<UserInfo> users = null;
        if (usersString != null) {
            users = new ArrayList<>();
            for (String userid : usersString) {
                users.add(getUserInfo(UUID.fromString(userid)));
            }
        }
        return users;
    }

    public List<UserInfo> getUsersFromActivity(Activity activity) {
        String[] usersString = interFace.getUsersFromActivity(activity.getId());
        List<UserInfo> userinformations = new ArrayList<>();
        for (String userID : usersString) {
            userinformations.add(getUserInfo(UUID.fromString(userID)));
        }
        //TODO convert String[] to list of users.
        return userinformations;
    }

    public ActivityList getUserActivities(UUID userId) {
        String[] userActivityStrings = interFace.getUserActivities(userId);
        ActivityList activityList = new ActivityList();
        if (userActivityStrings != null) {
            for (int i = 0; i < userActivityStrings.length; i++) {
                Activity activity = getActivity(UUID.fromString(userActivityStrings[i]));
                activityList.add(activity);
            }
        }
        return activityList;
    }

    public boolean deleteActivity(UUID activityid) {
        return interFace.deleteActivity(activityid);
    }

    /**
     * Creates an activity, and adds users to it. If successful returns true.
     *
     * @param activity
     * @param userid
     * @return
     */
    public boolean addActivity(Activity activity, UUID userid) {

        return interFace.addActivity(
                activity.getActivityName(),
                activity.getActivityType().toString(),
                activity.getStartDate(),
                activity.getEndDate(),
                activity.getId(),
                activity.getDescription())
                && interFace.addUserToActivity(activity.getId(), userid);
    }

    public Activity getActivity(UUID activityid) {
        String[] userActivity = interFace.getActivity(activityid);
        Activity activity = null;
        if (userActivity != null) {
            String name = userActivity[0];
            String type = userActivity[1];
            Long startTime = Long.parseLong(userActivity[2]);
            Long endTime = Long.parseLong(userActivity[3]);

            java.util.Date startDate = new java.util.Date(startTime);
            java.util.Date endDate = new java.util.Date(endTime);
            String description = userActivity[5];

            activity = new Activity(name, ActivityType.valueOf(type), startDate, endDate, activityid, description);
        }
        return activity;
    }

    public Address getUserAddress(UUID userid) {
        String[] userAddress = interFace.getUserAddress(userid);
        Address address = null;
        if (userAddress != null) {
            if (userAddress.length == 7) {
                String roadname = userAddress[0],
                        country = userAddress[1],
                        city = userAddress[3],
                        houseNumber = userAddress[4],
                        level = userAddress[5];
                int postcode = userAddress[2] != null ? Integer.parseInt(userAddress[2]) : 0;
                address = new Address(roadname, country, postcode, city, houseNumber, level, userid);
            } else {
                System.err.println("[DATA_ERROR](DatePipe.getUserAddress()): String[].length != 7.");
            }
        } else {
            System.err.println("[DATA](DataPipe.getUserAddress()): No results.");
        }
        return address;
    }

    public String getInstitutionName(int institutionid) {
        String[] institutionString = interFace.getInstitution(institutionid);
        String name = null;
        if (institutionString != null) {
            name = institutionString[0];
        }
        return name;
    }
    
    /**
     * Fetches the notes and adds it to a NoteList
     * so the notelist can be shown in the presentation layer. 
     * @param userID
     * @return 
     */
    public NoteList getNoteList(UUID userID) {
        String[] userNotesStrings = interFace.getNotesFromUser(userID);
        NoteList noteList = null;
        if (userNotesStrings != null) {
            noteList = new NoteList();
            for (int i = 0; i < userNotesStrings.length; i++) {
                Note note = getNote(UUID.fromString(userNotesStrings[i]), getUserInfo(userID));
                if (note != null) {
                    noteList.add(note);
                }
            }
        }
        return noteList;
    }

    /**
     * Fetches a note from the database by noteid and splits the notetext
     * So it fits the requirements to create a note object
     * @param noteid
     * @param citizenInfo
     * @return 
     */
    private Note getNote(UUID noteid, UserInfo citizenInfo) {

        String[] userNotes = interFace.getNote(noteid);
        Note note = null;
        if (userNotes != null) {
            String noteInfo = userNotes[0];
            String[] noteInfoSplitted = noteInfo.trim().split("\\+");
            Date date = new Date(Long.valueOf(userNotes[1]));
            String title = noteInfoSplitted[0].toString();
            String noteText = noteInfoSplitted[2].toString();
            String caretakerName = noteInfoSplitted[3].toString();

            note = new Note(noteid, date, noteText, citizenInfo, title, caretakerName);
        }
        return note;
    }

}
