/*

FIX TAX RATE IN "getTax()"

 */
package app.ApexLibrary.serverMode;

import app.ApexLibrary.Alcohol;
import app.ApexLibrary.Food;
import app.ApexLibrary.Item;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Vito on 12/24/2017.
 */

public class Ticket {

    private String _tableId;
    private Server _server;
    private Date _creationTime;
    private double _tip;
    private ArrayList<Food> _foodOrder;
    private ArrayList<Alcohol> _drinkOrder;
    private int _headCount;





    public Ticket(){
        this._tableId = "";
        this._server = new Server();
        this._creationTime = new Date();
        this._tip = 0.0;
        this._foodOrder = new ArrayList<>();
        this._drinkOrder = new ArrayList<>();
        this._headCount = 0;
    }

    public Ticket(String table, Server server){
        this._tableId = table;
        this._server = server;
        this._creationTime = new Date();
        this._tip = 0.0;
        this._foodOrder = new ArrayList<>();
        this._drinkOrder = new ArrayList<>();
        this._headCount = 0;
    }





    //Getters
    public String getTableId(){return this._tableId;}
    public Server getServer(){return this._server;}
    public Date getCreationTime(){return this._creationTime;}
    public Double getTip(){return this._tip;}
    public ArrayList<Food> getFoodOrder(){return this._foodOrder;}
    public ArrayList<Alcohol> getDrinkOrder(){return this._drinkOrder;}
    public int getHeadCount(){ return this._headCount; }

    public double getFoodTotal(){
        double total = 0.0;

        for(int i = 0; i < this._foodOrder.size(); i++){
            Food f = this._foodOrder.get(i);
            total += f.getPrice();
        }

        return total;
    }

    public double getDrinkTotal(){
        double total = 0.0;

        if(this._drinkOrder.size() > 0) {
            for (int i = 0; i < this._drinkOrder.size(); i++) {
                Alcohol f = this._drinkOrder.get(i);
                total += f.getPrice();
            }
        }

        return total;
    }

    public double getTotalCharge(){
        return getDrinkTotal() + getFoodTotal() + this._tip + getTax();
    }

    public double getTax(){
        double tax = (getDrinkTotal() + getFoodTotal()) * .08;
        return tax;
    }

    public long getElapsedTime(){
        return new Date().getDate() - this._creationTime.getDate();
    }

    public String getReceipt(){
        String receipt = "";

        return receipt;
    }


    public JsonObject getJson(){
        JsonObject json = new JsonObject();

        json.add("ticketId", this._tableId);
        json.add("serverId", this._server.getId());
        json.add("creationTime", this._creationTime.getTime());
        json.add("tip", this._tip);
        json.add("headCount", this._headCount);

        JsonArray foodArray = new JsonArray();
        for(int i = 0; i < this._foodOrder.size(); i++){
            Food  f = this._foodOrder.get(i);
            JsonObject foodJson = f.getJson();
            foodArray.add(foodJson);
        }
        json.add("foodOrder", foodArray);

        JsonArray drinkArray = new JsonArray();
        for(int i = 0; i < this._drinkOrder.size(); i++){
            Alcohol a = this._drinkOrder.get(i);
            JsonObject drinkJson = a.getJson();
            drinkArray.add(drinkJson);
        }
        json.add("drinkOrder", drinkArray);

        return json;
    }


    //Setters
    public void setTableId(String id){this._tableId = id;}
    public void setServer(Server s){this._server = s;}
    public void setTip(double t){this._tip = t;}
    public void setHeadCount(int count){ this._headCount = count; }

    public void addItem(Item item){
        if(item != null){
            String classType = item.getClass().getName();

            if (classType.equals("app.ApexLibrary.Alcohol")) {
                Alcohol a = (Alcohol) item;
                this._drinkOrder.add(a);
            } else if (classType.equals("app.ApexLibrary.Food")) {
                Food f = (Food) item;
                this._foodOrder.add(f);
            }
        }
    }
}
