package aservio.presentation.management.controllers.sideview;

import aservio.domain.management.activities.Activity;
import aservio.domain.management.activities.ActivityType;
import aservio.domain.platform.user.User;
import aservio.domain.platform.user.UserInfo;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxListCell;

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
    private ListView<UserInfo> userListView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Dropdown menu initialization.
        selectedUsers = new ArrayList();

        for (ActivityType a : ActivityType.values()) {
            typeDropDown.getItems().add(new MenuItem(a.getName()));
        }
        
        
        
        /*
        ObservableList<UserInfo> listItems = FXCollections.observableArrayList();
        for (UserInfo u : interFace.getCitizensFromCaretaker(User.getCurrentUser().getId())) {
            listItems.add(u);
            System.out.println("u is: " + u);
        }

        userListView.setItems(listItems);

        userListView.setCellFactory(CheckBoxListCell.forListView((String item) -> {
            BooleanProperty observable = new SimpleBooleanProperty();
            observable.addListener((obs, wasSelected, isNowSelected)
                    -> System.out.println("Check box for " + item + " changed from " + wasSelected + " to " + isNowSelected)
            );
            return observable;
        }));*/
    }

    @FXML
    private void handleActionButton(ActionEvent event) {
        //Not 100% consistent.
        //if (!(nameField.getText().isEmpty() && DatePicker.getValue() == null && startTimePicker.getValue() == null && typeDropDown.isShowing() == "Vælg" && endTimePicker.getValue() == null && descriptionField.getText().isEmpty())) {

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
        System.out.println("Startdate: " + startDate.toString());
        System.out.println("Enddate: " + endDate.toString());

        UUID userid = User.getCurrentUser().getId();
        Activity activity = new Activity(ActivityType.WALK/*ValueOf*/, startDate, endDate, UUID.randomUUID());
        interFace.addActivity(activity, userid);

    }

    @FXML
    private void menuButtonOnSelect(ActionEvent event) {

    }

    @FXML
    private void datePickerOnAction(ActionEvent event) {
        //autoset værdier baseret på source
    }

    @Override
    protected void initialize() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
