package aservio.management.overview;

import aservio.management.activities.ActivityList;
import aservio.management.interfaces.Pageable;
import aservio.management.interfaces.ShowableActivity;
import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.WeekFields;

import java.net.URL;
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

public class OverviewWeek extends Overview implements Pageable, ShowableActivity, Initializable{
    
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

    public OverviewWeek(){
        super(new Date());
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
        
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        labelWeekNumber.setText(String.valueOf("Uge " +weekNumber));
    }

}
