package aservio.domain.platform.interfaces.implementors;

import aservio.domain.DomainInterfaceManager;
import aservio.presentation.platform.interfaces.contracts.ILogin;

public class ILoginWithDBImp implements ILogin {


    @Override
    public void tempUserSetupByFile() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String checkForNoIllegalInput(String username, String password) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String verifyUser(String username, String password) {
        return DomainInterfaceManager.getIDataPipe().verifyUser(username, password);
    }

    @Override
    public void loadScene() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
