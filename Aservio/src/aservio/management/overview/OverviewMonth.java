    package aservio.management.overview;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class OverviewMonth extends Overview {

    public OverviewMonth() {
        this.initialize();
    }

    @Override
    protected void initialize() {
        // TODO create month overview

        // Merely testing how the view could be created.
        System.out.println("Created overview month");
        AnchorPane root = new AnchorPane();
        GridPane grid = new GridPane();
        grid.setVgap(1);
        grid.setHgap(1);
        grid.setGridLinesVisible(true);

        // TODO Populate GridPane automatically based on node width and number of days in month.
        // Keep it to 7x5?
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 5; j++) {
                Pane pane = new Pane();
                pane.setPrefWidth(50);
                pane.setPrefHeight(50);
                grid.add(pane, i, j);
            }
        }

        root.getChildren().add(grid);
        super.setView(root);
    }
}
