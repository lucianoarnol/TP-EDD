package agua;
import jerarquicas.ArbolAVL;
import lineales.dinamicas.*;

public class Ciudades {
    private String nombre;
    private String nomenclatura;
    private double superficie;
    private Lista habitantes;
    private double consuProm;
    private int numNomen = 3000;

    //constructores
    public Ciudades(String nom, double metro, Lista cantHabitantes, double consumo, int numero){
        nombre = nom;
        nomenclatura = obtenerNomen(nom);
        superficie = metro;
        habitantes = cantHabitantes;
        consuProm = consumo;
        numNomen = numero + 1;
        if (numNomen > 3999) { //reinicia el valor si supera el 3999
            numNomen = 3000; 
        }
    }

    public Ciudades(String nom){
        nombre = nom;
        nomenclatura = obtenerNomen(nom);
        superficie = 0.0;
        consuProm = 0.0;
        numNomen = (numNomen + 1) % 4000;
    }




    //Observadores
    public String getNombre() {
        return nombre;
    }

    public String getNomenclatura() {
        return nomenclatura;
    }

    public double getSuperficie() {
        return superficie;
    }

    public double getConsuProm() {
        return superficie;
    }


    //Modificadores
    public void setSuperficie(double metros){
        superficie = metros;
    }

    public void setConsumoProm(double consumo) {
        consuProm = consumo;
    }


    //Propios del tipo

    private String obtenerNomen(String nom){ 
        String nomen = "";
        if(nom.length() > 2){
            nomen = nom.substring(0, 2).toUpperCase() + numNomen;
        }
        return nomen;
    }



}
