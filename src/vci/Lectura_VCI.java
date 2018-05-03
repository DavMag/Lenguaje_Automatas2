package vci;

import java.util.ArrayList;
import java.util.Stack;
import java.util.StringTokenizer;

public class Lectura_VCI {

	ArrayList<String> tokens = new ArrayList<>(); //ArrayList que contendrá los elementos leidos
	ArrayList<String> vci = new ArrayList<>(); //ArrayList que contendrá el vector de código intermedio

	Stack<String> operadores = new Stack<String>(); //Pila de operadores

	//Método para leer los tokens de un código fuente y añadirlos al ArrayList tokens
	private void leerTokens(String codigo) {
		StringTokenizer stz = new StringTokenizer(codigo);

		while (stz.hasMoreTokens()) {
			tokens.add(stz.nextToken());
		}

	}

	//Determinamos la prioridad
	private int prioridad(String token) {
		int prioridad = 0;
		switch (token) {
		case "(":
		case ")":
		case "=":
			prioridad = 0;
			break;
		case "||":
			prioridad = 10;
			break;
		case "&&":
			prioridad = 20;
			break;
		case "!":
			prioridad = 30;
			break;
		case "<":
		case "<=":
		case ">":
		case ">=":
		case "!=":
		case "==":
			prioridad = 40;
			break;
		case "+":
		case "-":
			prioridad = 50;
			break;
		case "*":
		case "/":
			prioridad = 60;
			break;
		default:
			prioridad = -1;
			break;
		}
		return prioridad;
	}

	private void creacionVCI() {
		for (int i = 0; i < tokens.size(); i++) {
			if (prioridad(tokens.get(i)) == -1) {
				vci.add(tokens.get(i));
			} else {
				int aux;
				int prioridad = prioridad(tokens.get(i));
				// Verifica que la pila no esté vacía
				if (!(operadores.isEmpty())) {
					aux = prioridad(String.valueOf(operadores.peek()));
					if (prioridad >= aux) {
						operadores.push(tokens.get(i));
					} else {
						vci.add(operadores.pop());
					}
					// Si está vacía simplemente insertamos los valores en la pila
				} else if (i == tokens.size() - 1) {
					vci.add(operadores.pop());
				} else {
					operadores.push(tokens.get(i));
				}

			}

		}
	}

}
