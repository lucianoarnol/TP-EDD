package lineales.dinamicas;

public class Cola {
    private Nodo frente;
    private Nodo fin;
    
    
    public Cola(){
        this.frente = null;
        this.fin = null;
    }

    public boolean poner(Object objeto){
        Nodo nuevoNodo = new Nodo(objeto, null);
        if(this.frente == null){
            this.frente = nuevoNodo;
            this.fin = nuevoNodo;
            
        }
        else{
            this.fin.setEnlace(nuevoNodo);
            this.fin =this.fin.getEnlace();
        }


        return true; 
    
    }

    public boolean sacar(){
        boolean exito = true;
        if(this.frente == null){
            exito = false;
        }
        else{
            this.frente = this.frente.getEnlace();
            if(this.frente == null){
                this.fin = null;
            }

        }
        return exito;
    }

    public Object obtenerFrente(){
        Object elemenFrente = null;
        if (this.frente != null) {
            elemenFrente = this.frente.getElem();
        }

        return elemenFrente;
    }

    public boolean esVacia(){
        return this.frente == null;
    }
    
    public void vaciar(){
        this.fin = null;
        this.frente = null;
    }

    public Cola clone(){
        Cola colaClone = new Cola();
        Nodo aux = this.frente;
        if (aux != null) {
            colaClone.frente = new Nodo(aux.getElem(), null);
            aux = aux.getEnlace();
            Nodo aux2 = colaClone.frente;
            while (aux != null) {
                aux2.setEnlace(new Nodo(aux.getElem(), null));
                aux = aux.getEnlace();
                aux2 = aux2.getEnlace();
                   
            }
            if(aux== null){
                colaClone.fin = aux;
            }
             
        }
        return colaClone;
    }

    public String toString(){
        String s="";
        if(this.esVacia()){
            s = "[]";
        }
        else{
            Object objeto;
            Nodo aux = this.frente;
            s = "[";
            while (aux != null) {
                objeto = aux.getElem();
                s+= objeto;
                aux = aux.getEnlace();
                if(aux != null){
                    s += ",";
                }             
            }
            s+="]";
        }
        return s;
    }
}
