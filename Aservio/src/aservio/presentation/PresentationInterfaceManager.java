package aservio.presentation;

import aservio.presentation.journal.interfaces.contracts.IJournal;
import aservio.presentation.journal.interfaces.contracts.IJournalOverview;
import aservio.presentation.management.interfaces.contracts.IOverview;
import aservio.presentation.management.interfaces.contracts.ISideView;
import aservio.presentation.platform.interfaces.contracts.IAddProfile;
import aservio.presentation.platform.interfaces.contracts.ILogin;
import aservio.presentation.platform.interfaces.contracts.IProfile;

public class PresentationInterfaceManager {

    private static IOverview iOverview;
    private static ILogin iLogin;
    private static IProfile iProfile;
    private static IJournalOverview iJournalOverview;
    private static IJournal iJournal;
    private static ISideView iSideView;
    private static IAddProfile iAddProfile;

    public static IOverview getIOverview() {
        return iOverview;
    }

    public static ILogin getILogin() {
        return iLogin;
    }

    public static IProfile getIProfile() {
        return iProfile;
    }

    public static ISideView getISideView() {
        return iSideView;
    }

    public void setIOverview(IOverview implementation) {
        iOverview = implementation;
    }

    public static IJournalOverview getiJournalOverview() {
        return iJournalOverview;
    }

    public static IJournal getIJournal() {
        return iJournal;
    }

    public static IAddProfile getIAddProfile() {
        return iAddProfile;
    }

    public void setILogin(ILogin implementation) {
        iLogin = implementation;
    }

    public void setIProfile(IProfile implementation) {
        iProfile = implementation;
    }

    public void setISideView(ISideView implementation) {
        iSideView = implementation;
    }

    public void setiJournalOverview(IJournalOverview implementation) {
        iJournalOverview = implementation;
    }

    public void setiJournal(IJournal implementation) {
        iJournal = implementation;
    }

    public void setiAddProfile(IAddProfile implmentation) {
        iAddProfile = implmentation;
    }
    
}