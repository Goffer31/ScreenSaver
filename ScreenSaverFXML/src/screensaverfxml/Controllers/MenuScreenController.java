/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screensaverfxml.Controllers;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
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
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import screensaverfxml.Controllers.MainScreenController.ValueContainer;

/**
 *
 * @author root
 */
public class MenuScreenController implements Initializable {

    @FXML
    private ImageView imgFieldView;
    @FXML
    private Pane menuPane;

    private final String numberRegex = "[0-9]";

    Image image;
    double angleRotation;
    int photoSwipCounter = 0;
    int rotationsCounter = 0;

    private MainScreenController mainScreenController;
    private SettingsScreenController settingsScreenController;
    private ValueContainer valueContainer;

    final FileChooser fileChooser = new FileChooser();

    List<String> selectedImgsList;
    String singleFile;
    File selectedDirectory = null;
    ArrayList<String> keyCodeStringArrayList;
    ArrayList<KeyCode> keyCodeArrayList;
    ArrayList<String> pathTargetArrayList;
    int copyOrMoveStatusFlag = 1;

    void setMainScreenController(MainScreenController mainScreenController) {
        this.mainScreenController = mainScreenController;
    }

    public void setCopyOrMoveStatusFlag(int copyOrMoveStatusFlag) {
        this.copyOrMoveStatusFlag = copyOrMoveStatusFlag;
    }

    @FXML
    public void setFullScreenMenuItem(ActionEvent event) {
        mainScreenController.stage.setFullScreen(!mainScreenController.stage.isFullScreen());
    }

    public void setValueContainer(ValueContainer valueContainer) {
        this.valueContainer = valueContainer;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image startAppImage = new Image("/resourcePackage/greybox.png");
        imgFieldView.setImage(startAppImage);
    }

    public void resizeImageInsideWindow() {
        double width = valueContainer.xChecker - valueContainer.xCheckerOffset;
        double height = valueContainer.yChecker - valueContainer.yCheckerOffset;

        if (width != 0 && height != 0) {
            menuPane.setPrefWidth(width);
            menuPane.setPrefHeight(height);
        }

        if (width < imgFieldView.getImage().getWidth() || height < imgFieldView.getImage().getHeight()) {

            double scaleX = width / imgFieldView.getImage().getWidth();
            double scaleY = height / imgFieldView.getImage().getHeight();

            double scale;
            if (scaleX > scaleY) {
                scale = scaleY;
            } else {
                scale = scaleX;
            }

            imgFieldView.setFitWidth(imgFieldView.getImage().getWidth() * scale);
            imgFieldView.setFitHeight(imgFieldView.getImage().getHeight() * scale);

            imgFieldView.setX((width - (imgFieldView.getImage().getWidth() * scale)) / 2);
            imgFieldView.setY((height - (imgFieldView.getImage().getHeight() * scale)) / 2);
        } else {
            imgFieldView.setFitWidth(imgFieldView.getImage().getWidth());
            imgFieldView.setFitHeight(imgFieldView.getImage().getHeight());
            imgFieldView.setX((width - imgFieldView.getImage().getWidth()) / 2);
            imgFieldView.setY((height - imgFieldView.getImage().getHeight()) / 2);
        }
    }

    @FXML
    public void showSout(MouseEvent mouseEvent) {
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
                System.out.println("temporaryString: " + temporaryString + " size of temporaryString: " + temporaryString.length());
                if (temporaryString.matches(numberRegex)) {
                    temporaryKeyCode = KeyCode.valueOf("DIGIT" + temporaryString);
                } else {
                    temporaryKeyCode = KeyCode.valueOf(temporaryString);
                }
                keyCodeArrayList.add(temporaryKeyCode);
            } else {
                keyCodeArrayList.add(null);
            }
        }
        System.out.println("keyCodeArrayList contains: " + keyCodeArrayList);
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
        System.out.println("pathTargetArrayList contains: " + pathTargetArrayList);
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
    
    public void loadImageOnScreen(List<String> selectedImagesList) throws MalformedURLException {
        this.selectedImgsList = selectedImagesList;
        if (selectedImagesList != null) {
            singleFile = selectedImagesList.get(0);
            System.out.println("singleFile = " + singleFile);
            File temporaryFile = new File(singleFile);
            image = new Image(temporaryFile.toURI().toURL().toExternalForm());
            imgFieldView.setImage(image);
            photoSweep();
        } else {
            System.out.println("No File Selected");
        }
    }

    public void getNewImageHandler(KeyEvent event) {
        /**
         * Code responsible for switching photos accessed from MenuScreen.fxml
         */
        imgFieldView.requestFocus();

        if (event.getCode().equals(KeyCode.RIGHT) || event.getCode().equals(KeyCode.KP_RIGHT)) {
            photoSwipCounter++;
            angleRotation = 0;
            imgFieldView.setRotate(angleRotation);
            imgFieldView.getViewport();
        }

        if (event.getCode().equals(KeyCode.LEFT) || event.getCode().equals(KeyCode.KP_LEFT)) {
            photoSwipCounter--;
            angleRotation = 0;
            imgFieldView.setRotate(angleRotation);
            imgFieldView.getViewport();
        }

        photoSweep();
        
        if(event.getCode().equals(KeyCode.DELETE)) {
            try {
//                Files.deleteIfExists(Paths.get(singleFile));
//                Files.deleteIfExists(Paths.get(selectedImgsList.get(photoSwipCounter)));
                Files.deleteIfExists(Paths.get(singleFile));
                selectedImgsList.remove(photoSwipCounter);
//                selectedImgsList.set(photoSwipCounter, null);
            } catch (IOException ex) {
                Logger.getLogger(MenuScreenController.class.getName()).log(Level.SEVERE, null, ex);
            }
            photoSwipCounter++;
            photoSweep();
        }

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
        
        if(event.getCode().equals(KeyCode.SLASH)) {
            angleRotation += 180;
            imgFieldView.setRotate(angleRotation);
            imgFieldView.getViewport();
        }
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
                String patchContainer = null;
                for (int j = 0; j < keyCodeArrayList.size(); j++) {
                    if (keyCodeArrayList.get(i) == keyCodeArrayList.get(j)) {
                        if (copyOrMoveStatusFlag == 1) {
                            try {
                                savePhotoWithCopy(singleFile, pathTargetArrayList.get(j));
                            } catch (IOException e) {
                                Logger.getLogger(MenuScreenController.class.getName()).log(Level.SEVERE, null, e);
                            }
                        }
                        if (copyOrMoveStatusFlag == 2) {
                            try {
                                savePhotoWithCopy(singleFile, pathTargetArrayList.get(j));
                                patchContainer = pathTargetArrayList.get(j);
                            } catch (IOException e) {
                                Logger.getLogger(MenuScreenController.class.getName()).log(Level.SEVERE, null, e);
                            }
                        }
                    }
                }
                if (copyOrMoveStatusFlag == 1) {
                    clearListAfterCopy();
                    return;
                }
                if (copyOrMoveStatusFlag == 2) {
                    savePhotoAndDelete(singleFile, patchContainer);
                    clearListAfterCopy();
                    return;
                }
            }
        }
    }

    @FXML
    public void imageRotation(KeyEvent event) {
        if (event.getCode().equals(KeyCode.R)) {
            imgFieldView.setRotate(90);
        } else if (event.getCode().equals(KeyCode.L)) {
            imgFieldView.setRotate(-90);
        }
    }
    
//    public static final int ROTATE_LEFT = 270;
//    public static final int ROTATE_RIGHT = 90;
//    public static final int UPSIDE_DOWN = 180;
//    public void imageToFile(Image image, double angle) {
//        try {
//            if(singleFile == null) {
//                return;
//            }
//            File inputFile = new File(singleFile);
//            ImageInputStream iis = ImageIO.createImageInputStream(inputFile);
//            Iterator<ImageReader> iterator = ImageIO.getImageReaders(iis);
//            ImageReader reader = iterator.next();
//            String format = reader.getFormatName();
//
//            BufferedImage bufferedImage = ImageIO.read(iis);
//            int width = bufferedImage.getWidth();
//            int height = bufferedImage.getHeight();
//
//            BufferedImage rotated = new BufferedImage(height, width, bufferedImage.getType());
//            for (int y = 0; y < height; y++) {
//                for (int x = 0; x < width; x++) {
//                    switch ((int)angle) {
//                        case ROTATE_LEFT:
//                            rotated.setRGB(y, (width - 1) - x, bufferedImage.getRGB(x, y));
//                            break;
//                        case ROTATE_RIGHT:
//                            rotated.setRGB((height - 1) - y, x, bufferedImage.getRGB(x, y));
//                            break;
//                        case UPSIDE_DOWN:
//                            rotated.setRGB(y, x, bufferedImage.getRGB(x, y));
//                            break;
//                    }
//                }
//            }
//            ImageIO.write(rotated, format, inputFile);
//        } catch (IOException ex) {
//            Logger.getLogger(MenuScreenController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//    }

    public void saveFileAfterRotation(Image image) throws IOException {
        if (singleFile == null) {
            return;
        }
        File newSingleFile = new File(singleFile);
        BufferedImage bufferedImage = ImageIO.read(newSingleFile);
        
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

        ImageIO.write(bufferedImage, "png", new File(newSingleFile.getAbsolutePath()));
    }
    
    /**
     * nie działa przerobic
     * @param image
     * @param angle 
     */
    public void saveFileAfterRotation(Image image, double angle) {
        double sin = Math.abs(Math.sin(Math.toRadians(angle)));
        double cos = Math.abs(Math.cos(Math.toRadians(angle)));
        int w = (int) image.getWidth();
        int h = (int) image.getHeight();
        int neww = (int) Math.floor(w*cos + h*sin);
        int newh = (int) Math.floor(h*cos + w*sin);
        BufferedImage bimg = new BufferedImage(neww, newh, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bimg.createGraphics();
        
        g.translate((neww - w) / 2, (newh - h) / 2);
        g.rotate(Math.toRadians(angle), w/2, h/2);
        g.drawRenderedImage(bimg, null);
        g.dispose();
        System.out.println("g.toString = " + g.toString());
        singleFile = g.toString();
    }
    
    public BufferedImage rotateImageByAngle(BufferedImage img, double angle) {
        double rads = Math.toRadians(angle);
        double sin = Math.abs(Math.sin(rads));
        double cos = Math.abs(Math.cos(rads));
        int w = img.getWidth();
        int h = img.getHeight();
        int newWidth = (int) Math.floor(w * cos + h * sin);
        int newHeight = (int) Math.floor(h * cos + w * sin);
        
        BufferedImage rotated = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rotated.createGraphics();
        AffineTransform at = new AffineTransform();
        at.translate((newWidth - w) / 2, (newHeight - h) / 2);
        
        int x = w / 2;
        int y = h / 2;
        
        at.rotate(rads, x , y);
        g2d.setTransform(at);
        g2d.drawImage(img, 0, 0, null);
        g2d.setColor(null);
        g2d.drawRect(0, 0, newWidth - 1, newHeight - 1);
        g2d.dispose();
        
        return rotated;
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
    
    public static BufferedImage rotateClockwise180(BufferedImage src) {
        int width = src.getWidth();
        int height = src.getHeight();

        BufferedImage dest = new BufferedImage(height, width, src.getType());
        
        Graphics2D graphics2D = dest.createGraphics();
//        graphics2D.translate((height - width) / 2, (height - width) / 2);
        graphics2D.rotate(Math.PI, height, width);
        graphics2D.drawRenderedImage(src, null);

        return dest;
    }

    private void photoSweep() {
        if (selectedImgsList == null || selectedImgsList.isEmpty()) {
            singleFile = null;
            Image emptyListImage = new Image("/resourcePackage/greybox.png");
            imgFieldView.setImage(emptyListImage);
            resizeImageInsideWindow();
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
        singleFile = selectedImgsList.get(photoSwipCounter);
        File temporaryFile = new File(singleFile);
        try {
            image = new Image(temporaryFile.toURI().toURL().toExternalForm());
        } catch (MalformedURLException ex) {
            Logger.getLogger(MenuScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
        imgFieldView.setImage(image);
        resizeImageInsideWindow();
    }

    public String pathChooser() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select save location");
        if (selectedDirectory == null) {
            selectedDirectory = directoryChooser.showDialog(null);
        }
        return selectedDirectory.getAbsolutePath();
    }

    @FXML
    private void savePhotoWithCopy(String singleFile, String directory) throws IOException {
        System.out.println("imgFieldView.getImage().toString() = " + imgFieldView.getImage().toString());
        if (angleRotation != 0) {
//            saveFileAfterRotation(imgFieldView.getImage(), angleRotation);
            File imageFile = new File(singleFile);
            BufferedImage image = ImageIO.read(imageFile);
            System.out.println("imageBFImageIO = " + image);
            BufferedImage rotated = rotateImageByAngle(image, angleRotation);
            /**
             * przy ustawieniu jpg otrzymujemy niezwykłe kolory
             */
            ImageIO.write(rotated, "jpg", new File(imageFile.getName()));
        }
        Path sourceDirectory = Paths.get(singleFile);
        String fileName = new File(singleFile).getName();
        System.out.println("directory = " + directory);
        System.out.println("fileName = " + fileName);
        String targetDirectoryString = directory + "/" + fileName;
        Path targetDirectory = Paths.get(targetDirectoryString);
//        Files.copy(sourceDirectory, targetDirectory, StandardCopyOption.REPLACE_EXISTING);
//-------------------------------------------------------------------
//        InputStream inputStream = null;
//        OutputStream outputStream = null;
//        File sourceFile = new File(singleFile);
//        File destinationFile = new File(targetDirectoryString);
//        try {
//            inputStream = new FileInputStream(sourceFile);
//            outputStream = new FileOutputStream(destinationFile);
//            byte[] buffer = new byte[1024];
//            int lenght;
//            while ((lenght = inputStream.read(buffer)) > 0) {
//                outputStream.write(buffer, 0, lenght);
//            }
//        } finally {
//            inputStream.close();
//            outputStream.close();
//        }
//-------------------------------------------------------------------
    }

    @FXML
    public void savePhotoAndDelete(String singleImage, String directory) {
        if (angleRotation != 0) {
            try {
                saveFileAfterRotation(imgFieldView.getImage());
            } catch (IOException ex) {
                Logger.getLogger(MenuScreenController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        int index = singleImage.lastIndexOf("/");
        String fileName = singleImage.substring(index + 1);
        String fileNameAndPathToSave = directory + "/" + fileName;
//        String fileNameAndPathToSave = directory + "/" + singleFile.getName();
        File singleImageFile = new File(singleImage);
        
        singleImageFile.renameTo(new File(fileNameAndPathToSave));
//--------------------------------------------------------------------
//        FileInputStream inputStream = null;
//        FileOutputStream outputStream = null;
//        singleImageFile.deleteOnExit();
//        try { 
//            inputStream = new FileInputStream(singleImageFile);
//            outputStream = new FileOutputStream(fileNameAndPathToSave);
//        } catch(FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        
//        final FileChannel inChannel = inputStream.getChannel();
//        final FileChannel outChannel = outputStream.getChannel();
//        
//        try {
//            inChannel.transferTo(0, inChannel.size(), outChannel);
//        } catch (IOException ex) {
//            Logger.getLogger(MenuScreenController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        finally {
//            try {
//                inChannel.close();
//                outChannel.close();
//                inputStream.close();
//                outputStream.close();
//            } catch(IOException e) {
//                e.printStackTrace();
//            }
//        }
//--------------------------------------------------------------------   

        singleImageFile.delete();
    }

    private void clearListAfterCopy() {
        ArrayList<String> temporaryArrayList = new ArrayList<>();
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

    /**
     * Code responsible for load settings window
     */
    @FXML
    MenuItem settingsMenuItem;
    @FXML
    MenuItem aboutMenuItem;
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
    public void copyResourcesAndLoad() {
        File file = null;
        String resource = "/resourcePackage/PhotoBox.html";
        URL res = getClass().getResource(resource);
        if (res.getProtocol().equals("jar")) {
            try {
                InputStream input = getClass().getResourceAsStream(resource);

                file = new File(SystemCheck.returnApplicationPath() + "/tempfile.html");
                OutputStream out = new FileOutputStream(file);
                int read;
                byte[] bytes = new byte[1024];

                while ((read = input.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }
                out.close();
                String path = file.getAbsolutePath();
                System.out.println(URI.create(path.replace("\\", "/")));

                Desktop.getDesktop().browse(URI.create(path.replace("\\", "/")));
                file.deleteOnExit();
            } catch (IOException ex) {
                Logger.getLogger(MenuScreenController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            //this will probably work in your IDE, but not from a JAR
            file = new File(res.getFile());
        }

        if (file != null && !file.exists()) {
            throw new RuntimeException("Error: File " + file + " not found!");
        }

    }

    @FXML
    public void exit() {
        Platform.exit();
    }

}
