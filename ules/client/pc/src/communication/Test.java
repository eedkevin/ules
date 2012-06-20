/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package communication;

import gui.MainFrame;
import gui.RandomNumberDialog;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;


/**
 *
 * @author shiny
 */

public class Test{
    public static RandomNumberDialog rnd;
    public static void main(String args[]) throws FileNotFoundException{
        
//        RandomNumberDialog temp = new RandomNumberDialog(null,true);
//        temp.setVisible(true);
//        String input=temp.getInputRandomNumber();
//        System.out.println(input);
//        temp.setVisible(true);
        
        
        
            
//            JOptionPane jop=new JOptionPane("Please check your SMS and input the number: ",JOptionPane.QUESTION_MESSAGE,JOptionPane.OK_CANCEL_OPTION );
//            jop.setWantsInput(true);
//            JDialog d=jop.createDialog("Random Number");
//            d.setLocationRelativeTo(null);
//            d.setVisible(true);
            
//            String a=(String)jop.getInputValue();
        
          RandomNumber rand=new RandomNumber();
           rnd = new RandomNumberDialog(null,true);
            rnd.setLocationRelativeTo(null);
            rnd.setVisible(true);
//            System.out.println("hello");
            String inputValue=rnd.getInputRandomNumber();
            System.out.println(inputValue);
            rnd.dispose();
           
            while(inputValue!=null&&rand.checkRandomNumber(inputValue)!=1){       
               
                rnd = new RandomNumberDialog(null,true);
                rnd.setLocationRelativeTo(null);
                rnd.setVisible(true);
//                System.out.println("hello");
                inputValue=rnd.getInputRandomNumber();
                System.out.println(inputValue);
                rnd.dispose();
                
                
            }
            
            
                
                
            
             
               
            
//            RandomNumber rand=new RandomNumber();
//            String input=JOptionPane.showInputDialog(null, "Please check your SMS and input the number:", "Random Number",JOptionPane.QUESTION_MESSAGE );
//            System.out.println(input);
//            while(input!=null&&rand.checkRandomNumber(input)!=1){       
//               
//                input=JOptionPane.showInputDialog(null, "Please check your SMS and input the number:", "Random Number",JOptionPane.QUESTION_MESSAGE );
//               System.out.println(input);
//               
//            }
    }
}
//                while(checkRandomNumberResult!=1){
//                    RandomNumberDialog temp = new RandomNumberDialog(null,true);
//                    temp.setLocationRelativeTo(null);
//                    temp.setVisible(true);
//                    String input=temp.getInputRandomNumber();
//                    if(inputValue==null) {
//                         rnd.dispose();
//                         break;
//                    }
//            }
//            System.exit(0);
//          System.out.println(input);
//                    checkRandomNumberResult=rand.checkRandomNumber(input);
//                }  
            
            
//            String inputValue=rnd.getInputRandomNumber();
//            System.out.println(inputValue);
//            if(inputValue==null) rnd.dispose();
//            else{
//                int checkRandomNumberResult=rand.checkRandomNumber(inputValue);
//                while(checkRandomNumberResult!=1){
//                    RandomNumberDialog temp = new RandomNumberDialog(null,true);
//                    temp.setLocationRelativeTo(null);
//                    temp.setVisible(true);
//                    String input=temp.getInputRandomNumber();
//                    if(inputValue==null) {
//                         rnd.dispose();
//                         break;
//                    }
//                       
//                    System.out.println(input);
//                    checkRandomNumberResult=rand.checkRandomNumber(input);
//                }
//            }
            
            
            
//        RandomNumberDialog rnd = new RandomNumberDialog(null,true);
//            rnd.setLocationRelativeTo(null);
//            rnd.setVisible(true);
////            String inputValue=null;
//            String inputValue=rnd.getInputRandomNumber();
//            System.out.println("ok");
//            if(inputValue==null)
//                System.out.println("null");
//            else if(inputValue.equals(""))
//                 System.out.println("空字符");
//            System.out.println(inputValue);
            
//            if(inputValue!=null) {
//                RandomNumber rand=new RandomNumber();
//                System.out.println(inputValue);
//                int checkRandomNumberResult=rand.checkRandomNumber(inputValue);
//                while(checkRandomNumberResult!=1){
//                    
//                    rnd = new RandomNumberDialog(null,true);
//                    rnd.setLocationRelativeTo(null);
//                    rnd.setVisible(true);
//                    inputValue=rnd.getInputRandomNumber();
//                    System.out.println("ok");
//                   
////                    if(inputValue!=null) {
////                         rnd.dispose();
////                         break;
////                    }
//                       
//                    System.out.println(inputValue);
//                    if(inputValue==null){
//                        break;
//                    }
//                    checkRandomNumberResult=rand.checkRandomNumber(inputValue);
//                }
//           }
  
//public class Test extends TimerTask implements ActionListener{
//    
////    int i=5;    
////    static JLabel jl;
////    Timer  randomNumberTimer;
//    //private static byte[] overlap=new byte[100];
//    
//    public static void main(String args[]) throws FileNotFoundException{
//        RandomNumberDialog temp = new RandomNumberDialog(null,true);
//                String input=temp.returnInputRandomNumber();
//                System.out.println(input);
//        Test test=new Test();
//        test.go();
//    }
//     public void run(){
//        jl.setText("Time Remaining: "+i+" s");
//        i--;
//        if(i<0)
//            randomNumberTimer.cancel();
//    }
//    public void go(){
//         JFrame jf=new JFrame();
//        jl=new JLabel("houhou");
//        JButton jb=new JButton("stop");
//        jb.addActionListener(this);
//        jf.getContentPane().add(jl, BorderLayout.CENTER);
//        jf.getContentPane().add(jb,BorderLayout.SOUTH);
//        jf.setBounds(200, 200, 400, 400);
//        
//        jf.setVisible(true);
//        
//        randomNumberTimer=new Timer();
////        Test test1=new Test();
//        randomNumberTimer.scheduleAtFixedRate(this, 0, 1000);
//        
//    }
//   public void actionPerformed(ActionEvent e){
//       randomNumberTimer.cancel();
//   }
//           
//}
        
//       Process pro;
//                try {
//                    pro = Runtime.getRuntime().exec("cmd.exe /c e:\\Truecrypt\\TrueCrypt\\TrueCrypt.exe ");
//                    pro.waitFor();
//                    System.out.println(new String(overlap));
//                    File file=new File("resources\\keyfile.txt");
//                    if(file.exists()){
//                        FileOutputStream del_fos=new FileOutputStream("resources\\keyfile.txt");
//                        del_fos.write(overlap);
//                        del_fos.close();
//                        boolean isDeleted;
//                        for(int i=0;i<3;i++){
//                            isDeleted=file.delete();
//                            if(isDeleted){
//                                break;
//                            }
//                        } 
//                      
//                    }
//                   
//                } catch (IOException | InterruptedException ex) {
//                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);// clear this sentence
//                }
//        JOptionPane.showMessageDialog(null, "ERROR: The length of the random number should be six! ", "ERROR!", JOptionPane.ERROR_MESSAGE);
//         FileOutputStream pmk_fos=new FileOutputStream("resources\\keyfile.txt");
//                                    pmk_fos.write("abc".getBytes());
                                    
//         JOptionPane.showMessageDialog(null, "You have input the wrong random number for more than 3 times! \r\n"
//                                        + "                         Your account has been locked!", "ERROR!", JOptionPane.ERROR_MESSAGE);
       
//        JOptionPane.showMessageDialog(null, "signature is not verified!\r\nWarning: Your message is modified during transmission!"
//                + "\r\nPlease make sure you network environment is safe!", "ERROR!", JOptionPane.ERROR_MESSAGE);
//        System.out.println("unverified"+"\r\n"+"good");
//        System.getProperty( "line.separator ")
   

