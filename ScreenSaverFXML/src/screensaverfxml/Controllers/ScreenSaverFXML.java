/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screensaverfxml.Controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author root
 */
public class ScreenSaverFXML extends Application {
    
    LicenceScreenController licenceScreenController;
    MainScreenController mainScreenController;
    Stage stage;
    public void requestSetStage(MainScreenController mainScreenController)
    {
        mainScreenController.setStage(stage);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
//        
//        if(true){
//            licenceScreenController.loadLicenceScreen();
//        }
        
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/screensaverfxml/fxmlConfig/MainScreen.fxml"));
        Pane mainPane = loader.load();

        Scene scene = new Scene(mainPane, 1000, 700);
//        scene.getStylesheets().add(getClass().getResource("mainStyleSheet.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Photo Box");
        primaryStage.getIcons().add(new Image("resourcePackage/favicon-96x96.png"));
        stage = primaryStage;
        mainScreenController = loader.getController();
//        mainScreenController.setStage(primaryStage);
        mainScreenController.setScreenSaverFXML(this);
        mainScreenController.loadScreens();
        

//        if (mainScreenController.loadLicenceScreen() == true) {
//            primaryStage.show();
//        } else {
//            primaryStage.hide();
//        }
       
        scene.getRoot().requestFocus();
       
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
