package aservio.management.overview;

import javafx.scene.Parent;

public abstract class Overview {
    private final Parent view;

    protected Overview(Parent view) {
        this.view = view;
    }

    public abstract void initialize();

    public Parent getView() {
        return view;
    }
}
