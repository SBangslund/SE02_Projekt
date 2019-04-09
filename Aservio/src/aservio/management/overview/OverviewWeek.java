package aservio.management.overview;

import aservio.management.activities.Activity;
import aservio.management.activities.ActivityList;
import aservio.management.interfaces.Pageable;
import aservio.management.interfaces.ShowableActivity;
import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.WeekFields;

import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class OverviewWeek extends Overview implements Pageable, ShowableActivity, Initializable {
    
    Date currentDate;
    
    ActivityList activityList;
    @FXML
    private Label labelWeekNumber;
    @FXML
    private GridPane gridePaneWeek;
    
    private LocalDate localDate = LocalDate.now();
    private WeekFields weekFields = WeekFields.of(Locale.ENGLISH);
    private int weekNumber = localDate.get(weekFields.weekOfWeekBasedYear());
    @FXML
    private Pane paneMonday;
    @FXML
    private Pane paneTuersday;
    @FXML
    private Pane paneWednesday;
    @FXML
    private Pane paneThursday;
    @FXML
    private Pane paneFriday;
    @FXML
    private Pane paneSatuday;
    @FXML
    private Pane paneSunday;
    
    private Pane[] paneArray;
    
    public OverviewWeek(){
        super(new Date());
    }
    
    public OverviewWeek(Date date) {
        super(date);
        this.currentDate = date;
        
    }
    
    @Override
    protected void initialize() {
        
    }
    
    @Override
    public void next() {
        
    }
    
    @Override
    public void previous() {
        
    }
    
    @Override
    public void showActivities(ActivityList activities) {
        createVisualWeek(activities, currentDate);
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        labelWeekNumber.setText(String.valueOf("Uge " + weekNumber));
        paneArray = new Pane[]{paneSunday, paneMonday, paneTuersday, paneWednesday, paneThursday, paneFriday, paneSatuday};     

        
        
    }
    
    private void createVisualWeek(ActivityList activities, Date date) {
        Calendar currentCalendar = Calendar.getInstance();
        currentCalendar.setTime(new Date());
        
        for (int i = Calendar.SUNDAY; i <= Calendar.SATURDAY; i++) {
            currentCalendar.set(Calendar.DAY_OF_WEEK, i);
            for (Activity activiity : getActivitiesForDay(activities, currentCalendar.getTime())) {
                showActivity(activiity, paneArray[i-1]);
            }
        }
    }
    
    private ArrayList<Activity> getActivitiesForDay(ActivityList activityList, Date date) {
        Calendar currentDay = Calendar.getInstance();
        currentDay.setTime(date);
        ArrayList<Activity> returnActivitiesForDay = new ArrayList<>();
        for (Activity activity : activityList.getActivities()) {
            Calendar activityDay = Calendar.getInstance();
            activityDay.setTime(activity.getStartDate());
            if (currentDay.get(Calendar.DAY_OF_YEAR) == activityDay.get(Calendar.DAY_OF_YEAR)) {
                returnActivitiesForDay.add(activity);
            }
            
        }
        return returnActivitiesForDay;
    }
    
    private void showActivity(Activity activity, Pane pane) {
        Calendar c = Calendar.getInstance();
        c.setTime(activity.getStartDate());
        Button activityButton = new Button();
        
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int min = c.get(Calendar.MINUTE);
        int minPerDay = 1440;
        int minPast = (hour * 60) + min;  
        c.setTime(activity.getEndDate());
        int endHour = c.get(Calendar.HOUR_OF_DAY);
        int endMin = c.get(Calendar.MINUTE);
        
        int x = 0;
        int yStart = (minPast * minPerDay) / (24 * 60);
        
        int yEnd = (((endHour*60)+endMin) * minPerDay) / (24 * 60);
        
        
        
        activityButton.setLayoutX(x);
        activityButton.setLayoutY(yStart);
        activityButton.setPrefHeight(yEnd - yStart);

        activityButton.setText(activity.getDescription() + " " + activity.getTimeSlotString());
        
        
        
        pane.getChildren().add(activityButton);
        
    }
    
}
