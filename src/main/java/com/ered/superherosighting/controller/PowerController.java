/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ered.superherosighting.controller;
import com.ered.superherosighting.entities.Power;
import com.ered.superherosighting.entities.Identity;
import com.ered.superherosighting.entities.Organization;
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
public class PowerController {
    
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
    
    @GetMapping("powers")
    public String servePowers(Model model){
        List<Power> powers = powServ.getAllPowers();
        model.addAttribute("powers", powers);
        return "powers";
    }
    
    @GetMapping("power/new")
    public String serveNewPower(Model model){
        model.addAttribute("power", new Power());
        return "powernew";
    }
    
    @PostMapping("power/new")
    public String processNewPower(@Valid Power p, BindingResult result){
        if (result.hasErrors()){
            return "powernew";
        }
        p = powServ.addPower(p);
        //after power created, sends us to the power's details page
        return "redirect:/power/" + p.getPowerId();
    }
    
    @GetMapping("power/{powerId}")
    public String servePowerById(Model model, @PathVariable int powerId){
        Power p = powServ.getPowerById(powerId);
        model.addAttribute("power", p);
        
        List<Identity> identities = idServ.getIdentitiesByPower(powerId);
        model.addAttribute("identities", identities);
        
        Map<Integer, List<Organization>> orgsByIdentityIds = orgServ.getOrgsByIdentityIds(identities);
        model.addAttribute("orgsByIdentityIds", orgsByIdentityIds);
        
        return "powerdetails";
    }
    
    @GetMapping("power/edit/{powerId}")
    public String serveEditPowerById(Model model, @PathVariable int powerId){
        Power p = powServ.getPowerById(powerId);
        model.addAttribute("power", p);
        
        List<Identity> identities = idServ.getIdentitiesByPower(powerId);
        model.addAttribute("identities", identities);
        
        Map<Integer, List<Organization>> orgsByIdentityIds = orgServ.getOrgsByIdentityIds(identities);
        model.addAttribute("orgsByIdentityIds", orgsByIdentityIds);
        
        return "poweredit";
    }
    
    @PostMapping("power/edit/{powerId}")
    public String processEditPowerById(Model model, @Valid Power p,
            BindingResult result){
        if (result.hasErrors()){
            List<Identity> identities = idServ.getIdentitiesByPower(p.getPowerId());
            model.addAttribute("identities", identities);

            Map<Integer, List<Organization>> orgsByIdentityIds = orgServ.getOrgsByIdentityIds(identities);
            model.addAttribute("orgsByIdentityIds", orgsByIdentityIds);
        
            return "poweredit";
        }
        p = powServ.updatePower(p);
        return "redirect:/power/" + p.getPowerId();
    }
    
    @GetMapping("power/delete/{powerId}")
    public String serveDeletePowerById(Model model, @PathVariable int powerId){
        Power p = powServ.getPowerById(powerId);
        model.addAttribute("power", p);
        
        List<Identity> identities = idServ.getIdentitiesByPower(powerId);
        model.addAttribute("identities", identities);
        
        Map<Integer, List<Organization>> orgsByIdentityIds = orgServ.getOrgsByIdentityIds(identities);
        model.addAttribute("orgsByIdentityIds", orgsByIdentityIds);
        
        return "powerdelete";
    }
    
    @PostMapping("power/delete/{powerId}")
    public String processDeletePowerById(Model model, @PathVariable int powerId){
        powServ.deletePowerById(powerId);
        return "redirect:/powers";
    }
}
