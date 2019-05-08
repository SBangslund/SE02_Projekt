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

    private String personaleFirstName;
    private String personaleLastName;
    private String title;

    public HeaderNote(String personaleFirstName, String personaleLastName, String title) {
        this.personaleFirstName = personaleFirstName;
        this.personaleLastName = personaleLastName;
        this.title = title;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
}
