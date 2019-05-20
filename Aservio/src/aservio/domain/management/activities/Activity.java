package aservio.domain.management.activities;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class Activity {
    private String description;
    private ActivityType activityType;
    private UUID id;

    private Date startDate;
    private Date endDate;
    private String activityName;

    public Activity(ActivityType activityType, Date startDate) {
        this("ingen navn", activityType, startDate, new Date(), UUID.randomUUID(), "ingen beskrivelse");
        setEndDate(setStandardEndDate(startDate));
    }

    public Activity(String activityName, ActivityType activityType, Date startDate, Date endDate, UUID id, String description) {
        this.activityName = activityName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.activityType = activityType;
        this.id = id;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Activity && ((Activity) o).id == this.id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    //Create a date an hour from the specified date
    public Date setStandardEndDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        cal.add(Calendar.HOUR_OF_DAY, 3);
        return cal.getTime();
    }

    private void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public String getTimeSlotString() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        return String.format("%s - %s", formatter.format(getStartDate()), formatter.format(getEndDate()));
    }

    public ActivityType getActivityType() {
        return activityType;
    }

    public UUID getId() {
        return id;
    }

    public String getActivityName(){
        return activityName;
    }
}
