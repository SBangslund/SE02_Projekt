package aservio.presentation.management.controllers.overview;

import aservio.presentation.platform.OverviewType;
import aservio.domain.management.activities.ActivityList;
import aservio.domain.platform.user.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import java.io.IOException;

public class OverviewManager {

    private Overview currentOverview;

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
    public void updateCurrentView(){
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
        ActivityList list = currentOverview.interFace.getActivities(User.getCurrentUser().getId());
        currentOverview.showActivities(list);
    }

    public Overview getCurrentOverview() {
        return this.currentOverview;
    }
}
