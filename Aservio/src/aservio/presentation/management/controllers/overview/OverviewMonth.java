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
        windowHeight.set(200);
        Aservio.getInstance().getPrimaryStage().getScene().heightProperty().addListener((obs, oldVal, newVal) -> {
            int temp = (int) ((double) newVal / 8);
            if (Math.abs(temp - windowHeight.get()) >= 25) {
                windowHeight.set(temp);
                showActivities(activities);
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                VBox day = new VBox();
                day.setPadding(new Insets(3, 3, 3, 3));
                gridPaneMonth.add(day, j, i);
                daysObjects.add(day);
            }
        }
        gridPaneDays.getChildren().forEach(e -> e.getStyleClass().add("day_title"));
        populateDays(currentMonth);
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
        resetDays();

        GregorianCalendar monthCalender = new GregorianCalendar(new GregorianCalendar().get(Calendar.YEAR), month, 1);
        monthCalender.setTimeZone(TimeZone.getTimeZone("GMT+2"));

        labelMonthTitle.setText(Month.of(month + 1).toString().toLowerCase());

        int firstDayOfWeek = new GregorianCalendar(monthCalender.get(Calendar.YEAR), month, 1)
                .get(Calendar.DAY_OF_WEEK) - 1;
        int numberOfDaysInMonth = MonthDay.of(monthCalender.get(Calendar.MONTH) + 1, monthCalender
                .get(Calendar.DAY_OF_MONTH)).getMonth().length(monthCalender.isLeapYear(monthCalender.get(Calendar.YEAR)));

        for (int i = firstDayOfWeek; i < numberOfDaysInMonth + firstDayOfWeek; i++) {

            LocalDate locale = MonthDay.of(monthCalender.get(Calendar.MONTH) + 1, i - firstDayOfWeek + 1)
                    .atYear(monthCalender.get(Calendar.YEAR));

            Date date = Date.from(locale.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());

            daysDates.add(date);
            Label dayLabel = new Label(locale.getDayOfMonth() + "");
            dayLabel.getStyleClass().add("day_label");
            daysObjects.get(i).getChildren().add(dayLabel);
        }
    }

    /**
     * Reset all the information stored in each cell of the calender. (Number, position etc.)
     */
    private void resetDays() {
        daysObjects.forEach(e -> {
            e.getChildren().clear();
        });
        daysDates.clear();
    }

    /**
     * Reset all activities for the current Month view. Removed all children from {@link #daysObjects}.
     */
    private void resetActivities() {
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
        resetActivities();
        this.activities = activities;
        for (Activity activity : activities.getActivities()) {
            for (java.util.Date date : daysDates) {
                LocalDate activityDate = activity.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate currentDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                int firstDayOfWeek = new GregorianCalendar(currentDate.getYear(), currentDate.getMonthValue() - 1, 1).get(Calendar.DAY_OF_WEEK);

                if (activityDate.getDayOfMonth() == currentDate.getDayOfMonth() && activityDate.getMonth() == currentDate.getMonth()) {
                    Pane cell = daysObjects.get(activityDate.getDayOfMonth() - 2 + firstDayOfWeek);

                    if (windowHeight.get() / 35 >= cell.getChildren().size()) {
                        String defaultColor = String.format("#%02X%02X%02X",
                                (int) (activity.getActivityType().getColor().getRed() * 255),
                                (int) (activity.getActivityType().getColor().getGreen() * 255),
                                (int) (activity.getActivityType().getColor().getBlue() * 255));

                        HBox box = new HBox();
                        box.setStyle("-fx-background-color: linear-gradient(from 0px 0px to 10px 0px, " + defaultColor + " 99%, white);");
                        box.getStyleClass().add("activity_box");
                        box.setPadding(new Insets(0, 0, 0, 2));

                        box.setOnMouseClicked(e -> {
                            super.uponClick(activity);
                        });

                        box.setOnMouseEntered(event -> {
                            box.setStyle("-fx-background-color: linear-gradient(from 0px 0px to " + (box.getWidth() - 50) + "px 0px, " + defaultColor + " 99%, white);" +
                                    "-fx-background-position: left top, center;");
                        });

                        box.setOnMouseExited(event -> {
                            box.setStyle("-fx-background-color: linear-gradient(from 0px 0px to 10px 0px, " + defaultColor + " 99%, white);");
                        });

                        Label label = new Label(activity.getActivityType().getName());
                        Pane buffer = new Pane();
                        ImageView icon = new ImageView(activity.getActivityType().getIcon());

                        label.getStyleClass().add("activity_label");
                        buffer.setPrefWidth(Region.USE_COMPUTED_SIZE);
                        icon.setFitHeight(25);
                        icon.setPreserveRatio(true);

                        box.getChildren().add(label);
                        box.getChildren().add(buffer);
                        box.getChildren().add(icon);
                        HBox.setHgrow(buffer, Priority.ALWAYS);

                        cell.getChildren().add(box);
                    }
                }
            }
        }
    }
}
