package aservio.management.sideview;

import aservio.management.activities.Activity;
import aservio.management.activities.ActivityList;
import aservio.management.activities.ActivityType;
import aservio.platform.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class SideViewActivity extends SideView implements Initializable {

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

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

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
