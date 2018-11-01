package app.gui.controllers;

import app.ApexLibrary.Menu;
import app.ApexLibrary.PageLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class NewMenu {

    @FXML
    TextField menuText;

    @FXML
    Button emptyMenuButton;

    @FXML
    Button addItemButton;

    public void addButtonHandler(){
        try {
            writeMenu();
        } catch (IOException e) {
            e.printStackTrace();
        }
        PageLoader loader = new PageLoader();
        Stage stage = (Stage) addItemButton.getScene().getWindow();
        try {
            loader.loadPage("NewItem.fxml", stage, "New Item", 400.0, 500.0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void emptyMenuButtonHandler(){
        try {
            writeMenu();
        } catch (IOException e) {
            e.printStackTrace();
        }
        PageLoader loader = new PageLoader();
        Stage stage = (Stage) addItemButton.getScene().getWindow();
        try {
            loader.loadPage("MenuEditor.fxml", stage, "Menu Editor", 800.0, 600.0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeMenu() throws IOException {
        String menuName = menuText.getText();
        Menu menu = new Menu(menuName);
        menu.writeToFile();
    }
}
