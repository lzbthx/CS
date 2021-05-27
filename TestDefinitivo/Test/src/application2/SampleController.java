package application2;

import java.io.BufferedReader;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

import application2.ImageEncDec;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class SampleController {
	@FXML private Text actiontarget;
	String rutaclaves = "C:\\ApplicationCS\\Claves.txt";
	public static String id = null;
	public static LoginManager lm;
	public static Scene sc = null;
	@FXML private Button encriptar;
	@FXML public PasswordField password;
    public static String user = null;
	public static String UsuarioArchivoADesencriptar;
    public static PublicKey variablepublickey;
    public static PrivateKey variableprivatekey;
	
	
	public void setUser(String u) {
		user = u;
	}
	
	public void start(final Stage primaryStage) {
	    Button btn = new Button();
	    btn.setText("Open Dialog");
	    System.out.println("llega");
	    btn.setOnAction(
	        new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	                final Stage dialog = new Stage();
	                dialog.initModality(Modality.APPLICATION_MODAL);
	                dialog.initOwner(primaryStage);
	                VBox dialogVbox = new VBox(20);
	                dialogVbox.getChildren().add(new Text("This is a Dialog"));
	                Scene dialogScene = new Scene(dialogVbox, 300, 200);
	                dialog.setScene(dialogScene);
	                dialog.show();
	            }
	         });
	    }
	
    @FXML protected void handleSubmitButtonAction(ActionEvent event) throws IOException {
    	
    	FileChooser fileChooser = new FileChooser();
    	 			fileChooser.setTitle("Open Resource File");
		KeyGenerator keyGenerator;
		System.out.println("Usuario inicializado" + user);
		try {
			keyGenerator = KeyGenerator.getInstance("AES");
			keyGenerator.init(128);
			Key key = keyGenerator.generateKey();
	        System.out.println(key);
	        
	        fileChooser.getExtensionFilters().addAll(
	    	         new ExtensionFilter("Text Files", "*.txt"),
	    	         new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
	    	         new ExtensionFilter("Audio Files", "*.wav", "*.mp3", "*.aac"),
	    	         new ExtensionFilter("All Files", "*.*"));
	    	 String cosa = null;

	         
	 		if(event.getSource().toString().equalsIgnoreCase("Button[id=enc, styleClass=button]'Encriptar'")) {
	 			File selectedFile = fileChooser.showOpenDialog(new Stage());
		    	 System.out.println(selectedFile);
		    	 byte[] content = ImageEncDec.getFile(selectedFile);
		         System.out.println(content);
		         
		         //Encriptar
		         byte[] encrypted = ImageEncDec.encryptFile(key, content);
		         System.out.println(encrypted);
		         
		         String ruta2 = selectedFile.getAbsolutePath();
		         try {
			 			ImageEncDec.generateKey(ruta2, user);
			 		} catch (Exception e1) {
			 			// TODO Auto-generated catch block
			 			e1.printStackTrace();
			 		}
		         String ejemplo = ruta2.substring(0, ruta2.lastIndexOf("\\"));
		         
		         ejemplo = ejemplo + "\\Claves.txt";
		         System.out.println("Ejemplo: " + ejemplo);
		         rutaclaves = ejemplo;
		         byte encoded[] = key.getEncoded();
		         try {
						encoded = ImageEncDec.encryptRSA(encoded);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} //RESOLVER
		         String encodedKey = Base64.getEncoder().encodeToString(encoded);
		        
		         //String infoClaves = Files.readString(Paths.get(ejemplo));//A lo que habia escrito en el documento
		         String fileName = selectedFile.getName();//A nombre del archivo para guardarlo junto a su clave
		         String texto = "";
		         try(BufferedReader br = new BufferedReader(new FileReader(rutaclaves))) {
			           
			        	String line = br.readLine();
			            boolean a = false;
			            while (line != null && a == false) {
			            	//String [] nombre = line.split("&");
			            	if(line.equalsIgnoreCase(fileName)) {
			            		 line = br.readLine();
						         line = br.readLine();
						         line = br.readLine();
			            		
			            	}
			            	else {
				            	texto = texto + line + "\n";
					            line = br.readLine();

			            	}
			            }
			            
	 				br.close();
		        }
		         PrintWriter writer = new PrintWriter(ejemplo, "UTF-8");
		         writer.print(texto);//A escribir lo que ya habia en el documento
		         writer.println(fileName);//A escribir el nombre del archivo del que guardamos la clave
		         writer.println(encodedKey);
		         writer.println(user);
		         writer.close();
		         
		         
	         /*
	         if(Files.exists(firstPath) == false) {
	        	 writer = new PrintWriter(ejemplo, "UTF-8");
		         String ruta3 = selectedFile.getAbsolutePath();
	     		 String [] partes = ruta3.split("\\.");
	     		 partes[0] = partes[0] + "-encrypted";
	     		 String fin = partes[0] + "." + partes[1];
	             //writer.println(fin.substring(selectedFile.getAbsolutePath().lastIndexOf("\\")+1) + "&" + encodedKey + "&" + selectedFile.getParent().toString()  + "\n");
		         //writer.close();
	         }
	         else {
	        	 String ruta3 = selectedFile.getAbsolutePath();
     			 String [] partes = ruta3.split("\\.");
	     		 partes[0] = partes[0] + "-encrypted";
	     		 String fin = partes[0] + "." + partes[1];
	        	 String textToAppend = fin.substring(selectedFile.getAbsolutePath().lastIndexOf("\\")+1)+"" + "&" + encodedKey + 
	        			 "&" + selectedFile.getParent().toString() + "\n"; //new line in content
	        	 Files.write(firstPath, textToAppend.getBytes(), StandardOpenOption.APPEND);
	         }
	         */
	         
	         cosa = Files.readString(Paths.get(ejemplo));
	         System.out.println("Cosa: " + cosa);
	         
	         ImageEncDec.saveEncrypted(encrypted, selectedFile);

			 }
	 		
	         //Desencriptar
	 		
	 		System.out.println("rutaclaves " + rutaclaves);
	 		
	 		if(event.getSource().toString().equalsIgnoreCase("Button[id=des, styleClass=button]'Desencriptar'")) {
	 			List<String> choices = new ArrayList<>();
	 			boolean stop = false;
	 			System.out.println("rutaclaves" + rutaclaves);
	 			
	 				try(BufferedReader br = new BufferedReader(new FileReader(rutaclaves))) {
				           
			        	System.out.println("rutaclaves" + rutaclaves);
			        	String line = br.readLine();
			            boolean a = false;
			            while (line != null && a == false) {
			            	//String [] nombre = line.split("&");
			            	choices.add(line);//choices.add(nombre[0]);
			            	System.out.println(line);
				            line = br.readLine();
				            line = br.readLine();
				            line = br.readLine();
			            }
			            
	 				br.close();
		        }
	 			
	 			
	 			
	 			ChoiceDialog<String> dialog = new ChoiceDialog<>("", choices);
	 			dialog.setTitle("Desencriptar");
	 			dialog.setHeaderText("Desencriptar archivos");
	 			dialog.setContentText("Escoja un archivo:");
	 			
	 			// Traditional way to get the response value.
	 			Optional<String> result = dialog.showAndWait();
	 			if (result.isPresent()){
	 				String rutades = null;
	 				File parent = new File(rutaclaves);
	 				rutades = parent.getParent();
	 				
	 				try(BufferedReader reader = new BufferedReader(new FileReader(rutaclaves))) {
		 				String line = reader.readLine();
		 				
			 			while (line != null && stop == false) {
			 				if(line.equalsIgnoreCase(result.get())) {
			 					line = reader.readLine();//A lee la siguiente linea a la del nombre del archivo que debe de ser la clave del mismo
			 					cosa = line;
			 					line = reader.readLine();
			 					UsuarioArchivoADesencriptar = line;
			 					stop = true;//A deja de leer
			 				}else {
			 					line = reader.readLine();
					            line = reader.readLine();
					            line = reader.readLine();
			 				}
			 			}
			 			reader.close();
			        }
		 			
	 				/*try(BufferedReader br = new BufferedReader(new FileReader(rutaclaves))) {
	 					System.out.println(rutaclaves);
 					    String line = br.readLine();
 					    boolean a = false;
	 					
 					    while (line != null && a == false) {
	 						String [] nombre = line.split("&");
	 						System.out.println(nombre[0]);
	 						
	 						if(result.get().equalsIgnoreCase(nombre[0])) {
	 							rutades = nombre[2];
			            		a = true;
			            	}
			            	else
			            		line = br.readLine();
				            }
				        }
	 				 */
	 				System.out.println(result.get());
	 				String [] r = result.get().split("\\.");
	 				System.out.println(r[0]);
	 				System.out.println(r[1]);
	 				String var = r[0] + "-encrypted." + r[1]; 
	 				String selectedFile = rutades + "\\" + var;
	 				
	 			    System.out.println("Ruta: " + selectedFile );
	 			    System.out.println("Archivo: " + result.get());
	 			    File f = new File(selectedFile);
		 	        InputStream is = null;
		 	        
		 	        try {
		 	            is = new FileInputStream(f);
		 	        } catch (FileNotFoundException e2) {
		 	            e2.printStackTrace();
		 	        }
		 	        
		 	        byte[] content1 = null;
		 	        
		 	        try {
		 	            content1 = new byte[is.available()];
		 	        } catch (IOException e1) {
		 	            e1.printStackTrace();
		 	        }
		 	        
		 	        try {
		 	            is.read(content1);
		 	        } catch (IOException e) {
		 	            e.printStackTrace();
		 	        }
		 	      
			        /*try(BufferedReader br = new BufferedReader(new FileReader(f.getParent() + "\\Claves.txt"))) {
			           
			        	System.out.println(f.getParent() + "\\Claves.txt");
			        	String line = br.readLine();
			            boolean a = false;
			            while (line != null && a == false) {
			            	String [] nombre = line.split("&");
			            	System.out.println(nombre[0]);
			            	System.out.println(f.getAbsolutePath().substring(f.getAbsolutePath().lastIndexOf("\\")+1));
			            	if(nombre[0].equalsIgnoreCase( f.getAbsolutePath().substring(f.getAbsolutePath().lastIndexOf("\\")+1))) {
			            		cosa = nombre[1];
			            		a = true;
			            	}
			            	else {
			            		line = br.readLine();
			            	}
			            }
			        }*/
			        if(cosa != null) {
			        	System.out.print(cosa);
			        	byte[] decodedKey = Base64.getMimeDecoder().decode(cosa);
			        	   try {
			   				decodedKey = ImageEncDec.decryptRSA(decodedKey, UsuarioArchivoADesencriptar);
			   			} catch (Exception e) {
			   				// TODO Auto-generated catch block
			   				e.printStackTrace();
			   			}
			        	   if(!UsuarioArchivoADesencriptar.equalsIgnoreCase(user)){
								//encriptamos con la clave publica de usuario actual
								//debe ser la variable global 

								byte[] claveRSA;
								try {
									claveRSA = ImageEncDec.segundoEncrypt(variablepublickey, content1);
									content1 = ImageEncDec.segundoDecrypt(variableprivatekey, claveRSA);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
			        	
			        	Key key1 = new SecretKeySpec(decodedKey,0,decodedKey.length, "AES");
			        	System.out.println(key1);
			        	byte[] decrypted = ImageEncDec.decryptFile(key1, content1);
			        	System.out.println("long : " + decrypted.length);

			        	ImageEncDec.saveFile(decrypted, f);
			        	System.out.println("Done");
			        }
			        else {
			        	System.out.println("Clave errónea");
			        }
	 			}

	 			// The Java 8 way to get the response value (with lambda expression).
	 			result.ifPresent(letter -> System.out.println("Archivo: " + letter));
	 		}
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
    }
    public static void setScene(Scene scene) {
        sc = scene;
        System.out.println("Scene: " + sc);
    }
}