package grafos;

import java.util.HashMap;
import java.util.Map;
import lineales.dinamica.*;
import Utiles.IO;
/*
imports usados anteriormente solo en 4-1 o no utilizados
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import conjuntistas.ArbolAVL;
import transporteAgua.*;
*/

public class Grafo {

    NodoVert inicio;

    public Grafo() {
        this.inicio = null;
    }

    public boolean insertarVertice(Object nuevoVertice) {
        boolean exito = false;
        NodoVert aux = this.ubicarVertice(nuevoVertice);
        if (aux == null) {
            this.inicio = new NodoVert(nuevoVertice, this.inicio, null);
            exito = true;
        }
        return exito;

    }

    public boolean eliminarVertice(Object verticeElim) {
        boolean exito = false;
        NodoVert actual = this.inicio;
        NodoVert anterior = null;

        // Buscar el vértice a eliminar
        while (actual != null && !actual.getElem().equals(verticeElim)) {
            anterior = actual;
            actual = actual.getSigVertice();
        }

        if (actual != null) {
            // Eliminar todos los arcos que apuntan al vértice a eliminar
            NodoVert recorredor = this.inicio;
            while (recorredor != null) {
                eliminarArcoAux(recorredor, verticeElim);
                recorredor = recorredor.getSigVertice();
            }

            // Eliminar el vértice de la lista de vértices
            if (anterior == null) {
                this.inicio = actual.getSigVertice();
            } else {
                anterior.setSigVertice(actual.getSigVertice());
            }
            exito = true;
        }

        return exito;
    }

    public boolean existeVertice(Object buscado) {
        boolean existe = false;
        NodoVert vertices = this.inicio;

        while (vertices != null && existe == false) {
            if (vertices.getElem().equals(buscado)) {
                existe = true;
            } else {
                vertices = vertices.getSigVertice();
            }
        }

        return existe;
    }

    private NodoVert ubicarVertice(Object buscado) {
        NodoVert aux = this.inicio;
        while (aux != null && !aux.getElem().equals(buscado)) {
            aux = aux.getSigVertice();
        }
        return aux;
    }

    public boolean modEtiqueta(Object origen, Object destino, double caudal) {
        boolean exito = false;
        NodoVert nodoOrigen = ubicarVertice(origen);
        NodoVert nodoDestino = ubicarVertice(destino);

        if (nodoOrigen != null && nodoDestino != null) {
            NodoAdy adyacente = nodoOrigen.getPrimerAdy();
            while (adyacente != null) {
                if (adyacente.getVertice().getElem().equals(destino)) {
                    adyacente.setEtiqueta(caudal);
                    exito = true;
                }
                adyacente = adyacente.getSigAdyacente();
            }
        }
        return exito;
    }

    public boolean insertarArco(Object origen, Object destino, double etiqueta) {
        boolean exito = false;
        if (this.inicio != null) {
            NodoVert nodoOrigen = ubicarVertice(origen);
            NodoVert nodoDestino = ubicarVertice(destino);

            if (nodoOrigen != null & nodoDestino != null) {
                exito = true;
                NodoAdy recorredorAd, anteriorAd;
                NodoAdy nuevoArco = new NodoAdy(nodoDestino, null, etiqueta);
                if (nodoOrigen.getPrimerAdy() == null) {
                    nodoOrigen.setPrimerAdy(nuevoArco);
                } else {
                    anteriorAd = nodoOrigen.getPrimerAdy();
                    recorredorAd = anteriorAd.getSigAdyacente();

                    while (recorredorAd != null) {
                        anteriorAd = recorredorAd;
                        recorredorAd = recorredorAd.getSigAdyacente();
                    }

                    anteriorAd.setSigAdyacente(nuevoArco);
                }
            }

        }
        return exito;
    }

    public boolean eliminarArco(Object origen, Object destino) {

        boolean exito = false;
        NodoVert nodoOr = ubicarVertice(origen);

        if (nodoOr != null) {
            exito = eliminarArcoAux(nodoOr, destino);
        }

        return exito;
    }

    private boolean eliminarArcoAux(NodoVert origen, Object buscado) {

        boolean exito = false;
        NodoAdy adyacentes = origen.getPrimerAdy();
        NodoAdy recorredor;

        if (adyacentes != null) {
            Object elemento = adyacentes.getVertice().getElem();
            if (elemento.equals(buscado)) {
                origen.setPrimerAdy(adyacentes.getSigAdyacente());
                exito = true;
            } else {
                recorredor = adyacentes.getSigAdyacente();
                while (recorredor != null && !exito) {
                    if (recorredor.getVertice().getElem().equals(buscado)) {
                        adyacentes.setSigAdyacente(recorredor.getSigAdyacente());
                        exito = true;
                    }
                    adyacentes = recorredor;
                    recorredor = recorredor.getSigAdyacente();
                }
            }
        }
        return exito;
    }

    public boolean existeArco(Object origen, Object destino) {
        boolean exito = false;
        NodoVert nodoOrigen = ubicarVertice(origen);
        NodoVert nodoDestino = ubicarVertice(destino);

        if (nodoOrigen != null && nodoDestino != null) {
            NodoAdy adyacente = nodoOrigen.getPrimerAdy();
            while (adyacente != null) {
                if (adyacente.getVertice().getElem().equals(destino)) {
                    exito = true;
                }
                adyacente = adyacente.getSigAdyacente();
            }
        }
        return exito;
    }

    public String toString() {
        String cadena = "";

        if (this.inicio != null) {
            NodoVert recorredor = this.inicio;

            while (recorredor != null) {
                cadena = cadena + recorredor.getElem() + "-->";
                NodoAdy recorredorAd = recorredor.getPrimerAdy();
                while (recorredorAd != null) {
                    cadena = cadena + "(" + recorredorAd.getVertice().getElem() + " " + recorredorAd.getEtiqueta()
                            + ") ";
                    recorredorAd = recorredorAd.getSigAdyacente();
                }
                cadena = cadena + "\n";
                recorredor = recorredor.getSigVertice();
            }
        }
        return cadena;
    }

    public String dibujarGrafo() {
        StringBuilder sb = new StringBuilder();

        NodoVert recorredor = this.inicio;
        while (recorredor != null) {
            sb.append(recorredor.getElem()).append("\n");
            NodoAdy recorredorAd = recorredor.getPrimerAdy();
            if (recorredorAd != null) {
                while (recorredorAd != null) {
                    sb.append(" ├── ")
                            .append(recorredorAd.getVertice().getElem())
                            .append(" (")
                            .append(recorredorAd.getEtiqueta())
                            .append(")\n");
                    recorredorAd = recorredorAd.getSigAdyacente();
                }
            } else {
                sb.append(" (sin adyacentes)\n");
            }
            recorredor = recorredor.getSigVertice();
        }
        return sb.toString();
    }

    public boolean existeCamino(Object origen, Object destino) {
        boolean resultado = false;
        NodoVert auxO = null;
        NodoVert auxD = null;
        NodoVert aux = this.inicio;
        while ((auxO == null || auxD == null) && aux != null) {
            if (aux.getElem().equals(origen))
                auxO = aux;
            if (aux.getElem().equals(destino))
                auxD = aux;
            aux = aux.getSigVertice();
        }
        if (auxO != null && auxD != null) {
            // si ambos vertices existen busca si existe camino entre ambos nodos
            Lista visitados = new Lista();
            resultado = existeCaminoAux(auxO, destino, visitados);
        }
        return resultado;
    }

    private boolean existeCaminoAux(NodoVert n, Object dest, Lista vis) {
        boolean exito = false;
        if (n != null) {
            // si vertice n es el destino: HAY CAMINO!
            if (n.getElem().equals(dest)) {
                exito = true;
            } else {
                // si no es el destino verifica si hay camino entre n y destino
                vis.insertar(n.getElem(), vis.longitud() + 1);
                NodoAdy ady = n.getPrimerAdy();
                while (!exito && ady != null) {
                    if (vis.localizar(ady.getVertice().getElem()) < 0) {
                        exito = existeCaminoAux(ady.getVertice(), dest, vis);
                    }
                    ady = ady.getSigAdyacente();
                }
            }
        }

        return exito;

    }

    public Lista caminoMasCorto(Object origen, Object destino) {
        Lista camino = new Lista();
        NodoVert nodoOrigen = ubicarVertice(origen);
        NodoVert nodoDestino = ubicarVertice(destino);
        // Verifica si los nodos origen y destino existen
        if (nodoOrigen != null && nodoDestino != null) {
            Cola cola = new Cola();
            Map<NodoVert, NodoVert> predecesor = new HashMap<>();
            Lista visitados = new Lista();

            cola.poner(nodoOrigen);
            // Marca el nodo de origen como visitado
            visitados.insertar(nodoOrigen, 1);

            boolean encontrado = false;
            while (!cola.esVacia() && !encontrado) {
                // Extrae el nodo actual de la cola
                NodoVert actual = (NodoVert) cola.obtenerFrente();
                cola.sacar();

                NodoAdy ady = actual.primerAdy;
                while (ady != null) {
                    NodoVert vecino = ady.vertice;
                    if (visitados.localizar(vecino) < 0) {
                        cola.poner(vecino);
                        visitados.insertar(vecino, 1);
                        predecesor.put(vecino, actual);
                        if (vecino == nodoDestino) {
                            encontrado = true;
                            break;
                        }
                    }
                    ady = ady.sigAdyacente;
                }
            }

            // Construir el camino desde destino a origen usando el mapa de predecesores
            if (encontrado) {
                NodoVert actual = nodoDestino;
                while (actual != null) {
                    camino.insertar(actual.getElem(), 1);
                    actual = predecesor.get(actual);
                }
            }
        } else {
            IO.salida("No se pudo encontrar un camino: uno o ambos nodos no existen.", true);
        }
        return camino;
    }

    /*
     * public Lista caminoMasLargo(Object origen, Object destino) {
     * Lista resultado = new Lista();
     * // Innecesario?
     * return resultado;
     * }
     */

    public Lista listarEnProfundidad() {
        Lista visitados = new Lista();
        // define un vertice donde comenzar a recorrer
        NodoVert aux = this.inicio;
        while (aux != null) {
            if (visitados.localizar(aux.getElem()) < 0) {
                // si el vertice no fue visitado aun, avanza en profundidad
                listarEnProfundidadAux(aux, visitados);
            }
            aux = aux.getSigVertice();
        }
        return visitados;
    }

    private void listarEnProfundidadAux(NodoVert n, Lista vis) {
        if (n != null) {
            // marca al vertice n como visitado
            vis.insertar(n.getElem(), vis.longitud() + 1);
            NodoAdy ady = n.getPrimerAdy();
            while (ady != null) {
                // visita en profundidad los adyacentes de n aun no visitados
                if (vis.localizar(ady.getVertice().getElem()) < 0) {
                    listarEnProfundidadAux(ady.getVertice(), vis);
                }
                ady.getSigAdyacente();
            }
        }
    }

    public Lista listarEnAnchura() {
        Lista visitados = new Lista();
        NodoVert aux = this.inicio;
        Cola cola = new Cola();
        while (aux != null) {
            if (visitados.localizar(aux.getElem()) < 0) {
                // si el vertice no fue visitado aun, avanza en anchura
                anchuraDesde(aux, visitados, cola);
            }
            aux = aux.getSigVertice();
        }
        return visitados;
    }

    private void anchuraDesde(NodoVert n, Lista vis, Cola cola) {
        if (n != null) {
            // marca al vertice n como visitado
            vis.insertar(n.getElem(), vis.longitud() + 1);
            cola.poner(n);
            while (!cola.esVacia()) {
                NodoVert actual = (NodoVert) cola.obtenerFrente();
                cola.sacar();
                NodoAdy ady = actual.getPrimerAdy();
                while (ady != null) {
                    // visita en anchura los adyacentes de n aun no visitados
                    if (vis.localizar(ady.getVertice().getElem()) < 0) {
                        vis.insertar(ady.getVertice().getElem(), vis.longitud() + 1);
                        cola.poner(ady.getVertice());
                    }
                    ady = ady.getSigAdyacente();
                }
            }
        }
    }

    // -------
    public Lista obtenerCamino(Object origen, Object destino) {
        Lista resultado = new Lista();
        NodoVert auxO = null;
        NodoVert auxD = null;
        NodoVert aux = this.inicio;
        while ((auxO == null || auxD == null) && aux != null) {
            if (aux.getElem().equals(origen))
                auxO = aux;
            if (aux.getElem().equals(destino))
                auxD = aux;
            aux = aux.getSigVertice();
        }
        if (auxO != null && auxD != null) {
            // si ambos vertices existen busca si existe camino entre ambos nodos
            Lista visitados = new Lista();
            obtenerCaminoAux(auxO, destino, resultado, visitados);
        }
        return resultado;
    }

    private boolean obtenerCaminoAux(NodoVert n, Object dest, Lista res, Lista vis) {
        boolean exito = false;
        if (n != null) {
            // si vertice n es el destino: HAY CAMINO!
            if (n.getElem().equals(dest)) {
                exito = true;
            } else {
                // si no es el destino verifica si hay camino entre n y destino
                vis.insertar(n.getElem(), vis.longitud() + 1);
                NodoAdy ady = n.getPrimerAdy();
                while (!exito && ady != null) {
                    if (vis.localizar(ady.getVertice().getElem()) < 0) {
                        exito = obtenerCaminoAux(ady.getVertice(), dest, res, vis);
                    }
                    ady = ady.getSigAdyacente();
                }
            }
        }
        if (exito)
            res.insertar(n.getElem(), 1);
        return exito;

    }

    public Lista obtenerEtiquetasCamino(Lista camino) {
        Lista etiquetas = new Lista();

        if (camino != null && camino.longitud() > 1) {
            for (int i = 1; i < camino.longitud(); i++) {
                Object ciudadActual = camino.recuperar(i);
                NodoVert nodoActual = ubicarVertice(ciudadActual);
                Object ciudadSiguiente = camino.recuperar(i + 1);

                if (nodoActual != null) {
                    NodoAdy ady = nodoActual.getPrimerAdy();
                    boolean encontrado = false;

                    while (ady != null && !encontrado) {
                        if (ady.getVertice().getElem().equals(ciudadSiguiente)) {
                            etiquetas.insertar(ady.getEtiqueta(), etiquetas.longitud() + 1);
                            encontrado = true;
                        }
                        ady = ady.getSigAdyacente();
                    }
                }
            }
        }
        return etiquetas;
    }

    public double obtenerMenorEtiqueta(Lista etiquetas) {
        double menor;
        if (!etiquetas.esVacia()) {
            menor = (double) etiquetas.recuperar(1);
            for (int i = 1; i <= etiquetas.longitud(); i++) {
                double valor = (double) etiquetas.recuperar(i);
                if (valor < menor) {
                    menor = valor;
                }
            }
        } else {
            menor = -1;
        }
        return menor;
    }

    /*
     * // Utilizado anteriormente para el ejer 4-1, eliminado por innecesario
     * public Lista obtenerPrimerActivo(Object origen, Map<ClaveTuberia,
     * DatosTuberia> tuberias) {
     * Lista visitados = new Lista();
     * Lista caminoActual = new Lista();
     * Lista caminoFinal = new Lista();
     * NodoVert nodoOrigen = ubicarVertice(origen);
     * boolean encontrado = false;
     * 
     * if (nodoOrigen != null) {
     * encontrado = obtenerActivoAux(
     * nodoOrigen, tuberias, visitados, caminoActual, caminoFinal);
     * }
     * 
     * if (!encontrado) {
     * caminoFinal = null;
     * }
     * 
     * return caminoFinal;
     * }
     * 
     * private boolean obtenerActivoAux(NodoVert actual,
     * Map<ClaveTuberia, DatosTuberia> tuberias,
     * Lista visitados,
     * Lista caminoActual,
     * Lista caminoFinal) {
     * // Marcar como visitado y agregar al camino
     * Object elemActual = actual.getElem();
     * visitados.insertar(elemActual, visitados.longitud() + 1);
     * caminoActual.insertar(elemActual, caminoActual.longitud() + 1);
     * boolean exito = false;
     * boolean encontrado = false;
     * 
     * boolean tieneVecinoActivo = false;
     * 
     * NodoAdy vecino = actual.getPrimerAdy();
     * while (vecino != null && !encontrado) {
     * Object elemVecino = vecino.getVertice().getElem();
     * 
     * if (visitados.localizar(elemVecino) == -1) {
     * tieneVecinoActivo = true;
     * // Ir recursivamente por el primer vecino activo
     * encontrado = obtenerActivoAux(
     * vecino.getVertice(), tuberias, visitados, caminoActual, caminoFinal);
     * if (encontrado) {
     * exito = true; // ya se encontró el primer camino activo completo
     * }
     * }
     * 
     * vecino = vecino.getSigAdyacente();
     * }
     * 
     * if (!tieneVecinoActivo) {
     * // Este es un camino completo (no hay más vecinos activos no visitados)
     * // Copiar caminoActual a caminoFinal
     * for (int i = 1; i <= caminoActual.longitud(); i++) {
     * caminoFinal.insertar(caminoActual.recuperar(i), caminoFinal.longitud() + 1);
     * }
     * exito = true;
     * } else {
     * caminoActual.eliminar(caminoActual.longitud());
     * }
     * return exito;
     * }
     */

    // Metodo para testing
    public Lista obtenerTodosCaminos(Object origen, Object destino) {
        Lista todosLosCaminos = new Lista();
        Lista caminoAct = new Lista();

        NodoVert verOrigen = ubicarVertice(origen);
        NodoVert verDestino = ubicarVertice(destino);

        if (verOrigen != null & verDestino != null) {
            obtenerTodosCaminosAux(verOrigen, destino, caminoAct, todosLosCaminos);
        }

        return todosLosCaminos;
    }

    private void obtenerTodosCaminosAux(NodoVert actual, Object destino, Lista caminoActual, Lista todosLosCaminos) {
        caminoActual.insertar(actual.getElem(), caminoActual.longitud() + 1); // Se agrega en el que estoy

        // Si llegue al destino, agrego un clon de la lista a todos los caminos.
        if (actual.getElem().equals(destino)) {
            todosLosCaminos.insertar(caminoActual.clone(), todosLosCaminos.longitud() + 1);
        } else {
            // Si no, sigo recorriendo.
            NodoAdy ady = actual.getPrimerAdy();
            while (ady != null) {
                Object vecino = ady.getVertice().getElem();

                // si vecino no fue visitado
                if (caminoActual.localizar(vecino) == -1) {
                    obtenerTodosCaminosAux(ady.getVertice(), destino, caminoActual, todosLosCaminos);
                }
                ady = ady.getSigAdyacente();
            }
        }
        caminoActual.eliminar(caminoActual.longitud());
    }

    public Lista obtenerCaminoEtiqMin(Object origen, Object destino) {
        // Lista resultado = new Lista();
        CaminoVol resultado = new CaminoVol();
        Lista caminoAct = new Lista();

        NodoVert verOrigen = ubicarVertice(origen);
        NodoVert verDestino = ubicarVertice(destino);

        if (verOrigen != null & verDestino != null) {
            oCEM(verOrigen, verDestino, caminoAct, resultado, -1, 0);
        }

        return resultado.getCamino();
    }

    // retorna vol minima de etiquetas del camino
    private double oCEM(NodoVert actual, NodoVert destino, Lista caminoActual, CaminoVol resultado, double volMin,
            double volActual) {
        caminoActual.insertar(actual.getElem(), caminoActual.longitud() + 1); // Se agrega en el que estoy
        // IO.sout("Actual: " + actual.getElem() + ".O==D? "
        // + actual.getElem().equals(destino.getElem()));

        // Si llegue al destino
        if (actual.getElem().equals(destino.getElem())) {
            // volMin == -1: primer camino encontrado
            // IO.sout(volActual + ", " + volMin);
            if (volMin == -1 || volActual < volMin) {
                // IO.sout("Actualizar resultado");
                IO.sout("Parcial: " + caminoActual + ". Vol: " + volActual);
                resultado.setCamino(caminoActual.clone());
                resultado.setVol(volActual);
                volMin = volActual;
            }
        } else {
            // Si no, sigo recorriendo.
            NodoAdy ady = actual.getPrimerAdy();
            while (ady != null) {
                Object vecino = ady.getVertice().getElem();
                // IO.sout("\tVecino: " + vecino);

                // si vecino no fue visitado
                // if (vecino != null?? && caminoActual.localizar(vecino) == -1) {
                if (caminoActual.localizar(vecino) == -1) {
                    double valorEtiq = ady.getEtiqueta();
                    // Mantener etiqueta de menor valor
                    if ((volActual > 0) && volActual < valorEtiq) {
                        valorEtiq = volActual;
                    }
                    volMin = oCEM(ady.getVertice(), destino, caminoActual, resultado, volMin,
                            valorEtiq);
                }
                ady = ady.getSigAdyacente();
            }
        }
        caminoActual.eliminar(caminoActual.longitud());
        return volMin;
    }

}
