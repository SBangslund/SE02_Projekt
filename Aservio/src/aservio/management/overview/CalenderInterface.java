/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aservio.management.overview;

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
    String MONDAY_NAME = "monday";
    String TUESDAY_NAME = "tuesday";
    String WEDNESDAY_NAME = "wednesday";
    String THURSDAY_NAME = "thursday";
    String FRIDAY_NAME = "friday";
    String SATURDAY_NAME = "saturday";
    String SUNDAY_NAME = "sunday";
}
