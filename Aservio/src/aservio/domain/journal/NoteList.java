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

    public String toString() {
        return noteList.toString();
    }
}
