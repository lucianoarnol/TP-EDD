package agua;
import java.util.HashMap;
public class Tuberias {
    private double caudMin;
    private double caudMax;
    private double diametro;
    private char estado;

    //constructores
    public Tuberias(double caudalMin, double caudalMax, double diametros, char estados) {
        caudMin = caudalMin;
        caudMax = caudalMax;
        diametro = diametros;
        estado = estados;
    }

    //observadores
    public double getCaudMin() {
        return caudMin;
    }

    public double getCaudMax() {
        return caudMax;
    }

    public double getDiametro() {
        return diametro;
    }

    public char getEstado() {
        return estado;
    }


    //modificadores
    public void setCaudalMin(double caudalMin) {
        caudMin = caudalMin;
    }

    public void setCaudalMax(double caudalMax) {
        caudMax = caudalMax;
    }

    public void setDiametro(double metros) {
        diametro = metros;
    }

    public void setEstado(char est) {
        estado = est;
    }

    //Propios del tipo
}   
