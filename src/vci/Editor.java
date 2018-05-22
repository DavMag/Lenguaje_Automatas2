package vci;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author David Magallán Jiménez
 */
public class Editor {

    JFrame ventana;
    JTextArea notas;
    File codigo_fuente;
    File tabla_simbolos;
    String path;
    

    public Editor() {

        ventana = new JFrame("Creaci�n de VCI");

        // Inicializa todos los elementos del menu
        JMenuBar menu = new JMenuBar();

        JMenu archivo = new JMenu("Archivo");
        JMenu ayuda = new JMenu("Ayuda");

        
        JMenuItem vci = new JMenuItem("VCI");
        
        // Añade los elementos al menu
        
        archivo.add(vci);
    

        menu.add(archivo);
        menu.add(ayuda);

        // Añade la barra de menu a la ventana
        ventana.setJMenuBar(menu);

        // Cra un area de texto con scroll y lo añade a la ventana 
        notas = new JTextArea();
        JScrollPane scrollNotas = new JScrollPane(notas);
        ventana.add(scrollNotas);

        
        
         vci.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vci();
            }
        });
       
        // Hace visible la ventana
        ventana.setLocationRelativeTo(null);
        ventana.setSize(500, 250);
        ventana.setVisible(true);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void abrirArchivo() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if (JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(ventana)) {
            File archivo = fileChooser.getSelectedFile();
            FileReader lector = null;
            try {
                lector = new FileReader(archivo);
                BufferedReader bfReader = new BufferedReader(lector);

                String lineaFichero;
                StringBuilder contenidoFichero = new StringBuilder();

                // Recupera el contenido del fichero
                while ((lineaFichero = bfReader.readLine()) != null) {
                    contenidoFichero.append(lineaFichero);
                    contenidoFichero.append("\n");
                }
                bfReader.close();
                // Pone el contenido del fichero en el area de texto
                notas.setText(contenidoFichero.toString());

            } catch (FileNotFoundException ex) {
                Logger.getLogger(Editor.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Editor.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    lector.close();
                } catch (IOException ex) {
                    Logger.getLogger(Editor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void guardarArchivo() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if (JFileChooser.APPROVE_OPTION == fileChooser.showSaveDialog(ventana)) {
            codigo_fuente = fileChooser.getSelectedFile();
            FileWriter escritor = null;
            try {
                escritor = new FileWriter(codigo_fuente);
                escritor.write(notas.getText());
                path = codigo_fuente.getAbsolutePath();
                System.out.print(path);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Editor.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Editor.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    escritor.close();
                   
                } catch (IOException ex) {
                    Logger.getLogger(Editor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    /*
    Método que se ejecuta al lanzar las excepciones de errores léxicos: Si lanza alguna se abre el mismo archivo y le dice que corrija
    el error.
    */
    public void corregirArchivo()
    {
        
            File archivo = codigo_fuente;//Archivo escrito por el usuario
            FileReader lector = null;
            try {
                lector = new FileReader(archivo);
                BufferedReader bfReader = new BufferedReader(lector);

                String lineaFichero;
                StringBuilder contenidoFichero = new StringBuilder();

                // Recupera el contenido del fichero
                while ((lineaFichero = bfReader.readLine()) != null) {
                    contenidoFichero.append(lineaFichero);
                    contenidoFichero.append("\n");
                }
                bfReader.close();
                // Pone el contenido del fichero en el area de texto
                notas.setText(contenidoFichero.toString());
                

            } catch (FileNotFoundException ex) {
                Logger.getLogger(Editor.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Editor.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                
                    lector.close();
                    
                } catch (IOException ex) {
                    Logger.getLogger(Editor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        
    }
    
    /*
    Método Compilar: Primero hará el análisis léxico y como vaya avanzando en el proyecto se  añadirán los siguientes pasos para la compilación
    */
       public void vci()
       {
    	 
    	   System.out.println(notas.getText());
    	  if(!(notas.getText().equals(""))) {
    	   Lectura_VCI vci  = new Lectura_VCI(notas.getText());
    	   vci.creacionVCI(); 
    	   JOptionPane.showMessageDialog(null,vci.imprimirVCI(),"Vector C�digo Intermedio", JOptionPane.INFORMATION_MESSAGE);
    	   Ejecucion_VCI exVCI = new Ejecucion_VCI(vci.getVci());
    	   JOptionPane.showMessageDialog(null,exVCI.getResultado(),"Resultado", JOptionPane.INFORMATION_MESSAGE);
    	   
    	  }else {
    		  JOptionPane.showMessageDialog(null,"No ha escrito ninguna linea de instrucci�n\n"
    				  + "  Escriba una linea para continuar","Alertar", JOptionPane.WARNING_MESSAGE);  
    	  }
    	   
       }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Editor bn = new Editor();
    }

}
