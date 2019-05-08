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
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import aservio.presentation.journal.controllers.overview.JournalOverviewManager;
import com.jfoenix.controls.JFXTimePicker;

/**
 * FXML Controller class
 *
 * @author victo
 */
public class NotesController extends JournalOverview implements Initializable {

    @FXML
    private Label titelLabel;
    @FXML
    private TextArea noteTextArea;
    @FXML
    private Label locationLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private Label startTimeLabel;
    @FXML
    private Label endTimeLabel;
    @FXML
    private Button modifyButton;
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    


    @FXML
    private void modifyButtonEvent(ActionEvent event) {
       
    }


    
}
