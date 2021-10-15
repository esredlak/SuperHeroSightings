/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ered.superherosighting.dao;

import com.ered.superherosighting.entities.Power;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

/**
 *
 * @author Eric
 */
@Component
public class PowerMapper implements RowMapper<Power> {
    
    @Override
    public Power mapRow(ResultSet rs, int index) throws SQLException{
        Power power = new Power();
        power.setPowerId( rs.getInt("powerId") );
        power.setPowerName( rs.getString("powerName") ); 
        power.setPowerDescription( rs.getString("powerDescription"));
        return power;
    }
}

