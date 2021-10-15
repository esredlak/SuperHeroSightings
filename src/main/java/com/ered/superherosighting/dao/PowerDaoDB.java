/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ered.superherosighting.dao;

import com.ered.superherosighting.entities.Power;
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
public class PowerDaoDB implements PowerDao{

    @Autowired
    JdbcTemplate jdbc;
    
    @Autowired
    PowerMapper powerMapper;
    
    @Override
    @Transactional
    public Power addPower(Power power) {
        final String INSERT_POWER = "INSERT INTO power(powerName , powerDescription) "
                + "VALUES(?,?)";
        jdbc.update(INSERT_POWER,
                power.getPowerName(),
                power.getPowerDescription()
        );
        
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        power.setPowerId(newId);
        return power;
    }

    @Override
    public Power updatePower(Power power) {
        final String UPDATE_POWER = "UPDATE power SET powerName = ? , powerDescription = ? "
                + "WHERE powerId = ?";
        jdbc.update(UPDATE_POWER,
            power.getPowerName(),
            power.getPowerDescription(),
            power.getPowerId()
        );
        return power;
    }

    @Override
    @Transactional
    public void deletePowerById(int powerId) {
        //delete all entries in Super_Power
        final String DELETE_S_P = "DELETE FROM super_power WHERE powerId = ?";
        jdbc.update(DELETE_S_P , powerId);
        //delete all entries in power
        final String DELETE_POWER = "DELETE FROM power WHERE powerId = ?";
        jdbc.update(DELETE_POWER,
                powerId
        );
    }

    @Override
    public Power getPowerById(int id) {
        try {
            final String GET_POWER_BY_ID = "SELECT * FROM power "
                + "WHERE powerId = ?";
            return jdbc.queryForObject(GET_POWER_BY_ID, powerMapper , id);
        } catch (DataAccessException e){
            return null;
        }      
    }

    @Override
    public List<Power> getAllPowers() {
        final String GET_ALL_POWERS = "SELECT * FROM power";
        List<Power> powers = jdbc.query(GET_ALL_POWERS, new PowerMapper());
        return powers;
    }

    @Override
    public List<Power> getPowersForIdentity(int superId) {
        final String GET_P_FOR_S = "SELECT p.* FROM power p "
                + "JOIN super_power sp ON p.powerId = sp.powerId "
                + "WHERE sp.superId = ?";
        List<Power> powers = jdbc.query(GET_P_FOR_S, new PowerMapper(), superId);
        
        return powers;
    }

    //tries to fill powers based on the passed in powerIds list.
    //if that list is empty, gets list by identityId and builds that list.
    @Override
    public Identity fillIdentityPowers(Identity id) {
        List<Power> powers = new ArrayList<>();
        List<Integer> powerIds = id.getPowerIds();
        
        try{
            for(Integer i : powerIds){
                Power p = getPowerById(i);
                powers.add(p);
            }
        } catch (NullPointerException e ){
            powers = getPowersForIdentity( id.getIdentityId() );
            powerIds = new ArrayList<>();
            for(Power p : powers){
                    int powerId = p.getPowerId();
                    powerIds.add(powerId);
                }
        }     
        id.setPowers(powers);
        id.setPowerIds(powerIds);
        return id;
    }

    @Override
    public List<Identity> fillIdentityPowers(List<Identity> supers) {
        supers.forEach(s -> {
            
            s = fillIdentityPowers(s);
        });
        return supers;
    }
    
}