package app.ApexLibrary;




import com.eclipsesource.json.JsonObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Vito on 12/24/2017.
 */

public class Employee{

    protected String _firstName;
    protected String _lastName;
    protected Integer _systemId;  //what will be used to clock in/out etc
    protected Boolean _clockedIn;
    private String _dateOfBirth;
    private String _dateOfHire;
    private String _phoneNumber;
    private String _email;
    private double _wage;



    public Employee(){
        this._firstName = "";
        this._lastName = "";
        this._systemId = 0;
        this._clockedIn = false;
        this._dateOfBirth = "";
        this._dateOfHire  = "";
        this._phoneNumber = "";
        this._email = "";
        this._wage = 0.00;
    }

    public Employee(String firstName, String lastName, Integer id){
        this._firstName = firstName;
        this._lastName = lastName;
        this._systemId = Math.abs(id);
        this._dateOfBirth = "";
        this._dateOfHire = "";
        this._phoneNumber = "";
        this._email = "";
        this._wage = 0.00;
    }

    public Employee(JsonObject json){

        if(!json.isNull()) {
            this._firstName = json.get("firstName").asString().toString();
            this._lastName = json.get("lastName").toString().replace("\"", "");
            this._systemId = new Integer(json.get("systemId").toString().replace("\"", ""));
            this._clockedIn = json.get("clockedIn").asBoolean();
            this._dateOfBirth = json.get("dateOfBirth").toString().replace("\"", "");
            this._dateOfHire = json.get("dateOfHire").toString().replace("\"", "");
            this._phoneNumber = json.get("phoneNumber").toString().replace("\"", "");
            this._email = json.get("email").toString().replace("\"", "");
            this._wage = new Double(json.get("wage").toString().replace("\"", ""));
        }else{
            this._firstName = "";
            this._lastName = "";
            this._systemId = 0;
            this._clockedIn = false;
            this._dateOfBirth = "";
            this._dateOfHire  = "";
            this._phoneNumber = "";
            this._email = "";
            this._wage = 0.00;
        }
    }



    public void clockIn(){
        this._clockedIn = true;
    }

    public void clockOut(){
        this._clockedIn = false;
    }


    //Getters
    public boolean getClockStatus(){return this._clockedIn;}
    public String getFirstName(){return this._firstName;}
    public String getLastName(){return this._lastName;}
    public String getDateOfBirth(){return this._dateOfBirth;}
    public String getDateOfHire(){return this._dateOfHire;}
    public int getId(){return this._systemId;}
    public String getPhoneNumber(){return this._phoneNumber;}
    public String getEmail(){return this._email;}
    public double getWage(){  return this._wage; }

    public JsonObject getJson(){
        JsonObject json = new JsonObject();

        json.add("clockedIn", this._clockedIn);
        json.add("firstName", this._firstName);
        json.add("lastName", this._lastName);
        json.add("dateOfBirth", this._dateOfBirth);
        json.add("dateOfHire", this._dateOfHire);
        json.add("systemId", this._systemId);
        json.add("phoneNumber", this._phoneNumber);
        json.add("email", this._email);
        json.add("wage", this._wage);

        return json;
    }

    //Setters
    public void setSystemId(int id){this._systemId = Math.abs(id);}
    public void setFirstName(String fn){this._firstName = fn;}
    public void setLastName(String ln){this._lastName  = ln;}
    public void setDateOfBirth(String dob){this._dateOfBirth = dob;}
    public void setDateOfHire(String date){this._dateOfHire = date;}
    public void setPhoneNumber(String pn){this._phoneNumber = pn;}
    public void setEmail(String e){this._email = e;}
    public void setWage(double wage){ this._wage = wage; }

    public void writeFile(){

    }


}
