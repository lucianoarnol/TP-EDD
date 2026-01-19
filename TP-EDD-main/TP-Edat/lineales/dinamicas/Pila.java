package lineales.dinamicas;

public class Pila {
    private Nodo tope;

    public Pila(){
        this.tope = null;
    }

    public boolean apilar(Object nuevoObject){
        Nodo nuevo = new Nodo(nuevoObject, this.tope);
        this.tope = nuevo;
        return true;
    }

    public boolean desapilar(){
        boolean exito = false;
        if (this.tope != null) {
            this.tope=this.tope.getEnlace();
            exito=true;
        }
        return exito;
    }

    public Object obtenerTope(){
        Object elementoTope=null;
        if (this.tope != null) {
            elementoTope = this.tope.getElem();
        }
        return elementoTope;
    }

    public boolean esVacia(){
        return this.tope == null;
    }

    public void vaciar(){
        this.tope = null;
    }

    public Pila clone(){
        Pila pilaClone = new Pila();
        if (this.tope != null) {
            pilaClone.tope = cloneRecursivo(this.tope);
        }
        
        return pilaClone;

    }

    public Nodo cloneRecursivo(Nodo tope){
        Nodo auxTope = new Nodo(tope.getElem(), null);
        if (tope.getEnlace() != null) {
            auxTope.setEnlace(cloneRecursivo(tope.getEnlace()));
        }
        return auxTope;
    }

    public String toString(){
        String s= "";
        if(this.tope == null){
            s = "[]";
        }
        else{
            Nodo aux= this.tope;
            s = "[";
            while (aux != null) {
                s+= aux.getElem().toString();
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
