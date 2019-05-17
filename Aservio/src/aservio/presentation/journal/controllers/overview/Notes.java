package aservio.presentation.journal.controllers.overview;

import aservio.domain.journal.FooterNote;
import aservio.domain.journal.HeaderNote;
import aservio.domain.journal.Note;
import aservio.presentation.platform.interfaces.PermissionLimited;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author victo
 */
public class Notes extends JournalOverview implements Initializable, PermissionLimited {

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
            dateLabel.setText(note.getDate().toString());
            clientLabel.setText(note.getCitizenInfo().getFirstName() + " " + note.getCitizenInfo().getLastName());
            authorLabel.setText(note.getCaretakerInfo());
            titleLabel.setText(note.getTitle());
            noteTextArea.setText(note.getNoteText());
        } else {
            noteTextArea.setText("");
        }
    }

    @Override
    public void applyPermissionLimitations() {
        modifyButton.setVisible(DEFAULT_PERMISSIONS.canModifyNote());
    }

    @FXML
    private void modifyButton(ActionEvent event) {
        //Todo
    }

}
