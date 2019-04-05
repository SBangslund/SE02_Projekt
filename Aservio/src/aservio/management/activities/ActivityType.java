package aservio.management.activities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 * All the different types of activities the system support. The main limit is the different icons needed to be designed
 * and implemented.
 *
 * @see Activity
 */
public enum ActivityType {

    EAT("Eating", "Activity related to eating", "src/aservio/management/icons/iconEating.jpg", Color.BLUE),
    RUN("Running", "Activity related to running", "src/aservio/management/icons/iconRunning.png", Color.CORAL),
    WALK("Walking", "Activity related to walking", "src/aservio/management/icons/iconWalking.svg", Color.CRIMSON),
    VOLLEY("Vollay", "Activity related to playing volley", "src/aservio/management/icons/iconVolleyball.png", Color.DARKGREEN),
    TENNIS("Tennis", "Activity related to playing tennis", "src/aservio/management/icons/iconTennis.png", Color.YELLOW);


    /**
     * Name of the activity type.
     */
    private String name;

    /**
     * Description of the activity type.
     */
    private String description;

    /**
     * The icon of the activity type.
     */
    private Image icon;
    private Color color;
    private String iconURL;

    /**
     * The type of a specific {@link Activity}. This includes a name, a description and an icon. The icon is not initially set
     * and needs to be set at system initialization.
     *
     * @param name        The name of the activity.
     * @param description The description of the activity.
     */
    ActivityType(String name, String description, String iconLink, Color color) {
        this.iconURL = iconLink;
        this.name = name;
        this.description = description;
        this.icon = new Image(new File(iconLink).toURI().toString());
        this.color = color;
    }

    /**
     * Set the icon for the {@link ActivityType}. This is not set by the constructor and must be set before use.
     *
     * @param icon The icon representing this {@link ActivityType}.
     */
    public void setIcon(Image icon) {
        this.icon = icon;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public Image getIcon() {
        File file = new File(iconURL);
        Image image = new Image(file.toURI().toString());
        return image;
    }
}
