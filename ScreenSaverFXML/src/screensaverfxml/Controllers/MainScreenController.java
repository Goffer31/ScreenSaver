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
    
    private void loadMenuScreen() throws IOException {
       FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/screensaverfxml/fxmlConfig/MenuScreen.fxml"));
       Pane menuPane = loader.load();
//       menuPane.getStylesheets().add(getClass().getResource("/styles/StyleSheet.css").toExternalForm());
//        ("/styles/StyleSheet.css");
       
       menuScreenController = loader.getController();
       menuScreenController.setMainController(this);
       setScreen(menuPane);
    }
    
    public void setScreen(Pane pane) {
        mainPane.getChildren().clear();
        mainPane.getChildren().add(pane);
    } 

    @FXML
    public void initialize() throws IOException {
        loadMenuScreen();
    }
    
}
