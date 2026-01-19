package lineales.estaticas;

public class Cola {
    private Object[] arreglo;
    private int frente;
    private int fin;
    private static final int TAMANIO = 10;
    
    public Cola(){
        this.arreglo = new Object[TAMANIO];
        this.frente = 0;
        this.fin = 0;
    }

    public boolean poner(Object objeto){
        boolean exito = true;
        if ((this.fin+1)%TAMANIO != this.frente) {
            this.arreglo[this.fin] = objeto;
            this.fin = (this.fin +1)%TAMANIO; 
        }
        return exito;
    }

    public boolean sacar(){
        boolean exito = true;
        if (this.esVacia()) {
            exito = false;    
        }
        else{
            this.arreglo[this.frente]=null;
            this.frente = (this.frente + 1)%TAMANIO;
        }
        return exito;
    }

    public Object obtenerFrente(){
        Object elemenFrente = null;
        if(this.frente!=this.fin){
            elemenFrente = this.arreglo[this.frente];
        }

        return elemenFrente;
    }

    public boolean esVacia(){
        return this.frente == this.fin;
    }
    
    public void vaciar(){
        while (!this.esVacia()) {
            this.arreglo[this.frente]=null;
            this.frente = (this.frente + 1)%TAMANIO;
        }
    }

    public Cola clone(){
        Cola colaClone = new Cola();
        Object objeto;
        int aux = this.frente;
        while (this.fin != aux){
            objeto = this.arreglo[aux];
            colaClone.arreglo[aux] = objeto;
            colaClone.fin = (colaClone.fin +1)%TAMANIO;             
            aux = (aux +1)%TAMANIO; 
        }
        return colaClone;
    }

    public String toString(){
        String s="";
        if(this.esVacia()){
            s = "[]";
        }
        else{
            Object objecto;
            int aux = this.frente;
            s = "[";
            while (this.arreglo[aux] != null) {
                objecto = this.arreglo[aux];
               
                s+= objecto;
                aux = (aux + 1)%TAMANIO;
                if(this.arreglo[aux] != null){
                    s += ",";
                }
                
            }
            s+="]";
        }

        return s;
    }
}
