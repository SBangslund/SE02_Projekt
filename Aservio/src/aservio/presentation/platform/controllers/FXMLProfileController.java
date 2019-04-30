package aservio.presentation.platform.controllers;

import aservio.domain.platform.user.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FXMLProfileController implements Initializable {

    @FXML
    private Label labelName;
    @FXML
    private ImageView userImage;
    @FXML
    private AnchorPane profileView;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            labelName.setText(User.getCurrentUser().getUserInfo().getFirstName() + " " + User.getCurrentUser().getUserInfo().getLastName());
            profileView.getChildren().add(FXMLLoader.load(getClass().getResource("/aservio/presentation/platform/views/FXMLSeeProfile.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleHide(ActionEvent actionEvent) {
    }
}
