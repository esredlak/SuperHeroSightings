/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ered.superherosighting.dao;

import com.ered.superherosighting.entities.Organization;
import com.ered.superherosighting.entities.Sighting;
import com.ered.superherosighting.entities.Identity;
import java.util.List;

/**
 *
 * @author Eric
 */
public interface IdentityDao {
    /*
    Takes a super, saves it, and returns the saved super
    */
    Identity addIdentity(Identity toAdd);
    /*
    Takes a super, updates the record matching superId, returns updated Super
    */
    Identity updateIdentity(Identity toUpdate);
    /*
    Takes a superId, deletes the matching super, and returns the deleted super
    */
    void deleteIdentityById(int id);
    /*
    Takes a superId and returns the matching super.
    */
    Identity getIdentityById(int id);
    /*
    returns a list of all supers
    */
    List<Identity> getAllIdentities();
    /*
    Takes an organization'd id and returns all supers for that org
    */
    List<Identity> getIdentitiesForOrganization(int orgId);
    /*
    Takes a location's id and returns all supers seen at that location
    */
    List<Identity> getIdentitiesForLocation(int locId);
    
    List<Identity> getIdentitiesByPower(int powerId);
    
    List<Identity> getIdentitiesBySighting(int sightingId);
    
    List<Sighting> fillSightIdentities(List<Sighting> sightings);
    
    Sighting fillIdentities(Sighting s);
    
    List<Organization> fillOrgIdentities(List<Organization> orgs);
    
    Organization fillIdentities(Organization o);
    
    void setIdentityFileName(int id, String fileName);
}
