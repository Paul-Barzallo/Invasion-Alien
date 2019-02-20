package Dibujos;


import TDAs.Constante;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Darnex
 */
public class AlienRojo extends Alien{
    
    /**
     * Constructor del Alien rojo, se envian como parametros los X y Y, que 
     * van a depender del tamaño de pantalla 
     * @param x
     * @param y
     * @param factor 
     */
    public AlienRojo(double x, double y, double factor){
        super(factor);
        this.posicionX = x ; 
        this.posicionY = y ;
        alien = new Group();
        Rectangle rec =    new Rectangle(x+factor*0,  y+factor*2, factor*1, factor*3);
        Rectangle rec1 =   new Rectangle(x+factor*1,  y+factor*1, factor*2, factor*6);
        Rectangle rec2 =   new Rectangle(x+factor*2,  y+factor*7, factor*2, factor*1);
        Rectangle rec3 =   new Rectangle(x+factor*3,  y+factor*1, factor*6, factor*2);
        Rectangle rec4 =   new Rectangle(x+factor*3,  y+factor*4, factor*6, factor*2);
        Rectangle rec5 =   new Rectangle(x+factor*4,  y+factor*0, factor*4, factor*1);
        Rectangle rec6 =   new Rectangle(x+factor*5,  y+factor*3, factor*2, factor*1);
        Rectangle rec7 =   new Rectangle(x+factor*5,  y+factor*6, factor*2, factor*1);
        Rectangle rec8 =   new Rectangle(x+factor*8,  y+factor*7, factor*2, factor*1);
        Rectangle rec9 =   new Rectangle(x+factor*9,  y+factor*1, factor*2, factor*6);
        Rectangle rec10 =  new Rectangle(x+factor*11, y+factor*2, factor*1, factor*3);
        alien.getChildren().addAll(rec,rec1,rec2,rec3,rec4,rec5,rec6,rec7,rec8,rec9,rec10);
        for(Node nodo: alien.getChildren()){
            Rectangle r = (Rectangle) nodo;
            r.setFill(Constante.COLOR_ALIEN2);
        }
//        moverAliens();
   }

    

}
