package aservio.management.overview;

import aservio.management.activities.Activity;
import aservio.management.activities.ActivityList;
import aservio.management.interfaces.Pageable;
import aservio.management.interfaces.ShowableActivity;

import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

@SuppressWarnings("Duplicates")
public class OverviewWeek extends Overview implements Pageable, ShowableActivity, Initializable {

    Date currentDate;

    ActivityList activityList;
    @FXML
    private Label labelWeekNumber;

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
    @FXML
    private GridPane daysGridpane;
    @FXML
    private GridPane gridePaneHours;
    @FXML
    private ScrollPane scrollPaneHours;

    public OverviewWeek() {
        this(new Date());
    }

    public OverviewWeek(Date date) {
        super(date);
        this.currentDate = date;


    }

    @Override
    protected void initialize() {

    }
    // First currentday set. When 'next' has been pressed, the calendar get reset.
    // After currentDate + 7 been set and the acticities as well
    @Override
    public void next() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        cal.add(Calendar.DAY_OF_YEAR, 7);
        currentDate = cal.getTime();
        resetVisualWeek();
        System.out.println(cal.getTime());
        createVisualWeek(activityList, currentDate);

    }
    // Same as next(), only different is that currentDay - 7 i called instead  
    @Override
    public void previous() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        cal.add(Calendar.DAY_OF_YEAR, -7);
        currentDate = cal.getTime();
        resetVisualWeek();
        System.out.println(cal.getTime());
        createVisualWeek(activityList, currentDate);
    }
    
    @Override
    public void showActivities(ActivityList activities) {
        activityList = activities;
        createVisualWeek(activityList, currentDate);
    }
    // Panes for every day at the time 00 to 23
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        paneArray = new Pane[]{paneSunday, paneMonday, paneTuersday, paneWednesday, paneThursday, paneFriday, paneSatuday};
        gridePaneHours.setStyle("-fx-background-color: white");
        daysGridpane.setStyle("-fx-background-color: white; -fx-grid-lines-visible: false;");
    }

    private void createVisualWeek(ActivityList activities, Date date) {
        Calendar currentCalendar = Calendar.getInstance();
        currentCalendar.setTime(date);
        labelWeekNumber.setText(String.valueOf("Uge " + currentCalendar.get(Calendar.WEEK_OF_YEAR)));

        for (int i = Calendar.SUNDAY; i <= Calendar.SATURDAY; i++) {
            currentCalendar.set(Calendar.DAY_OF_WEEK, i);
            for (Activity activiity : getActivitiesForDay(activities, currentCalendar.getTime())) {
                showActivity(activiity, paneArray[i - 1]);
            }
        }
    }
    // A currentDay is set. The method loop throw the activityList and get the startDate for the activities(activityDay).
    // If curentDay is equal activtyDay, the activity is add to the array returnActivitiesForDay 
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
        
        // These parametre show where the activities are in the panes
        // It works like a system of coodinates with x and y
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int min = c.get(Calendar.MINUTE);
        int minPerDay = 1440; // 24*60
        int minPast = (hour * 60) + min;
        c.setTime(activity.getEndDate());
        int endHour = c.get(Calendar.HOUR_OF_DAY);
        int endMin = c.get(Calendar.MINUTE);
        int x = 0;
        int yStart = (minPast * minPerDay) / (24 * 60);
        int yEnd = (((endHour * 60) + endMin) * minPerDay) / (24 * 60);

        // Create hexadecimal color from the current activity type.
        String defaultColor = String.format("#%02X%02X%02X",
                    (int) (activity.getActivityType().getColor().getRed() * 255),
                    (int) (activity.getActivityType().getColor().getGreen() * 255),
                    (int) (activity.getActivityType().getColor().getBlue() * 255));

        // Setting up the activity as a HBox. (Because of how the design turned out)
        // The box is colored with CSS based on the Activity type. (Which is why it is done here, in code)
        // Reference to the CSS sheet is created, and paddings are created for the text within the HBox.
        HBox box = new HBox();
        box.setStyle("-fx-background-color: linear-gradient(from 0px 0px to 10px 0px, " + defaultColor + " 99%, white);");
        box.getStyleClass().add("activity_box");
        box.setPadding(new Insets(0, 0, 0, 2));

        // Setting up the different events necessary for the activity.
        // Prints the event to console. (Temporary)
        box.setOnMouseClicked(e -> {
            super.uponClick(activity);
        });

        // Whenever the mouse enters the element. (Hover)
        // Fill in the original activity color for a "select" effect.
        box.setOnMouseEntered(event -> {
            box.setStyle("-fx-background-color: linear-gradient(from 0px 0px to " + (box.getWidth() - 50) + "px 0px, " + defaultColor + " 99%, white);"
                        + "-fx-background-position: left top, center;");
        });

        // Whenever the mouse exits the element. Reset the color back to original.
        box.setOnMouseExited(event -> {
            box.setStyle("-fx-background-color: linear-gradient(from 0px 0px to 10px 0px, " + defaultColor + " 99%, white);");
        });

        // Initialize and declare three elements to fill out the activity element.
        // A label with the name. A buffer to separate name and icon and the icon.
        Label label = new Label(activity.getActivityType().getName() + "\n " + activity.getTimeSlotString());
        Pane buffer = new Pane();
        ImageView icon = new ImageView(activity.getActivityType().getIcon());

        label.getStyleClass().add("activity_label");    // Reference to the CSS.
        buffer.setPrefWidth(Region.USE_COMPUTED_SIZE);  // Make the buffer expandable.
        icon.setFitHeight(25);                          // Set the icon to a set height.
        icon.setPreserveRatio(true);                    // Preserve the icon ratio.

        box.getChildren().add(label);                   // Add the label to activity element.
        box.getChildren().add(buffer);                  // Add buffer to activity element.
        box.getChildren().add(icon);                    // Add icon to activity element.
        box.setLayoutX(x);
        box.setLayoutY(yStart);
        box.setPrefHeight(yEnd - yStart);
        box.prefWidthProperty().bind(pane.widthProperty());
        HBox.setHgrow(buffer, Priority.ALWAYS);         // Make the buffer always expand if possible.

        pane.getChildren().add(box);                    // Add activity element to GridPane cell.

    }
    // This method is used in prex() and next() to clear the calendar before adding an new page
    public void resetVisualWeek() {
        for (Pane pane : paneArray) {
            pane.getChildren().clear();
        }
    }

}
