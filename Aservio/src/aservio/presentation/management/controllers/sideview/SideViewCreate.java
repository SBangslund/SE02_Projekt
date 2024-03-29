package aservio.presentation.management.controllers.sideview;

import aservio.domain.management.activities.Activity;
import aservio.domain.management.activities.ActivityType;
import aservio.domain.platform.user.User;
import aservio.domain.platform.user.UserInfo;
import aservio.presentation.PopupType;
import aservio.presentation.PresentationInterfaceManager;
import aservio.presentation.management.controllers.Management;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SideViewCreate extends SideView implements Initializable {

    public VBox vBoxContent;
    public Label addActivityLabel;
    public HBox titleHBox;
    public VBox addActivityMainVBox;
    ActivityType selectedActivityType;
    @FXML
    private TextField nameField;
    @FXML
    private Button activityConfirmButton;
    @FXML
    private Button activityCancelButton;
    @FXML
    private MenuButton typeMenu;
    @FXML
    private JFXTimePicker startTimePicker;
    @FXML
    private JFXTimePicker endTimePicker;
    @FXML
    private TextArea descriptionField;
    @FXML
    private JFXDatePicker startDatePicker;
    @FXML
    private JFXDatePicker endDatePicker;
    @FXML
    private VBox vboxList;
    private List<CheckBox> checkboxes;

    private Activity activityToBeEditet;

    private boolean edit = false;

    /**
     * If an activity is being edited, edit is set to true.
     * @param edit boolean
     */
    public void setEdit(boolean edit) {
        this.edit = edit;
    }

    /**
     * Sets up a MenuButton(dropdown menu) and assigns eventhandlers to the buttons.
     * The eventhandlers change the selectedActivityType variable when pressed (fx WALKING, RUNNING etc).
     * Also sets up a list of checkboxes depending on the amount of users the caretaker has access to.
     * @param location
     * @param resources 
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //CSS
        activityConfirmButton.getStyleClass().add("button_confirm");
        activityCancelButton.getStyleClass().add("button_cancel");
        nameField.getStyleClass().add("text-field_name");
        vBoxContent.getStyleClass().add("vbox_content");
        titleHBox.getStyleClass().add("hbox_title");
        addActivityLabel.getStyleClass().add("label_title");

        selectedActivityType = null;
        //Dropdown menu initialization.
        for (ActivityType a : ActivityType.values()) {
            MenuItem mi = new MenuItem(a.getName());
            typeMenu.getItems().add(mi);
            mi.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    selectedActivityType = a;
                    System.out.println("activitytype selected: " + a.getName());
                    typeMenu.setText(a.getName());
                }
            });
        }

        //VboxList
        checkboxes = new ArrayList();
        UserInfo currentUserInfo = interFace.getUserInfo(User.getCurrentUser().getId());
        CheckBox currentUserCb = new CheckBox(currentUserInfo.getFirstName() + " " + currentUserInfo.getLastName());
        currentUserCb.setUserData(currentUserInfo);
        vboxList.getChildren().add(currentUserCb);
        currentUserCb.setSelected(true);
        checkboxes.add(currentUserCb);

        for (UserInfo u : interFace.getCitizensFromCaretaker(User.getCurrentUser().getId())) {
            CheckBox cb = new CheckBox(u.getFirstName() + " " + u.getLastName());
            cb.setUserData(u);
            vboxList.getChildren().add(cb);
            checkboxes.add(cb);
        }
    }

    @Override
    protected void initialize() {
        addActivityMainVBox.setVisible(true);
        if (isEdit()) {
            addActivityLabel.setText("Rediger aktivitet");
            fillForm(activityToBeEditet);
        } else {
            resetForm();
        }
    }

    private void resetForm() {
        nameField.setText("");
        typeMenu.setText("Vælg");
        selectedActivityType = null;
        descriptionField.setText("");
        startDatePicker.setValue(LocalDate.now());
        startTimePicker.setValue(LocalTime.now());
    }

    /*public void updatePresetTimes() {
        startDatePicker.setValue(LocalDate.now());
        startTimePicker.setValue(LocalTime.now());
        endTimePicker.setValue(LocalTime.now());*/

    /**
     * When clicking the confirm button, if all values are set, 
     * the values will be turned into an activity object, which is then added to a specific User.
     * The view is updated and a message is displayed to the user to reflect changes.
     * @param event 
     */
    @FXML
    private void handleConfirmButton(ActionEvent event) {
        //Not 100% consistent. //needs a label to inform user to select other values.
        if (isFormFilled() && startTimePicker.getValue().compareTo(endTimePicker.getValue()) < 0 ) {
            System.out.println("Selected activity type is : " + selectedActivityType);
            //For every selected user add the activity
            for (CheckBox cb : checkboxes) {
                if (cb.isSelected()) {
                    UserInfo userInfo = (UserInfo) cb.getUserData();
                    //System.out.println("getUserdata: " + userinfo.getFirstName());

                    String startDateString = startDatePicker.getValue() + " " + startTimePicker.getValue().toString();
                    Date startDate = new Date();
                    try {
                        startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(startDateString);
                    } catch (ParseException ex) {
                        Logger.getLogger(SideViewCreate.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    String endDateString = startDatePicker.getValue() + " " + endTimePicker.getValue().toString();
                    Date endDate = new Date();
                    try {
                        endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(endDateString);
                    } catch (ParseException ex) {
                        Logger.getLogger(SideViewCreate.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    Activity activity = new Activity(nameField.getText(), selectedActivityType, startDate, endDate, UUID.randomUUID(), descriptionField.getText());
                    interFace.addActivity(activity, userInfo.getId());
                    if (isEdit()) {
                        interFace.deleteActivity(activityToBeEditet.getId());
                        PresentationInterfaceManager.createPopupWindow(PopupType.SUCCESS, "Aktiviteten er opdateret");
                    } else {
                        PresentationInterfaceManager.createPopupWindow(PopupType.SUCCESS, "Aktiviteten er blevet opresttet");
                    }
                }
            }
            Management.getInstance().getOverviewManager().updateCurrentView();
        } else if(startTimePicker.getValue().compareTo(endTimePicker.getValue()) >= 0){

            PresentationInterfaceManager.createPopupWindow(PopupType.FAILURE, "Der må ikke være negativ tid");
        } else{
            PresentationInterfaceManager.createPopupWindow(PopupType.FAILURE, "Alle felter skal være udfyldt");

        }
    }

    @FXML
    private void menuButtonOnSelect(ActionEvent event) {

    }

    /**
     * fillForm is called when the user clicks on edit activity. 
     * The filForm fills the values from the activity that will be edited. 
     * @param activity 
     */
    private void fillForm(Activity activity) {
        System.out.println(activity);
        this.nameField.setText(activity.getActivityName());
        System.out.println(activity.getActivityName());
        typeMenu.setText(activity.getActivityType().getName());
        selectedActivityType = activity.getActivityType();
        this.descriptionField.setText(activity.getDescription());
        System.out.println("Current description is: " + activity.getDescription());
        this.startDatePicker.setValue(Instant.ofEpochMilli(activity.getStartDate().getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate());
        this.endDatePicker.setValue(Instant.ofEpochMilli(activity.getEndDate().getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate());
        this.startTimePicker.setValue(activity.getStartDate().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalTime());
        this.endTimePicker.setValue(activity.getEndDate().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalTime());
    }

    @FXML
    private void datePickerOnAction(ActionEvent event) {
        //autoset værdier baseret på source
    }

    /**
     * This value is to the activity that is being edited. 
     * Then when the activity is done being edited, a new is created to take it's place,
     * and this reference can delete the old version.
     * @param activity 
     */
    public void setActivityToBeEditet(Activity activity) {
        this.activityToBeEditet = activity;
    }

    /**
     * Checks the value of edit.
     * 
     * @return 
     */
    public boolean isEdit() {
        return edit;
    }

    /**
     * isFormFilled returns true if alle values have been entered.
     * The method isn't very restrictive, so errors can currently happen.
     * @return 
     */
    private boolean isFormFilled() {
        return (!nameField.getText().isEmpty()
                && !descriptionField.getText().isEmpty()
                && startDatePicker.getValue() != null
                && startTimePicker.getValue() != null
                && endTimePicker.getValue() != null
                && selectedActivityType != null);
    }

    /**
     * Handler for the cancel button, when pressed, the form is reset and the window closed.
     * @param actionEvent 
     */
    public void cancelButtonHandler(ActionEvent actionEvent) {
        resetForm();
        addActivityMainVBox.setVisible(false);

    }
}
