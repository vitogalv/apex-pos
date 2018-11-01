package app.gui.controllers;

import app.ApexLibrary.Food;
import app.ApexLibrary.Menu;
import com.eclipsesource.json.JsonObject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.*;


public class ImportMenu {

    private String filename;

    @FXML
    TextField menuId_input;

    @FXML
    TextField documentPath_input;

    @FXML
    Button importButton;

    public Menu getMenu(){
        Menu menu = new Menu(menuId_input.getText());
        try {
            FileReader fileReader = new FileReader(documentPath_input.getText());
            BufferedReader reader = new BufferedReader(fileReader);

            String line = reader.readLine();

            while(line != null){

                Food f = new Food();

                String[] oneItem = line.split(",");
                String name = oneItem[0];
                double price = Double.parseDouble(oneItem[1]);
                f.setName(name);
                f.setPrice(price);

                menu.addItem(f);

                line = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return menu;
    }

    public void writeJson(){
        JsonObject json = getMenu().getJson();
        filename = getMenu().getFileName();
        File newMenuFile = new File(filename);

        if(!newMenuFile.exists()){
            try {
                newMenuFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileOutputStream fos = new FileOutputStream(newMenuFile);
            String jsonString = json.toString();
            fos.write(jsonString.getBytes());

            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addFileToRoster(){
        File rosterFile = new File("C:\\Users\\Vito\\eclipse-workspace\\Apex\\src\\app\\appData\\Menus\\menuRoster.txt");
        try {

            FileOutputStream fos = new FileOutputStream(rosterFile, true);
            fos.write((filename + "\n").getBytes());

            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void importButtonListener(){
        writeJson();
        addFileToRoster();

    }
}
