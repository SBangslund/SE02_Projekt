package aservio.management.activities;

import java.util.Date;

public class Activity {
    private String description;
    Date date;

    public Activity(Date date, String description){
        this.date = date;
        this.description = description;
    }

    public Date getDate(){
        return date;
    }

    public String getDescription(){ return description;}
}
