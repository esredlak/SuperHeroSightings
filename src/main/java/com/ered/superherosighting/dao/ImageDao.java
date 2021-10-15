/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ered.superherosighting.dao;

import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Eric
 */
public interface ImageDao {
    
    public void saveImage(MultipartFile file , String fileName, String directory);
    
    public void updateImage(MultipartFile file , String fileName, String directory);
   
}
