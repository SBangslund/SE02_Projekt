/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aservio.domain.management.interfaces.implementors;

import aservio.domain.management.activities.Activity;
import aservio.domain.platform.Repository;
import aservio.presentation.management.interfaces.contracts.ISideView;
import java.util.UUID;

public class ISideViewImp implements ISideView {

    private final Repository repository;

    public ISideViewImp(Repository repository) {
        this.repository = repository;
    }

    @Override
    public boolean addActivity(Activity activity, UUID userid) {
        return repository.addActivity(activity, userid);
    }

    @Override
    public boolean deleteActivity(UUID activityid) {
        return repository.deleteActivity(activityid);
    }

}
