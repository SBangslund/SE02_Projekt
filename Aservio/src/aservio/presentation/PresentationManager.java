package aservio.presentation;

import aservio.presentation.management.interfaces.contracts.IOverview;

public class PresentationManager {
    private static IOverview iOverview;

    public void setIOverview(IOverview implementation) {
        iOverview = implementation;
    }

    public static IOverview getIOverview() {
        return iOverview;
    }
}
