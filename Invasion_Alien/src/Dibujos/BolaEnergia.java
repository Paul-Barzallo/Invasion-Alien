/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dibujos;

import java.util.ArrayList;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

/**
 *
 * @author Luis
 */
public class BolaEnergia implements Runnable{
    private Group bolaEnergia ;
    private Pane pane;
    private double posicionXBola ; 
    private double posicionYBola ; 
    private int arriba = -30;
    private ArrayList<Alien> arrAlien ;
    private ArrayList<Escudo> arrayEscudos ;
    private Pane aliens;
    private boolean estado; 
    private boolean destruido=false;
    private int rotacion = 20 ;
    private double posicionXBalaMax;
    private Text puntos;
    private AudioClip explosion;
    
    /*
     * Constructor de las municiones que van a iniciarse. 
     * dependiendo de la posicion de la nave. 
     * @param x Entero de la posicion en x.
     * @param y Entero de la posicion en y.
     * @param factor Entero para el ajuste en pantalla.
     * @param pane Panel que contiene la municion.  
     */
    public BolaEnergia(double x, double y, double factor, Pane pane,ArrayList<Alien> arrAlien, ArrayList<Escudo> arrayEscudos, Pane aliens, HUD hud){
        
        this.puntos=hud.getPuntos();
        estado=true;
        this.posicionXBola = x ; 
        this.posicionYBola = y ; 
        this.pane=pane;
        this.arrAlien = arrAlien ;
        this.arrayEscudos = arrayEscudos;
        this.aliens = aliens;
        bolaEnergia = new Group();
        
        //Bola de energia
        Circle c = new Circle(x+factor*5,y+factor*5,factor*4);
        c.setFill(Color.rgb(43, 102, 239));
        //c.setEffect(new DropShadow(20, Color.rgb(204, 246, 252)));
        
        //DropShadow somb = new DropShadow(10,Color.rgb(195, 215, 229));
        Color coloLineas = Color.CYAN;
        Line l1 = new Line(x+factor*5,y+factor*5, x+factor*6, y+factor*3);
        l1.setStroke(coloLineas);
        //l1.setEffect(somb);
        Line l2 = new Line(x+factor*6,y+factor*3, x+factor*6, y+factor*1);
        l2.setStroke(coloLineas);
        //l2.setEffect(somb);
        Line l3 = new Line(x+factor*5,y+factor*5,x+factor*9.5, y+factor*6.5);
        l3.setStroke(coloLineas);
        //l3.setEffect(somb);
        Line l4 = new Line(x+factor*5,y+factor*5,x+factor*5, y+factor*7);
        l4.setStroke(coloLineas);
        //l4.setEffect(somb);
        Line l5 = new Line(x+factor*5,y+factor*7,x+factor*6.5, y+factor*9.5);
        l5.setStroke(coloLineas);
        //l5.setEffect(somb);
        Line l6 = new Line(x+factor*3,y+factor*4,x+factor*1, y+factor*3);
        l6.setStroke(coloLineas);
        //l6.setEffect(somb);
        Line l7 = new Line(x+factor*5,y+factor*5,x+factor*3, y+factor*4);
        l7.setStroke(coloLineas);
        //l7.setEffect(somb);
        Line l8 = new Line(x+factor*3,y+factor*4,x+factor*0.5, y+factor*4.5);
        l8.setStroke(coloLineas);
        //l8.setEffect(somb);
        Line l9 = new Line(x+factor*5,y+factor*5,x+factor*5, y+factor*0.5);
        l9.setStroke(coloLineas);
        //l9.setEffect(somb);
        Line l10 = new Line(x+factor*7,y+factor*5.5,x+factor*9, y+factor*3);
        l10.setStroke(coloLineas);
        //l10.setEffect(somb);
        Line l11 = new Line(x+factor*5,y+factor*5,x+factor*3, y+factor*6);
        l11.setStroke(coloLineas);
        //l11.setEffect(somb);
        Line l12 = new Line(x+factor*3,y+factor*6,x+factor*2, y+factor*8);
        l12.setStroke(coloLineas);
        //l12.setEffect(somb);
        
        bolaEnergia.getChildren().addAll(c,l1,l2,l3,l4,l5,l6,l7,l8,l9,l10,l11,l12);
        
        //sonido
        String explosionURL = getClass().getResource("/Audio/explosión.wav").toString();
        explosion = new AudioClip(explosionURL);
    }

    
    @Override
    public void run() {
        while (estado){
             Platform.runLater(new Runnable() {
                @Override
                public void run() { 
                    bolaEnergia.setTranslateY(arriba);
                    bolaEnergia.setRotate(rotacion);
                    rotacion+=3;
                    arriba -= 1;
                    posicionXBola = bolaEnergia.getBoundsInParent().getMinX();
                    posicionYBola = bolaEnergia.getBoundsInParent().getMinY();
                    posicionXBalaMax = bolaEnergia.getBoundsInParent().getMaxX();
                    //eliminar alien
                    if(!destruido){
                        for (int i = arrAlien.size()-1; i  >= 0; i--) { 
                            if(arrAlien.get(i).DeterminarPosicionRango(posicionXBola,posicionXBalaMax, posicionYBola)){  
                                pane.getChildren().remove(bolaEnergia);
                                arrAlien.get(i).eliminar();
                                explosion.play();
                                if(arrAlien.get(i).getVidas()==0){
                                    puntos.setText(""+(Integer.parseInt(puntos.getText())+1000));
                                    arrAlien.remove(i);
                                    aliens.getChildren().remove(i);
                                    destruido=true;
                                }
                                estado = false;
                                break;
                            }  
                        }
                    }
                    //eliminar escudo
                    for (int i = arrayEscudos.size()-1; i  >= 0; i--) {
                        if(arrayEscudos.get(i).DeterminarPosicionRango(posicionXBola,posicionXBalaMax, posicionYBola, pane, bolaEnergia)){
                            explosion.play();
                            destruido = true;
                            //estado = false;
                            break;
                        }
                    }
                    //eliminar bola
                    if (posicionYBola < 0 ){
                        pane.getChildren().remove(bolaEnergia);
                        estado = false;   
                    }
                }
            });
                   
                try { 
                   Thread.sleep(2);
                } catch (InterruptedException ex) {
                   
                }
           
        }
    }
    public Group getBolaEnergia() {
        return bolaEnergia;
    }

    public void setBolaEnergia(Group bolaEnergia) {
        this.bolaEnergia = bolaEnergia;
    }

    public ArrayList<Alien> getArrAlien() {
        return arrAlien;
    }

    public void setArrAlien(ArrayList<Alien> arrAlien) {
        this.arrAlien = arrAlien;
    }

    public Pane getAliens() {
        return aliens;
    }

    public void setAliens(Pane aliens) {
        this.aliens = aliens;
    }
}
