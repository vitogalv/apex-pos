package app.ApexLibrary.editMode;

import app.ApexLibrary.Alcohol;
import app.ApexLibrary.Food;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;

public class ItemButtonHandlerEditor implements EventHandler{

    private Food _food;
    private Alcohol _alcohol;

    public ItemButtonHandlerEditor(Food food){
        this._food = food;
    }

    public ItemButtonHandlerEditor(Alcohol alcohol){
        this._alcohol = alcohol;
    }

    @Override
    public void handle(Event event) {

    }
}
