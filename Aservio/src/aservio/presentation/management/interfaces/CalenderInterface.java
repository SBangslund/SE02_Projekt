/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aservio.presentation.management.interfaces;

/**
 *
 * @author Rene_
 */
public interface CalenderInterface {
    int numberOfDays = 7;
    
    int BLANK = 1;
    // Constants values for days
    int MONDAY = 2;
    int TUESDAY = 3;
    int WEDNESDAY = 4;
    int THURSDAY = 5;
    int FRIDAY = 6;
    int SATURDAY = 7;
    int SUNDAY = 8;
    
    // Constatns names for days
    String MONDAY_NAME = "Mandag";
    String TUESDAY_NAME = "Tirsdag";
    String WEDNESDAY_NAME = "Onsdag";
    String THURSDAY_NAME = "Torsdag";
    String FRIDAY_NAME = "Fredag";
    String SATURDAY_NAME = "Lørdag";
    String SUNDAY_NAME = "Søndag";
}
