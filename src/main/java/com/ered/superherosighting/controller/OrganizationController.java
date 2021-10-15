/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ered.superherosighting.controller;
import com.ered.superherosighting.entities.Location;
import com.ered.superherosighting.entities.Organization;
import com.ered.superherosighting.entities.Identity;
import com.ered.superherosighting.service.LocationServiceImpl;
import com.ered.superherosighting.service.OrganizationServiceImpl;
import com.ered.superherosighting.service.PowerServiceImpl;
import com.ered.superherosighting.service.SightingServiceImpl;
import com.ered.superherosighting.service.IdentityServiceImpl;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author Eric
 */
@Controller
public class OrganizationController {
    
    @Autowired
    LocationServiceImpl locServ;
    
    @Autowired
    OrganizationServiceImpl orgServ;
    
    @Autowired
    PowerServiceImpl powServ;
    
    @Autowired
    SightingServiceImpl sightServ;
    
    @Autowired
    IdentityServiceImpl idServ;
    
    @GetMapping("organizations")
    public String serveOrganizations(Model model){
        List<Organization> organizations = orgServ.getAllOrganizations();
        model.addAttribute("organizations" , organizations);
        return "organizations";
    }
    
    @GetMapping("organization/new")
    public String serveNewOrganization(Model model){
        List<Location> locs = locServ.getAllLocations();
        List<Identity> allIdentities = idServ.getAllIdentities();
        model.addAttribute("locations" , locs);
        model.addAttribute("allIdentities" , allIdentities);
        model.addAttribute("organization", new Organization());
        return "organizationnew";
    }
    
    @PostMapping("organization/new")
    public String processNewOrganization(Model model, @Valid Organization org, BindingResult result){
        if (result.hasErrors()){
            List<Location> locs = locServ.getAllLocations();
            List<Identity> allIdentities = idServ.getAllIdentities();
            model.addAttribute("locations" , locs);
            model.addAttribute("allIdentities" , allIdentities);
            model.addAttribute("organization", org);
            return "organizationnew";
        }
        org = orgServ.addOrganization(org);
        return "redirect:/organization/" + org.getOrgId();
    }
    
    @GetMapping("organization/{orgId}")
    public String serveOrganizationById(Model model, @PathVariable int orgId){
        Organization o = orgServ.getOrganizationById(orgId);
        model.addAttribute("organization" , o);
        
        List<Identity> orgIdentities = idServ.getIdentitiesForOrganization(orgId);
        model.addAttribute("identities", orgIdentities);
        
        Map<Integer , List<Organization>> orgsByIdentityIds = orgServ.getOrgsByIdentityIds(orgIdentities);
        model.addAttribute("orgsByIdentityIds", orgsByIdentityIds);
        
        return "organizationdetails";
    }
    
    @GetMapping("organization/edit/{orgId}")
    public String serveEditOrganizationById(Model model, @PathVariable int orgId){
        Organization o = orgServ.getOrganizationById(orgId);
        model.addAttribute("organization", o);
        
        List<Location> locs = locServ.getAllLocations();
        model.addAttribute("locations" , locs);
        
        List<Identity> allIdentities = idServ.getAllIdentities();
        model.addAttribute("allIdentities" , allIdentities);
        
        List<Identity> orgIdentities = idServ.getIdentitiesForOrganization(orgId);
        model.addAttribute("identities", orgIdentities);
        
        Map<Integer , List<Organization>> orgsByIdentityIds = orgServ.getOrgsByIdentityIds(orgIdentities);
        model.addAttribute("orgsByIdentityIds", orgsByIdentityIds);
        
        return "organizationedit";
    }
    
    @PostMapping("organization/edit/{orgId}")
    public String processEditOrganizationById(Model model, @Valid Organization o, 
            BindingResult result ){
        if (result.hasErrors()) {
        
        List<Location> locs = locServ.getAllLocations();
        model.addAttribute("locations" , locs);
        
        List<Identity> allIdentities = idServ.getAllIdentities();
        model.addAttribute("allIdentities" , allIdentities);
        
        List<Identity> orgIdentities = idServ.getIdentitiesForOrganization( o.getOrgId() );
        model.addAttribute("identities", orgIdentities);
        
        Map<Integer , List<Organization>> orgsByIdentityIds = orgServ.getOrgsByIdentityIds(orgIdentities);
        model.addAttribute("orgsByIdentityIds", orgsByIdentityIds);
            return "organizationedit";
        }
        o = orgServ.updateOrganization(o);
        return "redirect:/organization/" + o.getOrgId();
    }
    
    @GetMapping("organization/delete/{orgId}")
    public String serveDeleteOrganizationById(Model model, @PathVariable int orgId){
        Organization o = orgServ.getOrganizationById(orgId);
        model.addAttribute("organization", o);
        List<Identity> identitiesForOrg = idServ.getIdentitiesForOrganization(orgId);
        model.addAttribute("identities", identitiesForOrg);
        Map<Integer , List<Organization>> orgsByIdentityIds = orgServ.getOrgsByIdentityIds(identitiesForOrg);
        model.addAttribute("orgsByIdentityIds", orgsByIdentityIds);
        List<Identity> allIdentities = idServ.getAllIdentities();
        model.addAttribute("allIdentities" , allIdentities);
        return "organizationdelete";
    }
    
    @PostMapping("organization/delete/{orgId}")
    public String processDeleteOrganizationById(Model model, @PathVariable int orgId){
        orgServ.deleteOrganizationById(orgId);
        return "redirect:/organizations";
    }
}
