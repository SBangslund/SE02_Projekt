package aservio.management.overview;

import aservio.management.Management;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import java.io.IOException;
import java.util.Date;

public class OverviewManager {

    private Date currentDate;

    public OverviewManager() {
        currentDate = new Date();
    }

    public void showMonth() {
        Overview view = new OverviewMonth();
        view.setView(getFXML("../views/FXMLOverviewMonth.fxml"));
        view.show();
    }

    public void showWeek() {
        Overview view = new OverviewWeek(null);
        view.setView(getFXML("../views/FXMLOverviewWeek.fxml"));
        view.show();
    }

    public void showDay() {
        Overview view = new OverviewDay();
        view.setView(getFXML("../views/FXMLOverviewDay.fxml"));
        view.show();
    }

    private void setTitle(String title) {
        Management.getInstance().setOverviewTitle(title);
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
