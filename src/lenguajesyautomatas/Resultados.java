/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lenguajesyautomatas;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

/**
 *
 * @author David
 */
public class Resultados extends JFrame{
    public static void main(String[]r)
    {
        SelecImp m = new SelecImp();
        m.abrirArchivo();
        JFrame j = new JFrame("Resultados");
        j.setBounds(20, 20, 500, 400);
        j.setVisible(true);
         JEditorPane editor = new JEditorPane();
           editor.setContentType("text/plain");
           editor.setText(m.getImprimir());
           editor.setEditable(false);
           JScrollPane scroll =new JScrollPane(editor);
           j.add(scroll);
           
           
           
        
    }
}
