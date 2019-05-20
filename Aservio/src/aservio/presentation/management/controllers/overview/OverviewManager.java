package aservio.presentation.management.controllers.overview;

import aservio.domain.platform.user.UserInfo;
import aservio.presentation.PresentationInterfaceManager;
import aservio.presentation.management.interfaces.contracts.IOverviewManager;
import aservio.presentation.platform.OverviewType;
import aservio.domain.management.activities.ActivityList;
import aservio.domain.platform.user.User;
import aservio.presentation.platform.controllers.Profile;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.List;

public class OverviewManager {

    private IOverviewManager interFace = PresentationInterfaceManager.getIOverviewManager();
    private Overview currentOverview;
    private ActivityList currentActivities = new ActivityList();

    public OverviewManager() {
        Profile.eventManager.addEventHandler(Profile.SELECTED_USERS_CHANGED, event -> {
            handleSelectedUsersChanged(event.getSelectedUsers());
        });
    }

    private void handleSelectedUsersChanged(List<UserInfo> userInfoList) {
        ActivityList activities = new ActivityList();
        for (UserInfo ui : userInfoList) {
            ActivityList usersActivities = interFace.getActivities(ui.getId());
            if (usersActivities != null) {
                activities.getActivities().addAll(usersActivities.getActivities());
            }
        }
        currentActivities = activities;
        updateActivities();
    }

    public void showMonth() {
        setCurrentOverview(OverviewType.MONTH.getURL());
    }

    public void showWeek() {
        setCurrentOverview(OverviewType.WEEK.getURL());
    }

    public void showDay() {
        setCurrentOverview(OverviewType.DAY.getURL());
    }

    public void showNext() {
        currentOverview.next();
        updateActivities();
    }

    public void showPrevious() {
        currentOverview.previous();
        updateActivities();
    }

    public void updateCurrentView() {
        updateActivities();
    }

    private void setCurrentOverview(String url) {
        FXMLLoader loader = new FXMLLoader();
        try {
            Pane p = loader.load(getClass().getResource(url).openStream());
            currentOverview = loader.getController();
            currentOverview.setView(p);
            currentOverview.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        updateActivities();
    }

    private void updateActivities() {
        ActivityList currentUserActivities = interFace.getActivities(User.getCurrentUser().getId());
        currentUserActivities.getActivities().forEach(currentActivities::remove);
        currentActivities.getActivities().addAll(currentUserActivities.getActivities());
        currentOverview.showActivities(currentActivities);
    }

    public Overview getCurrentOverview() {
        return this.currentOverview;
    }
}
