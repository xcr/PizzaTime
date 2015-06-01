package application;
	


import javafx.application.Application;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.StageStyle;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;

import java.nio.file.*;
import java.util.*;
import java.util.stream.Stream;



public class MainApp extends Application {

    private double xOffset;
    private double yOffset;
    private Stage primaryStage;
    private MainController mc;
    private static  String selectedSound;

    private static ArrayList<String> sounds = new ArrayList<String>();


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
			scene.getStylesheets().add(getClass().getResource("/css/application.css").toExternalForm());

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

            loadSounds();

            setStartSound();

            System.out.println((System.getProperty("user.home") + "\\PizzaTime"));
            setUpPreferenceFolder();


		} catch(Exception e) {
			e.printStackTrace();
		}

     //  SoundPlayer.playSound(SoundPlayer.getSounds().get("TMNT: Turtles in time - Pizza Time"));
       // SoundPlayer.playSound(SoundPlayer.getSounds().get("Majora's Mask - Get Mask"));
        //SoundPlayer.playSound(SoundPlayer.getSounds().get("Starcraft 2 - Spawn more overlords"));
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
            scene.getStylesheets().add(getClass().getResource("/css/application.css").toExternalForm());
            stage.setScene(scene);
            stage.setResizable(false);
            setWindowSettings(page, stage);

            //makes a reference to the application controller and sets the datafields.
            OptionsController controller = loader.getController();
            controller.setMain(this);
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

    public ArrayList<String> getSounds(){
        return this.sounds;
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
    public String getSelectedSound(){
        return selectedSound;
    }
    public void setSelectedSound(String m){
        selectedSound = m;
    }
    public void setStartSound() throws IOException{


        //AudioClip plonkSound = new AudioClip(this.getClass().getResource("/wav/Dexter - Launchee Time.wav").toURI().toString());
      // playSound(selectedSound);

       Scanner in;
        File file = new File(System.getProperty("user.home")+"\\PizzaTime\\sound.cfg");
        if (file.exists()) {
            // ...
             in = new Scanner(file);
        }
        else{
        in = new Scanner(this.getClass().getResourceAsStream("/res/sound.txt"));
        }
        this.selectedSound = in.nextLine();
        in.close();

    }
    public void stop() throws FileNotFoundException {
        System.out.println(selectedSound);
        PrintWriter writer = new PrintWriter(System.getProperty("user.home")+"\\PizzaTime\\sound.cfg");
        writer.flush();
        writer.write(selectedSound);
        writer.close();

    }

    public void playSound(String s) {

        AudioClip ac  = null;
        try {
            ac = new AudioClip(this.getClass().getResource("/wav/"+s+".wav").toURI().toString());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        ac.play();
    }

    public void loadSounds() throws IOException {
        URI uri = null;
        try {
            uri = this.getClass().getResource("/wav").toURI();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        Path myPath;
        if (uri.getScheme().equals("jar")) {
            FileSystem fileSystem = FileSystems.newFileSystem(uri, Collections.emptyMap());
            myPath = fileSystem.getPath("/wav");
        } else {
            myPath = Paths.get(uri);
        }
        Stream<Path> walk = Files.walk(myPath, 1);
        for (Iterator<Path> it = walk.iterator(); it.hasNext();){
            String str = it.next().toString();
           // System.out.println(str);
            if(str.endsWith(".wav")){
                String[] parts;
                if(str.startsWith("C")){
                    parts = str.split("\\\\");
                }
                else{
                    parts = str.split("/");
                }
                System.out.println(parts[parts.length-1].substring(0, parts[parts.length - 1].length() - 4));
                sounds.add(parts[parts.length-1].substring(0, parts[parts.length - 1].length()-4));
               // System.out.println(str.substring(0,parts[parts.length-1].length()-4));
            }

        }

    }
    public void setUpPreferenceFolder(){


        File theDir = new File(System.getProperty("user.home")+"\\PizzaTime");

// if the directory does not exist, create it
        if (!theDir.exists()) {
            System.out.println("creating directory: PizzaTime");

            try{
                theDir.mkdir();


            }
            catch(SecurityException se){
                se.printStackTrace();
            }

        }
        else{
            System.out.println("Preference folder already exists");
        }
    }

	public static void main(String[] args) {
		launch(args);
	}
}
