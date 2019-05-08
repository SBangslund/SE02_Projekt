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
 private Date startDate;
 private Date endDate;
 private StringBuilder noteText;

    public Note(UUID id, Date startDate, Date endDate) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public StringBuilder getNoteText() {
        return noteText;
    }

    public void setNoteText(StringBuilder noteText) {
        this.noteText = noteText;
    }
    
 
 
}
