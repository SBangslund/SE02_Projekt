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

    private boolean addUser(String username, String password, String userid, String mail, String firstname, String lastname, int phone, String picture, String instituionname){
        return false;
    }

    public UserRetriever(Connection connection) {
        this.connection = connection;
    }

    public String getUser(String username, String password) {
        Statement execStat = null;
        try {
            execStat = connection.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(IDataPipeImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            ResultSet result = execStat.executeQuery("SELECT getuserid('" + username + "', '" + password + "')");
            while (result.next()) {
                return result.getString(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(IDataPipeImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Method not working";
    }

    public String verifyUser(String username, String password) {
        Statement execStat = null;
        try {
            execStat = connection.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(IDataPipeImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            ResultSet result = execStat.executeQuery("SELECT verifyuser('" + username + "', '" + password + "')");
            while (result.next()) {
                return result.getString(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(IDataPipeImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String[] getUsers(UUID activityid) {
        Statement execStat = null;
        try {
            execStat = connection.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(IDataPipeImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            ResultSet result = execStat.executeQuery("SELECT getusersfromActivity('" + userid.toString() + "')");
            String[] resultArr = new String[7];
            result.getFetchSize();
            int index = 0;
            while (result.next()) {
                resultArr[index] = result.getString(index + 1);
                index++;
            }
            return resultArr;
        } catch (SQLException ex) {
            Logger.getLogger(IDataPipeImp.class.getName()).log(Level.SEVERE, null, ex);
        }
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
            while (result.next()) {
                resultArr[index] = result.getString(index);
                index++;
            }
            if (result.getFetchSize() >= 1) {
                return resultArr;
            }
        } catch (SQLException ex) {
            Logger.getLogger(IDataPipeImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String[] getUserAdress(UUID userid) {
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
            while (result.next()) {
                resultArr[index] = result.getString(index);
                index++;
            }
            if(result.getFetchSize() >= 1) {
                return resultArr;
            }
        } catch (SQLException ex) {
            Logger.getLogger(IDataPipeImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }


}
