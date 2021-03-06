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
import javafx.stage.StageStyle;

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
    ScreenSaverFXML screenSaverFXML;
    private LicenceScreenController licenceScreenController;
    private SystemCheck systemCheck;

    public MainScreenController() {
        this.valueContainer = new ValueContainer();
    }

    public void loadLicenceScreen() throws IOException {
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
    }

    private void loadMenuScreen() throws IOException {
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
            return "xChecker: " + xChecker + "(offset " + xCheckerOffset + ")" + ", yChecker: " + yChecker + "(offset " + yCheckerOffset + ")";
        }

        public void calculateXoffset(double sceneWidth) {
            if (xCheckerOffset <= 0) {
                xCheckerOffset = xChecker - sceneWidth;
            }
        }

        public void calculateYoffset(double sceneHeight) {
            if (yCheckerOffset <= 0) {
                yCheckerOffset = yChecker - sceneHeight;
            }
        }
    }

    public void setStage(Stage stage) {

        this.stage = stage;

        if (menuScreenController == null) {
            return;
        }

        stage.show();

        menuScreenController.setValueContainer(valueContainer);

        stage.widthProperty().addListener((obs, oldVal, newVal) -> {
            valueContainer.xChecker = newVal.doubleValue();

            valueContainer.calculateXoffset(stage.getScene().getWidth());
            if (valueContainer.xChecker != 0 && valueContainer.yChecker != 0) {
                menuScreenController.resizeImageInsideWindow();
            }
            stage.getScene().getWidth();
        });

        stage.heightProperty().addListener((obs, oldVal, newVal) -> {
            valueContainer.yChecker = newVal.doubleValue();

            valueContainer.calculateYoffset(stage.getScene().getHeight());
            if (valueContainer.xChecker != 0 && valueContainer.yChecker != 0) {
                menuScreenController.resizeImageInsideWindow();
            }
            stage.getScene().getWidth();

        });

    }

    public void setScreenSaverFXML(ScreenSaverFXML delegator) {
        screenSaverFXML = delegator;
    }

    @FXML
    public void initialize() throws IOException {

    }

    public void loadScreens() throws IOException {
        if (SystemCheck.trialCheck(SystemCheck.getOperatingSystemType())) {
            loadMenuScreen();
            screenSaverFXML.requestSetStage(this);
            stage.setMaxHeight(0);
            stage.setMaxHeight(Double.MAX_VALUE);
            stage.setMaxWidth(0);
            stage.setMaxWidth(Double.MAX_VALUE);
            menuScreenController.resizeImageInsideWindow();
        } else {
            loadLicenceScreen();
        }
    }

}
