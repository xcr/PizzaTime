package application;
	


import javafx.application.Application;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.StageStyle;

import java.io.*;
import java.net.URL;
import java.util.Map;
import java.util.Scanner;


public class MainApp extends Application {

    private double xOffset;
    private double yOffset;
    private Stage primaryStage;
    private MainController mc;
    private static Media selectedSound;
    private SoundPlayer soundPlayer;


    /*
    todo:
    Write a class for the media files
     */


    @Override
	public void start(final Stage primaryStage) throws IOException {
		try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/fxml/Main.fxml"));
            primaryStage.setTitle("PizzaTime");
            this.primaryStage = primaryStage;
            Pane pane = (Pane)loader.load();
            Scene scene = new Scene(pane);
			scene.getStylesheets().add(getClass().getResource("/css/pink.css").toExternalForm());

            primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Pizza Time!");
			primaryStage.show();
			primaryStage.setResizable(false);
			primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/res/pizza.png")));
            mc =loader.getController();
            mc.setMain(this);

            mc.text.requestFocus();
            mc.setStage(primaryStage);
            setWindowSettings(pane, primaryStage);


            this.soundPlayer = new SoundPlayer();
            soundPlayer.importFiles();

            setStartSound();

		} catch(Exception e) {
			e.printStackTrace();
		}

     //  SoundPlayer.playSound(SoundPlayer.getSounds().get("TMNT: Turtles in time - Pizza Time"));
       // SoundPlayer.playSound(SoundPlayer.getSounds().get("Majora's Mask - Get Mask"));
        //SoundPlayer.playSound(SoundPlayer.getSounds().get("Starcraft 2 - Spawn more overlords"));
	}

    public void stop() throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(this.getClass().getResource("/res/sound.txt").getPath());
        writer.flush();


        for (Map.Entry<String, Media> entry : SoundPlayer.getSounds().entrySet()) {
            if(entry.getValue() == selectedSound){
                writer.write(entry.getKey());
            }
        }
        writer.close();

    }
    public  boolean showOptions() {

        try {
            //loads the fxml.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/fxml/Options.fxml"));

            //creates the the popup window
            Stage stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(primaryStage);
            stage.initStyle(StageStyle.UNDECORATED);
            Pane page = (Pane)loader.load();
            Scene scene = new Scene(page);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            stage.setScene(scene);
            stage.setResizable(false);
            setWindowSettings(page, stage);

            //makes a reference to the application controller and sets the datafields.
            OptionsController controller = loader.getController();
            controller.setStage(stage);
            controller.setData();


            //wait for the user to finish in the popup window before it continues
            stage.showAndWait();
            return controller.getOk();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

	public void setWindowSettings(Node n, Stage stage){
        n.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        n.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });




        //css
        n.getStyleClass().add("rofl");
    }
    public static Media getSelectedSound(){
        return selectedSound;
    }
    public static void setSelectedSound(Media m){
        selectedSound = m;
    }
    public void setStartSound() throws IOException {

       Scanner in = new Scanner(this.getClass().getResourceAsStream("/res/sound.txt"));
        String str = in.nextLine();
        System.out.println(str);
        selectedSound = SoundPlayer.getSounds().get(str);
        in.close();
    }

	public static void main(String[] args) {
		launch(args);
	}
}
