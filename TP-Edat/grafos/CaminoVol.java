package grafos;

import lineales.dinamica.Lista;

public class CaminoVol {

    private Lista camino;
    private double vol;

    public CaminoVol() {
        this.vol = -1;
    }

    public Lista getCamino() {
        return camino;
    }

    public void setCamino(Lista camino) {
        this.camino = camino;
    }

    public double getVol() {
        return vol;
    }

    public void setVol(double vol) {
        this.vol = vol;
    }

}
