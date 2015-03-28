package application;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.awt.event.MouseEvent;
import java.util.Collections;

/**
 * Created by Eirik on 13.03.2015.
 */
public class OptionsController {

    private Stage stage;
    @FXML
    private ListView<String> soundList;
    @FXML
    private Button save, cancel;
    private boolean ok;
    private MainApp main;

    public void setMain(MainApp main){
        this.main = main;
    }

    public void setData(){

        soundList.getItems().addAll(main.getSounds());
        Collections.sort(soundList.getItems(), String.CASE_INSENSITIVE_ORDER);
        soundList.setOnMouseClicked(event -> {
            System.out.println(soundList.getSelectionModel().getSelectedItem().toString());
            if(event.getClickCount() == 2){
            main.playSound(soundList.getSelectionModel().getSelectedItem());
            }
        });
    }


    public void setStage(Stage stage){
        this.stage = stage;
    }

    public void rofl(){
        System.out.println(cancel.getHeight() + " " + cancel.getWidth());
    }
    public void closeWindow(){stage.close();}

    public boolean getOk(){
        return this.ok;
    }
    public void  okClicked(){
        if(soundList.getSelectionModel().getSelectedIndex() != -1){
           main.setSelectedSound(soundList.getSelectionModel().getSelectedItem());
        };
        stage.close();
    }
    public void cancelClicked() {
        stage.close();
    }

}
