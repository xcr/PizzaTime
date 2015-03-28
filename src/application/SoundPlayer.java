package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

/**
 * Created by Eirik on 13.03.2015.
 */

public class SoundPlayer {

  //  private Media bip = new Media(new File("src/wav/TMNT - PizzaTime.wav").toURI().toString());
    private  static MediaPlayer mediaPlayer;
    private static ObservableMap<String,Media> sounds = FXCollections.observableHashMap();
    private static int i = 0;
    private static String path = "src/wav/";


    public static ObservableMap<String, Media> getSounds() {
        return sounds;
    }

    public static void playSound(Media bip){
        mediaPlayer = new MediaPlayer(bip);
        mediaPlayer.play();
    }

    public  void importFiles() throws IOException, URISyntaxException {


            ClassLoader loader = SoundPlayer.class.getClassLoader();
            InputStream in = loader.getResourceAsStream(".");
            BufferedReader rdr = new BufferedReader(new InputStreamReader(in));
            String line;

            while ((line = rdr.readLine()) != null) {
                if(!line.equals("wav") && line.substring(line.length()-3,line.length()).equals("wav")){
                    System.out.println("Line: "+ "C:/Users/Eirik/Git/pizzatime/src/wav/"+line);
                      sounds.put(line.substring(0, line.length() - 4),new Media(this.getClass().getResource("src/wav/"+line).toURI().toString()));
              
                }
            }
            rdr.close();


        /*
        Files.walk(Paths.get("src/wav/")).forEach(filePath -> {
            if (Files.isRegularFile(filePath) && i > 0) {
                String str = filePath.getFileName().toString();
                System.out.println(i + str);


            }
            i++;
        });
/*
        sounds.put("TMNT: Turtles in time - Pizza Time", new Media(new File("res/wav/TMNT - PizzaTime.wav").toURI().toString()));
        sounds.put("Majora's Mask - Get Mask", new Media(new File("res/wav/Majora's Mask - Get Mask.wav").toURI().toString()));
        sounds.put("Starcraft2 - Spawn More Overlords", new Media(new File("res/wav/spawn more overlords.wav").toURI().toString()));
        sounds.put("HAH GAYYY", new Media(new File("res/wav/HahGay.wav").toURI().toString()));
*/

    }





}
