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
	int inx;
	int contadorBeginsEnds;
	
	public Analizador_Sintactico() {
		inx = 0;
		obtencionToken();
		contadorBeginsEnds=0;

	}

	public void obtencionToken() {
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

			}
			tokens.add("$");
			System.out.println(tokens.size());
		} catch (Exception e) {

		}
	}

	private boolean operacionAritmetica() {
		boolean continuar = false;

		if ((tokens.get(inx).equals("+")) || (tokens.get(inx).equals("-")) || (tokens.get(inx).equals("*"))
				|| (tokens.get(inx).equals("/"))) {
			continuar = true;
			inx++;
		}

		return continuar;
	}

	private boolean idArreglo() {
		boolean check = false;
		boolean continuar = true;
		int estado = 0;

		while (check != true) {
			switch (estado) {
			case 0:
				if (secuencia.get(inx).equals("-24")) {
					inx++;

					estado = 1;

				}

				else {
					// Estado que determina el error en la gram·tica
					estado = 5;
				}

				break;
			case 1:
				if (tokens.get(inx).equals("[")) {
					inx++;
					/*
					 * if (secuencia.get(inx).equals("-24")) { estado = 2; } else if
					 * (secuencia.get(inx).equals("-51")) { estado = 3; }
					 */
					estado = 2;
				} else {
					// Nos vamos al final
					estado = 4;
				}
				break;

			case 2:
				if (secuencia.get(inx).equals("-24") || secuencia.get(inx).equals("-51")) {
					inx++;
					estado = 3;
					/*
					 * if (tokens.get(inx).equals("]")) { estado = 5; } else if
					 * (tokens.get(inx).equals(",")) { estado = 4; }
					 */
				} else {
					// Error
					estado = 5;
				}
				break;

			case 3:

				if (tokens.get(inx).equals("]")) {
					estado = 4;
				} else if (tokens.get(inx).equals(",")) {
					estado = 2;
				} else {
					// Error
					estado = 5;
				}
				break;

			case 4:
				check = true;
				break;
			case 5:

				continuar = false;
				check = true;
				break;

			}

		}

		return continuar;

	}

	private boolean constantes() {
		boolean continuar = false;
		if (secuencia.get(inx).contains("-52") || secuencia.get(inx).contains("-53")
				|| secuencia.get(inx).contains("-54") || secuencia.get(inx).contains("-55")) {
			continuar = true;
			inx++;
		}
		return continuar;
	}

	private boolean expresionAritmetica(boolean cond) {
		boolean check = false;
		boolean continuar = true;
		int estado = 0;
		int contadorParentesis;
		if(cond)
		{
			contadorParentesis=1;
		}else {
			contadorParentesis=0;
		}

		while (check != true) {
			switch (estado) {
			case 0:
				if (tokens.get(inx).equals("(")) {
					inx++;
					contadorParentesis++;
					/*
					 * if (tokens.get(inx).equals("(")) { estado = 0; } else if
					 * (secuencia.get(inx).equals("-24")) { estado = 1; } else if
					 * (secuencia.get(inx).contains("-52-53-54-55")) { estado = 2; }
					 */
					estado = 0;

				} else if (secuencia.get(inx).equals("-24")) {
					inx++;
					estado = 1;
				} else if (constantes()) {

					estado = 1;
				} else {
					// estado de error
					estado = 3;
				}
				break;

			case 1:
				
				if(cond && tokens.get(inx).equals(")") && contadorParentesis==1)
				{
					estado=2;
					
				}
				else if (tokens.get(inx).equals(")")) {
					inx++;
					contadorParentesis--;
					estado = 1;

				} else if (operacionAritmetica()) {

					estado = 0;

				} else if(contadorParentesis==0 || (cond && contadorParentesis==1)){
					
					estado = 2;
				}else {
					estado=3;
				}
				break;

			case 2:
				check = true;
				break;

			case 3:

				continuar = false;
				check = true;
				break;

			}
		}
		return continuar;
	}

	private boolean asignar() {
		boolean check = false;
		boolean continuar = true;
		int estado = 0;

		while (check != true) {
			switch (estado) {
			case 0:
				if (idArreglo()) {
					estado = 1;
				} else { // Estado de error
					estado = 5;
				}

				break;

			case 1:
				if (tokens.get(inx).equals("=")) {
					inx++;
					estado = 2;
				} else {
					estado = 5;
				}
				break;

			case 2:
				if (expresionAritmetica(false)) {
					estado = 3;
				} else {
					estado = 5;
				}
				break;

			case 3:
				if (tokens.get(inx).equals(";")) {
					inx++;
					estado = 4;
				} else {
					estado = 5;
				}
				break;

			case 4:
				// Estado final
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

	private boolean operadoresLogicos() {
		boolean continuar = false;

		if ((tokens.get(inx).equals("&&")) || (tokens.get(inx).equals("||"))) {
			continuar = true;
			inx++;
		}

		return continuar;

	}

	private boolean operadoresRelacionales() {
		boolean continuar = false;

		if ((tokens.get(inx).equals("<")) || (tokens.get(inx).equals("<=")) || (tokens.get(inx).equals(">"))
				|| (tokens.get(inx).equals(">=")) || (tokens.get(inx).equals("!=")) || (tokens.get(inx).equals("=="))) {
			continuar = true;
			inx++;
		}

		return continuar;
	}

	private boolean condicion() {
		boolean check = false;
		boolean continuar = true;
		int estado = 0;
		while (check != true) {
			switch (estado) {
			case 0:
				if (expresionAritmetica(true)) {
					estado = 1;
				} else {
					estado = 4;
				}
				break;

			case 1:
				if (operadoresRelacionales()) {
					estado = 2;
				} else {
					estado = 4;
				}
				break;

			case 2:
				if (expresionAritmetica(true)) {
					estado = 3;
				} else {
					estado = 4;
				}
				break;

			case 3:
				if (operadoresLogicos()) {
					estado = 0;
				} else {
					check = true;
					break;
				}
				break;

			case 4:
				continuar = false;
				check = true;
				break;
			}
		}
		return continuar;
	}

	private boolean tipo() {
		boolean continuar = false;

		if ((tokens.get(inx).equals("boolean")) || (tokens.get(inx).equals("char"))
				|| (tokens.get(inx).equals("double")) || (tokens.get(inx).equals("int"))
				|| (tokens.get(inx).equals("string"))) {
			inx++;
			continuar = true;
		}

		return continuar;
	}

	private boolean variable() {
		boolean check = false;
		boolean continuar = true;
		int estado = 0;
		while (check != true) {
			switch (estado) {

			case 0:
				if (tipo()) {
					estado = 1;
				} else {
					// En esta gramatica podemos irnos al final inmediatamente
					check = true;

				}
				break;

			case 1:
				if (secuencia.get(inx).equals("-24")) {
					inx++;
					estado = 2;
				} else {
					// Estado de error
					estado = 6;
				}
				break;

			case 2:
				if (tokens.get(inx).equals("[")) {
					inx++;
					estado = 3;
				} else {
					// numero pendiente
					estado = 5;
				}

				break;

			case 3:
				if (secuencia.get(inx).equals("-51")) {
					inx++;
					estado = 4;
				} else {
					estado = 6;
				}
				break;

			case 4:
				if (tokens.get(inx).equals("]")) {
					inx++;
					estado = 5;
				} else if (tokens.get(inx).equals(",")) {
					inx++;
					estado = 3;

				} else {
					estado = 6;
				}
				break;

			case 5:
				if (tokens.get(inx).equals(";")) {
					inx++;
					estado = 0;
				} else if (tokens.get(inx).equals(",")) {
					inx++;
					estado = 1;
				} else {
					estado = 6;
				}
				break;

			case 6:
				continuar = false;
				check = true;
				break;

			}
		}
		return continuar;

	}

	public boolean prog() {
		boolean check = false;
		boolean continuar = true;
		int estado = 0;

		while (check != true) {
			switch (estado) {
			case 0:
				if (tokens.get(inx).equals("prog")) {
					inx++;
					estado = 1;
				} else {
					estado = 6;
				}
				break;
			case 1:
				if (secuencia.get(inx).equals("-24")) {
					inx++;

					estado = 2;

				} else {
					estado = 6;
				}
				break;

			case 2:
				if (tokens.get(inx).equals(";")) {
					inx++;
					estado = 3;
				} else {
					estado = 6;
				}
				break;

			case 3:
				if (variable()) {
					estado = 4;
				} else {
					estado = 6;
				}
				break;

			case 4:
				if (bloqueInicio()) {
					estado = 5;
				} else {
					estado = 6;
				}
				break;

			case 5:

				check = true;
				break;
			case 6:

				continuar = false;
				check = true;
				break;
			}
		}
		return continuar;
	}

	private boolean bloqueInicio() {
		boolean check = false;
		boolean continuar = true;
		int estado = 0;
		

		while (check != true) {
			switch (estado) {
			case 0:
				if (tokens.get(inx).equals("begin")) {
					contadorBeginsEnds++;
					inx++;
					estado = 1;
				} else {
					estado = 4; // error
				}
				break;
			case 1:

				if (cond()) {
					estado = 2;
				} else {
					estado = 4; // error
				}
				break;
			case 2:
				if (cond()) {
					estado = 2;
					break;
				} else if (tokens.get(inx).equals("end")) {
					contadorBeginsEnds --;
					inx++;
					if(tokens.get(inx).equals("$"))
					{
						if(contadorBeginsEnds==0) {
							estado=3;
						}else {
							estado=4;
						}
						
					}else {
						estado=3;
					}
					
//					if(!((tokens.size()-1)==inx))
//					{
//						inx++;
//					}else {
//						estado=3;
//					}
//					if(contadorBeginsEnds==0)
//					{
//						estado = 3;
//					}else {
//						estado=4;
//					}
					
					break;
				} else {
					estado = 4; // error
					break;
				}

			case 3:
				check = true;
				break;

			case 4:
				continuar = false;
				check = true;
				break;
			}
		}
		return continuar;
	}

	private boolean cond() {
		boolean continuar = false;

		if (asignar() || ifCheck() || doWhile() || whileCheck() || leer() || escribir()) {
			continuar = true;
		}

		return continuar;
	}

	private boolean leer() {
		boolean check = false;
		boolean continuar = true;
		int estado = 0;

		while (check != true) {
			switch (estado) {
			case 0:
				if (tokens.get(inx).equals("input")) {
					inx++;
					estado = 1;
				} else {
					// estado que determina el error
					estado = 6;
				}

			case 1:
				if (tokens.get(inx).equals("(")) {
					inx++;
					estado = 2;

				} else {
					estado = 6;
				}

				break;
			case 2:
				if (idArreglo()) {

					estado = 3;

				} else {
					estado = 6;
				}
				break;
			case 3:
				if (tokens.get(inx).equals(",")) {
					inx++;
					estado = 2;
				} else if (tokens.get(inx).equals(")")) {
					inx++;
					estado = 4;
				} else {
					estado = 6;
				}
				break;

			case 4:
				if (tokens.get(inx).equals(";")) {
					inx++;
					estado = 5;
				} else {
					estado = 6;
				}
				break;
			case 5:
				check = true;
				break;
			case 6:
				continuar = false;
				check = true;
				break;
			}
		}
		return continuar;

	}

	// Est· mal
	private boolean escribir() {
		boolean check = false;
		boolean continuar = true;
		int estado = 0;

		while (check != true) {
			switch (estado) {
			case 0:
				if (tokens.get(inx).equals("output")) {
					inx++;
					estado = 1;
				} else {
					// estado que determina el error
					estado = 6;
				}
				break;

			case 1:
				if (tokens.get(inx).equals("(")) {
					inx++;
					estado = 2;
				} else {
					estado = 6;
				}

				break;
			case 2:
				if (secuencia.get(inx).contains("-52-53-54-55")) {
					estado = 3;
					inx++;
				} else if (idArreglo()) {
					estado = 3; // para el idArreglo
				} else {
					estado = 6;
				}
				break;
			case 3:

				if (tokens.get(inx).equals(")")) {
					inx++;
					estado = 4;
				} else if (tokens.get(inx).equals(",")) {
					inx++;
					estado = 2;
				} else {
					estado = 6;
				}
				break;

			case 4:
				if (tokens.get(inx).equals(";")) {
					inx++;
					estado = 5;
				} else {
					estado = 6;
				}
				break;
			case 5:
				check = true;
				break;
			case 6:
				continuar = false;
				check = true;
				break;
			}
		}
		return continuar;

	}

	private boolean doWhile() {
		boolean check = false;
		boolean continuar = true;
		int estado = 0;

		while (check != true) {
			switch (estado) {
			case 0:
				if (tokens.get(inx).equals("do")) {
					inx++;
					estado = 1;
				} else {
					estado = 8;
				}
				break;
			case 1:
				if (bloqueInicio()) {
					estado = 2;
				} else {
					estado = 8;
				}
				break;
			case 2:
				// Aqui es la palabra while
				if (tokens.get(inx).equals("while")) {
					inx++;
					estado = 3;
				} else {
					estado = 8;
				}
				break;
			case 3:
				if (tokens.get(inx).equals("(")) {
					inx++;
					estado = 4;
				} else {
					estado = 8;
				}
				break;
			case 4:
				if (condicion()) {
					estado = 5;
				} else {
					estado = 8;
				}
				break;
			case 5:
				if (tokens.get(inx).equals(")")) {
					inx++;
					estado = 6;
				} else {
					estado = 8;
				}
				break;
			case 6:
				if (tokens.get(inx).equals(";")) {
					inx++;
					estado = 7;
				} else {
					estado = 8;
				}
			case 7:
				check = true;
				break;
			case 8:
				continuar = false;
				check = true;
				break;
			}
		}
		return continuar;

	}

	private boolean whileCheck() {
		boolean check = false;
		boolean continuar = true;
		int estado = 0;

		while (check != true) {
			switch (estado) {
			case 0:
				if (tokens.get(inx).equals("while")) {
					inx++;
					estado = 1;
				} else {
					estado = 6;
				}
				break;
			case 1:
				if (tokens.get(inx).equals("(")) {
					inx++;
					estado = 2;
				} else {
					estado = 6;
				}
				break;
			case 2:
				if (condicion()) {
					estado = 3;
				} else {
					estado = 6;
				}
				break;
			case 3:
				if (tokens.get(inx).equals(")")) {
					inx++;
					estado = 4;
				} else {
					estado = 6;
				}
				break;
			case 4:
				if (bloqueInicio()) {
					estado = 5;
				} else {
					estado = 6;
				}
				break;
			case 5:
				check = true;
				break;
			case 6:
				continuar = false;
				check = true;
				break;
				
			}
		}
		return continuar;

	}

	private boolean ifCheck() {
		boolean check = false;
		boolean continuar = true;
		int estado = 0;

		while (check != true) {
			switch (estado) {
			case 0:
				if (tokens.get(inx).equals("if")) {
					inx++;
					estado = 1;
				} else {
					estado = 8;
				}
				break;
			case 1:
				if (tokens.get(inx).equals("(")) {
					inx++;
					estado = 2;
				} else {
					estado = 8;
				}
				break;
			case 2:
				if (condicion()) {
					estado = 3;
				} else {
					estado = 8;
				}
				break;
			case 3:
				if (tokens.get(inx).equals(")")) {
					inx++;
					estado = 4;
				} else {
					estado = 8;
				}
				break;
			case 4:
				if (bloqueInicio()) {

					estado = 5;
				} else {
					estado = 8;
				}
				break;
			case 5:
				if (tokens.get(inx).equals("else")) {
					inx++;
					estado = 6;
				}  else {
					estado = 7;
				}
				break;
			case 6:
				if (bloqueInicio()) {

					estado = 7;
				} else {
					estado = 8;
				}
				break;
			
			case 7:
				check = true;
				break;
			case 8:
				continuar = false;
				check = true;
				break;
			}
		}
		return continuar;

	}
}
