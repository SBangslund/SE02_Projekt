/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aservio.domain.platform.interfaces;

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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ILoginImp implements ILogin {

    //Limits for keyboardinputs in username and password.
    private char minCapLetter = 'A'; //65; //A-Z
    private char maxCapLetter = 'Z'; //90;
    private char minLetter = 'a'; //97; //a-z
    private char maxLetter = 'z'; //122;
    private char charUnderscore = '_'; //95; //_
    private char minNumber = '0'; //48; //0-9
    private char maxNumber = '9'; //57;

    File file;

    /**
     * Tests login input for errors like illegal characters, spaces, and correct
     * account identification.
     *
     * @param username
     * @param password
     * @return
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

        } catch (ClassNotFoundException ex) {
            System.err.println("Could not find the class User");
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public String validateLogin(String username, String password) {
        throw new UnsupportedOperationException("validate Login not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setFile(String fileName) {
        file = new File(fileName);
    }

}
