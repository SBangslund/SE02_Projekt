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
public class FooterNote {

    private String citizenFirstName;
    private String citizenLastName;
    private String recsidence;

    public FooterNote(String citizenFirstName, String citizenLastName, String recsidence) {
        this.citizenFirstName = citizenFirstName;
        this.citizenLastName = citizenLastName;
        this.recsidence = recsidence;
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

    public String getRecsidence() {
        return recsidence;
    }

    public void setRecsidence(String recsidence) {
        this.recsidence = recsidence;
    }
    

}
