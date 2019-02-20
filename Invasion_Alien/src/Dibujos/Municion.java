/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dibujos;

import TDAs.Constante;
import java.util.ArrayList;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * Clase que contiene las municiones usadas en la partida.
 * @author Darnex
 * @author JoseAndresEscobar19
 */
public class Municion implements Runnable{
    private final Nave nave;
    private final Pane pane;
    private final Group bala;
    double posicionXBala ; 
    double posicionXBalaMax ;
    double posicionYBala ; 
    private int arriba = 0;
    private final ArrayList<Alien> arrayNaves ;
    private final ArrayList<Escudo> arrayEscudos ;
    private final Pane aliens;
    private final Pane escudos;
    private boolean estado;
    private boolean destruido;
    private final Text puntos;
    private final HBox vidas;
    private final double factor;
    private final int opacidad = 25;
    private final int movY;
    private boolean stop;
    private final AudioClip explosion;
    
    /*
     * Constructor de las municiones que van a iniciarse. 
     * dependiendo de la posicion de la nave. 
     * @param x Entero de la posicion en x.
     * @param y Entero de la posicion en y.
     * @param factor Entero para el ajuste en pantalla.
     * @param pane Panel que contiene la municion.  
     */
    public Municion(double x, double y, Pane pane, ArrayList<Alien> arrAlien, ArrayList<Escudo> arrayEscudos, Pane aliens, Pane escudos, HUD hud, Nave nave){
        this.nave=nave;
        this.estado=true;
        this.destruido=false;
        this.posicionXBala = x ; 
        this.posicionYBala = y ; 
        this.bala=new Group();
        this.pane=pane;
        this.arrayNaves = arrAlien ;
        this.arrayEscudos = arrayEscudos;
        this.aliens = aliens  ;
        this.escudos = escudos;
        this.puntos = hud.getPuntos();
        this.vidas = hud.getVidas();
        this.factor = Constante.FACTOR;
        this.bala.getChildren().add(crearBala(x, y, factor));
        this.movY=-1;
        
        String explosionURL = getClass().getResource("/Audio/explosión.wav").toString();
        explosion = new AudioClip(explosionURL);
    }
    
    public Path crearBala(double x, double y, double factor){
        Circle c1=new Circle(x+factor*5, y+factor*5, 2);
        Rectangle r2=new Rectangle(x+factor*4.4, y+factor*5, 4, 30 );
        Path bal=new Path();
        bal=(Path) Path.union(c1, r2);
        int r = (int) (Math.random() * 256);
        int g = (int) (Math.random() * 256);
        int b = (int) (Math.random() * 256);
        bal.setFill(Color.rgb(r,g,b));
        bal.setEffect(new DropShadow(BlurType.ONE_PASS_BOX,Color.rgb(r, g, b),10,0.1,0,0));
        return bal;
    }
    
    
    /**
     * Método para obtener la bala.
     * @return Group que conforma la bala.
     */
    public Group getBala() {
        return bala;
    }

    @Override
    public void run() {
        stop=true;
        while (estado){
            Platform.runLater(() -> {
                bala.setTranslateY(arriba);
                posicionYBala = bala.getBoundsInParent().getMinY() ;
                posicionXBala = bala.getBoundsInParent().getMinX() ;
                posicionXBalaMax = bala.getBoundsInParent().getMaxX();
                arriba += movY;
                //eliminar alien
                for (int i = arrayNaves.size()-1; i  >= 0; i--) {
                    if(arrayNaves.get(i).DeterminarPosicionRango(posicionXBala,posicionXBalaMax, posicionYBala)&& stop){
                        explosion.play();
                        pane.getChildren().remove(bala);
                        arrayNaves.get(i).restarVidas(stop);
                        arrayNaves.get(i).getAlien().setOpacity(3*arrayNaves.get(i).getAlien().getOpacity()/4);
                        stop=false;
                        if(arrayNaves.get(i).getVidas() == 0){
                            puntos.setText(""+(Integer.parseInt(puntos.getText())+1000));
                            if(Integer.parseInt(puntos.getText())%10000==0){
                                vidas.getChildren().add(new Nave(0.0,0.0,(factor*0.6), Constante.COLOR_VIDA, Constante.COLOR_VIDA).getNave());
                            }
                            arrayNaves.remove(i);
                            aliens.getChildren().remove(i);
                            destruido = true;
                        }
                        estado = false;
                        break;
                    }
                }
                //eliminar escudo
                for (int i = arrayEscudos.size()-1; i  >= 0; i--) {
                    if(arrayEscudos.get(i).DeterminarPosicionRango(posicionXBala,posicionXBalaMax, posicionYBala, pane, bala)){
                        explosion.play();
                        destruido = true;
                        estado = false;
                        break;
                    }
                }
                //eliminar bala cuando llega a la parte superior
                if (posicionYBala <= 0 ){
                    pane.getChildren().remove(bala);
                    estado = false;
                }
            });
            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {}   
        }
   }
    
    public double getPosicionXBala() {
        return posicionXBala;
    }

    public void setPosicionXBala(double posicionXBala) {
        this.posicionXBala = posicionXBala;
    }

    public double getPosicionYBala() {
        return posicionYBala;
    }

    public void setPosicionYBala(double posicionYBala) {
        this.posicionYBala = posicionYBala;
    }
    public boolean getDestruido(){
        return this.destruido ;
    }
     public Text getPuntos() {
        return puntos;
    }
}
