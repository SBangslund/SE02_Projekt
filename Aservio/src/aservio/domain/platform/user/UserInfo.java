package aservio.domain.platform.user;

import java.io.Serializable;
import java.util.UUID;

import javafx.scene.image.Image;

public class UserInfo implements Serializable{
    private UUID id;
    private Address address;
    private Image image;
    private int mobileNumber;
    private String firstName;
    private String lastName;
    private String mail;
    private int institutionid;

    public UserInfo(Address address, Image image, int mobileNumber, String firstName, String lastName, String mail, int institutionid, UUID id) {
        this.address = address;
        this.image = image;
        this.mobileNumber = mobileNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mail = mail;
        this.institutionid = institutionid;
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format("%s %s", firstName, lastName);
    }

    public Address getAddress() {
        return address;
    }

    public Image getImage() {
        return image;
    }

    public int getMobileNumber() {
        return mobileNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMail() {
        return mail;
    }

    public int getInstitution() {
        return institutionid;
    }
    
    public UUID getId() {
        return id;
    }
}
