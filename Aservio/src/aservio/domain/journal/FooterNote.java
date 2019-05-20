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
        return String.format("+%s+", personaleFirstName + " " + personaleLastName);
    }
    
}
