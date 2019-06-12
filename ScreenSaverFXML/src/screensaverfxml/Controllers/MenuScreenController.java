/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screensaverfxml.Controllers;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
<<<<<<< HEAD
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Rotate;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

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

    public MenuScreenController() {
    }
    
    
public class MenuScreenController{
 
    @FXML
    private StackPane stackImgPane;
    @FXML
    private ImageView imgFieldView;
    
    Image image;
    double angleRotation;
    int photoSwipCounter = 0;

   private MainScreenController mainScreenController;
   private SettingsScreenController settingsScreenController;
   
   final FileChooser fileChooser = new FileChooser();   
   
   List<File> selectedImgsList;
    File singleFile;
    File selectedDirectory = null;
    ArrayList<String> keyCodeStringArrayList;
    ArrayList<KeyCode> keyCodeArrayList;
    ArrayList<String> pathTargetArrayList;
    int copyOrMoveStatusFlag = 1;
    
    void setMainController(MainScreenController mainScreenController) {
        this.mainScreenController = mainScreenController;
    }
    
     public void setCopyOrMoveStatusFlag(int copyOrMoveStatusFlag) {
        this.copyOrMoveStatusFlag = copyOrMoveStatusFlag;
    }
    
    
//    Method to make image on the center of the screen
    public void centerImage(ImageView imgView) {
        Image img = imgView.getImage();
        if (img != null) {
            double imageWidth;
            double imageHeight;
            double ratioX = imgView.getFitHeight() / img.getWidth();
            double ratioY = imgView.getFitWidth() / img.getHeight();
            double reducCoeff;
            
            if (ratioX >= ratioY) {
                reducCoeff = ratioY;
            } else {
                reducCoeff = ratioX;
            }
            
            imageWidth = img.getWidth() * reducCoeff;
            imageHeight = img.getHeight() * reducCoeff;
            imgView.setX((imgView.getFitWidth() - imageWidth) / 2);
            imgView.setY((imgView.getFitHeight() - imageHeight) / 2);
            System.out.println("SetX" + ((imgView.getFitWidth() - imageWidth) / 2));
            System.out.println("SetY" + ((imgView.getFitHeight() - imageHeight) / 2));
        }
    }
     
     /**
      * Method generating keyCodes basic on String ArrayList with paths
      * @param pathArrayList
      * @return 
      */
     
    @FXML
    public ArrayList<KeyCode> stringToKeyCodeGenerator(ArrayList<String> pathArrayList) {
        String temporaryString;
        KeyCode temporaryKeyCode;
        
        keyCodeArrayList = new ArrayList<>();
        
        for (int i = 0; i < pathArrayList.size(); i++) {
            temporaryString = pathArrayList.get(i);
            if (temporaryString != null) {
                System.out.println("temporaryString: " + temporaryString);
                temporaryKeyCode = KeyCode.valueOf(temporaryString);
                keyCodeArrayList.add(temporaryKeyCode);
            } else {
                keyCodeArrayList.add(null);
            }

            
        }
        
        return keyCodeArrayList;
    }

    
    /**
     * Method assigns getted sources for photo save
     * @param pathArrayList
     * @return 
     */
    @FXML
    public ArrayList<String> pathsGenerator(ArrayList<String> pathArrayList) {
        String temporaryString;
        
        pathTargetArrayList = new ArrayList<>();
        
        for(int i = 0; i < pathArrayList.size(); i++) {
            temporaryString = pathArrayList.get(i);
            if(temporaryString != null) {
                pathTargetArrayList.add(temporaryString);
            } else {
                pathTargetArrayList.add(null);
            }
        }
        
        return pathTargetArrayList;
    }
    
    
    @FXML
    public void focusAndLoadFirstImage() throws MalformedURLException {
        imageViewRequestFocus();
        selectedImgsList = new ArrayList<>();
        loadImageOnScreen(selectedImgsList);
    }
    
    @FXML
    public void pathAndKeyGeneratorInitializer() {
        keyCodeStringArrayList = new ArrayList<>();
        stringToKeyCodeGenerator(keyCodeStringArrayList);
    }

    public void imageViewRequestFocus() {
        imgFieldView.requestFocus();
    }

    public void loadImageOnScreen(List<File> selectedImagesList) throws MalformedURLException {
        this.selectedImgsList = selectedImagesList;
        if (selectedImagesList != null) {
            singleFile = selectedImagesList.get(0);
            image = new Image(singleFile.toURL().toString(),
                    900, 400,
                    true, true, true);
            imgFieldView.setImage(image);
            System.out.println(singleFile.getName());
            for (int i = 0; i < selectedImagesList.size(); i++) {
                System.out.println(selectedImagesList.get(i).getName());
            }
        } else {
            System.out.println("No File Selected");
        }
    }
    @FXML
    public void imgDoubleClick(MouseEvent mouseEvent) throws MalformedURLException {
        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
            if(mouseEvent.getClickCount() == 2) {
                fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Img Files", "*.jpg", "*.jpeg", "*.png"),
                        new ExtensionFilter(".jpg / .jpeg", "*.jpeg", "*.jpg"),
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
                    
    public void getNewImageHandler(KeyEvent event) {
        /**
         * Code responsible for switching photos
         */
        imgFieldView.requestFocus();
        System.out.println(singleFile.getName());
        
        if (event.getCode().equals(KeyCode.RIGHT) || event.getCode().equals(KeyCode.KP_RIGHT)) {
            photoSwipCounter++;
        }

        if (event.getCode().equals(KeyCode.LEFT) || event.getCode().equals(KeyCode.KP_LEFT)) {
            photoSwipCounter--;
        }
        
        photoSweep();
        
        /**
         * Code responsible for rotate the image
         */
        
//        wykorzystac SnapshotsParameters do obracania i metodę imgFieldView.snapshot moze zeby cos pokombinowac 
//https://stackoverflow.com/questions/33613664/javafx-drawimage-rotated?rq=1

        if (event.getCode().equals(KeyCode.PERIOD)) {
            angleRotation += 90;
            imgFieldView.setRotate(angleRotation);
            imgFieldView.getViewport();
        }

        if (event.getCode().equals(KeyCode.COMMA)) {
            angleRotation -= 90;
            imgFieldView.setRotate(angleRotation);
            imgFieldView.getViewport();
        }
        //---***---***---***---***---***---***---***---***---***---***---***---*
        
        /**
         * Code responsible for saving photos into max four different locations 
         * using customize keyCode to write folders under buttons
         */
        
        for (int i = 0; i < keyCodeArrayList.size(); i++) {
            if(keyCodeArrayList.get(i) == null) {
                continue;
            }
            if (event.getCode().equals(keyCodeArrayList.get(i))) {
                if (copyOrMoveStatusFlag == 1) {
                    try {
                        savePhotoWithCopy(singleFile, pathTargetArrayList.get(i));
                    } catch (IOException ex) {
                        Logger.getLogger(MenuScreenController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (copyOrMoveStatusFlag == 2) {
                    savePhotoAndDelete(singleFile, pathTargetArrayList.get(i));
                }
            }
        }
        
//        centerImage(imgFieldView);
        
    }
    
    @FXML
    public void imageRotation(KeyEvent event) {
        if(event.getCode().equals(KeyCode.R)){
            imgFieldView.setRotate(90);
        } else if(event.getCode().equals(KeyCode.L)){
            imgFieldView.setRotate(-90);
        }
    }
    
    @FXML
    public void getNewImage(KeyEvent event) {
//        zrobić zabezpieczenie przed cofnięciem na pozycji 0
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
    public void imageToFile(Image image) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(singleFile);
        ImageIO.write(rotateClockwise90(bufferedImage), "jpg", new File(singleFile.getAbsolutePath() + "2"));
    }
    
    public static BufferedImage rotateClockwise90(BufferedImage src) {
    int width = src.getWidth();
    int height = src.getHeight();

    BufferedImage dest = new BufferedImage(height, width, src.getType());
    
    Graphics2D graphics2D = dest.createGraphics();
    graphics2D.translate((height - width) / 2, (height - width) / 2);
    graphics2D.rotate(Math.PI / 2, height / 2, width / 2);
    graphics2D.drawRenderedImage(src, null);

    return dest;
}

    private void photoSweep() {
        if (photoSwipCounter < 0) {
            photoSwipCounter = selectedImgsList.size() - 1;
        } else if (photoSwipCounter >= selectedImgsList.size()) {
            photoSwipCounter = 0;
        }
        
        /**
         * set greybox logo if list is empty
         */
        if(selectedImgsList == null) {
//            singleFile
        }
        
        singleFile = selectedImgsList.get(photoSwipCounter);
        
        image = new Image(singleFile.toURI().toString(),
                900, 400,
                true, true, true);
        imgFieldView.setImage(image);
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
        menuPane.setOnKeyPressed(new EventHandler<KeyEvent>() {
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
    
    
   
    public String pathChooser() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select save location");
        if (selectedDirectory == null) {
            selectedDirectory = directoryChooser.showDialog(null);
        }
        System.out.println("Directory choosed: " + selectedDirectory.getAbsolutePath());
        return selectedDirectory.getAbsolutePath();
    }
    
    @FXML
    private void savePhotoWithCopy(File singleFile, String directory) throws IOException {
        Path sourceDirectory = Paths.get(singleFile.getAbsolutePath());
        String targetDirectoryString = directory + "\\" + singleFile.getName();
        imageToFile(imgFieldView.getImage());
        Path targetDirectory = Paths.get(targetDirectoryString);
        Files.copy(sourceDirectory, targetDirectory);
        
        ArrayList<File> temporaryArrayList = new ArrayList<>();
        
        for(int i = 0; i < selectedImgsList.size(); i++) {
            if(i == photoSwipCounter) { 
                continue;
            }
            temporaryArrayList.add(selectedImgsList.get(i));
        }
        selectedImgsList = temporaryArrayList;
        
        
        
        photoSweep();
    }
    
    @FXML
    public void savePhotoAndDelete(File singleFile, String directory) {
        String fileNameAndPathToSave = directory + "\\" + singleFile.getName();
        System.out.println("fileNameToSave2: " + fileNameAndPathToSave);
        singleFile.renameTo(new File(fileNameAndPathToSave));
        selectedImgsList.get(photoSwipCounter).delete();
        photoSwipCounter++;
        photoSweep();
    }

    //---***---***---***---***---***---***---***---***---***---***---***---*---*
    /**
     * Code responsible for load settings window
     */
    
    @FXML
    MenuItem settingsMenuItem;
    Stage stage;
    
    @FXML
    public void showSettingsWindow(ActionEvent event) {
        try {
            FXMLLoader fXMLLoader = new FXMLLoader(getClass().getResource("/screensaverfxml/fxmlConfig/SettingsScreen.fxml"));
            Parent root1 = (Parent) fXMLLoader.load();
            if (stage == null) {
                stage = new Stage();
                stage.setTitle("Settings");
                stage.setScene(new Scene(root1));
                settingsScreenController = fXMLLoader.getController();
                settingsScreenController.setMenuScreenController(this);
            }
            stage.show();
        } catch (IOException e) {
            System.out.println("Can't load settings window");
        }
    }
    
    @FXML
    public void exit() {
        Platform.exit();
    }

}

//Centering image on the window
//https://stackoverflow.com/questions/20014787/how-do-i-center-an-image-view-in-an-anchor-pane?rq=1

//Creating a settings window
//https://www.youtube.com/watch?v=5NM27PP5rME

//Key Listener
//https://www.programcreek.com/java-api-examples/?class=javafx.scene.Scene&method=setOnKeyPressed
//https://www.youtube.com/watch?v=UotiVqAjhDY
//https://www.youtube.com/watch?v=MZAFix_-9UI <- best 
//https://www.youtube.com/watch?v=bUxwGl7W9-E
//https://www.youtube.com/watch?v=xwWARVJB5g0


//Save files
//https://stackoverflow.com/questions/4871051/getting-the-current-working-directory-in-java <---- very good
//https://www.youtube.com/watch?v=F4b55du_Nic <------- very good 
//https://stackoverflow.com/questions/6366743/saving-files-to-a-specific-directory-in-java
//https://stackoverflow.com/questions/26693550/how-can-i-save-a-file-in-the-current-directory
//https://stackoverflow.com/questions/16239130/java-user-dir-property-what-exactly-does-it-mean
//https://www.genuinecoder.com/save-files-javafx-filechooser/
//https://www.tutorialspoint.com/java/io/java_io_file.htm
//https://stackoverflow.com/questions/10083447/selecting-folder-destination-in-java
//https://stackoverflow.com/questions/20958668/jfilechooser-getcurrentdirectory-to-string
//https://docs.oracle.com/javase/6/docs/api/java/io/File.html
//https://www.rgagnon.com/javadetails/java-0370.html
//http://www.java2s.com/Code/Java/Swing-JFC/SelectadirectorywithaJFileChooser.htm
//https://stackoverflow.com/questions/14967449/read-from-a-file-that-is-in-the-same-folder-as-the-jar-file

//Rotation
//https://stackoverflow.com/questions/33613664/javafx-drawimage-rotated/54142453
//https://stackoverflow.com/questions/33613664/javafx-drawimage-rotated

//Visual side
//https://www.youtube.com/watch?v=1myTZQowNZw


