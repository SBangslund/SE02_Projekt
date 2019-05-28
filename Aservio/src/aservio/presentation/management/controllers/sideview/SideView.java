package aservio.presentation.management.controllers.sideview;

import aservio.presentation.PresentationInterfaceManager;
import aservio.presentation.management.controllers.Management;
import aservio.presentation.management.interfaces.contracts.ISideView;
import javafx.scene.Parent;

public abstract class SideView {
    
    private Parent view;
    /**
     * The interFace is a reference to the interface that connects the presentation layer to the domain layer.
     */
    protected ISideView interFace = PresentationInterfaceManager.getISideView();

    protected abstract void initialize();

    /**
     * when called, shows this view to the user.
     */
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
