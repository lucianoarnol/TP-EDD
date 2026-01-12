package jerarquicas;

public class NodoABB {
    private Comparable elem;
    private NodoABB izquierdo;
    private NodoABB derecho;

    public NodoABB(Comparable unElem, NodoABB NI, NodoABB ND ){
        elem = unElem;
        izquierdo = NI;
        derecho = ND;

    }

    public NodoABB(Comparable unELem){
        elem = unELem;
        izquierdo = null;
        derecho = null;
    }

    public Comparable getElem(){
        return elem;
    }

    public NodoABB getIzquierdo(){
        return izquierdo;
    }

    public NodoABB getDerecho(){
        return derecho;
    }

    public void setElem(Comparable unElem){
        elem = unElem;
    }

    public void setIzquierdo(NodoABB NI){
        izquierdo = NI;
    }

    public void setDerecho(NodoABB ND){
        derecho = ND;
    }
}
