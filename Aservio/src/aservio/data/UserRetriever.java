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


    public UserRetriever(Connection connection) {
        this.connection = connection;
    }


    boolean addUser(String username, String password, UUID userid) {
        Statement execStat = null;
        try {
            execStat = connection.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(IDataPipeImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            execStat.executeQuery("SELECT adduser('" + username + "', '" + password + "', '" + userid.toString() + "')");
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(IDataPipeImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    boolean addUserInfo(String mail, String firstname, String lastname, int phone, String picture, UUID userid, String institutionname) {
        Statement execStat = null;
        try {
            execStat = connection.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(IDataPipeImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            execStat.executeQuery("SELECT adduserinfo('" + mail + "', '" + firstname + "', '" + lastname + "', " + phone + ", '" + picture + "', '" + userid.toString() + "', '" + institutionname + "')");
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(IDataPipeImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    boolean addUserAddress(String roadname, String country, int postcode, String city, String housenumber, String level, UUID userid) {
        Statement execStat = null;
        try {
            execStat = connection.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(IDataPipeImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            execStat.executeQuery("SELECT adduserinfo('" + roadname + "', '" + country + "', " + postcode + ", '" + city + "', '" + housenumber + "', '" + level + "', '" + userid.toString() + "')");
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(IDataPipeImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
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
        return null;
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
            ResultSet result = execStat.executeQuery("SELECT getusersfromActivity('" + activityid.toString() + "')");
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
            if (result.getFetchSize() >= 1) {
                return resultArr;
            }
        } catch (SQLException ex) {
            Logger.getLogger(IDataPipeImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }


}
