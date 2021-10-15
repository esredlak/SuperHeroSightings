/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ered.superherosighting.controller;
import com.ered.superherosighting.entities.Location;
import com.ered.superherosighting.entities.Sighting;
import com.ered.superherosighting.entities.Identity;
import com.ered.superherosighting.entities.Organization;
import com.ered.superherosighting.service.LocationServiceImpl;
import com.ered.superherosighting.service.OrganizationServiceImpl;
import com.ered.superherosighting.service.PowerServiceImpl;
import com.ered.superherosighting.service.SightingServiceImpl;
import com.ered.superherosighting.service.IdentityServiceImpl;
import java.beans.PropertyEditorSupport;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author Eric
 */
@Controller
public class SightingController {
    
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
    
    @InitBinder(value="identity")
    protected void initBinder(final WebDataBinder binder){
        binder.registerCustomEditor(Identity.class, new IdentityPropertyEditor());
    }
    
    @GetMapping("sightings")
    public String serveSightings(Model model){
        List<Sighting> sightings = sightServ.getAllSightings();
        model.addAttribute("sightings", sightings);
        return "sightings";
    }
    
    @GetMapping("sighting/new")
    public String serveNewSighting(Model model){
        List<Location> locations = locServ.getAllLocations();
        List<Identity> allIdentities = idServ.getAllIdentities();
        model.addAttribute("locations", locations);
        model.addAttribute("allIdentities", allIdentities);
        model.addAttribute("sighting", new Sighting() );
        return "sightingnew";
    }
    
    @PostMapping("sighting/new")
    public String processNewSighting(Model model, @Valid Sighting s, 
            BindingResult result){
        
        if (result.hasErrors()){
            List<Location> locations = locServ.getAllLocations();
            List<Identity> allIdentities = idServ.getAllIdentities();
            model.addAttribute("locations", locations);
            model.addAttribute("allIdentities", allIdentities);
            model.addAttribute("sighting", s);
            return "sightingnew";
        }
        
        List<Integer> superIds = s.getIdentityIds();
        List<Identity> identities = idServ.getIdentitiesByIds(superIds);
        s.setIdentities(identities);
        s = sightServ.addSighting(s);
        //after sighting created, sends us to the sighting's details page
        return "redirect:/sighting/" + s.getSightingId();
    }
    
    @GetMapping("sighting/{sightId}")
    public String serveSightingById(Model model, @PathVariable int sightId){
        Sighting s = sightServ.getSightingById(sightId);
        model.addAttribute("sighting", s);
        
        List<Identity> sightingIdentities = idServ.getIdentitiesBySighting(s.getSightingId());
        model.addAttribute("identities", sightingIdentities);
        
            Map<Integer, List<Organization>> orgsByIdentityIds = orgServ.getOrgsByIdentityIds(sightingIdentities);
        model.addAttribute("orgsByIdentityIds", orgsByIdentityIds);
        
        return "sightingdetails";
    }
    
    @GetMapping("sighting/edit/{sightId}")
    public String serveEditSightingById(Model model, @PathVariable int sightId){
        Sighting sighting = sightServ.getSightingById(sightId);
        model.addAttribute("sighting", sighting);
        
        List<Location> locations = locServ.getAllLocations();
        model.addAttribute("locations", locations);
        
        List<Identity> allIdentities = idServ.getAllIdentities();
        model.addAttribute("allIdentities", allIdentities);
        
        List<Identity> sightingIdentities = idServ.getIdentitiesBySighting(sightId);
        model.addAttribute("identities", sightingIdentities);
        
        Map<Integer, List<Organization>> orgsByIdentityIds = orgServ.getOrgsByIdentityIds(sightingIdentities);
        model.addAttribute("orgsByIdentityIds", orgsByIdentityIds);
        
        return "sightingedit";
    }
    
    @PostMapping("sighting/edit/{sightId}")
    public String processEditSightingById(Model model, @Valid Sighting s, 
            BindingResult result){
        if (result.hasErrors()){
            model.addAttribute("sighting", s);
            List<Location> locations = locServ.getAllLocations();
            List<Identity> allIdentities = idServ.getAllIdentities();
            model.addAttribute("locations", locations);
            model.addAttribute("allIdentities", allIdentities);
            
            List<Identity> sightingIdentities = idServ.getIdentitiesBySighting(s.getSightingId());
        model.addAttribute("identities", sightingIdentities);
        
            Map<Integer, List<Organization>> orgsByIdentityIds = orgServ.getOrgsByIdentityIds(sightingIdentities);
        model.addAttribute("orgsByIdentityIds", orgsByIdentityIds);
        
            return "sightingedit";
        }
        s = sightServ.updateSighting(s);
        return "redirect:/sighting/" + s.getSightingId();
    }
    
    @GetMapping("sighting/delete/{sightId}")
    public String serveDeleteSightingById(Model model, @PathVariable int sightId){
        Sighting s = sightServ.getSightingById(sightId);
        model.addAttribute("sighting" , s);
        
        List<Identity> sightingIdentities = idServ.getIdentitiesBySighting(s.getSightingId());
        model.addAttribute("identities", sightingIdentities);
        
            Map<Integer, List<Organization>> orgsByIdentityIds = orgServ.getOrgsByIdentityIds(sightingIdentities);
        model.addAttribute("orgsByIdentityIds", orgsByIdentityIds);
        
        return "sightingdelete";
    }
    
    @PostMapping("sighting/delete/{sightId}")
    public String processDeleteSightingById(Model model, @PathVariable int sightId){
        sightServ.deleteSightingById(sightId);
        return "redirect:/sightings";
    }
    
    private class IdentityPropertyEditor extends PropertyEditorSupport {
        
        @Override
        public void setAsText(String identityId){
            int identityIdInt = Integer.getInteger(identityId);
            final Identity id = idServ.getIdentityById( identityIdInt );
            setValue(id);
        }
        
        @Override
        public String getAsText(){
            String text = String.valueOf(((Identity)getValue()).getIdentityId());
            return text;
        }
    }
}
