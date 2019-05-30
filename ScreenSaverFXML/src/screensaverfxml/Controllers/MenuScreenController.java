/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screensaverfxml.Controllers;

import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.MalformedURLException;
import java.util.List;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Point3D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.NonInvertibleTransformException;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
/**
 *
 * @author root
 */
public class MenuScreenController{
 
    @FXML
    private StackPane stackImgPane;
   @FXML
   ImageView imgFieldView;
   int photoSwipCounter = 0;
   Image image;
      
//   private Desktop desktop = Desktop.getDesktop();
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
    
    public void centerImage(ImageView imgView) {
        Image img = imgView.getImage();
        if (img != null) {
            double imageWidth = 0;
            double imageHeight = 0;
            double ratioX = imgView.getFitHeight() / img.getWidth();
            double ratioY = imgView.getFitWidth() / img.getHeight();
            double reducCoeff = 0;
            if (ratioX >= ratioY) {
                reducCoeff = ratioY;
            } else {
                reducCoeff = ratioX;
            }
            imageWidth = img.getWidth() * reducCoeff;
            imageHeight = img.getHeight() * reducCoeff;
            imgView.setX((imgView.getFitWidth() - imageWidth) / 2);
            imgView.setY((imgView.getFitHeight() - imageHeight) / 2);
        }
    }
    
    @FXML
    public void imgDoubleClick(MouseEvent mouseEvent) throws MalformedURLException {

        // image view setting position
        centerImage(imgFieldView);
        // end of image view setting position
        imgFieldView.requestFocus();

        if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
            if (mouseEvent.getClickCount() == 2) {
                fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Img Files", "*.jpg", "*.jpeg", "*.png"));
                selectedImgsList = fileChooser.showOpenMultipleDialog(null);
                if (selectedImgsList != null) {
                    singleFile = selectedImgsList.get(0);
                    image = new Image(singleFile.toURL().toString(),
                            900, 400,
                            true, true, true);
                    imgFieldView.setImage(image);
                    System.out.println(singleFile.getName());
                    for (int i = 0; i < selectedImgsList.size(); i++) {
                        System.out.println(selectedImgsList.get(i).getName());
                    }
                } else {
                    System.out.println("No File Selected");
                }
            }
        }
    }
    
    @FXML
    public void getNewImageHandler(KeyEvent event) {
        centerImage(imgFieldView);
        imgFieldView.requestFocus();
        System.out.println(singleFile.getName());
        
        if (event.getCode().equals(KeyCode.N)) {
            photoSwipCounter++;
            System.out.println("N clicked");

        }
        if (event.getCode().equals(KeyCode.P)) {
            photoSwipCounter--;
            System.out.println("P clicked");
        }

        if (photoSwipCounter < 0) {
            photoSwipCounter = selectedImgsList.size() - 1;
        } else if (photoSwipCounter >= selectedImgsList.size()) {
            photoSwipCounter = 0;
        }
        singleFile = selectedImgsList.get(photoSwipCounter);

        image = new Image(singleFile.toURI().toString(),
                900, 400,
                true, true, true);
        imgFieldView.setImage(image);
        
        /*
        from imageRotation()
        */
        
        Rectangle2D viewPoint = imgFieldView.getViewport();
        Rotate rotation = new Rotate();
        
        if (event.getCode().equals(KeyCode.L)) {
            
            SnapshotParameters params = new SnapshotParameters();
            Canvas canvas = new Canvas(100,100);
            
            image = imgFieldView.snapshot(params, null);
            canvas.getGraphicsContext2D().drawImage(image, 0, 0);
            //dokonczyc z https://stackoverflow.com/questions/40059836/rotating-image-in-javafx
            
//            stackImgPane.setBackground(new Background(new BackgroundImage(
//                    imgFieldView.getImage(), BackgroundRepeat.NO_REPEAT,
//                    BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
//                    BackgroundSize.DEFAULT)));
            
            
//            stackImgPane.getTransforms().add(new Rotate(90));
            
//            imgFieldView.setRotationAxis(Point3D.ZERO);
            
//            rotation.setAngle(90);
//            rotation.setPivotX(imgFieldView.getImage().getHeight()/2);
//            rotation.setPivotY(imgFieldView.getImage().getWidth()/2);


//            imgFieldView.getTransforms().add(new Rotate(90, Rotate.Z_AXIS));
           
//              addRotate(stackImgPane, Rotate.Z_AXIS, 45);

            
//            rotation.setPivotX(imgFieldView.getRotate());
            
            imgFieldView.getLocalToSceneTransform();
//            imgFieldView.setFitHeight(400);
//            imgFieldView.setFitWidth(900);
            imgFieldView.setPreserveRatio(true);
//            rotation.setPivotX(imgFieldView.getFitWidth() /2);
//            rotation.setPivotY(imgFieldView.getFitHeight() /2);    
//            rotation.setPivotY(imgFieldView.getImage().getRequestedHeight() /2);
//            rotation.setPivotX(imgFieldView.getImage().getRequestedWidth() /2);
            
//            rotation.setPivotX(viewPoint.getMaxX() /2);
//            rotation.setPivotY(viewPoint.getMaxY()/2);
//            rotation.setAngle(rotation.getAngle() + 90);

//            imgFieldView.getTransforms().add(new Rotate(90, imgFieldView.getFitHeight(), imgFieldView.getFitWidth()));
//            imgFieldView.getTransforms().add(new Rotate(90, imgFieldView.getRotationAxis()));
            imgFieldView.getTransforms().add(new Rotate(90,
                    imgFieldView.getBoundsInParent().getMinX()
                    + (imgFieldView.getBoundsInLocal().getWidth() / 2),
                    imgFieldView.getBoundsInParent().getMinY()
                    + (imgFieldView.getBoundsInLocal().getHeight() / 2)));

//            imgFieldView.getTransforms().add(new Rotate(90,
//                    imgFieldView.getFitWidth()
//                    - (imgFieldView.getImage().getWidth() / 2),
//                    imgFieldView.getFitHeight()
//                    - (imgFieldView.getImage().getHeight() / 2)));

//            imgFieldView.getTransforms().add(new Rotate(90, imgFieldView.getFitWidth()/2, imgFieldView.getFitHeight()/2));
//            imgFieldView.getTransforms().add(new Rotate(90, image.getRequestedWidth(), image.getRequestedHeight()));
            
            
            
//            imgFieldView.setRotate(90);
//            rotation.setPivotX(imgFieldView.getRotate());
            imgFieldView.getTransforms().add(rotation);
//            imgFieldView.getTransforms().add(new Rotate(90, imgFieldView.getFitHeight(), imgFieldView.getFitWidth()));
//            viewPoint = imgFieldView.getViewport();
            System.out.println("L clicked");
        }
        
        if (event.getCode().equals(KeyCode.R)) {
            imgFieldView.setRotate(-90);
            imgFieldView.getViewport();
            System.out.println("R clicked");
        }
    }
    
//    public static void addRotate(Node node, Point3D rotationAxis, double angle) {
//    ObservableList<Transform> transforms = node.getTransforms();
//    try {
//        for (Transform t : transforms) {
//            rotationAxis = t.inverseDeltaTransform(rotationAxis);
//        }
//    } catch (NonInvertibleTransformException ex) {
//        throw new IllegalStateException(ex);
//    }
//    transforms.add(new Rotate(angle, rotationAxis));
//}
    
    @FXML
    public void imageRotation(KeyEvent event) {
        if (event.getCode().equals(KeyCode.L)) {
            imgFieldView.setRotate(90);
            System.out.println("L clicked");

        }
        if (event.getCode().equals(KeyCode.R)) {
            imgFieldView.setRotate(-90);
            System.out.println("R clicked");
        }
    }
    
    @FXML 
    public void savePhoto(File singleFile) {
        File fileDirectoryOne = fileChooser.getInitialDirectory();
        
        File fileDirectory = new File(System.getProperty(fileChooser.getInitialDirectory().toURI().toString()));
        
        
        
    }


}
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

