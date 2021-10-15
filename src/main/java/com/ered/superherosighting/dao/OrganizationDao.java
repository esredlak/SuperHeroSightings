/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ered.superherosighting.dao;

import com.ered.superherosighting.entities.Organization;
import java.util.List;

/**
 *
 * @author Eric
 */
public interface OrganizationDao {
    //crud
    Organization addOrganization(Organization toAdd);
    
    Organization updateOrganization(Organization toUpdate);
    
    void deleteOrganizationById(int id);
    
    Organization getOrganizationById(int id);
    
    List<Organization> getOrganizationsForIdentity(int superId);
    
    List<Organization> getAllOrganizations();
}
