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
import javafx.fxml.FXML;
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
    List<File> selectedImgsList;
    File singleFile;
    Image temporaryImage;
    Image testImage;
    
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
        
        if(counter < 0) {
            counter = listSize - counter;
        }
        
        
        singleFile = selectedImgsList.get(counter);
        Image image = new Image(singleFile.toURL().toString(),
            900, 400,
            true, true, true);
        imgFieldView.setImage(image);
    }
    
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
                        showLabelCount(selectedImgsList, 3);
                    for (int i = 0; i < selectedImgsList.size(); i++) {
                        
                        
//                        listOfFiles.addAll(i, selectedImgsList);
//                        listView.getItems().add(selectedImgsList.get(i).getName());
                        listView.getItems().add(selectedImgsList.get(i).getName());
                    }
//                    loadImageView(1);
                } else {
                    System.out.println("No File Selected");
                }
            }
        }
    }
    
    public void selectItemFromListView(MouseEvent event) {
        listView.getSelectionModel().getSelectedItem();
    }
    
    
    public void loadImageView(int index) {
        
        if(index > selectedImgsList.size()) {
            System.out.println("Out Of List Size");
            return;
        }
        
        singleFile = selectedImgsList.get(index);
//        singleFile.equals(selectedImgsList.get(index));
        temporaryImage = new Image(singleFile.toURI().toString());
        imgFieldView.setImage(temporaryImage);
    }
    
    @FXML
    public void showLabelCount(List<File> filesList, int index) {
//        int currentNumber = filesList.indexOf();
        int generalCount = filesList.size();
        countLabel.setText(index + " / " + generalCount);
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
   
