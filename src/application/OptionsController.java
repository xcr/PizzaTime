package application;

import javafx.stage.Stage;

/**
 * Created by Eirik on 13.03.2015.
 */
public class OptionsController {

    private Stage stage;

    public void setStage(Stage stage){
        this.stage = stage;
    }

    public boolean  okClicked(){
        return true;
    }
}
