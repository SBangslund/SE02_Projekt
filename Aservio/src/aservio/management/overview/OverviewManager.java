package aservio.management.overview;

import aservio.management.Management;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class OverviewManager {

    private Overview currentOverview;

    public void showMonth() {
        updateCurrentOverview(OverviewType.MONTH.getURL());
    }

    public void showWeek() {
        updateCurrentOverview(OverviewType.WEEK.getURL());
    }

    public void showDay() {
        updateCurrentOverview(OverviewType.DAY.getURL());
    }

    public void showNext() {
        currentOverview.next();
    }

    public void showPrevious() {
        currentOverview.previous();
    }

    private void updateCurrentOverview(String url) {
        FXMLLoader loader = new FXMLLoader();
        try {
            Pane p = loader.load(getClass().getResource(url).openStream());
            currentOverview = loader.getController();
            currentOverview.setView(p);
            currentOverview.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Overview getCurrentOverview() {
        return this.currentOverview;
    }
}
