/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dibujos;

import TDAs.Constante;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

/**
 *
 * @author Darnex
 */
public class Flecha extends Polygon{
    
    public Flecha(){
        this.getPoints().addAll(new Double[]{
            Constante.XPANTALLA/80, 0.0,
            0.0, Constante.XPANTALLA/80,
            Constante.XPANTALLA/80, Constante.XPANTALLA/40 });
        this.setFill(Color.CADETBLUE);
    }
}
