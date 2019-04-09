    package aservio.management.overview;

import aservio.management.activities.ActivityList;
import aservio.management.interfaces.Pageable;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Month;
import java.time.MonthDay;
import java.util.*;

public class OverviewMonth extends Overview implements Initializable, Pageable {

    @FXML
    private Label labelMonthTitle;
    @FXML
    private GridPane gridPaneDays;
    @FXML
    private GridPane gridPaneMonth;

    private List<Pane> days = new ArrayList<>();
    private int currentMonth;

    public OverviewMonth() {
        super(new Date());
        currentMonth = new GregorianCalendar().get(Calendar.MONTH) + 1;
        System.out.println("Called OverviewMonth constructor: " + this);
    }

    @Override
    protected void initialize() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO create month overview
        System.out.println("Created overview month");
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                VBox day = new VBox();
                day.getChildren().add(new Label(j + ":" + i));
                gridPaneMonth.add(day, j, i);
                days.add(day);
            }
        }
        populateDays(currentMonth);
    }

    public void populateDays(int month) {
        resetDays();
        GregorianCalendar calender = new GregorianCalendar();
        GregorianCalendar monthCalender = new GregorianCalendar(calender.get(Calendar.YEAR), month - 1, 1);   // Creates new calender based on inserted month
        calender.setTimeZone(TimeZone.getTimeZone("GMT+2"));                                                            // Corrects timezone to DK
        labelMonthTitle.setText(Month.of(month).toString().toLowerCase());
        int firstDayOfWeek = monthCalender.get(Calendar.DAY_OF_WEEK) - 1;
        int numberOfDaysInMonth = MonthDay.of(monthCalender.get(Calendar.MONTH), monthCalender.get(Calendar.DAY_OF_MONTH)).getMonth().length(monthCalender.isLeapYear(monthCalender.get(Calendar.YEAR))) - 1;
        for (int i = firstDayOfWeek; i < numberOfDaysInMonth + firstDayOfWeek; i++) {
            days.get(i - 1).getChildren().add(new Label(MonthDay.of(monthCalender.get(Calendar.MONTH), i - firstDayOfWeek + 1).toString()));
        }
    }

    public void resetDays() {
        days.forEach(e -> {
            e.getChildren().remove(1, e.getChildren().size());
        });
    }

    @Override
    public void next() {
        if (!days.isEmpty()) {
            populateDays(Math.abs(++currentMonth % 12 + 1));
        } else {
            System.err.println("[WARNING][OverviewMonth] has yet to be initialized. populateDays() did not compute.");
        }
    }

    @Override
    public void previous() {
        if (!days.isEmpty()) {
            populateDays(Math.abs(--currentMonth % 12 + 1));
        } else {
            System.err.println("[WARNING][OverviewMonth] has yet to be initialized. populateDays() did not compute.");
        }
    }

    @Override
    public void showActivities(ActivityList activities) {
        System.out.println(activities);
    }
}
