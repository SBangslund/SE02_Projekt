package aservio.management.activities;

import aservio.platform.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityManager {
    private Map<User, ActivityList> userActivities = new HashMap<>();

    public void add(Activity activity, List<User> users) {
        // TODO add the activity to all the users ActivityLists
    }
}