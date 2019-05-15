package aservio.presentation.management.controllers.sideview;

import aservio.domain.management.activities.Activity;
import aservio.domain.management.activities.ActivityList;
import aservio.domain.management.activities.ActivityType;
import aservio.domain.platform.user.User;
import aservio.presentation.management.controllers.Management;
import aservio.presentation.platform.interfaces.PermissionLimited;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class SideViewActivity extends SideView implements Initializable, PermissionLimited {
    public ToolBar toolbarAddRemove;
    private Map<User, ActivityList> userActivities = new HashMap<>();
    public Button addButton;
    public Button modifyButton;
    public Button removeButton;
    @FXML
    private HBox activityBox;
    @FXML
    private Label timeStartText;
    @FXML
    private Label timeEndText;
    @FXML
    private Label activityName;
    @FXML
    private Text activityDescription;
    @FXML
    private Label dateText;

    private Activity selectedActivity;
    @FXML
    private VBox sideViewVbox;

    private SideViewCreate sideViewCreate;
    @FXML
    private Pane activityLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private Label timeStartLabel;
    @FXML
    private Label timeEndLabel;

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
        this.applyPermissionLimitations();
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
        if (activityBox.getChildren().size() > 2) {
            activityBox.getChildren().remove(2);
        }

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
        FXMLLoader loader = new FXMLLoader();
        try {
            sideViewCreate.setEdit(false);
            Pane p = loader.load(getClass().getResource("/aservio/presentation/management/views/FXMLActivityCreate.fxml").openStream());
            sideViewVbox.getChildren().add(p);
        } catch (IOException e) {
            e.printStackTrace();
        }

//        
//        LocalDateTime startdt = LocalDateTime.now();
//        LocalDateTime enddt = startdt.plusHours(2);
//        Date startdate = Date.from(startdt.atZone(ZoneId.systemDefault()).toInstant());
//        Date enddate = Date.from(enddt.atZone(ZoneId.systemDefault()).toInstant());
//        Activity activity1 = new Activity(ActivityType.WALK, startdate, enddate, UUID.randomUUID()); //QQ id: UUID.fromString("dc1e324b-cca4-499d-871f-8ff9076f214c"
//        System.out.println(startdate);
//        System.out.println(enddate);
//        interFace.addActivity(activity1, User.getCurrentUser().getId());
        updateView();
    }


    @FXML
    public void handleModify(ActionEvent actionEvent) {
        if(selectedActivity != null) {
            FXMLLoader loader = new FXMLLoader();
            try {
                Pane p = loader.load(getClass().getResource("/aservio/presentation/management/views/FXMLActivityCreate.fxml").openStream());
                sideViewCreate = loader.getController();
                sideViewCreate.setEdit(true);
                sideViewCreate.setActivityToBeEditet(selectedActivity);
                sideViewCreate.initialize();


                sideViewVbox.getChildren().add(p);
            } catch (IOException e) {
                e.printStackTrace();
            }
            updateView();
        }
    }

    @FXML
    public void handleRemove(ActionEvent actionEvent) {
        //delete selected activity this view (overview)
        activityName.setText(selectedActivity.getActivityType().getName() + " (Deleted)");
        //delete selected activity db (repository)
        if (interFace.deleteActivity(selectedActivity.getId())) {
            System.out.println("Deleted activity from db");
        } else {
            System.out.println("Activity was not deleted from db");
        }
        updateView();
    }

    private void updateView() {
        Management.getInstance().getOverviewManager().updateCurrentView();
    }


    @Override
    public void applyPermissionLimitations() {
        toolbarAddRemove.setVisible(DEFAULT_PERMISSIONS.canEditActivities());
    }
}
