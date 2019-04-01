/*
 * Created by Samuel Bangslund, Odense SDU Software Engineering 1. semester.
 */
package aservio.management;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.*;

/**
 *
 * @author sbang
 */
public class Management implements Initializable {

    public VBox wrapper;
    public MenuBar menubar;
    public ToolBar toolbar;
    public HBox adminToolbar;
    public HBox viewToolbar;
    public BorderPane borderPane;
    public Label leftStatus;
    public Label rightStatus;
    public ProgressIndicator progressIndicator;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}
