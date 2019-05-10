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
    private Date startTime;
    private Date endTime;
    private String noteText;
    private UserInfo citizenInfo;
    private String title;

    public Note(UUID id, Date date, Date startTime, Date endTime, StringBuilder noteText, UserInfo citizenInfo, String title) {
        this.date = date;
        this.noteid = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.citizenInfo = citizenInfo;
        this.title = title;
        createNoteText(noteText);
    }
    
    private void createNoteText(StringBuilder noteText){
        FooterNote footerNote = new FooterNote(User.getCurrentUser().getUserInfo());
        HeaderNote headerNote = new HeaderNote(citizenInfo, title);
        StringBuilder sb = new StringBuilder();
        sb.append(headerNote);
        sb.append(noteText);
        sb.append(footerNote);
        this.noteText = sb.toString();
        System.out.println(sb);
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

    public Date getStartDate() {
        return startTime;
    }

    public void setStartDate(Date startDate) {
        this.startTime = startDate;
    }

    public Date getEndDate() {
        return endTime;
    }

    public void setEndDate(Date endDate) {
        this.endTime = endDate;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }
    
    

}
