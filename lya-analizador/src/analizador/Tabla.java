package analizador;

import arbol_sintactico.Sx;

public class Tabla {
    int i;
    String signo;
    String id1;
    int e2;
    String e2s;
    Sx referencia;

    public Tabla(int i, String signo, String id1, String e2, Sx referencia) {

        this.i = i;
        this.signo = signo;
        this.id1 = id1;
        this.e2s = e2;
        this.referencia = referencia;

    }
    public int getI() {
        return i;
    }
    public void setI(int i) {
        this.i = i;
    }
    public String getSigno() {
        return signo;
    }
    public void setSigno(String signo) {
        this.signo = signo;
    }
    public String getId1() {
        return id1;
    }
    public void setId1(String id1) {
        this.id1 = id1;
    }
    public int getE2() {
        return e2;
    }
    public void setE2(int e2) {
        this.e2 = e2;
    }
    public String getE2s() {
        return e2s;
    }
    public void setE2s(String e2s) {
        this.e2s = e2s;
    }
}
