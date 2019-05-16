package aservio.presentation.management.controllers.sideview;

import aservio.domain.management.activities.Activity;
import aservio.domain.management.activities.ActivityType;
import aservio.domain.platform.user.User;
import aservio.domain.platform.user.UserInfo;
import aservio.presentation.management.controllers.Management;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SideViewCreate extends SideView implements Initializable {

    List<UserInfo> selectedUsers;
    @FXML
    private Button activityConfirmButton;
    @FXML
    private Button activityCancelButton;
    @FXML
    private TextField nameField;
    @FXML
    private MenuButton typeDropDown;
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


        //Dropdown menu initialization.
        selectedUsers = new ArrayList();

        for (ActivityType a : ActivityType.values()) {
            typeDropDown.getItems().add(new MenuItem(a.getName()));
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

    @FXML
    private void handleActionButton(ActionEvent event) {
        //Not 100% consistent. //needs a label to inform user to select other values.
        if (!(nameField.getText().isEmpty()
                && startDatePicker.getValue() == null
                && endDatePicker.getValue() == null
                && startTimePicker.getValue() == null
                && endTimePicker.getValue() == null
                && descriptionField.getText().isEmpty() /*&& typeDropDown.get == "Vælg"*/)) {

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

                    Activity activity = new Activity(nameField.getText(), ActivityType.WALK/*ValueOf*/, startDate, endDate, UUID.randomUUID(), descriptionField.getText());
                    interFace.addActivity(activity, userInfo.getId());
                    if(isEdit()){
                        interFace.deleteActivity(activityToBeEditet.getId());
                    }
                }
            }
        }
        Management.getInstance().getOverviewManager().updateCurrentView();
    }

    @FXML
    private void menuButtonOnSelect(ActionEvent event) {

    }

    private void replaceActivity() {

    }

    private void fillForm(Activity activity) {
        System.out.println(activity);
        this.nameField.setText(activity.getActivityName());
        System.out.println(activity.getActivityName());
        this.descriptionField.setText(activity.getDescription());
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

    @Override
    protected void initialize() {
        if (this.isEdit()) {
            this.fillForm(activityToBeEditet);
        }
    }

    public void setActivityToBeEditet(Activity activity) {
        this.activityToBeEditet = activity;
    }

    public boolean isEdit(){
        return edit;
    }

}
