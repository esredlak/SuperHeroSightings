/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ered.superherosighting.dao;

import com.ered.superherosighting.entities.Power;
import com.ered.superherosighting.entities.Identity;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

/**
 *
 * @author Eric
 */
@Component
final class IdentityMapper implements RowMapper<Identity>{

    @Override
    public Identity mapRow(ResultSet rs, int index) throws SQLException{
        Identity i = new Identity();
        i.setIdentityId( rs.getInt("superId"));
        i.setIdentityName( rs.getString("superName"));
        i.setIdentityDescription( rs.getString("superDescription"));
        i.setHero( rs.getBoolean("hero"));
        i.setImageFileName( rs.getString("imageFileName"));
        
        return i;
    }
}
