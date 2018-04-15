/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProyectoIntegrador;

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

        ventana = new JFrame("Mi bloc de Notas");

        // Inicializa todos los elementos del menu
        JMenuBar menu = new JMenuBar();

        JMenu archivo = new JMenu("Archivo");
        JMenu ayuda = new JMenu("Ayuda");

        JMenuItem nuevo = new JMenuItem("Nuevo");
        JMenuItem abrir = new JMenuItem("Abrir...");
        JMenuItem guardar = new JMenuItem("Guardar");
        JMenuItem compilar = new JMenuItem("Compilar");
        JMenuItem salir = new JMenuItem("Salir");
        JMenuItem acercaDe = new JMenuItem("Acerca de...");

        // Añade los elementos al menu
        archivo.add(nuevo);
        archivo.add(abrir);
        archivo.add(guardar);
        archivo.add(compilar);
        archivo.add(salir);
        ayuda.add(acercaDe);

        menu.add(archivo);
        menu.add(ayuda);

        // Añade la barra de menu a la ventana
        ventana.setJMenuBar(menu);

        // Cra un area de texto con scroll y lo añade a la ventana 
        notas = new JTextArea();
        JScrollPane scrollNotas = new JScrollPane(notas);
        ventana.add(scrollNotas);

        // Asigna a cada menuItem su listener
        nuevo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                notas.setText("");
            }
        });
        abrir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirArchivo();
            }
        });
        guardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarArchivo();
            }
        });
        
         compilar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                compilar();
            }
        });
        salir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // Hace visible la ventana
        ventana.setSize(1366, 728);
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
       public void compilar()
       {
    	   File tabla_simbolos = new File("Tabla_simbolos.txt");
           if(codigo_fuente != null)
           {
     
              JOptionPane.showMessageDialog(null, "Compilando");
              Analizador_Lexico analizador = new Analizador_Lexico(codigo_fuente);
               try {
                   //FileWriter escritor = new FileWriter(tabla_simbolos);
                   //PrintWriter writer = new PrintWriter(escritor);
                   //String aux=analizador.leerCodigoFuente();
                   //writer.write(aux);
                   //System.out.print(aux);
                   tabla_simbolos = analizador.leerCodigoFuente2();
                   
                   JOptionPane.showMessageDialog(null, "An�lisis L�xico exitoso");
                  // escritor.close();
               } catch (ErroresLexicosSimbolos ex) {
            	   //Logger.getLogger(Editor.class.getName()).log(Level.SEVERE, null, ex);
                   
               } catch (ErroresLexicosIdentificadores ex) {
                   //Logger.getLogger(Editor.class.getName()).log(Level.SEVERE, null, ex);
                  
               }
        	  
              
            
           }else{
                JOptionPane.showMessageDialog(null, "Necesita guardar primero el archivo");
           }
       }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Editor bn = new Editor();
    }

}
