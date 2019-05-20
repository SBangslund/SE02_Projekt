package aservio.presentation;

import aservio.presentation.journal.interfaces.contracts.IJournal;
import aservio.presentation.journal.interfaces.contracts.IJournalOverview;
import aservio.presentation.management.interfaces.contracts.IOverviewManager;
import aservio.presentation.management.interfaces.contracts.ISideView;
import aservio.presentation.platform.interfaces.contracts.IAddProfile;
import aservio.presentation.platform.interfaces.contracts.ILogin;
import aservio.presentation.platform.interfaces.contracts.IProfile;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PresentationInterfaceManager {

    private static IOverviewManager iOverviewManager;
    private static ILogin iLogin;
    private static IProfile iProfile;
    private static IJournalOverview iJournalOverview;
    private static IJournal iJournal;
    private static ISideView iSideView;
    private static IAddProfile iAddProfile;

    public static IOverviewManager getIOverviewManager() {
        return iOverviewManager;
    }

    public static ILogin getILogin() {
        return iLogin;
    }

    public static IProfile getIProfile() {
        return iProfile;
    }

    public static ISideView getISideView() {
        return iSideView;
    }

    public void setIOverviewManager(IOverviewManager implementation) {
        iOverviewManager = implementation;
    }

    public static IJournalOverview getiJournalOverview() {
        return iJournalOverview;
    }

    public static IJournal getIJournal() {
        return iJournal;
    }

    public static IAddProfile getIAddProfile() {
        return iAddProfile;
    }

    public void setILogin(ILogin implementation) {
        iLogin = implementation;
    }

    public void setIProfile(IProfile implementation) {
        iProfile = implementation;
    }

    public void setISideView(ISideView implementation) {
        iSideView = implementation;
    }

    public void setiJournalOverview(IJournalOverview implementation) {
        iJournalOverview = implementation;
    }

    public void setiJournal(IJournal implementation) {
        iJournal = implementation;
    }

    public void setiAddProfile(IAddProfile implmentation) {
        iAddProfile = implmentation;
    }

    public static void createPopupWindow(PopupType popupType, String message){
        Stage popupStage = new Stage();
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        popupStage.initStyle(StageStyle.TRANSPARENT);
        //set Stage boundaries to the lower right corner of the visible bounds of the main screen
        popupStage.setX(primaryScreenBounds.getMinX() + primaryScreenBounds.getWidth() - 200);
        popupStage.setY(primaryScreenBounds.getMinY() + primaryScreenBounds.getHeight() - 200);
        popupStage.setWidth(200);
        popupStage.setHeight(100);

        VBox popupContent = new VBox();
        popupContent.setStyle("-fx-background-color: " + popupType.getColor() + ";" +
                "-fx-background-radius: 10 0 0 10;" +
                "-fx-border-radius: 10 0 0 10; ");
        Label messageLabel = new Label(message);
        messageLabel.setWrapText(true);
        messageLabel.setTextAlignment(TextAlignment.JUSTIFY);

        messageLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14;-fx-label-padding: 10 10 10 10; -fx-font-weight: BOLD");
        popupContent.getChildren().add(messageLabel);
        Scene popupScene = new Scene(popupContent);
        popupStage.setScene(popupScene);
        popupStage.show();

        Thread ts = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    Platform.runLater(() -> {
                        popupStage.close();
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        ts.start();
    }
    
}
