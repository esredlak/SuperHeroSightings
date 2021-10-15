/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ered.superherosighting.dao;

import com.ered.superherosighting.entities.Location;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

/**
 *
 * @author Eric
 */
@Component
public class LocationMapper implements RowMapper<Location> {
    
    @Override
    public Location mapRow(ResultSet rs, int index) throws SQLException{
        Location l = new Location();
        l.setLocId( rs.getInt("locId") );
        l.setLocName( rs.getString("locName"));
        l.setLocDescription( rs.getString("locDescription"));
        l.setStreet( rs.getString("street"));
        l.setStreet2( rs.getString( "street2"));
        l.setCity( rs.getString("city"));
        l.setState( rs.getString("state"));
        l.setZip( rs.getString("zip"));
        l.setLongitude( rs.getString("longitude"));
        l.setLatitude( rs.getString("latitude"));
        
        return l;
    }
}
