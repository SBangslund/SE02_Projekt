package aservio.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserRetriever {

    private Connection connection;

    public UserRetriever(Connection connection){
        this.connection = connection;
    }

    public String getUser(String username, String password){
        Statement execStat = null;
        try {
            execStat = connection.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(IDataPipeImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            ResultSet result = execStat.executeQuery("SELECT getuserid('" + username + "', '" + password + "')");
            while(result.next()){
                return result.getString(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(IDataPipeImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Method not working";
    }

    public String verifyUser(String username, String password){
        Statement execStat = null;
        try {
            execStat = connection.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(IDataPipeImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            ResultSet result = execStat.executeQuery("SELECT verifyuser('" + username + "', '" + password + "')");
            while(result.next()){
                return result.getString(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(IDataPipeImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String[] getUsers(UUID activityid) {



        return null;
    }

    public String[] getUserInfo(UUID userid) {

        Statement execStat = null;
        try {
            execStat = connection.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(IDataPipeImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            ResultSet result = execStat.executeQuery("SELECT getuserinfo('" + userid.toString() + "')");
            String[] resultArr = new String[7];
            int index = 0;
            while(result.next()){
                resultArr[index] = result.getString(index);
                index++;
            }
            return resultArr;
        } catch (SQLException ex) {
            Logger.getLogger(IDataPipeImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String[] getUserAdress(UUID userid){
        Statement execStat = null;
        try {
            execStat = connection.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(IDataPipeImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            ResultSet result = execStat.executeQuery("SELECT getuseraddress('" + userid.toString() + "')");
            String[] resultArr = new String[7];
            int index = 0;
            while(result.next()){
                resultArr[index] = result.getString(index);
                index++;
            }
            return resultArr;
        } catch (SQLException ex) {
            Logger.getLogger(IDataPipeImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }



}
