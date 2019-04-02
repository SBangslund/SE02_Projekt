package aservio.management.overview;


import aservio.management.Management;
import javafx.fxml.FXML;
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
import java.util.*;

public class OverviewDay extends Overview implements Initializable{

    @FXML
    public GridPane gridPane;
    public Label labelTid;
    public Label labelDate;
    public ScrollPane scrollPane;

    Date date;
    Parent root;
    ArrayList<Pane> hourPanes;
    ArrayList<Pane> hourContentPanes;

    public OverviewDay() {
        this(new Date());
    }

    public OverviewDay(Date date){
        this.date = date;
        this.initialize();
    }
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        System.out.println("Created overview day");
        labelDate.setText(String.format("%s, den %tD", new SimpleDateFormat("EEE", new Locale("da", "DK")).format(date.getTime()), Calendar.getInstance().getTime()));
        hourPanes = new ArrayList<>();
        hourContentPanes = new ArrayList<>();
        gridPane.add(new Label("something"), 1, 1);
        fillGrid(gridPane);
        gridPane.setGridLinesVisible(true);

    }

    public void fillGrid(GridPane pane){
        fillTime(pane);
        fillTimeContent(pane, new ArrayList<String>());
    }
    
    public void fillTime(GridPane pane){
        for (int i = 1; i < 23; i++) {
            Pane hour = new Pane();
            pane.setMinHeight(30);
            hour.getChildren().add(new Text(i < 10 ? "0" + Integer.toString(i) : Integer.toString(i)));
            pane.add(hour, 0, i);
            hourPanes.add(hour);
        }
    }

    public void fillTimeContent(GridPane pane, ArrayList<String> activities){
        for (int i = 1; i < 23; i++) {
            Pane content = new Pane();
            pane.setMinHeight(30);
            content.getChildren().add(new Text("Activitynumber: " +  Math.random() * i));
            pane.add(content, 1, i);
            hourPanes.add(content);
        }
    }

    @Override
    protected void initialize() {
        
    }
}
