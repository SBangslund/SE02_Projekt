package aservio.presentation.platform.controllers;

import aservio.domain.platform.user.Address;
import aservio.domain.platform.user.User;
import aservio.domain.platform.user.UserInfo;
import aservio.presentation.PresentationInterfaceManager;
import aservio.presentation.platform.interfaces.PermissionLimited;
import aservio.presentation.platform.interfaces.contracts.IProfile;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class Profile implements Initializable, PermissionLimited {
    private IProfile interFace = PresentationInterfaceManager.getIProfile();

    @FXML
    private Button buttonAddUser;
    @FXML
    private TextField searchField;
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

    private List<UserInfo> selectedUsers = new ArrayList<>();
    private ProfileViews currentView;

    public final static EventType<SelectedUsersChangedEvent> SELECTED_USERS_CHANGED = new EventType<>("SELECTED_USERS_CHANGED");
    public static AnchorPane eventManager;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        applyPermissionLimitations();

        labelName.setText(User.getCurrentUser().getUserInfo().getFirstName() + " " + User.getCurrentUser().getUserInfo().getLastName());
        setView(ProfileViews.SEE_PROFILE);

        showButton.setVisible(false);

        userList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        userList.getSelectionModel().getSelectedItems().addListener((ListChangeListener<UserInfo>) c -> {
            c.next();
            selectedUsers.clear();
            selectedUsers.addAll(c.getAddedSubList());
            if (c.getAddedSubList().size() > 0)
                SeeProfile.getInstance().setUserInfo(c.getAddedSubList().get(0));

            SelectedUsersChangedEvent changedEvent = new SelectedUsersChangedEvent();
            profileAnchorPane.fireEvent(changedEvent);
        });

        eventManager = this.profileAnchorPane;

        showAllUsers();
    }

    private void setView(ProfileViews view) {
        currentView = view;
        profileView.getChildren().clear();
        try {
            profileView.getChildren().add(FXMLLoader.load(getClass().getResource(view.path)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAllUsers() {
        userList.getItems().clear();
        // Show users in list related to the current users institution id.
        List<UserInfo> userInfos = interFace.getUsersFromInstitution(User.getCurrentUser().getUserInfo().getInstitution());
        userList.getItems().addAll(
                userInfos.parallelStream()
                        .filter(e -> !e.getId().equals(User.getCurrentUser().getId()))
                        .collect(Collectors.toList())
        );
    }

    private void showSearchedUsers(String search) {
        userList.getItems().clear();
        if (search.length() > 0) {
            List<UserInfo> userInfos = interFace.getUsersFromInstitution(User.getCurrentUser().getUserInfo().getInstitution());
            userInfos.stream()
                    .filter(info -> info.getFirstName().toLowerCase().contains(search) || info.getLastName().toLowerCase().contains(search))
                    .forEach(userList.getItems()::add);
        } else {
            showAllUsers();
        }
    }

    @FXML
    public void handleSearchChanged(KeyEvent inputMethodEvent) {
        showSearchedUsers(searchField.getText().toLowerCase());
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

        vbox.getChildren().remove(2, 5);
    }

    @FXML
    public void handleShow(ActionEvent actionEvent) {
        hideButton.setVisible(true);
        showButton.setVisible(false);

        userList.setVisible(true);
        labelName.setVisible(true);
        profileView.setVisible(true);

        topLabel.setMaxWidth(290);
        userImage.setFitWidth(50);

        vbox.getChildren().add(profileView);
        vbox.getChildren().add(searchField);
        vbox.getChildren().add(userList);

        applyPermissionLimitations();
    }

    @FXML
    public void handleOnAddUser(ActionEvent actionEvent) {
        if(currentView.equals(ProfileViews.SEE_PROFILE)) {
            setView(ProfileViews.ADD_PROFILE);
            buttonAddUser.setText("Opret bruger");
        } else {
            setView(ProfileViews.SEE_PROFILE);
            buttonAddUser.setText("Tilføj bruger");
        }
    }

    @Override
    public void applyPermissionLimitations() {
        userList.setVisible(DEFAULT_PERMISSIONS.canSeeUserList());
        searchField.setVisible(DEFAULT_PERMISSIONS.canSeeUserList());
    }

    private enum ProfileViews {
        SEE_PROFILE("/aservio/presentation/platform/views/SeeProfile.fxml"),
        ADD_PROFILE("/aservio/presentation/platform/views/AddProfile.fxml");

        private String path;

        ProfileViews(String path) {
            this.path = path;
        }
    }

    public class SelectedUsersChangedEvent extends Event {

        private List<UserInfo> selectedUsersA;

        public SelectedUsersChangedEvent() {
            this(SELECTED_USERS_CHANGED, selectedUsers);
        }

        public SelectedUsersChangedEvent(EventType<? extends Event> eventType, List<UserInfo> selectedUsers) {
            super(eventType);
            this.selectedUsersA = selectedUsers;
        }

        public List<UserInfo> getSelectedUsers() {
            return selectedUsersA;
        }
    }
}
