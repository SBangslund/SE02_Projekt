package aservio.presentation;

import aservio.presentation.management.interfaces.contracts.IOverview;
import aservio.presentation.platform.interfaces.contracts.ILogin;
import aservio.presentation.platform.interfaces.contracts.IProfile;

public class PresentationInterfaceManager {
    private static IOverview iOverview;
    private static ILogin iLogin;
    private static IProfile iProfile;

    public static IOverview getIOverview() {
        return iOverview;
    }

    public static ILogin getILogin() {
        return iLogin;
    }

    public static IProfile getIProfile() {return iProfile;}

    public void setIOverview(IOverview implementation) {
        iOverview = implementation;
    }

    public void setILogin(ILogin implementation) {
        iLogin = implementation;
    }

    public void setIProfile(IProfile implementation) {
        iProfile = implementation;
    }
}

