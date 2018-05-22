package vci;

import java.util.ArrayList;
import java.util.Stack;

public class Ejecucion_VCI {

	private ArrayList<String> vci; // VCI
	private Stack<String> ejecucion = new Stack<String>(); // Stack de ejecución
	private String r="";
	
	public Ejecucion_VCI(ArrayList nVCI) {
		vci = nVCI;
		ejecucion();
	}

	private boolean isNumeric(String cadena) {
		try {
			Integer.parseInt(cadena);
			return true;
		} catch (NumberFormatException nfe) {
			return false;
		}
	}

	private boolean isBoolean(String cadena) {
		return Boolean.parseBoolean(cadena);
	}

	private boolean valorToken(String token) {
		boolean signo = false;
		switch (token) {
		case "(":
		case ")":
		case "=":
		case "||":
		case "&&":
		case "!":
		case "<":
		case "<=":
		case ">":
		case ">=":
		case "!=":
		case "==":
		case "+":
		case "-":
		case "*":
		case "/":
		signo = true;
		break;
		default:
		break;
		}
		return signo;
	}

	private void ejecucion() {
	

		for (int i = 0; i < vci.size(); i++) 
		{
			if (!(valorToken(vci.get(i)))) 
			{
				ejecucion.push(vci.get(i));
			}else{
				String tmp1 = ejecucion.pop();
				String tmp2 = ejecucion.pop();
				ejecucion.push(ejecucionOperaciones(tmp2,tmp1,vci.get(i)));
			}

		}
		
		r = ejecucion.pop();
		
		
	}
	
	/*
	 * Hay que preparar unas excepciones para cuando se ingresen valores que no sean números
	 */
	private String ejecucionOperaciones(String valor1, String valor2, String operador)
	{
		int ax1 = 0;
		int ax2 = 0;
	
		String resultado ="";
		boolean a1 =false;
		boolean a2 =false;
		
		if(isNumeric(valor1))
		{
			ax1 = Integer.parseInt(valor1);
		}else if(isBoolean(valor1))
		{
			a1 = Boolean.parseBoolean(valor1);
		}
		
		if(isNumeric(valor2))
		{
			ax2 = Integer.parseInt(valor2);
		}else if(isBoolean(valor2))
		{
			a1 = Boolean.parseBoolean(valor2);
		}
		
		System.out.println(valor1);
		System.out.println(valor2);
		switch(operador)
		{
		case "==":
			if(ax1 == ax2)
			{
				resultado="True";
			}else {
				resultado="False";
			}
		break;
		case "=":
			resultado = valor1 + " = " + valor2;
			break;
		case "||":
			if(a1 || a2)
			{
				resultado="True";
			}else {
				resultado="False";
			}
			break;
		case "&&":
			if(a1 && a2)
			{
				resultado="True";
			}else {
				resultado="False";
			}
			break;
		// Pendiente case "!":
			
		case "<":
			if(ax1<ax2)
			{
				resultado="True";
			}else {
				resultado="False";
			}
			break;
		case "<=":
			if(ax1<=ax2)
			{
				resultado="True";
			}else {
				resultado="False";
			}
			break;
		case ">":
			if(ax1>ax2)
			{
				resultado="True";
			}else {
				resultado="False";
			}
			break;
		case ">=":
			if(ax1>=ax2)
			{
				resultado="True";
			}else {
				resultado="False";
			}
			break;
		case "!=":
			if(ax1!=ax2)
			{
				resultado="True";
			}else {
				resultado="False";
			}
			break;
		case "+":
			
			resultado = "" + (ax1+ax2);
			break;
		case "-":
			resultado = "" + (ax1-ax2);
			break;
		case "*":
			resultado = "" + (ax1*ax2);
			break;
		case "/":
			resultado = "" + (ax1/ax2);
		
		}
		
		return resultado;
		
	}
	
	public String getResultado()
	{
		return r;
	}

}
