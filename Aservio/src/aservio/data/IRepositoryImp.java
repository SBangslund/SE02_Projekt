package aservio.data;

import aservio.domain.platform.interfaces.contracts.IRepository;

import java.sql.*;
import java.util.Date;
import java.util.UUID;

public class IRepositoryImp implements IRepository {

    private Connection connection;
    private boolean succesfulConnection = false;
    private UserRetriever userRetriever;
    private ActivityRetriever activityRetriever;
    private DocumentRetriever documentRetriever;

    public IRepositoryImp() {
        this.setupConnection();
        userRetriever = new UserRetriever(connection);
        activityRetriever = new ActivityRetriever(connection);
        documentRetriever = new DocumentRetriever(connection);
    }

    public void setupConnection() {
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
            succesfulConnection = true;
        } catch (SQLException e) {
            succesfulConnection = false;
            e.printStackTrace();
            return;
        }
        if (connection != null) {
            System.out.println("Successfully connected to Database");
        } else {
            System.err.println("Database connection failed");
        }
    }

    @Override
    public String getUser(String username, String password) {
        return userRetriever.getUser(username, password);
    }

    @Override
    public String[] getUsersFromActivity(UUID activityid) {
        return userRetriever.getUsers(activityid);
    }

    @Override
    public String[] getUsersFromInsitution(int institution) {
        return userRetriever.getUsers(institution);
    }

    @Override
    public String[] getActivity(UUID activityid) {
        return activityRetriever.getActivity(activityid);
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

    @Override
    public boolean addActivity(String name, String type, Date date, String starttime, String endtime, UUID activityid) {
        return activityRetriever.addActivity(name, type, date, starttime, endtime, activityid);
    }

    @Override
    public boolean addUserToActivity(UUID activityid, UUID userid) {
        return activityRetriever.addUserToActivity(activityid, userid);
    }

    public String[] getUserAddress(UUID userid) {
        return userRetriever.getUserAddress(userid);
    }

    @Override
    public String[] getInstitution(int institutionid) {
        return new String[0];
    }

    @Override
    public String[] getUserActivities(UUID userid) {
        return activityRetriever.getUserActivities(userid);
    }

    @Override
    public String[] getUserInfo(UUID userid) {
        return userRetriever.getUserInfo(userid);
    }

    @Override
    public String verifyUser(String username, String password) {
        return userRetriever.verifyUser(username, password);
    }

    @Override
    public String[] getNotesFromUser(UUID userid) {
        return documentRetriever.getNotesFromUser(userid);
    }

    @Override
    public boolean addUserNote(UUID noteid, Long noteDate, String startTime, String endTime, String noteText) {
        return documentRetriever.addNote(noteid, noteDate, startTime, endTime, noteText);
    }

    @Override
    public String[] getNote(UUID noteid) {
        return documentRetriever.getNote(noteid);
    }

    @Override
    public boolean addNoteToUser(UUID userid, UUID noteid) {
        return documentRetriever.addNoteToUser(userid, noteid);
    }

}
