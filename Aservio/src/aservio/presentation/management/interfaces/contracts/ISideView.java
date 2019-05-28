package aservio.presentation.management.interfaces.contracts;

import aservio.domain.management.activities.Activity;
import aservio.domain.platform.user.UserInfo;
import java.util.List;
import java.util.UUID;

public interface ISideView {
    boolean addActivity(Activity activity, UUID userid);
    boolean deleteActivity(UUID activityid);
    List<UserInfo> getCitizensFromCaretaker(UUID userID);
    UserInfo getUserInfo(UUID userID);
    Activity getActivity(UUID activityID);
    List<UserInfo> getUsersFromActivity(Activity activity);
}
