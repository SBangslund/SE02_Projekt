package aservio.management.activities;

import java.util.ArrayList;
import java.util.List;

public class ActivityList {
    private List<Activity> activities = new ArrayList<>();

    public void add(Activity activity) {
        activities.add(activity);
    }

    public void remove(Activity activity) {
        activities.remove(activity);
    }

    public List<Activity> getActivities() {
        return this.activities;
    }

    // TODO needs to implement all the different kinds of sorting deemed to be necessary. (Sort by Name, Type, Time etc)
}