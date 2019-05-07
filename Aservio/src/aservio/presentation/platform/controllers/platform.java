package aservio.presentation.platform.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class platform implements Initializable {
    @FXML
    private MenuBar menuBar;
    @FXML
    private BorderPane borderPane;
    @FXML
    private TabPane tabPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            borderPane.setLeft(FXMLLoader.load(getClass().getResource("/aservio/presentation/platform/views/Profile.fxml")));
            tabPane.getTabs().get(0).setContent(FXMLLoader.load(getClass().getResource("/aservio/presentation/management/views/FXMLManager.fxml")));
            setTabIcon(tabPane.getTabs().get(0), "resources/OrganiserWithoutText.png", "Planlægning");
            setTabIcon(tabPane.getTabs().get(1), "resources/DiaryWithoutText.png", "Dagbog");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setTabIcon(Tab tab, String imageURL, String name){
        File file = new File(imageURL);
        Image logo = new Image(file.toURI().toString());
        ImageView logoImageView = new ImageView(logo);
        logoImageView.setFitHeight(30);
        logoImageView.setFitWidth(30);
        logoImageView.setPreserveRatio(true);
        tab.setGraphic(logoImageView);
        tab.setText(name);

    }
}
