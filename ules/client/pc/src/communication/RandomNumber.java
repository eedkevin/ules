/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package communication;
import gui.Login;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
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
public class RandomNumber {
    
//    String randomNumber=null;
    public static int randomNumberWrongTimes=0;
    public int requestRandomNumber(){
        // trigger server to send a message to the mobile phone
          HttpClient client = new HttpClient();
          GetMethod method = new GetMethod("http://msp11001s1.cs.hku.hk:8080/sendsms.jsp?username="+gui.Login.username+"&from=laptop");
          method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,new DefaultHttpMethodRetryHandler(3, false));
          try {
            int statusCode = client.executeMethod(method);
            if (statusCode != HttpStatus.SC_OK) {
                JOptionPane.showMessageDialog(null, "Service unavailable: " + method.getStatusLine(), "ERROR!", JOptionPane.ERROR_MESSAGE); 
                return 0;
            }  
            else{
                if(randomNumberWrongTimes>=3){
                    JOptionPane.showMessageDialog(null, "You have input the wrong random number for more than 3 times! \r\n"
                                        + "                         Your account has been locked!", "ERROR!", JOptionPane.ERROR_MESSAGE);
                                    return 0;
                }
                return 1;
            
                    
//                InputStream responseBody = method.getResponseBodyAsStream();
//                        if(responseBody==null){
//                            JOptionPane.showMessageDialog(null, "The response body is not available or cannot be read", "ERROR!", JOptionPane.ERROR_MESSAGE);
//                            return 0;
//                        }
//                        else {
//                         // read the response                   
//                                InputStreamReader isr=new InputStreamReader(responseBody);
//                                char[] temp = new char[400];
//                                isr.read(temp);//need test
//                                String result=new String(temp);
//                                System.out.println(result);
//                                if(result.equalsIgnoreCase("WRONG RANDOM NUMBER OVER 3 TIMES")){
//                                    JOptionPane.showMessageDialog(null, "You have input the wrong random number for more than 3 times! \r\n"
//                                        + "                         Your account has been locked!", "ERROR!", JOptionPane.ERROR_MESSAGE);
//                                    return 0;
//                                } else{
//                                    return 1;
//                                }
//                        }
            }
          }catch (HttpException e) {
            JOptionPane.showMessageDialog(null, "Fatal protocol violation: " + e.getMessage(), "ERROR!", JOptionPane.ERROR_MESSAGE); 
            return 0;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Fatal transport error: " + e.getMessage(), "ERROR!", JOptionPane.ERROR_MESSAGE); 
            return 0;
        } finally {
            method.releaseConnection();
        }  
    }
    
    public int checkRandomNumber(String rand){
        
        //judge whether the input is valid(length=6, all numbers)
        if(rand==null||rand.equals("")){
            JOptionPane.showMessageDialog(null, "Random number cannot be empty! ", "ERROR!", JOptionPane.ERROR_MESSAGE);
            return 0;
        }else if(rand.length()!=6){
            JOptionPane.showMessageDialog(null, "ERROR: The length of the random number should be six ! ", "ERROR!", JOptionPane.ERROR_MESSAGE);
            return 0;
        } else{
            for(int i=0;i<6;i++){
                char c=rand.charAt(i);
                if(!(c>='0'&&c<='9')){
                    JOptionPane.showMessageDialog(null, "ERROR: The random number can only contain digits! ", "ERROR!", JOptionPane.ERROR_MESSAGE);
                    return 0;
                }
            }
            return 1;
        }
    
    }
    
    public int GetRandomNumberChallengeResponse(String randomNumber) throws ClassNotFoundException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
        
    // send random number back to the server for check, if user pass the random number check return OK, else return ERROR
        HttpClient client = new HttpClient();
        GetMethod method = new GetMethod("http://msp11001s1.cs.hku.hk:8080/getmountkey.jsp?username="+Login.username+"&sms="+randomNumber);
        method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,new DefaultHttpMethodRetryHandler(3, false));

        try {
                int statusCode = client.executeMethod(method);
                if (statusCode != HttpStatus.SC_OK) {
                     JOptionPane.showMessageDialog(null, "Service unavailable: " + method.getStatusLine(), "ERROR!", JOptionPane.ERROR_MESSAGE); 
                     return 0;
                }
                else{
                        InputStream responseBody = method.getResponseBodyAsStream();
                        if(responseBody==null){
                            JOptionPane.showMessageDialog(null, "The response body is not available or cannot be read", "ERROR!", JOptionPane.ERROR_MESSAGE); 
                            return 0;
                        }
                        else {
                         // read the response                   
                                InputStreamReader isr=new InputStreamReader(responseBody);
                                char[] a = new char[400];
                                isr.read(a);//need test
                                String temp=new String(a);
                                String result=temp.trim();
                                System.out.println(result+" "+result.length());
                                if(result.equalsIgnoreCase("pmk=errorsms")){
                                    JOptionPane.showMessageDialog(null, "Random Number Verification Failed", "ERROR!", JOptionPane.ERROR_MESSAGE);
                                    randomNumberWrongTimes++;
                                    
                                    //if randomNumberWrongTimes>=5, tell the server to lock the user
                                    if(randomNumberWrongTimes>=3){
                                         GetMethod lockMethod = new GetMethod("http://msp11001s1.cs.hku.hk:8080/lockuser.jsp?username="+Login.username);
                                         lockMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,new DefaultHttpMethodRetryHandler(3, false));
                                          try {
                                                int statusCode2 = client.executeMethod(lockMethod);
                                                if (statusCode2 != HttpStatus.SC_OK) {
                                                    JOptionPane.showMessageDialog(null, "Service unavailable: " + method.getStatusLine(), "ERROR!", JOptionPane.ERROR_MESSAGE); 
                                                }
                                                return 0;
                                                
                                          }catch (HttpException e) {
                                            JOptionPane.showMessageDialog(null, "Fatal protocol violation: " + e.getMessage(), "ERROR!", JOptionPane.ERROR_MESSAGE); 
                                            
                                        } catch (IOException e) {
                                            JOptionPane.showMessageDialog(null, "Fatal transport error: " + e.getMessage(), "ERROR!", JOptionPane.ERROR_MESSAGE);
                                            return 0;
                                        } finally {
                                            lockMethod.releaseConnection();
                                    }
                                       
                                   }
                                    return 0; 
                                }else{
                                    //verify signature, timestamp,decrypt the potential mount key
                                    
                                    MountKey mk=new MountKey();
                                    String getMKResult=mk.getMK(result);
                                    if(getMKResult.equalsIgnoreCase("ERROR")){
                                        return 0;
                                    }else{
                                        FileOutputStream pmk_fos=new FileOutputStream("resources\\keyfile.txt");
//                                        pmk_fos.write(result.getBytes());
//                                        pmk_fos.close();
                                        System.out.println(result);
                                        System.out.println(result.length());
                                        pmk_fos.write(getMKResult.getBytes());
                                        pmk_fos.close();
                                        return 1;
                                    }
                                    
                                }
                        }
                }
        } catch (HttpException e) {
            JOptionPane.showMessageDialog(null, "Fatal protocol violation: " + e.getMessage(), "ERROR!", JOptionPane.ERROR_MESSAGE); 
            return 0;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Fatal transport error: " + e.getMessage(), "ERROR!", JOptionPane.ERROR_MESSAGE);
            return 0;
        } finally {
            method.releaseConnection();
        }  
    }  
    
}
