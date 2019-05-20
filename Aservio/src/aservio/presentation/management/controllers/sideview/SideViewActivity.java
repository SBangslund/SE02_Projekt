package aservio.presentation.management.controllers.sideview;

import aservio.domain.management.activities.Activity;
import aservio.domain.management.activities.ActivityList;
import aservio.domain.management.activities.ActivityType;
import aservio.domain.platform.user.User;
import aservio.presentation.PopupType;
import aservio.presentation.PresentationInterfaceManager;
import aservio.presentation.management.controllers.Management;
import aservio.presentation.platform.interfaces.PermissionLimited;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
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

    /**
     * Here the SideView child FXMLActivityCreate is loaded. Activity create
     * edit and delete buttons are added to a toggle group.
     */
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
    /**
     * When a given Activity is clicked, the values are used in this method, to
     * set the UI up so the User can see what the Activity contains.
     *
     * @param activity
     */
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
        SimpleDateFormat simpleDateFormat
                = new SimpleDateFormat(pattern, setWeekDaysToDanish());
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

    /**
     * Sets Date to danish standards.
     *
     * @return
     */
    public DateFormatSymbols setWeekDaysToDanish() {
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
            "Lørdag",});
        return dateFormatSymbols;
    }

    /**
     * When an activity is created, a pane is created, taking the values of the
     * ActivityType that the Activity has.
     *
     * @param activityType
     * @return Pane
     */
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

    /**
     * When the add Activity Button is clicked, depending on the state (of 3
     * states), sideview hides, appears, or changes. Details are within the
     * method.
     *
     * @param actionEvent
     */
    @FXML
    public void handleAdd(ActionEvent actionEvent) {
        //Open form not in add. Enters add form.
        if (sideViewCreate.isEdit() && addActivityPane.isVisible()) {
            //reset
            sideViewCreate.setEdit(false);
            sideViewCreate.initialize();

        } //Closed form. Enters add form.
        else if (!sideViewCreate.isEdit() && !addActivityPane.isVisible()) {
            //reset
            addActivityPane.setVisible(true);
            sideViewCreate.initialize();

        } //Open form, is adding. Closes form.
        else if (!sideViewCreate.isEdit() && addActivityPane.isVisible()) {
            //close
            addActivityPane.setVisible(false);
        }
    }

    /**
     * When the modify Activity Button is clicked, depending on the state (of 3
     * states), SideView hides, appears, or changes. Details are within the
     * method.
     *
     * @param actionEvent
     */
    @FXML
    public void handleModify(ActionEvent actionEvent
    ) {
        if (selectedActivity != null) {
            //open form, not in modify. Enters modify form.
            if (!sideViewCreate.isEdit() && addActivityPane.isVisible()) {
                sideViewCreate.setEdit(true);
                sideViewCreate.setActivityToBeEditet(selectedActivity);
                sideViewCreate.initialize();
            } else if (sideViewCreate.isEdit() && addActivityPane.isVisible()) {
                //open form, in modify. Closes form.
                addActivityPane.setVisible(false);
                sideViewCreate.setEdit(false);
            } else if (!sideViewCreate.isEdit() && !addActivityPane.isVisible()) {
                //closed form. Enters modify form.
                addActivityPane.setVisible(true);
                sideViewCreate.setEdit(true);
                sideViewCreate.setActivityToBeEditet(selectedActivity);
                sideViewCreate.initialize();
            }
        }
    }

    /**
     * When the remove Activity Button is clicked, if an Activity is selected,
     * it will be deleted from the database, and updated in the view. A popup
     * windows will appear to display that it has been deleted.
     *
     * @param actionEvent
     */
    @FXML
    public void handleRemove(ActionEvent actionEvent
    ) {
        //delete selected activity this view (overview)
        activityName.setText(selectedActivity.getActivityType().getName() + " (Deleted)");
        //delete selected activity (repository)
        interFace.deleteActivity(selectedActivity.getId());
        PresentationInterfaceManager.createPopupWindow(PopupType.SUCCESS, "Aktiviteten " + selectedActivity.getActivityName() + " blev slettet.");
        updateView();
    }

    /**
     * updateView is a reference that updates the calendar (planlægning) when
     * called.
     */
    private void updateView() {
        Management.getInstance().getOverviewManager().updateCurrentView();
    }

    @Override
    public void applyPermissionLimitations() {
        toolbarAddRemove.setVisible(DEFAULT_PERMISSIONS.canEditActivities());
    }
}
