package aservio.domain.management.activities;

import aservio.presentation.management.controllers.sideview.SideViewActivity;
import aservio.domain.platform.user.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.List;

/**
 * Representing a class used to manage the adding and removal of activities, while simultaneously showing the {@link aservio.presentation.management.controllers.sideview.SideView} and
 * the {@link Activity}, the {@link User} has chosen from the {@link aservio.presentation.management.controllers.overview.Overview}
 */
public class ActivityManager {

    private SideViewActivity currentSideView;

    public ActivityManager(String url) {
        showSideView(url);
    }

    public void add(Activity activity, List<User> users) {
        // TODO add the activity to all the users ActivityLists
        for (User user : users) {
            user.getActivityList().add(activity);
        }
    }
    public void remove(Activity activity, List<User> users){
        for (User user : users) {
            user.getActivityList().remove(activity);
        }
    }

    public void showSideView(String url) {
        FXMLLoader loader = new FXMLLoader();
        try {
            Pane p = loader.load(getClass().getResource(url).openStream());
            currentSideView = loader.getController();
            currentSideView.setView(p);
            currentSideView.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateSideView(Activity activity) {
        currentSideView.showActivity(activity);
    }
}
