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
import aservio.presentation.platform.interfaces.PermissionLimited;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author victo
 */
public class Notes extends JournalOverview implements Initializable, PermissionLimited {

    private Label titelLabel;
    @FXML
    private TextArea noteTextArea;
    @FXML
    private Button modifyButton;
    private Note selectedNote;
    @FXML
    private VBox noteInformationVBox;
    @FXML
    private Button deleteButton;
    @FXML
    private Label titleLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private Label clientLabel;
    @FXML
    private Label authorLabel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        applyPermissionLimitations();
    }

    public void setNoteTitle(HeaderNote title) {
        titleLabel.setText(title.toString());
    }

    public void setFootNote(FooterNote footerNote) {
        authorLabel.setText(footerNote.toString());
    }

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
            String[] arr = note.getNoteText().trim().split("\\+");
            noteTextArea.setText(arr[2].toString());
            dateLabel.setText(note.getDate().toString());
            clientLabel.setText(note.getCitizenInfo().getFirstName() + " " + note.getCitizenInfo().getLastName());
        } else {
            noteTextArea.setText("");
        }
    }

    @Override
    public void applyPermissionLimitations() {
        modifyButton.setVisible(DEFAULT_PERMISSIONS.canModifyNote());
    }

}
