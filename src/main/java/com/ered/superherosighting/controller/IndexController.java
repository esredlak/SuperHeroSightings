/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ered.superherosighting.controller;

import com.ered.superherosighting.entities.Sighting;
import com.ered.superherosighting.service.SightingServiceImpl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author Eric
 */
@Controller
public class IndexController {
    
    @Autowired
    SightingServiceImpl sightServ;

    
    @GetMapping("/")
    public String displaylastTenSightings(Model model){
        List<Sighting> sightings = sightServ.getLastTenSightings();
        model.addAttribute("sightings" , sightings);
        return "index";
    }

    
}
