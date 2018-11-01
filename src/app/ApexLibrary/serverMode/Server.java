package app.ApexLibrary.serverMode;

import app.ApexLibrary.Employee;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;

import java.util.ArrayList;

/**
 * Created by Vito on 12/25/2017.
 */


/**
 * This class represents a server in a restaurant and is a subclass of Employee
 */

public class Server extends Employee {

    /**
     * The container for all the {@code Ticket} a
     */
    private ArrayList<Ticket> _tickets;

    public Server(){
        this._tickets = new ArrayList<>();
    }

    public ArrayList<Ticket> getTickets(){ return this._tickets; }

    @Override
    public JsonObject getJson(){
        JsonObject json = super.getJson();

        JsonArray ticketArray = new JsonArray();
        for(int i = 0; i < this._tickets.size(); i++){
            Ticket t = this._tickets.get(i);
            JsonObject ticketJson = t.getJson();
            ticketArray.add(ticketJson);
        }
        json.add("tickets", ticketArray);

        return json;
    }


    public void addTicket(Ticket ticket){ this._tickets.add(ticket); }



}
