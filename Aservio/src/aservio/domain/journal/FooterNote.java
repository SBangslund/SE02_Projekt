/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aservio.domain.journal;

import aservio.domain.platform.user.UserInfo;

/**
 *
 * @author Rene_
 */
public class FooterNote {

    private String personaleFirstName;
    private String personaleLastName;
    private int residence;

    public FooterNote(UserInfo personaleInfo) {
        this.personaleFirstName = personaleInfo.getFirstName();
        this.personaleLastName = personaleInfo.getLastName();
        this.residence = personaleInfo.getInstitution();
    }

    public String getPersonaleFirstName() {
        return personaleFirstName;
    }

    public void setPersonaleFirstName(String personaleFirstName) {
        this.personaleFirstName = personaleFirstName;
    }

    public String getPersonaleLastName() {
        return personaleLastName;
    }

    public void setPersonaleLastName(String personaleLastName) {
        this.personaleLastName = personaleLastName;
    }

    public int getResidence() {
        return residence;
    }

    public void setResidence(int residence) {
        this.residence = residence;
    }

    @Override
    public String toString() {
        return String.format("\nOprettet af: %15s\nResidence: %15s\n", personaleFirstName + " " + personaleLastName, residence);
        
    }
    
}
