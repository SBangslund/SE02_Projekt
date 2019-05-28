package aservio.domain.journal;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Rene_
 */
public class NoteList {

    private List<Note> noteList = new ArrayList<>();
    
    /**
     * Ensures that the user can add a note to a list, so it later can be visualized together
     * @param note 
     */
    public void add(Note note) {
        noteList.add(note);
    }

    /**
     * Removes a note from the note list if it no longer is needed
     * @param note 
     */
    public void remove(Note note) {
        noteList.remove(note);
    }

    public List<Note> getNotes() {
        return this.noteList;
    }

    public String toString() {
        return noteList.toString();
    }
}
