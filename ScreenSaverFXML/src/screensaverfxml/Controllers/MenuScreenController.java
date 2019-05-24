/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screensaverfxml.Controllers;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
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
   @FXML
   ListView listView;
   @FXML
   Label countLabel;
   @FXML
   Label photoNameLabel;
   @FXML
   Label photoSaveStatusLabel;
      
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

    List<File> listOfFiles;
    
    @FXML
    public void imgDoubleClick(MouseEvent mouseEvent) {
        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
            if(mouseEvent.getClickCount() == 2) {
                fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Img Files", "*.jpg", "*.jpeg", "*.png"));
                List<File> selectedImgsList = fileChooser.showOpenMultipleDialog(null);
                if(selectedImgsList != null) {
                    for (int i = 0; i < selectedImgsList.size(); i++) {
                        listOfFiles.addAll(i, selectedImgsList);
//                        listView.getItems().add(selectedImgsList.get(i).getName());
                        listView.getItems().add(listOfFiles.get(i).getName());
                    }
                } else {
                    System.out.println("No File Selected");
                }
            }
        }
    }
    
    public void selectOneFile() {
        
    }
    
//    public void listSaver(List<File> inputList, List<File> outputList){
//        if(inputList != null) {
//            for(int i = 0; i < inputList.size(); i++) {
//                outputList.add(i, inputList.get(i));
//            }       
//        } else {
//            System.out.println("InputList is empty");
//        }
//    }
   
    

//                    for(File file : selectedImgsList) {
//                        openFile(file);    
        private void openFile(File file) {
        try {
            desktop.open(file);
        } catch (IOException ex) {
            Logger.getLogger(
                MenuScreenController.class.getName()).log(
                    Level.SEVERE, null, ex
                );
        }
    }
    
    
    

    
}
   
