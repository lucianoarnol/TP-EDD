package lineales.dinamicas;
public class Lista {
    private Nodo cabecera;
    
    public Lista(){
        this.cabecera = null;
    }

    public boolean insertar(Object Elemento, int pos){
        boolean exito = true;
        if (pos<1 || pos>this.longitud() +1) {
            exito = false;  
        }
        else{
            if (pos == 1) {
                this.cabecera = new Nodo(Elemento, this.cabecera);
            }
            else{
                Nodo aux = this.cabecera;
                int i = 1;
                while (i<pos-1) {
                    aux = aux.getEnlace();
                    i++;
                    
                }
                Nodo nuevo = new Nodo(Elemento, aux.getEnlace());
                aux.setEnlace(nuevo);
            }
        }
        return exito;
    }

    public boolean eliminar(int pos){
        boolean exito = true;
        if (pos<1 || pos>this.longitud()) {
            exito = false;  
        }
        else{
            if (pos == 1) {
                this.cabecera = this.cabecera.getEnlace();
            }
            else{
                Nodo aux = this.cabecera;
                int i = 1;
                while (i<pos-1) {
                    aux = aux.getEnlace();
                    i++;
                    
                }
                
                aux.setEnlace(aux.getEnlace().getEnlace());
            }
        }
        return exito;
    }

    public Object recuperar(int pos){
        Object elem;
        if (this.cabecera == null || pos<1 || pos>this.longitud()) { //en caso de que la lista sea vacia devuelve tambien el -1
            elem = null;                                               //consultar lo de pos>this.longitud (en todos menos en insertar?)
        }
        else{
            Nodo aux = this.cabecera;
            int i = 0;
            while (i<pos-1) { //esta bien asi?, por que no se utilizar (i!= pos-1)??
                aux = aux.getEnlace();
                i++;    
            }
            elem = aux.getElem();
        }
        return elem;       
    }

    public int localizar(Object elemento){
        int pos = -1;
        Nodo aux = this.cabecera;
        int i=1;
        while (aux != null && !(aux.getElem().equals(elemento))) {
            aux = aux.getEnlace();
            i++;
            
        }
        if (aux != null && aux.getElem().equals(elemento)) {
            pos = i;
            
        }
        return pos;

    }

    public int longitud(){
        int longitud = 0;
        Nodo aux = this.cabecera;
        while (aux != null) {
            aux = aux.getEnlace();
            longitud++;
        }
        return longitud;
    }

    public boolean esVacia(){
        return this.cabecera == null;
    }

    public void vaciar(){
        this.cabecera = null;
    }

    public Lista clone(){
        Lista listaClone = new Lista();
        Nodo aux = this.cabecera;
        if (aux != null) {
            listaClone.cabecera = new Nodo(aux.getElem(), null);
            aux = aux.getEnlace();
            Nodo aux2 = listaClone.cabecera;
            while (aux != null) {
                aux2.setEnlace(new Nodo(aux.getElem(), null));
                aux = aux.getEnlace();
                aux2 = aux2.getEnlace();
            }  
        }
        return listaClone;
    }

    public String toString(){
        String s="";
        if(this.esVacia()){
            s = "[]";
        }
        else{
            Object objeto;
            Nodo aux = this.cabecera;
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

    public void invertir(){
        Nodo aux = this.cabecera;
        Nodo auxFin = null;
        int t=0;
        if (this.cabecera != null) {
            int i=0;
            while (aux!= null) {
                Nodo aux2 = this.cabecera;
                if (!(aux == this.cabecera) && aux.getEnlace() == null ){
                    int k=0;
                    while(k<i){ 
                        if (i-1==k) {
                            aux.setEnlace(aux2);
                            t++;
                            if (t==1) { //buscar una mejor manera para q esto ande
                                auxFin = aux;
                            }
                            aux = aux.getEnlace();
                            aux2.setEnlace(null);
                            i--; 
                        }
                        else{
                            aux2 = aux2.getEnlace();
                            k++;
                        }
                    } 
                }
                else{
                    aux = aux.getEnlace();
                    i++;
                }
                
            }
        this.cabecera = auxFin;
        }

    }


    public void eliminarAparicion(Object elemento){  //rehacer
        while(this.cabecera != null && this.cabecera.getElem().equals(elemento)){
            this.cabecera = this.cabecera.getEnlace();     
        }
        Nodo aux = this.cabecera;
        while (aux.getEnlace() != null ) {
            if (aux.getEnlace().getElem().equals(elemento)) {
                aux.setEnlace(aux.getEnlace().getEnlace());
            }
            else{ //este else funciona para que saltee cuando no sea igual al elemento 
                aux = aux.getEnlace();
            }    
        } 
    }

    
    public Lista obtenerMultiplos(int num){
        Lista lisNum = new Lista();
        Nodo aux = this.cabecera;
        Nodo aux2 = null;
        int contador = 1;
        while (aux != null) {
            if(contador == num){
                lisNum.cabecera = new Nodo(aux.getElem(), null);
                aux2 = lisNum.cabecera;
            }
            else if (contador != num && (contador%num)==0) {
                aux2.setEnlace(new Nodo(aux.getElem(), null));
                aux2 = aux2.getEnlace();
            }
            contador++;
            aux = aux.getEnlace();
        }
        return lisNum;
    }


    //ejercicio de parcial 2
    public void cambiarPosicion(int pos1, int pos2){
        boolean activador = false;
        int i = 1;
        Nodo aux = cabecera;
        Object auxElem = null;
        while (cabecera != null && i<=pos2 && aux.getEnlace() != null) {
            if (pos1 == i ) {
                auxElem = aux.getElem();   
                aux.setElem(aux.getEnlace().getElem()); 
                activador = true;
                }
            else if(pos2 == i && activador){
                aux.setElem(auxElem);           
            }
            else if(activador){
                aux.setElem(aux.getEnlace().getElem()); 
            }
            aux = aux.getEnlace();
            i++; 
        }
    }

    //hacerlo moviendo los nodos
    public void cambiarPosicion2(int pos1, int pos2){
        boolean activador = false;
        int i = 1;
        Nodo aux = cabecera;
        Nodo auxPos1 = null;
        Nodo auxPos2 = null;
        while (cabecera != null && i<=pos2 && pos1 < pos2) {
            if (pos1-1 == i) {
                auxPos1 = new Nodo(aux.getEnlace().getElem(), null);
                aux.setEnlace(aux.getEnlace().getEnlace()); 
                activador = true;
                }
            else if (pos1 == 1){
                auxPos1 = new Nodo(this.cabecera.getEnlace().getElem(), null);
                this.cabecera = this.cabecera.getEnlace();
                activador = true;

            }
            else if(pos2-1 == i && activador){
                auxPos2 = new Nodo(aux.getEnlace().getElem(), null);
                aux.setEnlace(auxPos1);   
                aux = aux.getEnlace();
                aux.setEnlace(auxPos2);        
            }
            aux = aux.getEnlace();        
            i++; 
        }
    }

    //ejercicio de parcial
    public boolean insertarAntes(Object elem1, Object elem2){
        Nodo aux = this.cabecera;
        boolean verif = false;
        if (this.cabecera != null && elem2.equals(this.cabecera.getElem())) {
            Nodo nuevaCabecera = new Nodo(elem1, this.cabecera);
            this.cabecera = nuevaCabecera;
        }
        while (aux != null) {
            if (aux.getEnlace() != null && elem2.equals(aux.getEnlace().getElem())) {
                Nodo hijos = aux.getEnlace();
                Nodo auxElem1 = new Nodo(elem1, null);
                aux.setEnlace(auxElem1);
                aux = aux.getEnlace();
                aux.setEnlace(hijos);
                verif = true;
            }
            aux = aux.getEnlace();
            
        }

        return verif;
    }

}
