package application;



import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class MainController implements Initializable{
	
	@FXML
	private Label timer = new Label();
	@FXML
	private TextField text = new TextField();
	@FXML
	private Button closeButton;
	private static long seconds;
	private InputStream pizzaStream;
	private static boolean timerActive = false;


	
	public void showStage(){
		Platform.runLater(new Runnable() {
    		@Override
            public void run() {
    			try{
    				Parent root = FXMLLoader.load(getClass().getResource("/fxml/popup.fxml"));
    				Stage newStage = new Stage();
    				Scene stageScene = new Scene(root);
    				newStage.setScene(stageScene);
    				newStage.show();
    				
    			}catch(Exception e) {
    				e.printStackTrace();
    			}	
	            
    		}
        });
		
	}
	@FXML
	public void closePopup(ActionEvent event){
		// get a handle to the stage
	    Stage stage = (Stage) closeButton.getScene().getWindow();
	    // do what you have to do
	    stage.close();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
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
	public void timerLabel(ActionEvent event){
		String str = text.getText();
		if(!isNumeric(str) || str.length() > 10){
			showStage();
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
       
		
	
	
	public void countDown(){
		this.timerActive = true;

		this.pizzaStream = this.getClass().getResourceAsStream("/wav/29.wav");

		Timer tmr = new Timer();
	    tmr.scheduleAtFixedRate(new TimerTask() {
	    	@Override
	        public void run() {
	        	Platform.runLater(new Runnable() {
	        		@Override
	                public void run() {
	        			if(seconds <= -1){
	        				setLabel("00:00");
    		            	MakeSound m = new MakeSound();
    		            	m.playSound(pizzaStream);
    		            	tmr.cancel();
    		            	MainController.timerActive = false;
	        			}
    					
    		            if(seconds > -1){
    		            	setLabel(MainController.getTimeString());
    		            }
    		            seconds--;
    		            
    		            
	        		}
	            });
	        }
	    },0, 1000);    
		/*
		System.out.println("\n\nPIZZA TIME!!!!!!!!!");
		System.out.println("    _.:`.--|--.`:._");
		System.out.println("  .: .'\\o  | o /'. '.");
		System.out.println(" // '.  \\ o|  /  o '.\\");
		System.out.println("//'._o'. \\ |o/ o_.-'o\\\\");
		System.out.println("|| o '-.'.\\|/.-' o   ||");
		System.out.println("||--o--o-->|<o-----o-||");
		System.out.println("\\\\  o _.-'/|\\'-._o  o//");
		System.out.println(" \\\\.-'  o/ |o\\ o '-.//");
		System.out.println("  '.'.o / o|  \\ o.'.'");
		System.out.println("    `-:/.__|__o\\:-'");
		System.out.println("       `\"--=--\"`");
		*/
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
	

}