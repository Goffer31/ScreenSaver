/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screensaverfxml.Controllers;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 *
 * @author root
 */
public class MenuScreenController implements Initializable{
    
    /*
            TO DO:
    1. Połączyć imageRotation i getNewImage tak żeby po kliknięciu przycisków działały (initializer)
    2. Zapis plików do folderu
    3. Wybieranie pod jakimi przyskami znajdują się w/w opcje, w nowym opcje ala settings
    4. Rozszerzenie tego do 4 folderów
    5. Zrobić transparentne przyciski wyświetlające się po wciśnięciu klawysza np. crtl lub alt
    */
    
    private MainScreenController mainScreenController;
    
   @FXML
   Pane menuPane;
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
   
    List<File> selectedImgsList;
    File singleFile;
    Image image;
    int numberOfPhoto = 0;
    int addKeyHandlerCounter = 0;
    final FileChooser fileChooser = new FileChooser();
    
    void setMainController(MainScreenController mainScreenController) {
        this.mainScreenController = mainScreenController;
    }

    
    
    @FXML
    public void imgDoubleClick(MouseEvent mouseEvent) throws MalformedURLException {
        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
            if(mouseEvent.getClickCount() == 2) {
                fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Img Files", "*.jpg", "*.jpeg", "*.png"),
                        new ExtensionFilter(".jpg/.jpeg", "*.jpeg", "*.jpg"),
                        new ExtensionFilter(".png", "*.png"));
                selectedImgsList = fileChooser.showOpenMultipleDialog(null);
                if(selectedImgsList != null) {
                        singleFile = selectedImgsList.get(numberOfPhoto);
                        image = new Image(singleFile.toURL().toString(),
                        900, 400,
                        true, true, true);
                        imgFieldView.setImage(image);    
                    } else {
                    System.out.println("No File Selected");
                    
                }
            }
        }
    }
    
    public void imageRotation(KeyEvent event) {
        if(event.getCode().equals(KeyCode.R)){
            imgFieldView.setRotate(90);
        } else if(event.getCode().equals(KeyCode.L)){
            imgFieldView.setRotate(-90);
        }
    }
    
    public void getNewImage(KeyEvent event) {
        if(event.getCode().equals(KeyCode.I)) {
            addKeyHandlerCounter++;
            singleFile = selectedImgsList.get(addKeyHandlerCounter);
            image = new Image(singleFile.toURI().toString(),
            900, 400,
            true, true, true);
            imgFieldView.setImage(image);
        }
        if(event.getCode().equals(KeyCode.D)) {
            addKeyHandlerCounter--;
            singleFile = selectedImgsList.get(addKeyHandlerCounter);
            image = new Image(singleFile.toURI().toString(),
            900, 400,
            true, true, true);
            imgFieldView.setImage(image);
        }
    }
 

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        menuPane.sceneProperty().addListener(new ChangeListener<Scene>() {
            @Override
            public void changed(ObservableValue<? extends Scene> observable, Scene oldValue, Scene newValue) {
                if(newValue != null) {
                    menuPane.requestFocus();
                }
            }
        });
        imgFieldView.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                imageRotation(event);
                getNewImage(event);
            }
        });
    }
    

    
//    private BufferedImage originalBI;
//    private BufferedImage newIB;
//   private int pixels[][];
//   private final JFileChooser openFileChooser;
//   private final JFileChooser saveFileChooser;
//    public MenuScreenController(JFileChooser openFileChooser, JFileChooser saveFileChooser) {
//        this.openFileChooser = openFileChooser;
//        this.saveFileChooser = saveFileChooser;
//    }
        
//    public void saveFileButtonActionPerformed(ActionEvent actionEvent){
//        imageToArray();
//        makeFilteredImage();
//        saveImage();
//    }
        
//        public void imageToArray() {
//        int width = originalBI.getWidth();
//        int heigh = originalBI.getHeight();
//        
//        newIB = new BufferedImage(width, heigh, BufferedImage.TYPE_INT_ARGB);
//        
//        pixels = new int[width][heigh];
//        
//        for(int i = 0; i < width; i++) {
//            for(int j = 0; j < heigh; j++) {
//                pixels[i][j] = originalBI.getRGB(i, j);
//            }
//        }
//    }
    
    
    
//        @FXML
//    private void addKeyHandler(ImageView imageView) {
//        Group root = new Group();
//        File singleFile;
//        Image image;
//        
//        root.setOnKeyPressed(new EventHandler<KeyEvent>() {
//        public void handle(KeyEvent ke) {
//            if (ke.getCode() == KeyCode.T) {
////                singleFile = selectedImgsList.get(addKeyHandlerCounter);
//            }
//        }
//    });
//    }
    
// Zrobione na FileChooserze    
//        @FXML
//    public void imgDoubleClick(MouseEvent mouseEvent) throws MalformedURLException {
//        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
//            if(mouseEvent.getClickCount() == 2) {
//                fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Img Files", "*.jpg", "*.jpeg", "*.png"),
//                        new ExtensionFilter(".jpg/.jpeg", "*.jpeg", "*.jpg"),
//                        new ExtensionFilter(".png", "*.png"));
//                selectedImgsList = fileChooser.showOpenMultipleDialog(null);
//                if(selectedImgsList != null) {
//                        singleFile = selectedImgsList.get(numberOfPhoto);
//                        Image image = new Image(singleFile.toURL().toString(),
//                        900, 400,
//                        true, true, true);
//                        imgFieldView.setImage(image);                 
//                    } else {
//                    System.out.println("No File Selected");
//                }
//            }
//        }
//    }
    
    
        
//    @FXML
//    public void scrollImage(KeyEvent key) throws MalformedURLException{
//        int counter = 0;
//        int listSize = selectedImgsList.size();
//        
//        if(key.getCode() == KeyCode.LEFT && key.getCode() == KeyCode.ALT) {
//            counter++;
//        }
//        
//        if(key.getCode() == KeyCode.RIGHT) {
//            counter--;
//        }
//        
//        if(counter < 0) {
//            counter = listSize - counter;
//        }
//        
//        
//        
//        singleFile = selectedImgsList.get(counter);
//        Image image = new Image(singleFile.toURL().toString(),
//            900, 400,
//            true, true, true);
//        imgFieldView.setImage(image);
//    }
    
    
   
    
    
    

    
}
   
