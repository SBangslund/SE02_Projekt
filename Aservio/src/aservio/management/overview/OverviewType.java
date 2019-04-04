package aservio.management.overview;

public enum OverviewType {
    MONTH(0, "../views/FXMLOverviewMonth.fxml"),
    WEEK(1, "../views/FXMLOverviewWeek.fxml"),
    DAY(2, "../views/FXMLOverviewDay.fxml");

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
