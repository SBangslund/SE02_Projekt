package aservio.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Used as a module in {@link IRepositoryImp} to organize and as an implementation to get access to everything related to documents
 * Similar to {@link UserRetriever} and {@link ActivityRetriever}
 *
 */
public class DocumentRetriever {

    private final Connection connection;

    public DocumentRetriever(Connection connection) {
        this.connection = connection;
    }
    
    private Statement createStatement() {
        Statement execStat = null;
        try {
            execStat = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        } catch (SQLException ex) {
            Logger.getLogger(IRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return execStat;
    }

    public boolean addNote(String noteText, long noteDate, UUID noteid) {
        try {
            createStatement().executeQuery("SELECT addNotes('" + noteText + "', " + noteDate + ", '" + noteid.toString() + "')");
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(IRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean addNoteToUser(UUID userid, UUID noteid) {
        try {
            createStatement().executeQuery("SELECT addNoteToUser('" + userid.toString() + "', '" + noteid.toString() + "')");
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(IRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public String[] getNotesFromUser(UUID userid) {
        try {
            ResultSet result = createStatement().executeQuery("SELECT get_notes_from_user('" + userid.toString() + "')");
            int countNotes = 0;
            while (result.next()) {
                countNotes++;
            }
            ResultSet resultLate = createStatement().executeQuery("SELECT get_notes_from_user('" + userid.toString() + "')");
            String[] resultArr = new String[countNotes];

            int index = 0;
            while (resultLate.next()) {
                resultArr[index] = resultLate.getString(1);
                index++;
            }
            if (resultArr.length > 1) {
                return resultArr;
            }

        } catch (SQLException ex) {
            Logger.getLogger(DocumentRetriever.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
        public String[] getNote(UUID noteid) {
        try {
            ResultSet result = createStatement().executeQuery("SELECT * FROM get_note('" + noteid.toString() + "')");
            String[] resultArr = null;
            if (result.next()) {
                resultArr = new String[3];
                for (int i = 0; i < resultArr.length; i++) {
                    resultArr[i] = result.getString(i + 1);
                }
            }
            return resultArr;
        } catch (SQLException ex) {
            Logger.getLogger(IRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
