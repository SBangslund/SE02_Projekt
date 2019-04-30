package aservio.presentation.management.interfaces.contracts;

import aservio.domain.management.activities.Activity;
import aservio.domain.management.activities.ActivityList;
import aservio.domain.platform.user.User;

import java.util.List;

public interface IOverview {
    ActivityList getActivities();

    List<User> getUsers(Activity activity);
}
