package aservio.management.overview;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;

import java.io.IOException;

public class OverviewManager {

    public void showMonth() {
        Overview view = new OverviewMonth();
        view.setView(getFXML("../views/FXMLOverviewMonth.fxml"));
        view.show();
    }

    public void showWeek() {
        Overview view = new OverviewWeek();
        view.setView(getFXML("../views/FXMLOverviewWeek.fxml"));
        view.show();
    }

    public void showDay() {
        Overview view = new OverviewDay();
        view.setView(getFXML("../views/FXMLOverviewDay.fxml"));
        view.show();
    }

    private Parent getFXML(String url) {
        try {
            return FXMLLoader.load(getClass().getResource(url));
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Wrong url: " + url);
        }
        return new Label("Null");
    }
}