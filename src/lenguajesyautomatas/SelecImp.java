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
import java.util.StringTokenizer;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

/**
 *
 * @author David
 */
public class SelecImp extends JFrame {
    
    private int cont=0;
    private String imprimir="";
    private Analizador analizador=new Analizador();
     public void abrirArchivo()
   {
       String linea;
       StringTokenizer token;
       try{
           JFileChooser jf = new JFileChooser();
           jf.showOpenDialog(this);
           
           File archivo = jf.getSelectedFile();
           
           if(archivo!=null)
           {
               String aux;
               FileReader archivos=new FileReader(archivo);
               BufferedReader lee=new BufferedReader(archivos);
               while((linea=lee.readLine()) != null)
               {
                   cont++;
                   if(linea==null)
                   {
                       break;
                   }
                   token=new StringTokenizer(linea);
                   while(token.hasMoreTokens())
                   {
                       aux=token.nextToken();
                       imprimir+=aux + ", " +analizador.verificadorEnTabla(aux, linea) + " ," + analizador.getPosicionTable() + ", " + cont + "\n";
                   }
                   
               }
           }else{
               JOptionPane.showMessageDialog(null, "El archivo no tiene lineas o no se ha escogido alguno");
               System.exit(0);
           }
           System.out.print(imprimir);
           JEditorPane editor = new JEditorPane();
           editor.setContentType("text/plain");
           editor.setText(imprimir);
           JScrollPane scroll =new JScrollPane(editor);
           
       }catch(IOException e)
       {
           JOptionPane.showMessageDialog(null,e+"" +
           "\nNo se ha encontrado el archivo",
                 "ADVERTENCIA!!!",JOptionPane.WARNING_MESSAGE);
       }
       
     
   }
     public String getImprimir()
     {
         return imprimir;
     }
}
