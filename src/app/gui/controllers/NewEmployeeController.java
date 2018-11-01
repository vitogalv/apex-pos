package app.gui.controllers;

import app.ApexLibrary.Employee;
import app.ApexLibrary.serverMode.Server;
import com.eclipsesource.json.JsonObject;
import javafx.collections.ObservableArray;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class NewEmployeeController implements Initializable {

    @FXML
    TextField firstNameTextField;

    @FXML
    TextField lastNameTextField;

    @FXML
    TextField dobTextField;

    @FXML
    TextField phoneTextField;

    @FXML
    TextField emailTextField;

    @FXML
    PasswordField systemPinPasswordField;

    @FXML
    TextField wageTextField;

    @FXML
    ComboBox positionSelectComboBox;

    @FXML
    Button discardButton;

    @FXML
    Button saveButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        positionSelectComboBox.getItems().addAll("Manager", "Server", "Chef", "Other");
    }

    public Employee getEmployee(){
        Employee e = new Employee();

        String selectedPosition = (String)positionSelectComboBox.getValue();

        e.setFirstName(firstNameTextField.getText());
        e.setLastName(lastNameTextField.getText());
        e.setDateOfBirth(dobTextField.getText());
        e.setDateOfHire(new Date().toString());
        e.setPhoneNumber(phoneTextField.getText());
        e.setEmail(emailTextField.getText());
        e.setSystemId(new Integer(systemPinPasswordField.getText()));
        e.setWage(new Double(wageTextField.getText()));

        return e;
    }

    public void writeEmployeeFile(){
        Employee e = getEmployee();
        String json = e.getJson().toString();
        String filename = "C:\\Users\\Vito\\eclipse-workspace\\Apex\\src\\app\\appData\\employees\\" + e.getId() + ".txt";
        File employeeFile = new File(filename);

        if(!employeeFile.exists()){
            try {
                employeeFile.createNewFile();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        try {
            FileOutputStream fos = new FileOutputStream(employeeFile);
            fos.write(json.getBytes());
            fos.close();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        //Below writes data to roster file

        File rosterFile = new File("C:\\Users\\Vito\\eclipse-workspace\\Apex\\src\\app\\appData\\employees\\employeeRoster");
        try {
            FileOutputStream  fos = new FileOutputStream(rosterFile, true);
            fos.write((filename + "\n").getBytes());
            fos.close();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public void saveButtonHandler(){
        writeEmployeeFile();
        System.out.println("file written");
    }



}
