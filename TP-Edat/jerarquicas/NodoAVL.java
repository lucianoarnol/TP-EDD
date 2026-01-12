package conjuntistas;

//Modificado como tabla de busqueda clave/valor
class NodoAVL {
    
    private Comparable clave;
    private Object valor;
    private NodoAVL izquierdo;
    private NodoAVL derecho;
    private int altura;
    
    //constructor
    public NodoAVL(Comparable clave, Object valor, NodoAVL enlaceizq, NodoAVL enlaceder) {
        this.clave = clave;
        this.valor = valor;
        this.izquierdo = enlaceizq;
        this.derecho = enlaceder;
        recalcularAltura();
    }
    
    //modificadores
    public void setElem(Comparable clave) {
        this.clave = clave;
    }

    public void setValor(Object valor) {
        this.valor = valor;
    }
    
    public void setIzquierdo(NodoAVL enlaceIzq) {
        this.izquierdo = enlaceIzq;
    }

    public void setDerecho(NodoAVL enlaceDer) {
        this.derecho = enlaceDer;
    }

    public void recalcularAltura() {
        if (!(izquierdo == null || derecho == null)) {
            if (izquierdo.getAltura() > derecho.getAltura()) {
                this.altura = 1 + izquierdo.getAltura();
            } else {
                this.altura = 1 + derecho.getAltura();
            }
        } else if (izquierdo != null) {
            this.altura = 1 + izquierdo.getAltura();
        } else if (derecho != null) {
            this.altura = 1 + derecho.getAltura();
        } else this.altura = 0;
    }
    
    //observadoras
    public Comparable getClave() {
        return clave;
    }

    public Object getValor() {
        return valor;
    }

    public int getAltura() {
        return altura;
    }
    
    public NodoAVL getIzquierdo() {
        return izquierdo;
    }
    
    public NodoAVL getDerecho() {
        return derecho;
    }

    public boolean esHoja(){
        return (this.izquierdo==null&&this.derecho==null);
    }

    public void dibujar(String espaciado) {
        int longi = this.valor.toString().length();
        if (!this.esHoja()) {
            if(this.getDerecho() != null && this.getIzquierdo() != null) {
                System.out.print(this.valor.toString() + sumarCaracter("----> ", longi+1, '-'));
                this.derecho.dibujar(espaciado + sumarCaracter(" |     ", longi*2, ' '));
                
                System.out.println("");
                System.out.print(espaciado +  sumarCaracter(" L----> ", longi*2, ' '));
                if (this.getIzquierdo().esHoja()) {
                    this.getIzquierdo().dibujar(espaciado + sumarCaracter(" |     ", longi*2, ' '));
                } else {
                    this.getIzquierdo().dibujar(espaciado + sumarCaracter("       ", longi*2, ' '));
                }
            } else if (this.getDerecho() != null) {
                System.out.print(this.valor.toString() + sumarCaracter("----> ", longi+1, '-'));
                this.getDerecho().dibujar(espaciado + sumarCaracter("        ", longi*2, ' '));
            } else {
                System.out.println(this.valor.toString());
                System.out.print(espaciado +  sumarCaracter(" L----> ", longi*2, ' '));
                if (this.getIzquierdo().esHoja()) {
                    this.getIzquierdo().dibujar(espaciado + sumarCaracter(" |     ", longi*2, ' '));
                } else {
                    this.getIzquierdo().dibujar(espaciado + sumarCaracter("        ", longi*2, ' '));
                }
            }
        } else {
            System.out.print(this.valor.toString());
        }
    }

    private String sumarCaracter(String text, int cant, char c) {
        for (int i = 0; i < cant; i++) {
            text = c + text;
            cant--;
        }
        return text;
    }
}