/**
 * 
 */
package ProyectoIntegrador;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * @author David
 *
 */
public class Analizador_Sintactico {

	File TABLA_TOKENS = new File("C:\\Users\\David\\git\\LenguajesYAutomatas\\Tabla_Tokens.txt");
	ArrayList<String> tokens = new ArrayList<String>();
	ArrayList<String> secuencia = new ArrayList<String>();
	int indice = 0;

	private void obtencionToken() {
		String linea;
		FileReader archivos;
		BufferedReader lee;
		StringTokenizer st;
		try {

			archivos = new FileReader(TABLA_TOKENS);// Leer el archivo de c√≥digo fuente
			lee = new BufferedReader(archivos);
			while ((linea = lee.readLine()) != null) {
				st = new StringTokenizer(linea);
				tokens.add(st.nextToken());
				secuencia.add(st.nextToken());
				indice++;

			}
		} catch (Exception e) {

		}
	}

	private boolean operacionAritmetica(int inx) {
		boolean continuar = false;

		if (tokens.get(inx).contains("+-*/")) {
			continuar = true;
			inx++;
		}

		return continuar;
	}

	private boolean idArreglo(int inx) {
		boolean check = false;
		boolean continuar = true;
		int estado = 0;

		while (check != true) {
			switch (estado) {
			case 0:
				if (secuencia.get(inx).equals("-23")) {
					inx++;
					if (tokens.get(inx).equals("[")) {
						estado = 1;

					} else {
						// Estado que determina el final de la gram·tica
						estado = 7;
						inx--;
					}

				} else {
					// Estado que determina el error en la gram·tica
					estado = 6;
				}

				break;
			case 1:
				if (tokens.get(inx).equals("[")) {
					inx++;
					if (secuencia.get(inx).equals("-23")) {
						estado = 2;
					} else if (secuencia.get(inx).equals("-51")) {
						estado = 3;
					}
				} else {
					estado = 6;
				}
				break;

			case 2:
				if (secuencia.get(inx).equals("-23")) {
					inx++;
					if (tokens.get(inx).equals("]")) {
						estado = 5;
					} else if (tokens.get(inx).equals(",")) {
						estado = 4;
					}

				} else {
					estado = 6;
				}
				break;

			case 3:
				if (secuencia.get(inx).equals("-51")) {
					inx++;
					if (tokens.get(inx).equals("]")) {
						estado = 5;
					} else if (tokens.get(inx).equals(",")) {
						estado = 4;
					}
				} else {
					estado = 6;
				}
				break;

			case 4:
				if (tokens.get(inx).equals(",")) {
					inx++;
					if (secuencia.get(inx).equals("-23")) {
						estado = 2;
					} else if (secuencia.get(inx).equals("-51")) {
						estado = 3;
					}
				} else {
					estado = 6;
				}
				break;

			case 5:
				if (tokens.get(inx).equals("]")) {
					inx++;
					estado = 7;
				} else {
					estado = 6;
				}
				break;

			case 6:
				// Estado de error regresamos false y terminamos el ciclo
				continuar = false;
				check = true;
				break;

			case 7:
				// Estado final
				check = true;
				break;

			}

		}

		return check;

	}
	
	private boolean expresionAritmetica(int inx)
	{
		boolean check = false;
		boolean continuar = true;
		int estado = 0;

		while (check != true) {
			switch (estado) {
			case 0:
				if (tokens.get(inx).equals("("))
				{
					inx++;
					if (tokens.get(inx).equals("("))
					{
						estado=0;
					}
					else if(secuencia.get(inx).equals("-23"))
					{
						estado=1;
					}
					else if(secuencia.get(inx).contains("-52-53-54-55"))
					{
						estado=2;
					}
				}else if(secuencia.get(inx).equals("-23"))
				{
					estado=1;
				}
				else if(secuencia.get(inx).contains("-52-53-54-55"))
				{
					estado=2;
				}
				else {
					//Estado que determina el error
					estado=6;
				}
				break;
				
			case 1:
				if(secuencia.get(inx).equals("-23"))
				{
					inx++;
					if (tokens.get(inx).equals("(")) {
					
						estado=3;
					}else if(operacionAritmetica(inx))
					{
						estado=4;
					}else {
						estado=5;
					}
				}else {
					estado=6;
				}
				break;
				
			case 2:
				if(secuencia.get(inx).contains("-52-53-54-55"))
				{
					inx++;
					if (tokens.get(inx).equals("(")) {
						estado=3;
					}else if(operacionAritmetica(inx))
					{
						estado=4;
					}else {
					//Estado final
					estado=5;
					}
				}else {
					estado=6;
				}
				break;
				
			case 3:
				if (tokens.get(inx).equals("("))
				{
					inx++;
					if (tokens.get(inx).equals("("))
					{
						estado=3;
					}else if(operacionAritmetica(inx))
					{
						estado=4;
					}else {
						estado=5;
					}
					
				}else
				{
					estado=6;
				}
				break;
				
			case 4:
				if(operacionAritmetica(inx))
				{
					inx++;
					estado=0;
				}else
				{
					estado=6;
				}
				break;
				
			case 5:
				//Estado final
				check = true;
				break;
				
			case 6:
				// Estado de error regresamos false y terminamos el ciclo
				continuar = false;
				check = true;
				break;
				
			}
		}
		return continuar;
	}
	
	private boolean asignar(int inx)
	{
		boolean check = false;
		boolean continuar = true;
		int estado = 0;
		
		while(check != true)
		{
			switch(estado){
			case 0:
					if(idArreglo(inx))
					{
						estado=1;
					}else
					{	//Estado de error
						estado=5;
					}
					
					break;
					
			case 1:
				if(tokens.get(inx).equals("="))
				{
					inx++;
					estado=2;
				}else {
					estado=5;
				}
				break;
				
			case 2:
				if(expresionAritmetica(inx)) {
					estado=3;
				}else {
					estado=5;
				}
				break;
				
			case 3:
				if(tokens.get(inx).equals(";"))
				{
					estado=4;
				}else {
					estado=5;
				}
				break;
				
			case 4:
				//Estado final
				check = true;
				break;
				
			case 5:
				// Estado de error regresamos false y terminamos el ciclo
				continuar = false;
				check = true;
				break;
			}
		}
		return continuar;
	}
	
	private boolean operadoresLogicos(int inx)
	{
		boolean continuar = false;

		if (tokens.get(inx).contains("&&||")) {
			continuar = true;
			inx++;
		}

		return continuar;
		
	}
	
	private boolean operadoresRelacionales(int inx)
	{
		boolean continuar = false;

		if (tokens.get(inx).equals("<")) {
			continuar = true;
			inx++;
		}else if(tokens.get(inx).equals("<="))
		{
			continuar=true;
			inx++;
		}else if(tokens.get(inx).equals(">"))
		{
			continuar=true;
			inx++;
		}else if(tokens.get(inx).equals(">="))
		{
			continuar=true;
			inx++;
		}else if(tokens.get(inx).equals("!="))
		{
			continuar=true;
			inx++;
		}else if (tokens.get(inx).equals("=="))
		{
			continuar=true;
			inx++;
		}
			
		return continuar;
	}
	
	
	
}



