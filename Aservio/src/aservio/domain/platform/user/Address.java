/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aservio.domain.platform.user;

/**
 *
 * @author victo
 */
public class Address {
    private String road;
    private String country;
    private int postcode;
    private String city;
    private String houseNumber;
    private String level;

    public Address(String road, String country, int postcode, String city, String houseNumber, String level) {
        this.road = road;
        this.country = country;
        this.postcode = postcode;
        this.city = city;
        this.houseNumber = houseNumber;
        this.level = level;
    }

    public String getRoad() {
        return road;
    }

    public String getCountry() {
        return country;
    }

    public int getPostcode() {
        return postcode;
    }

    public String getCity() {
        return city;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public String getLevel() {
        return level;
    }

    
    

}
