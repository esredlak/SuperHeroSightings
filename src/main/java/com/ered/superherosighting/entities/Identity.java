/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ered.superherosighting.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 *
 * @author Eric
 */
public class Identity {
    int identityId;
    @NotBlank(message = "Super Name cannot be blank")
    @Size(max = 30 , message = "Super Name must be fewer than 30 characters")
    String identityName;
    @Size(max  = 50 , message = "Super Description must be fewer than 50 characters")
    String identityDescription;
    boolean hero;
    List<Integer> powerIds;
    List<Power> powers;
    String imageFileName;

    

    public Identity(){
        this.powers = new ArrayList<>();
    }
    
    public int getIdentityId() {
        return identityId;
    }

    public void setIdentityId(int identityId) {
        this.identityId = identityId;
    }

    public String getIdentityName() {
        return identityName;
    }

    public void setIdentityName(String identityName) {
        this.identityName = identityName;
    }

    public String getIdentityDescription() {
        return identityDescription;
    }

    public void setIdentityDescription(String identityDescription) {
        this.identityDescription = identityDescription;
    }

    public boolean isHero() {
        return hero;
    }

    public void setHero(boolean hero) {
        this.hero = hero;
    }

    public List<Integer> getPowerIds() {
        return powerIds;
    }

    public void setPowerIds(List<Integer> powerIds) {
        this.powerIds = powerIds;
    }

    public List<Power> getPowers() {
        return powers;
    }

    public void setPowers(List<Power> powers) {
        this.powers = powers;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + this.identityId;
        hash = 17 * hash + Objects.hashCode(this.identityName);
        hash = 17 * hash + Objects.hashCode(this.identityDescription);
        hash = 17 * hash + (this.hero ? 1 : 0);
        hash = 17 * hash + Objects.hashCode(this.powers);
        hash = 17 * hash + Objects.hashCode(this.imageFileName);
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
        final Identity other = (Identity) obj;
        if (this.identityId != other.identityId) {
            return false;
        }
        if (this.hero != other.hero) {
            return false;
        }
        if (!Objects.equals(this.identityName, other.identityName)) {
            return false;
        }
        if (!Objects.equals(this.identityDescription, other.identityDescription)) {
            return false;
        }
        if (!Objects.equals(this.imageFileName, other.imageFileName)) {
            return false;
        }
        if (!Objects.equals(this.powers, other.powers)) {
            return false;
        }
        return true;
    }
    
       
    
}
