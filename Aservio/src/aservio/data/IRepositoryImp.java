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
        return userRetriever.getUsersFromActivity(activityid);
    }

    @Override
    public String[] getUsersFromInstitution(int institution) {
        return userRetriever.getUsersFromInstitution(institution);
    }

    @Override
    public boolean deleteInstitution(int institutionid) {
        return userRetriever.deleteInstitution(institutionid);
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
    public boolean addUserInfo(String mail, String firstname, String lastname, int phone, String picture, UUID userid, int institutionid, String role) {
        return userRetriever.addUserInfo(mail, firstname, lastname, phone, picture, userid, institutionid, role);
    }

    @Override
    public boolean addUserAddress(String roadname, String country, int postcode, String city, String housenumber, String level, UUID userid) {
        return userRetriever.addUserAddress(roadname, country, postcode, city, housenumber, level, userid);
    }

    @Override
    public boolean addActivity(String name, String type, Date starttime, Date endtime, UUID activityid, String description) {
        return activityRetriever.addActivity(name, type, starttime, endtime, activityid, description);
    }

    @Override
    public boolean deleteActivity(UUID activityid) {
        return activityRetriever.deleteActivity(activityid);
    }

    @Override
    public boolean addInstitution(String institutionName, int institutionid ) {
        return userRetriever.addInstitution(institutionName, institutionid);
    }

    @Override
    public String getUserRole(String userid) {
        return userRetriever.getUserRole(userid);
    }

    @Override
    public String[] getCitizensFromCaretaker(String caretakerID) {
        return userRetriever.getCitizensFromCaretaker(caretakerID);
    }

    @Override
    public boolean deleteUser(UUID userID) {
        return userRetriever.deleteUser(userID);
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
        return userRetriever.getInstitution(institutionid);
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
    public String[] getNote(UUID noteid) {
        return documentRetriever.getNote(noteid);
    }

    @Override
    public boolean deleteNote(UUID noteID) {
        return false;
    }

    @Override
    public boolean addNoteToUser(UUID userid, UUID noteid) {
        return documentRetriever.addNoteToUser(userid, noteid);
    }

    @Override
    public boolean addUserNote(String noteInfo, Long noteDate, UUID noteid) {
        return documentRetriever.addNote(noteInfo, noteDate, noteid);
    }

}
