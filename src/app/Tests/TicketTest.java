package app.Tests;

import app.ApexLibrary.Alcohol;
import app.ApexLibrary.Food;
import app.ApexLibrary.Item;
import app.ApexLibrary.serverMode.Server;
import app.ApexLibrary.serverMode.Ticket;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class TicketTest {

    /**
     * The following Tickets will contain different variables to test for
     */
    private Ticket _idealTicket;
    private Ticket _badFormatTicket;
    private Ticket _emptyFieldsTicket;
    private Ticket _nullTicket;
    private Ticket _negativeDoubleInputs;
    private Item[] _items;

    private Server _server;

    @Before
    public void setUp(){
        setServer();
        setItems();
        setIdealTicket();
        setBadFormatTicket();
        setNullTicket();
        setNegativeDoubleInputs();
    }

    public void setIdealTicket(){
        _idealTicket = new Ticket("14", _server);
        _idealTicket.setTip(20.33);
        _idealTicket.setHeadCount(4);

    }

    public void setBadFormatTicket(){

    }

    public void setNullTicket(){

    }

    public void setNegativeDoubleInputs(){

    }

    public void setItems(){

    }

    public void setServer(){
        _server = new Server();
        _server.setSystemId(6197);
        _server.setFirstName("Vito");
        _server.setLastName("Galvez");
        _server.setDateOfBirth("07/05/2000");
        _server.setDateOfHire("06/12/2016");
    }
    @Test
    public void testEmptyConstructor(){
        Ticket ticket = new Ticket();

        String id = ticket.getTableId();
        Server server = ticket.getServer();
        double tip = ticket.getTip();
        ArrayList<Food> foodOrder = ticket.getFoodOrder();
        ArrayList<Alcohol> alcoholOrder = ticket.getDrinkOrder();
        int headCount = ticket.getHeadCount();

        assertEquals("", id);
        assertEquals(new Server(), server);
        assertEquals(0.0, tip, 0.001);
        assertEquals(new ArrayList<Food>(), foodOrder);
        assertEquals(new ArrayList<Alcohol>(), alcoholOrder);
        assertEquals(0, headCount);
    }

    @Test
    public void testConstructorWithParams(){
        Server vito = new Server();
        Ticket table4 = new Ticket("4", vito);

        Server ticketServer = table4.getServer();
        String tableID = table4.getTableId();
        double tip = table4.getTip();
        ArrayList<Food> foodOrder = table4.getFoodOrder();
        ArrayList<Alcohol> alcoholOrder = table4.getDrinkOrder();
        int headCount = table4.getHeadCount();

        assertEquals("4", tableID);
        assertEquals(vito, ticketServer);
        assertEquals(0.0, tip, 0.001);
        assertEquals(new ArrayList<Food>(), foodOrder);
        assertEquals(new ArrayList<Alcohol>(), alcoholOrder);
        assertEquals(0, headCount);
    }

    @Test
    public void testGetFoodTotal(){
        Ticket ticket = new Ticket();

        Food hotDog = new Food("hotDog", 5.00);
        Food chicken = new Food("chicken", 8.75);
        Food freeFood = new Food("freeFood", 0.00);
        Food fries = new Food("fries", 3.50);

        ticket.addItem(hotDog);
        ticket.addItem(chicken);
        ticket.addItem(freeFood);
        ticket.addItem(fries);

        double foodTotal = ticket.getFoodTotal();

        assertEquals(17.25, foodTotal, 0.001);
    }

    @Test
    public void testGetFoodTotalNoFood(){
        Ticket ticket = new Ticket();
        double foodTotal = ticket.getFoodTotal();

        assertEquals(0.0, foodTotal, 0.001);
    }

    @Test
    public void testGetDrinkOrderTotal(){
        Ticket ticket = new Ticket();

        Alcohol martini, beer, wine, cocktail;
        martini = new Alcohol("martini", 12.00);
        beer = new Alcohol("beer", 3.50);
        wine = new Alcohol("wine", 7.50);
        cocktail = new Alcohol("cocktail", 5.00);

        ticket.addItem(martini);
        ticket.addItem(beer);
        ticket.addItem(wine);
        ticket.addItem(cocktail);

        double total = ticket.getDrinkTotal();

        assertEquals(28.0, total, 0.001);
    }

    @Test
    public void testGetDrinkTotalNoDrinks(){
        Ticket ticket = new Ticket();
        double total = ticket.getDrinkTotal();

        assertEquals(0.0, total, 0.001);
    }

    @Test
    public void testGetTotalCharge(){
        Ticket ticket = new Ticket();

        Alcohol martini, beer, wine, cocktail;
        martini = new Alcohol("martini", 12.00);
        beer = new Alcohol("beer", 3.50);
        wine = new Alcohol("wine", 7.50);
        cocktail = new Alcohol("cocktail", 5.00);

        ticket.addItem(martini);
        ticket.addItem(beer);
        ticket.addItem(wine);
        ticket.addItem(cocktail);

        Food hotDog = new Food("hotDog", 5.00);
        Food chicken = new Food("chicken", 8.75);
        Food freeFood = new Food("freeFood", 0.00);
        Food fries = new Food("fries", 3.50);

        ticket.addItem(hotDog);
        ticket.addItem(chicken);
        ticket.addItem(freeFood);
        ticket.addItem(fries);

        ticket.setTip(5.00);

        double total = ticket.getTotalCharge();
        assertEquals(50.53, total, 0.001);
    }

    @Test
    public void testGetJson(){
        Ticket ticket = new Ticket();
        ticket.setTableId("4");

        Server server = new Server();
        server.setFirstName("Vito");
        server.setLastName("galvez");
        server.setSystemId(6197);

        ticket.setServer(server);

        ticket.setTip(6.50);
        ticket.setHeadCount(5);

        Food fries = new Food("fries", 5.00);
        Food hotDog = new Food("fries", 6.75);
        Food hamburger = new Food("hamburger", 12.25);

        Alcohol martini = new Alcohol("martini", 13.00);
        Alcohol beer = new Alcohol("beer", 3.50);

        ticket.addItem(fries);
        ticket.addItem(hotDog);
        ticket.addItem(hamburger);
        ticket.addItem(martini);
        ticket.addItem(beer);

        JsonObject json = ticket.getJson();

        String ticketId = json.get("ticketId").asString().toString();
        int id = json.get("serverId").asInt();
        double tip = json.get("tip").asDouble();
        int headCount = json.get("headCount").asInt();

        JsonArray food = json.get("foodOrder").asArray();
        int foodSize = food.size();
        JsonObject foodObeject = food.get(0).asObject();

        JsonArray alcohol = json.get("drinkOrder").asArray();
        int drinkSizec = alcohol.size();


    }
}
