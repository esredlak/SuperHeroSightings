/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ered.superherosighting.service;

import com.ered.superherosighting.entities.Location;
import java.util.List;

/**
 *
 * @author Eric
 */
public interface LocationService {
    Location addLocation(Location location);
    
    Location updateLocation(Location location);
    
    void deleteLocationById(int id);
    
    Location getLocationById(int id);
    
    List<Location> getLocationsByIdentity(int superId);
    
    Location getLocationBySighting(int sightingId);
    
    List<Location> getAllLocations();
}
