package aservio.management.overview;

import java.io.File;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;

public class OverviewManager {

    public void showMonth() {
        Overview view = new OverviewMonth();
       
        view.setView(getFXML("src/aservio/management/views/FXMLOverviewMonth.fxml"));
        view.show();
    }

    public void showWeek() {
        Overview view = new OverviewWeek();
        view.setView(getFXML("src/aservio/management/views/FXMLOverviewWeek.fxml"));
        view.show();
    }

    public void showDay() {
        Overview view = new OverviewDay();
        view.setView(getFXML("src/aservio/management/views/FXMLOverviewDay.fxml"));
        view.show();
    }

    private Parent getFXML(String url) {
        try {
            URL file = new File(url).toURI().toURL();
            return FXMLLoader.load(file);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Wrong url: " + url);
        }
        return new Label("Null");
    }
}
