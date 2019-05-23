/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screensaverfxml.Controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class MainScreenController {
    
    @FXML
    private Pane mainPane;
    
    private void loadMenuScreen() throws IOException {
       FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/screensaverfxml/fxmlConfig/MenuScreen.fxml"));
       Pane menuPane = loader.load();
       MenuScreenController menuScreenController = loader.getController();
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
