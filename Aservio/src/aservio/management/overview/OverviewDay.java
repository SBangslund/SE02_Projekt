package aservio.management.overview;

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
}
