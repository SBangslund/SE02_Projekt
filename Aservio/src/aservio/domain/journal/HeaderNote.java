/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aservio.domain.journal;

/**
 *
 * @author Rene_
 */
public class HeaderNote {

    private String citizenFirstName;
    private String citizenLastName;
    private String title;

    public HeaderNote(String citizenFirstName, String citizenLastName, String title) {
        this.citizenFirstName = citizenFirstName;
        this.citizenLastName = citizenLastName;
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
        return citizenFirstName + " " + citizenLastName + "+" + title;
    }
    
}
