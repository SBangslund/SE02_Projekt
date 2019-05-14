package aservio.presentation.platform.controllers;

import aservio.domain.platform.user.Address;
import aservio.domain.platform.user.User;
import aservio.domain.platform.user.UserInfo;
import aservio.domain.platform.user.roles.*;
import aservio.presentation.PresentationInterfaceManager;
import aservio.presentation.platform.interfaces.contracts.IAddProfile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;

public class AddProfile implements Initializable {
    private IAddProfile interFace = PresentationInterfaceManager.getIAddProfile();

    @FXML
    private AnchorPane anchor;
    @FXML
    private TextField fieldAddressName;
    @FXML
    private TextField fieldAddressNumber;
    @FXML
    private TextField fieldAddressLevel;
    @FXML
    private TextField fieldInstitution;
    @FXML
    private TextField fieldUsername;
    @FXML
    private TextField fieldPassword;
    @FXML
    private TextField fieldFirstname;
    @FXML
    private TextField fieldLastname;
    @FXML
    private TextField fieldAddress;
    @FXML
    private TextField fieldCity;
    @FXML
    private TextField fieldPostalCode;
    @FXML
    private TextField fieldPhone;
    @FXML
    private TextField fieldMail;
    @FXML
    private SplitMenuButton menuRoles;

    private Role selectedRole;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void handleOnAddImage(ActionEvent actionEvent) {
    }

    @FXML
    public void handleOnCitizen(ActionEvent actionEvent) {
        selectedRole = new Citizen();
        menuRoles.setText("Borger");
    }

    @FXML
    public void handleOnCaretaker(ActionEvent actionEvent) {
        selectedRole = new Caretaker();
        menuRoles.setText("Personale");
    }

    @FXML
    public void handleOnRelative(ActionEvent actionEvent) {
        selectedRole = new Relative();
        menuRoles.setText("Pårørende");
    }

    @FXML
    public void handleOnAdmin(ActionEvent actionEvent) {
        selectedRole = new Admin();
        menuRoles.setText("SysAdmin");
    }

    @FXML
    public void handleOnCreateUser(ActionEvent actionEvent) {
        createUser();

        AnchorPane profileView = ((AnchorPane) anchor.getParent());
        profileView.getChildren().clear();
        try {
            profileView.getChildren().add(FXMLLoader.load(getClass().getResource("/aservio/presentation/platform/views/SeeProfile.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createUser() {
        UUID userid = UUID.randomUUID();

        Address address = new Address(
                fieldAddressName.getText(),
                "Danmark",
                Integer.parseInt(fieldPostalCode.getText()),
                fieldCity.getText(),
                fieldAddressNumber.getText(),
                fieldAddressLevel.getText(),
                userid
        );

        UserInfo userInfo = new UserInfo(
                address,
                null,
                Integer.parseInt(fieldPhone.getText()),
                fieldFirstname.getText(),
                fieldLastname.getText(),
                fieldMail.getText(),
                selectedRole,
                Integer.parseInt(fieldInstitution.getText()),
                userid
        );

        User user = new User(
                fieldUsername.getText(),
                fieldPassword.getText(),
                userid,
                userInfo
        );

        interFace.addUser(user);
    }
}
