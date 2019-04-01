package aservio.management.overview;

import aservio.management.Management;
import javafx.scene.Parent;

public abstract class Overview {

    private Parent view;

    protected abstract void initialize();

    public void show() {
        Management.getInstance().setCenterView(view);
    }

    protected void setView(Parent view) {
        this.view = view;
    }

    public Parent getView() {
        return view;
    }
}
