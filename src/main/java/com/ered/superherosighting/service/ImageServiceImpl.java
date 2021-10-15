/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ered.superherosighting.service;

import com.ered.superherosighting.dao.IdentityDaoDB;
import com.ered.superherosighting.dao.ImageDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Eric
 */

@Service
public class ImageServiceImpl implements ImageService{

    private final String identityDirectory = "./src/main/resources/static/img/identities/";
    
    @Autowired
    ImageDaoImpl imgDao;
    
    @Autowired
    IdentityDaoDB idDao;
    
    @Override
    public void saveImage(MultipartFile file, String fileName, String directory) {
        imgDao.saveImage(file, fileName, directory);
    }

    @Override
    public void updateImage(MultipartFile file, String fileName, String directory) {
        imgDao.updateImage(file, fileName, directory);
    }
    
    @Override
    public void saveImageForIdentity(MultipartFile file , int identityId){        
        String fileName = buildFileName(file , identityId);
        idDao.setIdentityFileName(identityId , fileName);
        saveImage(file, fileName, identityDirectory);
    }

    @Override
    public void updateImageForIdentity(MultipartFile file, int identityId) {
        String fileName = buildFileName(file , identityId);
        idDao.setIdentityFileName(identityId , fileName);
        updateImage(file, fileName, identityDirectory);
    }

    private String buildFileName(MultipartFile file, int identityId) {
        String contentType = file.getContentType();
        String[] contentTypeArray = contentType.split("/");
        String fileType = contentTypeArray[contentTypeArray.length - 1];
        String fileName = String.valueOf("id" + identityId + "." + fileType);
        return fileName;
    }
    
    
}
