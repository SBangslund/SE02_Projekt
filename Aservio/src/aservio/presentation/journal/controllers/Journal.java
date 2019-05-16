/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aservio.presentation.journal.controllers;

import aservio.domain.journal.Note;
import aservio.domain.journal.NoteList;
import aservio.domain.platform.user.User;
import aservio.domain.platform.user.UserInfo;
import aservio.presentation.PresentationInterfaceManager;
import aservio.presentation.journal.controllers.overview.JournalOverviewManager;
import aservio.presentation.journal.interfaces.contracts.IJournal;
import aservio.presentation.platform.controllers.Profile;
import aservio.presentation.platform.interfaces.PermissionLimited;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author victo
 */
public class Journal implements Initializable, PermissionLimited {

    private IJournal interFace = PresentationInterfaceManager.getIJournal();

    @FXML
    private MenuButton viewMenu;
    @FXML
    private Label leftStatus;
    @FXML
    private Label rightStatus;
    @FXML
    private ProgressIndicator progressIndicator;

    private static Journal instance; //Singleton reference
    private JournalOverviewManager journalOverviewManager;
    @FXML
    private BorderPane borderPane;
    @FXML
    private ListView<Note> showListView;
    @FXML
    private Button newNoteButton;
    private ObservableList<Note> observableList;
    private TextArea text = new TextArea();
    private Note selectedNote;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        instance = this;

        journalOverviewManager = new JournalOverviewManager();

        journalOverviewManager.showNote();

        observableList = FXCollections.observableArrayList();
        showListView.setItems(observableList);
        profileChangeEvent();
        applyPermissionLimitations();
        newNoteButton.setVisible(false);
        
    }

    private void profileChangeEvent() {
        Profile.eventManager.addEventHandler(Profile.SELECTED_USERS_CHANGED, event -> {
            handleSelectedUsersChanged(event.getSelectedUsers());
        });

        showListView.getSelectionModel().getSelectedItems().addListener((ListChangeListener<Note>) c -> {
            c.next();
            if (c.getAddedSize() > 0) {
                journalOverviewManager.updateSelectedNote(c.getAddedSubList().get(0));
            }
        });
    }

    private void handleSelectedUsersChanged(List<UserInfo> selectedUsers) {
        observableList.clear();
        journalOverviewManager.updateSelectedNote(null);
        if (!selectedUsers.isEmpty()) {
            NoteList noteList = interFace.getNoteList(selectedUsers.get(0));
            showNoteList(noteList);
            applyPermissionLimitations();
        }
    }

    private void showNoteList(NoteList noteList) {
        if (noteList != null && !noteList.getNotes().isEmpty()) {
            showListView.getItems().clear();
            List<Note> notes = noteList.getNotes();
            showListView.getItems().addAll(notes);
        }
    }

    public void setCenterView(Node node) {
        borderPane.setCenter(node);
    }

    public Node getCenterView() {
        return borderPane.getCenter();
    }

    @FXML
    private void handleShowDiagnosing(ActionEvent event) {
        journalOverviewManager.showDiagnosing();
    }

    @FXML
    private void handleShowPrescription(ActionEvent event) {
        journalOverviewManager.showPrescription();
    }

    @FXML
    private void handleShowService(ActionEvent event) {
        journalOverviewManager.showService();
    }
    
    @FXML
    private void handleShowNote(ActionEvent event) {
        journalOverviewManager.showNote();
    }

    public static Journal getInstance() {
        return instance;
    }

    public JournalOverviewManager getJournalOverviewManager() {
        return journalOverviewManager;
    }

    @FXML
    private void newNoteButtonEvent(ActionEvent event) {
        journalOverviewManager.showCreateNote();
        showListView.setVisible(false);
    }

    private void viewMenuEvent(ActionEvent event) {
        journalOverviewManager.showDiagnosing();
    }

    public ObservableList<Note> getObservableList() {
        return observableList;
    }

    public Note getSelectedNote() {
        return selectedNote;
    }

    public ListView<Note> getShowListView() {
        return showListView;
    }

    @Override
    public void applyPermissionLimitations() {
        newNoteButton.setVisible(DEFAULT_PERMISSIONS.canCreateNote());
        if (!DEFAULT_PERMISSIONS.canSeeUserList()) {
            showNoteList(interFace.getNoteList(User.getCurrentUser().getUserInfo()));
        }
    }
}
