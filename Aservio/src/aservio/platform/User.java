package aservio.platform;

import aservio.management.activities.ActivityList;

public class User {
    ActivityList activityList = new ActivityList();

    public User(ActivityList activityList) {
        this.activityList = activityList;
    }

    public ActivityList getActivityList() {
        return activityList;
    }
}
