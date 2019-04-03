package aservio.management.overview;

import aservio.management.activities.ActivityList;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class OverviewDay extends Overview {

    public OverviewDay() {
        super(new Date());
    }

    @Override
    protected void initialize() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO create day overview
        System.out.println("Created overview day");
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
