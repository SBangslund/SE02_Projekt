package aservio.presentation.management.interfaces.contracts;

import aservio.domain.management.activities.Activity;
import java.util.UUID;

public interface ISideView {
    boolean addActivity(Activity activity, UUID userid);
    boolean deleteActivity(UUID activityid);
    
}
