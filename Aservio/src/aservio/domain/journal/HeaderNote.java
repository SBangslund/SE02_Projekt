package aservio.domain.journal;

import aservio.domain.platform.user.UserInfo;

/**
 * Specifying and organising the data, that will be shown in the header of a note, primarily used in {@link Note}
 */
public class HeaderNote {

    private String citizenFirstName;
    private String citizenLastName;
    private String title;

    public HeaderNote(UserInfo citizenInfo, String title) {
        this.citizenFirstName = citizenInfo.getFirstName();
        this.citizenLastName = citizenInfo.getLastName();
        this.title = title;
    }

    public String getCitizenFirstName() {
        return citizenFirstName;
    }

    public void setCitizenFirstName(String citizenFirstName) {
        this.citizenFirstName = citizenFirstName;
    }

    public String getCitizenLastName() {
        return citizenLastName;
    }

    public void setCitizenLastName(String citizenLastName) {
        this.citizenLastName = citizenLastName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return String.format("%s+%s+", title, citizenFirstName + citizenLastName);

    }

}
