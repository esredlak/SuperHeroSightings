/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ered.superherosighting.service;

import com.ered.superherosighting.dao.PowerDaoDB;
import com.ered.superherosighting.entities.Power;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Eric
 */
@Service
public class PowerServiceImpl implements PowerService{
    
    @Autowired
    PowerDaoDB powerDao;
    
    @Override
    public Power addPower(Power power) {
        power = powerDao.addPower(power);
        
        return power;
    }

    @Override
    public Power updatePower(Power power) {
        power = powerDao.updatePower(power);
        
        return power;
    }

    @Override
    public void deletePowerById(int id) {
        powerDao.deletePowerById(id);
    }

    @Override
    public Power getPowerById(int id) {
        Power power = powerDao.getPowerById(id);
        
        return power;
    }

    @Override
    public List<Power> getAllPowers() {
        List<Power> allPowers = powerDao.getAllPowers();
        
        return allPowers;
    }

    @Override
    public List<Power> getPowersForIdentity(int superId) {
        List<Power> powersForSuper = powerDao.getPowersForIdentity(superId);
        
        return powersForSuper;
    }

    public List<Power> getPowersByIds(List<Integer> powerIds) {
        List<Power> powers = new ArrayList<>();
        for(Integer i : powerIds){
            Power p = powerDao.getPowerById(i);
            powers.add(p);
        }
        return powers;
    }
    
}
