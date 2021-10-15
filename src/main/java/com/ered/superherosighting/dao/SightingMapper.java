/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ered.superherosighting.dao;

import com.ered.superherosighting.entities.Location;
import com.ered.superherosighting.entities.Sighting;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

/**
 *
 * @author Eric
 */
@Component
public class SightingMapper implements RowMapper<Sighting> {
    
    @Override
    public Sighting mapRow(ResultSet rs, int index) throws SQLException{
        Sighting sight = new Sighting();
        sight.setSightingId( rs.getInt("sightingId"));
        sight.setSightingDate( rs.getDate("sightingDate"));
        sight.setSightingDescription( rs.getString("sightingDescription"));
        sight.setLocId( rs.getInt("locId"));
        
        Location loc = new Location();
        loc.setLocId( rs.getInt("locId"));
        loc.setLocName( rs.getString("locName"));
        loc.setLocDescription( rs.getString("locDescription"));
        loc.setStreet( rs.getString("street"));
        loc.setStreet2( rs.getString("street2"));
        loc.setCity( rs.getString("city"));
        loc.setState( rs.getString("state"));
        loc.setZip( rs.getString("zip"));
        loc.setLatitude( rs.getString("latitude"));
        loc.setLongitude( rs.getString("longitude"));
        sight.setLoc(loc);
        
        return sight;
    }
    
}
