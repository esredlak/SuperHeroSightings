/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ered.superherosighting.entities;

import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 * @author Eric
 */
public class Location {
    int locId;
    @NotBlank(message = "Location Name cannot be blank")
    @Size(max = 50 , message = "Location Name must be fewer than 50 characters")
    String locName;
    @Size(max = 250 , message = "Location Descripton must be fewer than 250 characters")
    String locDescription;
    @Size(max = 30 , message = "Street must be fewer than 30 characters")
    String street;
    @Size(max = 30 , message = "Street Line 2 must be fewer than 30 characters")
    String street2;
    @Size(max = 30 , message = "City must be fewer than 30 characters")
    String city;
    @Size(min = 2 , max = 2 , message = "State must be a 2 character abbreviation (AL, AK, etc.)")
    String state;
    @Size(min = 5 , max = 5 , message = "Zip must be 5 characters")
    String zip;
    
    @Pattern(regexp="^(\\+|-)?(?:90(?:(?:\\.0{1,6})?)|(?:[0-9]|[1-8][0-9])(?:(?:\\.[0-9]{1,6})?))$" , message = "Please enter a valid latitude - a decimal number between -90 and 90 ")
    String latitude;
    @Pattern(regexp="^(\\+|-)?(?:180(?:(?:\\.0{1,6})?)|(?:[0-9]|[1-9][0-9]|1[0-7][0-9])(?:(?:\\.[0-9]{1,6})?))$" , message = "Please enter a valid longitude - a decimal number between -180 and 180")
    String longitude;

    public int getLocId() {
        return locId;
    }

    public void setLocId(int locId) {
        this.locId = locId;
    }

    public String getLocName() {
        return locName;
    }

    public void setLocName(String locName) {
        this.locName = locName;
    }

    public String getLocDescription() {
        return locDescription;
    }

    public void setLocDescription(String locDescription) {
        this.locDescription = locDescription;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreet2() {
        return street2;
    }

    public void setStreet2(String street2) {
        this.street2 = street2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }


    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + this.locId;
        hash = 67 * hash + Objects.hashCode(this.locName);
        hash = 67 * hash + Objects.hashCode(this.locDescription);
        hash = 67 * hash + Objects.hashCode(this.street);
        hash = 67 * hash + Objects.hashCode(this.street2);
        hash = 67 * hash + Objects.hashCode(this.city);
        hash = 67 * hash + Objects.hashCode(this.state);
        hash = 67 * hash + Objects.hashCode(this.zip);
        hash = 67 * hash + Objects.hashCode(this.latitude);
        hash = 67 * hash + Objects.hashCode(this.longitude);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Location other = (Location) obj;
        if (this.locId != other.locId) {
            return false;
        }
        if (!Objects.equals(this.locName, other.locName)) {
            return false;
        }
        if (!Objects.equals(this.locDescription, other.locDescription)) {
            return false;
        }
        if (!Objects.equals(this.street, other.street)) {
            return false;
        }
        if (!Objects.equals(this.street2, other.street2)) {
            return false;
        }
        if (!Objects.equals(this.city, other.city)) {
            return false;
        }
        if (!Objects.equals(this.state, other.state)) {
            return false;
        }
        if (!Objects.equals(this.zip, other.zip)) {
            return false;
        }
        if (!Objects.equals(this.latitude, other.latitude)) {
            return false;
        }
        if (!Objects.equals(this.longitude, other.longitude)) {
            return false;
        }
        return true;
    }
    
    
}
