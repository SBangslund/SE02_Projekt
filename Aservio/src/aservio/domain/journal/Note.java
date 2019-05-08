/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aservio.domain.journal;

import aservio.domain.platform.user.User;
import java.util.Date;
import java.util.UUID;

/**
 *
 * @author Rene_
 */
public class Note {
 private UUID id;
 private Date date;
 private Date startTime;
 private Date endTime;
 private StringBuilder noteText;

    public Note(UUID id, Date date, Date startTime, Date endTime) {
        this.date = date;
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public StringBuilder getNoteText() {
        return noteText;
    }

    public void setNoteText(StringBuilder noteText) {
        this.noteText = noteText;
    }
    
 
 
}
