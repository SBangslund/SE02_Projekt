package aservio.domain.management.interfaces.implementors;

import aservio.domain.management.activities.Activity;
import aservio.domain.management.activities.ActivityList;
import aservio.domain.platform.user.User;
import aservio.presentation.management.interfaces.contracts.IOverview;

import java.util.List;

public class IOverviewImp implements IOverview {

    @Override
    public ActivityList getActivities() {
        System.out.println("Running interface from IOverviewImp");
        return null;
    }

    @Override
    public List<User> getUsers(Activity activity) {
        return null;
    }
}
