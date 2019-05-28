package aservio.domain.platform.interfaces.implementors;

import aservio.domain.platform.Aservio;
import aservio.domain.platform.user.User;
import aservio.presentation.platform.controllers.Login;
import aservio.presentation.platform.interfaces.contracts.ILogin;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class ILoginWithFileImp implements ILogin {

    //Limits for keyboardinputs in username and password.
    private final char minCapLetter = 'A'; //65; //A-Z
    private final char maxCapLetter = 'Z'; //90;
    private final char minLetter = 'a'; //97; //a-z
    private final char maxLetter = 'z'; //122;
    private final char charUnderscore = '_'; //95; //_
    private final char minNumber = '0'; //48; //0-9
    private final char maxNumber = '9'; //57;

    File file;
    
    //IMPORTANT. tempUserSetup must be run before accessing users. (previously located in Login)
    /**
     * Tests login input for errors like illegal characters, spaces, and correct
     * account identification. WIP
     *
     * @param username String from the textfield usernameField.
     * @param password String from the textfield passwordField.
     * @return String containing an error message or a String confirming access.
     */
    @Override
    public String checkForNoIllegalInput(String username, String password) {
        //Is Input empty?
        if (username.isEmpty() || password.isEmpty()) {
            return "Write your username and password to log in.";
        }
        //Is Input in username ILLEGALCHARACTER?
        for (char j : username.toCharArray()) {
            boolean capitalLetterRange = j >= minCapLetter && j <= maxCapLetter;
            boolean smallLetterRange = j >= minLetter && j <= maxLetter;
            boolean numberRange = j >= minNumber && j <= maxNumber;
            if (!(capitalLetterRange || smallLetterRange || numberRange || j == charUnderscore)) {
                return "USERNAME contains special characters. Only 0-9, a-z and _ is permitted";
            }
        }
        //Is Input in username ILLEGALCHARACTER?
        for (char j : password.toCharArray()) {
            boolean capitalLetterRange = j >= minCapLetter && j <= maxCapLetter;
            boolean smallLetterRange = j >= minLetter && j <= maxLetter;
            boolean numberRange = j >= minNumber && j <= maxNumber;
            if (!(capitalLetterRange || smallLetterRange || numberRange || j == charUnderscore)) {
                return "PASSWORD contains special characters. Only 0-9, a-z and _ is permitted";
            }
        }
        //Is Input a user?
        if (!findUserInFile(username, password)) {
            return "Wrong username or password";
        }
        //All constraints are followed.
        return "Access";
    }

    /**
     * findUserInFile utilizes fileobjectstreams to search through a file, 
     * looking for a specific object resembling the user with the given username and password.
     * Returns true if user is found.
     * @param username String from the textfield usernameField.
     * @param password String from the textfield passwordField.
     * @return boolean if the user was found.
     */
    private boolean findUserInFile(String username, String password) {
        boolean cont = true;
        int count = 0;
        List<User> users = new ArrayList();
        try {
            FileInputStream fileStream = new FileInputStream(file);
            ObjectInputStream objectIn = new ObjectInputStream(fileStream);

            while (cont) {
                Object o = objectIn.readObject();
                if (o != null) {
                    if (o instanceof User) {
                        users.add((User) o);
                        System.out.println(o);
                    }
                }
                count++;
            }
        } catch (EOFException ex) {
            System.out.println("reached end of file");
            System.out.println("count: " + count);
            System.out.println("--------");
            for (User u : users) {
                if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                    System.out.println("User with correct username and password found");
                    User.setCurrentUser(u);
                    System.out.println("The current users username is: " + User.getCurrentUser().getUsername());
                    return true;
                }
            }
        } catch (IOException ex) {
            System.err.println("----------");
            System.err.println("Could not find file or read from file. " + file.getAbsolutePath());
            System.err.println("Error usually caused by corrupted or modified file or path. Try deleting the file (see path above).");
            System.err.println("Can also be caused by changes in Aservio which requires a rebuild of the project. ");
            System.err.println("Especially likely if you just downloaded a new version or merged.");
        } catch (ClassNotFoundException ex) {
            System.err.println("Could not find the class User");
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     * Database method, not used in this class.
     * @param username String from the textfield usernameField.
     * @param password String from the textfield passwordField.
     * @return Nothing.
     */
    @Override
    public String verifyUser(String username, String password) {
        throw new UnsupportedOperationException("validate Login not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Sets a reference for the file containing users. If none exists, a new is created.
     *
     * @param fileName.
     */

    public void setFile(String fileName) {
        file = new File(fileName);
    }

    /**
     * If the file containing users has been deleted, moved or is empty. 
     * A new file is created and filled with fake users for testing purposes.
     */
    @Override
    public void tempUserSetupByFile() {
        file = new File("users.txt");
        try {
            //TEMPORARY
            if (!file.exists()) {//Use once pr new file.
                file.createNewFile();
                UUID user1ID = UUID.randomUUID(),
                        user2ID = UUID.randomUUID(),
                        user3ID = UUID.randomUUID();
                //fake users created to test read, as a temporary solution.
                //User user1 = new User("q", "q", user1ID,new Caretaker(), new UserInfo(new Address("Solsikkemarken", "Danmark", 5260, "Odense M", "18", "1", user1ID),
                //        null, 21212121, "Samuel", "Bangslund", "samuelbanglund@gmail.com", 1, user1ID));
                //User user2 = new User("tove_1234", "rambo", user2ID, new Caretaker(), new UserInfo(new Address("Solsikkemarken", "Danmark", 5260, "Odense M", "18", "1", user2ID),
                //        null, 21212121, "Victor", "Clemmensen", "samuelbanglund@gmail.com", 1, user2ID));
                //User user3 = new User("Slagteren", "affaldssortering", user3ID, new Caretaker(), new UserInfo(new Address("Solsikkemarken", "Danmark", 5260, "Odense M", "18", "1", user3ID),
                //        null, 21212121, "Rene", "Bangslund", "samuelbanglund@gmail.com", 1, user3ID));
                //writeToFile(user1);
                //appendWriteTOFile(user2);
                //appendWriteTOFile(user3);
            }
        } catch (IOException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        setFile("users.txt");
        System.out.println("File set: " + file);
    }

    /**
     * Objectoutputstream that is used to write to the file containing users. Is run if the file is empty on program start.
     * This method is only used the first time since it adds a header to the file.
     * @param u User reference that will be written to the file.
     */
    private void writeToFile(User u) {
        try (
                FileOutputStream fos = new FileOutputStream(file, true);
                ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            oos.writeObject(u);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            System.err.println("Could not find file or read from file " + file.getAbsolutePath());
        }
    }

    /**
     * This method adds additional users to the file if it already has been written to before.
     * @param s 
     */
    private void appendWriteTOFile(User s) {
        try (
                FileOutputStream fos = new FileOutputStream(file, true);
                AppendingObjectOutputStream oos = new AppendingObjectOutputStream(fos)) {

            oos.writeObject(s);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            System.err.println("Could not find file or read from file " + file.getAbsolutePath());
        }
    }

    /**
     * This method loads the next scene if the userinput is correct.
     */
    @Override
    public void loadScene() {
        try {
            Parent p = FXMLLoader.load(getClass().getResource("/aservio/presentation/platform/views/Platform.fxml"));
            Aservio.getInstance().getPrimaryStage().getScene().setRoot(p);
            Aservio.getInstance().getPrimaryStage().setMaximized(true);
        } catch (IOException ex) {
            System.out.println("Couldn't load Platform.fxml file.");
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Database method, unsupported in this class.
     * @param username
     * @param password
     * @return Nothing.
     */
    @Override
    public User getUser(String username, String password) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Unsupported. 
     * @param username
     * @param password 
     */
    @Override
    public void setUser(String username, String password) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Anonymous class, removes the automated header creation for filestreams when writing to documents.
     */
    private class AppendingObjectOutputStream extends ObjectOutputStream {

        public AppendingObjectOutputStream(OutputStream out) throws IOException {
            super(out);
        }

        @Override
        protected void writeStreamHeader() throws IOException {
            reset();
        }
    }

}
