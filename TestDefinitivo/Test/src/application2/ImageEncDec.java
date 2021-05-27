package application2;


import java.io.IOException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.PrintWriter;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

import javax.crypto.spec.SecretKeySpec;
import java.io.IOException; 
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Key;
import java.security.KeyFactory;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;



public class ImageEncDec {

	private final static String RSA = "RSA";
    public static String KeyPrivate;
    public static String KeyPublic;
    public static String UserPublic;
    private static SecretKeySpec secretKey;
    private static byte[] key;
    
    public static String usuariologged;
    public static String contraseñauser;
    public static PrivateKey rk;
    public static PublicKey uk;

    
    
    public static byte[] getFile(File ruta) {
        
        //File f = new File("C:/Users/A/Desktop/Captura.PNG");//ruta
        File f = ruta;
        InputStream is = null;
        try {
            is = new FileInputStream(f);
        } catch (FileNotFoundException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
        byte[] content = null;
        try {
            content = new byte[is.available()];
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        try {
            is.read(content);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return content;
    }

    public static byte[] encryptFile(Key key, byte[] content) {
        Cipher cipher;
        byte[] encrypted = null;
        try {
            cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            encrypted = cipher.doFinal(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encrypted;

    }

    public static byte[] decryptFile(Key key, byte[] textCryp) {
        Cipher cipher;
        byte[] decrypted = null;
        try {
            cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key);
            decrypted = cipher.doFinal(textCryp);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return decrypted;
    }

    public static void saveFile(byte[] bytes, File ruta) throws IOException {

    	String ruta2 = ruta.getAbsolutePath();
    	ruta2.substring(0,ruta2.length()-10);
    	String [] partes = ruta2.split("\\.");
    	partes[0] = partes[0].substring(0,partes[0].length()-10);
    	partes[0] = partes[0] + "-nuevo";
    	String fin = partes[0] + "." + partes[1];
        FileOutputStream fos = new FileOutputStream(fin); //ruta
        fos.write(bytes);
        fos.close();

    }
    public static void saveEncrypted(byte[] bytes, File ruta) throws IOException {

    	String ruta2 = ruta.getAbsolutePath();
    	String [] partes = ruta2.split("\\.");
    	partes[0] = partes[0] + "-encrypted";
    	String fin = partes[0] + "." + partes[1];
    	
        FileOutputStream fos = new FileOutputStream(fin); //ruta
        fos.write(bytes);
        fos.close();

    }
    
    public static void generateKey(String ruta, String usuario) throws Exception
    {
    /*SecureRandom sos = new SecureRandom();
     KeyPairGenerator gen = KeyPairGenerator.getInstance(RSA);
     gen.initialize(512, sos);
     KeyPair keyPair = gen.generateKeyPair();
     uk = keyPair.getPublic();
     rk = keyPair.getPrivate();
     System.out.println(sos);
     System.out.println("UK " + uk);
     System.out.println("RK: " +rk);
     
     
   */    String ejemplo = ruta.substring(0, ruta.lastIndexOf("\\"));
    //	  KeyPrivate = ejemplo + "\\ClavePrivada.txt";
    	//  KeyPublic = ejemplo + "\\ClavePublica.txt"; 

     UserPublic= ejemplo + "\\usuarios.txt"; /*

     
     

     System.out.println(ruta);
     System.out.println(ejemplo);
    System.out.println(KeyPrivate);
    System.out.println(KeyPublic);
              //------------------------------------------- CLAVES PRIV Y PUB -------------------------------------------//
    PrintWriter writer1 = new PrintWriter(KeyPublic, "UTF-8");
     writer1.println(uk);
     writer1.close();
     
     
     
     PrintWriter writer2 = new PrintWriter(KeyPrivate, "UTF-8");
     String devuelverk = encryptPrivateKeyAES(rk);
     writer2.println(devuelverk);
     writer2.close(); */
                 //------------------------------------------- ----------------- -------------------------------------------//
     
  //FASE3 leer el txt de usuarios para no sobreescribir la informacion al guardar uno nuevo
    String texto = "";
    boolean a = false;
    System.out.println("tus muertos -1");
    try(BufferedReader br = new BufferedReader(new FileReader(UserPublic))) {
    	 System.out.println("tus muertos -0.5");
           String line = br.readLine();
         
           while (line != null ) {
        	   if(line.equalsIgnoreCase(usuario)) {
        		   usuariologged = usuario;
		           line = br.readLine();//contraseña
		           contraseñauser = line;
		           line = br.readLine();//privada
		           rk = decryptAESprivatekey(line);
		           line = br.readLine();//publica
		           uk = decryptAESpublickey(line);
        		   a = true;
        		   System.out.println("tus muertos -0.4");
          	}
          	else {
          		 System.out.println("tus muertos -0.3");
		            line = br.readLine(); //USUARIO
		            line = br.readLine();
		            line = br.readLine();
		            line = br.readLine();
		            

          	}

           }

        br.close();
   }
    /*PrintWriter writer = new PrintWriter(UserPublic, "UTF-8");
    System.out.println("tus muertos 0");
    writer.print(texto);//FASE3 escribir lo que ya habia en el documento
    if(a==false) {
    	writer.println(usuario);//FASE3 escribir el usuario del que guardamos la clave
        writer.println("1234");//FASE3 escriir la contraseña del usuario
        String devuelverkg = encryptPrivateKeyAES(rk);//
        writer.println(devuelverkg);//FASE3 escribir la clave privada del usuario tras haberla encriptado con AES
        System.out.println("tus muertos 1");
        String devuelveukg = encryptPublicKeyAES(uk);//
        writer.println(devuelveukg);//FASE3.0 escribir la clave publica del usuario tras haberla encriptado con AES
        writer.close();
        System.out.println("tus muertos 2");
    }
    
    writer.close();*/
    } 
    
    public static String encryptPrivateKeyAES(PrivateKey keyrk) {
    	String claveS = Base64.getEncoder().encodeToString(keyrk.getEncoded());
       
        Cipher cipher;
        String myKey = contraseñauser; //CONTRASEÑA
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
    
  //FASE3.0
    public static String encryptPublicKeyAES(PublicKey keyrk) {
    	String claveS = Base64.getEncoder().encodeToString(keyrk.getEncoded());
       
        Cipher cipher;
        String myKey = contraseñauser; //CONTRASEÑA
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
    
    public static byte[] encryptRSA(byte[] text) throws Exception
    {
     
    
     Cipher cipher = Cipher.getInstance(RSA);
     cipher.init(Cipher.ENCRYPT_MODE, uk);
     return cipher.doFinal(text);
    }
    
    public static byte[] decryptRSA(byte[] text, String usuarioext) throws Exception
    {


    //String leeprivada = Files.readString(Paths.get(KeyPrivate));
    String leeprivada = BuscarClavePrivUsuarioExt(usuarioext);
    System.out.println(leeprivada);
    PrivateKey devuelta = decryptAESprivatekey(leeprivada);
    //devuelta = rk; //PROBAR COMENTAR A VER QUE PASA xd
     Cipher cipher = Cipher.getInstance(RSA);
     cipher.init(Cipher.DECRYPT_MODE, devuelta);
     System.out.println("ESTO COMPROBAR RK" + devuelta);
     return cipher.doFinal(text);
    } 
    
    public static String BuscarClavePrivUsuarioExt(String usuarioext) throws IOException {
    	String claveencontrada = null;
    	boolean stop = false;
    	String result = null;
    	try(BufferedReader reader = new BufferedReader(new FileReader(UserPublic))) {
				String line = reader.readLine();
				
 			while (line != null && stop == false) {
 				if(line.equalsIgnoreCase(usuarioext)) {
 					line = reader.readLine();//linea con la contraseña
 					line = reader.readLine();//linea con la clave privada
 					claveencontrada = line;
 					stop = true;//A deja de leer
 				}else {
 					line = reader.readLine();//linea con la contraseña
		            line = reader.readLine();//linea con la clave privada
		            line = reader.readLine();//linea con la clave publica
		            line = reader.readLine();//linea con el siguiente usuario a comprobar
 				}
 			}
 			reader.close();
        }
    			
    	return claveencontrada;
    }
    
    public static PrivateKey decryptAESprivatekey(String claveS) throws Exception
    {
    	System.out.println("*************************** EMPIEZA ****************************************");
    	System.out.println(claveS);
    	byte[] Clavebyte = Base64.getMimeDecoder().decode(claveS);
    	 Cipher cipher;
         byte[] decrypted = null;
         
         String myKey = contraseñauser; //CONTRASEÑA
         byte[] encrypted = null;
         KeyGenerator keyGenerator;
         
         MessageDigest sha = null;
         try {
             key = myKey.getBytes("UTF-8");
             sha = MessageDigest.getInstance("SHA-512");
             key = sha.digest(key);
             key = Arrays.copyOf(key, 16); 
             secretKey = new SecretKeySpec(key, "AES");
             System.out.println(secretKey);
             try {
            	 cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
                 cipher.init(Cipher.DECRYPT_MODE, secretKey);
                 System.out.println(cipher);
                 decrypted = cipher.doFinal(Clavebyte);
                 
              
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
         

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decrypted);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PrivateKey privKey = kf.generatePrivate(keySpec);
        
        return privKey;
    }
    
    //FASE3.0
    public static PublicKey decryptAESpublickey(String claveS) throws Exception
    {
    	byte[] Clavebyte = Base64.getMimeDecoder().decode(claveS);
    	 Cipher cipher;
         byte[] decrypted = null;
         
         String myKey = contraseñauser; //CONTRASEÑA
         byte[] encrypted = null;
         KeyGenerator keyGenerator;
         
         MessageDigest sha = null;
         try {
             key = myKey.getBytes("UTF-8");
             sha = MessageDigest.getInstance("SHA-2");
             key = sha.digest(key);
             key = Arrays.copyOf(key, 16); 
             secretKey = new SecretKeySpec(key, "AES");
             
             try {
            	 cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
                 cipher.init(Cipher.DECRYPT_MODE, secretKey);
                 decrypted = cipher.doFinal(Clavebyte);
              
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
         
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decrypted);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PublicKey pubkey = kf.generatePublic(keySpec);
        
        return pubkey;
    }
    
    public static byte[] segundoEncrypt(PublicKey key, byte[] text) throws Exception
    {
     
    
     Cipher cipher = Cipher.getInstance(RSA);
     cipher.init(Cipher.ENCRYPT_MODE, key);
     return cipher.doFinal(text);
    }
    
    public static byte[] segundoDecrypt(PrivateKey key, byte[] text) throws Exception
    {
     
    
    	Cipher cipher = Cipher.getInstance(RSA);
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(text);
    }
    
    
    /************************** SALT ****************************************/
    /*
    SecureRandom random = new SecureRandom();
    byte[] salt = new byte[16];
    random.nextBytes(salt);

    MessageDigest md = MessageDigest.getInstance("SHA-512");
    md.update(salt);

    byte[] hashedPassword = md.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));
    */
}

