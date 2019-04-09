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
        currentMonth = new GregorianCalendar().get(Calendar.MONTH) + 1;
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
                day.getChildren().add(new Label(j + ":" + i));
                gridPaneMonth.add(day, j, i);
                daysObjects.add(day);
            }
        }
        populateDays(currentMonth);
    }

    public void populateDays(int month) {
        resetDays();
        GregorianCalendar calender = new GregorianCalendar();
        GregorianCalendar monthCalender = new GregorianCalendar(calender.get(Calendar.YEAR), month, 1);   // Creates new calender based on inserted month
        monthCalender.setTimeZone(TimeZone.getTimeZone("GMT+2"));                                                            // Corrects timezone to DK
        labelMonthTitle.setText(Month.of(month).toString().toLowerCase());
        int firstDayOfWeek = new GregorianCalendar(calender.get(Calendar.YEAR), month - 1, 1).get(Calendar.DAY_OF_WEEK);
        System.out.println(firstDayOfWeek);
        int numberOfDaysInMonth = MonthDay.of(monthCalender.get(Calendar.MONTH), monthCalender.get(Calendar.DAY_OF_MONTH)).getMonth().length(monthCalender.isLeapYear(monthCalender.get(Calendar.YEAR)));
        for (int i = firstDayOfWeek - 1; i < numberOfDaysInMonth + firstDayOfWeek - 1; i++) {
            LocalDate locale = MonthDay.of(monthCalender.get(Calendar.MONTH), i - firstDayOfWeek + 2).atYear(monthCalender.get(Calendar.YEAR));
            Date date = Date.from(locale.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
            daysDates.add(date);
            daysObjects.get(i - 1).getChildren().add(new Label(date.toString()));
        }
    }

    public void resetDays() {
        daysObjects.forEach(e -> {
            e.getChildren().remove(1, e.getChildren().size());
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
        activities.getActivities().forEach(e -> {
            if(daysDates.contains(e.getStartDate())) {
                System.out.println(e);
            }
        });
    }
}
