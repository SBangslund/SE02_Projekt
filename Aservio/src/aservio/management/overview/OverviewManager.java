package aservio.management.overview;

import aservio.management.Management;
import aservio.management.activities.Activity;
import aservio.management.activities.ActivityList;
import aservio.management.activities.ActivityType;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.Date;

public class OverviewManager {

    private Overview currentOverview;

    public void showMonth() {
        updateCurrentOverview(OverviewType.MONTH.getURL());
    }

    public void showWeek() {
        updateCurrentOverview(OverviewType.WEEK.getURL());
    }

    public void showDay() {
        updateCurrentOverview(OverviewType.DAY.getURL());
    }

    public void showNext() {
        currentOverview.next();
    }

    public void showPrevious() {
        currentOverview.previous();
    }

    private void updateCurrentOverview(String url) {
        FXMLLoader loader = new FXMLLoader();
        try {
            Pane p = loader.load(getClass().getResource(url).openStream());
            p.getStylesheets().add(getClass().getResource("/aservio/management/views/CSSOverviewDay.css").toExternalForm());
            currentOverview = loader.getController();
            currentOverview.setView(p);
            currentOverview.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        updateActivities();
    }

    private void updateActivities() {
        ActivityList list = new ActivityList();
        list.add(new Activity(ActivityType.TENNIS, new Date()));
        currentOverview.showActivities(list);
    }

    public Overview getCurrentOverview() {
        return this.currentOverview;
    }
}
