/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ered.SuperheroSighting.dao;

import com.ered.superherosighting.dao.LocationDaoDB;
import com.ered.superherosighting.dao.OrganizationDaoDB;
import com.ered.superherosighting.dao.PowerDaoDB;
import com.ered.superherosighting.dao.SightingDaoDB;
import com.ered.superherosighting.dao.IdentityDaoDB;
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
public class SightingDaoDBTest {
        
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
    
    public SightingDaoDBTest() {
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
     * Test of addSighting method, of class SightingDaoDB.
     */
    @Test
    public void testAddSightingAndGetSightingById() {
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
        
        Sighting sight = new Sighting();
        sight.setLoc(l);
        sight.setLocId(l.getLocId());
        List<Integer> identityIds = new ArrayList<>();
        sight.setIdentityIds(identityIds);
        sight.setSightingDate( Date.valueOf( LocalDate.now() ));
        sight.setSightingDescription("How descriptive...");
        sight = sightDao.addSighting(sight);
        int sightId = sight.getSightingId();
        
        Sighting r = sightDao.getSightingById(sightId);
        
        assertEquals(sight , r);
    }

    /**
     * Test of updateSighting method, of class SightingDaoDB.
     */
    @Test
    public void testUpdateSighting() {
        Location l = new Location();
        l.setLocName("Avengers Tower");
        l.setLocDescription("Formerly Stark Tower");
        l.setStreet("4 Times Square");
        l.setStreet2("Top Floor");
        l.setCity("New York");
        l.setState("NY");
        l.setZip("10036");
        l.setLatitude( "40.7562");
        l.setLongitude( "-73.9856" );
        l = locDao.addLocation(l);
        int locId = l.getLocId();
        
        Sighting sight = new Sighting();
        sight.setLoc(l);
        sight.setSightingDate( Date.valueOf( LocalDate.now() ));
        sight.setSightingDescription("How descriptive...");
        sight = sightDao.addSighting(sight);
        int sightId = sight.getSightingId();
        
        Sighting r = sightDao.getSightingById(sightId);
        
        sight.setSightingDescription("Not very");
        
        sightDao.updateSighting(sight);
        
        Sighting u = sightDao.getSightingById(sightId);
        
        assertEquals( r.getSightingDescription(), "How descriptive...");
        assertEquals( u.getSightingDescription(), "Not very");
        
    }

    /**
     * Test of deleteSightingById method, of class SightingDaoDB.
     */
    @Test
    public void testDeleteSightingById() {
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
        
        Sighting sight = new Sighting();
        sight.setLoc(l);
        sight.setSightingDate( Date.valueOf( LocalDate.now() ));
        sight.setSightingDescription("How descriptive...");
        sight = sightDao.addSighting(sight);
        int sightId = sight.getSightingId();
        
        Sighting r = sightDao.getSightingById(sightId);
        
        sightDao.deleteSightingById(sightId);
        
        Sighting d = sightDao.getSightingById(sightId);
        
        assertNotNull(r);
        assertNull(d);
    }

    /**
     * Test of getSightingsByDate method, of class SightingDaoDB.
     */
    @Test
    public void testGetSightingsByDate() {
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
        
        Sighting sight = new Sighting();
        sight.setLoc(l);
        sight.setLocId(l.getLocId());
        sight.setSightingDate( Date.valueOf( "2020-01-01" ));
        sight.setSightingDescription("2020");
        sight = sightDao.addSighting(sight);
        
        Sighting sight2 = new Sighting();
        sight2.setLoc(l);
        sight2.setLocId(l.getLocId());
        sight2.setSightingDate( Date.valueOf("2021-01-01"));
        sight2.setSightingDescription("2021");
        sight2 = sightDao.addSighting(sight2);
        
        Sighting sight3 = new Sighting();
        sight3.setLoc(l);
        sight3.setLocId(l.getLocId());
        sight3.setSightingDate(Date.valueOf( "2020-01-01" ));
        sight3.setSightingDescription("2020, two");
        sight3 = sightDao.addSighting(sight3);
        List<Sighting> sights = sightDao.getSightingsByDate( Date.valueOf( "2020-01-01" ) );
        
        assertTrue(sights.contains(sight));
        assertFalse(sights.contains(sight2));
        assertTrue(sights.contains(sight3));
    }
    
    @Test
    public void testGetAllSightings(){
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
        
        Sighting sight = new Sighting();
        sight.setLoc(l);
        sight.setLocId(l.getLocId());
        sight.setSightingDate( Date.valueOf( "2020-01-01" ));
        sight.setSightingDescription("2020");
        sight = sightDao.addSighting(sight);
        
        Sighting sight2 = new Sighting();
        sight2.setLoc(l);
        sight2.setLocId(l.getLocId());
        sight2.setSightingDate( Date.valueOf("2021-01-01"));
        sight2.setSightingDescription("2021");
        sight2 = sightDao.addSighting(sight2);
        
        Sighting sight3 = new Sighting();
        sight3.setLoc(l);
        sight3.setLocId(l.getLocId());
        sight3.setSightingDate(Date.valueOf( "2020-01-01" ));
        sight3.setSightingDescription("2020, two");
        sight3 = sightDao.addSighting(sight3);
        
        List<Sighting> sights = sightDao.getAllSightings();
        
        assertTrue(sights.contains(sight));
        assertTrue(sights.contains(sight2));
        assertTrue(sights.contains(sight3));
    }
    
    @Test
    public void testGetLastTenSightings(){
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
        
        //put 12 sightings one day apart into db
        Sighting s0 = new Sighting();
        s0.setLoc(l);
        s0.setLocId(l.getLocId());
        s0.setSightingDescription("A sighting. Wowee.");
        s0.setSightingDate(Date.valueOf("2020-01-01"));
        s0 = sightDao.addSighting(s0);
        Sighting s1 = new Sighting();
        s1.setLoc(l);
        s1.setLocId(l.getLocId());
        s1.setSightingDescription("A sighting. Wowee.");
        s1.setSightingDate(Date.valueOf("2020-01-02"));
        s1 = sightDao.addSighting(s1);
        Sighting s2 = new Sighting();
        s2.setLoc(l);
        s2.setLocId(l.getLocId());
        s2.setSightingDescription("A sighting. Wowee.");
        s2.setSightingDate(Date.valueOf("2020-01-03"));
        s2 = sightDao.addSighting(s2);
        Sighting s3 = new Sighting();
        s3.setLoc(l);
        s3.setLocId(l.getLocId());
        s3.setSightingDescription("A sighting. Wowee.");
        s3.setSightingDate(Date.valueOf("2020-01-04"));
        s3 = sightDao.addSighting(s3);
        Sighting s4 = new Sighting();
        s4.setLoc(l);
        s4.setLocId(l.getLocId());
        s4.setSightingDescription("A sighting. Wowee.");
        s4.setSightingDate(Date.valueOf("2020-01-05"));
        s4 = sightDao.addSighting(s4);
        Sighting s5 = new Sighting();
        s5.setLoc(l);
        s5.setLocId(l.getLocId());
        s5.setSightingDescription("A sighting. Wowee.");
        s5.setSightingDate(Date.valueOf("2020-01-06"));
        s5 = sightDao.addSighting(s5);
        Sighting s6 = new Sighting();
        s6.setLoc(l);
        s6.setLocId(l.getLocId());
        s6.setSightingDescription("A sighting. Wowee.");
        s6.setSightingDate(Date.valueOf("2020-01-07"));
        s6 = sightDao.addSighting(s6);
        Sighting s7 = new Sighting();
        s7.setLoc(l);
        s7.setLocId(l.getLocId());
        s7.setSightingDescription("A sighting. Wowee.");
        s7.setSightingDate(Date.valueOf("2020-01-08"));
        s7 = sightDao.addSighting(s7);
        Sighting s8 = new Sighting();
        s8.setLoc(l);
        s8.setLocId(l.getLocId());
        s8.setSightingDescription("A sighting. Wowee.");
        s8.setSightingDate(Date.valueOf("2020-01-09"));
        s8 = sightDao.addSighting(s8);
        Sighting s9 = new Sighting();
        s9.setLoc(l);
        s9.setLocId(l.getLocId());
        s9.setSightingDescription("A sighting. Wowee.");
        s9.setSightingDate(Date.valueOf("2020-01-10"));
        s9 = sightDao.addSighting(s9);
        Sighting s10 = new Sighting();
        s10.setLoc(l);
        s10.setLocId(l.getLocId());
        s10.setSightingDescription("A sighting. Wowee.");
        s10.setSightingDate(Date.valueOf("2020-01-11"));
        s10 = sightDao.addSighting(s10);
        Sighting s11 = new Sighting();
        s11.setLoc(l);
        s11.setLocId(l.getLocId());
        s11.setSightingDescription("A sighting. Wowee.");
        s11.setSightingDate(Date.valueOf("2020-01-12"));
        s11 = sightDao.addSighting(s11);
        
        //get all sightings
        List<Sighting> all = sightDao.getAllSightings();
        //get last ten sightings
        List<Sighting> last10 = sightDao.getLastTenSightings();
        //verify that first 2 sightings are in all, but not in last 10
        assertTrue(all.contains(s0));
        assertTrue(all.contains(s1));
        assertFalse(last10.contains(s0));
        assertFalse(last10.contains(s1));
        //verify that sightings 3-12 (2-11) are in lastTen
        assertTrue(last10.contains(s2));
        assertTrue(last10.contains(s3));
        assertTrue(last10.contains(s4));
        assertTrue(last10.contains(s5));
        assertTrue(last10.contains(s6));
        assertTrue(last10.contains(s7));
        assertTrue(last10.contains(s8));
        assertTrue(last10.contains(s9));
        assertTrue(last10.contains(s10));
        assertTrue(last10.contains(s11));
    }
}
