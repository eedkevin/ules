/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package communication;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Administrator
 */
public class SocketTest {
    
    Socket socket;
    BufferedInputStream bis;
    InetAddress serverAddr;
    PrintWriter out;
    
    public static void main(String[] args){
        new SocketTest().start();
    }
    
    public void start(){
        redirect();
        connect();
    }    
    
    public void readInput() throws IOException{
            bis = new BufferedInputStream(socket.getInputStream());
            byte[] a=new byte[400];
            bis.read(a);
            String temp = new String(a);
            if(temp.startsWith("pmk=")){
                String pmk=temp.trim();
                System.out.println(pmk);
                System.out.println("PMK length: "+ pmk.length());
            }
    }
    
    public void connect(){
        try
        {
            serverAddr = InetAddress.getByName("127.0.0.1");
            socket = new Socket(serverAddr, 12581);//此处的12581是PC开放的端口，已重定向到Device的12345端口

            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
            out.println("this is a test from pc client");
            out.flush();         
            
            while(true){
                readInput();
            }
        }catch(SocketException ex2){
            System.out.println(ex2);
            reconnect();
            
        }catch(IOException ex){
            System.out.println(ex);
            Logger.getLogger(SocketTest.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public void reconnect(){
        System.out.println("reconnecting...");
        connect();
    }
    
    public void redirect(){
         try{
           //redirect the port
            Runtime.getRuntime().exec("adb forward tcp:12581 tcp:12345");
            //Runtime.getRuntime().exec("adb shell am broadcast -a StartULES");
            System.out.println("adb port forward succeeds");
        }catch (IOException e3){
            JOptionPane.showMessageDialog(null, "IO Exceptions:" + e3.toString(), "ERROR!", JOptionPane.ERROR_MESSAGE);
        }           
    }
    
//    
//    public void start_old(){
//         try
//        {
//           //redirect the port
//            Runtime.getRuntime().exec("adb forward tcp:12581 tcp:12345");
//            //Runtime.getRuntime().exec("adb shell am broadcast -a StartULES");
//            System.out.println("adb succeeds");
//        }catch (IOException e3)
//        {
//            JOptionPane.showMessageDialog(null, "IO Exceptions:" + e3.toString(), "ERROR!", JOptionPane.ERROR_MESSAGE);
//        }
//                
//        
//        Socket socket = null;
//        BufferedInputStream bis = null;
//        try
//        {
//            InetAddress serverAddr = null;
//            serverAddr = InetAddress.getByName("127.0.0.1");
//            
//            do{
//                 socket = new Socket(serverAddr, 12581);//此处的12581是PC开放的端口，已重定向到Device的12345端口
//            }while(!socket.isConnected());
//           
//            
//            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
//            out.println("this is a test from pc client");
//            out.flush();
//            
//            while(true){
//                bis = new BufferedInputStream(socket.getInputStream());
//            byte[] a=new byte[400];
//            bis.read(a);
//            String temp=new String(a);
//            String PMK=temp.trim();
//            System.out.println(PMK+" "+PMK.length());
//            }
//            
//        }         
//        catch (IOException ex) {
//            System.out.println(ex);
//            Logger.getLogger(SocketTest.class.getName()).log(Level.SEVERE, null, ex);
//        }    
//    }
    
}
