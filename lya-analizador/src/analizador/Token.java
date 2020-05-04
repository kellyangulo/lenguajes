package analizador;

public class Token {

    //Propiedades
    public Tipos getTipo(){
        return tipo;
    }
    public void setTipo(Tipos tipo){
        this.tipo = tipo;
    }
    public  String getValor(){
        return valor;
    }
    public void setValor(String valor) {
        this.valor = valor;
    }

    private Tipos tipo;
    private String valor;

    //Clasificación de los valores que entrarán
    public enum Tipos {
        //Palabras reservadas del lenguaje
        IF ("^if$"),
        THEN("^then$"),
        ELSE("^else$"),
        BEGIN("^begin$"),
        END("^end$"),
        PRINT("^print$"),
        EQU("^=$"),
        semi("^;$"), //Semicolon
        INT("^int$"),
        FLOAT("^float$"),
        num("^[+]?([0-9]+([.][0-9]*)?|[.][0-9])+$"), //Acepta números enteros y decimales
        id("^[a-zA-Z0-9]+$");


        public final String patron;

        Tipos(String s) {
            this.patron = s;
        }
    }

}
