/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screensaverfxml.Controllers;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author 8300
 */
public class SettingsScreenController implements Initializable {
    
    @FXML
    AnchorPane settingsPane;

    @FXML
    Button sourceFolderButton;
    @FXML
    Label sourcePathLabel;
    
    @FXML
    Button targetFolderButton1;
    @FXML
    Label targetPathLabel1;
    @FXML
    Button keyChooseButton1;
    String keyContainer1;
    @FXML
    Label keyValidation1;
    
    @FXML
    Button targetFolderButton2;
    @FXML
    Label targetPathLabel2;
    @FXML
    Button keyChooseButton2;
    
    @FXML
    Button targetFolderButton3;
    @FXML
    Label targetPathLabel3;
    @FXML
    Button keyChooseButton3;
    
    @FXML
    Button targetFolderButton4;
    @FXML
    Label targetPathLabel4;
    @FXML
    Button keyChooseButton4;
    
    @FXML
    RadioButton copyRadioButton;
    @FXML
    RadioButton moveRadioButton;
    @FXML
    Button returnButton;
    
    File[] selectedDirectory = new File[4];
    
    int whichIsLastClicked = -1;
    int whichIsLastClicked2 = -1;
    
    private final DirectoryChooser directoryChooser = new DirectoryChooser();
    private final Scanner scanner = new Scanner(System.in);
    
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
        settingsPane.requestFocus();
        ToggleGroup radioButtonsTg = new ToggleGroup();
        copyRadioButton.setToggleGroup(radioButtonsTg);
        moveRadioButton.setToggleGroup(radioButtonsTg);
        copyRadioButton.setSelected(true);

        targetFolderButton1.setOnAction(e -> {
            whichIsLastClicked = 0;
            System.out.println("Which is last clicked 0");
            savePathChooser(null);
        });
        targetFolderButton2.setOnAction(e -> {
            whichIsLastClicked = 1;
            System.out.println("Which is last clicked 1");
            savePathChooser(null);
        });
        targetFolderButton3.setOnAction(e -> {
            whichIsLastClicked = 2;
            System.out.println("Which is last clicked 2");
            savePathChooser(null);
        });
        targetFolderButton4.setOnAction(e -> {
            whichIsLastClicked = 3;
            System.out.println("Which is last clicked 3");
            savePathChooser(null);
        });
        
        keyChooseButton1.setOnAction(ae -> {
            whichIsLastClicked2 = 0;
            System.out.println("Which is last clicked key 0");
            keyChooser(null);
        });
        
        
    }
    
    @FXML
    public void printSavePath(String path, Label label) {
        System.out.println("Path: " + path);
        System.out.println("Label: " + label);
        label.setText(path);
    } 
    
    @FXML
    public void savePathChooser(MouseEvent mouseEvent) {
        switch (whichIsLastClicked) {
            case 0:
                directoryChooser.setTitle("Select first save location");
                selectedDirectory[0] = directoryChooser.showDialog(null);
                System.out.println("First Path: " + selectedDirectory[0]);
                printSavePath(selectedDirectory[0].getAbsolutePath(), targetPathLabel1);
                break;
            case 1:
                directoryChooser.setTitle("Select second save location");
                selectedDirectory[1] = directoryChooser.showDialog(null);
                System.out.println("Second Path: " + selectedDirectory[1]);
                printSavePath(selectedDirectory[1].getAbsolutePath(), targetPathLabel2);
                break;
            case 2:
                directoryChooser.setTitle("Select third save location");
                selectedDirectory[2] = directoryChooser.showDialog(null);
                System.out.println("Third Path: " + selectedDirectory[2]);
                printSavePath(selectedDirectory[2].getAbsolutePath(), targetPathLabel3);
                break;
            case 3:
                directoryChooser.setTitle("Select fourth save location");
                selectedDirectory[3] = directoryChooser.showDialog(null);
                System.out.println("Fourth Path: " + selectedDirectory[3]);
                printSavePath(selectedDirectory[3].getAbsolutePath(), targetPathLabel4);
                break;
        }
    }
    
    @FXML
    public void keyChooser(MouseEvent mouseEvent) {
        switch(whichIsLastClicked2) {
            case 1:
                System.out.println("Inside keyChooser method");
                keyChooseButton1.setText("Click save button");
                keyContainer1 = scanner.nextLine();
                if(keyContainer1.length() > 1) {
                    keyValidation1.setText("Too long");
                } else {
                    keyValidation1.setText("Confirm");
                }
                keyChooseButton1.setText(keyContainer1);
            case 2:
            
            case 3:
            
            case 4:
        
        }
    }
    
    @FXML
    public void exit() {
        Stage stage = (Stage) returnButton.getScene().getWindow();
        stage.close();
    }
    
    
}
