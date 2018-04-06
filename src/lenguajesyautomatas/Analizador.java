/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lenguajesyautomatas;


/**
 *
 * @author David
 */
public class Analizador {

    private String posicionTabla="";
    
    //modificar identificadores
    private String[][] tabla = {{"IF", "-1"}, {"ELSE", "-2"}, {"FOR", "-3"}, {"WHILE", "-4"}, {"DO", "-5"}, {"NULL", "-6"}, {"READ", "-7"}, {"READLINE", "-8"}, {"SWITCH", "-9"},
    {"PRINT", "-10"}, {"THIS", "-11"}, {"TRY", "-12"}, {"CATCH", "-13"}, {"ID", "-14"}, {"ID_ENTERO", "-15"}, {"ID_REAL", "-16"}, {"ID_STRING", "-17"}, {"ID_RUTINA", "-18"},
    {"+", "-19"}, {"-", "-20"}, {"*", "-21"}, {"/", "22"}, {"%", "-23"}, {".", "-24"}, {";", "-25"}, {"=", "-26"}, {"CONST_ENTERO", "-27"}, {"CONST_REAL", "-28"},
    {"CONTS_STRING", "-29"}, {">", "-30"}, {"<", "-31"}, {">=", "-32"}, {"<=", "-33"}, {"==", "-34"}, {"!=", "-35"}, {"!", "-36"}, {"&&", "-37"}, {"||", "-38"},
    {"AND", "-39"}, {"OR", "-40"}, {"NOT", "-41"}, {"EQUAL", "-42"}, {"(", "-43"}, {")", "-44"}, {"{", "-45"}, {"}", "-46"}, {"[", "-47"}, {"]", "-48"}};

    public String verificadorEnTabla(String token,String cadena) {
        posicionTabla="-1";
        String numero = "";
        String aux = token.toUpperCase();
        boolean check = true;
        if(token.contains("\""))
        {
            return "-29";
        }
        if (isNumeric(token)) {
            return "-27";
        } else if (isReal(token)) {
            return "-28";
        } else {
            for (int i = 0; i < tabla.length; i++) {
                if (aux.equals(tabla[i][0])) {
                    check = false;
                    numero = tabla[i][1];
                    break;

                }
            }

            //Poner validacion para verificar identificadores, como los errores
            if(check)
            {
                numero=valIdentificador(cadena,token);
                posicionTabla="-2";
                if(numero.equals(""))
                {
                    numero="-14";
                }
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

    public String getTabla(int x, int y) {
        return tabla[x][y];
    }

    public static boolean isLetter(String cadena) {
        boolean check = false;
        for (int i = 0; i < cadena.length(); i++) {
            if (Character.isLetter(cadena.charAt(i))) {
                check = true;
            }
        }
        return check;
    }

    //La cadena que entra es la linea para verificar el contexto del token
    public static String valIdentificador(String cadena, String token) {
        String reg = "";
        int indice = 0;
        boolean check=true;
        String[] linea = cadena.split(" ");
        for (int i = 0; i < linea.length; i++) {
            if (token.equals(linea[i])) {
                indice = i;
                break;
            }
        }
        if (indice == 0) {
            if (linea[1].equals("=")) {
                if (linea[2].contains("\"")) {
                    reg = "-17";
                } else if (isNumeric(linea[2])) {
                    reg = "-15";
                } else if (isReal(linea[2])) {
                    reg = "-16";
                } else if (isLetter(linea[2])) {
                    if (linea[3].contains("+-*/%")) {
                        if (linea[3].equals("+") && linea[3].contains("\"")) {
                            reg = "-17";
                        }
                        for (int i = 3; i < linea.length; i++) {
                            if (isNumeric(linea[i])) {
                                reg = "-15";
                                break;
                            } else if (isReal(linea[i])) {
                                reg = "-16";
                                break;
                            }

                        }
                    } else {
                        reg = "-14";
                    }
                }

            }
        } else if (indice == linea.length - 1) {
            if (linea[indice - 1].contains("+*/-%")) {
                if (linea[indice - 1].equals("+") && linea[indice - 2].contains("\"")) {
                    reg = "-17";
                }
                for (int i = 0; i < linea.length; i++) {
                    if (isNumeric(linea[i])) {
                        reg = "-15";
                        break;
                    } else if (isReal(linea[i])) {
                        reg = "-16";
                        break;
                    }

                }
            } else if (linea[indice - 1].equals("=")) {
                reg = "-14";
            }
        } else if (linea[indice - 1].contains("><=|&!+/*-%ANDORNOT") || linea[indice + 1].contains("><|&=!+-*/%ANDORNOT")) {
            if (linea[indice - 1].equals("+") || linea[indice + 1].contains("+")) {
                if (linea[indice - 1].equals("+")) {
                    if (linea[indice - 2].contains("\"")) {
                        reg = "-17";
                    }
                } else if (linea[indice + 1].equals("+")) {
                    if (linea[indice + 2].contains("\"")) {
                        reg = "-17";
                    }
                }
            }
                for (int i = 0; i < linea.length; i++) {
                    if (isNumeric(linea[i])) {
                        check=false;
                        reg = "-15";
                        break;
                    } else if (isReal(linea[i])) {
                        check=false;
                        reg = "-16";
                        break;
                    }
                    
                   if(check==true)
                   {
                       reg="-14";
                   }
        }
        }
        
        return reg;
    }

    public String getPosicionTable()
    {
        return posicionTabla;
    }
    
    public static void main(String[] args) {
        int i[] = new int[3];
        Analizador r = new Analizador();
        System.out.print(i.length);
    }

}
