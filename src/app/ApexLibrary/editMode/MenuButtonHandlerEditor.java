package app.ApexLibrary.editMode;

import app.ApexLibrary.Menu;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.util.ArrayList;

public class MenuButtonHandlerEditor implements EventHandler {

    private HBox _menuSelector;
    private GridPane _itemGrid;
    private app.ApexLibrary.Menu _menu;
    private TabPane _tabPane;

    public MenuButtonHandlerEditor(app.ApexLibrary.Menu menu, HBox menuSelector, GridPane itemGrid, TabPane tabPane){
        this._menu = menu;
        this._menuSelector = menuSelector;
        this._itemGrid = itemGrid;
        this._tabPane = tabPane;
    }

    @Override
    public void handle(Event event) {
        ArrayList<app.ApexLibrary.Menu> menus = this._menu.getSubMenus();

        for(int i = 0; i < menus.size(); i++){
            Menu menu = menus.get(i);
            double gridWidth = this._itemGrid.getWidth();
            double gridHeight = this._itemGrid.getHeight();

            GridPane itemGrid = new GridPane();
            itemGrid.setPrefSize(gridWidth, gridHeight);
            if(i == 0){
                menu.populateGrid(this._itemGrid);
            }else {
                menu.populateGrid(itemGrid);
                AnchorPane anchor = new AnchorPane(itemGrid);

                ScrollPane scroll = new ScrollPane(anchor);

                Tab tab = new Tab(menu.getName(), scroll);

                _tabPane.getTabs().add(tab);
            }
        }

    }


}
