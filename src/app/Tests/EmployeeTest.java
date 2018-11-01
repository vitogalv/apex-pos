package app.Tests;

import app.ApexLibrary.Employee;
import com.eclipsesource.json.JsonObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EmployeeTest {

    @Test
    public void testConstructorEmpty(){
        Employee e = new Employee();

        String firstName = e.getFirstName();
        String lastName = e.getLastName();
        int systemId = e.getId();
        boolean clockedIn = e.getClockStatus();
        String dob = e.getDateOfBirth();
        String doh = e.getDateOfHire();
        String phone = e.getPhoneNumber();
        String email = e.getEmail();
        double wage = e.getWage();

        assertEquals("", firstName);
        assertEquals("", lastName);
        assertEquals(0, systemId);
        assertEquals(false, clockedIn);
        assertEquals("", dob);
        assertEquals("", doh);
        assertEquals("", phone);
        assertEquals("", email);
        assertEquals(0.0, wage, 0.001);
    }

    @Test
    public void testConstructorWithParams(){
        Employee e = new Employee("Vito", "Galvez", 1234);

        String firstName = e.getFirstName();
        String lastName = e.getLastName();
        int systemId = e.getId();
        boolean clockedIn = e.getClockStatus();
        String dob = e.getDateOfBirth();
        String doh = e.getDateOfHire();
        String phone = e.getPhoneNumber();
        String email = e.getEmail();
        double wage = e.getWage();

        assertEquals("Vito", firstName);
        assertEquals("Galvez", lastName);
        assertEquals(1234, systemId);
        assertEquals(false, clockedIn);
        assertEquals("", dob);
        assertEquals("", doh);
        assertEquals("", phone);
        assertEquals("", email);
        assertEquals(0.0, wage, 0.001);
    }

    @Test
    public void testConstructorWithNullParams(){
        Employee e = new Employee(null, "lastName", -4321);

        String firstName = e.getFirstName();
        String lastName = e.getLastName();
        int systemId = e.getId();
        boolean clockedIn = e.getClockStatus();
        String dob = e.getDateOfBirth();
        String doh = e.getDateOfHire();
        String phone = e.getPhoneNumber();
        String email = e.getEmail();
        double wage = e.getWage();

        assertEquals(null, firstName);
        assertEquals("lastName", lastName);
        assertEquals(4321, systemId);
        assertEquals(false, clockedIn);
        assertEquals("", dob);
        assertEquals("", doh);
        assertEquals("", phone);
        assertEquals("", email);
        assertEquals(0.0, wage, 0.001);
    }
    @Test
    public void testJsonConstructor(){
        JsonObject json = new JsonObject();

        json.add("clockedIn", false);
        json.add("firstName", "Vito");
        json.add("lastName", "Galvez");
        json.add("dateOfBirth", "07/05/2000");
        json.add("dateOfHire", "07/08/2014");
        json.add("systemId", 2453);
        json.add("phoneNumber", "8457084325");
        json.add("email", "vitogalvez29@gmail.com");
        json.add("wage", 11.75);

        Employee e = new Employee(json);
        String firstName = e.getFirstName();
        String lastName = e.getLastName();
        int systemId = e.getId();
        boolean clockedIn = e.getClockStatus();
        String dob = e.getDateOfBirth();
        String doh = e.getDateOfHire();
        String phone = e.getPhoneNumber();
        String email = e.getEmail();
        double wage = e.getWage();

        assertEquals("Vito", firstName);
        assertEquals("Galvez", lastName);
        assertEquals(2453, systemId);
        assertEquals(false, clockedIn);
        assertEquals("07/05/2000", dob);
        assertEquals("07/08/2014", doh);
        assertEquals("8457084325", phone);
        assertEquals("vitogalvez29@gmail.com", email);
        assertEquals(11.75, wage, 0.001);
    }

    @Test
    public void testJsonConstructorWithNull() {
        Employee e = new Employee(null);

        String firstName = e.getFirstName();
        String lastName = e.getLastName();
        int systemId = e.getId();
        boolean clockedIn = e.getClockStatus();
        String dob = e.getDateOfBirth();
        String doh = e.getDateOfHire();
        String phone = e.getPhoneNumber();
        String email = e.getEmail();
        double wage = e.getWage();

        assertEquals("", firstName);
        assertEquals("", lastName);
        assertEquals(0, systemId);
        assertEquals(false, clockedIn);
        assertEquals("", dob);
        assertEquals("", doh);
        assertEquals("", phone);
        assertEquals("", email);
        assertEquals(0.0, wage, 0.001);
    }

    @Test
    public void testGetJson(){
        Employee e = new Employee("vito", "galvez", 1234);

        e.setWage(11.75);
        e.setEmail("vitogalvez29@gmail.com");
        e.setPhoneNumber("8457985531");
        e.setDateOfHire("07/10/2017");
        e.setDateOfBirth("07/05/2000");
        e.clockIn();

        JsonObject json = e.getJson();

        String firstName = json.get("firstName").asString().toString();
        String lastName = json.get("lastName").asString().toString();
        int id = json.get("systemId").asInt();
        double wage = json.get("wage").asDouble();
        String email = json.get("email").asString().toString();
        String phone = json.get("phoneNumber").asString().toString();
        String dob = json.get("dateOfBirth").asString().toString();
        String doh = json.get("dateOfHire").asString().toString();
        boolean clockedIn = json.get("clockedIn").asBoolean();

        assertEquals("vito", firstName);
        assertEquals("galvez", lastName);
        assertEquals(1234, id);
        assertEquals(false, clockedIn);
        assertEquals("07/05/2000", dob);
        assertEquals("07/10/2017", doh);
        assertEquals("8457985531", phone);
        assertEquals("vitogalvez29@gmail.com", email);
        assertEquals(11.75, wage, 0.001);
    }
}
