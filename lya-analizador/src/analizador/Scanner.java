package analizador;
import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Scanner {


    public static void main (String[] args) throws IOException {

        ArrayList<String> lineas = Scanner("Prueba2.txt");
        //String input;
        ArrayList<Token> tokens = lex(lineas);
        Parser parser = new Parser(tokens);
        Semantico sem = null;
        while(parser.tokenActual!=-1){
            parser.P();
        }
        if(parser.error){
            System.out.println("Error: cadena incorrecta");
        }else{
            System.out.println("Los tokens son correctos");
            sem = new Semantico(parser.p);
        }
    }

    public static ArrayList <String> Scanner (String archivo) throws FileNotFoundException, IOException {
        String cadena;
        ArrayList <String> lista = new ArrayList<>();
        //StringTokenizer st; //Clase que separa los tokens

        FileReader f = new FileReader(archivo);
        BufferedReader b = new BufferedReader(f);
        while((cadena = b.readLine())!=null) {
            lista.add(cadena);
        }
        b.close();
        return lista;
    }


    private static ArrayList<Token> lex (ArrayList<String> lineas){
        final ArrayList<Token> tokens = new ArrayList<Token>();
        for(String input : lineas){
            final StringTokenizer st = new StringTokenizer(input);
            while(st.hasMoreTokens()) {
                String palabra = st.nextToken();
                boolean matched = false;

                for (Token.Tipos tokenTipo : Token.Tipos.values()) {
                    Pattern patron = Pattern.compile(tokenTipo.patron);
                    Matcher matcher = patron.matcher(palabra);
                    if(matcher.find()) {
                        Token tk = new Token();
                        tk.setTipo(tokenTipo);
                        tk.setValor(palabra);
                        tokens.add(tk);
                        matched = true;
                        break;
                    }
                }

                if (!matched) {
                    throw new RuntimeException("Se encontró un token invalido.");
                }
            }
        }
        return tokens;
    }
}



