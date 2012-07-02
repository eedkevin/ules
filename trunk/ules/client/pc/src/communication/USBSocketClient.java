/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package communication;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Administrator
 */
public class USBSocketClient {

    private Socket socket;
    //private BufferedInputStream bis;
    private InetAddress serverAddr;
    //private PrintWriter out;
    private String pmk;        // potencial mount key

    public static void main(String[] args){
        USBSocketClient client = new USBSocketClient();
        client.start();
        String mountKey = client.getPMK();
        System.out.println("PMK =======>>>>>>>>"+client.getPMK());
    }
    
    public String getPMK(){
        return this.pmk;
    }
    
    public void start() {
        redirect();
        connect();
    }
    
    public void redirect() {
        try {
            //redirect the port
            Runtime.getRuntime().exec("adb forward tcp:12581 tcp:12345");
            //Runtime.getRuntime().exec("adb shell am broadcast -a StartULES");
            System.out.println("adb port forward succeeds");
        } catch (IOException e3) {
            JOptionPane.showMessageDialog(null, "IO Exceptions:" + e3.toString(), "ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void connect() {
        try {
            serverAddr = InetAddress.getByName("127.0.0.1");
            socket = new Socket(serverAddr, 12581);//此处的12581是PC开放的端口，已重定向到Device的12345端口

            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            out.println("this is a test from pc client");
            out.flush();

            //while(pmk==null || !pmk.startsWith("pmk=")){
            //while(true){
                readInput();
            //}
            
        } catch (SocketException ex2) {
            System.out.println(ex2);
            reconnect();

        } catch (IOException ex) {
            System.out.println(ex);
            Logger.getLogger(SocketTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void reconnect() {
        System.out.println("reconnecting...");
        connect();
    }
    
    public void readInput() throws IOException {
        BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
        byte[] a = new byte[400];
        bis.read(a);
        String temp = new String(a);
        if (temp.startsWith("pmk=")) {
            pmk = temp.trim();
            System.out.println(pmk);
            System.out.println("PMK length: " + pmk.length());
        }else{
            System.out.println(temp);
        }
    }

}
