/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aservio.presentation.journal.controllers.overview;

import aservio.domain.journal.FooterNote;
import aservio.domain.journal.HeaderNote;
import aservio.domain.journal.Note;
import aservio.domain.journal.NoteList;
import aservio.presentation.journal.controllers.Journal;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

/**
 * FXML Controller class
 *
 * @author victo
 */
public class Notes extends JournalOverview implements Initializable {

    @FXML
    private Label titelLabel;
    @FXML
    private TextArea noteTextArea;
    @FXML
    private Label dateLabel;
    @FXML
    private Label startTimeLabel;
    @FXML
    private Label endTimeLabel;
    @FXML
    private Button modifyButton;
    @FXML
    private Label footNoteLabel;
    private Note selectedNote;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void setNoteTitle(HeaderNote title) {
        titelLabel.setText(title.toString());
    }

    public void setFootNote(FooterNote footerNote) {
        footNoteLabel.setText(footerNote.toString());
    }
//    public void setSideview(Date date){
//        dateLabel
//        
//    }

    public void setNoteText(Note note) {
        interFace.addNote(note);
    }

    public void viewNote() {
    }

    public Note getSelectedNote() {
        return selectedNote;
    }

    @Override
    protected void updateSelectedNote(Note note) {

        if (note != null) {
            dateLabel.setText(note.getDate().toString());
            startTimeLabel.setText(note.getStartTime());
            endTimeLabel.setText(note.getEndTime());
            noteTextArea.setText(note.getNoteText());
        } else {
            noteTextArea.setText("");
        }
    }

}