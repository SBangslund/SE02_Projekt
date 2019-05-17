package aservio.presentation.journal.controllers.overview;

import aservio.domain.journal.Note;
import aservio.presentation.platform.interfaces.PermissionLimited;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author Rene_
 */
public class Prescription extends JournalOverview implements Initializable, PermissionLimited {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @Override
    protected void updateSelectedNote(Note note) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void applyPermissionLimitations() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
