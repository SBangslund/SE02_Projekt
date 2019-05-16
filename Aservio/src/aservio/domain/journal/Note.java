/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
    private UserInfo caretakerInfo;

    public Note(UUID id, Date date, String noteText, UserInfo citizenInfo, String title, String caretakerName) {
        this.date = date;
        this.noteid = id;
        this.citizenInfo = citizenInfo;
        this.title = title;
        this.noteText = noteText;
        this.caretakerInfo = caretakerInfo;
    }

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

    public void setId(UUID id) {
        this.noteid = id;
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

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    public UserInfo getCaretakerInfo() {
        return caretakerInfo;
    }

    @Override
    public String toString() {
        return String.format("%10s d.%10s", title, date);

    }

}
