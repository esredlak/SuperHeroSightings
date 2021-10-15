/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ered.SuperheroSighting.dao;

import com.ered.superherosighting.dao.IdentityDaoDB;
import com.ered.superherosighting.dao.LocationDaoDB;
import com.ered.superherosighting.dao.OrganizationDaoDB;
import com.ered.superherosighting.dao.PowerDaoDB;
import com.ered.superherosighting.dao.SightingDaoDB;
import com.ered.superherosighting.entities.Location;
import com.ered.superherosighting.entities.Organization;
import com.ered.superherosighting.entities.Power;
import com.ered.superherosighting.entities.Sighting;
import com.ered.superherosighting.entities.Identity;
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
 * @author Eric
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PowerDaoDBTest {
        
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
    
    public PowerDaoDBTest() {
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
     * Test of addPower method, of class PowerDaoDB.
     */
    @Test
    public void testAddPowerAndGetPowerById() {
        Power p = new Power();
        p.setPowerName("Flight");
        p.setPowerDescription("Like birds do");
        p = powerDao.addPower(p);
        int powerId = p.getPowerId();
        
        Power r = powerDao.getPowerById(powerId);
        
        assertEquals( p , r );
    }

    /**
     * Test of updatePower method, of class PowerDaoDB.
     */
    @Test
    public void testUpdatePower() {
        //build a power
        Power p = new Power();
        p.setPowerName("Flight");
        p.setPowerDescription("Like birds do");
        p = powerDao.addPower(p);
        int powerId = p.getPowerId();
        //get that power from DAO
        Power r = powerDao.getPowerById(powerId);
        //alter initial powerName  & description
        p.setPowerName("Levitation");
        p.setPowerDescription("Floaty");
        powerDao.updatePower(p);
        //get updated power from DAO
        Power u = powerDao.getPowerById(powerId);
        //check their name and description
        assertEquals(r.getPowerName(), "Flight");
        assertEquals(r.getPowerDescription() , "Like birds do");
        assertEquals(u.getPowerName(), "Levitation");
        assertEquals(u.getPowerDescription() , "Floaty");
    }
 

    @Test
    public void testDeletePower(){
        //build a power
        Power p = new Power();
        p.setPowerName("Flight");
        p.setPowerDescription("Like birds do");
        p = powerDao.addPower(p);
        int powerId = p.getPowerId();
        //delete it using powerId
        powerDao.deletePowerById(powerId);
        //try to retrieve the power @ powerId
        Power r = powerDao.getPowerById(powerId);
        //assert retrieved = null
        assertNull(r);
        assertNotNull(p);
    }
    
    /**
     * Test of getAllPowers method, of class PowerDaoDB.
     */
    @Test
    public void testGetAllPowers() {
        Power p = new Power();
        p.setPowerName("Flight");
        p = powerDao.addPower(p);
        Power p2 = new Power();
        p2.setPowerName("X-ray vision");
        p2 = powerDao.addPower(p2);
        List<Power> powers = powerDao.getAllPowers();     
        
        assertTrue(powers.contains(p));
        assertTrue(powers.contains(p2));
    }
       
}
