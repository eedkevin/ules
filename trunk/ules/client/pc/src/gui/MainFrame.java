/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import communication.RandomNumber;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 *
 * @author shiny
 */
public class MainFrame extends javax.swing.JFrame {
    private static byte[] overlap=new byte[100];
    public static RandomNumberDialog rnd;
    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton1.setText("Create&Mount&Dismount");
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton3.setText("More functions can be found on  UFLE website !");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 408, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 408, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(173, 173, 173)
                .addComponent(jButton1)
                .addGap(18, 18, 18)
                .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(20, 20, 20))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        // After user press "create mount and dismount"
        RandomNumber rand=new RandomNumber();
        int requestRandomNumberResult=rand.requestRandomNumber();
        
        if(requestRandomNumberResult==1){
      
//            String inputValue=JOptionPane.showInputDialog(null, "Please check your SMS and input the number:", "Random Number",JOptionPane.QUESTION_MESSAGE );
//            System.out.println(inputValue);
//            while(inputValue!=null&&rand.checkRandomNumber(inputValue)!=1){       
//               
//                inputValue=JOptionPane.showInputDialog(null, "Please check your SMS and input the number:", "Random Number",JOptionPane.QUESTION_MESSAGE );
//               System.out.println(inputValue);
//               
//            }
            
            rnd = new RandomNumberDialog(null,true);
            rnd.setLocationRelativeTo(null);
            rnd.setVisible(true);
            String inputValue=rnd.getInputRandomNumber();
            System.out.println(inputValue);
            rnd.dispose();
           
            while(inputValue!=null&&rand.checkRandomNumber(inputValue)!=1){       
               
                rnd = new RandomNumberDialog(null,true);
                rnd.setLocationRelativeTo(null);
                rnd.setVisible(true);
                inputValue=rnd.getInputRandomNumber();
                System.out.println(inputValue);
                rnd.dispose();
                
                
            }
            
            
            if(inputValue!=null){
                try {
                    int randomNumberChallengeResult;
                    randomNumberChallengeResult = rand.GetRandomNumberChallengeResponse(inputValue);
                    if(randomNumberChallengeResult==1){
                        Process pro;
                        try {
                                pro = Runtime.getRuntime().exec("cmd.exe /c C:/modified_truecrypt/TrueCrypt.exe");
                                this.setVisible(false);
                                pro.waitFor();//Wait for the truecrypt process to terminate. The programme continues only if the truecrypt terminates.
                                this.setVisible(true);

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
                            } catch (IOException ex) {
                            ex.printStackTrace();
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    } 
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                } catch (NoSuchAlgorithmException ex) {
                    ex.printStackTrace();
                } catch (NoSuchPaddingException ex) {
                    ex.printStackTrace();
                } catch (InvalidKeyException ex) {
                    ex.printStackTrace();
                } catch (IllegalBlockSizeException ex) {
                    ex.printStackTrace();
                } catch (BadPaddingException ex) {
                    ex.printStackTrace();
                }
            }
            
            
            
            
        }
       
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
         Desktop deskTop=Desktop.getDesktop();
        try {
            URI registerAddress=new URI("http://msp11001s1.cs.hku.hk:8080/");
            try {
                deskTop.browse(registerAddress);
            } catch (IOException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (URISyntaxException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /*
//         * Set the Nimbus look and feel
//         */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /*
//         * If Nimbus (introduced in Java SE 6) is not available, stay with the
//         * default look and feel. For details see
//         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /*
//         * Create and display the form
//         */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//
//            public void run() {
//                MainFrame mainFrame=new MainFrame();
//                mainFrame.setLocationRelativeTo(null);
//                mainFrame.setVisible(true);
//            }
//        });
//    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    // End of variables declaration//GEN-END:variables
}