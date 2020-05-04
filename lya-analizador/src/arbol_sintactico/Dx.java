package arbol_sintactico;

import analizador.Token;

public class Dx {
    public String t; //tipo
    public String  id;
    public Numx valor;

    public void setValor(String valor){
        this.valor.num1 = valor;
    }



    //Constructor
    public Dx(String t, String id, Numx valor){
        this.t = t;
        this.id = id;
        this.valor = valor;
    }
}
