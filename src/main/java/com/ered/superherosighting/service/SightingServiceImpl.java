/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ered.superherosighting.service;

import com.ered.superherosighting.dao.LocationDaoDB;
import com.ered.superherosighting.dao.SightingDaoDB;
import com.ered.superherosighting.dao.IdentityDaoDB;
import com.ered.superherosighting.dao.PowerDaoDB;
import com.ered.superherosighting.entities.Location;
import com.ered.superherosighting.entities.Sighting;
import com.ered.superherosighting.entities.Identity;
import com.ered.superherosighting.entities.Power;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Eric
 */
@Service
public class SightingServiceImpl implements SightingService{
    @Autowired
    IdentityDaoDB superDao;
    
    @Autowired
    SightingDaoDB sightDao;
    
    @Autowired
    LocationDaoDB locDao;
    
    @Autowired
    PowerDaoDB powerDao;

    @Override
    public Sighting addSighting(Sighting toAdd) {     
        Location l = locDao.getLocationById( toAdd.getLocId() );
        toAdd.setLoc(l);
        toAdd = sightDao.addSighting(toAdd);
        return toAdd;
    }

    @Override
    public Sighting updateSighting(Sighting toUpdate) {
        Location l = locDao.getLocationById( toUpdate.getLocId());
        toUpdate.setLoc(l);
        List<Identity> supers = new ArrayList<>();
        List<Integer> superInts = toUpdate.getIdentityIds();
        for(Integer i : superInts){
            Identity s = superDao.getIdentityById(i);
            supers.add(s);
        }
        toUpdate.setIdentities(supers);       
        toUpdate = sightDao.updateSighting(toUpdate);
        return toUpdate;
    }

    @Override
    public void deleteSightingById(int id) {
        sightDao.deleteSightingById(id);
    }

    @Override
    public Sighting getSightingById(int id) {
        Sighting s = sightDao.getSightingById(id);
        List<Integer> powerIdsForIdentity;
        
        for(Identity i : s.getIdentities()){
            List<Power> powersForIdentity = powerDao.getPowersForIdentity( i.getIdentityId() );
            
            i.setPowers( powersForIdentity );
            powerIdsForIdentity = new ArrayList<>();
            
            for(Power p : powersForIdentity){
                powerIdsForIdentity.add( p.getPowerId() );
            }
            
            i.setPowerIds(powerIdsForIdentity);
        }
        
        return s;
    }

    @Override
    public List<Sighting> getSightingsByDate(Date date) {
        List<Sighting> sightsOnDate = sightDao.getSightingsByDate(date);
        sightsOnDate = fillSightingsWithSupers(sightsOnDate);
        return sightsOnDate;
    }

    @Override
    public List<Sighting> getAllSightings() {
        List<Sighting> allSights = sightDao.getAllSightings();
        allSights = fillSightingsWithSupers(allSights);
        return allSights;
    }

    @Override
    public List<Sighting> getSightingsForLocation(int locId) {
        List<Sighting> sightsForLoc = sightDao.getSightingsForLocation(locId);
        sightsForLoc = fillSightingsWithSupers(sightsForLoc);
        return sightsForLoc;
    }

    @Override
    public List<Sighting> getLastTenSightings() {
        List<Sighting> lastTen = sightDao.getLastTenSightings();
        lastTen = fillSightingsWithSupers(lastTen);
        return lastTen;
    }

    private Sighting fillSightingWithSupers(Sighting s) {
        List<Identity> supers = superDao.getIdentitiesBySighting( s.getSightingId() );
        
        s.setIdentities(supers);
        
        return s;
    }
    
    private List<Sighting> fillSightingsWithSupers(List<Sighting> sights) {
        sights.forEach(s -> {
            s = fillSightingWithSupers(s);
        });
        return sights;
    }
}
