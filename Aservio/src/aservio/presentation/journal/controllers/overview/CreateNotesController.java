/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aservio.presentation.journal.controllers.overview;

import aservio.presentation.journal.controllers.Journal;
import com.jfoenix.controls.JFXTimePicker;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

/**
 * FXML Controller class
 *
 * @author victo
 */
public class CreateNotesController extends JournalOverview implements Initializable {

    @FXML
    private DatePicker datePicker;
    @FXML
    private Button cancelButton;
    @FXML
    private Button saveButton;
    @FXML
    private Label titelLabel;
    @FXML
    private TextArea noteTextArea;
    @FXML
    private Label locationLabel;
    @FXML
    private JFXTimePicker startTimePicker;
    @FXML
    private JFXTimePicker endTimePicker;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void cancelButtonEvent(ActionEvent event) {
        Journal.getInstance().getJournalOverviewManager().showNote();
    }

    @FXML
    private void saveButtonEvent(ActionEvent event) {
    }

    
}
