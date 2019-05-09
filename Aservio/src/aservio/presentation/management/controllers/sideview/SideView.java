package aservio.presentation.management.controllers.sideview;

import aservio.presentation.PresentationInterfaceManager;
import aservio.presentation.management.controllers.Management;
import aservio.presentation.management.interfaces.contracts.ISideView;
import javafx.scene.Parent;

public abstract class SideView {
    
    private Parent view;
    protected ISideView interFace = PresentationInterfaceManager.getISideView();

    protected abstract void initialize();

    public void show() {
        Management.getInstance().setLeftView(view);
        initialize();
    }

    public void setView(Parent view) {
        this.view = view;
    }

    public Parent getView() {
        return view;
    }
}
