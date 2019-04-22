package aservio.management.sideview;

import aservio.management.Management;
import javafx.scene.Parent;

public abstract class SideView {
    private Parent view;

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
