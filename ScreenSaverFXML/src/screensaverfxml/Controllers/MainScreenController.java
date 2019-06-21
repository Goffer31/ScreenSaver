/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screensaverfxml.Controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import screensaverfxml.Controllers.SystemCheck.OSType;


/**
 * FXML Controller class
 *
 * @author Admin
 */
public class MainScreenController {

    @FXML
    private Pane mainPane;
    Stage stage;
    MenuScreenController menuScreenController;
    final ValueContainer valueContainer;
    
    private LicenceScreenController licenceScreenController;
    private SystemCheck systemCheck;

    public MainScreenController() {
        this.valueContainer = new ValueContainer();
    }
    
    public boolean loadLicenceScreen() throws IOException {
        Stage stageLoadScreen = null;
         FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/screensaverfxml/fxmlConfig/LicenceScreen.fxml"));
            Parent root1 = (Parent) loader.load();
            if (stageLoadScreen == null) {
                stageLoadScreen = new Stage();
                stageLoadScreen.setTitle("Settings");
                stageLoadScreen.setScene(new Scene(root1));
                stageLoadScreen.initStyle(StageStyle.UNDECORATED);
                licenceScreenController = loader.getController();
            }
            
            stageLoadScreen.show();
            System.out.println("=================================");
//            String systemProperties = System.getProperty("os.name");
//            String systemProperties2 = systemCheck.getOperatingSystemType().toString();
//            System.out.println(systemProperties2);
//            System.out.println(systemProperties);
//            System.out.println();
            OSType detectedType = systemCheck.getOperatingSystemType();
            systemCheck.trialCheck(detectedType);
            System.out.println("=================================");
            return stageLoadScreen.isShowing();
    }
    
    private void loadMenuScreen() throws IOException {

        System.out.println("=================================");
        OSType detectedType = SystemCheck.getOperatingSystemType();
        System.out.println(detectedType.toString());
        SystemCheck.trialCheck(detectedType);
        System.out.println("=================================");

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/screensaverfxml/fxmlConfig/MenuScreen.fxml"));
        Pane menuPane = loader.load();
        
        
        menuScreenController = loader.getController();
        menuScreenController.setMainScreenController(this);
        setScreen(menuPane);


    }

    public void setScreen(Pane pane) {
        mainPane.getChildren().clear();
        mainPane.getChildren().add(pane);
    }
    
    public class ValueContainer {
        public double yChecker = 0;
        public double xChecker = 0;
        public double yCheckerOffset = 0;
        public double xCheckerOffset = 0;
        
        @Override
        public String toString() { 
            return "xChecker: " + xChecker + ", yChecker: " + yChecker;
        }
        
        public void calculateXoffset(double sceneWidth) {
            if(xCheckerOffset == 0) {
                xCheckerOffset = xChecker - sceneWidth;
            }
        }
        
        public void calculateYoffset(double sceneHeight) {
            if(yCheckerOffset == 0 ) {
                yCheckerOffset = yChecker - sceneHeight;
            }
        }
    } 

    public void setStage(Stage stage) {
        
        this.stage = stage;
        

//        if (menuScreenController == null) {
//            stage.hide();
//            return;
//        }
//        
//        stage.show();

        menuScreenController.setValueContainer(valueContainer);
        
        
        stage.widthProperty().addListener((obs, oldVal, newVal) -> {
            valueContainer.xChecker = newVal.doubleValue();
            
            System.out.println(valueContainer);
            System.out.println("Width Changed");
            System.out.println("OldVal width: " + oldVal);
            System.out.println("NewVal width: " + newVal);
            valueContainer.calculateXoffset(stage.getScene().getWidth());
            if(valueContainer.xChecker != 0 && valueContainer.yChecker != 0) {
                menuScreenController.resizeImageInsideWindow();
            }
            stage.getScene().getWidth();
            System.out.println("Stage.getScene().getWidth() " + stage.getScene().getWidth());
            System.out.println("Stage.getScene().getHeight() " + stage.getScene().getHeight());
        });
        
        stage.heightProperty().addListener((obs, oldVal, newVal) -> {
            valueContainer.yChecker = newVal.doubleValue();
           
            System.out.println(valueContainer);
            System.out.println("Height Changed");
            System.out.println("OldVal height: " + oldVal);
            System.out.println("NewVal height: " + newVal);
            
            valueContainer.calculateYoffset(stage.getScene().getHeight());
            if(valueContainer.xChecker != 0 && valueContainer.yChecker != 0) {
                menuScreenController.resizeImageInsideWindow();
            }
            stage.getScene().getWidth();
            System.out.println("Stage.getScene().getWidth() " + stage.getScene().getWidth());
            System.out.println("Stage.getScene().getHeight() " + stage.getScene().getHeight());
        });
        
    }

    @FXML
    public void initialize() throws IOException {
        
//        if (loadLicenceScreen() == false) {
            loadMenuScreen();
//        }
//        licenceScreenController.loadLicenceScreen();
    }

}
