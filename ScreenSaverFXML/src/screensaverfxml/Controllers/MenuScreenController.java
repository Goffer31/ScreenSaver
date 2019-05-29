/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screensaverfxml.Controllers;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
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
public class MenuScreenController {
 
   @FXML
   ImageView imgFieldView;
   @FXML
   ListView listView;
   @FXML
   Label countLabel;
   @FXML
   Label photoNameLabel;
   @FXML
   Label photoSaveStatusLabel;
      
   private MainScreenController mainScreenController;
   final FileChooser fileChooser = new FileChooser();
    
    void setMainController(MainScreenController mainScreenController) {
        this.mainScreenController = mainScreenController;
    }
    
    @FXML
    public void exit() {
        Platform.exit();
    }

    List<File> listOfFiles;
    List<File> selectedImgsList;
    File singleFile;
    Image temporaryImage;
    Image testImage;
    int numberOfPhoto = 0;
    int addKeyHandlerCounter = 0;
    
    
    @FXML
    public void scrollImage(KeyEvent key) throws MalformedURLException{
        int counter = 0;
        int listSize = selectedImgsList.size();

        if(key.getCode() == KeyCode.LEFT && key.getCode() == KeyCode.ALT) {
            counter++;
        }

        if(key.getCode() == KeyCode.RIGHT) {
            counter--;
        }
    }
    
    @FXML
    private void addKeyHandler(ImageView imageView) {
        Group root = new Group();
        File singleFile;
        Image image;
        
        root.setOnKeyPressed(new EventHandler<KeyEvent>() {
        public void handle(KeyEvent ke) {
            if (ke.getCode() == KeyCode.T) {
//                singleFile = selectedImgsList.get(addKeyHandlerCounter);
            }
        }
    });
    }
    
    @FXML
    public void imgDoubleClick(MouseEvent mouseEvent) throws MalformedURLException {
        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
            if(mouseEvent.getClickCount() == 2) {
                fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Img Files", "*.jpg", "*.jpeg", "*.png"));
                selectedImgsList = fileChooser.showOpenMultipleDialog(null);
                if(selectedImgsList != null) {
                        singleFile = selectedImgsList.get(numberOfPhoto);
                        Image image = new Image(singleFile.toURL().toString(),
                        900, 400,
                        true, true, true);
                        imgFieldView.setImage(image);                 
                    } else {
                    System.out.println("No File Selected");
                }
            }
        }
    }
    
    
    
   
    
    
    

    
}
   
