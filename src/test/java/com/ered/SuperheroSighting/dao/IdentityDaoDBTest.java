/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ered.SuperheroSighting.dao;
import com.ered.superherosighting.dao.IdentityDaoDB;
import com.ered.superherosighting.dao.LocationDaoDB;
import com.ered.superherosighting.dao.OrganizationDaoDB;
import com.ered.superherosighting.dao.SightingDaoDB;
import com.ered.superherosighting.dao.PowerDaoDB;
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
public class IdentityDaoDBTest {
    
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
    
    public IdentityDaoDBTest() {
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
     * Test of addHero method, of class HeroDaoDB.
     */
    @Test
    public void testAddSuperAndGetSuperById() {
        //build 2 powers
        Power p = new Power();
        p.setPowerName("Size Manipulation");
        p.setPowerDescription("Can resize at will");
        p = powerDao.addPower(p);
        Power p2 = new Power();
        p2.setPowerName("Insect Telepathy");
        p2.setPowerDescription("Can control insects");
        p2 = powerDao.addPower(p2);
        List<Power> powers = new ArrayList<>();
        powers.add(p);
        powers.add(p2);
        //build a super and give it that list<power>
        Identity s = new Identity();
        s.setIdentityName("Wasp");
        s.setIdentityDescription("Nasty sting");
        s.setPowers(powers);
        s.setHero(true);
        s = superDao.addIdentity(s);
        int superId = s.getIdentityId();
        
        Identity r = superDao.getIdentityById( superId );
        
        assertEquals(s , r);
    }

    /**
     * Test of updateHero method, of class HeroDaoDB.
     */
    @Test
    public void testUpdateHero() {
        //build 2 powers
        Power p = new Power();
        p.setPowerName("Size Manipulation");
        p.setPowerDescription("Can resize at will");
        p = powerDao.addPower(p);
        Power p2 = new Power();
        p2.setPowerName("Insect Telepathy");
        p2.setPowerDescription("Can control insects");
        p2 = powerDao.addPower(p2);
        List<Power> powers = new ArrayList<>();
        powers.add(p);
        powers.add(p2);
        //build a super and give it that list<power>
        Identity s = new Identity();
        s.setIdentityName("Wasp");
        s.setIdentityDescription("Nasty sting");
        s.setPowers(powers);
        s.setHero(true);
        s = superDao.addIdentity(s);
        int superId = s.getIdentityId();
        
        Identity r = superDao.getIdentityById( superId );
        
        s.setIdentityName("Wespe");
        s.setIdentityDescription("boser Stachel");
        
        superDao.updateIdentity(s);
        
        Identity u = superDao.getIdentityById( superId );
        
        assertEquals(r.getIdentityName() , "Wasp");
        assertEquals(r.getIdentityDescription() , "Nasty sting");
        assertEquals(u.getIdentityName() , "Wespe");
        assertEquals(u.getIdentityDescription() , "boser Stachel");
    }

    /**
     * Test of deleteHeroById method, of class HeroDaoDB.
     */
    @Test
    public void testDeleteHeroById() {
        //build 2 powers
        Power p = new Power();
        p.setPowerName("Size Manipulation");
        p.setPowerDescription("Can resize at will");
        p = powerDao.addPower(p);
        Power p2 = new Power();
        p2.setPowerName("Insect Telepathy");
        p2.setPowerDescription("Can control insects");
        p2 = powerDao.addPower(p2);
        List<Power> powers = new ArrayList<>();
        powers.add(p);
        powers.add(p2);
        //build a super and give it that list<power>
        Identity s = new Identity();
        s.setIdentityName("Wasp");
        s.setIdentityDescription("Nasty sting");
        s.setPowers(powers);
        s.setHero(true);
        s = superDao.addIdentity(s);
        int superId = s.getIdentityId();
        
        Identity r = superDao.getIdentityById( superId );
        
        superDao.deleteIdentityById(superId);
        
        Identity d = superDao.getIdentityById( superId );
        
        assertNotNull(r);
        assertNull(d);
    }

    /**
     * Test of getAllHeroes method, of class HeroDaoDB.
     */
    @Test
    public void testGetAllSupers() {
        //build 2 powers
        Power p = new Power();
        p.setPowerName("Size Manipulation");
        p.setPowerDescription("Can resize at will");
        p = powerDao.addPower(p);
        Power p2 = new Power();
        p2.setPowerName("Insect Telepathy");
        p2.setPowerDescription("Can control insects");
        p2 = powerDao.addPower(p2);
        List<Power> powers = new ArrayList<>();
        powers.add(p);
        powers.add(p2);
        //build a super and give it that list<power>
        Identity s = new Identity();
        s.setIdentityName("Wasp");
        s.setIdentityDescription("Nasty sting");
        s.setPowers(powers);
        s.setHero(true);
        s = superDao.addIdentity(s);
        int superId = s.getIdentityId();
        
        Identity s2 = new Identity();
        s2.setIdentityName("Antman");
        s2.setIdentityDescription("Very strong for his size. Can change size.");
        s2.setPowers(powers);
        s2.setHero(true);
        s2 = superDao.addIdentity(s2);
        int superId2 = s2.getIdentityId();
        
        List<Identity> supers = superDao.getAllIdentities();
        
        assertTrue(supers.contains(s));
        assertTrue(supers.contains(s2));
        
    }

    /**
     * Test of getHeroesForOrganization method, of class HeroDaoDB.
     */
    @Test
    public void testGetHeroesForOrganization() {
        //build 3 supers. 
        Identity s = new Identity();
        s.setIdentityName("Wasp");
        s.setIdentityDescription("Nasty sting");
        s.setHero(true);
        s = superDao.addIdentity(s);
        Identity s2 = new Identity();
        s2.setIdentityName("Wespe");
        s2.setIdentityDescription("Deutsche Wasp");
        s2.setHero(true);
        s2 = superDao.addIdentity(s2);
        Identity s3 = new Identity();
        s3.setIdentityName("Doc Oc");
        s3.setIdentityDescription("Insanity");
        s3.setHero(false);
        s3 = superDao.addIdentity(s3);
        //build a location for org
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
        //build an org
        Organization o = new Organization();
        o.setOrgName("International Insect Heroes Club");
        o.setOrgDescription("You already know");
        o.setLoc(l);
        List<Identity> heroes = new ArrayList<>();
        //put first 2 supers into it
        heroes.add(s);
        heroes.add(s2);
        o.setIdentities(heroes);
        o = orgDao.addOrganization(o);
        //get all heroes for org
        List<Identity> retrieved = superDao.getIdentitiesForOrganization( o.getOrgId() );
        //assert that first 2 supers are in and 3rd is not
        assertTrue(retrieved.contains(s));
        assertTrue(retrieved.contains(s2));
        assertFalse(retrieved.contains(s3));
    }

    /**
     * Test of getHeroesForLocation method, of class HeroDaoDB.
     */
    @Test
    public void testGetHeroesForLocation() {
        //build 4 supers
        //build 2 sightings. s and s2 into first, s3 into second, s4 into third
        //first 2 sightings at same location, third at different loc
        Identity s = new Identity();
        s.setIdentityName("Wasp");
        s.setIdentityDescription("Nasty sting");
        s.setHero(true);
        s = superDao.addIdentity(s);
        Identity s2 = new Identity();
        s2.setIdentityName("Wespe");
        s2.setIdentityDescription("Deutsche Wasp");
        s2.setHero(true);
        s2 = superDao.addIdentity(s2);
        Identity s3 = new Identity();
        s3.setIdentityName("Doc Oc");
        s3.setIdentityDescription("Insanity");
        s3.setHero(false);
        s3 = superDao.addIdentity(s3);
        Identity s4 = new Identity();
        s4.setIdentityName("Invisible Man");
        s4.setIdentityDescription("Did you really sight him?");
        s4.setHero(true);
        s4 = superDao.addIdentity(s4);
        
        //build 2 locations for sightings to occur
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
        
        Sighting si = new Sighting();
        si.setLoc(l);
        si.setSightingDate( Date.valueOf( LocalDate.now()));
        si.setSightingDescription("The American and German Wasps catching up");
        List<Identity> wasps = new ArrayList<>();
        wasps.add(s);
        wasps.add(s2);
        si.setIdentities(wasps);
        si = sightDao.addSighting(si);
        
        Sighting si2 = new Sighting();
        si2.setLoc(l);
        si2.setSightingDate( Date.valueOf(LocalDate.now()));
        si2.setSightingDescription("Doc Oc buying crocs");
        List<Identity> villians = new ArrayList<>();
        villians.add(s3);
        si2.setIdentities(villians);
        si2 = sightDao.addSighting(si2);
        
        //build a location and a sighting that won't be included in returned list
        Location l2 = new Location();
        l2.setLocName("Test");
        l2.setLocDescription("A very testy place");
        l2.setStreet("267 Oglethorpe Way");
        l2.setStreet2("");
        l2.setCity("Kingsland");
        l2.setState("GA");
        l2.setZip("31548");
        l2.setLongitude( "3.2546");
        l2.setLatitude( "52.3655");  
        l2 = locDao.addLocation(l2);
        
        Sighting si3 = new Sighting();
        si3.setLoc(l2);
        si3.setSightingDate( Date.valueOf(LocalDate.now()));
        si3.setSightingDescription("Was it really a sighting?");
        List<Identity> hardToSee = new ArrayList<>();
        hardToSee.add(s4);
        si3.setIdentities(hardToSee);
        si3 = sightDao.addSighting(si3);
       
        List<Identity> atLoc = superDao.getIdentitiesForLocation( l.getLocId() );
        List<Identity> atLoc2 = superDao.getIdentitiesForLocation( l2.getLocId() );
        
        //s4 is getting put into atLoc and not into atLoc2
        assertTrue(atLoc.contains(s));
        assertTrue(atLoc.contains(s2));
        assertTrue(atLoc.contains(s3));
        assertFalse(atLoc.contains(s4));
        assertTrue(atLoc2.contains(s4));
        assertTrue(atLoc2.size() == 1 );
    }

    /**
     * Test of getHeroBySighting method, of class HeroDaoDB.
     */
    @Test
    public void testGetHeroBySighting() {
        //build 3 heroes
        Identity s = new Identity();
        s.setIdentityName("Wasp");
        s.setIdentityDescription("Nasty sting");
        s.setHero(true);
        s = superDao.addIdentity(s);
        Identity s2 = new Identity();
        s2.setIdentityName("Wespe");
        s2.setIdentityDescription("Deutsche Wasp");
        s2.setHero(true);
        s2 = superDao.addIdentity(s2);
        Identity s3 = new Identity();
        s3.setIdentityName("Doc Oc");
        s3.setIdentityDescription("Insanity");
        s3.setHero(false);
        s3 = superDao.addIdentity(s3);
        //build 2 sightings
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
        
        //first 2 heroes seen at first sighting
        Sighting si = new Sighting();
        si.setLoc( l  );
        si.setSightingDate( Date.valueOf( LocalDate.now()));
        si.setSightingDescription("The American and German Wasps catching up");
        List<Identity> wasps = new ArrayList<>();
        wasps.add(s);
        wasps.add(s2);
        si.setIdentities(wasps);
        si = sightDao.addSighting(si);
        
        //third hero seen at second sighting
        Sighting si2 = new Sighting();
        si2.setLoc( l );
        si2.setSightingDate( Date.valueOf(LocalDate.now()));
        si2.setSightingDescription("Doc Oc buying crocs");
        List<Identity> villians = new ArrayList<>();
        villians.add(s3);
        si2.setIdentities(villians);
        si2 = sightDao.addSighting(si2);
         
        //get heroes for first sighting
        List<Identity> atSight1 = superDao.getIdentitiesBySighting( si.getSightingId() );
        List<Identity> atSight2 = superDao.getIdentitiesBySighting( si2.getSightingId() );
        //assert that it includes first two and not third
        assertTrue(atSight1.contains(s));
        assertTrue(atSight1.contains(s2));
        assertFalse(atSight1.contains(s3));
        assertTrue(atSight2.contains(s3));
        assertTrue(atSight2.size() == 1);
    }
    
}
