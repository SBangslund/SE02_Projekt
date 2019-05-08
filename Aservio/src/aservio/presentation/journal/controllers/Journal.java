/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aservio.presentation.journal.controllers;

import aservio.presentation.journal.controllers.overview.JournalOverviewManager;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
    private ListView<?> showListView;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        instance = this;

        journalOverviewManager = new JournalOverviewManager();

        journalOverviewManager.showNote();
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

}
