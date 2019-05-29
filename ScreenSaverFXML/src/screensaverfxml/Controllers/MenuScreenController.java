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
    
    @FXML
    public void handleOnKeyPressed(ActionEvent event) {
        
    }


}
   
