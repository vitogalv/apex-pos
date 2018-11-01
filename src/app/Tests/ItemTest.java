package app.Tests;

import app.ApexLibrary.Alcohol;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ItemTest {

    @Test
    public void testSetName(){
        Alcohol alcohol = new Alcohol();
        alcohol.setName("wine");

        assertEquals("wine", alcohol.getName());
    }

    @Test
    public void testSetNameNull(){
        Alcohol alcohol = new Alcohol();
        alcohol.setName(null);

        assertNull(alcohol.getName());
    }

    @Test
    public void testSetPrice(){
        Alcohol alcohol = new Alcohol();
        alcohol.setPrice(0.15);

        assertEquals(0.15, alcohol.getPrice(), .001);
    }

    @Test
    public void testSetPriceNeg(){
        Alcohol alcohol = new Alcohol(null, 4);
        alcohol.setPrice(-2.35);

        assertEquals(2.35, alcohol.getPrice(), 0.001);
    }

    @Test
    public void testAddDescriptor(){
        Alcohol alcohol = new Alcohol();
        alcohol.addDescriptor("Red");
        alcohol.addDescriptor("wine");
        alcohol.addDescriptor("age Restriction");

        ArrayList<String> descriptors = alcohol.getDescriptors();

        assertEquals("red", descriptors.get(0));
        assertEquals("wine", descriptors.get(1));
        assertEquals("agerestriction", descriptors.get(2));
        assertEquals(3, descriptors.size());
    }

    @Test
    public void testAddDescriptorNull(){
        Alcohol alcohol = new Alcohol();
        alcohol.addDescriptor("blue");
        alcohol.addDescriptor("green");
        alcohol.addDescriptor(null);

        ArrayList<String> descriptors = alcohol.getDescriptors();
        int size = descriptors.size();

        assertEquals(2, size);
        assertEquals("blue", descriptors.get(0));
        assertEquals("green", descriptors.get(1));
    }

    @Test
    public void testAddDescriptorWithDuplicates(){
        Alcohol alcohol = new Alcohol();
        alcohol.addDescriptor("green");
        alcohol.addDescriptor("green");
        alcohol.addDescriptor("green");
        alcohol.addDescriptor("blue");
        alcohol.addDescriptor("red");

        ArrayList<String> descriptors = alcohol.getDescriptors();
        int size = descriptors.size();

        assertEquals(3, size);
        assertEquals("green", descriptors.get(0));
        assertEquals("blue", descriptors.get(1));
        assertEquals("red", descriptors.get(2));
    }


    @Test
    public void testSetDescriptors(){
        Alcohol alcohol = new Alcohol();

        ArrayList<String> descriptors = new ArrayList<>();
        descriptors.add("blue");
        descriptors.add("red");
        descriptors.add("purple");

        alcohol.setDescriptors(descriptors);

        ArrayList<String> alcoholDescriptors = alcohol.getDescriptors();

        assertEquals("blue", alcoholDescriptors.get(0));
        assertEquals("red", alcoholDescriptors.get(1));
        assertEquals("purple", alcoholDescriptors.get(2));
    }

    @Test
    public void testSetDescriptorsNull(){
        ArrayList<String> descriptors = null;

        Alcohol alcohol = new Alcohol();
        alcohol.setDescriptors(descriptors);

        ArrayList<String> alcoholDescriptors = alcohol.getDescriptors();
        int size = alcoholDescriptors.size();

        assertNotNull(alcoholDescriptors);
        assertEquals(0, size);
    }

    @Test
    public void testHasDescriptor(){
        Alcohol alcohol = new Alcohol();
        alcohol.addDescriptor("red");
        alcohol.addDescriptor("blue");
        alcohol.addDescriptor(null);

        assertTrue(alcohol.hasDescriptor("red"));
        assertFalse(alcohol.hasDescriptor("yellow"));
        assertFalse(alcohol.hasDescriptor(null));
    }

    @Test
    public void testGetDescriptorArray(){
        Alcohol  alcohol = new Alcohol();
        alcohol.addDescriptor("red");
        alcohol.addDescriptor("pink");
        alcohol.addDescriptor("purple");
        alcohol.addDescriptor("blue");
        alcohol.addDescriptor("magenta");

        JsonArray json = alcohol.getDescriptorArray();

        assertEquals("red", json.get(0).toString());
        assertEquals("pink", json.get(1).toString());
        assertEquals("purple", json.get(2).toString());
        assertEquals("blue", json.get(3).toString());
        assertEquals("magenta", json.get(4).toString());

    }

    @Test
    public void testGetJson(){
        Alcohol alcohol = new Alcohol("beer", 3.50);
        alcohol.addDescriptor("cheap");
        alcohol.addDescriptor("beer");
        alcohol.addDescriptor("red");
        alcohol.addDescriptor("alcoholic");

        JsonObject json = alcohol.getJson();

        String name = json.get("name").toString();
        double price = json.get("price").asDouble();

        JsonArray descriptors = json.get("descriptors").asArray();
        String d1 = descriptors.get(0).toString();
        String d2 = descriptors.get(1).toString();
        String d3 = descriptors.get(2).toString();
        String d4 = descriptors.get(3).toString();

        assertEquals("beer", name);
        assertEquals(3.50, price, 0.001);
        assertEquals(4, descriptors.size());

        assertEquals("cheap", d1);
        assertEquals("beer", d2);
        assertEquals("red", d3);
        assertEquals("alcoholic", d4);
    }
}
