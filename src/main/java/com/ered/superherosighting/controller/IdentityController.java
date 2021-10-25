/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ered.superherosighting.controller;
import com.ered.superherosighting.entities.Organization;
import com.ered.superherosighting.entities.Power;
import com.ered.superherosighting.entities.Identity;
import com.ered.superherosighting.service.LocationServiceImpl;
import com.ered.superherosighting.service.OrganizationServiceImpl;
import com.ered.superherosighting.service.PowerServiceImpl;
import com.ered.superherosighting.service.SightingServiceImpl;
import com.ered.superherosighting.service.IdentityServiceImpl;
import com.ered.superherosighting.service.ImageServiceImpl;
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
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Eric
 */
@Controller
public class IdentityController {
    
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
    
    @Autowired
    ImageServiceImpl imgServ;
    
    @GetMapping("identities")
    public String serveIdentities(Model model){
        List<Identity> identities = idServ.getAllIdentities();
        model.addAttribute("identities", identities);
        Map<Integer, List<Organization>> orgsByIdentityIds = orgServ.getOrgsByIdentityIds(identities);
        model.addAttribute("orgsByIdentityIds", orgsByIdentityIds);
        return "identities";
    }
    
    @GetMapping("identity/new")
    public String serveNewIdentity(Model model){
        List<Power> allPowers = powServ.getAllPowers();
        model.addAttribute("allPowers", allPowers);
        //create an empty Identity called identity in the model
        //so that we can bind data to it
        model.addAttribute("identity", new Identity() );
        return "identitynew";
    }
    
    @PostMapping(path = "identity/new" , consumes = "multipart/form-data")
    public String processNewIdentity(Model model, MultipartFile file,
            @Valid Identity identity, BindingResult result){
        //check if we have any errors
        if (result.hasErrors()){
            //could call serveNewIdentity
            List<Power> allPowers = powServ.getAllPowers();
            model.addAttribute("allPowers", allPowers);
            model.addAttribute("identity", identity);
            return "identitynew";
        }
        
        //otherwise go ahead and create the new identity
        List<Integer> powerIds = identity.getPowerIds();
        List<Power> powersForId = powServ.getPowersByIds(powerIds);
        identity.setPowers(powersForId);
        identity = idServ.addIdentity(identity);
        
        //after identity is created, use it's identityId to save image
        imgServ.saveImageForIdentity(file, identity.getIdentityId() );
        
        
        //send us to the identity's details page
        return "redirect:/identity/" + identity.getIdentityId();
    }
    
    @GetMapping("identity/{identityId}")
    public String serveIdentityById(Model model, @PathVariable int identityId){
        Identity id = idServ.getIdentityById(identityId);
        model.addAttribute("identity", id);
        return "identitydetails";
    }
    
    @GetMapping("identity/edit/{identityId}")
    public String serveEditIdentityById(Model model, @PathVariable int identityId){   
        Identity id = idServ.getIdentityById(identityId);
        model.addAttribute("identity", id);
        
        //needs all powers so we can add powers
        List<Power> allPowers = powServ.getAllPowers();
        model.addAttribute("allPowers", allPowers);
        
        return "identityedit";
        
        
    }
    
    @PostMapping(path = "identity/edit/" , consumes = "multipart/form-data") //{identityId}
    public String processEditIdentityById(Model model, MultipartFile file ,
            @Valid Identity id, BindingResult result){
        
        if (result.hasErrors()){
            //needs all powers so we can add powers
            List<Power> allPowers = powServ.getAllPowers();
            model.addAttribute("allPowers", allPowers);
            model.addAttribute("identity", id);
            return "identityedit";
        }
        
        List<Integer> powerIds = id.getPowerIds();
        List<Power> powers = powServ.getPowersByIds(powerIds);
        id.setPowers(powers);
        id = idServ.updateIdentity(id);
        if ( file.getSize() > 1 ){
            imgServ.updateImageForIdentity(file, id.getIdentityId() );
        }    
        return "redirect:/identity/" + id.getIdentityId();
    }
    
    @GetMapping("identity/delete/{identityId}")
    public String serveDeleteIdentityById(Model model, @PathVariable int identityId){
        Identity id = idServ.getIdentityById(identityId);
        model.addAttribute("identity", id);
        return "identitydelete";
    }
    
    @PostMapping("identity/delete/{identityId}")
    public String processDeleteIdentityById(@PathVariable int identityId){
        idServ.deleteIdentityById(identityId);
        return "redirect:/identities";
    }  
    
                  
    
}
                                                                                                                                                                   