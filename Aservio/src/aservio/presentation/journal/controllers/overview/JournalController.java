/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aservio.presentation.journal.controllers.overview;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ProgressIndicator;

/**
 * FXML Controller class
 *
 * @author victo
 */
public class JournalController implements Initializable {

    @FXML
    private MenuButton viewMenu;
    @FXML
    private Label leftStatus;
    @FXML
    private Label rightStatus;
    @FXML
    private ProgressIndicator progressIndicator;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleShowNote(ActionEvent event) {
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
    
}
