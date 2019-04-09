package aservio.management.overview;

import java.io.File;
import aservio.management.Management;
import aservio.management.activities.Activity;
import aservio.management.activities.ActivityList;
import aservio.management.activities.ActivityType;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.GregorianCalendar;

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
            currentOverview = loader.getController();
            currentOverview.setView(p);
            currentOverview.show();
            URL file = new File(url).toURI().toURL();
            return FXMLLoader.load(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        updateActivities();
    }

    private void updateActivities() {
        ActivityList list = new ActivityList();
        list.add(new Activity(ActivityType.TENNIS, new Date()));
        list.add(new Activity(ActivityType.EAT, new Date()));
        list.add(new Activity(ActivityType.RUN, new GregorianCalendar(2019, 3, 4, 8, 20).getTime()));
        list.add(new Activity(ActivityType.RUN, new GregorianCalendar(2019, 3, 5, 8, 20).getTime()));
        list.add(new Activity(ActivityType.RUN, new GregorianCalendar(2019, 3, 6, 8, 20).getTime()));
        list.add(new Activity(ActivityType.RUN, new GregorianCalendar(2019, 3, 7, 10, 20).getTime()));
        list.add(new Activity(ActivityType.WALK, new GregorianCalendar(2019, 3, 8, 9, 20).getTime()));
        list.add(new Activity(ActivityType.TENNIS, new Date()));
        currentOverview.showActivities(list);
    }

    public Overview getCurrentOverview() {
        return this.currentOverview;
    }
}
