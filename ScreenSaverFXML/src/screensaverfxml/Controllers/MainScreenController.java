/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screensaverfxml.Controllers;

import com.sun.javafx.binding.BindingHelperObserver;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

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

    public MainScreenController() {
        this.valueContainer = new ValueContainer();
    }
    
    private void loadMenuScreen() throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/screensaverfxml/fxmlConfig/MenuScreen.fxml"));
        Pane menuPane = loader.load();

        menuScreenController = loader.getController();
        menuScreenController.setMainController(this);
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
        menuScreenController.setValueContainer(valueContainer);
        
        
        stage.widthProperty().addListener((obs, oldVal, newVal) -> {
            valueContainer.xChecker = newVal.doubleValue();
            
            
            stage.setFullScreen(true);
            
           
            
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
        
        //////////////////////////////////////////
        
//        if(stage.isFullScreen()) {
////            stage.getScene().getWidth();
//            menuScreenController.resizeImageInsideWindow(stage.getScene().getWidth(), stage.getScene().getHeight());
//            stage.getScene().getWidth();
//            
//        }
//        
//        stage.maximizedProperty().addListener((obs, oldVal, newVal) -> {
//            System.out.println("Maximized width Changed");
//            System.out.println("newVal: " + newVal);
//            
//            if(newVal || oldVal) {
//                menuScreenController.resizeImageInsideWindow(stage.getScene().getWidth(), stage.getScene().getHeight());
////                menuScreenController.resizeImageInsideWindow(0, 0);
////                stage.setMaximized(true);
//                
//            stage.getScene().getWidth();
//            }
//            
////            stage.setFullScreen(true);
////            stage.isFullScreen();
////            stage.sizeToScene();
////            stage.setFullScreen(true);
//            menuScreenController.resizeImageInsideWindow(stage.getScene().getWidth(), stage.getScene().getHeight());
//            stage.getScene().getWidth();
//            System.out.println("Stage.getScene().getWidth() " + stage.getScene().getWidth());
//        });
        
        

        
    }

    @FXML
    public void initialize() throws IOException {
        loadMenuScreen();
    }

}
