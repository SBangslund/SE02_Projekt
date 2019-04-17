package aservio.management.activities;

import aservio.management.Management;
import aservio.management.sideview.SideView;
import aservio.management.sideview.SideViewActivity;
import aservio.platform.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.List;

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
