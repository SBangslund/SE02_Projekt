package aservio.management.overview;

import aservio.management.Management;
import javafx.scene.Parent;

/**
 * An abstraction of an overview for the {@link Management} class. This is needed to display months, weeks and days.
 */
public abstract class Overview {

    private Parent view;

    /**
     * Initilizes all the necessary nodes for this view. This needs to be implemented by inherited classes.
     */
    protected abstract void initialize();

    /**
     * Show overview. (Is hard linked to a {@link javafx.scene.layout.BorderPane}).
     */
    public void show() {
        Management.getInstance().setCenterView(view);
    }

    /**
     * Set the view for the {@link Overview} to visualize.
     * @param view to set.
     */
    public void setView(Parent view) {
        this.view = view;
    }

    public Parent getView() {
        return view;
    }
}
