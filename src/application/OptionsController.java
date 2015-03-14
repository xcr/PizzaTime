package application;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

/**
 * Created by Eirik on 13.03.2015.
 */
public class OptionsController {

    private Stage stage;
    @FXML
    private ListView<String> soundList;


    public void setData(){
        soundList.getItems().addAll(SoundPlayer.getSounds().keySet());
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }

    public boolean  okClicked(){
        return true;
    }

    public void closeWindow(){stage.close();}
}
