/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ered.superherosighting.service;

import com.ered.superherosighting.dao.LocationDaoDB;
import com.ered.superherosighting.entities.Location;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Eric
 */
@Service
public class LocationServiceImpl implements LocationService{

    @Autowired
    LocationDaoDB locDao;
    
    @Override
    public Location addLocation(Location location) {
        location = locDao.addLocation(location);
        return location;
    }

    @Override
    public Location updateLocation(Location location) {
        location = locDao.updateLocation(location);
        return location;
    }

    @Override
    public void deleteLocationById(int id) {
        locDao.deleteLocationById(id);
    }

    @Override
    public Location getLocationById(int id) {
        return locDao.getLocationById(id);
    }

    @Override
    public List<Location> getLocationsByIdentity(int superId) {
        return locDao.getLocationsByIdentity(superId);
    }

    @Override
    public Location getLocationBySighting(int sightingId) {
        return locDao.getLocationBySighting(sightingId);
    }

    @Override
    public List<Location> getAllLocations() {
        return locDao.getAllLocations();
    }
    
}
