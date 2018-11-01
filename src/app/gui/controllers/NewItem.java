package app.gui.controllers;

import app.ApexLibrary.Alcohol;
import app.ApexLibrary.Food;
import app.ApexLibrary.Item;
import app.ApexLibrary.PageLoader;
import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import app.ApexLibrary.Menu;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class NewItem implements Initializable {

    @FXML
    TextField nameText;

    @FXML
    TextField priceText;

    @FXML
    ComboBox menuSelectorBox;

    @FXML
    Button newMenuButton;

    @FXML
    TextArea descriptorTextArea;

    @FXML
    Button saveItemButton;

    @FXML
    RadioButton foodRadio;

    @FXML
    RadioButton alcoholRadio;

    private Stage _defaultStage;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            setMenuSelectorBox();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setDefaultStage(Stage stage){
        this._defaultStage = stage;
    }

    public void setMenuSelectorBox() throws IOException {
        ArrayList<Menu> allMenus = new Menu("m").getAllMenus();

        if(allMenus.size() != 0){
            for(int i = 0; i < allMenus.size(); i++){
                Menu menu = allMenus.get(i);
                String name = menu.getName();
                menuSelectorBox.getItems().add(name);
            }
        }
    }


    public ArrayList<String> getDescriptors(){
        ArrayList<String> descriptors = new ArrayList<>();

        String allDescriptors = descriptorTextArea.getText();
        String[] split = allDescriptors.split(",");

        for(String descriptor : split){
            String editedDescriptor = descriptor.replace(" ", "").toLowerCase();
            descriptors.add(editedDescriptor);
        }

        return descriptors;
    }

    @FXML
    public void newMenuButtonHandler(){
        PageLoader loader = new PageLoader();
        try {
            loader.loadPage("NewMenu.fxml", (Stage)nameText.getScene().getWindow(), "New Menu", 400.0, 300.0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void saveItemButtonHandler(){

        String name = nameText.getText();
        double price = new Double(priceText.getText());
        String menuLocation = menuSelectorBox.getValue().toString().replace(" ", "").toLowerCase();
        ArrayList<String> descriptors = getDescriptors();
        File menuFile = new File("C:\\Users\\Vito\\eclipse-workspace\\Apex\\src\\app\\appData\\Menus\\" + menuLocation + ".json");
        JsonObject menuObject = new JsonObject();

        try {
            FileReader reader = new FileReader(menuFile);
            menuObject = Json.parse(reader).asObject();
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(foodRadio.isSelected()){
            Food f = new Food();
            f.setName(name);
            f.setPrice(price);
            f.setDescriptors(descriptors);
            JsonObject foodJson = f.getJson();
            JsonObject foodList = menuObject.get("food").asObject();
            foodList.add(name, foodJson);
        }else if(alcoholRadio.isSelected()){
            Alcohol a = new Alcohol();
            a.setName(name);
            a.setPrice(price);
            a.setDescriptors(descriptors);
            JsonObject alcoholJson = a.getJson();
            JsonObject alcoholList = menuObject.get("alcohol").asObject();
            alcoholList.add(name, alcoholJson);
        }

        try {
            FileOutputStream  fos = new FileOutputStream(menuFile);
            fos.write(menuObject.toString().getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Stage stage = (Stage)this.alcoholRadio.getScene().getWindow();
        stage.close();
    }


}
