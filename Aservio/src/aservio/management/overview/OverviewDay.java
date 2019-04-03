package aservio.management.overview;


import aservio.management.Management;
import aservio.management.activities.Activity;
import aservio.management.activities.ActivityList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class OverviewDay extends Overview implements Initializable{

    @FXML
    public GridPane gridPane;
    public ScrollPane scrollPane;
    public Label DayOfWeekLabel;
    public Label moreInformationLabel;

    Date date;
    Parent root;
    ArrayList<Pane> hourPanes;
    ArrayList<Pane> hourContentPanes;

    ActivityList activitieList;
    Map<Integer, List<Activity>> activitiesMap;


    public OverviewDay(ActivityList activitiesList) {
        this(new Date(), activitiesList);
    }

    public OverviewDay(Date date, ActivityList activityList){
        this.date = date;
        this.initialize();
    }
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        scrollPane.setFitToWidth(true);

        DayOfWeekLabel.setText(String.format("%s", new SimpleDateFormat("EEEEE",defineLocaleDate()).format(date.getTime())));
        moreInformationLabel.setText(String.format("den %tD", Calendar.getInstance().getTime()));

        hourPanes = new ArrayList<>();
        hourContentPanes = new ArrayList<>();
        fillGrid(gridPane);
    }

    public void fillGrid(GridPane pane, List<Activity> activities){
        fillTime(pane);
        fillTimeContent(pane, activities);
    }
    
    public void fillTime(GridPane pane) {
        for (int i = 0; i < 24; i++) {
            Pane hour = new Pane();
            hour.setMinHeight(30);
            hour.getStyleClass().add("cell");
            hour.getChildren().add(new Text(i < 10 ? "0" + Integer.toString(i) : Integer.toString(i)));
            pane.add(hour, 0, i);
            hourPanes.add(hour);
        }
    }

    public void fillTimeContent(GridPane pane, List<Activity> activities){
        for (int i = 0; i < 24; i++) {
            Pane content = new Pane();
            content.setMinHeight(30);
            content.getStyleClass().add("cell");
            content.getChildren().add(new Text("Activitynumber: " +  Math.random() * i));
            pane.add(content, 1, i);
            hourPanes.add(content);
        }
    }

    public void sortActivities()

    @Override
    protected void initialize() {
        
    }

    private DateFormatSymbols defineLocaleDate(){
        Locale locale = new Locale("da", "DK");
        DateFormatSymbols dateFormatSymbols = new DateFormatSymbols(locale);
        dateFormatSymbols.setWeekdays(new String[]{
                "Unused",
                "Søndag",
                "Mandag",
                "Tirsdag",
                "Onsdag",
                "Torsdag",
                "Fredag",
                "Lørdag"
        });

        return dateFormatSymbols;

    }
}
