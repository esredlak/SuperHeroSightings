/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ered.superherosighting.dao;

import com.ered.superherosighting.entities.Location;
import com.ered.superherosighting.entities.Organization;
import java.util.List;

/**
 *
 * @author Eric
 */
public interface LocationDao {
    /* 
    CRUD
    */
    Location addLocation(Location location);
    
    Location updateLocation(Location location);
    
    void deleteLocationById(int id);
    
    Location getLocationById(int id);
    
    List<Location> getLocationsByIdentity(int identityId);
    
    Location getLocationBySighting(int sightingId);
    
    List<Location> getAllLocations();
    
    Organization fillLocation(Organization o);
}
