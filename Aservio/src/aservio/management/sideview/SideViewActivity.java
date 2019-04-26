package aservio.management.sideview;

import aservio.management.activities.Activity;
import aservio.management.activities.ActivityList;
import aservio.management.activities.ActivityType;
import aservio.platform.user.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class SideViewActivity extends SideView implements Initializable {

    public Button addButton;
    public Button modifyButton;
    public Button removeButton;
    @FXML
    private HBox activityBox;
    @FXML
    private Label timeStartLabel;
    @FXML
    private Label timeStartText;
    @FXML
    private Label timeEndLabel;
    @FXML
    private Label timeEndText;
    @FXML
    private Label activityName;
    @FXML
    private Pane activityLabel;
    @FXML
    private Text activityDescription;
    @FXML
    private Label dateLabel;
    @FXML
    private Label dateText;

    private Map<User, ActivityList> userActivities = new HashMap<>();
    private Activity selectedActivity;

    @Override
    protected void initialize() {

        modifyButton.setText("Rediger");
        modifyButton.getStyleClass().add("button_modify");
        addButton.setText("Tilføj");
        addButton.getStyleClass().add("button_add");
        removeButton.setText("Slet");
        removeButton.getStyleClass().add("button_remove");

//        setButtonImage(modifyButton, "resources/generalIcons/modifyIcon.png", "button_modify");
//        setButtonImage(addButton, "resources/generalIcons/addIcon.png", "button_add");
//        setButtonImage(removeButton, "resources/generalIcons/removeIcon.png", "button_remove");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    //could be implementend, in case we want icons as buttons, instead of text
//    public void setButtonImage(Button button, String path, String style){
//        File modify = new File(path);
//        ImageView modifyImage = new ImageView(new Image(modify.toURI().toString()));
//        modifyImage.setFitHeight(20);
//        modifyImage.setFitWidth(20);
//        modifyImage.setPreserveRatio(true);
//        button.setGraphic(modifyImage);
//        button.getStylesheets().add(style);
//    }

    public void showActivity(Activity activity) {
        if(activityBox.getChildren().size() > 2)
            activityBox.getChildren().remove(2);

        selectedActivity = activity;
        activityName.setText(activity.getActivityType().getName());
        activityBox.getChildren().add(createActivityLabel(activity.getActivityType()));
        activityDescription.setText(activity.getDescription());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String dateString = dateFormat.format(activity.getStartDate());
        dateText.setText(dateString);

        SimpleDateFormat timeStartFormat = new SimpleDateFormat("HH:mm:ss");
        String timeStartString = timeStartFormat.format(activity.getStartDate());
        timeStartText.setText(timeStartString);

        SimpleDateFormat timeEndFormat = new SimpleDateFormat("HH:mm:ss");
        String timeEndString = timeEndFormat.format(activity.getEndDate());
        timeEndText.setText(timeEndString);
        
    }

    private Pane createActivityLabel(ActivityType activityType) {
        HBox box = new HBox();
        Pane pane = new Pane();
        pane.setPrefWidth(10);
        String defaultColor = String.format("#%02X%02X%02X",
                (int) (activityType.getColor().getRed() * 255),
                (int) (activityType.getColor().getGreen() * 255),
                (int) (activityType.getColor().getBlue() * 255));
        pane.setStyle("-fx-background-color: " + defaultColor);

        ImageView icon = new ImageView(activityType.getIcon());
        icon.setPreserveRatio(true);
        icon.setFitHeight(50);

        box.getChildren().add(icon);
        box.getChildren().add(pane);
        return box;
    }

    @FXML
    public void handleAdd(ActionEvent actionEvent) {
    }

    @FXML
    public void handleModify(ActionEvent actionEvent) {
    }

    @FXML
    public void handleRemove(ActionEvent actionEvent) {
    }
}
