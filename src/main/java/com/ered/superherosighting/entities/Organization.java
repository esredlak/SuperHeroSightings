/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ered.superherosighting.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 *
 * @author Eric
 */
public class Organization {
    int orgId;
    @NotBlank(message = "Org name cannot be blank")
    @Size(max = 50 , message = "Org name must be fewer than 50 characters")
    String orgName;
    @Size(max = 250 , message = "Org description must be fewer than 250 characters")
    String orgDescription;
    List<Integer> identityIds;
    List<Identity> identities;
    Integer locId;
    Location loc;

    public Organization(){
        this.identities = new ArrayList<>();
        this.identityIds = new ArrayList<>();
    }

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgDescription() {
        return orgDescription;
    }

    public void setOrgDescription(String orgDescription) {
        this.orgDescription = orgDescription;
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

    public Integer getLocId() {
        return locId;
    }

    public void setLocId(Integer locId) {
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
        int hash = 5;
        hash = 61 * hash + this.orgId;
        hash = 61 * hash + Objects.hashCode(this.orgName);
        hash = 61 * hash + Objects.hashCode(this.orgDescription);
        hash = 61 * hash + Objects.hashCode(this.identities);
        hash = 61 * hash + Objects.hashCode(this.loc);
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
        final Organization other = (Organization) obj;
        if (this.orgId != other.orgId) {
            return false;
        }
        if (!Objects.equals(this.orgName, other.orgName)) {
            return false;
        }
        if (!Objects.equals(this.orgDescription, other.orgDescription)) {
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
