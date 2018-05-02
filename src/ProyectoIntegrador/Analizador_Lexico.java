/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProyectoIntegrador;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import java.util.regex.*;
import javax.swing.JOptionPane;

/**
 *
 * @author David
 */
public class Analizador_Lexico {

	File IDENTIFICADORES;// Archivo con los elementos del lenguaje
	File ERRORES_L;// Archivo con los errores léxicos
	File CF;// Variable para codigo fuente que se recibirá en el constructor
	String[][] PALABRAS_RESERVADAS = new String[24][2];// Tabla donde guardaremos las palabras reservadas
	String[][] SIMBOLOS_CONSTANTES = new String[32][2];// Tabla donde guardaremos los símbolos y constantes
	String[][] ERRORES_LEXICOS = new String[12][2]; // Tabla donde guardaremos los errores léxicos
	private String posicionTabla = "";// Variable donde guardaremos la posicion de la tabla

	/*
	 * Constructor que recibe por parámetro el codigo fuente que se va a analizar
	 */
	public Analizador_Lexico(File codigo_fuente) {
		CF = codigo_fuente;
		leerTablaIdentificadores();
		leerTablaErrores();

	}

	public String leerCodigoFuente() throws ErroresLexicosSimbolos, ErroresLexicosIdentificadores {
		String linea; //// Variable donde se guardarán las lineas leidas del archivo
		StringTokenizer token; // Variable que se utilizará para dividir en tokens las lineas
		int CONT_LINEA = 0; // Variable que contará las lineas del archivo
		//int CONT_TOKEN = 0;// Variable que contará los tokens de las lineas
		String escribir = "";
		try {
			String aux;// Variable auxiliar donde guardamos el token leido
			FileReader archivos = new FileReader(CF);// Leer el archivo de código fuente
			BufferedReader lee = new BufferedReader(archivos);
			while ((linea = lee.readLine()) != null) {
				CONT_LINEA++;
				//CONT_TOKEN = 0;
				/*
				 * Método verificar que no tengamos comentarios
				 */
				if (!eliminarComentarios(linea)) {
					token = new StringTokenizer(linea);
					while (token.hasMoreTokens()) {
						//CONT_TOKEN++;
						aux = token.nextToken();
						erroresLexicosSimbolos(aux, CONT_LINEA);
						/*
						 * Falta agregar método donde se escribirá la tabla
						 */

						escribir += aux + ", " + analizadorLexico(aux, CONT_LINEA) + ", " + posicionTabla + ", "
								+ CONT_LINEA + "\n" + "\n";

					}
				}

			}

			lee.close();
		} catch (IOException e) {

		}

		return escribir;

	}

	public File leerCodigoFuente2() throws ErroresLexicosSimbolos, ErroresLexicosIdentificadores {
		String linea; //// Variable donde se guardarán las lineas leidas del archivo
		StringTokenizer token; // Variable que se utilizará para dividir en tokens las lineas
		int CONT_LINEA = 0; // Variable que contará las lineas del archivo
		int CONT_TOKEN = 0;// Variable que contará los tokens de las lineas
		String escribir = "";
		File tabla_simbolos = new File("Tabla_Tokens.txt");
		try {
			FileWriter w = new FileWriter(tabla_simbolos);
			PrintWriter writer = new PrintWriter(w);
			String aux;// Variable auxiliar donde guardamos el token leido
			FileReader archivos = new FileReader(CF);// Leer el archivo de código fuente
			BufferedReader lee = new BufferedReader(archivos);
			while ((linea = lee.readLine()) != null) {
				CONT_LINEA++;
				CONT_TOKEN = 0;
				/*
				 * Método verificar que no tengamos comentarios
				 */
				if (!eliminarComentarios(linea)) {
					token = new StringTokenizer(linea);
					while (token.hasMoreTokens()) {
						CONT_TOKEN++;
						aux = token.nextToken();
						erroresLexicosSimbolos(aux, CONT_LINEA);
						/*
						 * Falta agregar método donde se escribirá la tabla
						 */

						escribir = aux + " " + analizadorLexico(aux, CONT_LINEA) + " " + posicionTabla + " "
								+ CONT_LINEA;
						//
						writer.println(escribir);
					}
				}

			}
			writer.close();
			lee.close();
		} catch (IOException e) {

		}

		return tabla_simbolos;

	}

	/*
	 * Método que lee de un archivo los elementos que compondrán nuestro lenguaje
	 * Lo guarda en una tabla,que utilizaremos al momento de analizar el codigo
	 * fuente
	 */
	public void leerTablaIdentificadores() {
		String linea; // Variable donde se guardarán las lineas leidas del archivo
		StringTokenizer token; // Variable que se utilizará para dividir en tokens las lineas
		int cont = 0;// Contador que deterinará el identificador de cada elemento de nuestro
						// lenguaje
		boolean check = true;// Variable que nos permitirá verificar cuando debemos de cambiar de tabla
		IDENTIFICADORES = new File(
				"C:\\Users\\David\\git\\LenguajesYAutomatas\\src\\ProyectoIntegrador\\ide.txt");
		FileReader archivos = null;
		try {

			/*
			 * Utilizamos un buffer para leer nuestro archivo
			 */

			if (IDENTIFICADORES != null) {
				System.out.print(IDENTIFICADORES.getAbsolutePath());
			}
			archivos = new FileReader(IDENTIFICADORES);
			BufferedReader lee = new BufferedReader(archivos);

			/*
			 * Ciclo donde leemos el archivo
			 */
			while ((linea = lee.readLine()) != null) {
				/*
				 * Condición que verifica si lo leido en el archivo es nuestro separador "--"
				 * para saber cuando se han terminado las palabras reservadas
				 */
				if (linea.equals("--")) {
					check = false;
					cont = 0;

				} else {

					token = new StringTokenizer(linea);
					if (check) {
						/*
						 * Guardamos dentro de la tabla PALABRAS_RESERVADAS lo que hemos leido del
						 * archvo
						 */
						PALABRAS_RESERVADAS[cont][0] = token.nextToken();
						PALABRAS_RESERVADAS[cont][1] = token.nextToken();
						cont++;// Aumentamos el contador
					} else {
						/*
						 * Guardamos dentro de la tabla SIMBOLOS_CONSTANTES lo que hemos leido del
						 * archivo
						 */
						SIMBOLOS_CONSTANTES[cont][0] = token.nextToken();
						SIMBOLOS_CONSTANTES[cont][1] = token.nextToken();
						cont++;// Aumentar el contador
					}
				}
			}
			lee.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Error");
		}
	}

	public void leerTablaErrores() {
		String linea; // Variable donde se guardarán las lineas leidas del archivo
		StringTokenizer token; // Variable que se utilizará para dividir en tokens las lineas
		int cont = 0;// Contador que deterinará el identificador de cada elemento de nuestro
						// lenguaje
		ERRORES_L = new File(
				"C:\\Users\\David\\git\\LenguajesYAutomatas\\src\\ProyectoIntegrador\\err.txt");
		try {

			/*
			 * Utilizamos un buffer para leer nuestro archivo
			 */
			FileReader archivos = new FileReader(ERRORES_L);
			BufferedReader lee = new BufferedReader(archivos);

			/*
			 * Ciclo donde leemos el archivo
			 */
			while ((linea = lee.readLine()) != null) {
				token = new StringTokenizer(linea);
				/*
				 * Guardamos dentro de la tabla de errores lexicos el contenido leido desde el
				 * archivo
				 */
				ERRORES_LEXICOS[cont][0] = token.nextToken();
				ERRORES_LEXICOS[cont][1] = token.nextToken();
				cont++;// Aumentar el contador

			}
			lee.close();

		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Error");
		}
	}

	/*
	 * Método para eliminar los comentarios del código fuente
	 */
	public boolean eliminarComentarios(String linea) {
		return (linea.charAt(0) == '/' && linea.charAt(1) == '/');

	}

	public String getPosicionTable() {
		return posicionTabla;
	}

	/*
	 * Método que analizará el léxico del código fuente
	 */

	public String analizadorLexico(String token, int contadorToken) throws ErroresLexicosIdentificadores {

		posicionTabla = "-1";// Posición en la tabla inicilizamos con -1
		String numero = "";
		String aux = token.toLowerCase();// Hacemos mayúsculas todas los tokens para una mejor búsqueda
		boolean check = true;
		Pattern constStr = Pattern.compile("^\".\"$");// Variable que contiene la expresión regular de una constante
														// String
		Matcher mConstStr = constStr.matcher(aux);

		Pattern constChr = Pattern.compile("^\'.\'$");// Variable que contiene la expresión regular de una constante
														// String
		Matcher mConstChr = constChr.matcher(aux);

		/*
		 * Verificamoss si es una constante String
		 */
		if (mConstStr.matches()) {
			return "-54";
		}
		/*
		 * Verificamos si es una constant Char
		 */
		else if (mConstChr.matches()) {
			return "-55";
		}
		/*
		 * Verificamos si es una consante entera
		 */
		else if (isNumeric(aux)) {
			return "-52";
		}
		/*
		 * Verificamos si es una constante real
		 */
		else if (isReal(aux)) {
			return "-53";
		}
		/*
		 * Verificamos si es algún signo: aritmético/lógico/relacional/especial Si no
		 * contiene algún signo, verificamos si es alguna palabra reservada Si no se
		 * encuentra en alguna de ellas es un identificador. Si tiene algún signo pero
		 * no es igual, es un error sintáctico
		 */
		else {

			for (int i = 0; i < SIMBOLOS_CONSTANTES.length; i++) {
				if (aux.equals(SIMBOLOS_CONSTANTES[i][0])) {
					numero = SIMBOLOS_CONSTANTES[i][1];// Verificamos si el simbolo se encuentra en la tabla
					check = false;// Si se encuentra significa que no es un simbolo o identificador mal formado
					break;// Quebramos el ciclo para regresar el número del simbolo utilizado

				}
			}
			/*
			 * Verificamos si es el check es verdadero, si lo es, analizamos si la cadena es
			 * un error, si lo es, lanzamos una excepción para detener la búsqueda
			 */
			if (check) {
				erroresLexicosIdentificadores(aux, contadorToken);
			}

			for (int i = 0; i < PALABRAS_RESERVADAS.length; i++) {
				if (aux.equals(PALABRAS_RESERVADAS[i][0])) {
					numero = PALABRAS_RESERVADAS[i][1];

					break;

				}
			}

			if (numero.equals("")) {
				erroresLexicosIdentificadores(aux, contadorToken);
				posicionTabla = "-2";
				return "-24";
			}
		}
		return numero;
	}

	public static boolean isNumeric(String cadena) {
		try {
			Integer.parseInt(cadena);
			return true;
		} catch (NumberFormatException nfe) {
			return false;
		}
	}

	public static boolean isReal(String cadena) {
		try {
			Double.parseDouble(cadena);
			return true;
		} catch (NumberFormatException nfe) {
			return false;
		}
	}

	/*
	 * Método que verificará si tiene errores léxicos
	 */
	public void erroresLexicosSimbolos(String token, int contadorLineas) throws ErroresLexicosSimbolos {
		for (int i = 0; i < ERRORES_LEXICOS.length; i++) {
			if (token.equals(ERRORES_LEXICOS[i][0])) {
				throw new ErroresLexicosSimbolos(token, contadorLineas);
			}
		}
	}

	/*
	 * Método que verificará si nuestro identificador está bien escrito
	 */
	public void erroresLexicosIdentificadores(String token, int contadorLineas) throws ErroresLexicosIdentificadores {

		for (int i = 0; i < token.length(); i++) {
			if (Character.isLetter(token.charAt(0))) {
				for (int j = 0; j < ERRORES_LEXICOS.length; j++) {
					String aux = ERRORES_LEXICOS[j][0];

					if (aux.equals(Character.toString(token.charAt(i)))) {
						throw new ErroresLexicosIdentificadores(token, contadorLineas);
					}
				}

			} else {
				throw new ErroresLexicosIdentificadores(token, contadorLineas);
			}
		}

	}
}

// }
