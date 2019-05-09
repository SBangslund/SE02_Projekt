package aservio.domain.management.activities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ActivityList implements Serializable {
    private List<Activity> activities;

    public ActivityList() {
        this.activities = new ArrayList<>();
    }

    public ActivityList(List<Activity> activities) {
        this.activities = activities;
    }

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
