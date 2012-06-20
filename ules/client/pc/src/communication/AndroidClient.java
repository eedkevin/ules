/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package communication;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.JOptionPane;

/**
 *
 * @author shiny
 */
public class AndroidClient {
    public String getPMK(){
        try
        {
           //redirect the port
            Runtime.getRuntime().exec("E:\\android-sdk\\platform-tools\\adb forward tcp:12581 tcp:12345");
            System.out.println("adb succeeds");
        }catch (IOException e3)
        {
            JOptionPane.showMessageDialog(null, "IO Exceptions:" + e3.toString(), "ERROR!", JOptionPane.ERROR_MESSAGE);
            return "error";
        }

        Socket socket = null;
        try
        {
            InetAddress serverAddr = null;
            serverAddr = InetAddress.getByName("127.0.0.1");
            //System.out.println("TCP 1111" + "C: Connecting...");

            socket = new Socket(serverAddr, 12581);//此处的12581是PC开放的端口，已重定向到Device的12345端口

//            String message = "AndroidRes,Where is my Pig (Android)?";
//            System.out.println("TCP 2222" + "C: Sending: '" + message + "'");
//            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
//            out.println(message);
            
            BufferedInputStream bis=new BufferedInputStream(socket.getInputStream());
            byte[] a=new byte[400];
            bis.read(a);
            String temp=new String(a);
            String PMK=temp.trim();
            System.out.println(PMK+" "+PMK.length());
            bis.close();
            
            //close the socket
            try
            {
                if (socket != null)
                {
                    socket.close();
                }
            }
            catch (IOException e)
            {
                 JOptionPane.showMessageDialog(null, "TCP ERROR:" + e.toString(), "ERROR!", JOptionPane.ERROR_MESSAGE);
                //System.out.println("TCP ERROR:" + e.toString());
                return "error";
            }
            return PMK;
//            String tempNotTrimmed=new String(temp);
//            String tempTrimmed=tempNotTrimmed.trim();
//            System.out.println(tempNotTrimmed+tempNotTrimmed.length());
//            System.out.println(tempTrimmed+tempTrimmed.length());
        }
        catch (UnknownHostException e1)
        {
            JOptionPane.showMessageDialog(null, "TCP ERROR:" + e1.toString(), "ERROR!", JOptionPane.ERROR_MESSAGE);
            //System.out.println("TCP ERROR:" + e1.toString());
            //close the socket
             try
            {
                if (socket != null)
                {
                    socket.close();
                }
            }
            catch (IOException e)
            {
                JOptionPane.showMessageDialog(null, "TCP ERROR:" + e.toString(), "ERROR!", JOptionPane.ERROR_MESSAGE);
                //System.out.println("TCP ERROR:" + e.toString());
                return "error";
            }
             
            return "error";
        }
        catch (IOException e2)
        {
            //causes:1.adb debugging isn't ticked; 2:do not install usb driver
            JOptionPane.showMessageDialog(null, "Communication between phone and PC failed! \r\n"
                                        + "Please make sure:\r\n"+"1. You have inserted your mobile phone.\r\n"
                    +"2. You have installed the USB driver for your phone on the PC.\r\n"+""
                    + "3. You have checked the item \"Settings->Applications->Development->USB Debugging\" on your phone.", "ERROR!", JOptionPane.ERROR_MESSAGE);
           // System.out.println("TCP ERROR:" + e2.toString());
            //close the socket
             try
            {
                if (socket != null)
                {
                    socket.close();
                }
            }
            catch (IOException e)
            {
                JOptionPane.showMessageDialog(null, "TCP ERROR:" + e.toString(), "ERROR!", JOptionPane.ERROR_MESSAGE);
                //System.out.println("TCP ERROR:" + e.toString());
                return "error";
            }
             
            return "error";
        }
       
    }
    public static void main(String args[]){
        AndroidClient androidClient=new AndroidClient();
        String PMK=androidClient.getPMK();
        System.out.println(PMK);
    }
}

