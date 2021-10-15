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
import com.ered.superherosighting.entities.Organization;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Eric
 */
@Service
public class OrganizationServiceImpl implements OrganizationService{
    @Autowired
    IdentityDaoDB idDao;
    
    @Autowired
    LocationDaoDB locDao;
    
    @Autowired
    OrganizationDaoDB orgDao;
    
    @Autowired
    SightingDaoDB sightDao;
    
    @Autowired
    PowerDaoDB powerDao;

    @Override
    public Organization addOrganization(Organization toAdd) {         
        toAdd = locDao.fillLocation(toAdd);
        toAdd = idDao.fillIdentities(toAdd);
        toAdd = orgDao.addOrganization(toAdd);  
        return toAdd;
    }

    @Override
    public Organization updateOrganization(Organization toUpdate) {
        toUpdate = locDao.fillLocation(toUpdate);
        toUpdate = idDao.fillIdentities(toUpdate);
        toUpdate = orgDao.updateOrganization(toUpdate);
        
        return toUpdate;
    }

    @Override
    public void deleteOrganizationById(int id) {
        orgDao.deleteOrganizationById(id);
    }

    @Override
    public Organization getOrganizationById(int id) {
        //get org by id
        Organization org = orgDao.getOrganizationById(id);
        //give that org it's list of superheroes
        org = idDao.fillIdentities(org);
        return org;
    }

    @Override
    public List<Organization> getOrganizationsForIdentity(int superId) {
        List<Organization> orgs = orgDao.getOrganizationsForIdentity(superId);
        orgs = idDao.fillOrgIdentities(orgs);
        return orgs;
    }

    @Override
    public List<Organization> getAllOrganizations() {
        List<Organization> all = orgDao.getAllOrganizations();
        all = idDao.fillOrgIdentities(all);
        return all;
    }

    public List<Organization> getOrganizationsForLocation(int locId) {
        List<Organization> orgs = orgDao.getOrganizationsForLocation(locId);
        return orgs;
    }

    public Map<Integer, List<Organization>> getOrgsByIdentityIds(List<Identity> identities) {
        Map<Integer, List<Organization> > orgsByIdentityIds = new HashMap<>();
        for(Identity person : identities){
            //get superId as int
            int i = person.getIdentityId();
            //get list<organization>
            List<Organization> orgsForSuper = getOrganizationsForIdentity(i);
            orgsByIdentityIds.put(i, orgsForSuper);
        }
        return orgsByIdentityIds;
    }
    
}
