package app.Tests;


import app.ApexLibrary.Alcohol;
import app.ApexLibrary.Food;
import app.ApexLibrary.Item;
import app.ApexLibrary.Menu;
import com.eclipsesource.json.JsonObject;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class MenuTest {

    @Test
    public void testSimpleConstructor(){
        Menu menu = new Menu("Summer Menu");

        String name = menu.getName();
        ArrayList<Food> food = menu.getFood();
        ArrayList<Alcohol> drinks = menu.getDrinks();

        assertEquals("summermenu", name);
        assertEquals(0, food.size());
        assertEquals(0, drinks.size());

    }


    @Test
    public void testSimpleConstructorWithNull(){
        String menuName = null;
        Menu menu = new Menu(menuName);

        String name = menu.getName();
        ArrayList<Food> food = menu.getFood();
        ArrayList<Alcohol> drinks = menu.getDrinks();

        assertEquals(null, name);
        assertEquals(0, food.size());
        assertEquals(0, drinks.size());
    }

    @Test
    public void testJsonConstructor(){
        JsonObject json = new JsonObject();

        JsonObject food = new JsonObject();
        Food hotDog = new Food("hot dog", 2.50);
        Food chicken = new Food("chicken", 10.50);
        Food fries = new Food("fries", 3.75);
        food.add("hot dog", hotDog.getJson());
        food.add("chicken", chicken.getJson());
        food.add("fries", fries.getJson());

        JsonObject alcohol = new JsonObject();
        Alcohol martini = new Alcohol("martini", 7.50);
        Alcohol beer = new Alcohol("beer", 3.50);
        Alcohol wine = new Alcohol("wine", 8.00);
        alcohol.add("martini", martini.getJson());
        alcohol.add("beer", beer.getJson());
        alcohol.add("wine", wine.getJson());

        json.add("name", "summer menu");
        json.add("food", food);
        json.add("alcohol", alcohol);

        Menu menu = new Menu(json);

        String name = menu.getName();

        ArrayList<Food> foodList = menu.getFood();
        Food f1, f2, f3;
        f1 = foodList.get(0);
        f2 = foodList.get(1);
        f3 = foodList.get(2);

        ArrayList<Alcohol> alcoholList = menu.getDrinks();
        Alcohol a1, a2, a3;
        a1 = alcoholList.get(0);
        a2 = alcoholList.get(1);
        a3 = alcoholList.get(2);

        assertEquals("summer menu", name);

        assertEquals(3, foodList.size());
        assertEquals(3, alcoholList.size());

        assertEquals(hotDog, f1);
        assertEquals(chicken, f2);
        assertEquals(fries, f3);

        assertEquals(martini, a1);
        assertEquals(beer, a2);
        assertEquals(wine, a3);

    }

    @Test
    public void testJsonConstructorWithNull(){
        JsonObject json = null;
        Menu menu = new Menu(json);

        String name = menu.getName();
        ArrayList<Food> food = menu.getFood();
        ArrayList<Alcohol> drinks = menu.getDrinks();

        assertEquals("", name);
        assertEquals(0, food.size());
        assertEquals(0, drinks.size());
    }

    @Test
    public void testGetItem(){
        Menu menu = new Menu("summer menu");

        Food hotDog = new Food("hot dog", 5.00);
        Food chicken = new Food("chicken", 6.00);
        menu.addItem(hotDog);
        menu.addItem(chicken);

        Alcohol beer = new Alcohol("beer", 3.50);
        menu.addItem(beer);

        Food menuDog = (Food)menu.getItem("hot dog");
        Food menuChicken = (Food)menu.getItem("chicken");
        Alcohol menuBeer = (Alcohol)menu.getItem("beer");

        assertSame(hotDog, menuDog);
        assertSame(chicken, menuChicken);
        assertSame(beer, menuBeer);
    }

    @Test
    public void testGetItemNull(){
        Menu menu = new Menu("summer");
        Item it = menu.getItem("pizza");

        assertNull(it);
    }

    @Test
    public void testGetJsonShallow(){
        Menu menu = new Menu("summer");
        JsonObject json = menu.getJson();

        String name = json.get("name").asString().toString();
        JsonObject food = json.get("food").asObject();
        JsonObject drinks = json.get("alcohol").asObject();

        assertEquals("summer", name);
        assertEquals(0, food.size());
        assertEquals(0, drinks.size());
    }

    @Test
    public void testGetJsonDeep(){
        Menu menu = new Menu("winter");

        Food hotDog = new Food("hot dog", 5.00);
        Food chicken = new Food("chicken", 6.00);
        menu.addItem(hotDog);
        menu.addItem(chicken);

        Alcohol beer = new Alcohol("beer", 3.50);
        Alcohol wine = new Alcohol("wine", 10.00);
        menu.addItem(beer);
        menu.addItem(wine);

        JsonObject json = menu.getJson();

        String name = json.get("name").asString().toString();

        JsonObject food = json.get("food").asObject();

        JsonObject hotDogJson = food.get("hot dog").asObject();
        Food hotDogFromJson = new Food(hotDogJson);

        JsonObject chickenJson = food.get("chicken").asObject();
        Food chickenFromJson = new Food(chickenJson);

        int foodSize = food.size();

        JsonObject drinks = json.get("alcohol").asObject();

        JsonObject beerJson = drinks.get("beer").asObject();
        Alcohol beerFromJson = new Alcohol(beerJson);

        JsonObject wineJson = drinks.get("wine").asObject();
        Alcohol wineFromJson = new Alcohol(wineJson);

        int drinkSize = drinks.size();

        assertEquals("winter", name);

        assertEquals(hotDog, hotDogFromJson);
        assertEquals(chicken, chickenFromJson);

        assertEquals(beer, beerFromJson);
        assertEquals(wine, wineFromJson);

        assertEquals(2, foodSize);
        assertEquals(2, drinkSize);
    }

    @Test
    public void testGetJsonWithNull(){
        Menu menu = new Menu("summer");

        Food food = null;
        menu.addItem(food);

        Food chicken = new Food("chicken", 5.34);
        JsonObject expectedChickenObject = chicken.getJson();
        menu.addItem(chicken);

        Alcohol alcohol = null;
        menu.addItem(alcohol);

        JsonObject json = menu.getJson();

        String name = json.get("name").asString().toString();

        JsonObject foodObject = json.get("food").asObject();
        int foodSize = foodObject.size();
        JsonObject actualChickenObject = json.get("chicken").asObject();

        JsonObject alcoholObject = json.get("alcohol").asObject();
        int drinkSize = alcoholObject.size();

        assertEquals("summer", name);
        assertEquals(1, foodSize);
        assertEquals(0, drinkSize);

        assertEquals(expectedChickenObject, actualChickenObject);
    }

    @Test
    public void testGetFileName(){
        Menu menu = new Menu("Summer Menu");

        String filename = menu.getFileName();

        assertEquals("C:\\Users\\Vito\\eclipse-workspace\\Apex\\src\\app\\appData\\Menus\\summermenu.json", filename);

    }

    @Test
    public void testGetAllDescriptors(){
        Food chicken = new Food("chicken", 5.00);
        chicken.addDescriptor("healthy");
        chicken.addDescriptor("meat");
        chicken.addDescriptor("main course");

        Alcohol beer = new Alcohol("beer", 3.75);
        beer.addDescriptor("hoppy");
        beer.addDescriptor("cheap");
        beer.addDescriptor("local");

        Menu menu = new Menu("sample menu");
        menu.addItem(chicken);
        menu.addItem(beer);

        ArrayList<String> descriptors = menu.getAllDescriptors();
        int size = descriptors.size();

        assertEquals(6, size);

        assertTrue(descriptors.contains("healthy"));
        assertTrue(descriptors.contains("meat"));
        assertTrue(descriptors.contains("maincourse"));
        assertTrue(descriptors.contains("hoppy"));
        assertTrue(descriptors.contains("cheap"));
        assertTrue(descriptors.contains("local"));

        assertFalse(descriptors.contains("special"));
    }

    @Test
    public void testGetAllDescriptorsDNE(){
        Menu menu = new Menu("summer");

        ArrayList<String> descriptors = menu.getAllDescriptors();

        assertEquals(0, descriptors.size());
    }

    @Test
    public void testGetSubMenus(){
        Menu menu = new Menu("sample");

        Food hotDog = new Food("hot dog", 3.5);
        hotDog.addDescriptor("fresh");
        hotDog.addDescriptor("meat");
        hotDog.addDescriptor("protein");
        menu.addItem(hotDog);

        Food chicken = new Food("chicken", 5.00);
        chicken.addDescriptor("meat");
        chicken.addDescriptor("protein");
        menu.addItem(chicken);

        Food pizza = new Food("pizza", 10.00);
        pizza.addDescriptor("dairy");
        pizza.addDescriptor("cheese");
        menu.addItem(pizza);

        Food hamburger = new Food("hamburger", 5.50);
        hamburger.addDescriptor("meat");
        hamburger.addDescriptor("protein");
        menu.addItem(hamburger);

        Food summerSalad = new Food("summer salad", 8.00);
        summerSalad.addDescriptor("healthy");
        summerSalad.addDescriptor("vegitarian");
        summerSalad.addDescriptor("fresh");
        menu.addItem(summerSalad);

        Food winterSalad = new Food("winter salad", 8.50);
        winterSalad.addDescriptor("healthy");
        winterSalad.addDescriptor("vegitarian");
        winterSalad.addDescriptor("fresh");
        menu.addItem(winterSalad);

        Food yogurt = new Food("yogurt", 6.00);
        yogurt.addDescriptor("dairy");
        yogurt.addDescriptor("healthy");
        yogurt.addDescriptor("protein");
        menu.addItem(yogurt);

        Food steak = new Food("steak", 25.0);
        steak.addDescriptor("meat");
        steak.addDescriptor("protein");
        menu.addItem(steak);

        Food shrimp = new Food("shrimp", 12.50);
        shrimp.addDescriptor("seafood");
        menu.addItem(shrimp);

        Food lobster = new Food("lobster", 34.00);
        lobster.addDescriptor("seaFood");
        menu.addItem(lobster);

        Food clam = new Food("clam", 2.00);
        clam.addDescriptor(("seafood"));
        menu.addItem(clam);

        Food fish = new Food("fish", 6.75);
        fish.addDescriptor("seafood");
        fish.addDescriptor("protein");
        menu.addItem(fish);

        ArrayList<Menu> subs = menu.getSubMenus();

        assertEquals(4, subs.size());

        for(int i = 0; i < subs.size(); i++){
            Menu sub = subs.get(i);
            String name = sub.getName();
            ArrayList<Item> items = sub.getAllItems();
            int itemCount = items.size();

            if(name.equals("seafood")){
                assertEquals(4, itemCount);
            }else if(name.equals("meat")){
                assertEquals(4, itemCount);
            }else if(name.equals("protein")){
                assertEquals(6, itemCount);
            }else if(name.equals("other")){
                assertEquals(11, itemCount);
            }
        }
    }

    @Test
    public void testGetAllItems(){
        Menu menu = new Menu("sample");

        Food hotDog = new Food();
        menu.addItem(hotDog);

        Food chicken = new Food();
        menu.addItem(chicken);

        Food fries = new Food();
        menu.addItem(fries);

        Food popcorn = new Food();
        menu.addItem(popcorn);

        Alcohol beer = new Alcohol();
        menu.addItem(beer);

        Alcohol wine = new Alcohol();
        menu.addItem(wine);

        Alcohol martini = new Alcohol();
        menu.addItem(martini);

        ArrayList<Item> allItem = menu.getAllItems();

        assertEquals(7, allItem.size());

        assertTrue(allItem.contains(hotDog));
        assertTrue(allItem.contains(chicken));
        assertTrue(allItem.contains(fries));
        assertTrue(allItem.contains(popcorn));
        assertTrue(allItem.contains(beer));
        assertTrue(allItem.contains(wine));
        assertTrue(allItem.contains(martini));
    }

    @Test
    public void testGetAllItemsNoItems(){
        Menu menu = new Menu("sample");

        ArrayList<Item> allItems = menu.getAllItems();

        assertEquals(0, allItems.size());
    }

    @Test
    public void testGetAllItemsWithDescriptor(){
        Menu menu = new Menu("sample");

        Food hotDog = new Food("hot dog", 3.5);
        hotDog.addDescriptor("fresh");
        hotDog.addDescriptor("meat");
        hotDog.addDescriptor("protein");
        menu.addItem(hotDog);

        Food chicken = new Food("chicken", 5.00);
        chicken.addDescriptor("meat");
        chicken.addDescriptor("protein");
        menu.addItem(chicken);

        Food pizza = new Food("pizza", 10.00);
        pizza.addDescriptor("dairy");
        pizza.addDescriptor("cheese");
        menu.addItem(pizza);

        Food hamburger = new Food("hamburger", 5.50);
        hamburger.addDescriptor("meat");
        hamburger.addDescriptor("protein");
        menu.addItem(hamburger);

        Food summerSalad = new Food("summer salad", 8.00);
        summerSalad.addDescriptor("healthy");
        summerSalad.addDescriptor("vegitarian");
        summerSalad.addDescriptor("fresh");
        menu.addItem(summerSalad);

        Food winterSalad = new Food("winter salad", 8.50);
        winterSalad.addDescriptor("healthy");
        winterSalad.addDescriptor("vegitarian");
        winterSalad.addDescriptor("fresh");
        menu.addItem(winterSalad);

        Food yogurt = new Food("yogurt", 6.00);
        yogurt.addDescriptor("dairy");
        yogurt.addDescriptor("healthy");
        yogurt.addDescriptor("protein");
        menu.addItem(yogurt);

        Food steak = new Food("steak", 25.0);
        steak.addDescriptor("meat");
        steak.addDescriptor("protein");
        menu.addItem(steak);

        Food shrimp = new Food("shrimp", 12.50);
        shrimp.addDescriptor("seafood");
        menu.addItem(shrimp);

        Food lobster = new Food("lobster", 34.00);
        lobster.addDescriptor("seaFood");
        menu.addItem(lobster);

        Food clam = new Food("clam", 2.00);
        clam.addDescriptor(("seafood"));
        menu.addItem(clam);

        Food fish = new Food("fish", 6.75);
        fish.addDescriptor("seafood");
        fish.addDescriptor("protein");
        menu.addItem(fish);

        ArrayList<Item> freshItems, meats, proteins, dairy, cheese, healthyItems, vegtarian, seaFood;

        freshItems = menu.getItemsWithDescriptor("fresh");

        assertTrue(freshItems.contains(hotDog));
        assertTrue(freshItems.contains(summerSalad));
        assertTrue(freshItems.contains(winterSalad));
        assertEquals(3, freshItems.size());


        meats = menu.getItemsWithDescriptor("meat");

        assertTrue(meats.contains(hotDog));
        assertTrue(meats.contains(chicken));
        assertTrue(meats.contains(hamburger));
        assertTrue(meats.contains(steak));
        assertEquals(4, meats.size());

        proteins = menu.getItemsWithDescriptor("protein");

        assertTrue(proteins.contains(hotDog));
        assertTrue(proteins.contains(chicken));
        assertTrue(proteins.contains(hamburger));
        assertTrue(proteins.contains(yogurt));
        assertTrue(proteins.contains(steak));
        assertTrue(proteins.contains(fish));
        assertEquals(6, proteins.size());

        dairy = menu.getItemsWithDescriptor("dairy");

        assertTrue(dairy.contains(pizza));
        assertTrue(dairy.contains(yogurt));
        assertEquals(2, dairy.size());

        cheese = menu.getItemsWithDescriptor("cheese");

        assertTrue(cheese.contains(pizza));
        assertEquals(1, cheese.size());

        healthyItems = menu.getItemsWithDescriptor("healthy");

        assertTrue(healthyItems.contains(summerSalad));
        assertTrue(healthyItems.contains(winterSalad));
        assertTrue(healthyItems.contains(yogurt));
        assertEquals(3, healthyItems.size());

        vegtarian = menu.getItemsWithDescriptor("vegitarian");

        assertTrue(vegtarian.contains(summerSalad));
        assertTrue(vegtarian.contains(winterSalad));
        assertEquals(2, vegtarian.size());

        seaFood = menu.getItemsWithDescriptor("seafood");

        assertTrue(seaFood.contains(shrimp));
        assertTrue(seaFood.contains(lobster));
        assertTrue(seaFood.contains(clam));
        assertTrue(seaFood.contains(fish));
        assertEquals(4, seaFood.size());
    }

    @Test
    public void testGetItemsWithDescriptorNull(){
        Menu menu = new Menu("sample");

        ArrayList<Item> items = menu.getItemsWithDescriptor(null);

        assertEquals(0, items.size());
    }

    @Test
    public void testGetAllMenus() throws IOException {
        Menu menu = new Menu("sample");
        ArrayList<Menu> allMenus = menu.getAllMenus();

        Menu onlyMenu = allMenus.get(0);
        String name = onlyMenu.getName();

        assertEquals("Summer Menu", name);
        assertEquals(1, allMenus.size());
    }

    @Test
    public void testAddItem(){
        Menu menu = new Menu("sample");

        Food food = new Food("food", 5.00);
        Alcohol alcohol = new Alcohol("alcohol", 4.50);

        menu.addItem(food);
        menu.addItem(alcohol);

        ArrayList<Food> foodItems = menu.getFood();
        ArrayList<Alcohol> alcoholItems = menu.getDrinks();

        assertTrue(foodItems.contains(food));
        assertTrue(alcoholItems.contains(alcohol));

        assertEquals(1, foodItems.size());
        assertEquals(1, alcoholItems.size());
    }

    @Test
    public void testAddItemsNull(){
        Menu menu = new Menu("sample");
        menu.addItem(null);

        ArrayList<Item> allItems = menu.getAllItems();
        assertEquals(0, allItems.size());
    }

}
