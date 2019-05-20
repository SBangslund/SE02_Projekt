package aservio.presentation.management.controllers.overview;

import aservio.domain.platform.user.UserInfo;
import aservio.presentation.PresentationInterfaceManager;
import aservio.presentation.management.controllers.Management;
import aservio.presentation.management.interfaces.Pageable;
import aservio.presentation.management.interfaces.contracts.IOverviewManager;
import aservio.presentation.platform.OverviewType;
import aservio.domain.management.activities.ActivityList;
import aservio.domain.platform.user.User;
import aservio.presentation.platform.controllers.Profile;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages the different sets of {@link Overview}'s relevant for the {@link Management} view. Also manages relevant
 * {@link aservio.domain.management.activities.Activity activities} based on selected users.
 *
 * @see OverviewMonth
 * @see OverviewWeek
 * @see OverviewDay
 */
public class OverviewManager {

    /**
     * Access to domain layer. Needs to be given an implementation at system initialize.
     */
    private IOverviewManager interFace = PresentationInterfaceManager.getIOverviewManager();

    /**
     * The currently selected {@link Overview}.
     *
     * @see OverviewMonth
     * @see OverviewWeek
     * @see OverviewDay
     */
    private Overview currentOverview;

    /**
     * Relevant users for the view. Used to show {@link aservio.domain.management.activities.Activity}'s based on the
     * users selected in {@link Profile}.
     */
    private List<UserInfo> currentSelectedUsers = new ArrayList<>();

    /**
     * Whether or not to show {@link aservio.domain.management.activities.Activity activities} relevant to the current user.
     */
    private boolean showOwnActivites = true;

    public OverviewManager() {
        Profile.eventManager.addEventHandler(Profile.SELECTED_USERS_CHANGED, event -> {
            handleSelectedUsersChanged(event.getSelectedUsers());
        });
    }

    /**
     * Handles the triggered event from {@link Profile#SELECTED_USERS_CHANGED}. Will update the views current activities
     * based on what is selected during the event. Then calls the {@link #updateActivities()} method to insert the
     * relevant activities into the view.
     *
     * @param userInfoList The list of {@link UserInfo} given from the event.
     */
    private void handleSelectedUsersChanged(List<UserInfo> userInfoList) {
        currentSelectedUsers = userInfoList;
        if (showOwnActivites && !currentSelectedUsers.contains(User.getCurrentUser().getUserInfo())) {
            currentSelectedUsers.add(User.getCurrentUser().getUserInfo());
        } else {
            currentSelectedUsers.remove(User.getCurrentUser().getUserInfo());
        }

        ActivityList activities = new ActivityList();
        for (UserInfo ui : userInfoList) {
            ActivityList usersActivities = interFace.getActivities(ui.getId());
            if (usersActivities != null) {
                activities.getActivities().addAll(usersActivities.getActivities());
            }
        }
        currentOverview.showActivities(activities);
    }

    /**
     * Set the {@link #currentOverview} to the Month FXML using {@link OverviewType}.MONTH.
     */
    public void showMonth() {
        setCurrentOverview(OverviewType.MONTH.getURL());
        updateActivities();
    }

    /**
     * Set the {@link #currentOverview} to the Week FXML using {@link OverviewType}.WEEK.
     */
    public void showWeek() {
        setCurrentOverview(OverviewType.WEEK.getURL());
        updateActivities();
    }

    /**
     * Set the {@link #currentOverview} to the Day FXML using {@link OverviewType}.DAY.
     */
    public void showDay() {
        setCurrentOverview(OverviewType.DAY.getURL());
        updateActivities();
    }

    /**
     * Upon clicking the Next button in the right corner, call the {@link #currentOverview}'s
     * {@link Pageable#next()} method.
     */
    public void showNext() {
        currentOverview.next();
        updateActivities();
    }

    /**
     * Upon clicking the Previous button in the right corner, call the {@link #currentOverview}'s
     * {@link Pageable#previous()} method.
     */
    public void showPrevious() {
        currentOverview.previous();
        updateActivities();
    }

    /**
     * Update the view currently selected.
     */
    public void updateCurrentView() {
        updateActivities();
    }

    /**
     * Set the current overview (must be of the class {@link Overview}. This will be shown in the main window of the
     * application.
     *
     * @param url The FXML files resource URL.
     * @see OverviewMonth
     * @see OverviewWeek
     * @see OverviewDay
     */
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

    /**
     * Update all the {@link aservio.domain.management.activities.Activity Activities} currently relevant for the view.
     * Also checks for {@link #showOwnActivites} to take into account, whether or not to show activities related to
     * the currently logged in user.
     */
    private void updateActivities() {
        handleSelectedUsersChanged(currentSelectedUsers);
    }

    /**
     * Set {@link #showOwnActivites}. Will mostly be used by {@link Management}.
     *
     * @param showOwnActivites Whether or not to show activities related to the currently logged in user.
     */
    public void setShowOwnActivites(boolean showOwnActivites) {
        this.showOwnActivites = showOwnActivites;
        updateActivities();
    }

    /**
     * Retrieve the current Overview {@link #currentOverview}.
     *
     * @return The currently viewed {@link Overview}.
     */
    public Overview getCurrentOverview() {
        return this.currentOverview;
    }
}
