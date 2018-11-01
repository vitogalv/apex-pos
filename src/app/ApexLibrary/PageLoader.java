package app.ApexLibrary;

import app.gui.controllers.ImportMenu;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class PageLoader {

    public void loadPage(String fxmlFile, Stage stage, String title, double width, double height) throws IOException {
        FXMLLoader loader = new FXMLLoader(ImportMenu.class.getResource(fxmlFile));
            Parent root = loader.load();
            Scene scene = new Scene(root, width, height);
            stage.setScene(scene);
            stage.setTitle(title);
            stage.show();

    }
}
