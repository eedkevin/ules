/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package communication;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.JOptionPane;
import sun.misc.BASE64Decoder;

/**
 *
 * @author shiny
 */
public class MountKey {
    
    public String getMK(String all) throws ClassNotFoundException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
       
        String sEncryptedPMK=all.substring(4,178);//检查一下位数
        String sTimeStamp=all.substring(178,191);
        String sSig=all.substring(191);
        
        System.out.println(sEncryptedPMK);
        System.out.println(sEncryptedPMK.length());
        System.out.println(sTimeStamp);
        System.out.println(sTimeStamp.length());
        System.out.println(sSig);
        System.out.println(sSig.length());
        
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] encryptedPMK;
        byte[] unverifiedSig;
        byte[] sigSource;
       
            
//            long timestamp=Long.parseLong(sTimeStamp);
//    
//    //      transform long timestamp to byte[]        
//            byte[] time=new byte[8];
//            long a=timestamp;
//            for(int i=7;i>=0;i--){
//                long temp=a&255;
//                time[i]=(byte)temp;
//                a=a>>>8;               
//            }
            
//            byte plainText[]=new byte[encryptedPMK.length+time.length];
//             byte plainText[]=new byte[encryptedPMK.length+time.length];
//        System.arraycopy(encryptedPMK, 0, plainText, 0, encryptedPMK.length);
//        System.arraycopy(time, 0, plainText, encryptedPMK.length, time.length);
       
       
//     
        
        //combine encrypted_PMK and timestamp into a byte array to get the plainText for the signature
        
       
        //verify the signature
        Signature sig = Signature.getInstance("SHA1WithRSA");  
    
                FileInputStream  fis_pk;    
            try {
                fis_pk = new FileInputStream("resources\\0ServerPublicKey"); //read server's public key from javacard
                ObjectInputStream ois_pk = new ObjectInputStream(fis_pk);
                PublicKey pk=(PublicKey)ois_pk.readObject();
                ois_pk.close();
                fis_pk.close();
                sig.initVerify(pk);
                unverifiedSig = decoder.decodeBuffer(sSig);
                String sSigSource=sEncryptedPMK+sTimeStamp;
                sigSource=decoder.decodeBuffer(sSigSource);
                sig.update(sigSource);
                
                if (!sig.verify(unverifiedSig)) {
                    JOptionPane.showMessageDialog(null, "signature is not verified!\r\nWarning: Your message may be modified during transmission!"
                    + "\r\nPlease make sure you network environment is safe!", "ERROR!", JOptionPane.ERROR_MESSAGE);
                    return "ERROR";
                }else{
                     
                    //verify the timestamp 
                    long timestamp=Long.parseLong(sTimeStamp);
                    System.out.println(System.currentTimeMillis()-timestamp);
                    if(Math.abs(System.currentTimeMillis()-timestamp)>180000){
                        JOptionPane.showMessageDialog(null, "Your key is out of date!", "ERROR!", JOptionPane.ERROR_MESSAGE);
                        return "ERROR";
                    }else{
                        //decrypt potential mount key               
                            FileInputStream fis_prk;
                        try {
                            fis_prk = new FileInputStream("resources\\PrivateKey-kevin"); //get the user's private key from the java card
                            ObjectInputStream ois_prk=new ObjectInputStream(fis_prk);
                            PrivateKey prk=(PrivateKey)ois_prk.readObject();
                            ois_prk.close();
                            fis_prk.close();
                            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
                            cipher.init(Cipher.DECRYPT_MODE, prk);
                            encryptedPMK = decoder.decodeBuffer(sEncryptedPMK);
                            byte[] PMK = cipher.doFinal(encryptedPMK);
                            System.out.println(new String(PMK));
            //                for(int i=0;i<encryptedPMK.length;i++)
            //                        System.out.print(encryptedPMK[i]+",");
            //            
            //                    System.out.println("\n"+timestamp);
            //
            //                    for(int i=0;i<sig.length;i++)
            //                        System.out.print(sig[i]);s
                            return new String(PMK);
                        } catch (FileNotFoundException ex) {
                            JOptionPane.showMessageDialog(null, "Key not found!\r\nPlease insert the java card and try again!", "ERROR!", JOptionPane.ERROR_MESSAGE);
                            return "ERROR";
                        } catch (IOException ex){
                            JOptionPane.showMessageDialog(null, "Your private key cannot be read!", "ERROR!", JOptionPane.ERROR_MESSAGE);
                            return "ERROR";
                        }
                    }
                }
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(null, "Key not found!\r\nPlease insert the java card and try again!", "ERROR!", JOptionPane.ERROR_MESSAGE);
                return "ERROR";
            }catch (IOException ex){
                JOptionPane.showMessageDialog(null, "Server's public key cannot be read!", "ERROR!", JOptionPane.ERROR_MESSAGE);
                return "ERROR";
            }catch (SignatureException se) {
              JOptionPane.showMessageDialog(null, "Signature Exception!", "ERROR!", JOptionPane.ERROR_MESSAGE);
              return "ERROR";
            }
       
         }
         
    }
 
    

