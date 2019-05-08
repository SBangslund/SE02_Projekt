package aservio.data;


import aservio.domain.platform.interfaces.contracts.IRepository;

import java.sql.*;
import java.util.UUID;

public class IRepositoryImp implements IRepository {

    private Connection connection;
    private boolean succesfullConnection = false;
    private UserRetriever userRetriever;

    public IRepositoryImp(){
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

    @Override
    public boolean addUser(String username, String password, UUID userid) {
        return userRetriever.addUser(username, password, userid);
    }

    @Override
    public boolean addUserInfo(String mail, String firstname, String lastname, int phone, String picture, UUID userid, int institutionid) {
        return userRetriever.addUserInfo(mail, firstname, lastname, phone, picture, userid, institutionid);
    }

    @Override
    public boolean addUserAddress(String roadname, String country, int postcode, String city, String housenumber, String level, UUID userid) {
        return userRetriever.addUserAddress(roadname, country, postcode, city, housenumber, level, userid);
    }

    public String[] getUserAddress(UUID userid){
        return userRetriever.getUserAdress(userid);
    }

    @Override
    public String[] getInstitution(int institutionid) {
        return new String[0];
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
