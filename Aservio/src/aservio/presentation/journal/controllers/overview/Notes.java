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
    public Label titleDescriptionLabel;
    public Label dateDescriptionLabel;
    public Label klientDescriptionLabel;
    public Label authorDescriptionLabel;
    @FXML
    private TextArea noteTextArea;
    @FXML
    private Button modifyButton;
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
    
    private Note selectedNote;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        applyPermissionLimitations();
        titleLabel.getStyleClass().add("label_content");
        dateLabel.getStyleClass().add("label_content");
        clientLabel.getStyleClass().add("label_content");
        authorLabel.getStyleClass().add("label_content");
        noteTextArea.setWrapText(true);

        titleDescriptionLabel.getStyleClass().add("label_description");
        dateDescriptionLabel.getStyleClass().add("label_description");
        klientDescriptionLabel.getStyleClass().add("label_description");
        authorDescriptionLabel.getStyleClass().add("label_description");
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
    
    /**
     * The updateSelectedNote sets the note view, then an user click on a note in the noteList.
     * The note will only be set if the note is not empty
     * @param note 
     */
    @Override
    protected void updateSelectedNote(Note note) {
         if (note != null) {
             selectedNote = note;
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
        //TODO


    }

    public void deleteButtonAction(ActionEvent actionEvent) {
        if(selectedNote != null){
            interFace.deleteNote(selectedNote) ;
        }
    }
}
