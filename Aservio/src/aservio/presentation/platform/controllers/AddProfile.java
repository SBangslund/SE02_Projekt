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
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import javax.swing.event.ChangeListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.stream.Stream;

public class AddProfile implements Initializable {

    private IAddProfile interFace = PresentationInterfaceManager.getIAddProfile();

    @FXML
    private ImageView iconCity;
    @FXML
    private ImageView iconAddress;
    @FXML
    private ImageView iconPostal;
    @FXML
    private ImageView iconPhone;
    @FXML
    private ImageView iconMail;
    @FXML
    private ImageView iconInstitution;
    @FXML
    private ImageView iconUsername;
    @FXML
    private ImageView iconPassword;
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
    private TextField fieldCity;
    @FXML
    private TextField fieldPostalCode;
    @FXML
    private TextField fieldPhone;
    @FXML
    private TextField fieldMail;
    @FXML
    private SplitMenuButton menuRoles;

    private boolean
            approvedAddress,
            approvedCity,
            approvedPostal,
            approvedPhone,
            approvedMail,
            approvedInstitution,
            approvedUsername,
            approvedPassword;

    private Role selectedRole;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Tooltip addressTip = new Tooltip("Alle felter skal være udfyldt.");
        Tooltip postalTip = new Tooltip("Postnummeret skal være et tal.");
        Tooltip phoneTip = new Tooltip("Telefonnummeret kan kun været et tal.");
        Tooltip mailTip = new Tooltip("Skal være en valid mail.");
        Tooltip institutionTip = new Tooltip("ID'et skal være et tal.");
        Tooltip usernameTip = new Tooltip("Brugernavnet er taget");
        Tooltip passwordTip = new Tooltip("Invalide symboler i password.");

        Tooltip.install(iconAddress, addressTip);
        Tooltip.install(iconPostal, postalTip);
        Tooltip.install(iconPhone, phoneTip);
        Tooltip.install(iconMail, mailTip);
        Tooltip.install(iconInstitution, institutionTip);
        Tooltip.install(iconUsername, usernameTip);
        Tooltip.install(iconPassword, passwordTip);

        // Set event listeners.
        fieldAddressName.textProperty().addListener((observable, oldText, newText) -> handleOnAddressChange(newText));
        fieldAddressLevel.textProperty().addListener((observable, oldText, newText) -> handleOnAddressChange(newText));
        fieldAddressNumber.textProperty().addListener((observable, oldText, newText) -> handleOnAddressChange(newText));
        fieldCity.textProperty().addListener((observable, oldText, newText) -> handleOnCityChange(newText));
        fieldPostalCode.textProperty().addListener((observable, oldText, newText) -> handleOnPostalChange(newText));
        fieldPhone.textProperty().addListener((observable, oldText, newText) -> handleOnPhoneChange(newText));
        fieldMail.textProperty().addListener((observable, oldText, newText) -> handleOnMailChange(newText));
        fieldInstitution.textProperty().addListener((observable, oldText, newText) -> handleOnInstitutionChange(newText));
        fieldUsername.textProperty().addListener((observable, oldText, newText) -> handleOnUsernameChange(newText));
        fieldPassword.textProperty().addListener((observable, oldText, newText) -> handleOnPasswordChange(newText));
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

    private void handleOnAddressChange(String newText) {
        approvedAddress = !fieldAddressLevel.getText().isEmpty() && !fieldAddressNumber.getText().isEmpty() && !fieldAddressName.getText().isEmpty();
        setImage(iconAddress, approvedAddress);
    }

    private void handleOnCityChange(String newText) {
        approvedCity = !isInteger(newText) && newText.length() > 0;
        setImage(iconCity, approvedCity);
    }

    private void handleOnPostalChange(String newText) {
        approvedPostal = isInteger(newText) && newText.length() > 0;
        setImage(iconPostal, approvedPostal);
    }

    private void handleOnPhoneChange(String newText) {
        approvedPhone = isInteger(newText) && newText.length() > 0;
        setImage(iconPhone, approvedPhone);
    }

    private void handleOnMailChange(String newText) {
        approvedMail = newText.contains("@") && newText.contains(".");
        setImage(iconMail, approvedMail);
    }

    private void handleOnInstitutionChange(String newText) {
        approvedInstitution = isInteger(newText);
        setImage(iconInstitution, approvedInstitution);
    }

    private void handleOnUsernameChange(String newText) {
        approvedUsername = true; // TODO Set this..
        setImage(iconUsername, approvedUsername);
    }

    private void handleOnPasswordChange(String newText) {
        approvedPassword = true; // TODO Set this..
        setImage(iconPassword, approvedPassword);
    }

    @FXML
    public void handleOnCreateUser(ActionEvent actionEvent) {
        if(isAllApproved()) {
            createUser();

            AnchorPane profileView = ((AnchorPane) anchor.getParent());
            profileView.getChildren().clear();
            Profile.getInstance().showAllUsers();
            try {
                profileView.getChildren().add(FXMLLoader.load(getClass().getResource("/aservio/presentation/platform/views/SeeProfile.fxml")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isAllApproved() {
        return approvedAddress
                && approvedCity
                && approvedPostal
                && approvedPhone
                && approvedMail
                && approvedInstitution
                && approvedUsername
                && approvedPassword
                && selectedRole != null;
    }

    private boolean isInteger(String text) {
        for (char c : text.toCharArray()) {
            if (c < '0' || c > '9')
                return false;
        }
        return true;
    }

    private void setImage(ImageView imageView, boolean approved) {
        imageView.setImage(new Image(new File((approved ? Icons.APPROVED : Icons.WARNING).iconPath).toURI().toString()));
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

    public void handleOnFocus(ActionEvent actionEvent) {
    }

    private enum Icons {
        WARNING("src/aservio/presentation/platform/icons/warningIcon.png"),
        APPROVED("src/aservio/presentation/platform/icons/approvedIcon.png");

        String iconPath;

        Icons(String iconPath) {
            this.iconPath = iconPath;
        }
    }
}
