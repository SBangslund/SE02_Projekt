package aservio.data;

import aservio.domain.platform.interfaces.contracts.IDatabase;
import aservio.domain.platform.user.User;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IDatabaseImp implements IDatabase {

    private Connection connection;
    private boolean succesfullConnection = false;

    public IDatabaseImp(){
        this.setupConnection();
        System.out.println(this.userVerification("test", "me"));
        System.out.println(this.userVerification("test", "test"));
    }

    private void testConnection(){
        Statement execStat = null;
        try {
            execStat = connection.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(IDatabaseImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            ResultSet worths = execStat.executeQuery("SELECT * FROM userinfo");
            while(worths.next()){
                System.out.println("result: " + worths.getString("firstname"));
                System.out.println("result: " + worths.getString("userid"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(IDatabaseImp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String userVerification(String username, String password) {
        Statement execStat = null;
        try {
            execStat = connection.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(IDatabaseImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            ResultSet worths = execStat.executeQuery("SELECT getuserid('" + username + "', '" + password + "')");
            while(worths.next()){
                return worths.getString(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(IDatabaseImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "method not working";
    }

    @Override
    public String getUserActivities(User user) {
        return null;
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
