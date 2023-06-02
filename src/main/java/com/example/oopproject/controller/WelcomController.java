package com.example.oopproject.controller;

import com.example.oopproject.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class WelcomController {
    public void controlNhanvat(ActionEvent event) throws Exception{
        App.setRoot("CharacterOverView");
    }
}