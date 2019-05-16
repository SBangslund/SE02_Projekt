/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aservio.presentation.journal.controllers.overview;

import aservio.domain.journal.Note;
import aservio.domain.journal.NoteList;
import aservio.domain.platform.user.User;
import aservio.domain.platform.user.UserInfo;
import aservio.presentation.journal.controllers.Journal;
import aservio.presentation.platform.OverviewType;
import aservio.presentation.platform.controllers.Profile;
import com.jfoenix.controls.JFXTimePicker;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author victo
 */
public class CreateNotes extends JournalOverview implements Initializable {

    @FXML
    private DatePicker datePicker;
    @FXML
    private Button cancelButton;
    @FXML
    private Button saveButton;
    @FXML
    private TextArea noteTextArea;
    @FXML
    private JFXTimePicker startTimePicker;
    @FXML
    private TextField titleField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

    @FXML
    private void cancelButtonEvent(ActionEvent event) {
        Journal.getInstance().getJournalOverviewManager().showNote();
        Journal.getInstance().getShowListView().setVisible(true);
    }

    @FXML
    private void saveButtonEvent(ActionEvent event) {
        if (!selectedUsers.isEmpty() && startTimePicker.getValue() != null) {
            UserInfo citizenInfo = selectedUsers.get(0);
            UserInfo caretakerInfo = User.getCurrentUser().getUserInfo();
            String editTime = datePicker.getValue() + " " + startTimePicker.getValue().toString();
            Date editDate = new Date();
            try {
                editDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(editTime);
            } catch (ParseException ex) {
                System.err.println("The date was not defined correctly");
            }
            Note note = new Note(UUID.randomUUID(), editDate, noteTextArea.getText(), citizenInfo, titleField.getText(), caretakerInfo.getFirstName() + caretakerInfo.getLastName());
            note.createNoteText(noteTextArea.getText());
            interFace.addNote(note);
        } else{
            Journal.getInstance().getJournalOverviewManager().showNote();
        }            
       
        Journal.getInstance().getShowListView().setVisible(true);
    }

    @Override
    protected void updateSelectedNote(Note note) {
        // TODO WIP
    }
}
