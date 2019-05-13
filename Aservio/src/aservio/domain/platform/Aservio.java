/*
 * Created by Samuel Bangslund, Odense SDU Software Engineering 1. semester.
 */
package aservio.domain.platform;

import aservio.data.IRepositoryImp;
import aservio.domain.DomainInterfaceManager;
import aservio.domain.journal.interfaces.implementors.IJournalOverviewImp;
import aservio.domain.management.interfaces.implementors.IOverviewImp;
import aservio.domain.management.interfaces.implementors.ISideViewImp;
import aservio.domain.platform.interfaces.implementors.ILoginWithDBImp;
import aservio.domain.platform.interfaces.implementors.IProfileImp;
import aservio.presentation.PresentationInterfaceManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;

/**
 *
 * @author sbang
 */
public class Aservio extends Application {

    private static Aservio instance;
    private Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        instance = this;
        primaryStage = stage;


        DomainInterfaceManager domain = new DomainInterfaceManager();
        domain.setIDataPipe(new IRepositoryImp());

        PresentationInterfaceManager presentation = new PresentationInterfaceManager();

        Repository repository = new Repository();
        presentation.setIOverview(new IOverviewImp(repository));
        presentation.setILogin(new ILoginWithDBImp(repository));
        presentation.setIProfile(new IProfileImp(repository));
        presentation.setISideView(new ISideViewImp(repository));
        presentation.setiJournalOverview(new IJournalOverviewImp(repository));


        //Parent root = FXMLLoader.load(getClass().getResource("../Management/views/FXMLManager.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("/aservio/presentation/platform/views/Login.fxml"));

        Scene scene = new Scene(root);

        stage.setMinWidth(500);
        stage.setMinHeight(500);
        stage.setTitle("Aservio");
        stage.getIcons().add(new Image(new File("resources/logo/LogoIcon.png").toURI().toString()));

        stage.setScene(scene);
        stage.show();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        
        //UserInfo(Address address, Image image, String mobileNumber, String name, String mail, Address institution) {
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static Aservio getInstance() {
        return instance;
    }

}
