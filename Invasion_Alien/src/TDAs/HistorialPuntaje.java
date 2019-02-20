/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TDAs;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Luis
 */
public class HistorialPuntaje implements Serializable{
    private ArrayList<Jugador> arrJugador = new ArrayList<>() ;

    public HistorialPuntaje() {
    }

    public ArrayList<Jugador> getArrJugador() {
        return arrJugador;
    }

    public void setArrJugador(ArrayList<Jugador> arrJugador) {
        this.arrJugador = arrJugador;
    }
    public void ordenar(){
        arrJugador.sort(new Comparator<Jugador>() {
            @Override
            public int compare(Jugador j1, Jugador j2) {
                if(j1.getPuntaje() > j2.getPuntaje())
                    return -1;
                else 
                    return 1 ;
            }
        });
    }
    public static HistorialPuntaje cargarArchivo(){ 
        HistorialPuntaje Hpuntaje = new HistorialPuntaje();
        try {
                FileInputStream archivo = new FileInputStream ("puntajes.txt");
                ObjectInputStream objetoEntrada = new ObjectInputStream (archivo);

                 Hpuntaje = (HistorialPuntaje) objetoEntrada.readObject();
                 Hpuntaje.ordenar();
            } catch (FileNotFoundException ex) {
                System.out.println(ex.getMessage());
            } catch (ClassNotFoundException | IOException ex) {
                System.out.println(ex.getMessage());
            }
        return Hpuntaje ; 
    }
    public void escribirObjeto(){ 
        try {                   
                    FileOutputStream archivoSalida = new FileOutputStream ("puntajes.txt");
                    ObjectOutputStream objetoSalida = new ObjectOutputStream (archivoSalida);
                    objetoSalida.writeObject(this);
                    archivoSalida.flush();
                    archivoSalida.close();     
                } catch (IOException ex) {
                    
                }
    }
    public ObservableList<Jugador> getJugador (){
        ObservableList<Jugador> jugadores = FXCollections.observableArrayList();
        for (int i = 0; i < arrJugador.size(); i++) {
            jugadores.add(arrJugador.get(i));           
        }
        return jugadores ;
    }   
    public void escogerMejores (){
        for (int i = 0; i < arrJugador.size(); i++) {
            if(i >= 10)
                arrJugador.remove(i); 
        }  
    }
}
