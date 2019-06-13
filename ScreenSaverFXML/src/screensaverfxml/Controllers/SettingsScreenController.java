/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screensaverfxml.Controllers;

import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
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
    
    private MenuScreenController menuScreenController;

    public void setMenuScreenController(MenuScreenController menuScreenController) {
        this.menuScreenController = menuScreenController;
    }
    
    @FXML
    AnchorPane settingsPane;

    @FXML
    Button sourceFolderButton;
    @FXML
    Label sourcePathLabel;
    @FXML
    Label countOfSelectedFilesLabel;
    
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
    
    ArrayList<String> keyList = new ArrayList<>();
    ArrayList<String> pathList = new ArrayList<>();
    
    private final DirectoryChooser directoryChooser = new DirectoryChooser();
    private final FileChooser fileChooser = new FileChooser();
    
    List<File> selectedImgsList;

    public List<File> getSelectedImgsList() {
        return selectedImgsList;
    }

    public void setSelectedImgsList(List<File> selectedImgsList) {
        this.selectedImgsList = selectedImgsList;
    }
    
    /**
     * Method RadioButtonsGroup() is responsible for grouping the radioButtons together, after that set copyButton selected at the begining and logic setting flag responsible for choosing between copying and moveing
     */
    
     @FXML
    private void RadioButtonsGroup() {
        ToggleGroup radioButtonsTg = new ToggleGroup();
        copyRadioButton.setToggleGroup(radioButtonsTg);
        moveRadioButton.setToggleGroup(radioButtonsTg);
        copyRadioButton.setSelected(true);

        radioButtonsTg.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if (copyRadioButton.isSelected()) {
                    menuScreenController.setCopyOrMoveStatusFlag(1);
                }
                if (moveRadioButton.isSelected()) {
                    menuScreenController.setCopyOrMoveStatusFlag(2);
                }
            }
        });
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        settingsPane.requestFocus();

        /**
         * Radio button graphic settings and initialize method RadioButtonsGruup(); responsible for group buttons together and logic backend setting falg
         */
        InputStream copyStream = getClass().getResourceAsStream("/resourcePackage/copy.png");
        InputStream moveStream = getClass().getResourceAsStream("/resourcePackage/move.png");
        Image copyImg = new Image(copyStream);
        Image moveImg = new Image(moveStream);
        copyRadioButton.setGraphic(new ImageView(copyImg));
        moveRadioButton.setGraphic(new ImageView(moveImg));

        RadioButtonsGroup();

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
        
        pathList = new ArrayList<>();
        for(int i = 0; i < 4; i++) {
            pathList.add(null);
        }
    }

    /**
     * Load files and transfer data to MenuScreenController 
     */
    @FXML
    public void sourceFolderChooser(MouseEvent mouseEvent) throws MalformedURLException {
        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
            fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Img Files", "*.jpg", "*.jpeg", "*.png"));
            selectedImgsList = fileChooser.showOpenMultipleDialog(null);
            
            menuScreenController.loadImageOnScreen(selectedImgsList);
            
            /**
             * Printing label and count of selected images
             */
            
            String printLabel = selectedImgsList.get(0).toString();
            int indexOfCut = printLabel.lastIndexOf("\\");
            if (indexOfCut > 0) {
                printLabel = printLabel.substring(0, indexOfCut);
            }
                printSavePath(printLabel, sourcePathLabel);       
                          
            countOfSelectedFilesLabel.setText(String.valueOf(selectedImgsList.size()));
            countOfSelectedFilesLabel.setTextFill(Paint.valueOf("#1a7a17"));
        }
    }
    
    @FXML
    private void printSavePath(String path, Label label) {
        System.out.println("Path: " + path);
        System.out.println("Label: " + label);
        label.setText(path);
        label.setTextFill(Paint.valueOf("#4e0754"));
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
        
        for(int i = 0; i < selectedDirectory.length; i++) {
            if(selectedDirectory[i] != null) {
                pathList.set(i, selectedDirectory[i].toString());
            } else { 
                pathList.set(i, null);
            }
        }
        
        menuScreenController.pathsGenerator(pathList);
    }
    
    /**
     * keyChooser method is reponsible for get letter after click on button in settings
     * @param event 
     */
    @FXML
    public void keyChooser(KeyEvent event) {
        switch (whichIsLastClicked2) {
            case 0:
                System.out.println("Inside keyChooser method");
                keyChooseButton1.setText("Click save button");
                
                if (cancelationOfListening(event, keyContainer1, keyValidation1, keyChooseButton1)) break;
                
                if (event == null) {
                    return;
                }
                
                keyContainer1 = event.getText().toUpperCase().trim();
                if (keyContainer1.length() > 1 || keyContainer1.length() == 0) {
                    keyValidation1.setText("Too long or empty");
                } else {
                    keyValidation1.setText("Confirm");
                    keyValidation1.setTextFill(Paint.valueOf("#1a7a17"));
                    keyChooseButton1.setText(keyContainer1);
                }
                whichIsLastClicked2 = -1;
                break;

            case 1:
                System.out.println("Inside keyChooser method");
                keyChooseButton2.setText("Click save button");
                
                if (cancelationOfListening(event, keyContainer2, keyValidation2, keyChooseButton2)) break;
                
                if (event == null) {
                    return;
                }
                
                keyContainer2 = event.getText().toUpperCase().trim();
                if (keyContainer2.length() > 1 || keyContainer2.length() == 0) {
                    keyValidation2.setText("Too long or empty");
                } else {
                    keyValidation2.setText("Confirm");
                    keyValidation2.setTextFill(Paint.valueOf("#1a7a17"));
                    keyChooseButton2.setText(keyContainer2);
                }
                whichIsLastClicked2 = -1;
                break;
                
            case 2:
                System.out.println("Inside keyChooser method");
                keyChooseButton3.setText("Click save button");
                
                if (cancelationOfListening(event, keyContainer3, keyValidation3, keyChooseButton3)) break;

                if (event == null) {
                    return;
                }

                keyContainer3 = event.getText().toUpperCase().trim();
                if (keyContainer3.length() > 1 || keyContainer3.length() == 0) {
                    keyValidation3.setText("Too long or empty");
                } else {
                    keyValidation3.setText("Confirm");
                    keyValidation3.setTextFill(Paint.valueOf("#1a7a17"));
                    keyChooseButton3.setText(keyContainer3);
                }
                whichIsLastClicked2 = -1;
                break;
                
            case 3:
                System.out.println("Inside keyChooser method");
                keyChooseButton4.setText("Click save button");

                if (cancelationOfListening(event, keyContainer4, keyValidation4, keyChooseButton4)) break;

                if (event == null) {
                    return;
                }

                keyContainer4 = event.getText().toUpperCase().trim();
                if (keyContainer4.length() > 1 || keyContainer4.length() == 0) {
                    keyValidation4.setText("Too long or empty");
                } else {
                    keyValidation4.setText("Confirm");
                    keyValidation4.setTextFill(Paint.valueOf("#1a7a17"));
                    keyChooseButton4.setText(keyContainer4);
                }
                whichIsLastClicked2 = -1;
                break;
        }
        
        if(keyList.isEmpty()) {
            keyList.add(keyContainer1);
            keyList.add(keyContainer2);
            keyList.add(keyContainer3);
            keyList.add(keyContainer4);
        } else {
            keyList.set(0, keyContainer1);
            keyList.set(1, keyContainer2);
            keyList.set(2, keyContainer3);
            keyList.set(3, keyContainer4);
        }

        menuScreenController.stringToKeyCodeGenerator(keyList);
    }

    
    /**
     * calcelationOfListening is reponsible for cancel of listening in keyChooser method, before we choose key to connect with path, after click ESCAPE.
     * @param event
     * @param containerLabel
     * @param validationLabel
     * @param keyChooseButton
     * @return 
     */    
    private boolean cancelationOfListening(KeyEvent event, String containerLabel, Label validationLabel, Button keyChooseButton) {
        if (event.getCode().equals(KeyCode.ESCAPE)) {
            event.consume();
            containerLabel = null;
            validationLabel.setText(null);
            keyChooseButton.setText("Click to choose key");
            System.out.println("CancelationOfListening");
            whichIsLastClicked2 = -1;
            return true;
        }
        return false;
    }
    
    @FXML
    public void exit() {
        Stage stage = (Stage) returnButton.getScene().getWindow();
        stage.close();
        menuScreenController.imageViewRequestFocus();
    }

    
}