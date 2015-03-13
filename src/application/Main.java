package application;
	


import javafx.application.Application;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.StageStyle;

import java.io.IOException;


public class Main extends Application {

    private double xOffset;
    private double yOffset;
    private Stage primaryStage;
    private MainController mc;


    @Override
	public void start(final Stage primaryStage) throws IOException {
		try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/fxml/Main.fxml"));
            primaryStage.setTitle("PizzaTime");
            this.primaryStage = primaryStage;
            Pane pane = (Pane)loader.load();
            Scene scene = new Scene(pane);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

            primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Pizza Time!");
			primaryStage.show();
			primaryStage.setResizable(false);
			primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("pizza.png")));
            mc =loader.getController();
            mc.setMain(this);

            pane.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    xOffset = event.getSceneX();
                    yOffset = event.getSceneY();
                }
            });
            pane.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    primaryStage.setX(event.getScreenX() - xOffset);
                    primaryStage.setY(event.getScreenY() - yOffset);
                }
            });

            //css
            pane.getStyleClass().add("rofl");
		} catch(Exception e) {
			e.printStackTrace();
		}

        SoundPlayer.importFiles();
     //  SoundPlayer.playSound(SoundPlayer.getSounds().get("TMNT: Turtles in time - Pizza Time"));
       // SoundPlayer.playSound(SoundPlayer.getSounds().get("Majora's Mask - Get Mask"));
        //SoundPlayer.playSound(SoundPlayer.getSounds().get("Starcraft 2 - Spawn more overlords"));
	}



    public  boolean showOptions() {

        try {
            //loads the fxml.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/fxml/Options.fxml"));

            //creates the the popup window
            Stage stage = new Stage();
            stage.setTitle("Ny gruppe");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(primaryStage);
            Pane page = (Pane)loader.load();
            Scene scene = new Scene(page);
            stage.setScene(scene);
            stage.setResizable(false);

            //makes a reference to the application controller and sets the datafields.
            OptionsController controller = loader.getController();
            controller.setStage(stage);


            //wait for the user to finish in the popup window before it continues
            stage.showAndWait();
            return controller.okClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

	
	public static void main(String[] args) {
		launch(args);

		
	}
}
