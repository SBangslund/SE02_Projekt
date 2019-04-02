package aservio.management.activities;

import javafx.scene.image.Image;

/**
 * All the different types of activities the system support. The main limit is the different icons needed to be designed
 * and implemented.
 *
 * @see Activity
 */
public enum ActivityType {

    EAT("Eating", "Activity related to eating");

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

    /**
     * The type of a specific {@link Activity}. This includes a name, a description and an icon. The icon is not initially set
     * and needs to be set at system initialization.
     *
     * @param name        The name of the activity.
     * @param description The description of the activity.
     */
    ActivityType(String name, String description) {
        this.name = name;
        this.description = description;
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
        return this.icon;
    }
}
