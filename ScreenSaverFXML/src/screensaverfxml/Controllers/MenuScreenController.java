/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screensaverfxml.Controllers;

import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.MalformedURLException;
import java.util.List;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.transform.Rotate;
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
        imgFieldView.requestFocus();
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
                        for(int i = 0; i < selectedImgsList.size(); i++) {
                            System.out.println(selectedImgsList.get(i).getName());                            
                        }
                } else {
                    System.out.println("No File Selected");
                }
            }
        }
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
        
        /*
        from imageRotation()
        */
        
        Rectangle2D viewPoint = imgFieldView.getViewport();
        Rotate rotation = new Rotate();
        
        if (event.getCode().equals(KeyCode.L)) {
            
            
            
            
//            rotation.setPivotX(imgFieldView.getRotate());
            
            imgFieldView.getLocalToSceneTransform();
//            imgFieldView.setFitHeight(400);
//            imgFieldView.setFitWidth(900);
            imgFieldView.setPreserveRatio(true);
//            rotation.setPivotX(imgFieldView.getFitWidth() /2);
//            rotation.setPivotY(imgFieldView.getFitHeight() /2);    
//            rotation.setPivotY(imgFieldView.getImage().getRequestedHeight() /2);
//            rotation.setPivotX(imgFieldView.getImage().getRequestedWidth() /2);
            
//            rotation.setPivotX(viewPoint.getMaxX() /2);
//            rotation.setPivotY(viewPoint.getMaxY()/2);
//            rotation.setAngle(rotation.getAngle() + 90);

//            imgFieldView.getTransforms().add(new Rotate(90, imgFieldView.getFitHeight(), imgFieldView.getFitWidth()));
//            imgFieldView.getTransforms().add(new Rotate(90, imgFieldView.getRotationAxis()));
//            imgFieldView.getTransforms().add(new Rotate(90,
//                    imgFieldView.getBoundsInParent().getMinX()
//                    + (imgFieldView.getBoundsInLocal().getWidth() / 2),
//                    imgFieldView.getBoundsInParent().getMinY()
//                    + (imgFieldView.getBoundsInLocal().getHeight() / 2)));

//            imgFieldView.getTransforms().add(new Rotate(90, imgFieldView.getFitWidth()/2, imgFieldView.getFitHeight()/2));
            imgFieldView.getTransforms().add(new Rotate(90, image.getRequestedWidth(), image.getRequestedHeight()));
            
            
            
//            imgFieldView.setRotate(90);
//            rotation.setPivotX(imgFieldView.getRotate());
            imgFieldView.getTransforms().add(rotation);
//            imgFieldView.getTransforms().add(new Rotate(90, imgFieldView.getFitHeight(), imgFieldView.getFitWidth()));
//            viewPoint = imgFieldView.getViewport();
            System.out.println("L clicked");
        }
        
        if (event.getCode().equals(KeyCode.R)) {
            imgFieldView.setRotate(-90);
            imgFieldView.getViewport();
            System.out.println("R clicked");
        }
    }
    
    @FXML
    public void imageRotation(KeyEvent event) {
        if (event.getCode().equals(KeyCode.L)) {
            imgFieldView.setRotate(90);
            System.out.println("L clicked");

        }
        if (event.getCode().equals(KeyCode.R)) {
            imgFieldView.setRotate(-90);
            System.out.println("R clicked");
        }
    }
    
    @FXML 
    public void savePhoto(File singleFile) {
        File fileDirectoryOne = fileChooser.getInitialDirectory();
        
        File fileDirectory = new File(System.getProperty(fileChooser.getInitialDirectory().toURI().toString()));
        
        
        
    }


}
   
//https://www.programcreek.com/java-api-examples/?class=javafx.scene.Scene&method=setOnKeyPressed
//https://www.youtube.com/watch?v=UotiVqAjhDY
//https://www.youtube.com/watch?v=MZAFix_-9UI <- best 
//https://www.youtube.com/watch?v=bUxwGl7W9-E
//https://www.youtube.com/watch?v=xwWARVJB5g0

