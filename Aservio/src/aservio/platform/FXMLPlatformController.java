package aservio.platform;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FXMLPlatformController implements Initializable {
    @FXML
    private MenuBar menuBar;
    @FXML
    private BorderPane borderPane;
    @FXML
    private TabPane tabPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            borderPane.setLeft(FXMLLoader.load(getClass().getResource("/aservio/platform/views/FXMLProfile.fxml")));
            tabPane.getTabs().get(0).setContent(FXMLLoader.load(getClass().getResource("/aservio/management/views/FXMLManager.fxml")));
            //tabPane.getTabs().get(1).setGraphic(new ImageView(new Image(new File("resources/DiaryWithoutText.png").toURI().toString())));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
