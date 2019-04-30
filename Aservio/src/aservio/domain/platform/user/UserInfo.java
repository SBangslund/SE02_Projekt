/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aservio.domain.platform.user;

import javafx.scene.image.Image;

/**
 *
 * @author victo
 */
public class UserInfo {
    private Address address;
    private Image image;
    private int mobileNumber;
    private String firstName;
    private String lastName;
    private String mail;
    private String institution;

    public UserInfo(Address address, Image image, int mobileNumber, String firstName, String lastName, String mail, String institution) {
        this.address = address;
        this.image = image;
        this.mobileNumber = mobileNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mail = mail;
        this.institution = institution;
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

    public String getInstitution() {
        return institution;
    }
    
    
}
