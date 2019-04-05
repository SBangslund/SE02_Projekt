package aservio.management.overview;


import aservio.management.activities.Activity;
import aservio.management.activities.ActivityList;
import aservio.management.activities.ActivityType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.*;

public class OverviewDay extends Overview implements Initializable {

    @FXML
    public GridPane gridPane;
    public ScrollPane scrollPane;
    public Label DayOfWeekLabel;
    public Label moreInformationLabel;
    public Pane normalPane;
    public ImageView imageViewTest;

    Date date;
    Parent root;
    ArrayList<Pane> hourPanes;
    ArrayList<Pane> hourContentPanes;

    ActivityList activityList;

    ArrayList<Button> eventButtonList;

    public OverviewDay() {
        this(new Date(), new ActivityList());
    }

    public OverviewDay(ActivityList activitiesList) {
        this(new Date(), activitiesList);
    }

    public OverviewDay(Date date, ActivityList activityList) {
        super(date);
        this.date = date;
        this.activityList = activityList;
        this.initialize();
        eventButtonList = new ArrayList<>();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        scrollPane.setFitToWidth(true);

        DayOfWeekLabel.setText(String.format("%s", new SimpleDateFormat("EEEEE", defineLocaleDate()).format(date.getTime())));
        moreInformationLabel.setText(String.format("den %tD", Calendar.getInstance().getTime()));

        hourPanes = new ArrayList<>();
        hourContentPanes = new ArrayList<>();

        //adding test activities;
        activityList.add(new Activity(ActivityType.EAT, new Date(), standartEndDate(new Date())));
        activityList.add(new Activity(ActivityType.RUN, new GregorianCalendar(2019, 5, 4, 8, 20).getTime(), standartEndDate(new Date())));
        activityList.add(new Activity(ActivityType.WALK, new GregorianCalendar(2019, 5, 4, 9, 20).getTime(), standartEndDate(new Date())));
        activityList.add(new Activity(ActivityType.TENNIS, new Date(), standartEndDate(new Date())));

        //imageViewTest.setImage(activityList.getActivities().get(1).getActivityType().getIcon());
        imageViewTest.setImage(new Image("/aservio/management/icons/iconEating.png"));
        fillPane(normalPane, activityList.getActivities());

    }

    public void fillPane(Pane pane, List<Activity> activities) {
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
            //gridPane.add(hour, 1, i);
            hourPanes.add(hour);
        }
    }

    public void fillTimeContent(Pane pane, List<Activity> activities) {
        activities.stream().forEach(e -> showActivity(e));


    }

    //Test, Creating stardart ending dates for testpurposes
    public Date standartEndDate(Date date) {
        Calendar c = getCalendarWithSetTime(date);
        c.add(Calendar.HOUR_OF_DAY, 5);
        return c.getTime();
    }

    public void showActivity(Activity activity) {
        int eventButtonWidth = 100;

        int startHour = getCalendarWithSetTime(activity.getStartDate()).get(Calendar.HOUR_OF_DAY);
        int startMin = getCalendarWithSetTime(activity.getStartDate()).get(Calendar.MINUTE);

        int endHour = getCalendarWithSetTime(activity.getEndDate()).get(Calendar.HOUR_OF_DAY);
        int endMin = getCalendarWithSetTime(activity.getEndDate()).get(Calendar.MINUTE);

/**
 * Use this in case there has to be shown more on the button, than only description and timeslot
 */
        Text buttonContentText = new Text(String.format("%s\n%s", activity.getActivityType().getName(), activity.getTimeSlotString()));
        buttonContentText.getStyleClass().add("buttonContent");
        ImageView image = new ImageView(activity.getActivityType().getIcon());

        VBox buttonContent = new VBox();
        buttonContent.getChildren().add(buttonContentText);
        buttonContent.getChildren().add(image);

        Button eventButton = new Button("", buttonContent);
        eventButton.setGraphic(new ImageView(activity.getActivityType().getIcon()));
        imageViewTest.setImage(activity.getActivityType().getIcon());
//        Button eventButton = new Button();
//        eventButton.setText(String.format("%s\n%s", activity.getActivityType(), activity.getTimeSlotString()));

        //Calculating the position of the event, y relative to the height of the node
        int yStart = (((startHour * 60) + startMin) * hourPanes.size() * 30) / 1440;
        int yEnd = (((endHour * 60) + endMin) * hourPanes.size() * 30) / 1440;
        int xStart = spaceUsed(yStart, yEnd) == 1 ? eventButtonWidth : eventButtonWidth * spaceUsed(yStart, yEnd);


        eventButton.setLayoutX(xStart);
        eventButton.setLayoutY(yStart);
        //eventButton.getStyleClass().add("button");

        eventButton.setPrefHeight(yEnd - yStart);
        eventButton.setPrefWidth(eventButtonWidth);

        eventButtonList.add(eventButton);
        normalPane.getChildren().add(eventButton);

    }

    public Calendar getCalendarWithSetTime(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c;
    }

    @Override
    protected void initialize() {

    }

    public int spaceUsed(double yStart, double yEnd) {
        int countActivitiesInTimeslot = 1;
        for (Button b : eventButtonList) {

            if ((yStart >= b.getLayoutY() && yStart <= b.getLayoutY() + b.getPrefHeight()) || yEnd >= b.getLayoutY() && yEnd <= b.getLayoutY() + b.getPrefHeight() || yStart <= b.getLayoutY() && yEnd >= b.getLayoutY() + b.getPrefHeight()) {
                countActivitiesInTimeslot++;
            }
        }
        return countActivitiesInTimeslot;
    }

    private DateFormatSymbols defineLocaleDate() {
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
        fillPane(normalPane, activities.getActivities());

    }
}
