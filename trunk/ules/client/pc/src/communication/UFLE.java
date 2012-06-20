/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package communication;

import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 *
 * @author shiny
 */
public class UFLE {
// modify truecrypt source code, then we use this class. Otherwise it is not useful.
    /**
     * @param args the command line arguments
     */
    public String username;
    
    public static void main(String[] args) {
        // TODO code application logic here
       
    }
    
    public void RandomAndPMK() throws IOException, InterruptedException, ClassNotFoundException,NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
         RandomNumber randomNumber=new RandomNumber();
         randomNumber.requestRandomNumber();
        //弹出对话框要求用户输入random number
        String response=randomNumber.GetRandomNumberChallengeResponse("a");//从对话框得到该函数的参数
        if(response.equalsIgnoreCase("ERROR")){
            Process pro=Runtime.getRuntime().exec("cmd.exe /c taskkill /f /im TrueCrypt.exe ");
            pro.waitFor();//回到主界面
        }else{
            //deal with the message(encrypted PMK, signature and timestamp)
            MountKey pmk=new MountKey();
            String PMKey=pmk.getPMK(response);
            //create the keyfile
            FileOutputStream pmk_fos=new FileOutputStream("");
            pmk_fos.write(b);
            //修改xml文件
            
        }   
        
    }
}
