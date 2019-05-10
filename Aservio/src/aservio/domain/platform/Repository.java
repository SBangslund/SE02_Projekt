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

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Repository {

    private final IRepository interFace = DomainInterfaceManager.getIDataPipe();

    public String verifyUser(String username, String password) {
        return interFace.verifyUser(username, password);
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
                userInfo.getInstitution()
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

    public boolean addUserNote(Note note) {
        return interFace.addUserNote(
                note.getId(),
                note.getDate(),
                note.getStartDate().toString(),
                note.getEndDate().toString(),
                note.getNoteText().toString()
        );
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
            Address userAddress = getUserAddress(userId);

            userInfo = new UserInfo(userAddress, null, phone, firstname, lastname, mail, institutionid, userId);
        } else {
            System.err.println("[DATA_ERROR](DatePipe.getUserInfo()): String[].length != 7.");
        }
        return userInfo;
    }

    public User getUser(String username, String password) {
        UUID userID = UUID.fromString(interFace.getUser(username, password));
        User user = new User(username, password, userID, null /*User role not implemented*/, getUserInfo(userID));
        System.err.println("OBS: Role not implemented in repository/getUser");
        return user;
    }

    public List<UserInfo> getUsersFromInstitution(int institutionid) {
        String[] usersString = interFace.getUsersFromInsitution(institutionid);
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
        //TODO convert String[] to list of users.
        return null;
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

    public Activity getActivity(UUID activityid) {
        String[] userActivity = interFace.getActivity(activityid);
        Activity activity = null;
        if (userActivity != null) {
            String name = userActivity[0],
                    type = userActivity[1],
                    startTime = userActivity[3],
                    endTime = userActivity[4];
            java.sql.Date date = Date.valueOf(userActivity[2]);

            //startTime -> java.util.Date
            java.util.Date startDate = null;
            try {
                startDate = new SimpleDateFormat("hh:mm").parse(startTime);
            } catch (ParseException e) {
                System.err.println("[DATA_ERROR](DatePipe.getActivity()):ID=" + activityid + ": startDate has a wrong format.");
            }
            //endTime -> java.util.Date
            java.util.Date endDate = null;
            try {
                endDate = new SimpleDateFormat("hh:mm").parse(endTime);
            } catch (ParseException e) {
                System.err.println("[DATA_ERROR](DatePipe.getActivity()):ID=" + activityid + ": endDate has a wrong format.");
            }
            //date -> java.util.Date
            java.util.Date currentDate = new java.util.Date(date.getTime());

            activity = new Activity(ActivityType.valueOf(type), startDate, endDate, activityid);
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

    public NoteList getNoteList(UUID userID) {
        return null;
    }

}
