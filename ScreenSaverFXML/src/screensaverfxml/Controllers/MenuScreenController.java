/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screensaverfxml.Controllers;

import java.awt.Desktop;
import java.io.File;
import java.net.MalformedURLException;
import java.util.List;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
/**
 *
 * @author root
 */
public class MenuScreenController{
 
   @FXML
   ImageView imgFieldView;
   int photoSwipCounter = 0;
   Image image;
      
   private Desktop desktop = Desktop.getDesktop();
   private MainScreenController mainScreenController;
   final FileChooser fileChooser = new FileChooser();
    
    void setMainController(MainScreenController mainScreenController) {
        this.mainScreenController = mainScreenController;
    }
    
    @FXML
    public void exit() {
        Platform.exit();
    }

    List<File> selectedImgsList;
    File singleFile;
    
    @FXML
    public void imgDoubleClick(MouseEvent mouseEvent) throws MalformedURLException {
        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
            if(mouseEvent.getClickCount() == 2) {
                fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Img Files", "*.jpg", "*.jpeg", "*.png"));
                selectedImgsList = fileChooser.showOpenMultipleDialog(null);
                if(selectedImgsList != null) {
                        singleFile = selectedImgsList.get(0);
                        image = new Image(singleFile.toURL().toString(),
                        900, 400,
                        true, true, true);
                        imgFieldView.setImage(image);
                        System.out.println(singleFile.getName());
                } else {
                    System.out.println("No File Selected");
                }
            }
        }
        imgFieldView.setOnKeyPressed(e -> {
            System.out.println("Before if clicked");

            if (e.getCode() == KeyCode.N) {
                photoSwipCounter++;
                System.out.println("P clicked");
            } else if (e.getCode() == KeyCode.P) {
                photoSwipCounter--;
                System.out.println("N clicked");

            }
        });
    }
    
    @FXML
    public void handleOnKeyPressed(KeyEvent event) {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                //Background
                getNewImageHandler(event);
                imageRotation(event);
                
                
            }
        };
        
        new Thread(task).start();
    }
    
    @FXML
    public void getNewImageHandler(KeyEvent event) {
        System.out.println(singleFile.getName());
//        imgFieldView.setOnKeyPressed(e -> {
//            if (e.getCode() == KeyCode.N) {
//                photoSwipCounter++;
//                System.out.println("P clicked");
//            } else if (e.getCode() == KeyCode.P) {
//                photoSwipCounter--;
//            }
//        });
        
//        if (event.getCode().equals(KeyCode.N)) {
//            photoSwipCounter++;
//        }
//        if (event.getCode().equals(KeyCode.P)) {
//            photoSwipCounter--;
//        }
        
        if (photoSwipCounter < 0) {
            singleFile = selectedImgsList.get(selectedImgsList.size() - photoSwipCounter);
        } else {
            singleFile = selectedImgsList.get(photoSwipCounter);
        }
        
        image = new Image(singleFile.toURI().toString(),
                900, 400,
                true, true, true);
        imgFieldView.setImage(image);
        

    }
    
    @FXML
    public void imageRotation(KeyEvent event) {
        if(event.getCode().equals(KeyCode.L)) {
            imgFieldView.setRotate(90);
        }
        if(event.getCode().equals(KeyCode.R)) {
            imgFieldView.setRotate(-90);
        }
    }
    


}
   
//https://www.programcreek.com/java-api-examples/?class=javafx.scene.Scene&method=setOnKeyPressed
//https://www.youtube.com/watch?v=UotiVqAjhDY
//https://www.youtube.com/watch?v=MZAFix_-9UI <- best 
//https://www.youtube.com/watch?v=bUxwGl7W9-E
//https://www.youtube.com/watch?v=xwWARVJB5g0

