/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ered.superherosighting.service;

import com.ered.superherosighting.entities.Power;
import java.util.List;

/**
 *
 * @author Eric
 */
public interface PowerService {
    Power addPower(Power power);
    
    Power updatePower(Power power);
    
    void deletePowerById(int id);
    
    Power getPowerById(int id);
    
    List<Power> getAllPowers();
    
    List<Power> getPowersForIdentity(int superId);
}
