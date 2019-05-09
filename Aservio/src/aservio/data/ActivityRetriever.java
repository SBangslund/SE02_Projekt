package aservio.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ActivityRetriever {

    Connection connection;

    public ActivityRetriever(Connection connection) {
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
    
    public String[] getActivity(UUID activityid) {
        try {
            ResultSet result = createStatement().executeQuery("SELECT get_activity('" + activityid.toString() + "')");
            String[] resultArr = new String[6];
            System.out.println(result.getFetchSize());
            int index = 0;
            while (result.next()) {
                resultArr[index] = result.getString(index + 1);
                index++;
            }
            if (resultArr.length > 1) {
                return resultArr;
            }
        } catch (SQLException ex) {
            Logger.getLogger(IRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String[] getUserActivities(UUID userid) {
        try {
            ResultSet result = createStatement().executeQuery("SELECT get_activity_from_user('" + userid.toString() + "')");
            String[] resultArr = new String[result.getFetchSize()];
            System.out.println(resultArr.length);
            System.out.println(result.getFetchSize());
            int index = 0;
            while (result.next()) {
                resultArr[index] = result.getString(index + 1);
                index++;
            }
            if (resultArr.length > 1) {
                return resultArr;
            }
        } catch (SQLException ex) {
            Logger.getLogger(IRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public boolean addActivity(String name, String type, Date date, String starttime, String endtime, UUID activityid) {
        try {
            createStatement().executeQuery("SELECT add_activity('" + name + "', '" + type + "', '" + date.getTime() + "', " + starttime + ", '" + endtime + "', '" + activityid.toString() + "')");
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(IRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean addUserToActivity(UUID activityid, UUID userid) {
        try {
            createStatement().executeQuery("SELECT add_user_to_activity('" + activityid.toString() + "', '" + userid.toString() + "')");
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(IRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean deleteActivity(UUID activityid) {
        try {
            ResultSet result = createStatement().executeQuery("SELECT deleteActivity('" + activityid + "')");
            return result.wasNull();
        } catch (SQLException ex) {
            Logger.getLogger(IRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
