package aservio.management.overview;

import aservio.management.activities.ActivityList;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class OverviewWeek extends Overview {

    public OverviewWeek() {
        super(new Date());
    }

    @Override
    public void initialize() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO create week overview
        System.out.println("Created overview week");
    }

    @Override
    public void next() {

    }

    @Override
    public void previous() {

    }

    @Override
    public void showActivities(ActivityList activities) {

    }
}
