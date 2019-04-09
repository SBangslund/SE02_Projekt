    package aservio.management.overview;

import aservio.management.activities.ActivityList;
import aservio.management.interfaces.Pageable;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.net.URL;
import java.time.*;
import java.util.*;

public class OverviewMonth extends Overview implements Initializable, Pageable {

    @FXML
    private Label labelMonthTitle;
    @FXML
    private GridPane gridPaneDays;
    @FXML
    private GridPane gridPaneMonth;

    private List<Pane> daysObjects = new ArrayList<>();
    private List<Date> daysDates = new ArrayList<>();
    private int currentMonth;

    public OverviewMonth() {
        super(new Date());
        currentMonth = new GregorianCalendar().get(Calendar.MONTH);
        System.out.println("Called OverviewMonth constructor: " + this);
    }

    @Override
    protected void initialize() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO create month overview
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                VBox day = new VBox();
                gridPaneMonth.add(day, j, i);
                daysObjects.add(day);
            }
        }
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
            daysObjects.get(i).getChildren().add(new Label(locale.getDayOfMonth() + ""));
        }
    }

    public void resetDays() {
        daysObjects.forEach(e -> {
            e.getChildren().clear();
        });
        daysDates.clear();
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
        activities.getActivities().forEach(activity -> {
            LocalDate activityDate = activity.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            daysDates.forEach(date -> {
                LocalDate currentDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                int firstDayOfWeek = new GregorianCalendar(currentDate.getYear(), currentDate.getMonthValue() - 1, 1).get(Calendar.DAY_OF_WEEK);
                if(activityDate.getDayOfMonth() == currentDate.getDayOfMonth() && activityDate.getMonth() == currentDate.getMonth()) {
                    String defaultColor = String.format( "#%02X%02X%02X",
                            (int)( activity.getActivityType().getColor().getRed() * 255 ),
                            (int)( activity.getActivityType().getColor().getGreen() * 255 ),
                            (int)( activity.getActivityType().getColor().getBlue() * 255 ) );

                    HBox box = new HBox();
                    box.getStyleClass().add("HBox");
                    box.setStyle("-fx-background-color: " + defaultColor);
                    box.setOnMouseClicked(event -> {
                        // Whenever clicked.
                        System.out.println(event);
                    });
                    box.setOnMouseEntered(event -> {
                        box.setStyle("-fx-background-color: red");
                    });
                    box.setOnMouseExited(event -> {

                        box.setStyle("-fx-background-color: " + defaultColor);
                    });
                    box.setSpacing(20);
                    box.setPadding(new Insets(2, 2, 2, 2));

                    Label button = new Label(activity.getActivityType().getName());
                    Pane buffer = new Pane();
                    ImageView icon = new ImageView(activity.getActivityType().getIcon());

                    buffer.setPrefWidth(Region.USE_COMPUTED_SIZE);
                    icon.setFitWidth(25);
                    icon.setFitHeight(25);

                    box.getChildren().add(button);
                    box.getChildren().add(buffer);
                    box.getChildren().add(icon);
                    HBox.setHgrow(buffer, Priority.ALWAYS);

                    daysObjects.get(activityDate.getDayOfMonth() - 2 + firstDayOfWeek).getChildren().add(box);
                }
            });
        });
    }
}
