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
    private UserRetriever userRetriever;

    public IDataPipeImp(){
        this.setupConnection();
        userRetriever = new UserRetriever(connection);
        System.out.println(this.verifyUser("test", "me"));
        System.out.println(this.verifyUser("test", "test"));
    }

    @Override
    public String getUser(String username, String password) {
        return userRetriever.getUser(username, password);
    }

    @Override
    public String[] getUsers(UUID activityid) {
        return new String[0];
    }

    @Override
    public String[] getActivity(UUID activityid) {
        return new String[0];
    }

    public String[] getUserAddress(UUID userid){
        return userRetriever.getUserAdress(userid);
    }

    @Override
    public String[] getInstitution(UUID institutionid) {
        return new String[0];
    }

    @Override
    public boolean addUser(String username, String password, String userid, String mail, String firstname, String lastname, int phone, String picture, String instituionname) {
        return false;
    }

    @Override
    public String[] getUserActivities(UUID userid) {
        return new String[0];
    }

    @Override
    public String[] getUserInfo(UUID userid) {
        return userRetriever.getUserInfo(userid);
    }

    @Override
    public String verifyUser(String username, String password) {
        return userRetriever.verifyUser(username, password);
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
