/*
 * Created by Samuel Bangslund, Odense SDU Software Engineering 1. semester.
 */
package aservio.management;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import aservio.management.overview.Overview;
import aservio.management.overview.OverviewDay;
import aservio.management.overview.OverviewMonth;
import aservio.management.overview.OverviewWeek;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;

/**
 *
 * @author sbang
 */
public class Management implements Initializable {
    @FXML
    private VBox wrapper;
    @FXML
    private MenuBar menubar;
    @FXML
    private ToolBar toolbar;
    @FXML
    private HBox adminToolbar;
    @FXML
    private HBox viewToolbar;
    @FXML
    private SplitMenuButton viewMenu;
    @FXML
    private BorderPane borderPane;
    @FXML
    private Label leftStatus;
    @FXML
    private Label rightStatus;
    @FXML
    private ProgressIndicator progressIndicator;

    private static Management instance; // Singleton reference

    private List<Overview> views = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        instance = this;
    }

    public void setCenterView(Node node) {
        borderPane.setCenter(node);
    }

    public Node getCenterView() {
        return borderPane.getCenter();
    }

    /**
     * Gains access to the Management singleton instance.
     * @return The single instance of Management.
     */
    public static Management getInstance() {
        return instance;
    }

    @FXML
    public void handleShowMonth(ActionEvent actionEvent) {
        Overview view = new OverviewMonth();
        view.show();
    }

    @FXML
    public void handleShowWeek(ActionEvent actionEvent) {
        Overview view = new OverviewWeek();
        view.show();
    }

    @FXML
    public void handleShowDay(ActionEvent actionEvent) {
        Overview view = new OverviewDay();
        view.show();
    }
}
