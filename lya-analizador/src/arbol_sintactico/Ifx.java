package arbol_sintactico;

public class Ifx implements Sx {
    public Ex e;
    public Sx s1;
    public Sx s2;

    public Ifx(Ex e, Sx s1, Sx s2){
        this.e = e;
        this.s1 = s1;
        this.s2 = s2;
    }
}
