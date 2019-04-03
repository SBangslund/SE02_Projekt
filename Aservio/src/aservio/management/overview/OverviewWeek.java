package aservio.management.overview;

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
}
