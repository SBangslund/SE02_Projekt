package aservio.management.overview;

import aservio.management.activities.ActivityList;

import java.util.Date;

public class OverviewDay extends Overview {

    protected OverviewDay() {
        super(new Date());
    }

    @Override
    protected void initialize() {
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
