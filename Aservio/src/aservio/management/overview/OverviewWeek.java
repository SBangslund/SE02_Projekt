package aservio.management.overview;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class OverviewWeek extends Overview implements CalenderInterface{

    private int[] days;
    private int daysIndex;
    private GridPane grid;

    public OverviewWeek() {
        this.initialize();
    }

    @Override
    protected void initialize() {
        // TODO create week overview
        System.out.println("Created overview week");
        AnchorPane root = new AnchorPane();
        grid = new GridPane();
        grid.setVgap(1);
        grid.setHgap(1);
        grid.setGridLinesVisible(true);

        // TODO Populate GridPane automatically based on node width and number of days in month.
        // Keep it to 7x5?
        for (int i = BLANK; i <= SUNDAY; i++) {
            for (int j = 0; j <= 23; j++) {
                Pane pane = new Pane();
                pane.setPrefWidth(50);
                pane.setPrefHeight(50);
                grid.add(pane, i, j);
                grid.add(new Text(String.format("%d / %d", i, j)), i, j);
            }
        }

        root.getChildren().add(grid);
        super.setView(root);
    }
//
//    public void fillDays() {
//        for (int i = MONDAY; i <= SUNDAY; i++) {
//            for (int j = 0; j <= 23; j++) {
//                grid.add(new Text(String.format("%d / %d", i, j)), i, j);
//            }
//        }
//    }
}
