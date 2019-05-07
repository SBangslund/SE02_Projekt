package aservio.domain.platform;

import aservio.domain.DomainInterfaceManager;
import aservio.domain.management.activities.Activity;
import aservio.domain.management.activities.ActivityList;
import aservio.domain.management.activities.ActivityType;
import aservio.domain.platform.interfaces.contracts.IDataPipe;
import aservio.domain.platform.user.Address;
import aservio.domain.platform.user.User;
import aservio.domain.platform.user.UserInfo;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

public class DataPipe {
    private IDataPipe interFace = DomainInterfaceManager.getIDataPipe();

    public String verifyUser(String username, String password) {
        return interFace.verifyUser(username, password);
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
            UUID institutionid = UUID.fromString(userInfoStrings[6]);
            Address userAddress = getUserAddress(userId);
            String institutionName = getInstitutionName(institutionid);

            userInfo = new UserInfo(userAddress, null, phone, firstname, lastname, mail, institutionName);
        } else {
            System.err.println("[DATA_ERROR](DatePipe.getUserInfo()): String[].length != 7.");
        }
        return userInfo;
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
        ActivityList activityList = new ActivityList();
        if (userActivityStrings != null)
            for (int i = 0; i < userActivityStrings.length; i++) {
                Activity activity = getActivity(UUID.fromString(userActivityStrings[i]));
                activityList.add(activity);
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
        if (userAddress != null && userAddress.length == 7) {
            String roadname = userAddress[0],
                    country = userAddress[1],
                    city = userAddress[3],
                    houseNumber = userAddress[4],
                    level = userAddress[5];
            int postcode = Integer.parseInt(userAddress[2]);
            address = new Address(roadname, country, postcode, city, houseNumber, level);
        } else {
            System.err.println("[DATA_ERROR](DatePipe.getUserAddress()): String[].length != 7.");
        }
        return address;
    }

    public String getInstitutionName(UUID institutionId) {
        String[] institutionString = interFace.getInstitution(institutionId);
        String name = null;
        if (institutionString != null) {
            name = institutionString[0];
        }
        return name;
    }
}
