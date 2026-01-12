package jerarquicas;

import lineales.dinamicas.Lista;

public class ArbolBB {
    private NodoABB raiz;

    public ArbolBB(){
        raiz = null;
    }

    public boolean insertar(Comparable elem){ 
        boolean exito = true;
        if(this.raiz == null){
            this.raiz = new NodoABB(elem);
        }else{
            exito = insertarAux(this.raiz, elem);
        }
        return exito;

    }

    private boolean insertarAux(NodoABB n, Comparable elem){ 
        //precondicion: n es nulo
        boolean exito = true;
        if ((elem.compareTo(n.getElem())) == 0) {
            //reportar error: elemento repetido
            exito = false;
        }
        else if(elem.compareTo(n.getElem())<0){
            //elemento es menor que n.getElem()
            //si tiene HI baja a la izquierda, sino agrega elemento
            if (n.getIzquierdo() != null) {
                exito = insertarAux(n.getIzquierdo(), elem);
            }
            else{
                n.setIzquierdo(new NodoABB(elem));
            }

        }
        else{ //elemento es mayor que n.getElem()
              //si tiene HD baja a la derecha, sino agrega elemento
            if (n.getDerecho() != null) {
                exito = insertarAux(n.getDerecho(), elem);
                
            }
            else{
                n.setDerecho(new NodoABB(elem));
            }

        }

        return exito;
    }

    public boolean eliminar (Comparable elem){
        boolean exito = false;
        if (this.raiz != null) {
             exito = eliminarAux(this.raiz, elem, null);
            
        }
        return exito;
    }

    private boolean eliminarAux(NodoABB n, Comparable elem, NodoABB padre){
        boolean exito = false;
        //encuentra
        if (elem.compareTo(n.getElem()) == 0) { //mandar todos los casos a metodos privados separados
            casosAux(n, elem, padre);
            exito = true;
           
        }

        //Se mueve
        else if(elem.compareTo(n.getElem())<0){
            if (n.getIzquierdo()!= null) {
                exito = eliminarAux(n.getIzquierdo(), elem, n);
            }
        
        }
        else{
            if (n.getDerecho()!= null) {
                exito = eliminarAux(n.getDerecho(), elem, n);
            }
        }

        return exito;
    }

    private void casosAux(NodoABB n, Comparable elem, NodoABB padre){
            if (n.getIzquierdo() == null && n.getDerecho() == null) { // caso 1 (eliminar hojas)
                if (padre == null) {
                    this.raiz = null;
                }
                else if (n.getElem().compareTo(padre.getElem())<0) { 
                    padre.setIzquierdo(null);
                }
                else{
                    padre.setDerecho(null);
                }
                
            }
            else if (n.getIzquierdo() != null && n.getDerecho() == null) { //caso 2 (eliminar padre con Hijo izquierdo)
                if (padre == null) {
                    this.raiz = n.getIzquierdo();
                }
                else if (n.getElem().compareTo(padre.getElem())<0) {
                    padre.setIzquierdo(n.getIzquierdo());
                }
                else{
                    padre.setDerecho(n.getIzquierdo());
                }
            }
            else if (n.getIzquierdo() == null && n.getDerecho() != null) { //caso 2 (eliminar padre con hijo derecho)
                if (padre == null) {
                    this.raiz = n.getDerecho();
                }
                else if (n.getElem().compareTo(padre.getElem())<0) {
                    padre.setIzquierdo(n.getDerecho());
                }
                else{
                    padre.setDerecho(n.getDerecho());
                }
            }

            else if(n.getIzquierdo() != null && n.getDerecho() != null){ // caso 3 (eliminar padre con ambos hijos)
                //usa como padre al mayor de la izquierda
                Comparable izqMax = buscarMayorIzquierda(n.getIzquierdo(), n);
                padre.setElem(izqMax);
                
                
            }      
    }

    private Comparable buscarMayorIzquierda(NodoABB n, NodoABB padre){
        
        Comparable maxIzq = n.getElem();
        if (n.getDerecho() != null) {
            maxIzq = buscarMayorIzquierda(n.getDerecho(), n);
        }
        else if(n.getIzquierdo() != null) {
            padre.setIzquierdo(n.getIzquierdo());
        }
           
        
        return maxIzq;

    }

    public boolean pertenece(Comparable elem){
        boolean exito = false;
        if (this.raiz !=null) {
            exito = perteneceAux(this.raiz, elem);
        }

        return exito;
    }

    public boolean perteneceAux(NodoABB n, Comparable elem){
        boolean exito = false;
        if ((elem.compareTo(n.getElem())) == 0) {
            exito = true;
        }
        else if(elem.compareTo(n.getElem())<0){
            if (n.getIzquierdo() != null) {
                exito = perteneceAux(n.getIzquierdo(), elem);
            }
          

        }
        else{ 
            if (n.getDerecho() != null) {
                exito = perteneceAux(n.getDerecho(), elem);
                
            }
        }
        return exito;
    }

    public boolean esVacio(){
        return this.raiz == null;
    }

    public Lista listar(){
        Lista listaElem = new Lista();
        if (this.raiz != null) {
            listarAux(this.raiz, listaElem, this.raiz.getElem());
        }
        
        return listaElem;
    }

    private void listarAux(NodoABB n, Lista listaElem, Comparable elem){
        
        if(n.getIzquierdo() != null && n.getIzquierdo().getElem().compareTo(elem)<=0){   
            listarAux(n.getIzquierdo(), listaElem, n.getIzquierdo().getElem());
        }
        listaElem.insertar(elem, listaElem.longitud()+1);
        if(n.getDerecho() != null && n.getDerecho().getElem().compareTo(elem)>0){ 
            
            listarAux(n.getDerecho(), listaElem, n.getDerecho().getElem());
        }

    }
    public Lista listarRango(Comparable elemMin, Comparable elemMax){
        Lista listaRango = new Lista();
        if(this.raiz != null){
            listarRangoAux(elemMin, elemMax, this.raiz, listaRango, this.raiz.getElem());
        }

        return listaRango;

    }

    private void listarRangoAux(Comparable elemMin, Comparable elemMax, NodoABB n, Lista lista, Comparable elemActual){
        
        if(n.getIzquierdo() != null && elemMin.compareTo(n.getElem())<0){
            listarRangoAux(elemMin, elemMax, n.getIzquierdo(), lista, n.getIzquierdo().getElem());
        }
        if (elemMin.compareTo(elemActual)<=0 && elemMax.compareTo(n.getElem())>=0) {
            lista.insertar(n.getElem(), lista.longitud()+1);
        }
        if(n.getDerecho() != null && elemMax.compareTo(n.getElem())>0){ 
            listarRangoAux(elemMin, elemMax, n.getDerecho(), lista, n.getDerecho().getElem());
        }

    }

    public Comparable minimoElemento(){
        Comparable elem = null;
        if (this.raiz != null) {
            elem = buscarMinimo(this.raiz, this.raiz.getElem());
        }

        return elem;

    }

    private Comparable buscarMinimo(NodoABB n, Comparable elem){
        Comparable min = elem;
        if(n.getIzquierdo() != null && min.compareTo(n.getIzquierdo().getElem())>=0){ //si min es mayor a al elemento izquierdo
            min = buscarMinimo(n.getIzquierdo(), n.getIzquierdo().getElem());
        }
        
        return min;

    }

    public Comparable maximoElemento(){
        Comparable elem = null;
        if (this.raiz != null) {
            elem = buscarMaximo(this.raiz, this.raiz.getElem());
        }

        return elem;
        
    }

    private Comparable buscarMaximo(NodoABB n, Comparable elem){
        Comparable max = elem;
        if(n.getDerecho() != null && max.compareTo(n.getDerecho().getElem())<=0){ //si max es menor al elemento derecho
            max = buscarMaximo(n.getDerecho(), n.getDerecho().getElem());
        }
        
        return max;

    }

    public String toString(){
        String cad = "";
        if(this.raiz != null){
            cad = cad + "raiz " + this.raiz.getElem() + "\n";
            cad = toStringAux(this.raiz, cad);
        }
        return cad;
    }

    private String toStringAux(NodoABB nodo,String cad){
        if(nodo != null){
            if(nodo.getIzquierdo() != null){
                cad = cad + nodo.getIzquierdo().getElem() + " HI - PADRE: " + nodo.getElem() + "\n";
                cad = toStringAux(nodo.getIzquierdo(), cad);
            }
            if(nodo.getDerecho() != null){
                cad = cad + nodo.getDerecho().getElem() + " HD - PADRE: " + nodo.getElem() + "\n";
                cad = toStringAux(nodo.getDerecho(), cad);
            }
        }
        return cad;
    }


    //ejercicio 1

    public void eliminarMinimo(){
        NodoABB nodo = this.raiz;
        if (nodo != null) {
            eliminarMinimoAux(nodo, null);
        }
    }

    private void eliminarMinimoAux(NodoABB nodo, NodoABB padre){
        System.out.println(nodo.getElem());
        if (nodo.getIzquierdo() != null) {
            eliminarMinimoAux(nodo.getIzquierdo(), nodo);
        }
        else if(padre != null && nodo.getIzquierdo() == null && nodo.getDerecho() != null){
            padre.setIzquierdo(nodo.getDerecho());
        }
        else if(padre == null && nodo.getIzquierdo() == null && nodo.getDerecho() != null){
            this.raiz = nodo.getDerecho();
        }
        else{
            padre.setIzquierdo(null);
            
        }
    }


    //ejercicio 2

    public ArbolBB clonarParteInvertida(Comparable elem){
        ArbolBB cloneBB = new ArbolBB();
        if (this.raiz != null) {
            cloneBB.raiz = clonarParteInvertidaAux(elem, this.raiz, false);
        }
        return cloneBB;
    }

    private NodoABB clonarParteInvertidaAux(Comparable elem, NodoABB nodo, boolean exito){
        NodoABB auxArbol = null;
        if (nodo != null && exito == false && (elem.compareTo(nodo.getElem()) < 0)) { //se mueve a la izquierda
            auxArbol = clonarParteInvertidaAux(elem, nodo.getIzquierdo(), exito);
        }

        else if (nodo != null && exito == false && elem.compareTo(nodo.getElem()) > 0) { //se mueve a la derecha
            auxArbol = clonarParteInvertidaAux(elem, nodo.getDerecho(), exito);
        }
        
        else if(nodo != null && (elem.compareTo(nodo.getElem()) == 0 || exito == true)){ //si lo encuentra lo crea
            auxArbol = new NodoABB(nodo.getElem(), null, null);
            auxArbol.setIzquierdo(clonarParteInvertidaAux(elem, nodo.getDerecho(), true));
            auxArbol.setDerecho(clonarParteInvertidaAux(elem, nodo.getIzquierdo(), true));
        }

        
        return auxArbol;
    }

    
    //ejercicio 3

    public Lista listarMayorIgual(Comparable elem){
        Lista lis = new Lista();
        listarMayorIgualAux(elem, lis, this.raiz);
        return lis;
    }

    private void listarMayorIgualAux(Comparable elem, Lista lis, NodoABB nodo){
        if (nodo != null) {
            if(elem.compareTo(nodo.getElem())<=0){
                listarMayorIgualAux(elem, lis, nodo.getIzquierdo());
                lis.insertar(nodo.getElem(), 1);
                listarMayorIgualAux(elem, lis, nodo.getDerecho());
            }
            else{
                listarMayorIgualAux(elem, lis, nodo.getDerecho());
            }
            
        }
    }

    //ejercicio 4

    public Lista listarMenorIgual(Comparable elem){
        Lista lis = new Lista();
        listarMenorIgualAux(elem, lis, this.raiz);
        return lis;
    }

    private void listarMenorIgualAux(Comparable elem, Lista lis, NodoABB nodo){
        if (nodo != null) {
            if(elem.compareTo(nodo.getElem())>=0){
                listarMenorIgualAux(elem, lis, nodo.getDerecho());
                lis.insertar(nodo.getElem(), 1);
                listarMenorIgualAux(elem, lis, nodo.getIzquierdo());
            }
            
            else{
                listarMenorIgualAux(elem, lis, nodo.getIzquierdo());
            }
            
        }
    }

    public Comparable mejorCandidato(Comparable elem){
        Comparable mejor = mejorCandidatoAux(elem, this.raiz);
        return mejor;
    }

    private int mejorCandidatoAux(Comparable elem, NodoABB nodo){
        Comparable mejor = null;
        if (nodo != null) {
            if (nodo.getElem().compareTo(elem) == 0) {
                if (nodo.getDerecho() != null && nodo.getIzquierdo() != null) {
                    int cand1 = buscarMaxArbol(nodo.getIzquierdo());
                    int cand2 = buscarMinArbol(nodo.getDerecho());
                    int act = (int) nodo.getElem();
                    if ((act -cand1) < (cand2 - act)) {
                        mejor = cand1;
                    }
                    else{
                        mejor = cand2;
                    }
                }
                else if (nodo.getDerecho() != null && nodo.getIzquierdo() == null) {
                    mejor =  nodo.getDerecho().getElem();
                }
                else if (nodo.getDerecho() == null && nodo.getIzquierdo() != null) {
                    mejor = nodo.getIzquierdo().getElem();
                }
                
            }
            else if (nodo.getElem().compareTo(elem) < 0) {
                mejor = mejorCandidatoAux(elem, nodo.getDerecho());
                
            }
            else{
                mejor = mejorCandidatoAux(elem, nodo.getIzquierdo());
            }
        }
        
        return (int) mejor;
    }

    private int buscarMinArbol(NodoABB nodo){
        int cand2 = 0;
        if (nodo.getIzquierdo() == null) {
            cand2 = (int) nodo.getElem();
        }
        else{
            cand2 = buscarMinArbol(nodo.getIzquierdo());
        }
        return cand2;
    }

    private int buscarMaxArbol(NodoABB nodo){
        int cand1 = 0;
        if (nodo.getDerecho() == null) {
            cand1 = (int)nodo.getElem();
        }
        else{
            cand1 = buscarMaxArbol(nodo.getDerecho());
        }
        return cand1;
    }

    public boolean eliminarMinSubarbol(Comparable elem){
        boolean exito = false;
        if (this.raiz != null) {
            exito = eliminarMinSubarbolAux(elem, this.raiz);
        }
        return exito;
    }

    private boolean eliminarMinSubarbolAux(Comparable elem, NodoABB nodo){
        boolean exito = false;
        if (nodo != null) {
            if (elem.compareTo(nodo.getElem()) == 0) {
                eliminarMinAux2(nodo);
                exito = true;
            }
            else if (elem.compareTo(nodo.getElem()) < 0) {
                exito = eliminarMinSubarbolAux(elem, nodo.getIzquierdo());
            }   
            else{
                exito = eliminarMinSubarbolAux(elem, nodo.getDerecho());
            }
        
        }
        return exito;
    }

    private void eliminarMinAux2(NodoABB nodo){
        while (nodo.getIzquierdo().getIzquierdo() != null ) {
            nodo = nodo.getIzquierdo();
        }
        if (nodo.getIzquierdo().getIzquierdo() == null) {
            nodo.setIzquierdo(null);
            
        }
    }

}
