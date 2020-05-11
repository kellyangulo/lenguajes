package arbol_sintactico;

import java.util.ArrayList;

public class Beginx implements Sx {
    public ArrayList<Sx> sentences = new ArrayList<Sx>();
    public int ToLine=-1;
    public int order=0;

    public Beginx(ArrayList<Sx> sentences){
        this.sentences = sentences;
    }
}
