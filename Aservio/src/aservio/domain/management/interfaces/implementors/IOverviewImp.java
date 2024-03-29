package aservio.domain.management.interfaces.implementors;

import aservio.domain.management.activities.Activity;
import aservio.domain.management.activities.ActivityList;
import aservio.domain.platform.Repository;
import aservio.domain.platform.user.User;
import aservio.presentation.management.interfaces.contracts.IOverviewManager;

import java.util.List;
import java.util.UUID;

/**
 * Implementation of {@link IOverviewManager}, used to handle the logic behind viewing  {@link User} {@link Activity}
 * Representing access to {@link Repository} and thereby the database
 */
public class IOverviewImp implements IOverviewManager {

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
