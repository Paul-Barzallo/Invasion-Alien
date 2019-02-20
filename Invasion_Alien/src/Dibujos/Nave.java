package Dibujos;


import TDAs.Constante;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Luis
 */
public class Nave {
    private Group nave ;
    private Rectangle recCentral ; 
    private double posX , posY ; 
    private Color c1;
    private Color c2;
    
    /**
     * Construvtor de la nave que recibe los valores para armar la nave con rectangulos
     * @param x
     * @param y
     * @param factor 
     * @param color1 
     * @param color2 
     */
    public Nave(double x, double y, double factor, Color color1, Color color2){
        this.posX = x ; 
        this.posY = y ;
        this.c1 = color1;
        this.c2 = color2;
        nave = new Group();
        Rectangle rec =   new Rectangle(x+factor*0, y+factor*5, factor*1, factor*3);
        Rectangle rec1 =  new Rectangle(x+factor*1, y+factor*4, factor*1, factor*5);
        Rectangle rec2 =  new Rectangle(x+factor*2, y+factor*2, factor*1, factor*8);
        Rectangle rec3 =  new Rectangle(x+factor*3, y+factor*0, factor*1, factor*10);
        recCentral  =  new Rectangle(x+factor*4, y+factor*5, factor*2, factor*5);
        Rectangle rec5 =  new Rectangle(x+factor*6, y+factor*0, factor*1, factor*10);
        Rectangle rec6 =  new Rectangle(x+factor*7, y+factor*2, factor*1, factor*8);
        Rectangle rec7 =  new Rectangle(x+factor*8, y+factor*4, factor*1, factor*5);
        Rectangle rec8 =  new Rectangle(x+factor*9, y+factor*5, factor*1, factor*3);
        nave.getChildren().addAll(rec,rec1,rec2,rec3,recCentral,rec5,rec6,rec7,rec8);
        for(Node nodo: nave.getChildren()){
            Rectangle r = (Rectangle) nodo;
            r.setFill(c1);
        }
        recCentral.setFill(c2);
      
    }
    /**
     * Devuelve un Group en donde esta dibujada la nave  
     * @return nave
     */
    public Group getNave(){
        return nave;
       
    }
    // Metodos set and get para las posiciones de la nave
    
    public double getPosX() {
        return posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }
   public double getTamanioNave (){
       double tamanio ; 
       tamanio = Constante.YPANTALLA-nave.getLayoutBounds().getMinY();
       return tamanio ;
    }

    public Color getC1() {
        return c1;
    }

    public void setC1(Color c1) {
        this.c1 = c1;
        nave.getChildren().stream().map((nodo) -> (Rectangle) nodo).forEach((r) -> {
            r.setFill(c1);
        });
        recCentral.setFill(c2);
    }

    public Color getC2() {
        return c2;
    }

    public void setC2(Color c2) {
        this.c2 = c2;
        recCentral.setFill(c2);
    }

    public boolean DeterminarPosicionRango(double posicionBalaX , double posicionXBalaMax ,double posicionBalaY, double mov){   
        if((posicionXBalaMax >= getNave().getBoundsInParent().getMinX()+mov) && (posicionBalaX <= getNave().getBoundsInParent().getMaxX()+mov) ) {
            if ((posicionBalaY >= getNave().getBoundsInParent().getMinY()) && (  posicionBalaY <= getNave().getBoundsInParent().getMaxY())){
                return true;
            }
        }
        return false ; 
    }
    
 }