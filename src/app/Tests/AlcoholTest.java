package app.Tests;

import app.ApexLibrary.Alcohol;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class AlcoholTest {

    @Test
    public void testEmptyContructor(){
        Alcohol alcohol = new Alcohol();

        String name = alcohol.getName();
        double price = alcohol.getPrice();
        ArrayList<String> descriptors = alcohol.getDescriptors();
        int descriptorCount = descriptors.size();

        assertEquals("", name);
        assertEquals(0.0, price, 0.001);
        assertEquals(0, descriptorCount);
    }

    @Test
    public void testConstructorWithNameAndPrice(){
        Alcohol alcohol = new Alcohol("Beer", 4.50);

        double price = alcohol.getPrice();
        String name = alcohol.getName();
        int descriptorCount = alcohol.getDescriptors().size();

        assertEquals(4.5, price, .001);
        assertEquals("Beer", name);
        assertEquals(0, descriptorCount);
    }

    @Test
    public void testContructorWithNullAndNeg(){
        Alcohol alcohol = new Alcohol(null, -5.00);

        assertNull(alcohol.getName());
        assertEquals(5.0, alcohol.getPrice(), 0.001);
    }
}
