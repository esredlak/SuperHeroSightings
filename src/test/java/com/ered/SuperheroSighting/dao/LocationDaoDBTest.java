/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ered.SuperheroSighting.dao;

import com.ered.superherosighting.dao.LocationDaoDB;
import com.ered.superherosighting.dao.OrganizationDaoDB;
import com.ered.superherosighting.dao.PowerDaoDB;
import com.ered.superherosighting.dao.IdentityDaoDB;
import com.ered.superherosighting.dao.SightingDaoDB;
import com.ered.superherosighting.entities.Location;
import com.ered.superherosighting.entities.Organization;
import com.ered.superherosighting.entities.Power;
import com.ered.superherosighting.entities.Sighting;
import com.ered.superherosighting.entities.Identity;
import java.sql.Date;
import java.time.LocalDate;
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
public class LocationDaoDBTest {
    
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
           
    public LocationDaoDBTest() {
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
     * Test of addLocation method, of class LocationDaoDB.
     */
    @Test
    public void testAddLocationAndGetLocationById() {
        //build location
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
        //put it into DAO
        l = locDao.addLocation(l);
        //get locId from loc
        int locId = l.getLocId();
        //retrieve loc
        Location r = locDao.getLocationById(locId);
        //compare
        assertEquals(l , r);
    }

    /**
     * Test of updateLocation method, of class LocationDaoDB.
     */
    @Test
    public void testUpdateLocation() {
        //build location
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
        //put it into DAO
        l = locDao.addLocation(l);
        //get locId from loc
        int locId = l.getLocId();
        //retrieve loc
        Location r = locDao.getLocationById(locId);
        
        //Create a new location and steal l's locId
        Location l2 = new Location();
        l2.setLocId(locId);
        l2.setLocName("Avengers Tower2");
        l2.setLocDescription("Formerly Stark Tower2");
        l2.setStreet("4 Times Square2");
        l2.setStreet2("Top Floor2");
        l2.setCity("New York2");
        l2.setState("GA");
        l2.setZip("10035");
        l2.setLatitude( "50.7562");
        l2.setLongitude( "-83.9856");
        
        Location u = locDao.updateLocation(l2);
        
        assertEquals( l , r );
        assertEquals( l2, u );
    }

    /**
     * Test of deleteLocationById method, of class LocationDaoDB.
     */
    @Test
    public void testDeleteLocationById() {
        //build location
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
        //put it into DAO
        l = locDao.addLocation(l);
        //get locId from loc
        int locId = l.getLocId();
        //retrieve loc
        Location r = locDao.getLocationById(locId);
        //delete location from db
        locDao.deleteLocationById(locId);
        //get deleted location
        Location d = locDao.getLocationById(locId);
        //check that r is not null
        assertNotNull(r);
        //check that d is null
        assertNull(d);
    }

    /**
     * Test of getLocationsByHero method, of class LocationDaoDB.
     */
    @Test
    public void testGetLocationsBySuper() {
        //build 2 locs
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
        
        Location l2 = new Location();
        l2.setLocName("Avengers Tower2");
        l2.setLocDescription("Formerly Stark Tower2");
        l2.setStreet("4 Times Square2");
        l2.setStreet2("Top Floor2");
        l2.setCity("New York2");
        l2.setState("GA");
        l2.setZip("10035");
        l2.setLatitude( "50.7562");
        l2.setLongitude( "-83.9856");
        l2 = locDao.addLocation(l2);
       
        //build a power
        Power p = new Power();
        p.setPowerName("Flight");
        p.setPowerDescription("like a bird");
        p = powerDao.addPower(p);
        List<Power> powers = powerDao.getAllPowers();
        //build a super
        Identity s = new Identity();
        s.setIdentityName("Superman");
        s.setIdentityDescription("Is it a bird? A plane?");
        s.setHero(true);
        s.setPowers(powers);
        s = superDao.addIdentity(s);
        List<Identity> supers = new ArrayList<>();
        supers.add(s);
        //build 2 sightings, one at each loc
        Sighting st = new Sighting();
        st.setLoc(l);
        st.setSightingDate( Date.valueOf(LocalDate.now() ));
        st.setSightingDescription("Checking in on the Avengers?");
        st.setIdentities(supers);
        st = sightDao.addSighting(st);
        
        Sighting st2 = new Sighting();
        st2.setLoc(l2);
        st2.setSightingDate( Date.valueOf(LocalDate.now()) );
        st2.setSightingDescription("Checking in on the CDC");
        st2.setIdentities(supers);
        st2 = sightDao.addSighting(st2);
        
        List<Location> locs = locDao.getLocationsByIdentity( s.getIdentityId() );
        
        assertTrue(locs.get(0).equals(l) );
        assertTrue(locs.get(1).equals(l2));
    }   

    /**
     * Test of getLocationBySighting method, of class LocationDaoDB.
     */
    @Test
    public void testGetLocationBySighting() {
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
        
        Sighting st = new Sighting();
        st.setLoc(l);
        st.setSightingDate( Date.valueOf(LocalDate.now() ));
        st.setSightingDescription("Who was even sighted?");
        List<Identity> supers = new ArrayList<>();
        st.setIdentities(supers);
        st = sightDao.addSighting(st);
        int sightId = st.getSightingId();
        
        Location r = locDao.getLocationBySighting(sightId);
        
        assertEquals(l , r);
    }
    
    @Test
    public void testGetAllLocations(){
       //build location
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
        //put it into DAO
        l = locDao.addLocation(l);

        //Create a new location
        Location l2 = new Location();
        l2.setLocName("CDC Headquarters");
        l2.setLocDescription("Center for Disease Control & Prevention");
        l2.setStreet("1600 Clifton Rd");
        l2.setStreet2("-");
        l2.setCity("Atlanta");
        l2.setState("GA");
        l2.setZip("30333");
        l2.setLatitude( "33.7997");
        l2.setLongitude( "-84.3281");
        
        l2 = locDao.addLocation(l2);
        
        List<Location> locs = locDao.getAllLocations();
        
        assertTrue(locs.contains(l));
        assertTrue(locs.contains(l2));
        
    }
}
