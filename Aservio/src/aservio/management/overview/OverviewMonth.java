package aservio.management.overview;

import aservio.management.activities.Activity;
import aservio.management.activities.ActivityList;
import aservio.management.interfaces.Pageable;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.net.URL;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
    private Number[] height = new Number[1];

    public OverviewMonth() {
        super(new Date());
        currentMonth = new GregorianCalendar().get(Calendar.MONTH);
        System.out.println("Called OverviewMonth constructor: " + this);
    }

    @Override
    protected void initialize() {
        height[0] = 40;
        gridPaneMonth.getScene().heightProperty().addListener((obs, oldVal, newVal) -> {
            height[0] = (double) newVal / 8;
            showActivities(activities);
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

    public void populateDays(int month) {
        resetDays();
        GregorianCalendar monthCalender = new GregorianCalendar(new GregorianCalendar().get(Calendar.YEAR), month, 1);   // Creates new calender based on inserted month
        monthCalender.setTimeZone(TimeZone.getTimeZone("GMT+2"));                                                            // Corrects timezone to DK
        labelMonthTitle.setText(Month.of(month + 1).toString().toLowerCase());
        int firstDayOfWeek = new GregorianCalendar(monthCalender.get(Calendar.YEAR), month, 1).get(Calendar.DAY_OF_WEEK) - 1;
        int numberOfDaysInMonth = MonthDay.of(monthCalender.get(Calendar.MONTH) + 1, monthCalender.get(Calendar.DAY_OF_MONTH)).getMonth().length(monthCalender.isLeapYear(monthCalender.get(Calendar.YEAR)));
        for (int i = firstDayOfWeek; i < numberOfDaysInMonth + firstDayOfWeek; i++) {
            LocalDate locale = MonthDay.of(monthCalender.get(Calendar.MONTH) + 1, i - firstDayOfWeek + 1).atYear(monthCalender.get(Calendar.YEAR));
            Date date = Date.from(locale.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
            daysDates.add(date);
            Label dayLabel = new Label(locale.getDayOfMonth() + "");
            dayLabel.getStyleClass().add("day_label");
            daysObjects.get(i).getChildren().add(dayLabel);
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

    @Override
    public void next() {
        if (!daysObjects.isEmpty()) {
            populateDays(Math.abs(++currentMonth % 12));
        } else {
            System.err.println("[WARNING][OverviewMonth] has yet to be initialized. populateDays() did not compute.");
        }
    }

    @Override
    public void previous() {
        if (!daysObjects.isEmpty()) {
            populateDays(Math.abs(--currentMonth % 12));
        } else {
            System.err.println("[WARNING][OverviewMonth] has yet to be initialized. populateDays() did not compute.");
        }
    }

    @Override
    public void showActivities(ActivityList activities) {
        resetActivities();
        this.activities = activities;
        for (Activity activity : activities.getActivities()) {
            for (Date date : daysDates) {
                LocalDate activityDate = activity.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate currentDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                int firstDayOfWeek = new GregorianCalendar(currentDate.getYear(), currentDate.getMonthValue() - 1, 1).get(Calendar.DAY_OF_WEEK);
                if (activityDate.getDayOfMonth() == currentDate.getDayOfMonth() && activityDate.getMonth() == currentDate.getMonth()) {

                    Pane p = daysObjects.get(activityDate.getDayOfMonth() - 2 + firstDayOfWeek);
                    if (height[0].intValue() / 35 >= p.getChildren().size()) {
                        String defaultColor = String.format("#%02X%02X%02X",
                                (int) (activity.getActivityType().getColor().getRed() * 255),
                                (int) (activity.getActivityType().getColor().getGreen() * 255),
                                (int) (activity.getActivityType().getColor().getBlue() * 255));

                        HBox box = new HBox();
                        VBox.setVgrow(box, Priority.NEVER);
                        box.setPrefHeight(Region.USE_COMPUTED_SIZE);
                        box.setStyle("-fx-background-color: linear-gradient(from 0px 0px to 10px 0px, " + defaultColor + " 99%, white);");
                        box.getStyleClass().add("activity_box");
                        box.setPadding(new Insets(0, 0, 0, 2));

                        box.setOnMouseClicked(System.out::println);
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
                        label.setPrefHeight(Region.USE_COMPUTED_SIZE);
                        buffer.setPrefWidth(Region.USE_COMPUTED_SIZE);
                        buffer.setPrefHeight(Region.USE_COMPUTED_SIZE);
                        icon.setFitHeight(25);
                        icon.setPreserveRatio(true);

                        box.getChildren().add(label);
                        box.getChildren().add(buffer);
                        box.getChildren().add(icon);
                        HBox.setHgrow(buffer, Priority.ALWAYS);

                        p.getChildren().add(box);
                    } else {

                    }
                }
            }
        }
    }
}
