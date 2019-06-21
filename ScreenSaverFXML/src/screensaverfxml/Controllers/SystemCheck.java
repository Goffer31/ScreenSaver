/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screensaverfxml.Controllers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author Admin
 */
public class SystemCheck {

    protected static OSType detectedOS;
    static boolean fileExists;
    final static String FILENAME = "gbbeta.txt";
    static String PATH;
    static final long TRIAL_PERIOD_IN_MS = 30000;


    public enum OSType {
        Windows, MacOS, Linux, Other
    };
    
    public static OSType getOperatingSystemType() { 
        if(detectedOS == null) {
            String OS = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
            if((OS.indexOf("mac") >= 0) || (OS.indexOf("darwin") >= 0)) {
                detectedOS = OSType.MacOS;
            } else if(OS.indexOf("win") >= 0) {
                detectedOS = OSType.Windows;
            } else if(OS.indexOf("nux") >= 0) {
                detectedOS = OSType.Linux;
            } else { 
                detectedOS = OSType.Other;
            }
        }
        return detectedOS;
    }
    
    public static boolean trialCheck(OSType detectedOS) throws FileNotFoundException, IOException { 
        if(detectedOS == null) {
            getOperatingSystemType();
        }
        
        if(isTrialFileCreated(detectedOS)){
            System.out.println("Trial file gbbeta exists already");
            File verySecretFile = new File (PATH + "/xoBotohP/" + FILENAME);

            BufferedReader br = new BufferedReader(new FileReader(verySecretFile));
            String timeStamp;
            while((timeStamp = br.readLine()) != null){
            long timeStampLong = Long.parseLong(timeStamp);
            System.out.println(timeStampLong);
            if(new Date().getTime() - timeStampLong > TRIAL_PERIOD_IN_MS){
                return false;
            }else{
                return true;
            }
            }
            
        }else{
            System.out.println("Trial file needs to be created.");
            System.out.println("Env Variable of the OS is "+ PATH);
            
            File verySecretFile = new File (PATH + "/xoBotohP/" + FILENAME);
            try{
                File directory = new File(PATH + "/xoBotohP");
                directory.mkdir();
                FileWriter fw = new FileWriter(PATH + "/xoBotohP/" + FILENAME, true);
                BufferedWriter bw = new BufferedWriter(fw);
                
                long date = new Date().getTime();
                bw.write(""+date);
                bw.close();
                        
                System.out.println("gbbeta.txt file " + verySecretFile.getPath() + " created. File contains " + date);
            } catch (IOException e) {
                System.out.println("Error while creating file: " + e);
            }
        }
        return true;
    }

    public static boolean isTrialFileCreated(OSType detectedOS){
        if(detectedOS == OSType.Windows){
            PATH = System.getenv("APPDATA");
            File tmpDir = new File(PATH + "/xoBotohP/" + FILENAME);
            fileExists = tmpDir.exists();
            System.out.println(fileExists);
            return fileExists;
        }else if(detectedOS == OSType.Linux){
            PATH = "~/";
            File tmpDir = new File(PATH + "/xoBotohP/" + FILENAME);
            fileExists = tmpDir.exists();
            System.out.println(fileExists);
            return fileExists;
        }else if(detectedOS == OSType.MacOS){
            PATH = "~/Library";
            File tmpDir = new File(PATH + "/xoBotohP/" + FILENAME);
            fileExists = tmpDir.exists();
            System.out.println(fileExists);
            return fileExists;
        } else {
            PATH = "~/";
            File tmpDir = new File(PATH + "/xoBotohP/" + FILENAME);
            fileExists = tmpDir.exists();
            System.out.println(fileExists);
            return fileExists;
        }
    }
}
