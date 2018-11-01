package app.Tests;

import app.ApexLibrary.Roster;
import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class RosterTests {

    @Test
    public void testGetMenuJson(){
        Roster roster = new Roster("menus");

        ArrayList<String> expectedMenuPaths = new ArrayList<>();
        expectedMenuPaths.add("C:\\Users\\Vito\\eclipse-workspace\\Apex\\src\\app\\appData\\Menus\\summermenu.json");

        try {
            JsonArray json = roster.getJson();
            int size = json.size();

            assertTrue(size == expectedMenuPaths.size());

            for(int i = 0; i < size; i++){
                String actualPath = json.get(i).asString();
                String expectedPath = expectedMenuPaths.get(i);

                assertEquals("The expected path: " +  expectedPath + ", does not match the actual path: " + actualPath, expectedPath, actualPath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetEmployeeJson(){
        Roster roster = new Roster("employee");

        ArrayList<String> expectedPaths = new ArrayList<>();

        try {
            JsonArray json = roster.getJson();
            int actualSize = json.size();
            int expectedSize = expectedPaths.size();

            assertTrue("Expected size: " + expectedSize + ", does not match the actual size: " + actualSize,json.size() == expectedSize);

            if(actualSize != 0) {
                for (int i = 0; i < actualSize; i++) {
                    String actualPath = json.get(i).toString();
                    String expected = expectedPaths.get(i);
                    assertEquals("The expected: " + expected + " does not match the actual: " + actualPath, expected, actualPath);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testToJson(){
        Roster roster = new Roster();
        JsonArray json = roster.toJson();

        int expectedSize = 0;
        int actualSize = json.size();

        assertSame("The actual size of the array did not match the expected." ,expectedSize, actualSize);
    }

    @Test
    public void testAddFileAndGetPaths(){
        Roster roster = new Roster();
        roster.addFile("C:\\Users\\Vito\\eclipse-workspace\\Apex\\src\\app\\appData\\Menus\\summermenu.json");
        roster.addFile("C:\\Users\\Vito\\eclipse-workspace\\Apex\\src\\app\\appData\\Menus\\summermenu.json");
        roster.addFile(null);
        roster.addFile("C:\\Users\\Vito\\eclipse-workspace\\Apex\\src\\app\\appData\\Menus\\springmenu.json");
        roster.addFile("C:\\Users\\Vito\\eclipse-workspace\\Apex\\src\\app\\appData\\Menus\\fallmenu.json");
        roster.addFile("C:\\Users\\Vito\\eclipse-workspace\\Apex\\src\\app\\appData\\Menus\\fallmenu.json");
        ArrayList<String> paths = roster.getFilePaths();

        int size = paths.size();
        String first = paths.get(0), second = paths.get(1), third = paths.get(2);

        assertEquals("C:\\Users\\Vito\\eclipse-workspace\\Apex\\src\\app\\appData\\Menus\\summermenu.json", first);
        assertEquals("C:\\Users\\Vito\\eclipse-workspace\\Apex\\src\\app\\appData\\Menus\\springmenu.json", second);
        assertEquals("C:\\Users\\Vito\\eclipse-workspace\\Apex\\src\\app\\appData\\Menus\\fallmenu.json", third);
        assertEquals(3, size);
    }

    @Test
    public void testWriteFile(){
        Roster roster = new Roster("menus");
        roster.addFile("C:\\Users\\Vito\\eclipse-workspace\\Apex\\src\\app\\appData\\Menus\\latenightmenu.json");
        roster.addFile("C:\\Users\\Vito\\eclipse-workspace\\Apex\\src\\app\\appData\\Menus\\dinnermenu.json");
        roster.addFile("C:\\Users\\Vito\\eclipse-workspace\\Apex\\src\\app\\appData\\Menus\\lunchmenu.json");
        roster.addFile("C:\\Users\\Vito\\eclipse-workspace\\Apex\\src\\app\\appData\\Menus\\breakfastmenu.json");

        try {
            roster.writeFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            FileReader reader = new FileReader(roster.getFileName());
            JsonArray json = Json.parse(reader).asArray();

            int size = json.size();
            assertEquals("The actual size of the array did not match the expected", 5, size);

            String first = json.get(0).asString(), second = json.get(1).asString(), third = json.get(2).asString(), fourth = json.get(3).asString();

            assertEquals("C:\\Users\\Vito\\eclipse-workspace\\Apex\\src\\app\\appData\\Menus\\latenightmenu.json", first);
            assertEquals("C:\\Users\\Vito\\eclipse-workspace\\Apex\\src\\app\\appData\\Menus\\dinnermenu.json", second);
            assertEquals("C:\\Users\\Vito\\eclipse-workspace\\Apex\\src\\app\\appData\\Menus\\lunchmenu.json", third);
            assertEquals("C:\\Users\\Vito\\eclipse-workspace\\Apex\\src\\app\\appData\\Menus\\breakfastmenu.json", fourth);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
