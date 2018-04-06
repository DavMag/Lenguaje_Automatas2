/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProyectoIntegrador;

import javax.swing.JOptionPane;

/**
 *
 * @author David
 */
public class ErroresLexicosIdentificadores extends Exception{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ErroresLexicosIdentificadores(String token, int contLineas)
    {
		JOptionPane.showMessageDialog(null, "El identificador: " + token + " de la linea: " + contLineas + "está mal escrito."
                + "\nRecuerde que los identificadores deben de comenzar con alguna letra", "ERROR", JOptionPane.ERROR_MESSAGE);
        
    }
}
