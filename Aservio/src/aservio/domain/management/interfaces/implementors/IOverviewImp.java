package aservio.domain.management.interfaces.implementors;

import aservio.domain.management.activities.Activity;
import aservio.domain.management.activities.ActivityList;
import aservio.domain.platform.Repository;
import aservio.domain.platform.user.User;
import aservio.presentation.management.interfaces.contracts.IOverview;

import java.util.List;
import java.util.UUID;

public class IOverviewImp implements IOverview {

    private Repository repository;

    public IOverviewImp(Repository repository) {
        this.repository = repository;
    }

    @Override
    public ActivityList getActivities(UUID userid) {
        return repository.getUserActivities(userid);
    }

    @Override
    public List<User> getUsers(Activity activity) {
        return null;
    }
}
