package aservio.presentation.management.interfaces.contracts;

import aservio.domain.management.activities.Activity;
import aservio.domain.management.activities.ActivityList;
import aservio.domain.platform.user.User;

import java.util.List;
import java.util.UUID;

public interface IOverviewManager {
    ActivityList getActivities(UUID userid);
    List<User> getUsers(Activity activity);
}
