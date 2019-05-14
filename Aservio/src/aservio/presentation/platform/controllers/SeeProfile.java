package aservio.presentation.platform.controllers;

import aservio.domain.platform.user.User;
import aservio.domain.platform.user.UserInfo;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class SeeProfile implements Initializable {

    private static SeeProfile instance;

    @FXML
    private AnchorPane anchor;
    @FXML
    private ImageView imagePerson;
    @FXML
    private Label labelFirstName;
    @FXML
    private Label labelLastName;
    @FXML
    private Label labelAddress;
    @FXML
    private Label labelCity;
    @FXML
    private Label labelPostcode;
    @FXML
    private Label labelPhoneNumber;
    @FXML
    private Label labelMail;
    @FXML
    private Label labelInstitution;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showUser();
        instance = this;
    }

    public void showUser() {
        setUserInfo(User.getCurrentUser().getUserInfo());
    }

    public void showUser(User user) {
        setUserInfo(user.getUserInfo());
    }

    public void setUserInfo(UserInfo info) {
        if (info.getAddress() != null) {
            labelFirstName.setText(info.getFirstName());
            labelLastName.setText(info.getLastName());
            labelAddress.setText(info.getAddress().getRoad() + " " + info.getAddress().getHouseNumber() + ", " + info.getAddress().getLevel());
            labelCity.setText(info.getAddress().getCity());
            labelPostcode.setText(Integer.toString(info.getAddress().getPostcode()));
            labelPhoneNumber.setText(Integer.toString(info.getMobileNumber()));
            labelMail.setText(info.getMail());
            labelInstitution.setText(Integer.toString(info.getInstitution()));
        }
    }

    public static SeeProfile getInstance() {
        return instance;
    }

    @FXML
    public void handleOnAddUser(ActionEvent actionEvent) {
        AnchorPane profileView = ((AnchorPane)anchor.getParent());
        profileView.getChildren().clear();
        try {
            profileView.getChildren().add(FXMLLoader.load(getClass().getResource("/aservio/presentation/platform/views/AddProfile.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
