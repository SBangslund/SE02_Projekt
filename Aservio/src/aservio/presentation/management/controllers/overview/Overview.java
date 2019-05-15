package aservio.presentation.management.controllers.overview;

import aservio.domain.platform.user.UserInfo;
import aservio.presentation.PresentationInterfaceManager;
import aservio.presentation.management.controllers.Management;
import aservio.domain.management.activities.Activity;
import aservio.domain.management.activities.ActivityList;
import aservio.presentation.management.interfaces.Pageable;
import aservio.presentation.management.interfaces.ShowableActivity;
import aservio.presentation.management.interfaces.contracts.IOverview;
import aservio.presentation.platform.controllers.Profile;
import javafx.fxml.Initializable;
import javafx.scene.Parent;

import java.util.List;

/**
 * An abstraction of an overview for the {@link Management} class. This is needed to display months, weeks and days.
 */
public abstract class Overview implements ShowableActivity, Pageable, Initializable {

    private Parent view;
    protected IOverview interFace = PresentationInterfaceManager.getIOverview();

    /**
     * Initializes all the necessary nodes for this view. This needs to be implemented by inherited classes.
     */
    protected abstract void initialize();

    private void handleSelectedUsersChanged(List<UserInfo> userInfoList) {
        ActivityList activities = new ActivityList();
        for (UserInfo ui : userInfoList) {
            ActivityList usersActivities = new ActivityList();
            for (Activity a : interFace.getActivities(ui.getId()).getActivities()) {
                usersActivities.add(a);
            }
            activities.getActivities().addAll(usersActivities.getActivities());
        }
        showActivities(activities);
    }

    /**
     * Show overview. (Is hard linked to a {@link javafx.scene.layout.BorderPane}).
     */
    public void show() {
        Management.getInstance().setCenterView(view);

        Profile.eventManager.addEventHandler(Profile.SELECTED_USERS_CHANGED, event -> {
            handleSelectedUsersChanged(event.getSelectedUsers());
        });

        initialize();
    }

    protected void uponClick(Activity activity) {
        Management.getInstance().getActivityManager().updateSideView(activity);
    }

    /**
     * Set the view for the {@link Overview} to visualize.
     * @param view to set.
     */
    public void setView(Parent view) {
        this.view = view;
    }

    public Parent getView() {
        return view;
    }
}
