package app.ApexLibrary;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;

import java.util.ArrayList;

/**
 * Created by Vito on 12/24/2017.
 */

public class Food extends Item {

    private ArrayList<String> _allergens;

    public Food(){
        this._name = "";
        this._price = 0.0;
        this._allergens = new ArrayList<>();
        this._descriptors = new ArrayList<>();
    }

    public Food(String name, Double price){
        this._name = name;
        this._price = Math.abs(price);
        this._allergens = new ArrayList<>();
        this._descriptors = new ArrayList<>();
    }

    public Food(JsonObject json){

        if(json != null) {
            this._name = json.get("name").toString();
            this._price = json.get("price").asDouble();

            this._descriptors = new ArrayList<>();
            JsonArray descriptors = json.get("descriptors").asArray();
            for (int i = 0; i < descriptors.size(); i++) {
                String descriptorString = descriptors.get(i).toString().replace("\"", "");
                this._descriptors.add(descriptorString);
            }

            this._allergens = new ArrayList<>();
            JsonArray allergens = json.get("allergens").asArray();

            for (int i = 0; i < allergens.size(); i++) {
                String allergen = allergens.get(i).toString().replace("\"", "");
                this._allergens.add(allergen);
            }
        }else{
            this._name = "";
            this._price = 0.0;
            this._allergens = new ArrayList<>();
            this._descriptors = new ArrayList<>();
        }
    }




    //Getters
    public ArrayList<String> getAllergens(){return this._allergens;}

    public boolean containsAllergen(String allergen){
        String editedAllergen = allergen.replace(" ", "").toLowerCase();
        ArrayList<String> allergens = this.getAllergens();
        return allergens.contains(editedAllergen);
    }

    public JsonArray getAllergenJson(){
        JsonArray json = new JsonArray();

        for(int i = 0; i < this._allergens.size(); i++){
            String allergen = this._allergens.get(i);
            json.add(allergen);
        }

        return json;
    }


    @Override
    public JsonObject getJson(){
        JsonObject json = super.getJson();

        JsonArray allergens = getAllergenJson();
        json.add("allergens", allergens);

        return json;
    }

    @Override
    public boolean isFood() {
        return true;
    }


    //Setters
    public void addAllergen(String allergen){

        if(allergen != null) {

            String editedAllergen = allergen.replace(" ", "").toLowerCase();

            if(!this._allergens.contains(editedAllergen)) {

                this._allergens.add(editedAllergen);

            }
        }
    }
}
