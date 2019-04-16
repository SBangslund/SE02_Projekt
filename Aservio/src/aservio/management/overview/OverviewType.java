package aservio.management.overview;

public enum OverviewType {
    MONTH(0, "/aservio/management/views/FXMLOverviewMonth.fxml"),
    WEEK(1, "/aservio/management/views/FXMLOverviewWeek.fxml"),
    DAY(2, "/aservio/management/views/FXMLOverviewDay.fxml");

    private int type;
    private String url;

    OverviewType(int type, String url) {
        this.type = type;
        this.url = url;
    }

    public String getURL() {
        return this.url;
    }
}
