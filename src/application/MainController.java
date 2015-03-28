package application;

import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MainController implements Initializable{
	
	@FXML
	private Label timer = new Label();
	@FXML
	public TextField text = new TextField();
	@FXML
	private Button closeButton;
	
	private static long seconds;
	private InputStream pizzaStream;
	private static boolean timerActive = false;
    private MainApp main;
    private Stage stage;

    public void setMain(MainApp main){
        this.main = main;
    }





    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("lol");
        closeButton.setStyle(         "-fx-min-width: 20; " +
                "-fx-min-height: 20; " +
                "-fx-max-width: 20; " +
                "-fx-max-height: 20;" +
                "-fx-font-weight: bold;" +
        "-fx-font-size: 9;");
    }
	
	public void setLabel(String text){
		timer.setText(text);
	}
	
	@FXML
	public void addTime(ActionEvent event){
		this.seconds += 16;
		setLabel(getTimeString());	
	}
	
	@FXML
	public void removeTime(ActionEvent event){
		this.seconds -= 16;
		if(this.seconds < 0){
			setLabel("00:00");
		}
		else{
			setLabel(getTimeString());	
		}
	}


	@FXML
	public void handleStart15(){
		String str = "15";

		//this.seconds = 3;
		this.seconds = Long.parseLong(str) * 60;
		if(timerActive == false){
			countDown();
		}
		else{
			System.out.println("Can only run one timer at the time");
		}
	}
	@FXML
	public void handleStart20(){
		String str = "20";

		//this.seconds = 3;
		this.seconds = Long.parseLong(str) * 60;
		if(timerActive == false){
			countDown();
		}
		else{
			System.out.println("Can only run one timer at the time");
		}
	}
	@FXML
	public void timerLabel(ActionEvent event){
		String str = text.getText();
        if(str.toLowerCase().equals("sounds")){
            main.showOptions();
        }
		if(!isNumeric(str) || str.length() > 10){
			return;
			}
		//this.seconds = 3;
		this.seconds = Long.parseLong(str) * 60;
		if(timerActive == false){
			countDown();
		}
		else{
			System.out.println("Can only run one timer at the time");		
		}
	}
	
	public static String getTimeString(){
		String printm = "" + (seconds / 60);
		String prints = "" + (seconds%60);
		if((seconds / 60) < 10){
			printm = "0"+ (seconds / 60);
		}
		if((seconds%60) < 10){
			prints = "0" + (seconds%60);
		}
		return ""+printm + ":" + prints;
	}
///wav/TMNT turtles in time - Pizza Time.wav
	public void countDown(){
		this.timerActive = true;
		this.pizzaStream = this.getClass().getResourceAsStream("/res/wav/Majora's Mask - Get Mask.wav");
		final Timer tmr = new Timer();
	    tmr.scheduleAtFixedRate(new TimerTask() {
	    	@Override
	        public void run() {
	        	Platform.runLater(() -> {
                    if(seconds <= -1){
                        setLabel("00:00");
                        SoundPlayer.playSound(main.getSelectedSound());
                        tmr.cancel();
                        MainController.timerActive = false;
                    }

                    if(seconds > -1){
                        setLabel(MainController.getTimeString());
                    }
                    seconds--;
                });
	        }
	    },0, 1000);    
	}
	
	@SuppressWarnings("unused")
	public static boolean isNumeric(String str)  
	{
		for (int i = 0; i < str.length(); i++) {
			if(!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

        public void handleExit(ActionEvent actionEvent) {
            this.stage.close();

    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }


}
