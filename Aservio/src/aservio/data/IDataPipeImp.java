package aservio.data;

import aservio.domain.management.activities.Activity;
import aservio.domain.platform.interfaces.contracts.IDataPipe;

import java.sql.*;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IDataPipeImp implements IDataPipe {

    private Connection connection;
    private boolean succesfullConnection = false;

    public IDataPipeImp(){
        this.setupConnection();
        System.out.println(this.verifyUser("test", "me"));
        System.out.println(this.verifyUser("test", "test"));
    }

    @Override
    public String getUser(String username, String password) {
        return null;
    }

    @Override
    public String[] getUsers(UUID activityid) {
        return new String[0];
    }

    @Override
    public String[] getActivity(UUID activityid) {
        return new String[0];
    }

    @Override
    public String[] getUserAddress(UUID userid) {
        return new String[0];
    }

    @Override
    public String[] getInstitution(UUID institutionid) {
        return new String[0];
    }

    @Override
    public String[] getUserActivities(UUID userid) {
        return new String[0];
    }

    @Override
    public String[] getUserInfo(UUID userid) {
        return new String[0];
    }

    @Override
    public String verifyUser(String username, String password) {
        Statement execStat = null;
        try {
            execStat = connection.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(IDataPipeImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            ResultSet worths = execStat.executeQuery("SELECT getuserid('" + username + "', '" + password + "')");
            while(worths.next()){
                return worths.getString(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(IDataPipeImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "method not working";
    }

    public void setupConnection(){
        System.out.println("-------- PostgreSQL "
                + "JDBC Connection Testing ------------");
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your PostgreSQL JDBC Driver? "
                    + "Include in your library path!");
            e.printStackTrace();
            return;
        }
        System.out.println("PostgreSQL JDBC Driver Registered!");
        connection = null;
        try {
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://balarama.db.elephantsql.com:5432/kzpurfgw", "kzpurfgw",
                    "ZyHDoKdmCOf-89xy6pPGSry97kpVWb1l");
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return;
        }
        if (connection != null) {
            System.out.println("You made it, take control your database now!");
        } else {
            System.out.println("Failed to make connection!");
        }

    }
}
