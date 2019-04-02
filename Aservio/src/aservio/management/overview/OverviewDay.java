package aservio.management.overview;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

public class OverviewDay extends Overview implements Initializable{

    Date date;

    Parent root;

    public OverviewDay() {
        this(new Date());
    }

    public OverviewDay(Date date){
        this.date = date;
        this.initialize();
    }

    @Override
    protected void initialize() {

        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("aservio/management/views/FXMLOverviewDay.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("unable to find the FXML Document");
        }
        // TODO create day overview
        System.out.println("Created overview day");
        ScrollPane root = new ScrollPane();
        GridPane grid = new GridPane();
        grid.setVgap(1);
        grid.setHgap(1);

        grid.add(new Label(String.format("%s, den %tD", new SimpleDateFormat("EE", new Locale("da", "DK")).format(date.getTime()), Calendar.getInstance().getTime())), 1,0);

        grid.setGridLinesVisible(true);
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 23; j++) {
                int width = i == 0 ? 50 : 300;
                Pane pane = new Pane();
                //pane.getChildren().add(new Text(String.format("%d, %d", i, j)));
                pane.setPrefWidth(width);
                pane.setPrefHeight(50);
                grid.add(pane, i, j);
            }
        }


        //root.setContent(grid);
        super.setView(root);
    }

    public void fillGrid(){

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
