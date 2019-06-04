/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screensaverfxml.Controllers;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;

/**
 * FXML Controller class
 *
 * @author 8300
 */
public class SettingsScreenController implements Initializable {

    @FXML
    Button sourceFolderButton;
    @FXML
    Label sourcePathLabel;
    
    @FXML
    Button targetFolderButton1;
    @FXML
    Label targetPathLabel1;
    @FXML
    TextField keyText1;
    
    @FXML
    Button targetFolderButton2;
    @FXML
    Label targetPathLabe2;
    @FXML
    TextField keyText2;
    
    @FXML
    Button targetFolderButton3;
    @FXML
    Label targetPathLabe3;
    @FXML
    TextField keyText3;
    
    @FXML
    Button targetFolderButton4;
    @FXML
    Label targetPathLabe4;
    @FXML
    TextField keyText4;
    
    @FXML
    RadioButton copyRadioButton;
    @FXML
    RadioButton moveRadioButton;
    
    File[] selectedDirectory;
    
    int whichIsLastClicked = -1;
    
    @FXML
    private void RadioButtonsGroup() {
        ToggleGroup radioButtonsTg = new ToggleGroup();
        copyRadioButton.setToggleGroup(radioButtonsTg);
        moveRadioButton.setToggleGroup(radioButtonsTg);
        
        radioButtonsTg.selectedToggleProperty().addListener((observable, oldValue, newValue) -> { if(radioButtonsTg.getSelectedToggle() != null) {
            copyRadioButton.setSelected(true);
        }
        });
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ToggleGroup radioButtonsTg = new ToggleGroup();
        copyRadioButton.setToggleGroup(radioButtonsTg);
        moveRadioButton.setToggleGroup(radioButtonsTg);
        copyRadioButton.setSelected(true);
        
//        radioButtonsTg.selectedToggleProperty().addListener((observable, oldValue, newValue) -> { if(radioButtonsTg.getSelectedToggle() != null)           
        }
    
    @FXML
    public void printSavePath(String path) {
        keyText1.setText(path);
    }
    
    @FXML
    public String savePathChooser(MouseEvent mouseEvent) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select save location");

        targetFolderButton1.setOnAction(e -> whichIsLastClicked = 1);
        targetFolderButton2.setOnAction(e -> whichIsLastClicked = 2);
        targetFolderButton3.setOnAction(e -> whichIsLastClicked = 3);
        targetFolderButton4.setOnAction(e -> whichIsLastClicked = 4);
        
        
        switch (whichIsLastClicked) {
            case 1:
                selectedDirectory[1] = directoryChooser.showDialog(null);
                printSavePath(selectedDirectory[1].getAbsolutePath());
                return selectedDirectory[1].getAbsolutePath();
            case 2:
                selectedDirectory[2] = directoryChooser.showDialog(null);
                printSavePath(selectedDirectory[2].getAbsolutePath());
                return selectedDirectory[2].getAbsolutePath();

            case 3:
                selectedDirectory[3] = directoryChooser.showDialog(null);
                printSavePath(selectedDirectory[3].getAbsolutePath());
                return selectedDirectory[3].getAbsolutePath();

            case 4:
                selectedDirectory[4] = directoryChooser.showDialog(null);
                printSavePath(selectedDirectory[4].getAbsolutePath());
                return selectedDirectory[4].getAbsolutePath();
        }
        return null;
    }
    
    
        
   
       
    
}
