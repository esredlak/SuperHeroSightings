/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ered.superherosighting.controller;
import com.ered.superherosighting.entities.Location;
import com.ered.superherosighting.entities.Organization;
import com.ered.superherosighting.entities.Sighting;
import com.ered.superherosighting.service.LocationServiceImpl;
import com.ered.superherosighting.service.OrganizationServiceImpl;
import com.ered.superherosighting.service.PowerServiceImpl;
import com.ered.superherosighting.service.SightingServiceImpl;
import com.ered.superherosighting.service.IdentityServiceImpl;
import java.util.List;
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
public class LocationController {
    
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
    
    @GetMapping("locations")
    public String serveLocations(Model model){
        List<Location> locs = locServ.getAllLocations();
        model.addAttribute("locs" , locs);
        return "locations";
    }
    
    @GetMapping("location/new")
    public String serveNewLocation(Model model){   
        model.addAttribute("location", new Location());
        return "locationnew";
    }
    
    @PostMapping("location/new")
    public String processNewLocation(@Valid Location l, BindingResult result){
        if( result.hasErrors()){
            return "locationnew";
        }
        
        l = locServ.addLocation(l);
        return "redirect:/location/" + l.getLocId();
    }
    
    @GetMapping("location/{locId}")
    public String serveLocationById(Model model, @PathVariable int locId){
        Location l = locServ.getLocationById(locId);
        model.addAttribute("location" , l);
        List<Sighting> sights = sightServ.getSightingsForLocation(locId);
        model.addAttribute("sightings", sights);
        List<Organization> organizations = orgServ.getOrganizationsForLocation(locId);
        model.addAttribute("organizations", organizations);
        return "locationdetails";
    }
    
    @GetMapping("location/edit/{locId}")
    public String serveEditLocationById(Model model, @PathVariable int locId){
        Location l = locServ.getLocationById(locId);
        model.addAttribute("location" , l);
        List<Sighting> sights = sightServ.getSightingsForLocation(locId);
        model.addAttribute("sightings", sights);
        List<Organization> organizations = orgServ.getOrganizationsForLocation(locId);
        model.addAttribute("organizations", organizations);
        return "locationedit";
    }
    
    @PostMapping("location/edit/{locId}")
    public String processEditLocationById(@Valid Location l , BindingResult result){
        if ( result.hasErrors() ){
            return "locationedit";
        }
        locServ.updateLocation(l);
        return "redirect:/location/" + l.getLocId();
    }
    
    @GetMapping("location/delete/{locId}")
    public String serveDeleteLocationById(Model model, @PathVariable int locId){
        Location l = locServ.getLocationById(locId);
        model.addAttribute("location" , l);
        List<Sighting> sights = sightServ.getSightingsForLocation(locId);
        model.addAttribute("sightings", sights);
        List<Organization> organizations = orgServ.getOrganizationsForLocation(locId);
        model.addAttribute("organizations", organizations);
        return "locationdelete";
    }
    
    @PostMapping("location/delete/{locId}")
    public String processDeleteLocationById(@PathVariable int locId){
        locServ.deleteLocationById(locId);
        return "redirect:/locations";
    }
}
