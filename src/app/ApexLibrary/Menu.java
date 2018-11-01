package app.ApexLibrary;

import app.ApexLibrary.editMode.ItemButtonHandlerEditor;
import com.eclipsesource.json.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by Vito on 12/28/2017.
 */


public class Menu {

    private String _menuName;
    private ArrayList<Food> _food;
    private ArrayList<Alcohol> _drinks;

    public Menu(String n){
        this._menuName = n;
        this._food = new ArrayList<>();
        this._drinks = new ArrayList<>();
    }

    public Menu(JsonObject json){

        if(!json.isNull()) {
            this._menuName = json.get("name").toString().replace("\"", "");

            ArrayList<Food> foods = new ArrayList<>();
            JsonObject foodListJson = json.get("food").asObject();

            for (JsonObject.Member value : foodListJson) {
                JsonObject foodObject = value.getValue().asObject();
                Food f = new Food(foodObject);
                foods.add(f);
            }

            JsonObject alcoholList = json.get("alcohol").asObject();
            ArrayList<Alcohol> aList = new ArrayList<>();

            for (JsonObject.Member value : alcoholList) {
                JsonObject aObject = value.getValue().asObject();
                Alcohol a = new Alcohol(aObject);
                aList.add(a);
            }

            this._food = foods;
            this._drinks = aList;
        }else{
            this._menuName = "";
            this._food = new ArrayList<>();
            this._drinks = new ArrayList<>();
        }
    }



    //Getters
    public ArrayList<Food> getFood(){return this._food;}
    public ArrayList<Alcohol> getDrinks(){return this._drinks;}
    public String getName(){ return this._menuName; }

    public Item getItem(String item){
        ArrayList<Item> allItems = getAllItems();
        int size = allItems.size();

        if(size > 0) {
            for (int i = 0; i < size; i++) {
                Item it = allItems.get(i);
                String name = it.getName();

                if(name.equals(item)){
                    return it;
                }
            }
        }

        return null;
    }

    public JsonObject getJson(){
        JsonObject json = new JsonObject();

        json.add("name", this._menuName);

        JsonObject food = new JsonObject();

        if(this._food.size() != 0) {
            for (int i = 0; i < this._food.size(); i++) {
                Food f = this._food.get(i);
                JsonObject fJson = f.getJson();
                String name = f.getName();
                food.add(name, fJson);
            }
        }
        json.add("food", food);

        JsonObject alcohol = new JsonObject();

        if(this._drinks.size() != 0) {
            for (int i = 0; i < this._drinks.size(); i++) {
                Alcohol a = this._drinks.get(i);
                JsonObject drinkJson = a.getJson();
                String name = a.getName();
                alcohol.add(name, drinkJson);
            }
        }
        json.add("alcohol", alcohol);

        return json;
    }

    public String getFileName(){

        String noSpaces = this._menuName.replace(" ", "");
        String fileName = noSpaces.toLowerCase();

        return "C:\\Users\\Vito\\eclipse-workspace\\Apex\\src\\app\\appData\\Menus\\" + fileName + ".json";
    }

    /*
    this will return a list of all the descriptors used by all the items in the menu
     */
    public ArrayList<String> getAllDescriptors(){
        ArrayList<String> descriptors = new ArrayList<>();
        ArrayList<Item> allItems = getAllItems();

        for(int i = 0; i < allItems.size(); i++){
            Item item = allItems.get(i);
            ArrayList<String> descriptorList = item.getDescriptors();

            for(int j = 0; j < descriptorList.size(); j++){
                String descriptor = descriptorList.get(j);

                if(!descriptors.contains(descriptor)){
                    descriptors.add(descriptor);
                }
            }

        }
        return descriptors;
    }

    public ArrayList<Menu> getSubMenus() {
        ArrayList<Menu> subs = new ArrayList<>();
        ArrayList<String> descriptors = getAllDescriptors();

        Menu other = new Menu("other");

        for (int i = 0; i < descriptors.size(); i++) {
            String descriptor = descriptors.get(i);
            ArrayList<Item> matches = getItemsWithDescriptor(descriptor);

            Menu menu = new Menu(descriptor);
            if (matches.size() <= 3) {
                menu = other;
            }

            for (int j = 0; j < matches.size(); j++) {
                Item it = matches.get(j);
                menu.addItem(it);
            }

            if(menu != other) {
                subs.add(menu);
            }
        }

        subs.add(other);

        return subs;
    }


    public ArrayList<Item> getAllItems(){
        ArrayList<Item> items = new ArrayList<>();

        for(int i = 0; i < this._food.size(); i++){
            items.add(this._food.get(i));
        }
        for(int i = 0; i < this._drinks.size(); i++){
            items.add(this._drinks.get(i));
        }

        return items;
    }

    public ArrayList<Item> getItemsWithDescriptor(String descriptor){
        if(descriptor != null) {
            ArrayList<Item> items = new ArrayList<>();

            ArrayList<Item> allItems = getAllItems();
            for (int i = 0; i < allItems.size(); i++) {
                Item item = allItems.get(i);
                ArrayList<String> descriptors = item.getDescriptors();

                for (int j = 0; j < descriptors.size(); i++) {
                    String itemDescriptor = descriptors.get(j);

                    if (itemDescriptor.equals(descriptor)) {
                        items.add(item);
                    }
                }
            }

            return items;
        }else{
            return new ArrayList<Item>();
        }
    }

    public ArrayList<Menu> getAllMenus() throws IOException {
        ArrayList<Menu> menus = new ArrayList<>();

        File rosterFile = new File("C:\\Users\\Vito\\eclipse-workspace\\Apex\\src\\app\\appData\\Menus\\menuRoster.json");
        FileReader fileReader = new FileReader(rosterFile);
        JsonArray rosterArray = Json.parse(fileReader).asArray();

        ArrayList<String> menuPaths = new ArrayList<>();

        if(rosterArray.size() != 0) {
            for (int i = 0; i < rosterArray.size(); i++) {
                String filePath = rosterArray.get(i).toString();
                menuPaths.add(filePath);
            }
        }else{
            fileReader.close();
            return menus;
        }

        fileReader.close();

            for (int i = 0; i < menuPaths.size(); i++) {
                String menuPath = menuPaths.get(i).replace("\"", "");
                File menuFile = new File(menuPath);
                FileReader inputReader = new FileReader(menuFile);
                JsonValue value = Json.parse(inputReader);
                JsonObject menuObject = value.asObject();
                Menu menu = new Menu(menuObject);
                menus.add(menu);
            }

        return menus;
    }





    //Setters
    public void addItem(Item item){
        if(item != null) {
            String classType = item.getClass().getName();

            if (classType.equals("app.ApexLibrary.Alcohol")) {
                Alcohol a = (Alcohol) item;
                this._drinks.add(a);
            } else if (classType.equals("app.ApexLibrary.Food")) {
                Food f = (Food) item;
                this._food.add(f);
            }
        }
    }

    public void setName(String name){ this._menuName = name; }
    public void writeToFile() throws IOException {
        String json = getJson().toString();

        String fileName = getFileName();
        File file = new File(fileName);

        if(!file.exists()){
                file.createNewFile();
        }

        FileOutputStream fos = new FileOutputStream(file);
            fos.write(json.getBytes());
            fos.close();


        File roster = new File("C:\\Users\\Vito\\eclipse-workspace\\Apex\\src\\app\\appData\\Menus\\menuRoster.json");
        FileReader rosterReader = new FileReader(roster);
        JsonArray rosterJson = Json.parse(rosterReader).asArray();
        rosterJson.add(getFileName());

        FileOutputStream rosterOutput = new FileOutputStream(file);
        rosterOutput.write(rosterJson.toString().getBytes());

    }


    public void populateGrid(GridPane itemGrid){

        ArrayList<Food> food = this.getFood();
        ArrayList<Alcohol> alcohol = this.getDrinks();
        int numberOfItems = food.size() + alcohol.size();

        double gridHeight;
        if(numberOfItems > 25) {
            int rows = numberOfItems / 5 + 1;
            gridHeight = rows * 83;
            itemGrid.setPrefHeight(gridHeight);
        }

        int rowIndex = 0;
        int columnIndex = 0;

        if(food.size() != 0) {
            for (int i = 0; i < food.size(); i++) {
                Food f = food.get(i);
                String name = f.getName();
                Button itemButton = new Button(name);
                ItemButtonHandlerEditor action = new ItemButtonHandlerEditor(f);
                itemButton.setOnAction(action);
                itemButton.setPrefSize(140.0, 75.0);
                itemGrid.add(itemButton, columnIndex, rowIndex);

                boolean rowComplete = (i % 5) == 4;
                if (rowComplete || (i == 4)) {
                    rowIndex++;
                    itemGrid.addRow(rowIndex, new Label(""));
                }

                if (columnIndex == 4) {
                    columnIndex = 0;
                } else {
                    columnIndex++;
                }

            }
        }

        if(alcohol.size() != 0) {
            for (int i = 0; i < alcohol.size(); i++) {
                Alcohol a = alcohol.get(i);
                String name = a.getName();
                Button itemButton = new Button(name);
                ItemButtonHandlerEditor action = new ItemButtonHandlerEditor(a);
                itemButton.setOnAction(action);
                itemGrid.add(itemButton, columnIndex, rowIndex);

                if (columnIndex == 4) {
                    columnIndex = 0;
                } else {
                    columnIndex++;
                }

                if (rowIndex >= 4) {
                    rowIndex++;
                    itemGrid.addRow(rowIndex, new Label(""));
                }
            }
        }
    }
}
