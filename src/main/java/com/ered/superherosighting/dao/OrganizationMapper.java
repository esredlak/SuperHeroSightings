/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ered.superherosighting.dao;

import com.ered.superherosighting.entities.Location;
import com.ered.superherosighting.entities.Organization;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

/**
 *
 * @author Eric
 */

@Component
public class OrganizationMapper implements RowMapper<Organization> {
    
    @Override
    public Organization mapRow(ResultSet rs, int index) throws SQLException {
        Organization org = new Organization();
        org.setOrgId( rs.getInt("orgId"));
        org.setOrgName( rs.getString("orgName"));
        org.setOrgDescription( rs.getString("orgDescription"));
        org.setLocId( rs.getInt("locId"));
        
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
        org.setLoc(loc);
        
        return org;
    }
    
}
