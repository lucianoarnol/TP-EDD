package lineales.estaticas;

public class Pila {
    private Object[] arreglo;
    private int tope;
    private static final int TAMANIO = 20;

    public Pila(){
        this.arreglo =new Object[TAMANIO];
        this.tope = -1;
    }

    public boolean apilar(Object nuevoElem){
        boolean exito;
        if(this.tope+1>=TAMANIO){
            exito=false;
        }
        else{
            this.tope++;
            this.arreglo[tope] = nuevoElem;
            exito=true;
        }

        return exito;
    }

    public boolean desapilar(){
        boolean exito;
        if(this.tope<=-1){
            exito=false;
        }
        else{
            this.arreglo[tope] = null;
            this.tope--;
            exito=true;
        }

        return exito;
    }

    public Object obtenerTope(){
        Object elemenTope;
        if(this.tope==-1){
            elemenTope = null;
        }
        else{
            elemenTope = this.arreglo[this.tope];
        }

        return elemenTope;
    }

    public boolean esVacia(){
        return this.tope==-1;
    }

    public void vaciar(){
        while (this.tope>-1){
            this.arreglo[this.tope]=null;
            this.tope--;
        }
    }

    public Pila clone(){
        Pila pilaCopia = new Pila();
        Object objeto;
        int i=this.tope;
        while (i>-1) {
            objeto = this.arreglo[i];
            pilaCopia.arreglo[i] = objeto;
            i--;
        }
        pilaCopia.tope=this.tope;
        return pilaCopia;
    }

    
    public String toString(){
        String s= "";
        if(this.tope == -1){
            s = "[]";
        }
        else{
            s = "[";
            for(int i=this.tope; i>=0; i--){
                s += this.arreglo[i];
                if ((i!= 0)) {
                    s+=",";
                }
            }
            s+="]";
        }
        return s;
    }
}
