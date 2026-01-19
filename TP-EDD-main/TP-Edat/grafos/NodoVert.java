package grafos;

public class NodoVert {
    
    //atributos
    Object elem;
    NodoVert sigVertice;
    NodoAdy primerAdy;


    public NodoVert(Object elemento, NodoVert sigvert, NodoAdy primerAd){
        this.elem = elemento;
        this.sigVertice = sigvert;
        this.primerAdy = primerAd;
    }

    //getters y setters
    public Object getElem() {
        return elem;
    }
    public void setElem(String elem) {
        this.elem = elem;
    }
    public NodoVert getSigVertice() {
        return sigVertice;
    }
    public void setSigVertice(NodoVert sigVertice) {
        this.sigVertice = sigVertice;
    }
    public NodoAdy getPrimerAdy() {
        return primerAdy;
    }
    public void setPrimerAdy(NodoAdy primerAdy) {
        this.primerAdy = primerAdy;
    }

    
}
