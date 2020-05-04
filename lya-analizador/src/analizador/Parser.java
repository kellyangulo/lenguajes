package analizador;

import java.util.ArrayList;

import analizador.Token.Tipos;
import arbol_sintactico.*;

public class Parser {

    ArrayList<Token> tokens;
    int tokenActual;
    Token tok;
    boolean error;
    public static int contandor = 0;
    Programa p;
    public static int contadorSaveLineDo = 0;

    public Parser(ArrayList<Token> tokens){
        this.tokens = tokens;
        this.tokenActual = 0;
        this.tok = tokens.get(this.tokenActual);

    }

    public void advance(){
        if (this.tokens.size()-1 > this.tokenActual){
            this.tokenActual ++;
            this.tok = this.tokens.get(this.tokenActual);
        }else{
            this.tokenActual = -1;
        }
    }

    public void eat(Tipos t){
        if(this.tok.getTipo() == t)
            advance();
        else
            error();
    }

    ArrayList<Dx> dec = new ArrayList<Dx>();
    ArrayList<Sx> stat = new ArrayList<Sx>();

    public Programa P(){
        while(this.tok.getTipo() == Tipos.INT || this.tok.getTipo() == Tipos.FLOAT){
            dec.add(D()); // Se guardan las declaraciones :(
        }
        while(this.tok.getTipo() == Tipos.IF || this.tok.getTipo() == Tipos.BEGIN || this.tok.getTipo() == Tipos.PRINT){
            stat.add(S()); // Se guardan los estatus xd
        }
        this.p = new Programa(dec,stat);
        return this.p;

    }


    public Dx D(){
        Dx d;
        Numx n;
        if (tokenActual == -1){
            return null;
        }
        switch (this.tok.getTipo()){
            case INT:
                eat(Tipos.INT);
                d = new Dx("int", this.tok.getValor(), new Numx("0"));
                eat(Tipos.id);
                eat(Tipos.EQU);
                d.setValor(this.tok.getValor());
                eat(Tipos.num);
                eat(Tipos.semi);
                //D();
                return d;

            case FLOAT:
                eat(Tipos.FLOAT);
                d = new Dx("float", this.tok.getValor(), new Numx("0"));
                eat(Tipos.id);
                eat(Tipos.EQU);
                d.setValor(this.tok.getValor());
                eat(Tipos.num);
                eat(Tipos.semi);
                //D();
                return  d;
            default:
                errorNumeros();

        }

        return null;
    }

    Ex e, e2;
    Sx s1, s2;

    public Sx S(){
        if (tokenActual == -1){
            return null;
        }

        switch (this.tok.getTipo()){
            case IF:
                eat(Tipos.IF);
                e = E();
                eat(Tipos.THEN);
                s1 = S();
                eat(Tipos.ELSE);
                s2 = S();
                return new Ifx(e, s1, s2);
            case BEGIN:
                eat(Tipos.BEGIN);
                S();
                L();
                return null;
            case PRINT:
                eat(Tipos.PRINT);
                e2 = E();
                return new Printx(e2);
            default:
                error();
        }
        return  null;
    }

    public void L(){
        if (tokenActual == -1){
            return;
        }
        switch (this.tok.getTipo()){
            case END:
                eat(Tipos.END);
                break;
            case semi:
                eat(Tipos.semi);
                S();
                L();
                break;
            default:
                error();
        }
    }
    Numx n1, n2;
    Idx i1, i2;
    public Ex E(){
        Ex e = null;
        if (tokenActual == -1){
            return null;
        }
        switch (this.tok.getTipo()){
            case num:
                n1 = new Numx (this.tok.getValor());
                eat(Tipos.num);
                eat(Tipos.EQU);
                n2 = new Numx(this.tok.getValor());
                eat(Tipos.num);
                e = new ComparaNum(n1, n2);
                break;
            case id:
                i1 = new Idx(this.tok.getValor());
                eat(Tipos.id);
                eat(Tipos.EQU);
                i2 = new Idx(this.tok.getValor());
                eat(Tipos.id);
                e = new ComparaId(i1, i2);
                break;
            default:
                error();
        }
        return e;
    }

    public void error(){
//        System.out.println("Error");
//        System.out.println(this.tok.getTipo());
//        System.out.println(this.tokenActual);
        error = true;
        throw new RuntimeException("Cadena invalida");

    }

    public void errorNumeros(){
        error = true;
        throw new RuntimeException("Error en los identificadores");
    }
}
