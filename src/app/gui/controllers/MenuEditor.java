package app.gui.controllers;

import app.ApexLibrary.Menu;
import app.ApexLibrary.editMode.MenuButtonHandlerEditor;
import app.ApexLibrary.PageLoader;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MenuEditor implements Initializable{

    @FXML
    HBox menuSelector;

    @FXML
    Button importMenuButton;

    @FXML
    GridPane itemGrid;

    @FXML
    Button addMenuButton;

    @FXML
    Button newItemButton;

    @FXML
    TabPane tabPane;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            setMenuSelector();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setMenuSelector() throws IOException {
        ArrayList<Menu> menus = new Menu("pizza").getAllMenus();

        if(menus.size() != 0) {

            for (int i = 0; i < menus.size(); i++) {
                Menu menu = menus.get(i);
                String menuName = menu.getName();

                Button menuButton = new Button(menuName);
                MenuButtonHandlerEditor action = new MenuButtonHandlerEditor(menu, menuSelector, itemGrid, tabPane);
                menuButton.setOnAction(action);
                menuButton.setPrefSize(100, 60);
                menuSelector.getChildren().add(menuButton);
            }
        }

    }



    @FXML
    public void importMenuButtonEvent(){ }

    @FXML
    public void addMenuButtonHandler(){
        Stage stage = new Stage();
        PageLoader loader = new PageLoader();
        try {
            loader.loadPage("NewMenu.fxml", stage, "New Menu", 400.0, 300.0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void createItemButton(){
        Stage stage = new Stage();
        PageLoader loader = new PageLoader();
        try {
            loader.loadPage("NewItem.fxml", stage, "New Item", 400.0, 500.0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
