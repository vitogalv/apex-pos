package app.Tests;

import app.ApexLibrary.Food;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class FoodTest {

    @Test
    public void testConstructorEmpty(){
        Food food = new Food();

        String name =  food.getName();
        double price = food.getPrice();
        ArrayList<String> allergens = food.getAllergens();
        ArrayList<String> descriptors = food.getDescriptors();

        int allergenSize = allergens.size();
        int descriptorSize = descriptors.size();

        assertEquals("", name);
        assertEquals(0.0, price, 0.001);
        assertEquals(0, allergenSize);
        assertEquals(0, descriptorSize);
    }

    @Test
    public void testConstructorWithNameAndPrice(){
        Food food = new Food("Hot Dog", 5.25);

        String name = food.getName();
        double price = food.getPrice();
        ArrayList<String> allergens = food.getAllergens();
        ArrayList<String> descriptors = food.getDescriptors();

        int allergenSize = allergens.size();
        int descriptorSize = descriptors.size();

        assertEquals("Hot Dog", name);
        assertEquals(5.25, price, 0.001);
        assertEquals(0, allergenSize);
        assertEquals(0, descriptorSize);
    }

    @Test
    public void testConstructorWithNull(){
        Food food = new Food(null, 0.0);

        String name = food.getName();
        double price = food.getPrice();
        ArrayList<String> descriptors = food.getDescriptors();
        ArrayList<String> allergens = food.getAllergens();

        assertNull(name);
        assertEquals(0.0, price, 0.001);
        assertEquals(0, descriptors.size());
        assertEquals(0, allergens.size());

    }

    @Test
    public void testJsonConstructor(){
        JsonObject json = new JsonObject();

        json.add("name", "pizza");
        json.add("price", 10.75);

        JsonArray descriptors = new JsonArray();
        descriptors.add("cheesy");
        descriptors.add("gooey");
        json.add("descriptors", descriptors);

        JsonArray allergens = new JsonArray();
        allergens.add("dairy");
        allergens.add("meat");
        allergens.add("tomato");
        json.add("allergens", allergens);

        Food food = new Food(json);
        String name = food.getName();
        Double price = food.getPrice();

        ArrayList<String> descriptList = food.getDescriptors();
        String d1, d2;
        d1 = descriptList.get(0);
        d2 = descriptList.get(1);

        ArrayList<String> allerList = food.getAllergens();
        String a1, a2, a3;
        a1 = allerList.get(0);
        a2 = allerList.get(1);
        a3 = allerList.get(2);

        assertEquals("pizza", name);
        assertEquals(10.75, price, 0.001);

        assertEquals(3, descriptList.size());
        assertEquals(2, allerList.size());

        assertEquals("cheesy", d1);
        assertEquals("gooey", d2);

        assertEquals("dairy", a1);
        assertEquals("meat", a2);
        assertEquals("tomato", a3);

    }

    @Test
    public void testNullJsonConstructor(){
        Food food = new Food(null);

        String name = food.getName();
        double price = food.getPrice();
        ArrayList<String> descriptors =  food.getDescriptors();
        ArrayList<String> allergens  = food.getAllergens();

        assertEquals("", name);
        assertEquals(0.0, price, 0.001);
        assertEquals(0, descriptors.size());
        assertEquals(0, allergens.size());
    }

    @Test
    public void testGetAllergens(){
        Food food = new Food();
        food.addAllergen("dairy");
        food.addAllergen("nuts");

        ArrayList<String> allergens = food.getAllergens();
        int size = allergens.size();

        assertEquals(2, size);
        assertEquals("dairy", allergens.get(0));
        assertEquals("nuts", allergens.get(1));
    }

    @Test
    public void testGetAllergenJson(){
        Food food = new Food("burger", 4.01);
        food.addAllergen("beef");
        food.addAllergen("meat");
        food.addAllergen("gluten");

        JsonArray json = food.getAllergenJson();
        String a1 = json.get(0).toString();
        String a2 = json.get(1).toString();
        String a3 = json.get(2).toString();

        assertEquals(3, json.size());
        assertEquals("beef", a1);
        assertEquals("meat", a2);
        assertEquals("gluten", a3);
    }

    @Test
    public void testAddAllergenWithDuplicates(){
        Food food = new Food();
        food.addAllergen("dairy");
        food.addAllergen("nuts");
        food.addAllergen("dairy");

        ArrayList<String> allergens = food.getAllergens();

        assertEquals(2, allergens.size());
        assertEquals("dairy", allergens.get(0));
        assertEquals("nuts", allergens.get(1));
    }

    @Test
    public void testGetJson(){
        Food food = new Food("pizza", 10.00);
        food.addDescriptor("large");
        food.addDescriptor(null);
        food.addDescriptor("extra Cheese");
        food.addDescriptor("cheap");

        food.addAllergen("dairy");
        food.addAllergen("nuts");
        food.addAllergen("fish");
        food.addAllergen(null);

        JsonObject json = food.getJson();

        String name = json.get("name").toString();
        double price = json.get("price").asDouble();

        JsonArray descriptors = json.get("descriptors").asArray();
        String d1 = descriptors.get(0).toString();
        String d2 = descriptors.get(1).toString();
        String d3 = descriptors.get(2).toString();

        JsonArray allergens = json.get("allergens").asArray();
        String  a1 = allergens.get(0).toString();
        String  a2 = allergens.get(1).toString();
        String  a3 = allergens.get(2).toString();

        assertEquals("pizza", name);
        assertEquals(10.00, price, 0.001);

        assertEquals(3, descriptors.size());
        assertEquals("large", d1);
        assertEquals("extracheese", d2);
        assertEquals("cheap", d3);

        assertEquals(3, allergens.size());
        assertEquals("dairy", a1);
        assertEquals("nuts", a2);
        assertEquals("fish", a3);
    }

    @Test
    public void testContainsAllergen(){
        Food food = new Food();
        food.addAllergen("dairy");
        food.addAllergen("nuts");
        food.addAllergen("nuts");

        assertTrue(food.containsAllergen("dairy       "));
        assertTrue(food.containsAllergen(" NUts"));
        assertFalse(food.containsAllergen("red"));
    }

}
