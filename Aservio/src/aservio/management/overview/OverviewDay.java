package aservio.management.overview;


import aservio.management.activities.Activity;
import aservio.management.activities.ActivityList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.net.URL;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class OverviewDay extends Overview implements Initializable {

    @FXML
    public ScrollPane scrollPane;
    public Label DayOfWeekLabel;
    public Label moreInformationLabel;
    public Pane activityPane;

    private Date currentDate;

    private ActivityList activityList;

    private ArrayList<Button> eventButtonList;

    private int backgroundHeight = 1200;

    public OverviewDay() {
        this(new Date());
    }

    public OverviewDay(Date date) {
        super(date);
        this.currentDate = date;
        eventButtonList = new ArrayList<>();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        scrollPane.setFitToWidth(true);
    }

    @Override
    public void showActivities(ActivityList activities) {
        resetVisualDay();
        this.activityList = activities;
        createVisualDay(activityList, currentDate);


    }

    @Override
    public void next() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        cal.add(Calendar.DAY_OF_YEAR, 1);
        currentDate = cal.getTime();

        resetVisualDay();
        createVisualDay(activityList, currentDate);
    }

    @Override
    public void previous() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        cal.add(Calendar.DAY_OF_YEAR, -1);
        currentDate = cal.getTime();

        resetVisualDay();
        createVisualDay(activityList, currentDate);
    }

    private void createVisualDay(ActivityList activities, Date date) {
        setBackgroundImage();
        DayOfWeekLabel.setText(String.format("%s", new SimpleDateFormat("EEEEE", setLocaleToDanish()).format(date.getTime())));
        moreInformationLabel.setText(String.format("den %s", new SimpleDateFormat("dd/MM/yyyy", setLocaleToDanish()).format(date.getTime())));
        List<Activity> activitiesForCurrentDay = activities.getActivities().stream().filter(e -> isActivityOnDay(e, date)).collect(Collectors.toList());

        activitiesForCurrentDay.stream().forEach(e -> showActivity(e));
        showLineAtCurrentTime(date);
        scrollPane.setVvalue(0.4);
    }

    private boolean isActivityOnDay(Activity activity, Date date) {
        return new SimpleDateFormat("dd.MM.yyyy", setLocaleToDanish()).format(activity.getStartDate().getTime())
                .equals(new SimpleDateFormat("dd.MM.yyyy", setLocaleToDanish()).format(date.getTime()));
    }

    private void showLineAtCurrentTime(Date date){
        int hour = getCalendarWithSetTime(date).get(Calendar.HOUR_OF_DAY);
        int min = getCalendarWithSetTime(date).get(Calendar.MINUTE);

        System.out.println(String.format("%d, %d", hour, min));
        int minPerDay = 1440;

        int currentY = backgroundHeight/25 + (((hour * 60) + min) * (backgroundHeight - backgroundHeight/25)) / minPerDay;
        Line line = new Line(50, currentY, 1200, currentY);
        line.setStroke(Color.RED);
        line.setStrokeWidth(2);
        Circle c = new Circle(50, currentY, 5, Color.RED);

        activityPane.getChildren().add(line);
        activityPane.getChildren().add(c);
    }

    private void setBackgroundImage() {
        File file = new File("src/aservio/management/icons/OverViewDayTimeTable.png");
        Image image = new Image(file.toURI().toString());
        ImageView backgroundImage = new ImageView(image);
        backgroundImage.setFitHeight(backgroundHeight);
        backgroundImage.setPreserveRatio(true);
        activityPane.getChildren().add(backgroundImage);
    }


    private void showActivity(Activity activity) {
        int standartButtonWidth = 170;

        Button eventButton = createRightSizedButton(activity, standartButtonWidth);
        HBox buttonContent = new HBox(10);
        buttonContent.getStyleClass().add("hbox");
        buttonContent.setPadding(new Insets(5, 0, 0, 5));
        String color = String.format( "#%02X%02X%02X",
                (int)( activity.getActivityType().getColor().getRed() * 255 ),
                (int)( activity.getActivityType().getColor().getGreen() * 255 ),
                (int)( activity.getActivityType().getColor().getBlue() * 255 ) );

        buttonContent.setPrefWidth(standartButtonWidth);

        Text buttonContentText = new Text(String.format("%s\n%s", activity.getActivityType().getName(), activity.getTimeSlotString()));
        buttonContentText.getStyleClass().add("text_button");
        buttonContent.getChildren().add(buttonContentText);

        ImageView imageView = new ImageView(activity.getActivityType().getIcon());
        imageView.setFitWidth(50);
        imageView.setPreserveRatio(true);
        buttonContent.getChildren().add(imageView);

        eventButton.setGraphic(buttonContent);
        eventButton.setUserData(activity);
        eventButton.setStyle("-fx-background-color: linear-gradient(from 0px 0px to 10px 0px, " + color + " 99%, white);");

        eventButton.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        final Stage dialog = new Stage(StageStyle.DECORATED);
                        dialog.initModality(Modality.APPLICATION_MODAL);
                        dialog.initOwner(activityPane.getScene().getWindow());
                        dialog.setScene(createActivityPopUp(activity));
                        dialog.show();
                    }
                });
        eventButtonList.add(eventButton);
        activityPane.getChildren().add(eventButton);
    }

    private Button createRightSizedButton(Activity activity, int standartButtonWidth) {

        int startHour = getCalendarWithSetTime(activity.getStartDate()).get(Calendar.HOUR_OF_DAY);
        int startMin = getCalendarWithSetTime(activity.getStartDate()).get(Calendar.MINUTE);
        int endHour = getCalendarWithSetTime(activity.getEndDate()).get(Calendar.HOUR_OF_DAY);
        int endMin = getCalendarWithSetTime(activity.getEndDate()).get(Calendar.MINUTE);

        int topBackgroundPadding = backgroundHeight/25;
        int leftBackgroundPadding = 50;
        int minPerDay = 1440;

        int yStartPosition = topBackgroundPadding + (((startHour * 60) + startMin) * (backgroundHeight - topBackgroundPadding)) / minPerDay;
        int yEndPosition = topBackgroundPadding + (((endHour * 60) + endMin) * (backgroundHeight - topBackgroundPadding)) / minPerDay;
        int xStartPosition = standartButtonWidth * getColumnNotUsed(yStartPosition, yEndPosition) - leftBackgroundPadding;

        Button eventButton = new Button();
        eventButton.setLayoutX(xStartPosition);
        eventButton.setLayoutY(yStartPosition);
        eventButton.setPrefHeight(yEndPosition - yStartPosition);
        eventButton.setMaxWidth(standartButtonWidth);

        return eventButton;
    }

    private Scene createActivityPopUp(Activity activity){
        HBox dialogVbox = new HBox(20);
        ImageView imageView = new ImageView(activity.getActivityType().getIcon());
        imageView.setFitWidth(50);
        imageView.setPreserveRatio(true);

        Text buttonContentText = new Text(String.format("%s\n%s", activity.getActivityType().getName(), activity.getTimeSlotString()));
        buttonContentText.getStyleClass().add("text_button");
        dialogVbox.getChildren().add(buttonContentText);
        dialogVbox.getChildren().add(imageView);
        Scene dialogScene = new Scene(dialogVbox, 300, 200);
        return dialogScene;
    }

    private int getColumnNotUsed(double yStart, double yEnd) {
        int countActivitiesInTimeslot = 1;
        for (Button b : eventButtonList) {
            if ((yStart >= b.getLayoutY() && yStart <= b.getLayoutY() + b.getPrefHeight()) || yEnd >= b.getLayoutY() && yEnd <= b.getLayoutY() + b.getPrefHeight() || yStart <= b.getLayoutY() && yEnd >= b.getLayoutY() + b.getPrefHeight()) {
                countActivitiesInTimeslot++;
            }
        }
        return countActivitiesInTimeslot;
    }

    private DateFormatSymbols setLocaleToDanish() {
        Locale locale = new Locale("da", "DK");
        DateFormatSymbols dateFormatSymbols = new DateFormatSymbols(locale);
        dateFormatSymbols.setWeekdays(new String[]{"Unused", "Søndag", "Mandag", "Tirsdag", "Onsdag", "Torsdag", "Fredag", "Lørdag"});
        return dateFormatSymbols;
    }

    private void resetVisualDay() {
        eventButtonList.clear();
        activityPane.getChildren().clear();
    }

    private Calendar getCalendarWithSetTime(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c;
    }

    @Override
    protected void initialize() {

    }


}
