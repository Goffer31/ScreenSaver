/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screensaverfxml.Controllers;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
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
    String keyContainer2;
    @FXML
    Label keyValidation2;
    
    @FXML
    Button targetFolderButton3;
    @FXML
    Label targetPathLabel3;
    @FXML
    Button keyChooseButton3;
    String keyContainer3;
    @FXML
    Label keyValidation3;
    
    @FXML
    Button targetFolderButton4;
    @FXML
    Label targetPathLabel4;
    @FXML
    Button keyChooseButton4;
    String keyContainer4;
    @FXML
    Label keyValidation4;
    
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
    private final FileChooser fileChooser = new FileChooser();
    
    List<File> selectedImgsList;
    
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

        /**
         * Radio button grouping
         */
        ToggleGroup radioButtonsTg = new ToggleGroup();
        copyRadioButton.setToggleGroup(radioButtonsTg);
        moveRadioButton.setToggleGroup(radioButtonsTg);
        copyRadioButton.setSelected(true);

        //---***---***---***---***---***---***---***---***---***---***---***---*---*
        
        /**
         * Choosing folders to save photos
         */
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

        //---***---***---***---***---***---***---***---***---***---***---***---*---*
        
        /**
         * Choosing key to save photos
         */
        keyChooseButton1.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                keyChooseButton1.setOnAction(ae -> {
                    whichIsLastClicked2 = 0;
                    System.out.println("Which is last clicked key 0");
                    keyChooser(null);
                });
            }
        });

        keyChooseButton2.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                keyChooseButton2.setOnAction(ae -> {
                    whichIsLastClicked2 = 1;
                    System.out.println("Which is last clicked key 1");
                    keyChooser(null);
                });
            }
        });

        keyChooseButton3.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                keyChooseButton3.setOnAction(ae -> {
                    whichIsLastClicked2 = 2;
                    System.out.println("Which is last clicked key 2");
                    keyChooser(null);
                });
            }
        });

        keyChooseButton4.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                keyChooseButton4.setOnAction(ae -> {
                    whichIsLastClicked2 = 3;
                    System.out.println("Which is last clicked key 3");
                    keyChooser(null);
                });
            }
        });
    }
    ////////////////////////////////////////////////////////////
    /**
     * TO CONTINUE 
     */
    @FXML
    public void sourceFolderChooser(MouseEvent mouseEvent) {
        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
            fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Img Files", "*.jpg", "*.jpeg", "*.png"));
            selectedImgsList = fileChooser.showOpenMultipleDialog(null);
        }
    }
    
    @FXML
    private void printSavePath(String path, Label label) {
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
    public void keyChooser(KeyEvent event) {
        switch (whichIsLastClicked2) {
            case 0:
                System.out.println("Inside keyChooser method");
                keyChooseButton1.setText("Click save button");
                
                if (event == null) {
                    return;
                }
                
                keyContainer1 = event.getText().toUpperCase().trim();
                if (keyContainer1.length() > 1 || keyContainer1.length() == 0) {
                    keyValidation1.setText("Too long or empty");
                } else {
                    keyValidation1.setText("Confirm");
                    keyChooseButton1.setText(keyContainer1);
                }
                whichIsLastClicked2 = -1;
                break;

            case 1:
                System.out.println("Inside keyChooser method");
                keyChooseButton2.setText("Click save button");
                
                if (event == null) {
                    return;
                }
                
                keyContainer2 = event.getText().toUpperCase().trim();
                if (keyContainer2.length() > 1 || keyContainer2.length() == 0) {
                    keyValidation2.setText("Too long or empty");
                } else {
                    keyValidation2.setText("Confirm");
                    keyChooseButton2.setText(keyContainer2);
                }
                whichIsLastClicked2 = -1;
                break;
                
            case 2:
                System.out.println("Inside keyChooser method");
                keyChooseButton3.setText("Click save button");

                if (event == null) {
                    return;
                }

                keyContainer3 = event.getText().toUpperCase().trim();
                if (keyContainer3.length() > 1 || keyContainer3.length() == 0) {
                    keyValidation3.setText("Too long or empty");
                } else {
                    keyValidation3.setText("Confirm");
                    keyChooseButton3.setText(keyContainer3);
                }
                whichIsLastClicked2 = -1;
                break;
                
            case 3:
                System.out.println("Inside keyChooser method");
                keyChooseButton4.setText("Click save button");

                if (event == null) {
                    return;
                }

                keyContainer4 = event.getText().toUpperCase().trim();
                if (keyContainer4.length() > 1 || keyContainer4.length() == 0) {
                    keyValidation4.setText("Too long or empty");
                } else {
                    keyValidation4.setText("Confirm");
                    keyChooseButton4.setText(keyContainer4);
                }
                whichIsLastClicked2 = -1;
                break;
        }
    }
    
    @FXML
    public void exit() {
        Stage stage = (Stage) returnButton.getScene().getWindow();
        stage.close();
    }
    
    
}
