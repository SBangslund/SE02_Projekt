/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aservio.presentation.journal.controllers.overview;

import aservio.domain.journal.Note;
import aservio.presentation.platform.OverviewType;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

/**
 *
 * @author victo
 */
public class JournalOverviewManager {

    private JournalOverview currentJournalOverview;

    public void showNote() {
        updateCurrentJournalOverview(OverviewType.NOTE.getURL());
    }

    public void showCreateNote() {
        updateCurrentJournalOverview(OverviewType.CREATENOTE.getURL());
    }

    public void showDiagnosing() {
        updateCurrentJournalOverview(OverviewType.DIAGNOSING.getURL());
    }

    public void updateSelectedNote(Note note) {
        currentJournalOverview.updateSelectedNote(note);
    }
    
    private void updateCurrentJournalOverview(String url) {
        FXMLLoader loader = new FXMLLoader();
        try {
            Pane p = loader.load(getClass().getResource(url).openStream());
            currentJournalOverview = loader.getController();
            currentJournalOverview.setView(p);
            currentJournalOverview.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JournalOverview getCurrentJournalOverview() {
        return this.currentJournalOverview;
    }

}
