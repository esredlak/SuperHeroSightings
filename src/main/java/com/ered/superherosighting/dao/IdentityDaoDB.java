/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ered.superherosighting.dao;

import com.ered.superherosighting.entities.Organization;
import com.ered.superherosighting.entities.Power;
import com.ered.superherosighting.entities.Sighting;
import com.ered.superherosighting.entities.Identity;
import java.util.ArrayList;
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
public class IdentityDaoDB implements IdentityDao{
    
    @Autowired
    JdbcTemplate jdbc;

    @Override
    @Transactional
    public Identity addIdentity(Identity i) {
        final String INSERT_SUPER = "INSERT INTO super "
                + "(superName, superDescription, hero, imageFileName) "
                + "VALUES(?,?,?,?);";
        jdbc.update(INSERT_SUPER,
                i.getIdentityName(),
                i.getIdentityDescription(),
                i.isHero(),
                i.getImageFileName()
        );
        
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        i.setIdentityId(newId);
        linkPowersToSuper(i);
        i = fillSuperPowers(i);
        return i;
    }

    @Transactional
    private void linkPowersToSuper(Identity s) {
        final String INSERT_S_P = "INSERT INTO super_power(superId, powerId) "
                + "VALUES(?,?)";
        List<Power> powers = s.getPowers();
        //for each p, add an entry to super_power
        if(!(powers.isEmpty())){
            for(Power p : powers){
                jdbc.update(INSERT_S_P, 
                        s.getIdentityId(),
                        p.getPowerId()
                );
            }
        } 
    }
    
    @Override
    public Identity updateIdentity(Identity id) {
        final String UPDATE_SUPER = "UPDATE super SET superName = ?, "
                + "superDescription = ?, hero = ? "
                + "WHERE superId = ?";
        jdbc.update(UPDATE_SUPER,
                id.getIdentityName(),
                id.getIdentityDescription(),
                id.isHero(),
                id.getIdentityId()
        );
        //if imageFileName is not null, then update imageFileName too
        if ( id.getImageFileName() != null ){
            setIdentityFileName(id.getIdentityId() , id.getImageFileName());
        }
        updateSuperPowers(id);
        id = fillSuperPowers(id);
        return id;
    }

    @Override
    @Transactional
    public void deleteIdentityById(int id) {
        //superId is a FK in Super_organization, Super_Sighting, and Super_Power
        //delete all entries in s_o
        final String DELETE_S_O = "DELETE FROM Super_Organization WHERE superId = ?";
        jdbc.update(DELETE_S_O , id);
        //delete all entries in s_s
        final String DELETE_S_S = "DELETE FROM Super_Sighting WHERE superId = ?";
        jdbc.update(DELETE_S_S , id);
        //delete all entries in s_p
        final String DELETE_S_P = "DELETE FROM Super_Power WHERE superId = ?";
        jdbc.update(DELETE_S_P , id);
        //delete super
        final String DELETE_SUPER = "DELETE FROM super WHERE superId = ?";
        jdbc.update(DELETE_SUPER, id);
        
    }

    @Override
    public Identity getIdentityById(int id) {
        try {
            final String GET_SUPER = "SELECT * FROM super WHERE superId = ?";
            Identity s = jdbc.queryForObject(GET_SUPER, new IdentityMapper(), id);
            s = fillSuperPowers(s);
            return s;
        } catch (DataAccessException e){
            return null;
        }
    }
    
    @Override
    public List<Identity> getAllIdentities() {
        final String GET_ALL_SUPERS = "SELECT * FROM super";
        List<Identity> supers = jdbc.query(GET_ALL_SUPERS, new IdentityMapper());
        supers = fillIdentityPowers(supers);
        return supers;
    }

    @Override
    public List<Identity> getIdentitiesForOrganization(int orgId) {
        final String GET_SUPERS_FOR_ORG = "SELECT s.* FROM super s "
                + "JOIN super_organization so ON s.superId = so.superId "
                + "WHERE so.orgId = ?";
        List<Identity> supers = jdbc.query(GET_SUPERS_FOR_ORG, new IdentityMapper(), orgId);
        supers = fillIdentityPowers(supers);
        return supers;
    }

    @Override
    public List<Identity> getIdentitiesForLocation(int locId) {
        final String GET_SUPERS_FOR_LOC = "SELECT s.* FROM super s "
                + "JOIN super_sighting ss ON s.superId = ss.superId "
                + "JOIN sighting si ON ss.sightingId = si.sightingId "
                + "WHERE si.locId = ?";
        List<Identity> supers = jdbc.query(GET_SUPERS_FOR_LOC, new IdentityMapper(), locId);
        supers = fillIdentityPowers(supers);
        return supers;
    }

    @Override
    public List<Identity> getIdentitiesBySighting(int sightingId) {
        final String GET_SUPER_BY_SIGHTING = "SELECT * FROM super s "
                + "JOIN super_sighting ss ON s.superId = ss.superId "
                + "WHERE ss.sightingId = ?";
        List<Identity> supers = jdbc.query(GET_SUPER_BY_SIGHTING, new IdentityMapper(), sightingId);
        supers = fillIdentityPowers(supers);
        return supers;
    }

    @Override
    public List<Identity> getIdentitiesByPower(int powerId) {
        final String GET_SUPER_BY_POWER = "SELECT s.* from super s "
                + "JOIN super_power sp ON s.superId = sp.superId "
                + "WHERE sp.powerId = ?";
        List<Identity> supers;
        supers = jdbc.query(GET_SUPER_BY_POWER, new IdentityMapper(), powerId);
        supers = fillIdentityPowers(supers);
        return supers;
    }
    
    @Override
    public List<Sighting> fillSightIdentities(List<Sighting> sightings) {
        sightings.forEach(s -> {
            s.setIdentities( getIdentitiesBySighting( s.getSightingId() ) );
        });
        
        return sightings;
    }
    
    @Override
    public Sighting fillIdentities(Sighting s){
        List<Identity> supers = getIdentitiesBySighting( s.getSightingId() );
        supers = fillIdentityPowers(supers);
        s.setIdentities(supers);
        return s;
    }
    
    @Override
    public List<Organization> fillOrgIdentities(List<Organization> orgs){
        orgs.forEach(o -> {
            o.setIdentities( getIdentitiesForOrganization( o.getOrgId() ) );
        });
        return orgs;
    }
    
    
    //tries to fill identities based on the passed in identityIds list.
    //if that list is empty, gets list by orgid and builds that list.
    @Override
    public Organization fillIdentities(Organization o) {
        List<Identity> identities = new ArrayList<>();
        List<Integer> identityIds = o.getIdentityIds();
        
        if (identityIds.isEmpty() ){
            identities = getIdentitiesForOrganization(o.getOrgId());
            for(Identity identity : identities){
                int id = identity.getIdentityId();
                identityIds.add(id);
            }
        } else {
            try {
                for(Integer i : identityIds){
                    Identity s = getIdentityById(i);
                    identities.add(s);
                }
            } catch (NullPointerException e){
                //runs if superIds is null and fills by orgId instead.
                identities = getIdentitiesForOrganization(o.getOrgId());
            }
        }
        identities = fillIdentityPowers(identities);
        o.setIdentities(identities);
        return o;
    }
    
    /*3 of these methods are copied from powerDaoDB. 
    I'm leery of importing my powerDao into my superDao.
    it feels like the DAO should not be dependent on each other.
    But then again I'm breaking DRY.
    */
    
    private Identity fillSuperPowers(Identity s) {
        List<Power> powers = new ArrayList<>();
        List<Integer> powerIds = s.getPowerIds();
        try{
            for(Integer i : powerIds){
                Power p = getPowerById(i);
                powers.add(p);
            }
        } catch (NullPointerException e ){
            powers = getPowersForSuper( s.getIdentityId() );
        }     
        s.setPowers(powers);
        return s;
    }

    private List<Identity> fillIdentityPowers(List<Identity> supers) {
        supers.forEach(id -> {          
            id.setPowers( getPowersForSuper(id.getIdentityId()) );
        });

        return supers;
    }
    
    private List<Power> getPowersForSuper(int superId) {
        final String GET_P_FOR_S = "SELECT p.* FROM power p "
                + "JOIN super_power sp ON p.powerId = sp.powerId "
                + "WHERE sp.superId = ?";
        List<Power> powers = jdbc.query(GET_P_FOR_S, new PowerMapper(), superId);
        
        return powers;
    }
    
    private Power getPowerById(int id) {
        try {
            final String GET_POWER_BY_ID = "SELECT * FROM power "
                + "WHERE powerId = ?";
            return jdbc.queryForObject(GET_POWER_BY_ID, new PowerMapper() , id);
        } catch (DataAccessException e){
            return null;
        }      
    }
    
    @Transactional
    private void updateSuperPowers(Identity s) {
        final String DELETE_SUPER_POWER_ENTRIES = "DELETE FROM super_power sp "
                + "WHERE sp.superId = ?";
        final String INSERT_SUPER_POWER_ENTRIES = "INSERT INTO super_power "
                + "(superId, powerId) VALUES (?,?)";
        List<Power> powers = s.getPowers();
        jdbc.update(DELETE_SUPER_POWER_ENTRIES, s.getIdentityId());
        for(Power p : powers){
            jdbc.update(INSERT_SUPER_POWER_ENTRIES, s.getIdentityId() , p.getPowerId() );
        }
    }

    @Override
    public void setIdentityFileName(int id , String fileName) {
        final String SET_FILENAME = "UPDATE super SET imageFileName = ? "
                + "WHERE superId = ?";
        
        jdbc.update(SET_FILENAME,
                fileName,
                id
        );
        
    }

}
