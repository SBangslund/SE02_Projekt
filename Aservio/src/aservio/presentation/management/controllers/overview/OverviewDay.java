package aservio.presentation.management.controllers.overview;


import aservio.domain.management.activities.Activity;
import aservio.domain.management.activities.ActivityList;
import aservio.domain.platform.user.UserInfo;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import java.net.URL;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class OverviewDay extends Overview implements Initializable {

    @FXML
    public ScrollPane scrollPane;
    public Label DayOfWeekLabel;
    public Label moreInformationLabel;
    public Pane activityPane;
    public VBox backgroundVbox;
    public StackPane stackPane;

    private Date currentDate;

    private ActivityList activityList;

    private ArrayList<Button> eventButtonList;

    private int backgroundHeight = 1440;
    private HBox visualHour;

    public OverviewDay() {
        this(new Date());
    }

    public OverviewDay(Date date) {
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

    private void showLineAtCurrentTime(Date date) {
        int hour = getCalendarWithSetTime(date).get(Calendar.HOUR_OF_DAY);
        int min = getCalendarWithSetTime(date).get(Calendar.MINUTE);
        int minPerDay = 1440;

        int timeShift = ((backgroundHeight / 24) / 2);

        int currentY = timeShift + (((hour * 60) + min) * (backgroundHeight)) / minPerDay;
        Line line = new Line(30, currentY, 1200, currentY);
        line.setStroke(Color.RED);
        line.setStrokeWidth(2);
        Circle c = new Circle(30, currentY, 5, Color.RED);

        activityPane.getChildren().add(line);
        activityPane.getChildren().add(c);
        activityPane.setStyle("-fx-background-color: transparent;");
    }

    private void setBackgroundImage() {
        backgroundVbox.getChildren().clear();
        for (int i = 0; i < 24; i++) {
            HBox visualHour = new HBox();
            //System.out.println(stackPane.getWidth());
            //System.out.println(stackPane.getHeight());
            visualHour.prefWidthProperty().bind(stackPane.widthProperty());
            visualHour.setPrefHeight(backgroundHeight / 24);
            visualHour.setMinHeight(backgroundHeight / 24);
            visualHour.setMaxHeight(backgroundHeight / 24);
            Label time = new Label(i < 10 ? "0" + i : Integer.toString(i));
            visualHour.setAlignment(Pos.CENTER_LEFT);
            time.setMinWidth(30);

            int linePos = (backgroundHeight / 24) / 2;
            Line line = new Line(linePos, linePos, stackPane.widthProperty().get() - linePos, linePos);
            line.setStrokeWidth(0.5);
            visualHour.getChildren().add(time);
            visualHour.getChildren().add(line);

            backgroundVbox.getChildren().add(visualHour);
            backgroundVbox.setStyle("-fx-background-color: white;");
        }

    }


    private void showActivity(Activity activity) {
        int standartButtonWidth = 170;

        Button eventButton = createRightSizedButton(activity, standartButtonWidth);
        HBox buttonContent = new HBox(10);
        buttonContent.getStyleClass().add("hbox");
        buttonContent.setPadding(new Insets(5, 0, 0, 5));
        String color = String.format("#%02X%02X%02X",
                (int) (activity.getActivityType().getColor().getRed() * 255),
                (int) (activity.getActivityType().getColor().getGreen() * 255),
                (int) (activity.getActivityType().getColor().getBlue() * 255));

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
        eventButton.setStyle("-fx-background-color: linear-gradient(from 0px 0px to 10px 10px, " + color + " 99%, white);");
        AtomicInteger colorWidth = new AtomicInteger(10);

        eventButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                new AnimationTimer() {
                    @Override
                    public void handle(long now) {


                        if (colorWidth.get() >= 0) {
                            eventButton.setStyle("-fx-background-color: linear-gradient(from 0px 0px to 10px" + colorWidth + "px, " + color + " 99%, white);");
                            colorWidth.decrementAndGet();
                        } else if(colorWidth.get() < 0){
                            this.stop();
                        }
                    }
                }.start();
            }
        });

        eventButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                new AnimationTimer() {
                    @Override
                    public void handle(long now) {
                        if (colorWidth.get() <= 10) {
                            eventButton.setStyle("-fx-background-color: linear-gradient(from 0px 0px to 10px " + colorWidth + "px, " + color + " 99%, white);");
                            colorWidth.addAndGet(1);
                        } else {
                            this.stop();
                        }
                    }
                }.start();
            }
        });
        eventButton.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        OverviewDay.super.uponClick(activity);
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

        int leftBackgroundPadding = 50;
        int minPerDay = 1440;
        int timeShift = ((backgroundHeight / 24) / 2);

        int yStartPosition = timeShift + (((startHour * 60) + startMin) * (backgroundHeight)) / minPerDay;
        int yEndPosition = timeShift + (((endHour * 60) + endMin) * (backgroundHeight)) / minPerDay;
        int xStartPosition = (standartButtonWidth * getColumnNotUsed(yStartPosition, yEndPosition)) + leftBackgroundPadding;

        Button eventButton = new Button();
        eventButton.setLayoutX(xStartPosition);
        eventButton.setLayoutY(yStartPosition);
        eventButton.setPrefHeight(yEndPosition - yStartPosition);
        eventButton.setMaxWidth(standartButtonWidth);

        return eventButton;
    }

    private Scene createActivityPopUp(Activity activity) {
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
        int countActivitiesInTimeslot = 0;
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
        stackPane.widthProperty().addListener((obs, oldValue, newValue) -> {
            setBackgroundImage();
        });
        stackPane.heightProperty().addListener((obs, oldValue, newValue) -> {
            setBackgroundImage();
        });

    }

    @Override
    protected void handleSelectedUsersChanged(List<UserInfo> userInfoList) {
        // TODO needs implementing
    }
}