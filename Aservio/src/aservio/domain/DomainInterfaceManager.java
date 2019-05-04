package aservio.domain;

import aservio.domain.platform.interfaces.contracts.IDatabase;

public class DomainInterfaceManager {
    private static IDatabase iDatabase;

    public void setiDatabase(IDatabase implementation){
        this.iDatabase = implementation;
    }

    public static IDatabase getiDatabase(){
        return iDatabase;
    }
}
