package application2;

import java.io.IOException;
import java.util.logging.*;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/** Manages control flow for logins */
public class LoginManager {
	private Scene scene;
	public String id = null;
	@FXML private Button desencriptar;

	public LoginManager(Scene scene) {
		this.scene = scene;
	}

	/**
	 * Callback method invoked to notify that a user has been authenticated.
	 * Will show the Sample application screen.
	 */ 
	public void authenticated(String sessionID) {
		System.out.println("Usuario correcto.");
		showSample(sessionID);
		//showMainView(sessionID);
	}

	/**
	 * Callback method invoked to notify that a user has logged out of the main application.
	 * Will show the login application screen.
	 */ 
	public void logout() {
		showLoginScreen();
	}
  
  public void showLoginScreen() {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
      
      scene.setRoot((Parent) loader.load());
      
      LoginController controller = loader.<LoginController>getController();
      controller.initManager(this);
      
    } catch (IOException ex) {
      Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  private void showMainView(String sessionID) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("mainview.fxml"));
      scene.setRoot((Parent) loader.load());
      MainViewController controller = loader.<MainViewController>getController();
      controller.initSessionID(this, sessionID);
    } catch (IOException ex) {
    	Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
  public void showPassword() {
	    desencriptar.setOnAction(new EventHandler<ActionEvent>() {
	      @Override public void handle(ActionEvent event) {
	        //loginManager.logout();
	    	  try {
	    		  System.out.println("Muestro la aplicación.");
	    		  FXMLLoader loader = new FXMLLoader(getClass().getResource("password.fxml"));
	    	      scene.setRoot((Parent) loader.load());
	    		  //scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	    		  Stage stage = new Stage();
	    		  stage.setScene(scene);
	    		  stage.show(); 
	    		
	    	  }catch(IOException ex) {
	        	Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
	        	System.out.println("Error");
	    	  }
	      }
	    });
	  }
  
  private void showSample(String sessionID) {
	  try {
		  System.out.println("Muestro la aplicación.");
		  BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("Sample.fxml"));
		  Scene scene = new Scene(root,955,570);
		  //scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		  Stage stage = new Stage();
		  stage.setScene(scene);
		  stage.show(); 
		
	  }catch(IOException ex) {
    	Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
    	System.out.println("Error");
	  }
  }
}