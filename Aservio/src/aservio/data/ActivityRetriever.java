package aservio.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ActivityRetriever {

    private final Connection connection;
    public ActivityRetriever(Connection connection){
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
            ResultSet result = createStatement().executeQuery("SELECT * from  get_activity('" + activityid+ "')");
            String[] resultArr = new String[6];
            int index = 0;
            while (result.next()) {
                for (int i = 0; i < resultArr.length -1; i++) {
                    resultArr[i] = result.getString(i+1);
                }
            }
            if (resultArr.length >= 1) {
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
            int in = 0;
            while(result.next()){
                in++;
            }
            //System.out.println("in:  " + in);
            ResultSet resultLate = createStatement().executeQuery("SELECT  get_activity_from_user('" + userid.toString() + "')");
            String[] resultArr = new String[in];

            int index = 0;
            while (resultLate.next()) {
                resultArr[index] = resultLate.getString(1);
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

    public boolean addUserToActivity(UUID activityid, UUID userid) {
        try {
            createStatement().executeQuery("SELECT add_user_to_activity('" + activityid.toString() + "', '" + userid.toString() + "')");
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(IRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean addActivity(String name, String type, Date starttime, Date endtime, UUID activityid, String description) {
        try {
            createStatement().executeQuery("SELECT add_activity('" + name + "', '" + type + "', " + starttime.getTime() + ", " + endtime.getTime() + ", '" + activityid.toString() + ", '" + description + "')");
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
