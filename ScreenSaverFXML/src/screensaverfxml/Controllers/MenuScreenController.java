/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screensaverfxml.Controllers;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 *
 * @author root
 */
public class MenuScreenController{
 
    @FXML
    private StackPane stackImgPane;
    @FXML
    ImageView imgFieldView;
    int photoSwipCounter = 0;
    Image image;
    double angleRotation;
    

   private MainScreenController mainScreenController;
   
   final FileChooser fileChooser = new FileChooser();   
   
   List<File> selectedImgsList;
    File singleFile;
    File selectedDirectory = null;
   
    void setMainController(MainScreenController mainScreenController) {
        this.mainScreenController = mainScreenController;
    }
    
    @FXML
    public void exit() {
        Platform.exit();
    }
    
    //Method to make image on the center of the screen
    public void centerImage(ImageView imgView) {
        Image img = imgView.getImage();
        if (img != null) {
            double imageWidth;
            double imageHeight;
            double ratioX = imgView.getFitHeight() / img.getWidth();
            double ratioY = imgView.getFitWidth() / img.getHeight();
            double reducCoeff;
            
            if (ratioX >= ratioY) {
                reducCoeff = ratioY;
            } else {
                reducCoeff = ratioX;
            }
            imageWidth = img.getWidth() * reducCoeff;
            imageHeight = img.getHeight() * reducCoeff;
            imgView.setX((imgView.getFitWidth() - imageWidth) / 2);
            imgView.setY((imgView.getFitHeight() - imageHeight) / 2);
        }
    }
    
    //Select photos from driver by double click on imageView field
    @FXML
    public void imgDoubleClick(MouseEvent mouseEvent) throws MalformedURLException {

        // image view setting position
//        centerImage(imgFieldView);
        // end of image view setting position
        
        /**
         * requestFocus() gives us opportunity to operate on imgFieldView
         */
        imgFieldView.requestFocus();
        
        /**
         * Code responsible for load photos from a driver
        */
        if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
            if (mouseEvent.getClickCount() == 2) {
                fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Img Files", "*.jpg", "*.jpeg", "*.png"));
                selectedImgsList = fileChooser.showOpenMultipleDialog(null);
                if (selectedImgsList != null) {
                    singleFile = selectedImgsList.get(0);
                    image = new Image(singleFile.toURL().toString(),
                            900, 400,
                            true, true, true);
                    imgFieldView.setImage(image);
                    System.out.println(singleFile.getName());
                    for (int i = 0; i < selectedImgsList.size(); i++) {
                        System.out.println(selectedImgsList.get(i).getName());
                    }
                } else {
                    System.out.println("No File Selected");
                }
            }
        }
    }
    
    
    @FXML
    public void getNewImageHandler(KeyEvent event) {
        /**
         * Code responsible for switching photos
         */
        centerImage(imgFieldView);
        imgFieldView.requestFocus();
        System.out.println(singleFile.getName());
        
        if (event.getCode().equals(KeyCode.N)) {
            photoSwipCounter++;
            System.out.println("N clicked");
        }

        if (event.getCode().equals(KeyCode.P)) {
            photoSwipCounter--;
            System.out.println("P clicked");
        }
        
        photoSweep(photoSwipCounter);
        
        /**
         * Code responsible for rotate the image
         */
        if (event.getCode().equals(KeyCode.L)) {

            angleRotation += 90;
            imgFieldView.setRotate(angleRotation);
            imgFieldView.getViewport();
        }

        if (event.getCode().equals(KeyCode.R)) {
            angleRotation -= 90;
            imgFieldView.setRotate(angleRotation);
            imgFieldView.getViewport();
        }
        //---***---***---***---***---***---***---***---***---***---***---***---*
        
        /**
         * Code responsible for saving photos into four different locations 
         * wired to key 'Z', key 'V' is responsible for choosing folder for save
         */
        
        if(event.getCode().equals(KeyCode.V)) {
            patchChooser();
        }
        
        if(event.getCode().equals(KeyCode.Z)) {
            savePhotoAndDelete(singleFile, patchChooser());
        }
        
        if(event.getCode().equals(KeyCode.X)) {
            try {
                savePhotoWithCopy(singleFile, patchChooser());
            } catch (IOException ex) {
                Logger.getLogger(MenuScreenController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
    }

    private void photoSweep(int photoSwipCounter) {
        if (photoSwipCounter < 0) {
            photoSwipCounter = selectedImgsList.size() - 1;
        } else if (photoSwipCounter >= selectedImgsList.size()) {
            photoSwipCounter = 0;
        }
        singleFile = selectedImgsList.get(photoSwipCounter);
        
        image = new Image(singleFile.toURI().toString(),
                900, 400,
                true, true, true);
        imgFieldView.setImage(image);
    }
    
    @FXML
    public String patchChooser() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select save location");
        if (selectedDirectory == null) {
            selectedDirectory = directoryChooser.showDialog(null);
        }
        System.out.println("Directory choosed: " + selectedDirectory.getAbsolutePath());
        return selectedDirectory.getAbsolutePath();
    }
    
    @FXML
    private void savePhotoWithCopy(File singleFile, String directory) throws IOException {
        Path sourceDirectory = Paths.get(singleFile.getAbsolutePath());
        String targetDirectoryString = directory + "\\" + singleFile.getName();
        Path targetDirectory = Paths.get(targetDirectoryString);
        Files.copy(sourceDirectory, targetDirectory);
        selectedImgsList.get(photoSwipCounter).delete();
        photoSwipCounter++;
        photoSweep(photoSwipCounter);
    }
    
    @FXML
    public void savePhotoAndDelete(File singleFile, String directory) {
        String fileNameAndPathToSave = directory + "\\" + singleFile.getName();
        System.out.println("fileNameToSave2: " + fileNameAndPathToSave);
        singleFile.renameTo(new File(fileNameAndPathToSave));
        selectedImgsList.get(photoSwipCounter).delete();
        photoSwipCounter++;
        photoSweep(photoSwipCounter);
    }


}
//Key Listener
//https://www.programcreek.com/java-api-examples/?class=javafx.scene.Scene&method=setOnKeyPressed
//https://www.youtube.com/watch?v=UotiVqAjhDY
//https://www.youtube.com/watch?v=MZAFix_-9UI <- best 
//https://www.youtube.com/watch?v=bUxwGl7W9-E
//https://www.youtube.com/watch?v=xwWARVJB5g0


//Save files
//https://stackoverflow.com/questions/4871051/getting-the-current-working-directory-in-java <---- very good
//https://www.youtube.com/watch?v=F4b55du_Nic <------- very good 
//https://stackoverflow.com/questions/6366743/saving-files-to-a-specific-directory-in-java
//https://stackoverflow.com/questions/26693550/how-can-i-save-a-file-in-the-current-directory
//https://stackoverflow.com/questions/16239130/java-user-dir-property-what-exactly-does-it-mean
//https://www.genuinecoder.com/save-files-javafx-filechooser/
//https://www.tutorialspoint.com/java/io/java_io_file.htm
//https://stackoverflow.com/questions/10083447/selecting-folder-destination-in-java
//https://stackoverflow.com/questions/20958668/jfilechooser-getcurrentdirectory-to-string
//https://docs.oracle.com/javase/6/docs/api/java/io/File.html
//https://www.rgagnon.com/javadetails/java-0370.html
//http://www.java2s.com/Code/Java/Swing-JFC/SelectadirectorywithaJFileChooser.htm
//https://stackoverflow.com/questions/14967449/read-from-a-file-that-is-in-the-same-folder-as-the-jar-file

//Rotation
//https://stackoverflow.com/questions/33613664/javafx-drawimage-rotated/54142453
//https://stackoverflow.com/questions/33613664/javafx-drawimage-rotated

