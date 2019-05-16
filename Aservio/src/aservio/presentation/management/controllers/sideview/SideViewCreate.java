package aservio.presentation.management.controllers.sideview;

import aservio.domain.management.activities.Activity;
import aservio.domain.management.activities.ActivityType;
import aservio.domain.platform.user.User;
import aservio.domain.platform.user.UserInfo;
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
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class SideViewCreate extends SideView implements Initializable {

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

    public void setEdit(boolean edit) {
        this.edit = edit;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

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
        if (isEdit()) {
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
        endDatePicker.setValue(LocalDate.now());
    }

    public void updatePresetTimes() {
        startDatePicker.setValue(LocalDate.now());
        startTimePicker.setValue(LocalTime.now());
        endTimePicker.setValue(LocalTime.now());
    }

    @FXML
    private void handleActionButton(ActionEvent event) {
        //Not 100% consistent. //needs a label to inform user to select other values.
        if (isFormFilled()) {

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

                    String endDateString = endDatePicker.getValue() + " " + endTimePicker.getValue().toString();
                    Date endDate = new Date();
                    try {
                        endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(endDateString);
                    } catch (ParseException ex) {
                        Logger.getLogger(SideViewCreate.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    //System.out.println("Startdate: " + startDate.toString());
                    //System.out.println("Enddate: " + endDate.toString());

                    Activity activity = new Activity(nameField.getText(), selectedActivityType, startDate, endDate, UUID.randomUUID(), descriptionField.getText());
                    interFace.addActivity(activity, userInfo.getId());
                    if (isEdit()) {
                        interFace.deleteActivity(activityToBeEditet.getId());
                    }
                }
            }
            Management.getInstance().getOverviewManager().updateCurrentView();
        }
    }

    @FXML
    private void menuButtonOnSelect(ActionEvent event) {

    }

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

    public void setActivityToBeEditet(Activity activity) {
        this.activityToBeEditet = activity;
    }

    public boolean isEdit() {
        return edit;
    }

    private boolean isFormFilled() {
        return (
                !nameField.getText().isEmpty()
                && !descriptionField.getText().isEmpty()
                && startDatePicker.getValue() != null
                && endDatePicker.getValue() != null
                && startTimePicker.getValue() != null
                && endTimePicker.getValue() != null
                && selectedActivityType != null);
    }

}
