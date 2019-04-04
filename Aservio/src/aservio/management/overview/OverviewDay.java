package aservio.management.overview;


import aservio.management.activities.Activity;
import aservio.management.activities.ActivityList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.control.ScrollPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import java.net.URL;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class OverviewDay extends Overview implements Initializable{

    @FXML
    public GridPane gridPane;
    public ScrollPane scrollPane;
    public Label DayOfWeekLabel;
    public Label moreInformationLabel;
    public Pane normalPane;

    Date date;
    Parent root;
    ArrayList<Pane> hourPanes;
    ArrayList<Pane> hourContentPanes;

    ActivityList activityList;
    Map<Integer, List<Activity>> activitiesMap;

    public OverviewDay(){
        this(new Date(), new ActivityList());
    }

    public OverviewDay(ActivityList activitiesList) {
        this(new Date(), activitiesList);
    }

    public OverviewDay(Date date, ActivityList activityList){
        super(date);
        this.date = date;
        this.activityList = activityList;
        this.initialize();
    }
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        scrollPane.setFitToWidth(true);

        DayOfWeekLabel.setText(String.format("%s", new SimpleDateFormat("EEEEE",defineLocaleDate()).format(date.getTime())));
        moreInformationLabel.setText(String.format("den %tD", Calendar.getInstance().getTime()));

        hourPanes = new ArrayList<>();
        hourContentPanes = new ArrayList<>();
        normalPane.setStyle("-fx-border-color: black");

        //adding test activities;
        activityList.getActivities().add(new Activity("Løbe", new Date(), standartEndDate(new Date())));
        activityList.getActivities().add(new Activity("Meeting", new GregorianCalendar(2019, Calendar.APRIL, 2, 8, 20).getTime(), standartEndDate(new Date())));


        fillPane(normalPane, activityList.getActivities());

    }

    public void fillPane(Pane pane, List<Activity> activities){
        fillTime(pane);
        fillTimeContent(pane, activities);
    }
    
    public void fillTime(Pane pane) {
        for (int i = 0; i < 24; i++) {
            Pane hour = new Pane();
            hour.setMinHeight(30);
            hour.getStyleClass().add("cell");
            hour.getChildren().add(new Text(i < 10 ? "0" + Integer.toString(i) : Integer.toString(i)));
            gridPane.add(hour, 0, i);
            hourPanes.add(hour);
        }
    }

    public void fillTimeContent(Pane pane, List<Activity> activities){
        activities.stream().forEach(e -> System.out.println(e));
        activities.stream().forEach(e -> showActivity(e));


    }

    //Test, Creating stardart ending dates for testpurposes
    public Date standartEndDate(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR_OF_DAY, 5);
        return cal.getTime();
    }

    public void showActivity(Activity activity){
        System.out.println("numbe2");
        Calendar c = Calendar.getInstance();
        c.setTime(activity.getStartDate());
        int startHour = c.get(Calendar.HOUR_OF_DAY);
        int startMin = c.get(Calendar.MINUTE);

        c.setTime(activity.getEndDate());
        int endHour = c.get(Calendar.HOUR_OF_DAY);
        int endMin = c.get(Calendar.MINUTE);

        Text description = new Text(activity.getDescription());
        description.getStyleClass().add("buttonContent");
        Text timeSlot = new Text(activity.getTimeSlotString());
        timeSlot.getStyleClass().add("buttonContent");

        VBox buttonContent = new VBox();
        buttonContent.getChildren().add(description);
        buttonContent.getChildren().add(timeSlot);

        Button eventButton = new Button("", buttonContent);

        //Calculating the position of the event, y relative to the height of the node
        int yStart = (((startHour * 60) + startMin)*hourPanes.size() * 30)/1440;
        int xStart = spaceUsed(activity) ? 200 : 60;
        int yEnd = (((endHour * 60) + endMin)*hourPanes.size() * 30)/1440;

        eventButton.setLayoutX(xStart);
        eventButton.setLayoutY(yStart);
        eventButton.setStyle("-fx-background-color: blue");
        //eventButton.getStyleClass().add("button");

        eventButton.setPrefHeight(yEnd-yStart);
        eventButton.setPrefWidth(60);

        normalPane.getChildren().add(eventButton);

    }

    @Override
    protected void initialize() {
        
    }

    public boolean spaceUsed(Activity activity){
        for (Activity act : activityList.getActivities()) {
            if(activity.getStartDate().compareTo(act.getStartDate()) > 0 && activity.getEndDate().compareTo(act.getEndDate()) < 0){
                return true;
            }
        }
        return false;
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

    @Override
    public void next() {

    }

    @Override
    public void previous() {

    }

    @Override
    public void showActivities(ActivityList activities) {


    }
}
