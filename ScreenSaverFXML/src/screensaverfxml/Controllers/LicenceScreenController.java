/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screensaverfxml.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class LicenceScreenController implements Initializable {

    LicenceScreenController licenceScreenController = this;

    @FXML
    private AnchorPane licenceScreenPane;

    public void loadLicenceScreen() throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/screensaverfxml/fxmlConfig/LicenceScreen.fxml"));
        licenceScreenPane = loader.load();
        licenceScreenController = loader.getController();
        setScreen(licenceScreenPane);

    }

    public void setScreen(AnchorPane pane) {
        licenceScreenPane.getChildren().clear();
        licenceScreenPane.getChildren().add(pane);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    public void exit() {
        Platform.exit();
    }

}
