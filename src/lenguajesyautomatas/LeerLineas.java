/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lenguajesyautomatas;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author David
 */
public class LeerLineas extends JFrame{
    private int cont=0;
    
   public void abrirArchivo()
   {
       String aux;
       try{
           JFileChooser jf = new JFileChooser();
           jf.showOpenDialog(this);
           
           File archivo = jf.getSelectedFile();
           
           if(archivo!=null)
           {
               FileReader archivos=new FileReader(archivo);
               BufferedReader lee=new BufferedReader(archivos);
               while(lee.readLine() != null)
               {
                   cont++;
               }
           }else{
               JOptionPane.showMessageDialog(null, "El archivo no tiene lineas o no se ha escogido alguno");
               System.exit(0);
           }
           JOptionPane.showMessageDialog(null, "El archivo tiene " + cont + " lineas");
       }catch(IOException e)
       {
           JOptionPane.showMessageDialog(null,e+"" +
           "\nNo se ha encontrado el archivo",
                 "ADVERTENCIA!!!",JOptionPane.WARNING_MESSAGE);
       }
   }
   public static void main(String[] args)
   {
       LeerLineas ll = new LeerLineas();
       ll.abrirArchivo();
   }
}
