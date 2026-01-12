package conjuntistas;

public class HeapMin {
    private Comparable[] Heap;
    private int TAMANIO = 21;
    private int ultimo  = 0;


    public HeapMin(){
        this.Heap = new Comparable[TAMANIO];
        this.ultimo = 0;
    }

    public HeapMin(int tamanio){
        this.Heap = new Comparable[tamanio + 1]; // +1 para el 0 que no se usa
        this.ultimo = 0;
    }

    public boolean insertar(Comparable n) {
        boolean resp = false;
        int i = 2;
        Comparable padre;

        if (Heap[1] == null) {
            Heap[1] = n;
            resp = true;
            ultimo = 1;
        } else {
            if (ultimo < TAMANIO) {
                resp = true;
                ultimo++;
                Heap[ultimo] = n;
                i = ultimo;
            }

            if (resp == true) {
                i--;
                padre = Heap[i / 2];
                if (i > ultimo) {
                    ultimo = i;
                }

                while (padre != null && n.compareTo(padre) < 0) {
                    Heap[i/2] = n;
                    Heap[i] = padre;
                    i = i/2;
                    padre = Heap[i/2];
                }
            }
        }

        return resp;
    }

    public boolean eliminarCima() {
        boolean resp = false, corte = true;
        int posHijoMenor = -1, i = 1;
        Comparable hijoDerecho, hijoIzquierdo, aux;
        if (ultimo != 0) {
            //Intercambiamos hoja y raiz
            Heap[1] = Heap[ultimo];
            Heap[ultimo] = null;
            ultimo--;
            resp = true;
            
            hijoIzquierdo = Heap[2];
            hijoDerecho = Heap[3];

            do {
                if (hijoDerecho == null) {
                    if (hijoIzquierdo == null) {
                        corte = true;
                    } else {
                        posHijoMenor = i * 2;
                        corte = (Heap[i].compareTo(Heap[posHijoMenor]) <= 0);
                    }
                } else {
                    if (hijoIzquierdo.compareTo(hijoDerecho) < 0) {
                        posHijoMenor = i * 2;
                        corte = (Heap[i].compareTo(Heap[posHijoMenor]) <= 0);
                    } else {
                        posHijoMenor = i * 2 + 1;
                        corte = (Heap[i].compareTo(Heap[posHijoMenor]) <= 0);
                    }
                }

                if (!corte) {
                    aux = Heap[posHijoMenor];
                    Heap[posHijoMenor] = Heap[i];
                    Heap[i] = aux;
                    i = posHijoMenor;

                    if (i * 2 + 1 <= ultimo) {
                        hijoIzquierdo = Heap[i * 2];
                        hijoDerecho = Heap[i * 2 + 1];
                    } else {
                        corte = true;
                    }
                }
            } while (!corte);
        }

        return resp;
    }

    public Comparable obtenerCima() {
        return Heap[1];
    }

    public boolean esVacio() {
        return ultimo == 0;
    }

    public void vaciar() {
        for (int i = 1; i <= ultimo; i++) {
            Heap[i] = null;
        }

        ultimo = 0;
    }

    public HeapMin clone() {
        HeapMin newArbol = new HeapMin();
        newArbol.ultimo = this.ultimo;
        newArbol.Heap = this.Heap.clone();

        return newArbol;
    }

    public String toString() {
        String text = "vacio";
        int i = 1;

        if (ultimo != 0) {
            text = "";
            while (i <= ultimo) {
                text = text + "\n" + "Pos " + i + ": " + Heap[i];
                i++;
            }
        }

        return text;
    }
}