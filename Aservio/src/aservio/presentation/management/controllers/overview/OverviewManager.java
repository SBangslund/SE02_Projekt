package aservio.presentation.management.controllers.overview;

import aservio.presentation.platform.OverviewType;
import aservio.domain.management.activities.Activity;
import aservio.domain.management.activities.ActivityList;
import aservio.domain.platform.user.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import java.io.IOException;

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
        updateActivities();
    }

    public void showPrevious() {
        currentOverview.previous();
        updateActivities();
    }

    private void updateCurrentOverview(String url) {
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


        //selected user
        //last user
        
        /*list.add(new Activity(ActivityType.TENNIS, new Date()));
        list.add(new Activity(ActivityType.EAT, new Date()));
        list.add(new Activity(ActivityType.RUN, new GregorianCalendar(2019, 3, 4, 8, 20).getTime()));
        list.add(new Activity(ActivityType.RUN, new GregorianCalendar(2019, 3, 5, 8, 20).getTime()));
        list.add(new Activity(ActivityType.RUN, new GregorianCalendar(2019, 3, 6, 8, 20).getTime()));
        list.add(new Activity(ActivityType.RUN, new GregorianCalendar(2019, 3, 7, 10, 20).getTime()));
        list.add(new Activity(ActivityType.WALK, new GregorianCalendar(2019, 3, 8, 9, 20).getTime()));
        list.add(new Activity(ActivityType.WALK, new GregorianCalendar(2019, 4, 8, 9, 20).getTime()));
        for (int i = 0; i < 100; i++) {
            Activity activity = new Activity(ActivityType.RUN, new GregorianCalendar(2019, (int)(Math.random() * 12), (int)(Math.random() * 25), 9, 20).getTime());
            activity.setDescription("Der skal løbes så svedet drypper og fødderne bløder!");
            list.add(activity);
        }
        list.add(new Activity(ActivityType.TENNIS, new Date()));*/
        currentOverview.showActivities(list);
    }

    public Overview getCurrentOverview() {
        return this.currentOverview;
    }
}
