/*
 * Created by Samuel Bangslund, Odense SDU Software Engineering 1. semester.
 */
package aservio.presentation.management.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import aservio.domain.management.activities.ActivityManager;
import aservio.presentation.management.controllers.overview.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
    private MenuButton viewMenu;
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
    private OverviewManager overviewManager;
    private ActivityManager activityManager;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        instance = this;

        overviewManager = new OverviewManager();
        activityManager = new ActivityManager("/aservio/presentation/management/views/FXMLActivityView.fxml");
        
        overviewManager.showDay();
    }

    public void setCenterView(Node node) {
        List<Node> children = ((VBox)borderPane.getCenter()).getChildren();
        if(children.size() > 1) {
            children.remove(1);
        }
        VBox.setVgrow(node, Priority.ALWAYS);
        children.add(1, node);
    }

    public void setLeftView(Node node) {
        borderPane.setLeft(node);
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
        overviewManager.showMonth();
    }

    @FXML
    public void handleShowWeek(ActionEvent actionEvent) {
        overviewManager.showWeek();
    }

    @FXML
    public void handleShowDay(ActionEvent actionEvent) {
        overviewManager.showDay();
    }

    @FXML
    public void handlePrevious(ActionEvent actionEvent) {
        overviewManager.showPrevious();
    }

    @FXML
    public void handleNext(ActionEvent actionEvent) {
        overviewManager.showNext();
    }

    public OverviewManager getOverviewManager() {
        return overviewManager;
    }

    public ActivityManager getActivityManager() {
        return activityManager;
    }
}
