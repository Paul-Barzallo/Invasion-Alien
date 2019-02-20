/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TDAs;

import java.io.Serializable;

/**
 *
 * @author Luis
 */
public class Jugador implements Serializable{
    private String nombre ; 
    private int puntaje ; 
    private int vidas = 3 ;
    
    public Jugador(String nombre, int puntaje) {
        this.nombre = nombre;
        this.puntaje = puntaje;
    }

    public Jugador() {
        
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }
    
    public int getVidas(){
        return vidas; 
    }
    
    public void setVidas(int vidas){
        this.vidas = vidas;
    }
    
    public void restarVida(){
        vidas-=1;
    }
    public void sumVida(){
        vidas = vidas+1;
    }
    
   public void calcularPuntos(){
        this.puntaje += Constante.PUNTOSXALIEN;
        
    }
}

