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
import java.time.MonthDay;
import java.util.*;

public class OverviewMonth extends Overview implements Initializable, Pageable {

    @FXML
    private GridPane gridPane;

    private List<Pane> days = new ArrayList<>();

    public OverviewMonth() {
        super(new Date());
    }

    @Override
    protected void initialize() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO create month overview
        System.out.println("Created overview month");
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 7; j++) {
                VBox day = new VBox();
                day.getChildren().add(new Label(j + ":" + i));
                gridPane.add(day, j, i);
                days.add(day);
            }
        }

        populateDays(2);
    }

    public void populateDays(int month) {
        days.clear();
        GregorianCalendar calender = new GregorianCalendar();
        GregorianCalendar monthCalender = new GregorianCalendar(calender.get(Calendar.YEAR), month, 1);
        calender.setTimeZone(TimeZone.getTimeZone("GMT+2"));
        for (int i = 0; i < MonthDay.of(monthCalender.get(Calendar.MONTH), monthCalender.get(Calendar.DAY_OF_MONTH)).getMonth().length(monthCalender.isLeapYear(0)); i++) {
            Date date = new GregorianCalendar(monthCalender.get(Calendar.YEAR), monthCalender.get(Calendar.MONTH), i).getTime();
            days.get(i).getChildren().add(new Label((new SimpleDateFormat("EEEE").format(date))));
            days.get(i).getChildren().add(new Label(MonthDay.of(monthCalender.get(Calendar.MONTH), i + 1).toString()));
        }
    }

    @Override
    public void next() {
        populateDays(3);
    }

    @Override
    public void previous() {
        populateDays(1);
    }

    @Override
    public void showActivities(ActivityList activities) {

    }
}
