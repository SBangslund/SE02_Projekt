package aservio.domain.journal;

import aservio.domain.platform.user.User;
import aservio.domain.platform.user.UserInfo;
import java.util.Date;
import java.util.UUID;

/**
 *
 * @author Rene_
 */
public class Note {

    private UUID noteid;
    private Date date;
    private String noteText;
    private UserInfo citizenInfo;
    private String title;
    private String caretakerName;

    public Note(UUID id, Date date, String noteText, UserInfo citizenInfo, String title, String caretakerName) {
        this.date = date;
        this.noteid = id;
        this.citizenInfo = citizenInfo;
        this.title = title;
        this.noteText = noteText;
        this.caretakerName = caretakerName;
    }

    /**
     * Creates a notetext so it can be displayed
     * @param noteText 
     */
    public void createNoteText(String noteText) {
        FooterNote footerNote = new FooterNote(User.getCurrentUser().getUserInfo());
        HeaderNote headerNote = new HeaderNote(citizenInfo, title);
        StringBuilder sb = new StringBuilder();
        sb.append(headerNote);
        sb.append(noteText);
        sb.append(footerNote);
        this.noteText = sb.toString();
    }

    public UUID getId() {
        return noteid;
    }

    public Date getDate() {
        return date;
    }

    public UserInfo getCitizenInfo() {
        return citizenInfo;
    }

    public String getNoteText() {
        return noteText;
    }

    public String getCaretakerInfo() {
        return caretakerName;
    }

    public String getTitle() {
        return title;
    }

    public void setId(UUID id) {
        this.noteid = id;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    @Override
    public String toString() {
        return String.format("%10s d.%10s", title, date);

    }

}
