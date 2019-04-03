package aservio.management.overview;

import aservio.management.activities.ActivityList;

import java.util.Date;

public class OverviewWeek extends Overview {

    protected OverviewWeek() {
        super(new Date());
    }

    @Override
    public void initialize() {
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
