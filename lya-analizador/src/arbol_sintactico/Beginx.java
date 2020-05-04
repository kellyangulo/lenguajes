package arbol_sintactico;

import java.util.ArrayList;

public class Beginx implements Sx {
    public ArrayList<Sx> sentences = new ArrayList<Sx>();

    public Beginx(ArrayList<Sx> sentences){
        this.sentences = sentences;
    }
}
