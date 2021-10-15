/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ered.superherosighting.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author Eric
 */
public class Sighting {
    int sightingId;
    @NotNull(message = "A sighting date must be selected")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date sightingDate;
    @Size(max = 250 , message = "Description must be 250 characters or fewer")
    String sightingDescription;
    List<Integer> identityIds;
    List<Identity> identities;
    int locId;
    Location loc;

    
    public Sighting(){
        this.identities = new ArrayList<>();
    }

    public int getSightingId() {
        return sightingId;
    }

    public void setSightingId(int sightingId) {
        this.sightingId = sightingId;
    }

    public Date getSightingDate() {
        return sightingDate;
    }

    public void setSightingDate(Date sightingDate) {
        this.sightingDate = sightingDate;
    }

    public String getSightingDescription() {
        return sightingDescription;
    }

    public void setSightingDescription(String sightingDescription) {
        this.sightingDescription = sightingDescription;
    }

    public List<Integer> getIdentityIds() {
        return identityIds;
    }

    public void setIdentityIds(List<Integer> identityIds) {
        this.identityIds = identityIds;
    }

    public List<Identity> getIdentities() {
        return identities;
    }

    public void setIdentities(List<Identity> identities) {
        this.identities = identities;
    }

    public int getLocId() {
        return locId;
    }

    public void setLocId(int locId) {
        this.locId = locId;
    }

    public Location getLoc() {
        return loc;
    }

    public void setLoc(Location loc) {
        this.loc = loc;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + this.sightingId;
        hash = 19 * hash + Objects.hashCode(this.sightingDate);
        hash = 19 * hash + Objects.hashCode(this.sightingDescription);
        hash = 19 * hash + Objects.hashCode(this.identityIds);
        hash = 19 * hash + Objects.hashCode(this.identities);
        hash = 19 * hash + this.locId;
        hash = 19 * hash + Objects.hashCode(this.loc);
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
        final Sighting other = (Sighting) obj;
        if (this.sightingId != other.sightingId) {
            return false;
        }
        if (this.locId != other.locId) {
            return false;
        }
        if (!Objects.equals(this.sightingDescription, other.sightingDescription)) {
            return false;
        }
        if (!Objects.equals(this.sightingDate, other.sightingDate)) {
            return false;
        }
        if (!Objects.equals(this.identityIds, other.identityIds)) {
            return false;
        }
        if (!Objects.equals(this.identities, other.identities)) {
            return false;
        }
        if (!Objects.equals(this.loc, other.loc)) {
            return false;
        }
        return true;
    }
    
    
}
