/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ered.superherosighting.dao;

import com.ered.superherosighting.entities.Sighting;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Eric
 */
public interface SightingDao {
    //crud
    Sighting addSighting(Sighting toAdd);
    
    Sighting updateSighting(Sighting toUpdate);
    
    void deleteSightingById(int id);
    
    Sighting getSightingById(int id); 
    
    List<Sighting> getSightingsByDate(Date date);
    
    List<Sighting> getAllSightings();
    
    List<Sighting> getSightingsForLocation(int locId);
    /**
     * Returns the last 10 sightings based on sightingDate, not sightId.
     * @return List<Sighting>
     */
    List<Sighting> getLastTenSightings();
}
