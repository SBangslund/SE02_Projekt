package aservio.presentation;

import aservio.presentation.management.interfaces.contracts.IOverview;
import aservio.presentation.platform.interfaces.contracts.ILogin;

public class PresentationInterfaceManager {
    private static IOverview iOverview;
    private static ILogin iLogin;

    public void setIOverview(IOverview implementation) {
        iOverview = implementation;
    }

    public static IOverview getIOverview() {
        return iOverview;
    }

    public static ILogin getILogin() {
        return iLogin;
    }

    public void setILogin(ILogin implementation) {
        iLogin = implementation;
    }
}

