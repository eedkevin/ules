/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author shiny
 */
public class CommandLine {
    public static void main(String args[]) throws IOException, InterruptedException{
        String ls_1;
//        Process pro=Runtime.getRuntime().exec("cmd.exe /c start e:\\Truecrypt\\TrueCrypt\\TrueCrypt.exe ");
         Process pro=Runtime.getRuntime().exec("cmd.exe /c taskkill /f /im TrueCrypt.exe ");
//        Process pro=Runtime.getRuntime().exec("cmd.exe /k calc");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(pro.getInputStream())); 
        while ( (ls_1=bufferedReader.readLine()) != null) 
        System.out.println(ls_1); 


        pro.waitFor();
        System.out.println("good");
//        Runtime.getRuntime().exec("cmd.exe /c taskkill /f /pid " + pid);     


    }
}
