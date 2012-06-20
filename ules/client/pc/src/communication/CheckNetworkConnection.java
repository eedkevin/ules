/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package communication;

import gui.Login;
import gui.MainFrame;
import gui.NoNetworkConnectionDialog;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.JOptionPane;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
/**
 *
 * @author shiny
 */
public class CheckNetworkConnection {
    private static byte[] overlap=new byte[100];
    public static final int NoNetwork=0;
    public static final int HaveNetwork=1;
    public int checkNetworkStatus(){
        
          HttpClient client = new HttpClient();
          GetMethod method = new GetMethod("http://msp11001s1.cs.hku.hk:8080/");
          method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,new DefaultHttpMethodRetryHandler(3, false));
          try {
            int statusCode = client.executeMethod(method);
            if (statusCode != HttpStatus.SC_OK) {
                //this situation is impossible to happen, unless server changes the domain name or the port
                //JOptionPane.showMessageDialog(null, "Service unavailable: " + method.getStatusLine(), "ERROR!", JOptionPane.ERROR_MESSAGE); 
                return NoNetwork;
            }else{
                
                // If UFLE client can connect to the UFLE server
                return HaveNetwork;
                
            }
            
          }catch (HttpException e) {
             //this situation is impossible to happen
            //JOptionPane.showMessageDialog(null, "Fatal protocol violation: " + e.getMessage(), "ERROR!", JOptionPane.ERROR_MESSAGE); 
            return NoNetwork;
          } catch (IOException e) {
              //If UFLE client cannot connect to the UFLE server
           //JOptionPane.showMessageDialog(null, "Fatal Transport error: " + e.getMessage(), "ERROR!", JOptionPane.ERROR_MESSAGE); 
           return NoNetwork;
          } finally{
              method.releaseConnection();
          }
    }
    
    public static void main(String args[]) throws ClassNotFoundException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
        
          HttpClient client = new HttpClient();
          GetMethod method = new GetMethod("http://msp11001s1.cs.hku.hk:8080/");
          method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,new DefaultHttpMethodRetryHandler(3, false));
          try {
            int statusCode = client.executeMethod(method);
            if (statusCode != HttpStatus.SC_OK) {
                method.releaseConnection();
                JOptionPane.showMessageDialog(null, "Service unavailable: " + method.getStatusLine(), "ERROR!", JOptionPane.ERROR_MESSAGE); 
            }else{
                
                // If UFLE client can connect to the UFLE server
                method.releaseConnection();
                Login.login=new Login();
                Login.login.setLocationRelativeTo(null);
                Login.login.setVisible(true);
            }
            
          }catch (HttpException e) {
             method.releaseConnection();
            JOptionPane.showMessageDialog(null, "Fatal protocol violation: " + e.getMessage(), "ERROR!", JOptionPane.ERROR_MESSAGE); 
            
          } catch (IOException e) {
              //If UFLE client cannot connect to the UFLE server
            method.releaseConnection();
            NoNetworkConnectionDialog dia=new NoNetworkConnectionDialog(null,true);
            dia.setLocationRelativeTo(null);
            dia.setVisible(true);
            int choose=dia.getReturnStatus();
           
            if(choose==1){  
                //if user choose to run the client on mobile phone,do the following:
                int result1=JOptionPane.showConfirmDialog(null, "Now please run the UFLE client on your mobile phone.",null,JOptionPane.OK_CANCEL_OPTION,JOptionPane.INFORMATION_MESSAGE);
                System.out.println(result1);
                if(result1==JOptionPane.OK_OPTION){
                   int result2=JOptionPane.showConfirmDialog(null, "After the client on your phone said \"Waiting for the PC to connect\",press OK to continue.",null,JOptionPane.OK_CANCEL_OPTION,JOptionPane.INFORMATION_MESSAGE); 
                   if(result2==JOptionPane.OK_OPTION)
                   {
                       AndroidClient androidClient=new AndroidClient();
                       String PMK=androidClient.getPMK();
                       System.out.println(PMK);
                       if(PMK.equalsIgnoreCase("error")){
                           //If error happens in communication between phone and PC
                           System.exit(1);
                       }else{
                            MountKey mk=new MountKey();
                            String getMKResult=mk.getMK(PMK);
                            if(getMKResult.equalsIgnoreCase("ERROR")){
                                //if error happens in obtaining the mount key:signature exception,etc.
                                System.exit(1);
                            }else{
                                    FileOutputStream mk_fos;
                                try {
                                    mk_fos = new FileOutputStream("resources\\keyfile.txt");
                                    System.out.println("MountKey"+getMKResult);
                                    System.out.println("MountKeyLength"+getMKResult.length());
                                    mk_fos.write(getMKResult.getBytes());
                                    mk_fos.close();
                                } catch (FileNotFoundException ex) {
                                    JOptionPane.showMessageDialog(null, "KeyFileNotFoundException: "+ex.toString(), "ERROR!", JOptionPane.ERROR_MESSAGE);
                                }catch (IOException ex) {
                                    JOptionPane.showMessageDialog(null, "IO Exception: "+ex.toString(), "ERROR!", JOptionPane.ERROR_MESSAGE);
                                }
                                    
                                Process pro;
                                try {
                                    pro = Runtime.getRuntime().exec("cmd.exe /c C:/modified_truecrypt/TrueCrypt.exe");
                                    pro.waitFor();//Wait for the truecrypt process to terminate. The programme continues only if the truecrypt terminates.

                                    //after user closes truecrypt


                                    File file=new File("resources\\keyfile.txt");
                                    if(file.exists()){
                                        FileOutputStream del_fos=new FileOutputStream("resources\\keyfile.txt");
                                        del_fos.write(overlap);
                                        del_fos.close();
                                        boolean isDeleted;
                                        for(int i=0;i<3;i++){
                                            isDeleted=file.delete();
                                            if(isDeleted){
                                                break;
                                                }
                                            } 
                                    }

                                }
                                catch (InterruptedException ex) {
                                    ex.printStackTrace();
                                }                                catch (IOException ex) {
                                    ex.printStackTrace();
                                }                            }
                       }
                   }
                }
              }
          } 
    }
}
    

