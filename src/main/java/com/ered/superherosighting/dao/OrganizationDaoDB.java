/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ered.superherosighting.dao;

import com.ered.superherosighting.entities.Organization;
import com.ered.superherosighting.entities.Power;
import com.ered.superherosighting.entities.Identity;
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
public class OrganizationDaoDB implements OrganizationDao {

    @Autowired
    JdbcTemplate jdbc;
    
    @Override
    @Transactional
    public Organization addOrganization(Organization org) {
        //add organization into organization table
        final String INSERT_ORG = "INSERT INTO organization"
                + "(orgName , orgDescription , locId) VALUES (?,?,?)";
        jdbc.update(INSERT_ORG,
                org.getOrgName(),
                org.getOrgDescription(),
                org.getLoc().getLocId()
        );
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        org.setOrgId(newId);
        
        addToSuper_Organization(org);
        
        return org;
    }
    
    private void addToSuper_Organization(Organization org){
        //for each super in the org's List<SuperPerson> supers
        //add entries to super_organization using superId and orgId
        final String INSERT_SO = "INSERT INTO super_organization "
                + "(orgId, superId) VALUES (?,?)";
        List<Identity> supers = org.getIdentities();
        if (!supers.isEmpty()){
            for(Identity s : supers) {          
                jdbc.update(INSERT_SO,
                        org.getOrgId(),
                        s.getIdentityId()
                );
            }
        }
    }
    
    //transcational b/c we are modifying multiple tables
    @Transactional
    @Override
    public Organization updateOrganization(Organization org) {
        final String UPDATE_ORG = "UPDATE organization SET orgName = ? , "
                + "orgDescription = ? , locId = ? "
                + "WHERE orgId = ?";
        jdbc.update(UPDATE_ORG, 
                org.getOrgName(),
                org.getOrgDescription(),
                org.getLoc().getLocId(),
                org.getOrgId()
        );
        
        updateHero_Organization(org);
        return org;
    }
    
    @Transactional
    private void updateHero_Organization(Organization org) {
        //delete from table
        final String DELETE_SO = "DELETE FROM super_organization "
                + "WHERE orgId = ?";
        jdbc.update(DELETE_SO, org.getOrgId());
        
        //for each hero in the org's List<Hero> heroes
        //add entries to hero_organization using heroId and orgId
        addToSuper_Organization(org);
          
    }

    @Override
    @Transactional
    public void deleteOrganizationById(int id) {
        //orgId is FK in hero_organization so delete all rows in h_o
        final String DELETE_SUPER_ORG = "DELETE FROM super_organization WHERE orgId = ?";
        jdbc.update(DELETE_SUPER_ORG , id);
        //then delete all entries in organization
        final String DELETE_ORG = "DELETE FROM organization WHERE orgId = ?";
        jdbc.update(DELETE_ORG, id);
    }

    @Override
    @Transactional
    public Organization getOrganizationById(int orgId) {
        try {
            final String GET_ORG = "SELECT o.*, l.* FROM organization o "
                    + "JOIN location l ON o.locId = l.locId "
                    + "WHERE orgId = ?";
            Organization org = jdbc.queryForObject(GET_ORG, new OrganizationMapper(), orgId);
            
            List<Identity> supers = getSupersForOrganization(orgId);
            org.setIdentities(supers);
            
            return org;
        } catch (DataAccessException e) {
            return null;
        }
    }

    private List<Identity> getSupersForOrganization(int id) {
        final String GET_SUPERS_FOR_ORG = "SELECT s.* FROM super s "
                + "JOIN super_organization so ON s.superId = so.superId "
                + "WHERE so.orgId = ?";
        List<Identity> supers;
        supers = jdbc.query(GET_SUPERS_FOR_ORG, new IdentityMapper(), id);
        for(Identity s : supers) {
            s.setPowers( getPowersForSuper( s.getIdentityId()) );
        }
        return supers;
    }
    
    private List<Power> getPowersForSuper(int superId) {
        final String GET_P_FOR_S = "SELECT p.* FROM power p "
                + "JOIN super_power sp ON p.powerId = sp.powerId "
                + "WHERE sp.superId = ?";
        List<Power> powers = jdbc.query(GET_P_FOR_S, new PowerMapper(), superId);
        
        return powers;
    }
    
    @Override
    public List<Organization> getOrganizationsForIdentity(int superId) {
        final String GET_ORG_FOR_HERO = "SELECT o.*, l.* FROM organization o "
                + "JOIN location l ON o.locId = l.locId "
                + "JOIN super_organization so ON o.orgId = so.orgId "
                + "WHERE so.superId = ?";
        List<Organization> orgs;
        orgs = jdbc.query(GET_ORG_FOR_HERO, new OrganizationMapper(), superId);
        for(Organization o : orgs) {
            o.setIdentities( getSupersForOrganization(o.getOrgId()) );
        }
        return orgs;
    }

    @Override
    public List<Organization> getAllOrganizations() {
        final String GET_ALL_ORGS = "SELECT o.*, l.* FROM organization o "
                + "JOIN location l ON o.locId = l.locId ";
        List<Organization> orgs;
        orgs = jdbc.query(GET_ALL_ORGS, new OrganizationMapper());
        return orgs;
    }

    public List<Organization> getOrganizationsForLocation(int locId) {
        final String GET_ORGS_FOR_LOC = "SELECT o.*, l.* FROM organization o "
                + "JOIN location l ON o.locId = l.locId "
                + "WHERE l.locId = ?";
        List<Organization> orgs;
        orgs = jdbc.query(GET_ORGS_FOR_LOC, new OrganizationMapper(), locId);
        for(Organization o : orgs) {
            o.setIdentities( getSupersForOrganization(o.getOrgId()) );
        }
        return orgs;
    }
   
}


