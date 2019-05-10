/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 *
 * @author victo
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

    public boolean addNote(UUID noteid, Date noteDate, String startTime, String endTime, String noteText) {
        try {
            createStatement().executeQuery("SELECT addNotes('" + noteid.toString() + "', '" + noteDate.getTime() + "', '" + startTime + "', " + endTime + "', " + noteText + "')");
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(IRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public boolean addNoteToUser(UUID userid, UUID noteid){
         try {
            createStatement().executeQuery("SELECT addNoteToUser('" + userid.toString() + "', '" + noteid.toString() + "')");
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(IRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}