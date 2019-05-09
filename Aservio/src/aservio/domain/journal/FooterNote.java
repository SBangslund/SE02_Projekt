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

    private String personaleFirstName;
    private String personaleLastName;
    private String residence;

    public FooterNote(String personaleFirstName, String personaleLastName, String residence) {
        this.personaleFirstName = personaleFirstName;
        this.personaleLastName = personaleLastName;
        this.residence = residence;
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

    public String getRecsidence() {
        return residence;
    }

    public void setRecsidence(String recsidence) {
        this.residence = recsidence;
    }

    @Override
    public String toString() {
        return personaleFirstName + " " + personaleLastName + " + " + residence;
    }
    
}
