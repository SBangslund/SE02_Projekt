package aservio.domain.platform.user;

import java.io.Serializable;


public class Permissions implements Serializable {
    
    private boolean test1;
    private boolean sePlanlægning;
    private boolean redigerPlanlægning;

    //Returns a boolean for different permissions.
    public boolean isTest1() {
        return test1;
    }
    public boolean isSePlanlægning() {
        return sePlanlægning;
    }
    public boolean isRedigerPlanlægning() {
        return redigerPlanlægning;
    }
    public void setTest1(boolean test1) {
        this.test1 = test1;
    }
    public void setSePlanlægning(boolean sePlanlægning) {
        this.sePlanlægning = sePlanlægning;
    }
    public void setRedigerPlanlægning(boolean redigerPlanlægning) {
        this.redigerPlanlægning = redigerPlanlægning;
    }
    
    
}
