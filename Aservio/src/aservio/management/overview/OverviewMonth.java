package aservio.management.overview;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import java.net.URL;
import java.util.*;

public class OverviewMonth extends Overview implements Initializable {

    @FXML
    private GridPane gridPane;

    private List<Pane> days = new ArrayList<>();

    public OverviewMonth() {
        super(new Date());
    }

    @Override
    protected void initialize() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO create month overview
        System.out.println("Created overview month");
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 5; j++) {
                Pane day = new Pane();
                day.getChildren().add(new Label(i + ":" + j));
                gridPane.add(day, i, j);
                days.add(day);
            }
        }
    }

    public void populateDays() {
        GregorianCalendar calender = new GregorianCalendar();
        calender.setTimeZone(TimeZone.getTimeZone("GMT+2"));
        for (Pane day: days) {
            Label name = (Label)day.getChildren().get(0);
        }
    }
}
