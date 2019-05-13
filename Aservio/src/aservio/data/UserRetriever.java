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

    private Statement createStatement() {
        Statement execStat = null;
        try {
            execStat = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        } catch (SQLException ex) {
            Logger.getLogger(IRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return execStat;
    }

    boolean addUser(String username, String password, UUID userid) {
        try {
            createStatement().executeQuery("SELECT adduser('" + username + "', '" + password + "', '" + userid.toString() + "')");
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(IRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    boolean addUserInfo(String mail, String firstname, String lastname, int phone, String picture, UUID userid, int institutionid) {
        try {
            createStatement().executeQuery("SELECT adduserinfo('" + mail + "', '" + firstname + "', '" + lastname + "', " + phone + ", '" + picture + "', '" + userid.toString() + "', '" + institutionid + "')");
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(IRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    boolean addUserAddress(String roadname, String country, int postcode, String city, String housenumber, String level, UUID userid) {
        try {
            createStatement().executeQuery("SELECT adduseraddress('" + roadname + "', '" + country + "', " + postcode + ", '" + city + "', '" + housenumber + "', '" + level + "', '" + userid.toString() + "')");
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(IRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public String getUser(String username, String password) {
        try {
            ResultSet result = createStatement().executeQuery("SELECT * FROM getuserid('" + username + "', '" + password + "')");
            while (result.next()) {
                return result.getString(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(IRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String verifyUser(String username, String password) {
        try {
            ResultSet result = createStatement().executeQuery("SELECT verifyuser('" + username + "', '" + password + "')");
            while (result.next()) {
                return result.getString(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(IRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String[] getUsersFromActivity(UUID activityid) {
        try {
            ResultSet result = createStatement().executeQuery("SELECT * FROM getusersfromActivity('" + activityid.toString() + "')");
            String[] resultArr = new String[7];
            int index = 0;
            while (result.next()) {
                resultArr[index] = result.getString(index + 1);
                index++;
            }
            return resultArr;
        } catch (SQLException ex) {
            Logger.getLogger(IRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String[] getUsersFromInstitution(int institutionid) {
        try {
            ResultSet result = createStatement().executeQuery("SELECT getusersfrominstitution('" + institutionid +"')");
            String[] resultArr = null;
            if(result.last()) {
                int size = result.getRow();
                resultArr = new String[size];
                result.first();
                for (int i = 0; i < size; i++) {
                    resultArr[i] = result.getString(1);
                    result.next();
                }
            }
            return resultArr;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String[] getUserInfo(UUID userid) {
        try {
            ResultSet result = createStatement().executeQuery("SELECT * FROM getuserinfo('" + userid.toString() + "')");
            String[] resultArr = null;
            if (result.next()) {
                resultArr = new String[7];
                for (int i = 0; i < 7; i++) {
                    resultArr[i] = result.getString(i + 1);
                }
            }
            return resultArr;
        } catch (SQLException ex) {
            Logger.getLogger(IRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String[] getUserAddress(UUID userid) {
        try {
            ResultSet result = createStatement().executeQuery("SELECT * FROM getuseraddress('" + userid + "')");
            String[] resultArr = null;
            if (result.next()) {
                resultArr = new String[7];
                for (int i = 0; i < 7; i++) {
                    resultArr[i] = result.getString(i + 1);
                }
            }
            return resultArr;
        } catch (SQLException ex) {
            Logger.getLogger(IRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String getUserRole(String userid) {
        try {
            ResultSet result = createStatement().executeQuery("SELECT role FROM userinfo WHERE userinfo.userid = '" + userid + "'");
            result.next();
            return result.getString(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String[] getCitizensFromCaretaker(String caretakerID) {
        try {
            ResultSet result = createStatement().executeQuery("SELECT citizen_id from caretaker_has_citizen where caretaker_has_citizen.caretaker_id = '" + caretakerID + "'");
            String[] resultArr = null;
            if(result.last()) {
                int size = result.getRow();
                resultArr = new String[size];
                result.first();
                for (int i = 0; i < size; i++) {
                    resultArr[i] = result.getString(1);
                    result.next();
                }
            }
            return resultArr;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
