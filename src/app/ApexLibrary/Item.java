package app.ApexLibrary;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;

import java.util.ArrayList;

/**
 * Created by Vito on 12/23/2017.
 */

public abstract class Item {

    protected String _name;
    protected Double _price;
    protected ArrayList<String> _descriptors;

    //Getters
    public String getName(){return this._name;}
    public Double getPrice(){return this._price;}
    public ArrayList<String> getDescriptors(){ return this._descriptors; }

    public JsonObject getJson(){
        JsonObject json = new JsonObject();

        json.add("name", this._name);
        json.add("price", this._price);

        JsonArray  descriptorArray = getDescriptorArray();
        json.add("descriptors", descriptorArray);

        return json;
    }

    public boolean hasDescriptor(String descriptor){

        for(int i = 0; i < this._descriptors.size(); i++){
            String d = this._descriptors.get(i);
            String editedDescriptor = descriptor.replace("\"", "").replace(" ", "").toLowerCase();
            if(editedDescriptor.equals(d)){
                return true;
            }
        }

        return false;
    }


    public JsonArray getDescriptorArray(){
        JsonArray descriptorArray = new JsonArray();
        for(int i = 0; i < this._descriptors.size(); i++){
            String descriptor = this._descriptors.get(i);
            descriptorArray.add(descriptor);
        }

        return descriptorArray;
    }

    public abstract boolean isFood();


    //setters
    public void setName(String name){this._name = name;}
    public void setPrice(Double price){ this._price = Math.abs(price);}

    public void addDescriptor(String descriptor){

        String editedDescriptors = descriptor.replace(" ", "").toLowerCase();

        if(descriptor != null && !this._descriptors.contains(editedDescriptors)) {
            this._descriptors.add(editedDescriptors);
        }
    }

    public void setDescriptors(ArrayList<String> descriptors){ this._descriptors = descriptors; }

}
