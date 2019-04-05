package aservio.management.activities;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Activity {
    private String description;
    private ActivityType activityType;



    private Date startDate;
    private Date endDate;

    public Activity(ActivityType activityType, Date startDate){
        this(activityType, startDate, new Date());
        setEndDate(standartEndDate(startDate));
    }

    public Activity(ActivityType activityType, Date startDate, Date endDate){
        this.startDate = startDate;
        this.endDate = endDate;
        this.activityType = activityType;
    }

    public String getDescription(){ return description;}

    //Create a date an hour from the specified date
    public Date standartEndDate(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        cal.add(Calendar.HOUR_OF_DAY, 1);
        return cal.getTime();
    }

    private void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public String getTimeSlotString(){
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        return String.format("%s - %s", formatter.format(getStartDate()), formatter.format(getEndDate()));
    }
}
