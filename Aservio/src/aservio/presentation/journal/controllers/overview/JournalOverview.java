/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aservio.presentation.journal.controllers.overview;

import aservio.presentation.PresentationInterfaceManager;
import aservio.presentation.journal.controllers.Journal;
import aservio.presentation.journal.interfaces.contracts.IJournalOverview;
import javafx.scene.Parent;
import javafx.fxml.Initializable;

/**
 *
 * @author victo
 */
public abstract class JournalOverview implements Initializable {

    private Parent view;
    protected IJournalOverview interFace = PresentationInterfaceManager.getIJournalOverview();

    public void show() {
        Journal.getInstance().setCenterView(view);
    }

    public void setView(Parent view) {
        this.view = view;
    }

    public Parent getView() {
        return view;
    }
}
