package application;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.scene.control.Button;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import javax.swing.*;

public class ExitButton extends Button {

        public ExitButton() {
            this.setText("x");
            this.getStyleClass().add("closeButton");
            this.setOnAction(actionEvent -> Platform.exit());
        }
    }
