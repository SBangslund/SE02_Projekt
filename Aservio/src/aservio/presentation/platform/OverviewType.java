package aservio.presentation.platform;

/**
 * The enum class ensure that we can fetch the right path
 * @author Rene_
 */
public enum OverviewType {
    MONTH(0, "/aservio/presentation/management/views/FXMLOverviewMonth.fxml"),
    WEEK(1, "/aservio/presentation/management/views/FXMLOverviewWeek.fxml"),
    DAY(2, "/aservio/presentation/management/views/FXMLOverviewDay.fxml"),
    CREATENOTE(3, "/aservio/presentation/journal/views/CreateNote.fxml"),
    NOTE(4, "/aservio/presentation/journal/views/Notes.fxml"),
    PRESCRIPTION(5, "/aservio/presentation/journal/views/Prescription.fxml"),
    DIAGNOSING(6, "/aservio/presentation/journal/views/Diagnosing.fxml"),
    SERVICE(7, "/aservio/presentation/journal/views/Service.fxml");

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
