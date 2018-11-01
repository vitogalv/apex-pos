package app.ApexLibrary;


import com.eclipsesource.json.JsonObject;

import java.util.ArrayList;

/**
 * Created by Vito on 12/24/2017.
 */

public class Alcohol extends Item {

    public Alcohol(){
        this._name = "";
        this._price = 0.0;
        this._descriptors = new ArrayList<>();
    }

    public Alcohol(String name, double price){
        this._name = name;
        this._price = Math.abs(price);
    }

    public Alcohol(JsonObject json){
        this._name = json.get("name").toString();
        this._price = new Double(json.get("price").toString());
    }

    @Override
    public boolean isFood() {
        return false;
    }
}
