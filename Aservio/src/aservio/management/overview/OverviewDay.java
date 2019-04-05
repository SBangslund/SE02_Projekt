package aservio.management.overview;


import aservio.management.Management;
import aservio.management.activities.Activity;
import aservio.management.activities.ActivityList;
import aservio.management.activities.ActivityType;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
    private ArrayList<Pane> hourPanes;

    private ActivityList activityList;

    private ArrayList<Button> eventButtonList;

    public OverviewDay() {
        this(new Date());
    }

    public OverviewDay(Date date) {
        super(date);
        this.currentDate = date;
        eventButtonList = new ArrayList<>();
        hourPanes = new ArrayList<>();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        scrollPane.setFitToWidth(true);
    }

    private void createBackgroundGrid() {
        File file = new File("src/aservio/management/icons/OverViewDayTimeTable.png");
        Image image = new Image(file.toURI().toString());
        ImageView backgroundImage = new ImageView(image);
        backgroundImage.setFitHeight(1200);
        backgroundImage.setPreserveRatio(true);
        activityPane.getChildren().add(backgroundImage);
    }

    private void showActivity(Activity activity) {
        int standartButtonWidth = 100;
        Button eventButton = createRightSizedButton(activity, standartButtonWidth);

        VBox buttonContent = new VBox();
        buttonContent.setPrefWidth(standartButtonWidth);

        ImageView imageView = new ImageView(activity.getActivityType().getIcon());
        imageView.setFitWidth(standartButtonWidth);
        imageView.setPreserveRatio(true);

        Text buttonContentText = new Text(String.format("%s\n%s", activity.getActivityType().getName(), activity.getTimeSlotString()));
        buttonContentText.getStyleClass().add("text_button");

        buttonContent.getChildren().add(buttonContentText);
        buttonContent.getChildren().add(imageView);
        eventButton.setGraphic(buttonContent);
        eventButton.setUserData(activity);

        eventButton.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        final Stage dialog = new Stage();
                        dialog.initModality(Modality.APPLICATION_MODAL);
                        dialog.initOwner(activityPane.getScene().getWindow());
                        VBox dialogVbox = new VBox(20);
                        ImageView imageView = new ImageView(activity.getActivityType().getIcon());
                        imageView.setFitWidth(standartButtonWidth);
                        imageView.setPreserveRatio(true);

                        Text buttonContentText = new Text(String.format("%s\n%s", activity.getActivityType().getName(), activity.getTimeSlotString()));
                        buttonContentText.getStyleClass().add("text_button");
                        dialogVbox.getChildren().add(buttonContentText);
                        dialogVbox.getChildren().add(imageView);
                        Scene dialogScene = new Scene(dialogVbox, 300, 200);
                        dialog.setScene(dialogScene);
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

        int yStartPosition = 30 + (((startHour * 60) + startMin) * 1200) / 1440;
        int yEndPosition = 30 + (((endHour * 60) + endMin) * 1200) / 1440;
        int xStartPosition = spaceUsed(yStartPosition, yEndPosition) == 1 ? standartButtonWidth : standartButtonWidth * spaceUsed(yStartPosition, yEndPosition);

        Button eventButton = new Button();
        eventButton.setLayoutX(xStartPosition);
        eventButton.setLayoutY(yStartPosition);
        eventButton.setPrefHeight(yEndPosition - yStartPosition);
        eventButton.setMaxWidth(standartButtonWidth);

        return eventButton;
    }

    private Calendar getCalendarWithSetTime(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c;
    }

    @Override
    protected void initialize() {

    }

    private int spaceUsed(double yStart, double yEnd) {
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

    private void createVisualDay(ActivityList activities, Date date) {
        createBackgroundGrid();
        DayOfWeekLabel.setText(String.format("%s", new SimpleDateFormat("EEEEE", setLocaleToDanish()).format(date.getTime())));
        moreInformationLabel.setText(String.format("den %s", new SimpleDateFormat("dd/MM/yyyy", setLocaleToDanish()).format(date.getTime())));
        List<Activity> activitiesForCurrentDay = activities.getActivities().stream().filter(e -> isActivityOnDay(e, date)).collect(Collectors.toList());

        activitiesForCurrentDay.stream().forEach(e -> showActivity(e));
    }

    private boolean isActivityOnDay(Activity activity, Date date) {
        return new SimpleDateFormat("dd.MM.yyyy", setLocaleToDanish()).format(activity.getStartDate().getTime())
                .equals(new SimpleDateFormat("dd.MM.yyyy", setLocaleToDanish()).format(date.getTime()));
    }

    private void resetVisualDay() {
        eventButtonList.clear();
        activityPane.getChildren().clear();

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

    @Override
    public void showActivities(ActivityList activities) {
        resetVisualDay();
        this.activityList = activities;
        createVisualDay(activityList, currentDate);


    }
}
