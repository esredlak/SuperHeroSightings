/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ered.superherosighting.dao;

import com.ered.superherosighting.entities.Sighting;
import com.ered.superherosighting.entities.Identity;
import java.util.ArrayList;
import java.util.Date;
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
public class SightingDaoDB implements SightingDao{

    @Autowired
    JdbcTemplate jdbc;
    
    @Autowired
    SightingMapper sightingMapper;
    
    @Autowired
    IdentityMapper superPersonMapper;
    
    @Override
    @Transactional
    public Sighting addSighting(Sighting sight) {
        //create entry in sighting table
        final String INSERT_SIGHTING = "INSERT INTO sighting (locId, sightingDate, sightingDescription) "
                + "VALUES(?,?,?)";
        jdbc.update(INSERT_SIGHTING,
                sight.getLoc().getLocId(),
                sight.getSightingDate(),
                sight.getSightingDescription()
        );
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        sight.setSightingId(newId);
        //create entries in super_sighting for each super in List
        addEntriesToSuperSighting(sight);
        
        return sight;
    }

    @Override
    @Transactional
    public Sighting updateSighting(Sighting sight) {
        //update sighting entry
        final String UPDATE_SIGHTING = "UPDATE sighting SET locId = ? , "
                + "sightingDate = ? , sightingDescription = ? WHERE sightingId = ?";
        jdbc.update(UPDATE_SIGHTING ,
                sight.getLoc().getLocId(),
                sight.getSightingDate(),
                sight.getSightingDescription(),
                sight.getSightingId()
        );
        //delete all entries in super_sighting with sightingId
        deleteSightingById(sight.getLoc().getLocId());
        //create entries in super_sighting for each super in List
        updateEntriesToSuperSighting(sight);
        
        return sight;
    }

    @Override
    @Transactional
    public void deleteSightingById(int id) {
        //sightingId is FK in super_sighting so delete from s_s first
        final String DELETE_S_S = "DELETE FROM super_sighting WHERE sightingId = ?";
        jdbc.update(DELETE_S_S , id);
        //then delete from sighting
        final String DELETE_SIGHTING = "DELETE FROM sighting WHERE sightingId = ? ";
        jdbc.update(DELETE_SIGHTING , id);
    }

    @Override
    @Transactional
    public Sighting getSightingById(int id) {
        try {
            //build sighting object from sighting table
            final String GET_SIGHTING = "SELECT s.*, l.* FROM sighting s "
                    + "JOIN location l ON l.locId = s.locId "
                    + "WHERE s.sightingId = ? ";
            Sighting sight =  jdbc.queryForObject(GET_SIGHTING, new SightingMapper(), id);
            //build list of heroes for given sighting  
            List<Identity> identities = getSupersForSighting(id);
            List<Integer> identityIds = new ArrayList<>();
            for(Identity i : identities){
                identityIds.add(i.getIdentityId());
            }
            //add those supers to the sighting and return
            sight.setIdentities(identities);
            sight.setIdentityIds(identityIds);
            return sight;
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Sighting> getSightingsByDate(Date date) {
        final String GET_SIGHTING_BY_DATE = "SELECT s.*, l.* FROM sighting s "
                + "JOIN location l ON s.locId = l.locId "
                + "WHERE s.sightingDate = ?";
        List<Sighting> sightings = jdbc.query(GET_SIGHTING_BY_DATE, new SightingMapper(), date);
        
        for(Sighting s : sightings){
            s.setIdentities( getSupersForSighting(s.getSightingId()) );
        }
        
        return sightings;
    }

    @Override
    public List<Sighting> getAllSightings() {
        final String GET_ALL_SIGHTS = "SELECT s.*, l.* FROM sighting s "
                + "JOIN location l ON s.locId = l.locId "
                + "ORDER BY s.sightingDate DESC ";
        List<Sighting> sights;
        sights = jdbc.query(GET_ALL_SIGHTS, new SightingMapper());
        
        for(Sighting s : sights){
            s.setIdentities( getSupersForSighting(s.getSightingId()) );
        }
        
        return sights;
    }

    private void addEntriesToSuperSighting(Sighting sight) {
        final String INSERT_SUPER_SIGHTING = "INSERT INTO super_sighting "
                + "(superId, sightingId) VALUES (?,?)";
        
        List<Identity> supers = sight.getIdentities();
        
        if (!(supers.isEmpty()) ){
            for(Identity s : supers){
                jdbc.update(INSERT_SUPER_SIGHTING, 
                        s.getIdentityId(),
                        sight.getSightingId()
                );
            }
        }   
        
    }
    
    @Transactional
    private void updateEntriesToSuperSighting(Sighting sight) {
        final String DELETE_SUPER_SIGHTING = "DELETE FROM super_sighting "
                + "WHERE sightingId = ?";
        jdbc.update(DELETE_SUPER_SIGHTING , sight.getSightingId() );
        addEntriesToSuperSighting(sight);
    }

    @Override
    public List<Sighting> getSightingsForLocation(int locId){
        final String GET_SIGHTS_FOR_LOC = "SELECT s.*, l.* "
                + "FROM sighting s "
                + "JOIN location l ON s.locId = l.locId "
                + "WHERE l.locId = ? "
                + "ORDER BY s.sightingDate DESC ";
        List<Sighting> sights = jdbc.query(GET_SIGHTS_FOR_LOC , new SightingMapper(), locId);
        
        return sights;
    }
    
    @Override
    public List<Sighting> getLastTenSightings() {
        final String GET_LAST_10 = "SELECT s.* , l.* "
                + "FROM sighting s "
                + "JOIN location l ON s.locId = l.locId "
                + "ORDER BY s.sightingDate DESC "
                + "LIMIT 10 ";
        
        List<Sighting> last10 = jdbc.query(GET_LAST_10, new SightingMapper() );
        
        return last10;
    }
    
    private List<Identity> getSupersForSighting(int id) {
        final String GET_SUPERS_FOR_SIGHTING = "SELECT s.* FROM super s "
                    + "JOIN super_sighting ss ON s.superId = ss.superId "
                    + "WHERE ss.sightingId = ?";
            List<Identity> supers;
            supers = jdbc.query(GET_SUPERS_FOR_SIGHTING, new IdentityMapper(), id);
            
            return supers;
    }
    
}

