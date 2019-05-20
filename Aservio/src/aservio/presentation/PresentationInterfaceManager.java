package aservio.presentation;

import aservio.presentation.journal.interfaces.contracts.IJournal;
import aservio.presentation.journal.interfaces.contracts.IJournalOverview;
import aservio.presentation.management.interfaces.contracts.IOverviewManager;
import aservio.presentation.management.interfaces.contracts.ISideView;
import aservio.presentation.platform.interfaces.contracts.IAddProfile;
import aservio.presentation.platform.interfaces.contracts.ILogin;
import aservio.presentation.platform.interfaces.contracts.IProfile;

public class PresentationInterfaceManager {

    private static IOverviewManager iOverviewManager;
    private static ILogin iLogin;
    private static IProfile iProfile;
    private static IJournalOverview iJournalOverview;
    private static IJournal iJournal;
    private static ISideView iSideView;
    private static IAddProfile iAddProfile;

    public static IOverviewManager getIOverviewManager() {
        return iOverviewManager;
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

    public void setIOverviewManager(IOverviewManager implementation) {
        iOverviewManager = implementation;
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
