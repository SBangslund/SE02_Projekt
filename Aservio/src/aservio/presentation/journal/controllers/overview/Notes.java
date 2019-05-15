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
    private Label footNoteLabel;
    private Note selectedNote;
    @FXML
    private VBox noteInformationVBox;
    @FXML
    private TextField titleTextField;
    @FXML
    private TextField dateTextField;
    @FXML
    private TextField clientTextField;
    @FXML
    private TextField authorTextField;
    @FXML
    private Button deleteButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        applyPermissionLimitations();
    }

    public void setNoteTitle(HeaderNote title) {
        titelLabel.setText(title.toString());
    }

    public void setFootNote(FooterNote footerNote) {
        footNoteLabel.setText(footerNote.toString());
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
            String[] arr = note.getNoteText().trim().split("+");
            
            dateTextField.setText(arr[0].toString());
            clientTextField.setText(arr[1].toString());
//            clientTextField.setText(note.getCitizenInfo().getFirstName() + note.getCitizenInfo().getLastName());
//            authorTextField.setText(note.);
        } else {
            noteTextArea.setText("");
        }
    }

    @Override
    public void applyPermissionLimitations() {
        modifyButton.setVisible(DEFAULT_PERMISSIONS.canModifyNote());
    }

}
