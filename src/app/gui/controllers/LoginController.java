package app.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class LoginController{

    @FXML
    private PasswordField password_feild;


    @FXML
    public void one(){
        String text = password_feild.getText();
        text += 1;
        password_feild.setText(text);
    }

    @FXML
    public void two(){
        String text = password_feild.getText();
        text += 2;
        password_feild.setText(text);
    }

    @FXML
    public void three(){
        String text = password_feild.getText();
        text += 3;
        password_feild.setText(text);
    }

    @FXML
    public void four(){
        String text = password_feild.getText();
        text += 4;
        password_feild.setText(text);
    }

    @FXML
    public void five(){
        String text = password_feild.getText();
        text += 5;
        password_feild.setText(text);
    }

    @FXML
    public void six(){
        String text = password_feild.getText();
        text += 6;
        password_feild.setText(text);
    }

    @FXML
    public void seven(){
        String text = password_feild.getText();
        text += 7;
        password_feild.setText(text);
    }

    @FXML
    public void eight(){
        String text = password_feild.getText();
        text += 8;
        password_feild.setText(text);
    }

    @FXML
    public void nine(){
        String text = password_feild.getText();
        text += 9;
        password_feild.setText(text);
    }

    @FXML
    public void zero(){
        String text = password_feild.getText();
        text += 0;
        password_feild.setText(text);
    }

    @FXML
    public void login(){

    }

}
