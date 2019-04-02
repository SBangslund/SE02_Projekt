package aservio.management.overview;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class OverviewWeek extends Overview implements CalenderInterface {

    private int[] days;
    private int daysIndex;
    
     public OverviewWeek() {
        this.initialize();
    }

    @Override
    protected void initialize() {
        Parent root = new Label();
        try {
            // TODO create week overview
            root = FXMLLoader.load(getClass().getResource("FXMLOverviewWeek.fxml"));
        } catch (IOException ex) {
            System.out.println("Could not find file");
        }
        super.setView(root);
    }
}
