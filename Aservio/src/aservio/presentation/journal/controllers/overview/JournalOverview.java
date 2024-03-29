package aservio.presentation.journal.controllers.overview;

import aservio.domain.journal.Note;
import aservio.presentation.PresentationInterfaceManager;
import aservio.presentation.journal.controllers.Journal;
import aservio.presentation.journal.interfaces.contracts.IJournalOverview;
import aservio.presentation.platform.controllers.Profile;
import java.util.List;
import javafx.scene.Parent;
import javafx.fxml.Initializable;
import aservio.domain.platform.user.UserInfo;
import java.util.ArrayList;

/**
 *
 * @author victo
 */
public abstract class JournalOverview implements Initializable {

    private Parent view;
    protected IJournalOverview interFace = PresentationInterfaceManager.getiJournalOverview();
    protected static List<UserInfo> selectedUsers = new ArrayList<>();

    protected JournalOverview() {
        Profile.eventManager.addEventHandler(Profile.SELECTED_USERS_CHANGED, event -> {
            selectedUsers = event.getSelectedUsers();
        });
    }

    protected abstract void updateSelectedNote(Note note);

    /**
     * Show overview. (Is hard linked to a
     * {@link javafx.scene.layout.BorderPane}).
     */
    public void show() {
        Journal.getInstance().setCenterView(view);
    }

    /**
     * Set the view for the{@link JournalOverview} to visualize
     * @param view
     */
    public void setView(Parent view) {
        this.view = view;
    }

    public Parent getView() {
        return view;
    }
}
