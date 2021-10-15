/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ered.superherosighting.service;

import com.ered.superherosighting.dao.LocationDaoDB;
import com.ered.superherosighting.dao.OrganizationDaoDB;
import com.ered.superherosighting.dao.PowerDaoDB;
import com.ered.superherosighting.dao.SightingDaoDB;
import com.ered.superherosighting.dao.IdentityDaoDB;
import com.ered.superherosighting.entities.Identity;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Eric
 */
@Service
public class IdentityServiceImpl implements IdentityService{
    @Autowired
    IdentityDaoDB superDao;
    
    @Autowired
    LocationDaoDB locDao;
    
    @Autowired
    OrganizationDaoDB orgDao;
    
    @Autowired
    SightingDaoDB sightDao;
    
    @Autowired
    PowerDaoDB powerDao;

    @Override
    public Identity addIdentity(Identity s) {
        s = superDao.addIdentity(s);
        s = powerDao.fillIdentityPowers(s);
        return s;
    }

    @Override
    public Identity updateIdentity(Identity s) {
        s = powerDao.fillIdentityPowers(s);
        s = superDao.updateIdentity(s);   
        return s;
    }

    @Override
    public void deleteIdentityById(int id) {
        superDao.deleteIdentityById(id);
    }

    @Override
    public Identity getIdentityById(int id) {
        Identity s = superDao.getIdentityById(id);
        s = powerDao.fillIdentityPowers(s);
        return s;
    }

    @Override
    public List<Identity> getAllIdentities() {
        List<Identity> supers = superDao.getAllIdentities();
        supers = powerDao.fillIdentityPowers(supers);
        return supers;
    }

    @Override
    public List<Identity> getIdentitiesForOrganization(int orgId) {
        List<Identity> supers = superDao.getIdentitiesForOrganization(orgId);
        supers = powerDao.fillIdentityPowers(supers);
        return supers;
    }

    @Override
    public List<Identity> getIdentitiesForLocation(int locId) {
        List<Identity> supers = superDao.getIdentitiesForLocation(locId);
        supers = powerDao.fillIdentityPowers(supers);
        return supers;
    }

    @Override
    public List<Identity> getIdentitiesByPower(int powerId) {
        List<Identity> supers = superDao.getIdentitiesByPower(powerId);
        supers = powerDao.fillIdentityPowers(supers);
        return supers;
    }

    @Override
    public List<Identity> getIdentitiesBySighting(int sightingId) {
        List<Identity> supers = superDao.getIdentitiesBySighting(sightingId);
        supers = powerDao.fillIdentityPowers(supers);
        return supers;
    }

    @Override
    public List<Identity> getIdentitiesByIds(List<Integer> superIds) {
        List<Identity> supers = new ArrayList<>();
        for(Integer i : superIds){
            Identity s = superDao.getIdentityById(i);
            supers.add(s);
        }
        return supers;
    }
 
}
