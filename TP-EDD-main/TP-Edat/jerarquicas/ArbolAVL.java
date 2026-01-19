package jerarquicas;

import lineales.dinamicas.*;

//Modificado como tabla de busqueda clave/valor
public class ArbolAVL {
    private NodoAVL raiz;

    public ArbolAVL() {
        raiz = null;
    }

    public Object obtenerValor(Comparable clave) {
        Object valor = null;
        NodoAVL nodoBuscado = obtenerNodo(raiz, clave);
        if (nodoBuscado != null) {
            valor = nodoBuscado.getValor();
        }
        return valor;
    }

    public boolean pertenece(Comparable clave) {
        boolean resultado = true;
        if (obtenerNodo(raiz, clave) == null)
            resultado = false;
        return resultado;
    }

    private NodoAVL obtenerNodo(NodoAVL nodo, Comparable clave) {
        NodoAVL nodoBuscado = null;
        if (nodo != null) {
            if (nodo.getClave().compareTo(clave) > 0) {
                nodoBuscado = obtenerNodo(nodo.getIzquierdo(), clave);
            } else if (nodo.getClave().compareTo(clave) < 0) {
                nodoBuscado = obtenerNodo(nodo.getDerecho(), clave);
            } else {
                nodoBuscado = nodo;
            }
        }
        return nodoBuscado;
    }

    public boolean insertar(Comparable clave, Object valor) {
        boolean[] resultado = new boolean[1];
        resultado[0] = false;
        if (raiz != null) {
            raiz = insertarAux(raiz, clave, valor, resultado);
        } else {
            raiz = new NodoAVL(clave, valor, null, null);
            raiz.recalcularAltura();
            resultado[0] = true;
        }
        return resultado[0];
    }

    private NodoAVL insertarAux(NodoAVL nodo, Comparable clave, Object valor, boolean[] resultado) {
        NodoAVL aux = nodo;
        if (nodo != null) {
            if (clave.compareTo(nodo.getClave()) < 0) {
                nodo.setIzquierdo(insertarAux(nodo.getIzquierdo(), clave, valor, resultado));
            } else if (clave.compareTo(nodo.getClave()) > 0) {
                nodo.setDerecho(insertarAux(nodo.getDerecho(), clave, valor, resultado));
            }

            if (resultado[0]) {
                aux = balancear(nodo);
                aux.recalcularAltura();
            }
        } else {
            // Caso en el que el nodo es null, se crea un nuevo nodo
            aux = new NodoAVL(clave, valor, null, null);
            aux.recalcularAltura();
            resultado[0] = true;
        }
        return aux;
    }

    private NodoAVL balancear(NodoAVL nodo) {
        int balance;
        balance = calcularBalance(nodo);
        if (balance < -1) {
            if (calcularBalance(nodo.getDerecho()) <= 0) {
                // Rotacion simple a izquierda
                nodo = rotarIzquierda(nodo);
                // System.out.println("RI");
            } else {
                // Rotacion doble a izquierda
                nodo.setDerecho(rotarDerecha(nodo.getDerecho()));
                nodo.getDerecho().recalcularAltura();
                nodo = rotarIzquierda(nodo);
                // System.out.println("RDI");
            }
        } else if (balance > 1) {
            if (calcularBalance(nodo.getIzquierdo()) >= 0) {
                // Rotacion simple a derecha
                nodo = rotarDerecha(nodo);
                // System.out.println("RD");
            } else {
                // Rotacion doble a derecha
                nodo.setIzquierdo(rotarIzquierda(nodo.getIzquierdo()));
                nodo.getIzquierdo().recalcularAltura();
                nodo = rotarDerecha(nodo);
                // System.out.println("RDD");
            }
        }
        return nodo;
    }

    private int calcularBalance(NodoAVL nodo) {
        int altIzq = -1, altDer = -1;
        if (nodo.getIzquierdo() != null)
            altIzq = nodo.getIzquierdo().getAltura();
        if (nodo.getDerecho() != null)
            altDer = nodo.getDerecho().getAltura();
        return altIzq - altDer;
    }

    private NodoAVL rotarIzquierda(NodoAVL pivot) {
        NodoAVL y = pivot.getDerecho();
        pivot.setDerecho(y.getIzquierdo());
        y.setIzquierdo(pivot);
        pivot.recalcularAltura();
        y.recalcularAltura();
        return y;
    }

    private NodoAVL rotarDerecha(NodoAVL pivot) {
        NodoAVL y = pivot.getIzquierdo();
        pivot.setIzquierdo(y.getDerecho());
        y.setDerecho(pivot);
        pivot.recalcularAltura();
        y.recalcularAltura();
        return y;
    }

    public boolean eliminar(Comparable clave) {
        boolean[] eliminado = new boolean[1]; // Flag para saber si se eliminó
        raiz = eliminarAux(raiz, clave, eliminado);
        return eliminado[0];
    }

    // Devuelve el nuevo subárbol raíz tras la eliminación
    private NodoAVL eliminarAux(NodoAVL nodo, Comparable clave, boolean[] eliminado) {
        NodoAVL aux = null;
        if (nodo == null) {
            eliminado[0] = false;
        } else {
            int cmp = nodo.getClave().compareTo(clave);
            if (cmp > 0) {
                nodo.setIzquierdo(eliminarAux(nodo.getIzquierdo(), clave, eliminado));
                nodo.recalcularAltura();
                nodo = balancear(nodo);
                aux = nodo;
            } else if (cmp < 0) {
                nodo.setDerecho(eliminarAux(nodo.getDerecho(), clave, eliminado));
                nodo.recalcularAltura();
                nodo = balancear(nodo);
                aux = nodo;
            } else {
                // Nodo encontrado
                eliminado[0] = true;
                // Caso 1: solo un hijo o ninguno
                if (nodo.getIzquierdo() == null) {
                    aux = nodo.getDerecho();
                } else if (nodo.getDerecho() == null) {
                    aux = nodo.getIzquierdo();
                } else {
                    // Caso 2: dos hijos
                    // Buscar el menor del subárbol derecho
                    NodoAVL sucesor = nodo.getDerecho();
                    while (sucesor.getIzquierdo() != null) {
                        sucesor = sucesor.getIzquierdo();
                    }
                    // Reemplazar valor y eliminar sucesor recursivamente
                    nodo.setElem(sucesor.getClave());
                    nodo.setValor(sucesor.getValor());
                    nodo.setDerecho(eliminarAux(nodo.getDerecho(), sucesor.getClave(), eliminado));
                    nodo.recalcularAltura();
                    nodo = balancear(nodo);
                    aux = nodo;
                }
            }
        }
        return aux;
    }

    public Lista listarClave() {
        // Lista en inorden
        Lista salida = new Lista();
        listarInordenClave(raiz, salida);
        return salida;
    }

    private void listarInordenClave(NodoAVL nodo, Lista salida) {
        if (nodo != null) {
            // Se visita invertido para no obtener la longitud de la lista para cada
            // elemento.
            listarInordenClave(nodo.getDerecho(), salida);

            salida.insertar(nodo.getClave(), 1);

            listarInordenClave(nodo.getIzquierdo(), salida);
        }
    }

    public Lista listarValor() {
        // Lista en inorden
        Lista salida = new Lista();
        listarInordenValor(raiz, salida);
        return salida;
    }

    private void listarInordenValor(NodoAVL nodo, Lista salida) {
        if (nodo != null) {
            // Se visita invertido para no obtener la longitud de la lista para cada
            // elemento.
            listarInordenValor(nodo.getDerecho(), salida);

            salida.insertar(nodo.getValor(), 1);

            listarInordenValor(nodo.getIzquierdo(), salida);
        }
    }

    public Lista listarRangoClave(Comparable claveMin, Comparable claveMax) {
        Lista lista = new Lista();
        if (claveMax.compareTo(claveMin) >= 0) {
            listarRangoClaveAux(claveMin, claveMax, raiz, lista);
        }
        return lista;
    }

    private void listarRangoClaveAux(Comparable min, Comparable max, NodoAVL nodo, Lista lista) {
        Comparable elem;
        if (nodo != null) {
            elem = nodo.getClave();

            if (elem.compareTo(min) > 0) {
                // Puede haber claves dentro del rango en el subarbol izquierdo
                listarRangoClaveAux(min, max, nodo.getIzquierdo(), lista);
            }

            if (elem.compareTo(min) >= 0 && elem.compareTo(max) <= 0) {
                // Clave dentro del rango
                lista.insertar(elem, 1);
            }

            if (elem.compareTo(max) < 0) {
                // Puede haber claves dentro del rango en el subarbol derecho
                listarRangoClaveAux(min, max, nodo.getDerecho(), lista);
            }
        }
    }

    public Lista listarRango(Comparable claveMin, Comparable claveMax) {
        Lista lista = new Lista();
        if (claveMax.compareTo(claveMin) >= 0) {
            listarRangoAux(claveMin, claveMax, raiz, lista);
        }
        return lista;
    }

    private void listarRangoAux(Comparable min, Comparable max, NodoAVL nodo, Lista lista) {
        Comparable elem;
        if (nodo != null) {
            elem = nodo.getClave();

            if (elem.compareTo(min) > 0) {
                // Puede haber claves dentro del rango en el subarbol izquierdo
                listarRangoAux(min, max, nodo.getIzquierdo(), lista);
            }

            if (elem.compareTo(min) >= 0 && elem.compareTo(max) <= 0) {
                // Clave dentro del rango
                lista.insertar(nodo.getValor(), 1);
            }

            if (elem.compareTo(max) < 0) {
                // Puede haber claves dentro del rango en el subarbol derecho
                listarRangoAux(min, max, nodo.getDerecho(), lista);
            }
        }
    }

    public Comparable minimoElem() {
        NodoAVL nodo;
        Comparable elem = null;
        if (raiz != null) {
            nodo = raiz;
            while (nodo.getIzquierdo() != null)
                nodo = nodo.getIzquierdo();
            elem = nodo.getClave();
        }
        return elem;
    }

    public Comparable maximoElem() {
        NodoAVL nodo;
        Comparable elem = null;
        if (raiz != null) {
            nodo = raiz;
            while (nodo.getDerecho() != null)
                nodo = nodo.getDerecho();
            elem = nodo.getClave();
        }
        return elem;
    }

    public ArbolAVL clone() {
        ArbolAVL arbolClon = new ArbolAVL();
        if (!this.esVacio()) {
            arbolClon.raiz = new NodoAVL(raiz.getClave(), raiz.getValor(), null, null);
            cloneAux(raiz, arbolClon.raiz);
        }

        return arbolClon;
    }

    private void cloneAux(NodoAVL nodoOr, NodoAVL nodoCl) {
        NodoAVL nodo1 = null, nodo2 = null;
        if (nodoOr != null) {
            if (nodoOr.getDerecho() != null) {
                nodo2 = new NodoAVL(nodoOr.getDerecho().getClave(), nodoOr.getDerecho().getValor(), null, null);
                nodoCl.setDerecho(nodo2);
                cloneAux(nodoOr.getDerecho(), nodo2);
            }
            if (nodoOr.getIzquierdo() != null) {
                nodo1 = new NodoAVL(nodoOr.getIzquierdo().getClave(), nodoOr.getIzquierdo().getValor(), null, null);
                nodoCl.setIzquierdo(nodo1);
                cloneAux(nodoOr.getIzquierdo(), nodo1);
            }
        }
    }

    public boolean esVacio() {
        return raiz == null;
    }

    public void vaciar() {
        raiz = null;
    }

    public String toString() {
        // in-orden
        String[] arr = { "" };
        return obtenerElems(arr, raiz);
    }

    private String obtenerElems(String[] text, NodoAVL nodo) {
        if (nodo != null) {
            obtenerElems(text, nodo.getIzquierdo());
            text[0] = text[0] + " " + nodo.getValor().toString() + ", " + nodo.getClave().toString();
            obtenerElems(text, nodo.getDerecho());
        }
        return text[0];
    }

    public void dibujar() {
        if (raiz != null)
            raiz.dibujar("");
        System.out.println();
    }
}
