package aservio.management.overview;


import aservio.management.activities.Activity;
import aservio.management.activities.ActivityList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
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
        showActivity(new Activity(new Date(), "test11111111111111111111111111"));
        showActivity(new Activity(new GregorianCalendar(2019, Calendar.APRIL, 2, 8, 20).getTime(), "test2"));


    }

    public void showActivity(Activity activity){
        Calendar c = Calendar.getInstance();

        c.setTime(activity.getDate());
        int hour = c.get(Calendar.HOUR_OF_DAY);
        System.out.println(hour);
        int min = c.get(Calendar.MINUTE);
        System.out.println(min);

        Pane eventRectangle = new Pane();
        //Calculating the position of the event, y relative to the height of the node
        int y = (((hour * 60) + min)*hourPanes.size() * 30)/1440;
        int x = 60;
        Rectangle rect = new Rectangle(x, y, 60, 30);
        rect.setArcWidth(10);
        rect.setArcHeight(10);
        rect.setFill(Color.web("#ed4b00"));
        Text text = new Text(activity.getDescription());
        text.setY(y);
        text.setX(x);
        normalPane.getChildren().add(rect);
        normalPane.getChildren().add(text);

    }

    public final float map(float value, float istart, float istop, float ostart, float ostop) {
        return ostart + (ostop - ostart) * ((value - istart) / (istop - istart));
    }

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
