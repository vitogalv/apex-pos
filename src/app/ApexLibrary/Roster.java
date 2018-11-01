package app.ApexLibrary;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;

import java.io.*;
import java.util.ArrayList;
/*
    This class represents the file that holds all the related file paths such as menu files or employee files
 */
public class Roster {

    /*
            @var[_rosterFileName]
     */
    private String _rosterFileName;
    private ArrayList<String> _filePaths;
    private String _rosterType;

    public Roster(){
        this._filePaths  = new ArrayList<>();
        this._rosterFileName = "";
        this._rosterType = "";
    }

    /*
            Constructor will construct an object based off of the roster type

            @param[type] as of 2/9/18 11:11pm the only valid roster types are "employees" and "menus"
                "employees" - will represent the employeeRoster file
                "menus" - will represent the menuRoster file
     */
    public Roster(String type){

        this._filePaths = new ArrayList<>();

        this._rosterType = null;

        if(type.equals("menus")){
            setFileName("C:\\Users\\Vito\\eclipse-workspace\\Apex\\src\\app\\appData\\Menus\\menuRoster.json");
            _rosterType = "menus";
        }else if(type.equals("employees")){
            setFileName("C:\\Users\\Vito\\eclipse-workspace\\Apex\\src\\app\\appData\\employees\\employeeRoster.json");
            _rosterType = "employees";
        }

        if(_rosterType != null){

            JsonArray rosterArray = null;
            try {
                rosterArray = getJson();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (rosterArray.size() > 0 && rosterArray != null) {
                for (int i = 0; i < rosterArray.size(); i++) {
                    String path = rosterArray.get(i).toString().replace("\"", "");
                    this._filePaths.add(path);
                }
            }
        }
    }




    //Getters

    /*
            This method will get the raw JSON array from the file
     */
    public JsonArray getJson() throws IOException {

        File rosterFile = getRosterFile();

        if(!rosterFile.exists()){
            rosterFile.createNewFile();
            return null;
        }

        FileReader reader = new FileReader(rosterFile);
        JsonArray json = Json.parse(reader).asArray();
        return json;
    }


    /*
            This method will return the JSON representing the current Object
     */
    public JsonArray toJson(){
        JsonArray json = new JsonArray();

        for(int i = 0; i < this._filePaths.size(); i++){
            String path = this._filePaths.get(i);
            System.out.println(path);
            json.add(path);
        }

        return json;
    }


    //Simple getter methods

    public ArrayList<String> getFilePaths(){ return this._filePaths; }
    public String getFileName(){ return this._rosterFileName; }



    //Setters

    public void setFileName(String fileName){ this._rosterFileName = fileName; }

    //adds a new file path but does not update the file writeFile() must be called
    public void addFile(String fileName){
        if(fileName != null) {

            ArrayList<String> paths = getFilePaths();

            if(!paths.contains(fileName)) {
                this._filePaths.add(fileName);
            }

        }
    }

    public File getRosterFile(){
        File file = new File(getFileName());
        return file;
    }

    //writes to file most recent version of the Roster
    public void writeFile() throws IOException {
        JsonArray json = toJson();
        File file = getRosterFile();

        if(!file.exists()){
            file.createNewFile();
        }

        FileOutputStream fos = new FileOutputStream(file);
        fos.write(json.toString().getBytes());
        fos.close();
    }
}
