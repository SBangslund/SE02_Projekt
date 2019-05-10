package aservio.domain;

import aservio.domain.platform.interfaces.contracts.IRepository;

public class DomainInterfaceManager {
    private static IRepository iRepository;

    public void setIDataPipe(IRepository implementation){
        this.iRepository = implementation;
    }

    public static IRepository getIRepository(){
        return iRepository;
    }
}
