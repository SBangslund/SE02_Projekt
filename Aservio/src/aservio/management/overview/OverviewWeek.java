package aservio.management.overview;

import aservio.management.activities.ActivityList;
import aservio.management.interfaces.Pageable;
import aservio.management.interfaces.ShowableActivity;
import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.WeekFields;

import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.control.DatePicker;

public class OverviewWeek extends Overview implements Pageable, ShowableActivity, Initializable{
    
    ActivityList activityList;
    @FXML
    private Label labelWeekNumber;
    @FXML
    private GridPane gridePaneWeek;
    
    
//    DatePicker datePicker = new DatePicker();
//    LocalDate date = datePicker.getValue();
////    Locale locale = new Locale("da", "DK");
//  //  Locale locale = Locale.US;
//    int weekOfYear = date.get(WeekFields.ISO.weekBasedYear());
    
    LocalDate localDate = LocalDate.now(); 
    WeekFields weekFields = WeekFields.of(Locale.ENGLISH);
    int weekNumber = localDate.get(weekFields.weekOfWeekBasedYear());

    public OverviewWeek(ActivityList activityList) {
        super(new Date());
        this.activityList = activityList;
    }
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
