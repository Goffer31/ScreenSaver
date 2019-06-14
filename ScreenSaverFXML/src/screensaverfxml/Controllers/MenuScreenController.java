/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screensaverfxml.Controllers;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.imageio.ImageIO;

/**
 *
 * @author root
 */
public class MenuScreenController implements Initializable {

    @FXML
    private ImageView imgFieldView;
    @FXML
    private Pane menuPane;

    Image image;
    double angleRotation;
    int photoSwipCounter = 0;
    int rotationsCounter = 0;

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
            double menuPaneHeight = menuPane.getHeight();
            double menuPaneWidth = menuPane.getWidth();

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

    @FXML
    public void centerImageResize(ImageView imageView) {
        if (imageView.getImage() != null) {
            double ratioX = imageView.getFitWidth() / imageView.getImage().getWidth();
            double ratioY = imageView.getFitHeight() / imageView.getImage().getHeight();
            double dividerRatio;

            if (ratioX >= ratioY) {
                dividerRatio = ratioY;
            } else {
                dividerRatio = ratioX;
            }

            imageView.setFitWidth(imageView.getImage().getWidth() * dividerRatio);
            imageView.setFitHeight(imageView.getImage().getHeight() * dividerRatio);

            imageView.setX(imageView.getFitWidth() - imageView.getImage().getWidth());
            imageView.setY(imageView.getFitHeight() - imageView.getImage().getHeight());
        }
    }

    @FXML
    public void center(ImageView imageView) {
        imageView.setX((menuPane.getWidth() - imageView.getImage().getWidth()) / 2);
        imageView.setY((menuPane.getHeight() - imageView.getImage().getHeight()) / 2);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image startAppImage = new Image("/resourcePackage/greybox.png");
        imgFieldView.setImage(startAppImage);
    }

    public void resizeImageInsideWindow(double width, double height) {
        if (width != 0 && height != 0) {
            menuPane.setPrefWidth(width);
            menuPane.setPrefHeight(height);
        }

        if (menuPane.getWidth() < imgFieldView.getImage().getWidth() || menuPane.getHeight() < imgFieldView.getImage().getHeight()) {
            double scaleX = menuPane.getWidth() / imgFieldView.getImage().getWidth();
            double scaleY = menuPane.getHeight() / imgFieldView.getImage().getHeight();
            System.out.println("scaleX " + scaleX);
            System.out.println("scaleY " + scaleY);

            double scale;
            if (scaleX > scaleY) {
                scale = scaleY;
            } else {
                scale = scaleX;
            }

            imgFieldView.setFitWidth(imgFieldView.getImage().getWidth() * scale);
            imgFieldView.setFitHeight(imgFieldView.getImage().getHeight() * scale);

            imgFieldView.setX((menuPane.getWidth() - (imgFieldView.getImage().getWidth() * scale)) / 2);
            imgFieldView.setY((menuPane.getHeight() - (imgFieldView.getImage().getHeight() * scale)) / 2);
        } else {
            imgFieldView.setFitWidth(imgFieldView.getImage().getWidth());
            imgFieldView.setFitHeight(imgFieldView.getImage().getHeight());
            imgFieldView.setX((menuPane.getWidth() - imgFieldView.getImage().getWidth()) / 2);
            imgFieldView.setY((menuPane.getHeight() - imgFieldView.getImage().getHeight()) / 2);
        }
    }

    @FXML
    public void showSout(MouseEvent mouseEvent) {
        System.out.println("Mouse released showSout");
    }

    /**
     * Method generating keyCodes basic on String ArrayList with paths
     *
     * @param stringKeyArrayList
     * @return
     */
    @FXML
    public ArrayList<KeyCode> stringToKeyCodeGenerator(ArrayList<String> stringKeyArrayList) {
        String temporaryString;
        KeyCode temporaryKeyCode;

        keyCodeArrayList = new ArrayList<>();

        for (int i = 0; i < stringKeyArrayList.size(); i++) {
            temporaryString = stringKeyArrayList.get(i);
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
     *
     * @param pathArrayList
     * @return
     */
    @FXML
    public ArrayList<String> pathsGenerator(ArrayList<String> pathArrayList) {
        String temporaryString;

        pathTargetArrayList = new ArrayList<>();

        for (int i = 0; i < pathArrayList.size(); i++) {
            temporaryString = pathArrayList.get(i);
            if (temporaryString != null) {
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
            image = new Image(singleFile.toURL().toString());
            imgFieldView.setImage(image);
            photoSweep();
            System.out.println(singleFile.getName());
            for (int i = 0; i < selectedImagesList.size(); i++) {
                System.out.println(selectedImagesList.get(i).getName());
            }
        } else {
            System.out.println("No File Selected");
        }
    }

    @FXML
    public void getNewImageHandler(KeyEvent event) {
        /**
         * Code responsible for switching photos
         */
        imgFieldView.requestFocus();

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
        if (keyCodeArrayList == null) {
            return;
        }
        for (int i = 0; i < keyCodeArrayList.size(); i++) {
            if (keyCodeArrayList.get(i) == null) {
                continue;
            }
            if (event.getCode().equals(keyCodeArrayList.get(i)) && singleFile != null) {
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
    }

    @FXML
    public void imageToFile(Image image) throws IOException {

        if (singleFile == null) {
            return;
        }

        BufferedImage bufferedImage = ImageIO.read(singleFile);

        while (angleRotation < 0 || angleRotation >= 360) {
            if (angleRotation < 0) {
                angleRotation += 360;
            } else {
                angleRotation -= 360;
            }
        }

        int temporaryInt = (int) angleRotation / 90;

        for (int i = 0; i < temporaryInt; i++) {
            bufferedImage = rotateClockwise90(bufferedImage);
        }

        ImageIO.write(bufferedImage, "png", new File(singleFile.getAbsolutePath()));
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
        if (selectedImgsList == null || selectedImgsList.isEmpty()) {
            singleFile = null;
            Image emptyListImage = new Image("/resourcePackage/greybox.png");
            imgFieldView.setImage(emptyListImage);
            resizeImageInsideWindow(0, 0);
            return;
        }

        if (photoSwipCounter < 0) {
            photoSwipCounter = selectedImgsList.size() - 1;
        } else if (photoSwipCounter >= selectedImgsList.size()) {
            photoSwipCounter = 0;
        }

        /**
         * set greybox logo if list is empty
         */
        System.out.println("sizeOfSelectedImgsList: " + selectedImgsList.size());
        singleFile = selectedImgsList.get(photoSwipCounter);
        image = new Image(singleFile.toURI().toString());
        imgFieldView.setImage(image);
        resizeImageInsideWindow(0, 0);
    }

    @FXML
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
        imageToFile(imgFieldView.getImage());
        Path sourceDirectory = Paths.get(singleFile.getAbsolutePath());
        String targetDirectoryString = directory + "\\" + singleFile.getName();
        Path targetDirectory = Paths.get(targetDirectoryString);
        Files.copy(sourceDirectory, targetDirectory);

        ArrayList<File> temporaryArrayList = new ArrayList<>();
        for (int i = 0; i < selectedImgsList.size(); i++) {
            if (i == photoSwipCounter) {
                continue;
            }
            temporaryArrayList.add(selectedImgsList.get(i));
        }
        selectedImgsList = temporaryArrayList;

        angleRotation = 0;
        imgFieldView.setRotate(angleRotation);

        photoSweep();
    }

    @FXML
    public void savePhotoAndDelete(File singleFile, String directory) {
        try {
            imageToFile(imgFieldView.getImage());
        } catch (IOException ex) {
            Logger.getLogger(MenuScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
        String fileNameAndPathToSave = directory + "\\" + singleFile.getName();
        System.out.println("fileNameToSave2: " + fileNameAndPathToSave);
        singleFile.renameTo(new File(fileNameAndPathToSave));

        ArrayList<File> temporaryArrayList = new ArrayList<>();
        for (int i = 0; i < selectedImgsList.size(); i++) {
            if (i == photoSwipCounter) {
                continue;
            }
            temporaryArrayList.add(selectedImgsList.get(i));
        }
        selectedImgsList = temporaryArrayList;

        angleRotation = 0;
        imgFieldView.setRotate(angleRotation);

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
                stage.initStyle(StageStyle.UNDECORATED);
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
