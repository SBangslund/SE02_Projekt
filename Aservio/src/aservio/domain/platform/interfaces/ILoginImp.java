/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aservio.domain.platform.interfaces;

import aservio.presentation.platform.interfaces.contracts.ILogin;

/**
 *
 * @author Sigur
 */
public class ILoginImp implements ILogin {

    //Limits for keyboardinputs in username and password.
    private char minCapLetter = 65; //A-Z
    private char maxCapLetter = 90;
    private char minLetter = 97; //a-z
    private char maxLetter = 122;
    private char charUnderscore = 95; //_
    private char minNumber = 48; //0-9
    private char maxNumber = 57;

    @Override
    public String checkForNoIllegalInput(String username, String password) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String validateLogin(String username, String password) {
        throw new UnsupportedOperationException("validate Login not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
