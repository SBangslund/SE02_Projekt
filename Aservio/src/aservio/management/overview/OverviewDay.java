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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
    ArrayList<Pane> hourPanes;

    ActivityList activityList;

    ArrayList<Button> eventButtonList;

    public OverviewDay() {
        this(new Date());
    }

    public OverviewDay(Date date) {
        super(date);
        this.date = date;
        this.initialize();
        eventButtonList = new ArrayList<>();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        scrollPane.setFitToWidth(true);

        DayOfWeekLabel.setText(String.format("%s", new SimpleDateFormat("EEEEE", defineLocaleDate()).format(date.getTime())));
        moreInformationLabel.setText(String.format("den %tD", Calendar.getInstance().getTime()));

        hourPanes = new ArrayList<>();

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
        Button eventButton = new Button();

        int eventButtonWidth = 100;

        int startHour = getCalendarWithSetTime(activity.getStartDate()).get(Calendar.HOUR_OF_DAY);
        int startMin = getCalendarWithSetTime(activity.getStartDate()).get(Calendar.MINUTE);
        int endHour = getCalendarWithSetTime(activity.getEndDate()).get(Calendar.HOUR_OF_DAY);
        int endMin = getCalendarWithSetTime(activity.getEndDate()).get(Calendar.MINUTE);

        VBox buttonContent = new VBox();
        buttonContent.setPrefWidth(eventButtonWidth);
        ImageView imageView = new ImageView(activity.getActivityType().getIcon());
        imageView.setFitWidth(eventButtonWidth);
        imageView.setPreserveRatio(true);

        Text buttonContentText = new Text(String.format("%s\n%s", activity.getActivityType().getName(), activity.getTimeSlotString()));
        buttonContentText.getStyleClass().add("button");

        buttonContent.getChildren().add(buttonContentText);
        buttonContent.getChildren().add(imageView);

        eventButton.setGraphic(buttonContent);

        eventButton.setUserData(activity);

        //Calculating the position of the event, y relative to the height of the node
        int yStart = (((startHour * 60) + startMin) * hourPanes.size() * 30) / 1440;
        int yEnd = (((endHour * 60) + endMin) * hourPanes.size() * 30) / 1440;
        int xStart = spaceUsed(yStart, yEnd) == 1 ? eventButtonWidth : eventButtonWidth * spaceUsed(yStart, yEnd);


        eventButton.setLayoutX(xStart);
        eventButton.setLayoutY(yStart);

        eventButton.setPrefHeight(yEnd - yStart);
        eventButton.setMaxWidth(eventButtonWidth);

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
        activities.add(new Activity(ActivityType.EAT, new Date(), standartEndDate(new Date())));
        activities.add(new Activity(ActivityType.RUN, new GregorianCalendar(2019, 5, 4, 8, 20).getTime(), standartEndDate(new Date())));
        activities.add(new Activity(ActivityType.WALK, new GregorianCalendar(2019, 5, 4, 9, 20).getTime(), standartEndDate(new Date())));
        activities.add(new Activity(ActivityType.TENNIS, new Date(), standartEndDate(new Date())));

        fillPane(normalPane, activities.getActivities());

    }
}
