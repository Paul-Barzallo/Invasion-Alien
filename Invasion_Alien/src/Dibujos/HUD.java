/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dibujos;

import TDAs.Constante;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 *
 * @author Darnex
 */
public class HUD {
    private final Text tVidas;
    private final HBox vidas;
    private final HBox hbVidas;
    private final Text tMisiles;
    private final Text misiles;
    private final HBox hbMisiles;
    private Text tPuntos;
    private Text puntos;
    private HBox hbPuntos;
    private Text tNivel;
    private Text nivel;
    private HBox hbNivel;
    
    
    private final Pane hud;
    
    public HUD(int nVidas, int nPuntos, int nNivel){
        //VIDAS
        tVidas = new Text("VIDAS:");
        tVidas.setFont(Constante.LETRA_HUD);
        tVidas.setFill(Constante.COLOR_HUD);
        
        vidas = new HBox();
        for(int i=0; i<nVidas; i++){
            Group vida = new Nave(0,0, (Constante.FACTOR*0.6), Constante.COLOR_VIDA, Constante.COLOR_VIDA).getNave();
            vidas.getChildren().add(vida);
        }
        vidas.setSpacing(Constante.FACTOR*1.5);
        vidas.setAlignment(Pos.CENTER);
        
        hbVidas = new HBox(tVidas, vidas);
        hbVidas.setSpacing(Constante.FACTOR*1.5);
        
        
        //MISILES
        tMisiles = new Text("MISILES:");
        tMisiles.setFont(Constante.LETRA_HUD);
        tMisiles.setFill(Constante.COLOR_HUD);
        
        misiles = new Text("10");
        misiles.setFont(Constante.LETRA_HUD);
        misiles.setFill(Constante.COLOR_VIDA);
        
        hbMisiles = new HBox(tMisiles, misiles);
        hbMisiles.setSpacing(Constante.FACTOR*1.5);
        
        
        //PUNTOS        
        tPuntos = new Text("PUNTOS:");
        tPuntos.setFont(Constante.LETRA_HUD);
        tPuntos.setFill(Constante.COLOR_HUD);
        
        puntos = new Text(""+nPuntos);
        puntos.setFont(Constante.LETRA_HUD);
        puntos.setFill(Constante.COLOR_VIDA);
        
        hbPuntos = new HBox(tPuntos, puntos);
        hbPuntos.setSpacing(Constante.FACTOR*1.5);
        
        
        //NIVEL
        tNivel = new Text("NIVEL:");
        tNivel.setFont(Constante.LETRA_HUD);
        tNivel.setFill(Constante.COLOR_HUD);
        
        nivel = new Text(""+nNivel);
        nivel.setFont(Constante.LETRA_HUD);
        nivel.setFill(Constante.COLOR_VIDA);
        
        hbNivel = new HBox(tNivel, nivel);
        hbNivel.setSpacing(Constante.FACTOR*1.5);
        
        
        double posHudY = puntos.getFont().getSize();
        Rectangle linea = new Rectangle(Constante.XPANTALLA/14, posHudY+Constante.YPANTALLA/100
                , Constante.XPANTALLA-Constante.XPANTALLA/7, Constante.YPANTALLA/100);
        linea.setFill(Constante.COLOR_LINEA);
        
        hud = new Pane(hbVidas, hbMisiles, hbPuntos, hbNivel, linea);
        
        
        
        hbVidas.setLayoutX(30*Constante.FACTOR);
        hbVidas.setLayoutY(posHudY/10);
        
        hbMisiles.setLayoutX(160*Constante.FACTOR);
        hbMisiles.setLayoutY(posHudY/10);
        
        hbPuntos.setLayoutX(210*Constante.FACTOR);
        hbPuntos.setLayoutY(posHudY/10);
        
        hbNivel.setLayoutX(282*Constante.FACTOR);
        hbNivel.setLayoutY(posHudY/10);
    }

    public Pane getHud() {
        return hud;
    }

    public HBox getVidas() {
        return vidas;
    }

    public Text getMisiles() {
        return misiles;
    }

    public Text getPuntos() {
        return puntos;
    }

    public Text getNivel() {
        return nivel;
    }
    
    public void restarVidas(boolean stop){
        if (stop)
            vidas.getChildren().remove(0);
    }
    
    public void restarMisiles(){
        misiles.setText(""+(Integer.parseInt(misiles.getText())-1));
    }
    
    public void sumarPuntos(){
        puntos.setText(""+(Integer.parseInt(puntos.getText())+1000));
    }
    public void sumarPuntosNivelSuperado(){
        puntos.setText(""+(Integer.parseInt(puntos.getText())+500));
    }

}
