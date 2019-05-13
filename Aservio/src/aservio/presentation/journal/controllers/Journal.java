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
import aservio.presentation.journal.controllers.overview.Notes;
import aservio.presentation.journal.interfaces.contracts.IJournal;
import aservio.presentation.platform.controllers.Profile;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author victo
 */
public class Journal implements Initializable {

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

        Profile.eventManager.addEventHandler(Profile.SELECTED_USERS_CHANGED, event -> {
            handleSelectedUsersChanged(event.getSelectedUsers());
        });

    }

    private void handleSelectedUsersChanged(List<UserInfo> selectedUsers) {
        if (!selectedUsers.isEmpty()) {
            NoteList noteList = interFace.getNoteList(selectedUsers.get(0));
            showNoteList(noteList);
        }
    }

    private void showNoteList(NoteList noteList) {
        if (!noteList.getNotes().isEmpty()) {
            showListView.getItems().clear();
            List<Note> notes = noteList.getNotes();
            showListView.getItems().addAll(notes);
        }
    }

    @FXML
    private void handleShowNote(ActionEvent event) {

    }

    public void setCenterView(Node node) {
        borderPane.setCenter(node);

//        List<Node> children = ((VBox) borderPane.getCenter()).getChildren();
//        if (children.size() > 1) {
//            children.remove(1);
//        }
//        VBox.setVgrow(node, Priority.ALWAYS);
//        children.add(1, node);
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
    }

    @FXML
    private void handleShowService(ActionEvent event) {
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
    }

    private void viewMenuEvent(ActionEvent event) {
        journalOverviewManager.showDiagnosing();
    }

    public ObservableList<Note> getObservableList() {
        return observableList;
    }

}
