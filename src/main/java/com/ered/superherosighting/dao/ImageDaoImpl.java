/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ered.superherosighting.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Eric
 */

@Repository
public class ImageDaoImpl implements ImageDao{

    @Override
    public void saveImage(MultipartFile file, String fileName, String directory) {  
        FileOutputStream output = null;
        
        try{
            String filePath = directory + fileName;
            File newFile = new File(filePath);            
            
            //if it doesn't exist (it shouldn't...) create new file
            
            if (!newFile.exists()){
                newFile.getParentFile().mkdirs();
                newFile.createNewFile();
            }
            
            output = new FileOutputStream(newFile);
            
            byte[] fileBytes = file.getBytes();
            
            output.write(fileBytes);
            output.flush();
        } catch (IOException e){
            System.out.println("File write unsuccessful: " + e.getMessage());
        } 
        
        finally {
            try{
                if (output != null){
                output.close();
                }
            } catch (IOException e){
            System.out.println("Error closing stream: " + e.getMessage());
            }
        } 
             
    }

    @Override
    public void updateImage(MultipartFile file, String fileName, String directory) {
        FileOutputStream output = null;
        
        try{
            String filePath = directory + fileName;
            File newFile = new File(filePath);            
            
            //newFile should exist and must be overwritten
            newFile.delete();
            newFile.createNewFile();
                    
            output = new FileOutputStream(newFile);
            
            byte[] fileBytes = file.getBytes();
            
            output.write(fileBytes);
            output.flush();
        } catch (IOException e){
            System.out.println("File write unsuccessful: " + e.getMessage());
        } 
        
        finally {
            try{
                if (output != null){
                output.close();
                }
            } catch (IOException e){
            System.out.println("Error closing stream: " + e.getMessage());
            }
        } 
           
    }
    
}
