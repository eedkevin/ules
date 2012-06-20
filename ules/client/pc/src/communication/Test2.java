/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package communication;

import gui.MainFrame;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 *
 * @author shiny
 */
public class Test2 {
    public static void main(String args[]){
        Process pro;
            try {
                    //pro = Runtime.getRuntime().exec("cmd.exe /c e:\\Truecrypt\\TrueCrypt\\TrueCrypt.exe ");
                   // pro = Runtime.getRuntime().exec("cmd.exe cd G: ");
                     //pro = Runtime.getRuntime().exec("C:\\Windows\\system32\\notepad.exe G:\\bao.txt ");
                pro = Runtime.getRuntime().exec("E:\\java\\Java\\jdk1.7.0_02\\bin\\java.exe -cp G: bao ");//core point!!!
                    pro.waitFor();
                    System.out.println("completed");
                }catch (IOException | InterruptedException ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);// clear this sentence
                }
    }
}
//         JOptionPane    pane = new JOptionPane("Please input a value", JOptionPane.QUESTION_MESSAGE,
//                                              JOptionPane.OK_CANCEL_OPTION, null,
//                                              null, null);
//
//        pane.setWantsInput(true);
//        pane.setSelectionValues(null);
//        pane.setInitialSelectionValue(null);
//        pane.setComponentOrientation(null);
////        pane.setComponentOrientation(((parentComponent == null) ?
////            getRootFrame() : parentComponent).getComponentOrientation());
//
////        int style = styleFromMessageType(JOptionPane.QUESTION_MESSAGE);
//        JDialog dialog = pane.createDialog(null, "rn", JOptionPane.QUESTION_MESSAGE);
//
//        pane.selectInitialValue();
//        dialog.show();
//        dialog.dispose();
//
//        Object value = pane.getInputValue();
//
//        if (value == JOptionPane.UNINITIALIZED_VALUE) {
//            return null;
//        }
//        return value;
//        String inputValue = JOptionPane.showInputDialog("Please input a value");
//        System.out.println(inputValue);
//        String a=null;
//        System.out.println(a);
//        JOptionPane jop=new JOptionPane("Please check your SMS and input the number: ",JOptionPane.QUESTION_MESSAGE,JOptionPane.OK_CANCEL_OPTION );
//        jop.setWantsInput(true);
//        JDialog d=jop.createDialog("Random Number");
//        d.setVisible(true);
//        String a=(String)jop.getInputValue();
//        System.out.println(a);
//        while(a!=null&&!a.equals("exit")){
//            if(a.equals("hello")){
//                jop.setMessage("hello baobao");
//                d.setVisible(true);
//                 a=(String)jop.getInputValue();                
//                System.out.println(a);
//            } else{
//                jop.setInputValue("");
//                d.setVisible(true);
//                a=(String)jop.getInputValue();                
//                System.out.println(a);
//            }
//        }
//        System.exit(0);
        
//        JOptionPane jop=new JOptionPane("Please input a value",JOptionPane.QUESTION_MESSAGE,JOptionPane.OK_CANCEL_OPTION );
//        jop.setWantsInput(true);
//        JDialog d=jop.createDialog("Random Number");
//        d.setVisible(true);
//        String a=(String)jop.getInputValue();
//        System.out.println(a);
//        while(a!=null&&!a.equals("exit")){
//            if(a.equals("hello")){
//                jop.setMessage("hello baobao");
//                d.setVisible(true);
//                 a=(String)jop.getInputValue();                
//                System.out.println(a);
//            } else{
//                jop.setInputValue("");
//                d.setVisible(true);
//                a=(String)jop.getInputValue();                
//                System.out.println(a);
//            }
//        }
//        System.exit(0);
//        
  
