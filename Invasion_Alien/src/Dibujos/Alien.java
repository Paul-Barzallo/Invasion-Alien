/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dibujos;

import javafx.scene.Group;
import javafx.scene.shape.Rectangle;

/**
 * Clase padre que va a heredar a los demas aliens, todos los aliens se mueven igual en cada nivel
 * @author Darnex
 */
public class Alien{
    protected Group alien ;
    protected double posicionX ; 
    protected double posicionY ; 
    private double factor;
    private int bajar = 0;
    private int vidas;
    private Rectangle r1=new Rectangle(this.getPosicionX(),this.getPosicionY(),5, 10);
    
    
    /**
     * Constructor de la clase Alien
     * @param factor Double necesario para el diseño de las naves hijas
     */
    public Alien(double factor){
        this.factor = factor;
    }
    
    /**
     * 
     * @return 
     */
    public Group getAlien(){
        return alien;
    }

    /**
     * 
     * @return 
     */
    public double getPosicionX() {
        return posicionX;
    }

    /**
     * 
     * @param posicionX 
     */
    public void setPosicionX(double posicionX) {
        this.posicionX = posicionX;
    }

    /**
     * 
     * @return 
     */
    public double getPosicionY() {
        return posicionY;
    }

    
    /**
     * 
     * @param posicionY 
     */
    public void setPosicionY(double posicionY) {
        this.posicionY = posicionY;
    }

    /**
     * Método para obtener las vidas de un alien
     * @return Entero de las vidas de un alien
     */
    public int getVidas() {
        return vidas;
    }

    /**
     * Método para setear las vidas del alien
     * @param vidas Entero de las vidas deseadas.
     */
    public void setVidas(int vidas) {
        this.vidas = vidas;
    }
    
    /**
     * Método para retar vidas al alien
     * @param stop Boolean para el control de sustracción de vidas
     */
    public void restarVidas(boolean stop){
        if (stop)
            this.vidas = this.vidas-1 ;
    }
    
    /**
     * Método usado para elimiar de un impacto al alien (misiles)
     */
    public void eliminar(){
        this.vidas=0;
    }
    
    /**
     * Método usado para determinar si un disparo esta dentro de los bordes de un alien.
     * @param posicionBalaX Double de la posicion inicial en X de la bala
     * @param posicionXBalaMax Double de la posicion final en X de la bala
     * @param posicionBalaY Double de la posicion inicial en Y de la bala
     * @return true si esta dentro del margen, caso contrario false
     */
    public boolean DeterminarPosicionRango(double posicionBalaX , double posicionXBalaMax ,double posicionBalaY){        
        if((posicionXBalaMax >= getAlien().getBoundsInParent().getMinX()) && (posicionBalaX <= getAlien().getBoundsInParent().getMaxX() ) ) {
            if ((posicionBalaY >= getAlien().getBoundsInParent().getMinY()) && (  posicionBalaY <= getAlien().getBoundsInParent().getMaxY())){
                    return true;
            }
        }
        return false ; 
    }

}
