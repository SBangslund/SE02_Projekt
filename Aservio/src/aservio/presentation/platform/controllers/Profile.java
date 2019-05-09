package aservio.presentation.platform.controllers;

import aservio.domain.platform.user.User;
import aservio.domain.platform.user.UserInfo;
import aservio.presentation.PresentationInterfaceManager;
import aservio.presentation.platform.interfaces.contracts.IProfile;
import javafx.animation.AnimationTimer;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Profile implements Initializable {

    private IProfile interFace = PresentationInterfaceManager.getIProfile();

    @FXML
    private VBox vbox;
    @FXML
    private Separator seperator;
    @FXML
    private HBox topLabel;
    @FXML
    private Button hideButton;
    @FXML
    private Button showButton;
    @FXML
    private AnchorPane profileAnchorPane;
    @FXML
    private ListView<UserInfo> userList;
    @FXML
    private Label labelName;
    @FXML
    private ImageView userImage;
    @FXML
    private AnchorPane profileView;

    private List<Node> tempNodes;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        userList.getSelectionModel().getSelectedItems().addListener((ListChangeListener<UserInfo>) c -> {
            c.next();
            if (c.getAddedSubList().size() > 0)
                System.out.println("Selected: " + c.getAddedSubList().get(0));
        });
        try {
            labelName.setText(User.getCurrentUser().getUserInfo().getFirstName() + " " + User.getCurrentUser().getUserInfo().getLastName());
            profileView.getChildren().add(FXMLLoader.load(getClass().getResource("/aservio/presentation/platform/views/SeeProfile.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        showButton.setVisible(false);

        // Show users in list related to the current users institution id.
        ObservableList<UserInfo> observableUserList = FXCollections.observableArrayList();
        List<UserInfo> userInfos = interFace.getUsersFromInstitution(User.getCurrentUser().getUserInfo().getInstitution());
        observableUserList.addAll(userInfos);
        userList.getItems().addAll(observableUserList);
    }

    @FXML
    public void handleHide(ActionEvent actionEvent) {
        hideButton.setVisible(false);
        showButton.setVisible(true);

        userList.setVisible(false);
        labelName.setVisible(false);
        profileView.setVisible(false);

        topLabel.setMaxWidth(30);
        userImage.setFitWidth(0);

        vbox.getChildren().remove(2, 4);
    }

    @FXML
    public void handleShow(ActionEvent actionEvent) {
        hideButton.setVisible(true);
        showButton.setVisible(false);

        userList.setVisible(true);
        labelName.setVisible(true);
        profileView.setVisible(true);

        topLabel.setMaxWidth(250);
        userImage.setFitWidth(50);

        vbox.getChildren().add(profileView);
        vbox.getChildren().add(userList);
    }

    private void handleSelectUser(ListChangeListener<String> list) {
        System.out.println(list);
    }
}
