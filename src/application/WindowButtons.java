package application;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.scene.control.Button;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class WindowButtons extends HBox {

        public WindowButtons() {
            Button closeBtn = new Button("x");
            closeBtn.getStyleClass().add("closeButton");

            closeBtn.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent actionEvent) {
                    Platform.exit();
                }
            });

            this.getChildren().add(closeBtn);
        }
    }
