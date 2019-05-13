package aservio.presentation.management.controllers.overview;

import aservio.domain.platform.user.UserInfo;
import aservio.presentation.PresentationInterfaceManager;
import aservio.presentation.management.controllers.Management;
import aservio.domain.management.activities.Activity;
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
    private IOverview interFace = PresentationInterfaceManager.getIOverview();

    /**
     * Initializes all the necessary nodes for this view. This needs to be implemented by inherited classes.
     */
    protected abstract void initialize();

    protected abstract void handleSelectedUsersChanged(List<UserInfo> userInfoList);

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

    public IOverview getInterFace() {
        return interFace;
    }
}
