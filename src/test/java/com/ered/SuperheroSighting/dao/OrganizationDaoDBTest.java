/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ered.SuperheroSighting.dao;

import com.ered.superherosighting.dao.LocationDaoDB;
import com.ered.superherosighting.dao.IdentityDaoDB;
import com.ered.superherosighting.dao.OrganizationDaoDB;
import com.ered.superherosighting.dao.OrganizationMapper;
import com.ered.superherosighting.dao.PowerDaoDB;
import com.ered.superherosighting.dao.SightingDaoDB;
import com.ered.superherosighting.entities.Location;
import com.ered.superherosighting.entities.Organization;
import com.ered.superherosighting.entities.Power;
import com.ered.superherosighting.entities.Sighting;
import com.ered.superherosighting.entities.Identity;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 *
 * @author esred
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class OrganizationDaoDBTest {
        
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
    
    public OrganizationDaoDBTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        //clear all heroes in db
        List<Identity> supers = superDao.getAllIdentities();
        for(Identity s : supers){
            superDao.deleteIdentityById( s.getIdentityId() );
        }
        //clear all locations in db
        List<Location> locations = locDao.getAllLocations();
        for(Location l : locations){
            locDao.deleteLocationById( l.getLocId() );
        }
        //clear all orgs in db
        List<Organization> orgs = orgDao.getAllOrganizations();
        for(Organization o : orgs){
            orgDao.deleteOrganizationById( o.getOrgId() );
        }
        //clear all sightings in db
        List<Sighting> sights = sightDao.getAllSightings();
        for(Sighting s : sights){
            sightDao.deleteSightingById( s.getSightingId() );
        }
        //clear all powers in db
        List<Power> powers = powerDao.getAllPowers();
        for(Power p : powers){
            powerDao.deletePowerById( p.getPowerId() );
        }
        
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of addOrganization method, of class OrganizationDaoDB.
     */
    @Test
    public void testAddOrganizationAndGetOrganizationById() {
        Location l = new Location();
        l.setLocName("Avengers Tower");
        l.setLocDescription("Formerly Stark Tower");
        l.setStreet("4 Times Square");
        l.setStreet2("Top Floor");
        l.setCity("New York");
        l.setState("NY");
        l.setZip("10036");
        l.setLatitude( "40.7562");
        l.setLongitude( "-73.9856");
        l = locDao.addLocation(l);
        int locId = l.getLocId();
        
        Organization o = new Organization();
        o.setOrgName("The best org");
        o.setOrgDescription("Only the best");
        o.setLoc(l);
        o = orgDao.addOrganization(o);
        int orgId = o.getOrgId();
        
        Organization r = orgDao.getOrganizationById(orgId);
        
        assertEquals(o , r);
    }

    /**
     * Test of updateOrganization method, of class OrganizationDaoDB.
     */
    @Test
    public void testUpdateOrganization() {
        Location l = new Location();
        l.setLocName("Avengers Tower");
        l.setLocDescription("Formerly Stark Tower");
        l.setStreet("4 Times Square");
        l.setStreet2("Top Floor");
        l.setCity("New York");
        l.setState("NY");
        l.setZip("10036");
        l.setLatitude( "40.7562");
        l.setLongitude( "-73.9856");
        l = locDao.addLocation(l);
                
        Organization o = new Organization();
        o.setOrgName("The best org");
        o.setOrgDescription("Only the best");
        o.setLoc(l);
        o = orgDao.addOrganization(o);
        int orgId = o.getOrgId();
        
        Organization r = orgDao.getOrganizationById(orgId);
        
        o.setOrgName("The worst!!");
        o.setOrgDescription("only the worst");
        orgDao.updateOrganization(o);
        
        Organization u = orgDao.getOrganizationById(orgId);
        
        assertEquals( r.getOrgName() , "The best org");
        assertEquals( r.getOrgDescription() , "Only the best");
        assertEquals( u.getOrgName() , "The worst!!");
        assertEquals( u.getOrgDescription() , "only the worst");
    }

    /**
     * Test of deleteOrganizationById method, of class OrganizationDaoDB.
     */
    @Test
    public void testDeleteOrganizationById() {
        Location l = new Location();
        l.setLocName("Avengers Tower");
        l.setLocDescription("Formerly Stark Tower");
        l.setStreet("4 Times Square");
        l.setStreet2("Top Floor");
        l.setCity("New York");
        l.setState("NY");
        l.setZip("10036");
        l.setLatitude( "40.7562");
        l.setLongitude( "-73.9856");
        l = locDao.addLocation(l);
        int locId = l.getLocId();
        
        Organization o = new Organization();
        o.setOrgName("The best org");
        o.setOrgDescription("Only the best");
        o.setLoc(l);
        o = orgDao.addOrganization(o);
        int orgId = o.getOrgId();
        
        Organization r = orgDao.getOrganizationById(orgId);
        
        orgDao.deleteOrganizationById(orgId);
        
        Organization d = orgDao.getOrganizationById(orgId);
        
        assertNotNull(r);
        assertNull(d);
    }

    
    /**
     * Test of getOrganizationsForHero method, of class OrganizationDaoDB.
     */
    @Test
    public void testGetOrganizationsForSuper() {
        Location l = new Location();
        l.setLocName("Avengers Tower");
        l.setLocDescription("Formerly Stark Tower");
        l.setStreet("4 Times Square");
        l.setStreet2("Top Floor");
        l.setCity("New York");
        l.setState("NY");
        l.setZip("10036");
        l.setLatitude( "40.7562");
        l.setLongitude( "-73.9856");
        l = locDao.addLocation(l);
        int locId = l.getLocId();
        
        //build a super
        Identity s = new Identity();
        s.setIdentityName("Testo");
        s.setIdentityDescription("A testy boi");
        s.setHero(true);
        List<Identity> supers = new ArrayList<>();
        s = superDao.addIdentity(s);
        supers.add(s);
        
        //build 3 organizations, putting super in o and o2, but not o3
        Organization o = new Organization();
        o.setOrgName("The best org");
        o.setOrgDescription("Only the best");
        o.setLoc(l);
        o.setIdentities(supers);
        o = orgDao.addOrganization(o);
        
        Organization o2 = new Organization();
        o2.setOrgName("An ok org");
        o2.setOrgDescription("Most supers are allowed");
        o2.setLoc(l);
        o2.setIdentities(supers);
        o2 = orgDao.addOrganization(o2);
        
        Organization o3 = new Organization();
        o3.setOrgName("Baddies");
        o3.setOrgDescription("Watch out for them!");
        o3.setLoc(l);       
        o3 = orgDao.addOrganization(o3);
        
        List<Organization> orgsForSuper = orgDao.getOrganizationsForIdentity(s.getIdentityId());
        
        //when we do getOrganizationForSuper it doesn't build 
        //the list<super> inside org
        assertTrue(orgsForSuper.get(0).equals(o));
        assertTrue(orgsForSuper.get(1).equals(o2));
        assertTrue(orgsForSuper.size() == 2);
    }
    
}
