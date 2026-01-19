package jerarquicas;
import lineales.dinamicas.Lista;


public class ArbolBin {
    private NodoArbol raiz;
    

    public ArbolBin(){
        this.raiz =null;

    }

    public boolean insertar(Object elemNuevo, Object elemPadre, char lugar){
        boolean exito = true;
        if(this.raiz == null){
            this.raiz = new NodoArbol(elemNuevo, null, null);
        }
        else{
            NodoArbol nPadre = obtenerNodo(this.raiz, elemPadre);
            if (nPadre != null) {
                if (lugar == 'I' && nPadre.getIzquierdo() == null) {
                    nPadre.setIzquierdo(new NodoArbol(elemNuevo, null, null));
                }
                else if(lugar == 'D' && nPadre.getDerecho() == null){
                    nPadre.setDerecho(new NodoArbol(elemNuevo, null, null));
                }
                else{
                    exito = false;
                }
            }
            else{
                exito =false;
            }
        }
        return exito;
    }
    

    private NodoArbol obtenerNodo(NodoArbol n, Object buscado){
        NodoArbol resultado = null;
        if(n != null){ 
            if (n.getElem().equals(buscado)) {
                resultado = n;
                
            }
            else{
                resultado = obtenerNodo(n.getIzquierdo(), buscado);
                if (resultado == null) {
                    resultado = obtenerNodo(n.getDerecho(), buscado);
                    
                }
            }
        }
        return resultado;
    }

    public boolean insertarPorPosicion(Object elem, int posPadre, char posHijo){ //rehacer
        boolean exito = true;
        if(this.raiz == null){
            this.raiz = new NodoArbol(elem, null, null);
        }
        else{
            int[] posactual = {1};
            NodoArbol nPadre = encontrarPadre(this.raiz, posPadre, posactual);
            if (nPadre != null) {
                if (posHijo == 'I' && nPadre.getIzquierdo() == null) {
                    nPadre.setIzquierdo(new NodoArbol(elem, null, null));
                }
                else if(posHijo == 'D' && nPadre.getDerecho() == null){
                    nPadre.setDerecho(new NodoArbol(elem, null, null));
                }
                else{
                    exito = false;
                }
            }
            else{
                exito =false;
            }
        }
        return exito;
    }

    private NodoArbol encontrarPadre(NodoArbol aux, int padre, int[] posactual){ //tuki
        NodoArbol nPadre = null;
        if(aux != null){ 
            
            if (padre == posactual[0]) {
                nPadre = aux;
                
            }
            else{
                posactual[0]++;
                nPadre = encontrarPadre(aux.getIzquierdo(), padre, posactual);
                if (nPadre == null) {
                    nPadre = encontrarPadre(aux.getDerecho(), padre,posactual);
                    
                }
            }
        }
        return nPadre;
    }

    public boolean esVacia(){
        return this.raiz == null;
    }

    public Object padre(Object elem){
        Object padre = buscaPadre(this.raiz, elem);
        return padre;
    }

    private Object buscaPadre(NodoArbol nodo, Object elem){
        Object padre = null;
        if(nodo != null && padre == null){
            if (nodo.getIzquierdo() != null && nodo.getIzquierdo().getElem().equals(elem)) {
                padre = nodo.getElem();
            }
            else if (nodo.getDerecho() != null && nodo.getDerecho().getElem().equals(elem)){
                padre = nodo.getElem();
            }
            else{
                padre = buscaPadre(nodo.getIzquierdo(), elem);
                padre = buscaPadre(nodo.getDerecho(), elem);
            }
        }
        return padre;
    }

    public int altura(){
        NodoArbol nodoAux = this.raiz;
        int altura = recorreAltura(nodoAux, 0, 0);
        return altura;
    }

    private int recorreAltura(NodoArbol nodo, int k, int max){
        int altura = max;
        if(nodo != null){ 
            altura = recorreAltura(nodo.getIzquierdo(), k+1, altura);
            altura = recorreAltura(nodo.getDerecho(), k+1, altura);  
        }
        else if(k>max){
            altura = k-1;
        }
        

        return altura;

    }

    public int nivel(Object elem){     
        NodoArbol aux = this.raiz;
        int nivel = recorrerNivel(elem, aux, 0, -1);
        return nivel;
    }

    private int recorrerNivel(Object elem,NodoArbol nodo, int k, int n){ 
        int nivel=n;
        if(nodo != null && nodo.getElem().equals(elem) && nivel == -1){
            nivel = k;
            
        }
        else if (nodo != null && !nodo.getElem().equals(elem)) {
            nivel = recorrerNivel(elem, nodo.getIzquierdo(), k+1, nivel);
            nivel = recorrerNivel(elem, nodo.getDerecho(), k+1, nivel); 
        }
        
        return nivel;
    }

    public void vaciar(){
        this.raiz = null;
    }

    public ArbolBin clonar(){
        ArbolBin arbolClone = new ArbolBin();
        NodoArbol auxRaiz = this.raiz;
        if (!esVacia()) {
            arbolClone.raiz = ArbolBinRecursivo(auxRaiz);
        }
        return arbolClone;
    }

    private NodoArbol ArbolBinRecursivo(NodoArbol raiz){
        NodoArbol auxArbol = null;
        if (raiz != null) {
            auxArbol = new NodoArbol(raiz.getElem(), null, null);
            auxArbol.setIzquierdo(ArbolBinRecursivo(raiz.getIzquierdo()));
            auxArbol.setDerecho(ArbolBinRecursivo(raiz.getDerecho()));
        }

        return auxArbol;
    }

    public String toString(){   
        return ("PreOrden:" + this.listarPreOrden()  + // hacer salto de linea
                " | InOrden:"+ this.listarInOrden() +//
                " | PosOrden:" + this.listarPosOrden()); //+ 
                //" | PorNivel:" + this.listarPorNiveles());
    }

    public Lista listarPreOrden(){
        Lista lis = new Lista();
        listarPreOrdenAux(this.raiz, lis);
        return lis;
    }

    public void listarPreOrdenAux(NodoArbol nodo, Lista lis){
        if (nodo != null) {
            lis.insertar(nodo.getElem(), lis.longitud()+1);
            listarPreOrdenAux(nodo.getIzquierdo(), lis);
            listarPreOrdenAux(nodo.getDerecho(), lis);
            
        }
    }

    public Lista listarPosOrden(){
        Lista lis = new Lista();
        listarPosOrdenAux(this.raiz, lis);
        return lis;
    }

    public void listarPosOrdenAux(NodoArbol nodo, Lista lis){
        if (nodo != null) {
            
            listarPosOrdenAux(nodo.getIzquierdo(), lis);
            listarPosOrdenAux(nodo.getDerecho(), lis);
            lis.insertar(nodo.getElem(), lis.longitud()+1);
        }
    }

    public Lista listarInOrden(){
        Lista lis = new Lista();
        listarInOrdenAux(this.raiz, lis);
        return lis;
    }

    public void listarInOrdenAux(NodoArbol nodo, Lista lis){
        if (nodo != null) {
            
            listarInOrdenAux(nodo.getIzquierdo(), lis);
            lis.insertar(nodo.getElem(), lis.longitud()+1);
            listarInOrdenAux(nodo.getDerecho(), lis);
            
        }
    }

    public Lista listarPorNiveles(){
        Lista lis = new Lista();
        listarPorNivelesAux(this.raiz, lis, 0);
        return lis;
    }

    public void listarPorNivelesAux(NodoArbol nodo, Lista lis, int niv){ //deberia de devoler [1,2,5,3,4,6,2]
        if (nodo != null) {
            if (niv == nivel(nodo.getElem())) {
                lis.insertar(nodo.getElem(), lis.longitud()+1);
                
            }
            listarPorNivelesAux(nodo.getIzquierdo(), lis, niv+1);
            listarPorNivelesAux(nodo.getDerecho(), lis, niv+1);
            
            
        }
    }
    //fronte izquierda - derecha
    /*public Lista frontera(){
        Lista hojas = new Lista();
        listarHojas(this.raiz, hojas, null);
        return hojas;
    }
    public void listarHojas(NodoArbol nodo, Lista hojas, NodoArbol padre){
        if (nodo != null) {
            listarHojas(nodo.getIzquierdo(), hojas, nodo);
            listarHojas(nodo.getDerecho(), hojas, nodo);
            if (nodo.getDerecho()==null && nodo.getIzquierdo() == null) {
                hojas.insertar(nodo.getElem(), hojas.longitud()+1);
                
            }
        }
        

    }*/

    public Lista obtemerAncestros(Object elem){
        Lista ancestros = new Lista();
        listarAncestros(this.raiz, ancestros, elem, null);
        return ancestros;
    }

    public void listarAncestros(NodoArbol nodo, Lista ancestros, Object elem, NodoArbol padre){ //modificar con el codigo de agus
        if(nodo != null){
            listarAncestros(nodo.getIzquierdo(), ancestros, elem, nodo);
            listarAncestros(nodo.getDerecho(), ancestros, elem, nodo);
            if (nodo.getElem().equals(elem)) {
                ancestros.insertar(padre.getElem(), ancestros.longitud()+1);
            }

        }
    
    }

    public Lista obtenerDescendientes(Object elem){
        Lista descendientes = new Lista();
        listarDescendientes(this.raiz, descendientes, elem, false);
        return descendientes;
    }

    private void listarDescendientes(NodoArbol nodo, Lista descendientes, Object elem, boolean exito){ //cambiarlo a boolean
       if (nodo != null) {
         if(!nodo.getElem().equals(elem) && exito == false){
             listarDescendientes(nodo.getIzquierdo(), descendientes, elem, exito);
             listarDescendientes(nodo.getDerecho(), descendientes, elem, exito);
         }
         else if ((elem.equals(nodo.getElem())) || exito == true) { //funciona pero si hay elementos iguales se muestran todos sus hijos
                                                                    //deberia de mostar tambien al padre? o solamente a los hijos?
             descendientes.insertar(nodo.getElem(), descendientes.longitud()+1);
             listarDescendientes(nodo.getIzquierdo(), descendientes, elem, true);
             listarDescendientes(nodo.getDerecho(), descendientes, elem, true);
             
             
         }
       }
    }

    //e
    /*public boolean verificarPatron(Lista patron){
        NodoArbol aux = this.raiz;
        boolean exito = recursivoPatron(patron, aux);
        return exito;
    }

    

    private boolean recursivoPatron(Lista patron, NodoArbol aux){
        boolean exito = true;
        if (aux != null && !patron.esVacia() && (aux.getElem() != patron.recuperar(1))) {
            exito = false;
        }
        else if (aux != null && !patron.esVacia() && (aux.getElem() == patron.recuperar(1))) {
            patron.eliminar(1);
            exito = recursivoPatron(patron, aux.getIzquierdo());
            exito = recursivoPatron(patron, aux.getDerecho());
        }
       
        return exito;
    }*/
    //g
    public ArbolBin clonarInvertido(){
        ArbolBin arbolClonInv = new ArbolBin();
        NodoArbol aux = this.raiz;
        if (this.raiz !=null) {
            arbolClonInv.raiz = recursivoClonInv(aux);
        }
        return arbolClonInv;
    }

    private NodoArbol recursivoClonInv(NodoArbol aux){
        NodoArbol clonRaiz = null;
        if (aux != null) {
            clonRaiz = new NodoArbol(aux.getElem(), null, null);
            clonRaiz.setDerecho(recursivoClonInv(aux.getIzquierdo()));
            clonRaiz.setIzquierdo(recursivoClonInv(aux.getDerecho()));
            
        }
        return clonRaiz;
    }



    //ejercicio parcial 3
    public boolean menosDeCantAparaiciones(Object elem, int cant){
        NodoArbol aux = this.raiz;
        boolean exito= true;
        int cantAparicion = recursivoCantApariciones(elem, cant, aux, 0);
        if (cantAparicion >= cant) {
            exito = false;
        }
        return exito;

    }

    private int recursivoCantApariciones(Object elem, int cant, NodoArbol aux, int k){
        int i = k;
        if (aux != null && !(cant <= i)) {
            if (aux.getElem().equals(elem)) {
                i++;
            }
            i = recursivoCantApariciones(elem, cant, aux.getIzquierdo(), i);
            i = recursivoCantApariciones(elem, cant, aux.getDerecho(), i);
            
        } 
        
        
        return i;
    }

    //parcial eje 2
    public boolean estaRepetido(Object elem){
        boolean verif = false;
        NodoArbol aux = this.raiz;
        int[] cont = {0};
        if (this.raiz != null) {
            verif = estaRepetidoRec(elem, aux, cont);
        }

        return verif;
    }

    private boolean estaRepetidoRec(Object elem, NodoArbol aux, int[] cont){
        boolean verif = false;
        if (aux != null && cont[0] < 2) {
            if (aux.getElem().equals(elem)) {
                cont[0]++;
            }
            else if (cont[0] < 2) {
                verif = estaRepetidoRec(elem, aux.getIzquierdo(), cont);
                verif = estaRepetidoRec(elem, aux.getDerecho(), cont);
            }
        }
        if(cont[0] >= 2){
            verif = true;
        }

        return verif;
    }

    //frontera derecha - izquierda
    public Lista frontera(){
        NodoArbol aux = this.raiz;
        Lista lista = new Lista();
        if (this.raiz != null) {
            fronteraRec(lista, aux);
        }

        return lista;

    }

    private void fronteraRec(Lista lis, NodoArbol nodo){
        if (nodo != null) {
            if (nodo.getIzquierdo() == null && nodo.getDerecho()== null) {
                lis.insertar(nodo.getElem(), 1);
            }
            else{
                fronteraRec(lis, nodo.getIzquierdo());
                fronteraRec(lis, nodo.getDerecho());
            }
        }
    }

    public boolean verificarPatron(Lista patron){
        NodoArbol aux = this.raiz;
        boolean verif = false;
        
        if (this.raiz != null) {
            verif = verificacion(patron, aux, 1, patron.longitud());
        }
        return verif;

    }

    private boolean verificacion(Lista patron, NodoArbol nodo, int cont, int longit){
        boolean verif = false;
        
        if (nodo != null) {
            if (cont <= longit && nodo.getElem().equals(patron.recuperar(cont))) {
                cont++;
                verif = verificacion(patron, nodo.getIzquierdo(), cont, longit);
                if (!verif) {
                    verif = verificacion(patron, nodo.getDerecho(), cont, longit);
                }
                
            }
            if (cont > longit && nodo.getDerecho() == null && nodo.getIzquierdo() == null) {
                verif = true;
            }

            
        }
        

        return verif;

    }


    //patron ejercicio nico
    public boolean verifPatron(Lista patron){
        NodoArbol aux = this.raiz;
        boolean verif = false;
        if (this.raiz != null) {
            verif = verificacion2(patron, aux);
        }
        return verif;
    }

    private boolean verificacion2(Lista patron, NodoArbol nodo){
        boolean verif = false;
        if (nodo!= null) {
            if(patron.recuperar(1) != null){
                if ((char) patron.recuperar(1) == 'I' && nodo.getIzquierdo() != null) {
                patron.eliminar(1);
                verif = verificacion2(patron, nodo.getIzquierdo());
                }
                else if ((char) patron.recuperar(1) == 'D' && nodo.getDerecho() != null) {
                    patron.eliminar(1);
                    verif = verificacion2(patron, nodo.getDerecho());
                }
            }
            else if(patron.recuperar(1) == null && nodo.getDerecho()== null && nodo.getIzquierdo() == null){
                verif = true;
            }
            
        }
        

        return verif;

    }

    //ejercicio recupperatorio
    public boolean equals(ArbolBin otro){
        boolean verif = false;
        NodoArbol nodo = this.raiz;
        if (this.raiz != null && otro.raiz != null) {
            verif = equalsRec(otro.raiz, nodo);
        }
        return verif;

    }

    private boolean equalsRec(NodoArbol otroNodo, NodoArbol nodo){
        boolean verif = true;
        if (nodo != null )  {
            if (otroNodo != null &&  nodo.getElem().equals(otroNodo.getElem())) {
                verif = equalsRec(otroNodo.getIzquierdo(), nodo.getIzquierdo());
                if (verif) {
                    verif = equalsRec(otroNodo.getDerecho(), nodo.getDerecho());
                }      
            }
            else{
                verif = false;
            }
        }
        return verif;
    }




}
