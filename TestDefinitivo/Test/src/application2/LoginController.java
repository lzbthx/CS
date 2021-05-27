package application2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import application2.SampleController;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.shape.Path;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/** Controls the login screen */
public class LoginController {
  @FXML private TextField user;
  @FXML public  PasswordField password;
  @FXML private Button loginButton;
  public String usuario;
  public String contra;
  public boolean existeu = false;
  public boolean existep = false;
  private final static String RSA = "RSA";
  private static SecretKeySpec secretKey;
  private static byte[] key;
  
  public void initialize() {}
  
  public void initManager(final LoginManager loginManager) {
    loginButton.setOnAction(new EventHandler<ActionEvent>() {

      @Override public void handle(ActionEvent event) {
        String sessionID = authorize();
        if (sessionID != null) {
          loginManager.authenticated(sessionID);
          	
        //Para cerrar la ventana actual
          Stage stage = (Stage) loginButton.getScene().getWindow();
          SampleController.setScene(loginButton.getScene());
          stage.close();
        }
      }
    });
  }

  /**
   * Check authorization credentials.
   * 
   * If accepted, return a sessionID for the authorized session
   * otherwise, return null.
   */   
  private String authorize() {
	    String texto = "";
	    boolean a = false;
	   
	    ArrayList<String> users = new ArrayList<String>(); 
	    ArrayList<String> pass = new ArrayList<String>(); 
	  //comprobamos usuario  
	  try(BufferedReader br = new BufferedReader(new FileReader("C:\\ApplicationCS\\usuarios.txt"))) {
	           String line = br.readLine();
	           
	           while (line != null ) {
	        	   		
	        		   texto = line;
	        		   usuario = texto;
	        		   a = true;
	        		   users.add(usuario);
	        		   texto = texto + line + "\n";
				       line = br.readLine();
				       line = br.readLine();
				       line = br.readLine();
				       System.out.println(usuario);
	           }
	           System.out.println(users);

	        br.close();
	   } catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  
	  
	  texto = "";
	  a = false;
	  //comprobamos contraseña 
	  System.out.println(password.getText());
	  String userpasscom = encriptasalt(password.getText());
	  System.out.println(userpasscom);
		  try(BufferedReader br2 = new BufferedReader(new FileReader("C:\\ApplicationCS\\usuarios.txt"))) {
		           String line2 = br2.readLine();
		           line2 = br2.readLine();
		           
		           while (line2 != null ) {
		        	   		
		        		   texto = line2;
		        		   contra = texto;
		        		   a = true;
		        		   pass.add(contra);
		        		   texto = texto + line2 + "\n";
					       line2 = br2.readLine();
					       line2 = br2.readLine();
					       line2 = br2.readLine();
					       System.out.println(contra);
		           }
		           System.out.println(pass);	

		        br2.close();
		   } catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  for(int i=0;i< users.size() && existeu == false ; i++) {
			  if(users.get(i).equalsIgnoreCase(user.getText()) && pass.get(i).equalsIgnoreCase(userpasscom) ) {
				  System.out.println("***********************USUARIO******************************");
				  System.out.println(users.get(i));
				  existeu = true;
				  existep = true;
		          SampleController.user = user.getText();

			  	}
			  if(users.get(i).equalsIgnoreCase(user.getText()) && pass.get(i).equalsIgnoreCase(userpasscom)==false ) {
				  
				  Alert dialogoAlerta = new Alert(AlertType.INFORMATION);
				  dialogoAlerta.setTitle("¡ERROR!");
				  dialogoAlerta.setHeaderText(null);
				  dialogoAlerta.setContentText("Contraseña incorrecta");
				  dialogoAlerta.initStyle(StageStyle.UTILITY);
				  dialogoAlerta.showAndWait();
				  existep = false;
				  existeu = true;
			  	}
			  
			  }
			  
		  if(existeu == false) {
			  Alert dialogoAlerta = new Alert(AlertType.INFORMATION);
			  dialogoAlerta.setTitle("¡ERROR!");
			  dialogoAlerta.setHeaderText(null);
			  dialogoAlerta.setContentText("Usuario no encontrado en el sistema, se creará un nuevo");
			  dialogoAlerta.initStyle(StageStyle.UTILITY);
			  dialogoAlerta.showAndWait();
			  crearUsuario();
			  
		  }
		  for(int i2=0;i2< pass.size() && existep == false ; i2++) {
			  if(pass.get(i2).equalsIgnoreCase(userpasscom)) {
				  
				  existep = true;
				  
			  }
			  
		  }
		  

    return 
    		
      //usuario.equals(user.getText()) && "1234".equals(password.getText()) 
    		existeu && existep
            ? generateSessionID()
            : null;
  }
  

	/*public String encriptasalt(String contrasenasalt) {
		byte[] hashedPassword = null;
		String clavestring = null;
		 	SecureRandom random = new SecureRandom();
		    byte[] salt = new byte[16];
		    random.nextBytes(salt);

		    MessageDigest md;	
			try {
				md = MessageDigest.getInstance("SHA-512");

			    md.update(salt);

			    hashedPassword = md.digest(contrasenasalt.getBytes(StandardCharsets.UTF_8));
			    
			    clavestring = new String(hashedPassword);
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		
		return clavestring;
	}*/
  
  
  public String encriptasalt(String contrasenasalt){
	    MessageDigest md = null;
	    try {
	        md = MessageDigest.getInstance("SHA-256");
	    } 
	    catch (NoSuchAlgorithmException e) {
	        e.printStackTrace();
	        return null;
	    }

	    byte[] hash = md.digest(contrasenasalt.getBytes());
	    StringBuffer sb = new StringBuffer();

	    for(byte b : hash) {
	        sb.append(String.format("%02x", b));
	    }

	    return sb.toString();
	    }
	
  
	
	/*public String desencriptasalt(String contrasenasalt) {
		byte[] hashedPassword = contrasenasalt.getBytes();
		 	SecureRandom random = new SecureRandom();
		    byte[] salt = new byte[16];
		    random.nextBytes(salt);

		    MessageDigest md;
			try {
				md = MessageDigest.getInstance("SHA-512");

			    md.update(salt);

			    hashedPassword = md.digest(contrasenasalt.getBytes(StandardCharsets.UTF_8));
			    
			    clavestring = new String(hashedPassword);
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		
		return clavestring;
	}
   */
  private void crearUsuario() {
	String userpass = encriptasalt(password.getText());
	String textToAppend = user.getText() + "\n" + userpass + "\n";
	SampleController.user = user.getText();
	
	try {
	    existeu=existep=true;
	    SecureRandom sos = new SecureRandom();
	    KeyPairGenerator gen;
		try {
			gen = KeyPairGenerator.getInstance(RSA);
			 gen.initialize(512, sos);
		     KeyPair keyPair = gen.generateKeyPair();
		     PublicKey uk = keyPair.getPublic();
		     PrivateKey rk = keyPair.getPrivate();
		     System.out.println(sos);
		     System.out.println("UK " + uk);
		     System.out.println("RK: " +rk);
		     String claveencr = encryptPrivateKeyAES(rk);
		     System.out.println("Clave privada ejemplo:" + claveencr);
		     String devuelveukg = encryptPublicKeyAES(uk);
		     textToAppend += claveencr + "\n"+devuelveukg+ "\n";
			 Files.write(Paths.get("C:\\ApplicationCS\\usuarios.txt"), textToAppend.getBytes(), StandardOpenOption.APPEND);

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	     
	}catch (IOException e) {
	    //exception handling left as an exercise for the reader
	}
	
}
  
  public String encryptPublicKeyAES(PublicKey keyrk) {
  	String claveS = Base64.getEncoder().encodeToString(keyrk.getEncoded());
     
      Cipher cipher;
      String myKey = password.getText(); //CONTRASEÑA
      byte[] encrypted = null;
      KeyGenerator keyGenerator;
      
      MessageDigest sha = null;
      try {
          key = myKey.getBytes("UTF-8");
          sha = MessageDigest.getInstance("SHA-512");
          key = sha.digest(key);
          key = Arrays.copyOf(key, 16); 
          secretKey = new SecretKeySpec(key, "AES");
          
          try {
              cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
              cipher.init(Cipher.ENCRYPT_MODE, secretKey);
              encrypted = cipher.doFinal(keyrk.getEncoded());
              
              return Base64.getEncoder().encodeToString(encrypted);
          } catch (Exception e) {
              e.printStackTrace();
          }
      } 
      catch (NoSuchAlgorithmException e) {
          e.printStackTrace();
      } 
      catch (UnsupportedEncodingException e) {
          e.printStackTrace();
      }
      return Base64.getEncoder().encodeToString(encrypted);
      //return claveS;
  }
  
  public String encryptPrivateKeyAES(PrivateKey keyrk) {
  	String claveS = Base64.getEncoder().encodeToString(keyrk.getEncoded());
     
      Cipher cipher;
      String myKey = password.getText(); //CONTRASEÑA
      byte[] encrypted = null;
      KeyGenerator keyGenerator;
      
      MessageDigest sha = null;
      try {
          key = myKey.getBytes("UTF-8");
          sha = MessageDigest.getInstance("SHA-1");
          key = sha.digest(key);
          key = Arrays.copyOf(key, 16); 
          secretKey = new SecretKeySpec(key, "AES");
          
          try {
              cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
              cipher.init(Cipher.ENCRYPT_MODE, secretKey);
              encrypted = cipher.doFinal(keyrk.getEncoded());
              
              return Base64.getEncoder().encodeToString(encrypted);
          } catch (Exception e) {
              e.printStackTrace();
          }
      } 
      catch (NoSuchAlgorithmException e) {
          e.printStackTrace();
      } 
      catch (UnsupportedEncodingException e) {
          e.printStackTrace();
      }
      return Base64.getEncoder().encodeToString(encrypted);
      //return claveS;
  }
  
  
private static int sessionID = 0;

  private String generateSessionID() {
    sessionID++;
    return "xyzzy - session " + sessionID;
  }
}