package aservio.presentation.journal.controllers.overview;

import aservio.domain.journal.Note;
import aservio.domain.platform.user.User;
import aservio.domain.platform.user.UserInfo;
import aservio.presentation.journal.controllers.Journal;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.UUID;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author victo
 */
public class CreateNotes extends JournalOverview implements Initializable {

    @FXML
    private JFXDatePicker datePicker;
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
        saveButton.getStyleClass().add("button_save");
        cancelButton.getStyleClass().add("button_cancel");
        titleField.getStyleClass().add("text-field_name");

    }
    /**
     * The cancelButton does that the user can cancel create note mode 
     * and returns to the note overview 
     * @param event 
     */
    @FXML
    private void cancelButtonEvent(ActionEvent event) {
        Journal.getInstance().getJournalOverviewManager().showNote();
        Journal.getInstance().getShowListView().setVisible(true);
    }
    /**
     * This event save the created note to the selected user
     * To make the note, the user have to insert: 
     * -Date, startTime, title and note text
     * Then the save button has been pressed, the caretaker's name will be set on the note
     * NB The singleton getInstance() is used to call the journalOverviewManager to showNote()
     * @param event 
     */
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
        Journal.getInstance().getJournalOverviewManager().showNote();
        Journal.getInstance().getShowListView().setVisible(true);

    }

    @Override
    protected void updateSelectedNote(Note note) {
        // TODO WIP
    }
}
