package aservio.presentation.management.controllers.overview;

import aservio.domain.management.activities.Activity;
import aservio.domain.management.activities.ActivityList;
import aservio.domain.platform.user.UserInfo;
import aservio.presentation.management.interfaces.Pageable;
import aservio.domain.platform.Aservio;
import aservio.presentation.platform.controllers.Profile;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import java.net.URL;
import java.time.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class OverviewMonth extends Overview implements Initializable, Pageable {

    private transient Vector listeners;

    @FXML
    private AnchorPane anchor;
    @FXML
    private Label labelMonthTitle;
    @FXML
    private GridPane gridPaneDays;
    @FXML
    private GridPane gridPaneMonth;

    private List<Pane> daysObjects = new ArrayList<>();
    private List<Date> daysDates = new ArrayList<>();
    private int currentMonth;
    private ActivityList activities;
    private AtomicInteger windowHeight = new AtomicInteger();

    public OverviewMonth() {
        currentMonth = new GregorianCalendar().get(Calendar.MONTH);
    }

    /**
     * Initialize the view AFTER the scene has been initialized. (This is very useful for setting up eventhandlers and the like)
     */
    @Override
    protected void initialize() {
        // This is needed to setup event handlers AFTER the scene has been initialized.
        windowHeight.set(200);                      // This is set to 40 because of the start size.
        Aservio.getInstance().getPrimaryStage().getScene().heightProperty().addListener((obs, oldVal, newVal) -> {
            int temp = (int) ((double) newVal / 8); // The calender has 6 rows (roughly) so this fits the height correctly.
            if (Math.abs(temp - windowHeight.get()) >= 25) {     // If it wasn't the same as before, then do...
                windowHeight.set(temp);             // Update the global value
                showActivities(activities);         // Show activities.
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (int i = 0; i < 6; i++) {       // For every row...
            for (int j = 0; j < 7; j++) {   // For every column
                VBox day = new VBox();
                day.setPadding(new Insets(3, 3, 3, 3));
                gridPaneMonth.add(day, j, i);   // Add the VBox to the correct grid position.
                daysObjects.add(day);           // Add the VBox to a list for later reference.
            }
        }
        gridPaneDays.getChildren().forEach(e -> e.getStyleClass().add("day_title"));    // Link CSS
        populateDays(currentMonth);                                                     // Fill in the days correctly.

        //Profile.eventManager.addEventHandler(Profile.SELECTED_USERS_CHANGED, e -> {
        //    Do the stuff
        //});
    }

    /**
     * Will, depending on the month, fill in the allocated GridPane with accordingly dates and days. This needs to be
     * called before showing activities as it sets a label for the correct day.
     * <p>
     * Also lines up with days in the top: Mandag, tirsdag, onsdag, torsdag, fredag, lørdag and søndag.
     *
     * @param month from 0-11.
     */
    public void populateDays(int month) {
        resetDays();    // Reset the days, ready for new data.

        // Creates new Calender based on the given month and sets the correct timezone.
        GregorianCalendar monthCalender = new GregorianCalendar(new GregorianCalendar().get(Calendar.YEAR), month, 1);
        monthCalender.setTimeZone(TimeZone.getTimeZone("GMT+2"));

        // Sets the month label in the left most corner to the given month name.
        // (The +1 index is because of Month.of operates from 1-12)
        labelMonthTitle.setText(Month.of(month + 1).toString().toLowerCase());

        // Retrieve the number for the first day of the week. This is needed to offset the month view.
        // Then get the number of days in the given month. Needed to iterate through.
        int firstDayOfWeek = new GregorianCalendar(monthCalender.get(Calendar.YEAR), month, 1)
                .get(Calendar.DAY_OF_WEEK) - 1;
        int numberOfDaysInMonth = MonthDay.of(monthCalender.get(Calendar.MONTH) + 1, monthCalender
                .get(Calendar.DAY_OF_MONTH)).getMonth().length(monthCalender.isLeapYear(monthCalender.get(Calendar.YEAR)));

        // Iterate from the first day (offset) to the length of the month + offset.
        for (int i = firstDayOfWeek; i < numberOfDaysInMonth + firstDayOfWeek; i++) {

            // Store the local date for this particular day. (MonthDay.of operates from 1-*, so some +- ones are present.)
            LocalDate locale = MonthDay.of(monthCalender.get(Calendar.MONTH) + 1, i - firstDayOfWeek + 1)
                    .atYear(monthCalender.get(Calendar.YEAR));

            // Converts LocalDate to Date for better referencing.
            Date date = Date.from(locale.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());

            daysDates.add(date);                                            // Add date to list for later reference.
            Label dayLabel = new Label(locale.getDayOfMonth() + "");    // Create Label date number for grid cell.
            dayLabel.getStyleClass().add("day_label");                      // Link label to CSS
            daysObjects.get(i).getChildren().add(dayLabel);                 // Add label to correct VBox
        }
    }

    public void resetDays() {
        daysObjects.forEach(e -> {
            e.getChildren().clear();
        });
        daysDates.clear();
    }

    public void resetActivities() {
        daysObjects.forEach(e -> {
            e.getChildren().remove(e.getChildren().size() != 0 ? 1 : 0, e.getChildren().size());
        });
    }

    /**
     * Change the view to the next month.
     */
    @Override
    public void next() {
        if (!daysObjects.isEmpty()) {
            populateDays(Math.abs(++currentMonth % 12));
            showActivities(this.activities);
        } else {
            System.err.println("[WARNING][OverviewMonth] has yet to be initialized. populateDays() did not compute.");
        }
    }

    /**
     * Change the view to the previous month.
     */
    @Override
    public void previous() {
        if (!daysObjects.isEmpty()) {
            populateDays(Math.abs(--currentMonth % 12));
            showActivities(this.activities);
        } else {
            System.err.println("[WARNING][OverviewMonth] has yet to be initialized. populateDays() did not compute.");
        }
    }

    /**
     * Show all the activities from the {@link ActivityList} to the OverviewMonth. This will remove any previously shown
     * activities.
     *
     * @param activities Activities to show.
     */
    @Override
    public void showActivities(ActivityList activities) {
        // TODO this needs to be run in parallel with threads.
        resetActivities();              // Reset all the activities currently present in the view.
        this.activities = activities;   // Store the given activities globally for later referencing.
        for (Activity activity : activities.getActivities()) {  // For every activity..
            for (java.util.Date date : daysDates) {                       // For every date..
                // Create a reference to the date of the Activity and the current iterable date.
                LocalDate activityDate = activity.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate currentDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                // Get the first day of week (i.e. monday, tuesday) to offset search to match month view.
                int firstDayOfWeek = new GregorianCalendar(currentDate.getYear(), currentDate.getMonthValue() - 1, 1).get(Calendar.DAY_OF_WEEK);

                // If the activity date is the same as the current iterable date, then..
                if (activityDate.getDayOfMonth() == currentDate.getDayOfMonth() && activityDate.getMonth() == currentDate.getMonth()) {
                    // Retrieve the correct pane from the global daysObjects. (-2 is because of the few margins previously created)
                    Pane cell = daysObjects.get(activityDate.getDayOfMonth() - 2 + firstDayOfWeek);

                    // Check to see if the activity can fit within the GridPane cell.
                    if (windowHeight.get() / 35 >= cell.getChildren().size()) {
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
                            box.setStyle("-fx-background-color: linear-gradient(from 0px 0px to " + (box.getWidth() - 50) + "px 0px, " + defaultColor + " 99%, white);" +
                                    "-fx-background-position: left top, center;");
                        });

                        // Whenever the mouse exits the element. Reset the color back to original.
                        box.setOnMouseExited(event -> {
                            box.setStyle("-fx-background-color: linear-gradient(from 0px 0px to 10px 0px, " + defaultColor + " 99%, white);");
                        });

                        // Initialize and declare three elements to fill out the activity element.
                        // A label with the name. A buffer to separate name and icon and the icon.
                        Label label = new Label(activity.getActivityType().getName());
                        Pane buffer = new Pane();
                        ImageView icon = new ImageView(activity.getActivityType().getIcon());

                        label.getStyleClass().add("activity_label");    // Reference to the CSS.
                        buffer.setPrefWidth(Region.USE_COMPUTED_SIZE);  // Make the buffer expandable.
                        icon.setFitHeight(25);                          // Set the icon to a set height.
                        icon.setPreserveRatio(true);                    // Preserve the icon ratio.

                        box.getChildren().add(label);                   // Add the label to activity element.
                        box.getChildren().add(buffer);                  // Add buffer to activity element.
                        box.getChildren().add(icon);                    // Add icon to activity element.
                        HBox.setHgrow(buffer, Priority.ALWAYS);         // Make the buffer always expand if possible.

                        cell.getChildren().add(box);                    // Add activity element to GridPane cell.
                    } else {

                    }
                }
            }
        }
    }
}
