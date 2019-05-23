/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screensaverfxml.Controllers;

import java.awt.Desktop;
import java.io.File;
import java.util.List;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
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
    
    
    @FXML
    public void imgDoubleClick(MouseEvent mouseEvent) {
        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
            if(mouseEvent.getClickCount() == 2) {
                List<File> listofFiles = fileChooser.showOpenDialog(stage);
                
            }
        }
    }
    

    
}
   
