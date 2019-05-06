package aservio.domain;

import aservio.domain.platform.interfaces.contracts.IDataPipe;

public class DomainInterfaceManager {
    private static IDataPipe iDataPipe;

    public void setIDataPipe(IDataPipe implementation){
        this.iDataPipe = implementation;
    }

    public static IDataPipe getIDataPipe(){
        return iDataPipe;
    }
}
