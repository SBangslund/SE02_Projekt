package aservio.presentation.management.controllers.sideview;

import aservio.domain.management.activities.Activity;
import aservio.domain.management.activities.ActivityList;
import aservio.domain.management.activities.ActivityType;
import aservio.domain.platform.user.User;
import aservio.presentation.management.controllers.Management;
import aservio.presentation.platform.interfaces.PermissionLimited;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

public class SideViewActivity extends SideView implements Initializable, PermissionLimited {

    public HBox toolbarAddRemove;
    public AnchorPane mainPane;
    public VBox vBoxTime;
    private Map<User, ActivityList> userActivities = new HashMap<>();
    public ToggleButton addButton;
    public ToggleButton modifyButton;
    public ToggleButton removeButton;
    Pane addActivityPane;
    @FXML
    private HBox activityBox;
    @FXML
    private Label timeStartText;
    @FXML
    private Label timeEndText;
    @FXML
    private Label activityName;
    @FXML
    private Label activityDescription;
    @FXML
    private Label dateText;

    private Activity selectedActivity;
    @FXML
    private VBox sideViewVbox;

    private SideViewCreate sideViewCreate;
    @FXML
    private Pane activityLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private Label timeStartLabel;
    @FXML
    private Label timeEndLabel;

    ToggleGroup addRemoveButtonGroup;

    @Override
    protected void initialize() {

        addButton.setText("Tilføj");
        addButton.getStyleClass().add("toggle-button_add");
        modifyButton.setText("Rediger");
        modifyButton.getStyleClass().add("toggle-button_modify");
        removeButton.setText("Slet");
        removeButton.getStyleClass().add("toggle-button_remove");

        addRemoveButtonGroup = new ToggleGroup();
        addButton.setToggleGroup(addRemoveButtonGroup);
        modifyButton.setToggleGroup(addRemoveButtonGroup);
        removeButton.setToggleGroup(addRemoveButtonGroup);

        mainPane.getStyleClass().add("pane_main");

        //Add activity button
        try {
            FXMLLoader loader = new FXMLLoader();
            addActivityPane = loader.load(getClass().getResource("/aservio/presentation/management/views/FXMLActivityCreate.fxml").openStream());
            sideViewCreate = loader.getController();
        } catch (IOException ex) {
            Logger.getLogger(SideViewActivity.class.getName()).log(Level.SEVERE, null, ex);
        }
        sideViewVbox.getChildren().add(addActivityPane);
        addActivityPane.setVisible(false);
//        setButtonImage(modifyButton, "resources/generalIcons/modifyIcon.png", "button_modify");
//        setButtonImage(addButton, "resources/generalIcons/addIcon.png", "button_add");
//        setButtonImage(removeButton, "resources/generalIcons/removeIcon.png", "button_remove");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.applyPermissionLimitations();
    }

    //could be implementend, in case we want icons as buttons, instead of text
//    public void setButtonImage(Button button, String path, String style){
//        File modify = new File(path);
//        ImageView modifyImage = new ImageView(new Image(modify.toURI().toString()));
//        modifyImage.setFitHeight(20);
//        modifyImage.setFitWidth(20);
//        modifyImage.setPreserveRatio(true);
//        button.setGraphic(modifyImage);
//        button.getStylesheets().add(style);
//    }


    public void showActivity(Activity activity) {
        if (activityBox.getChildren().size() > 2) {
            activityBox.getChildren().remove(2);
        }

        selectedActivity = activity;
        activityName.setText(activity.getActivityName());
        activityBox.getChildren().add(createActivityLabel(activity.getActivityType()));
        activityDescription.setText(activity.getDescription());
        activityDescription.setWrapText(true);
        activityDescription.setTextAlignment(TextAlignment.JUSTIFY);

        String pattern = "EEEEE, dd. MMMMM";
        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat(pattern, setWeekDaysToDanish());
        String dateString = simpleDateFormat.format(activity.getStartDate());
        dateText.setText(dateString);

        SimpleDateFormat timeStartFormat = new SimpleDateFormat("HH:mm");
        String timeStartString = timeStartFormat.format(activity.getStartDate());
        timeStartText.setText(timeStartString);
        timeStartText.getStyleClass().add("label_time");
        SimpleDateFormat timeEndFormat = new SimpleDateFormat("HH:mm");
        String timeEndString = timeEndFormat.format(activity.getEndDate());
        timeEndText.setText(timeEndString);
        timeEndText.getStyleClass().add("label_time");
    }

    public DateFormatSymbols setWeekDaysToDanish(){
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
                "Lørdag",
        });
        return dateFormatSymbols;
    }

    private Pane createActivityLabel(ActivityType activityType) {
        HBox box = new HBox();
        Pane pane = new Pane();
        pane.setPrefWidth(10);
        String defaultColor = String.format("#%02X%02X%02X",
                (int) (activityType.getColor().getRed() * 255),
                (int) (activityType.getColor().getGreen() * 255),
                (int) (activityType.getColor().getBlue() * 255));
        pane.setStyle("-fx-background-color: " + defaultColor);

        ImageView icon = new ImageView(activityType.getIcon());
        icon.setPreserveRatio(true);
        icon.setFitHeight(50);

        box.getChildren().add(icon);
        box.getChildren().add(pane);
        return box;
    }

    @FXML
    public void handleAdd(ActionEvent actionEvent) {
        //Open form not adding (editing)
        if (sideViewCreate.isEdit() && addActivityPane.isVisible()) {
            //reset
            sideViewCreate.setEdit(false);
            sideViewCreate.initialize();
        } //Closed form
        else if (!sideViewCreate.isEdit() && !addActivityPane.isVisible()) {
            //reset
            addActivityPane.setVisible(true);
            sideViewCreate.initialize();
        } //Open form already adding
        else if (!sideViewCreate.isEdit() && addActivityPane.isVisible()) {
            //close
            addActivityPane.setVisible(false);
        }
    }

    @FXML
    public void handleModify(ActionEvent actionEvent
    ) {
        if (selectedActivity != null) {
            if (!sideViewCreate.isEdit() && addActivityPane.isVisible()) { //goes into modify cause Addactivity form was visible, but not in modify
                sideViewCreate.setEdit(true);
                sideViewCreate.setActivityToBeEditet(selectedActivity);
                sideViewCreate.initialize();
            } else if (sideViewCreate.isEdit() && addActivityPane.isVisible()) { //closes form cause already open (modifying)
                addActivityPane.setVisible(false);
                sideViewCreate.setEdit(false);
            } else if (!sideViewCreate.isEdit() && !addActivityPane.isVisible()) { //opens cause form is closed, not editing or adding.
                addActivityPane.setVisible(true);
                sideViewCreate.setEdit(true);
                sideViewCreate.setActivityToBeEditet(selectedActivity);
                sideViewCreate.initialize();
            }
        }
    }

    @FXML
    public void handleRemove(ActionEvent actionEvent
    ) {
        //delete selected activity this view (overview)
        activityName.setText(selectedActivity.getActivityType().getName() + " (Deleted)");
        //delete selected activity (repository)
        interFace.deleteActivity(selectedActivity.getId());
        updateView();
    }

    private void updateView() {
        Management.getInstance().getOverviewManager().updateCurrentView();
    }

    @Override
    public void applyPermissionLimitations() {
        toolbarAddRemove.setVisible(DEFAULT_PERMISSIONS.canEditActivities());
    }
}
