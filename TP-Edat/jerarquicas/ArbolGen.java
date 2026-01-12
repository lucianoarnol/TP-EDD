package jerarquicas;

import lineales.dinamicas.Cola;
import lineales.dinamicas.Lista;


public class ArbolGen {
    private NodoGen raiz;
    

    public ArbolGen(){
        this.raiz =null;

    }

    public boolean insertar(Object elemNuevo, Object elemPadre){
        boolean exito = true;
        if(this.raiz == null){
            this.raiz = new NodoGen(elemNuevo, null, null);
        }
        else{
            NodoGen nPadre = obtenerNodo(this.raiz, elemPadre);
            if (nPadre != null) {
                if (nPadre.getHijoIzquierdo() != null) {
                    nPadre = nPadre.getHijoIzquierdo();
                    while (nPadre.getHermanoDerecho() != null) {
                        nPadre = nPadre.getHermanoDerecho();
                    } 
                    nPadre.setHermanoDerecho(new NodoGen(elemNuevo, null, null));
                }
                else{
                    nPadre.setHijoIzquierdo(new NodoGen(elemNuevo, null, null));
                }
                
                
            }
            else{
                exito =false;
            }
        }
        return exito;
    }
    

    private NodoGen obtenerNodo(NodoGen n, Object buscado){
        NodoGen resultado = null;
        if(n != null){ 
            if (n.getElem().equals(buscado)) {
                resultado = n;
                
                
            }
            else{
                NodoGen hijo = n.getHijoIzquierdo();
                while (hijo != null && resultado == null) {
                    resultado = obtenerNodo(hijo, buscado);
                    hijo = hijo.getHermanoDerecho();
                    
                    
                }
            }
        }
        return resultado;
    }
    

    public boolean insertarPorPosicion(Object elem, int posPadre){ 
        boolean exito = true;
        if(this.raiz == null){
            this.raiz = new NodoGen(elem, null, null);
        }
        else{
            int[] posactual = {1};
            NodoGen nPadre = encontrarPadre(this.raiz, posPadre, posactual);
            if (nPadre != null) {
                if (nPadre.getHijoIzquierdo() != null) {
                    nPadre = nPadre.getHijoIzquierdo();
                    while (nPadre.getHermanoDerecho() != null) {
                        nPadre = nPadre.getHermanoDerecho();
                    } 
                    nPadre.setHermanoDerecho(new NodoGen(elem, null, null));
                }
                else{
                    nPadre.setHijoIzquierdo(new NodoGen(elem, null, null));
                }
                
                
            }
            else{
                exito =false;
            }
        }
        return exito;
    }

    private NodoGen encontrarPadre(NodoGen aux, int padre, int[] posactual){ //tuki
        NodoGen nPadre = null;
        if(aux != null){ 
            
            if (padre == posactual[0]) {
                nPadre = aux;
                
            }
            else{
                aux = aux.getHijoIzquierdo();
                posactual[0]++;
                while (aux != null && nPadre == null) {
                    nPadre = encontrarPadre(aux, padre,posactual);
                    aux = aux.getHermanoDerecho();
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

    private Object buscaPadre(NodoGen nodo, Object elem){
        Object padre = null;
        if (nodo!= null && nodo.getElem().equals(elem)) {
            padre = nodo.getElem();
        }
        NodoGen aux = nodo.getHijoIzquierdo();
        while(aux != null && padre == null){
            padre = buscaPadre(aux, elem);
            aux = aux.getHermanoDerecho();
        }
        return padre;
    }

    public int altura(){
        NodoGen nodoAux = this.raiz;
        int altura = recorreAltura(nodoAux, 0, 0);
        return altura;
    }

    private int recorreAltura(NodoGen nodo, int k, int max){
        int[] altura = {max};
        int h=k;
        
        if(nodo != null){ 
            nodo = nodo.getHijoIzquierdo();
            h = h+1;
            while (nodo!= null) {
                altura[0] = recorreAltura(nodo, h, altura[0]);
                nodo = nodo.getHermanoDerecho();
            }     

        }
        
        if(k>altura[0]){
            altura[0] = k;
            
        }
        
        return altura[0];

    }

    public int nivel(Object elem){     
        NodoGen aux = this.raiz;
        int nivel = recorrerNivel(elem, aux, 0, -1);
        return nivel;
    }

    private int recorrerNivel(Object elem,NodoGen nodo, int k, int n){ 
        int nivel=n;
        if (nodo != null) {
            if(nodo.getElem().equals(elem)){
                nivel = k;
                
            }
            nodo = nodo.getHijoIzquierdo();
            while (nodo != null && nivel == -1) {
                nivel = recorrerNivel(elem, nodo, k+1, nivel);
                nodo = nodo.getHermanoDerecho();
            }
        }
        return nivel;
    }

    public void vaciar(){
        this.raiz = null;
    }

    public ArbolGen clonar(){
        ArbolGen arbolClone = new ArbolGen();
        NodoGen auxRaiz = this.raiz;
        if (!esVacia()) {
            arbolClone.raiz = ArbolBinRecursivo(auxRaiz);
        }
        return arbolClone;
    }

    private NodoGen ArbolBinRecursivo(NodoGen raiz){
        NodoGen auxArbol = null;
        if (raiz != null) {
            auxArbol = new NodoGen(raiz.getElem(), null, null);
            auxArbol.setHijoIzquierdo(ArbolBinRecursivo(raiz.getHijoIzquierdo()));
            auxArbol.setHermanoDerecho(ArbolBinRecursivo(raiz.getHermanoDerecho()));
        }

        return auxArbol;
    }

    public String toString(){   
        return toStringAux(this.raiz);
    }

    private String toStringAux(NodoGen n){
        String s = "";
        if(n!= null){
            //visita el nodo n
            s += n.getElem().toString() + " -> ";
            NodoGen hijo = n.getHijoIzquierdo();
            while (hijo != null) {
                s += hijo.getElem().toString() + ", ";
                hijo = hijo.getHermanoDerecho();
                
            }
            //comienza recorrido de los hijos de n llamando recursivamente
            //para que cada hijo agregue su subcadena a la general
            hijo = n.getHijoIzquierdo();
            while (hijo != null) {
                s+= "\n" + toStringAux(hijo);
                hijo = hijo.getHermanoDerecho();
                
            }
        }
        return s;
    }

    public Lista listarPreOrden(){
        Lista salida = new Lista();
        listarPreordenAux(this.raiz, salida);
        return salida;
    }

    public void listarPreordenAux(NodoGen nodo, Lista lis){
        if (nodo != null) {
            //visita el nodo n
            lis.insertar(nodo.getElem(), lis.longitud() + 1);

            //llamados recursivos con los hijos de n
            NodoGen hijo = nodo.getHijoIzquierdo();
            while (hijo != null) {
                listarPreordenAux(hijo, lis);
                hijo = hijo.getHermanoDerecho();
                
            }
        }
    }

    public Lista listarPosOrden(){
        Lista salida = new Lista();
        listarPosordenAux(this.raiz, salida);
        return salida;
    }

    public void listarPosordenAux(NodoGen nodo, Lista lis){
        if (nodo != null) {
            NodoGen hijo = nodo.getHijoIzquierdo();
            while (hijo != null) {
                listarPreordenAux(hijo, lis);
                hijo = hijo.getHermanoDerecho();
                
            }
            lis.insertar(nodo.getElem(), lis.longitud() + 1);
        }
    }

    public Lista listarInOrden(){
        Lista salida = new Lista();
        listarInordenAux(this.raiz, salida);
        return salida;
    }

    private void listarInordenAux(NodoGen nodo, Lista lis){
        if (nodo != null) {
            //llamado recursivo con primo hijo de n
            if (nodo.getHijoIzquierdo() != null) {
                listarInordenAux(nodo.getHijoIzquierdo(), lis);
            }
            //visita el nodo n
            lis.insertar(nodo.getElem(), lis.longitud() + 1);

            //llamado recursivos con los otros hijos de n
            if (nodo.getHijoIzquierdo() != null) {
                NodoGen hijo = nodo.getHijoIzquierdo().getHermanoDerecho(); //consulta como sabe que hay un getHermanoDerecho?? y por que se hace asi?
                while (hijo != null) {
                    listarInordenAux(nodo, lis);
                    hijo = hijo.getHermanoDerecho();
                    
                }
                
            }

        }
    }

    public Lista listarPorNiveles(){
        Lista salida = new Lista();
        NodoGen aux = this.raiz;
        NodoGen hijo;
        if (aux != null) {
            Cola q = new Cola();
            q.poner(aux);
            while (!q.esVacia()) {
                aux = (NodoGen) q.obtenerFrente();
                q.sacar();
                salida.insertar(aux.getElem(), salida.longitud()+1);
                hijo = aux.getHijoIzquierdo();
                while (hijo != null) {
                    q.poner(hijo);
                    hijo = hijo.getHermanoDerecho();
                }

                
            }
        }
        return salida;
    }

    
    
   
    public Lista ancestros(Object elem){
        Lista ancestros = new Lista();
        NodoGen aux = this.raiz;
        listarAncestros(aux, ancestros, elem);
        return ancestros;
    }

    private boolean listarAncestros(NodoGen nodo, Lista lis, Object elem){ 
        NodoGen nPadre;
        boolean verificar = false;
        if(nodo != null){
            if (nodo.getElem().equals(elem)) {
                verificar = true;

            }   
            else{
                nPadre = nodo;
                nodo = nodo.getHijoIzquierdo();
               
                while (nodo != null && verificar == false) {
                    verificar = listarAncestros(nodo, lis, elem);
                    if (verificar == true) {
                            lis.insertar(nPadre.getElem(), 1);
                    }
                    nodo = nodo.getHermanoDerecho();
                    
                }

            }
            
        }
        return verificar;
    
    }

    public boolean pertenece(Object elem){
        NodoGen aux = this.raiz;
        boolean verificar = buscarElemento(aux, elem);
        return verificar;

    }

    private boolean buscarElemento(NodoGen aux, Object elem){
        boolean verificar = false;
        if (aux != null) {
            if (aux.getElem().equals(elem)) {
                verificar = true;
            }
            aux = aux.getHijoIzquierdo();
            while (aux != null && verificar == false) {
                verificar= buscarElemento(aux, elem);
                aux = aux.getHermanoDerecho();
            }
            
        }
        return verificar;
    }

    public boolean verificarPatron(Lista lisPatron){
        NodoGen aux = this.raiz;
        boolean verificar = recursividadPatron(aux, lisPatron);
        return verificar;
    }

    private boolean recursividadPatron(NodoGen aux, Lista lisPatron){
        boolean verificar =true;
        if (aux != null) {
            if (!aux.getElem().equals(lisPatron.recuperar(1))) {
                verificar = false;
                
            }
            aux = aux.getHijoIzquierdo();
            while (aux != null && verificar == true) {
                lisPatron.eliminar(1);
                verificar= buscarElemento(aux, lisPatron);
                aux = aux.getHermanoDerecho();
            }
            
        }
        return verificar;
    }

    public Lista listaQueJustificaLaAltura(){
        Lista lisAltura = new Lista();
        NodoGen aux = this.raiz;
        listaAlturaRecursiva(aux,0,0, lisAltura);
        return lisAltura;
    }

    private int listaAlturaRecursiva(NodoGen nodo, int k, int max, Lista lis){ //rehacer (copie el metodo altura)
        int[] altura = {max};
        int h=k;
        
        if(nodo != null){ 
            nodo = nodo.getHijoIzquierdo();
            h = h+1;
            while (nodo!= null) {
                altura[0] = recorreAltura(nodo, h, altura[0]);
                nodo = nodo.getHermanoDerecho();
            }     

        }
        
        if(k>altura[0]){
            altura[0] = k;
            
        }
        
        return altura[0];

        

    }

    public Lista frontera(){
        Lista fronteraLista = new Lista();
        NodoGen aux = this.raiz;
        fronteraRecursividad(fronteraLista, aux);
        return fronteraLista;
    }

    private void fronteraRecursividad(Lista lis, NodoGen aux){
        if (aux != null) {
            if (aux.getHijoIzquierdo() == null) {
                lis.insertar(aux.getElem(), lis.longitud()+1);
            }
            else{
                aux = aux.getHijoIzquierdo();
                while (aux != null ) {
                    fronteraRecursividad(lis, aux);;
                    aux = aux.getHermanoDerecho();
                }
            }
            
            
        }
        
    }





    //ejercicio de parcial
    public boolean verificarCamino(Lista lis){
        int[] i = {1};
        boolean verif = AuxVerificarcamino(lis, this.raiz, i);
        return verif;
        
    }

    private boolean AuxVerificarcamino(Lista lis, NodoGen nodo, int[] i){
        boolean verif = false;
        if (nodo != null && lis.recuperar(i[0]) != null && lis.recuperar(i[0]).equals(nodo.getElem())) {
            i[0]++; //como se cumple la condicion voy recorriendo la lista
            //visita el nodo n
            //llamados recursivos con los hijos de n
            NodoGen hijo = nodo.getHijoIzquierdo();
            while (hijo != null) {
                verif = AuxVerificarcamino(lis, hijo, i);
                hijo = hijo.getHermanoDerecho();
            }
        }
        if (lis.recuperar(i[0])==null) { //si se completa la lista entonces es porque todo se verifico correctamente
            verif =true; //devuelvo true
        }  
        return verif; 
    }



    //ejercicio 2 parcial
    public Lista listarEntreNiveles(int niv1, int niv2){
        Lista salida = new Lista();
        NodoGen aux = this.raiz;
        
        if (aux != null) {
            listarEntreNivelesAux(salida, aux, niv1, niv2, 0);
        }
        return salida;

    }

    private void listarEntreNivelesAux(Lista lis, NodoGen nodo, int niv1, int niv2, int nivel){
        NodoGen hijo;
        
        //visita el nodo n
        if (niv1<= nivel && niv2>= nivel) {
            lis.insertar(nodo.getElem(), lis.longitud() + 1);
            
        }
        //llamados recursivos con los hijos de n
        hijo = nodo.getHijoIzquierdo();
        
        while (hijo != null && niv2>=nivel) {
            listarEntreNivelesAux(lis, hijo, niv1, niv2, nivel+1);
            hijo = hijo.getHermanoDerecho();
                
        }
        
    }

    //ejercio 3 parcial

    public void eliminar(Object elem){
        NodoGen aux = this.raiz;
        eliminarAux(elem, aux);
    }

    private void eliminarAux(Object elem, NodoGen nodo){
        boolean exito = false;
        if (nodo != null) {
            NodoGen hijo = nodo.getHijoIzquierdo();
            NodoGen hermano = nodo.getHermanoDerecho();
            if (this.raiz.getElem().equals(elem)) {
                this.raiz = null;
            }
            
            else if ((hijo != null &&  hijo.getElem().equals(elem))) {
                nodo.setHijoIzquierdo(hijo.getHermanoDerecho());
                

            }
            else if ((hermano != null && hermano.getElem().equals(elem))) {
               nodo.setHermanoDerecho(hermano.getHermanoDerecho());
            }
            else{
                while (hijo != null && exito == false) {
                    eliminarAux(elem, hijo);
                    hijo = hijo.getHermanoDerecho();
                    
                }
            }
        }   
    }


    //ejercicio 4 
    public Lista listarHastaNivel(int niv){
        Lista lis = new Lista();
        listarHastaNivelAux(niv, lis, this.raiz, -1);
        return lis;
    }

    private void listarHastaNivelAux(int niv, Lista lis, NodoGen nodo, int cont){
        if (nodo != null) {
            cont++;
            if (cont<= niv) {
                lis.insertar(nodo.getElem(), lis.longitud()+1);
            }
            NodoGen hijo = nodo.getHijoIzquierdo();
            while (hijo != null && cont<= niv) {
                listarHastaNivelAux(niv,lis,hijo, cont);
                hijo = hijo.getHermanoDerecho();
            }
        }
    }

    //ejercicio parcial 1
    public boolean jerarquizar(Object elem){
        boolean exito = false;
        if (this.raiz != null) {
            exito = jerarquizarAux(elem, this.raiz, null);
        }
        return exito;
    }

    private boolean jerarquizarAux(Object elem, NodoGen nodo, NodoGen padre){
        boolean exito = false;
        if (nodo != null) {
            NodoGen herm = nodo.getHermanoDerecho();
            if (padre != this.raiz && nodo.getElem().equals(elem) && nodo != this.raiz) {
                NodoGen hermanos = padre.getHermanoDerecho();
                padre.setHijoIzquierdo(herm);
                padre.setHermanoDerecho(nodo);
                padre.getHermanoDerecho().setHermanoDerecho(hermanos);
                exito = true;
            }
            else if(padre != this.raiz && herm != null && herm.getElem().equals(elem) && nodo != this.raiz) {
                NodoGen hermanos = padre.getHermanoDerecho();
                NodoGen hijoHermanos = herm.getHermanoDerecho();
                nodo.setHermanoDerecho(hijoHermanos);
                padre.setHermanoDerecho(herm);
                padre.getHermanoDerecho().setHermanoDerecho(hermanos);
                exito = true;
            }
            else{
                NodoGen hijo = nodo.getHijoIzquierdo();
                while (hijo != null && exito == false) {
                    exito = jerarquizarAux(elem, hijo, nodo);
                    hijo = hijo.getHermanoDerecho();   
                }
            }
        }
        return exito;
    }

}

