/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ered.superherosighting.dao;

import com.ered.superherosighting.entities.Power;
import com.ered.superherosighting.entities.Identity;
import java.util.List;

/**
 *
 * @author esred
 */
public interface PowerDao {
    Power addPower(Power power);
    
    Power updatePower(Power power);
    
    void deletePowerById(int id);
    
    Power getPowerById(int id);
    
    List<Power> getAllPowers();
    
    List<Power> getPowersForIdentity(int superId);
    
    Identity fillIdentityPowers(Identity s);
    
    List<Identity> fillIdentityPowers(List<Identity> supers);
    
}
