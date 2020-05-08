package analizador;

import arbol_sintactico.*;


import java.lang.annotation.Target;
import java.util.ArrayList;

public class Semantico {
    Printx print;
    Beginx begin;
    Ifx ifx;
    Programa programa;
    ComparaNum comparaNum;
    ArrayList<String> variables, noExisten;
    ComparaId comparaId;
    Programa p;
    ArrayList<Tabla> tabla = new ArrayList<Tabla>();

    public Semantico(Programa programa) {
        this.programa = programa;
        variables = new ArrayList<String>();
        noExisten = new ArrayList<String>();


        for (int i = 0; i < programa.d.size(); i++) {
            String temp2 = programa.d.get(i).id;
            for (int j = programa.d.size() - 1; j >= 0; j--) {
                if (i != j && temp2.equals(programa.d.get(j).id)) {
                    System.out.println(temp2 + Help.whiteSpaces(temp2.length()) + "La variable es repetida :(");
                    programa.d.remove(j);
                }
            }
        }
        //Todas las variables que se utilizan en la setencias y en este caso se utiliza en en los if y en los print
        for (int i = 0; i < programa.s.size(); i++) {
            if (programa.s.get(i) instanceof Ifx) {
                ifx = (Ifx) programa.s.get(i); //Array con las sentencias .s
                Instancias(i, ifx.e);
            }
            if (programa.s.get(i) instanceof Printx) {
                print = (Printx) programa.s.get(i); //Array con las sentencias .s
                Instancias(i, print.e);
            }
        }

        for (int i = 0; i < variables.size(); i++) {
            for (int j = variables.size() - 1; j >= 0; j--) {
                if (i != j)
                    if (variables.get(i).equals(variables.get(j))) //Aquí es donde se fija si la variable está repetida
                        variables.remove(j);                        //Si es asi, la borra del array variables
            }
        }
        int cont = 0;
        String temp = "";
        for (int i = 0; i < variables.size(); i++) { // variables es un array donde se guarda variable por variable
            cont = 0;
            for (int j = 0; j < p.d.size(); j++) { //La p sirve para comparar con lo que está recibiendo de programa
                temp = p.d.get(j).id; //temp nos ayuda a guardar el valor que está en las declaraciones de p
                if (!variables.get(i).equals(temp)) {
                    cont++; //Si no existe lo que está recibiendo  entonces el cont aumentará
                }
            }
            if (cont == p.d.size()) {
                noExisten.add(variables.get(i));
                System.out.println(variables.get(i) + Help.whiteSpaces(variables.get(i).length()) + "Varrible no declarada en el programa");
            }
        }
        //Tabla de simbolos
        System.out.println("\n\tTabla de simbolos\n\nNo." + Help.whiteSpaces("No.".length() + 5) + "Tipo" + Help.whiteSpaces("Tipo".length()) +
                "Nombre" + Help.whiteSpaces("Nombre".length() - 5) + "Valor");
        for (int i = 0; i < programa.d.size(); i++) {
            String tipo = programa.d.get(i).t;
            String id = programa.d.get(i).id;
            String valor = programa.d.get(i).valor.num1;

            System.out.println((i + 1) + Help.whiteSpaces(String.valueOf(i + 1).length() + 5) + tipo + Help.whiteSpaces(tipo.length()) + id + Help.whiteSpaces(id.length() - 5) + valor);
        }
        Intermedio();
        System.out.println("\t\tTRIPLES\n");
        for (int i = 0; i < tabla.size(); i++) {
            System.out.println(	   tabla.get(i).i	+ Help.whiteSpaces(String.valueOf(tabla.get(i).i).length())
                    + tabla.get(i).signo	+ Help.whiteSpaces(tabla.get(i).signo.length())
                    + tabla.get(i).id1	+ Help.whiteSpaces(tabla.get(i).signo.length())
                    + tabla.get(i).e2s	+ Help.whiteSpaces(tabla.get(i).signo.length())
            );
        }
    }

    //Agarra todos los id (variables) sin importar si está definida.
    public void Instancias(int i, Object obj) {
        try {
            if (obj == ifx.e && obj instanceof ComparaId) {
                comparaId = (ComparaId) obj;
                variables.add(((Idx) comparaId.id1).id1);
                variables.add(((Idx) comparaId.id2).id1);
            }
        } catch (Exception e) {
        }
        try {
            if (obj == print.e && obj instanceof ComparaId) {
                comparaId = (ComparaId) obj;
                variables.add(((Idx) comparaId.id1).id1);
                variables.add(((Idx) comparaId.id2).id1);
            }
        } catch (Exception e) {
        }
    }

    private void PrintIntermedio(int i, String signo, String id1, String e2) {
        tabla.add(new Tabla(i, signo, id1, e2)); //signo: sub, jz, =, jp // = 0 x
    }


    private void Intermedio() {
        int indiceVerdadero = 1;
        for(int i = 0; i < programa.d.size(); i++){ //Recorre declaraciones
            PrintIntermedio(indiceVerdadero, "=", programa.d.get(i).valor.num1, programa.d.get(i).id);
            indiceVerdadero++;
        }
        for (int i = programa.s.size()-1; i >= 0; i--) { //Recorre las sentencias
            if (programa.s.get(i) instanceof Ifx) { //if
                ifx = (Ifx) programa.s.get(i);
                try {
                    if (ifx.e instanceof ComparaNum) {
                        comparaNum = (ComparaNum) ifx.e;
                        PrintIntermedio(indiceVerdadero, "SUB", ((Numx) comparaNum.e1).num1, ((Numx) comparaNum.e2).num1);
                        indiceVerdadero++;
                        PrintIntermedio(indiceVerdadero, "JZ", "("+ (indiceVerdadero-1) +")", "?");
                        indiceVerdadero++;
                    }
                    if (ifx.e instanceof ComparaId) {
                        comparaId = (ComparaId) ifx.e;
                        PrintIntermedio(indiceVerdadero, "SUB", ((Idx) comparaNum.e1).id1, ((Idx) comparaNum.e2).id1);
                        indiceVerdadero++;
                        PrintIntermedio(indiceVerdadero, "JZ", "("+ (indiceVerdadero-1) +")", "?");
                        indiceVerdadero++;
                    }

                }catch(Exception e){

                }
            }
            else if(programa.s.get(i) instanceof Printx){
                print = (Printx) programa.s.get(i);
                try{
                    if(print.e instanceof ComparaNum){
                        comparaNum = (ComparaNum) print.e;
                    }
                    if(print.e instanceof ComparaId){
                        comparaId = (ComparaId) print.e;
                    }

                }catch (Exception e){

                }
            }
        }
    }
}