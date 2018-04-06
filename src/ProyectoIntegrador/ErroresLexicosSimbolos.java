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
public class ErroresLexicosSimbolos extends Exception{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ErroresLexicosSimbolos(String token, int contLinea)
    {
		JOptionPane.showMessageDialog(null, "El archivo tiene un error léxico en la linea: " 
    + contLinea + ". \nSimbolo a corregir: " + token, "ERROR", JOptionPane.ERROR_MESSAGE);
        
    }
    
}
