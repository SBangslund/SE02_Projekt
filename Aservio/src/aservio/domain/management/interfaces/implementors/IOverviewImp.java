package aservio.domain.management.interfaces.implementors;

import aservio.data.IDataPipeImp;
import aservio.domain.management.activities.Activity;
import aservio.domain.management.activities.ActivityList;
import aservio.domain.platform.DataPipe;
import aservio.domain.platform.interfaces.contracts.IDataPipe;
import aservio.domain.platform.user.Address;
import aservio.domain.platform.user.User;
import aservio.domain.platform.user.UserInfo;
import aservio.domain.platform.user.roles.Caretaker;
import aservio.presentation.management.interfaces.contracts.IOverview;
import javafx.scene.control.DatePicker;

import java.util.List;
import java.util.UUID;

public class IOverviewImp implements IOverview {

    private DataPipe pipe;

    public IOverviewImp() {
        pipe = new DataPipe();
    }

    @Override
    public ActivityList getActivities(UUID userid) {
        return pipe.getUserActivities(userid);
    }

    @Override
    public List<User> getUsers(Activity activity) {
        return null;
    }
}
