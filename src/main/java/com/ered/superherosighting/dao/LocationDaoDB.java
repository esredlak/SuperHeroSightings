/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ered.superherosighting.dao;

import com.ered.superherosighting.entities.Location;
import com.ered.superherosighting.entities.Organization;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Eric
 */

@Repository
public class LocationDaoDB implements LocationDao{

    @Autowired
    JdbcTemplate jdbc;
    
    @Autowired
    LocationMapper locMapper;
    
    @Override
    @Transactional
    public Location addLocation(Location l) {
        final String INSERT_LOCATION = "INSERT INTO location"
                + "(locName, locDescription, street, street2, city, state, "
                + "zip, latitude , longitude) "
                + "VALUES(?,?,?,?,?,?,?,?,?)";
        jdbc.update(INSERT_LOCATION,
                l.getLocName(),
                l.getLocDescription(),
                l.getStreet(),
                l.getStreet2(),
                l.getCity(),
                l.getState(),
                l.getZip(),
                l.getLatitude(),
                l.getLongitude()
        );
        
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        l.setLocId(newId);
        return l;
    }

    @Override
    public Location updateLocation(Location location) {
        final String UPDATE_LOCATION = "UPDATE location SET locName = ? , "
                + "locDescription = ? , street = ? , street2 = ? , city = ? , "
                + "state = ? , zip = ? , latitude = ? , longitude = ? "
                + "WHERE locId = ?";
        jdbc.update(UPDATE_LOCATION,
                location.getLocName(),
                location.getLocDescription(),
                location.getStreet(),
                location.getStreet2(),
                location.getCity(),
                location.getState(),
                location.getZip(),
                location.getLatitude(),
                location.getLongitude(),
                location.getLocId()
        );
        return location;
    }

    @Override
    @Transactional
    public void deleteLocationById(int locId) {
        //locId is FK in organization. delete all orgs first
        final String DELETE_LOC_ORG = "DELETE FROM organization WHERE locId = ?";
        jdbc.update(DELETE_LOC_ORG , locId);
        //locId is a FK in sighting. Delete all sightings first.
        final String DELETE_LOC_SIGHT = "DELETE FROM sighting WHERE locId = ?";
        jdbc.update(DELETE_LOC_SIGHT, locId);
        //delete location
        final String DELETE_LOC = "DELETE FROM location WHERE locId = ?";
        jdbc.update(DELETE_LOC , locId);
    }

    @Override
    public Location getLocationById(int id) {
        try {
            final String GET_LOCATION = "SELECT * FROM location WHERE locId = ?";
            return jdbc.queryForObject(GET_LOCATION, locMapper, id);
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Location> getLocationsByIdentity(int superId) {
        final String GET_LOC_BY_SUPER = "SELECT * FROM location l "
                + "JOIN sighting s ON l.locId = s.locId "
                + "JOIN super_sighting ss ON s.sightingId = ss.sightingId "
                + "WHERE ss.superId = ?";
        List<Location> locations = jdbc.query(GET_LOC_BY_SUPER, locMapper, superId);
        return locations;
    }

    @Override
    public Location getLocationBySighting(int sightingId) {
        final String GET_LOC_BY_SIGHTING = "SELECT * FROM location l "
                + "JOIN sighting s ON l.locId = s.locId "
                + "WHERE s.sightingId = ?";
        Location location = jdbc.queryForObject(GET_LOC_BY_SIGHTING, locMapper, sightingId);
        return location;
    }

    @Override
    public List<Location> getAllLocations() {
        final String GET_ALL_LOC = "SELECT * FROM location";
        List<Location> locs;
        locs = jdbc.query(GET_ALL_LOC, locMapper);
        
        return locs;
    }

    @Override
    public Organization fillLocation(Organization o) {
        Location l = getLocationById(o.getLocId());
        o.setLoc(l);
        return o;
    }
    
}
