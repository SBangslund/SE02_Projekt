/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aservio.domain.journal;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Rene_
 */
public class NoteList {

    private List<Note> noteList = new ArrayList<>();

    public void add(Note note) {
        noteList.add(note);
    }

    public void remove(Note note) {
        noteList.remove(note);
    }

    public List<Note> getNotes() {
        return this.noteList;
    }
}
