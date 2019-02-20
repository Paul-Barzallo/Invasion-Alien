package Dibujos;


import TDAs.Constante;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Luis
 */
public class Escudo {
    private double xi;
    private double xf;
    private double yi;
    private double yf;
    private final double factor;
    private final Group escudo ;
    
    /**
     * Constructor del Escudo, se envian como parametros los X y Y, que 
     * van a depender del tamaño de pantalla 
     * @param x
     * @param y
     * @param fact
     */
    public Escudo(double x, double y, double fact){
        this.factor = 2*fact;
        escudo = new Group();
        Rectangle rec;
        for(int i=0; i<4; i++){
            rec =   new Rectangle(x+factor*(3+i), y+factor*0, factor*1,  factor*1);
            escudo.getChildren().add(rec);
        }
        for(int i=0; i<8; i++){
            rec =   new Rectangle(x+factor*(1+i), y+factor*1, factor*1,  factor*1);
            escudo.getChildren().add(rec);
        }
        for(int j=0; j<2; j++){
            for(int i=0; i<10; i++){
                rec =   new Rectangle(x+factor*(i), y+factor*(2+j), factor*1,  factor*1);
                escudo.getChildren().add(rec);
            }
        }
        
        escudo.getChildren().stream().map((nodo) -> (Rectangle) nodo).forEach((r) -> {
            r.setFill(Constante.COLOR_ESCUDO);
        });
    }
    
    public Group getEscudo(){
        return escudo;
    }

    public boolean DeterminarPosicionRango(double posicionBalaX , double posicionXBalaMax ,double posicionBalaY, Pane pane, Group bala){
        boolean eliminar = false;
        for(int i=0; i<getEscudo().getChildren().size(); i++){
            Rectangle parte = (Rectangle)getEscudo().getChildren().get(i);
            if((posicionXBalaMax >= parte.getBoundsInParent().getMinX()) && (posicionBalaX <= parte.getBoundsInParent().getMaxX() ) ) {
                if ((posicionBalaY >= parte.getBoundsInParent().getMinY()) && (  posicionBalaY <= parte.getBoundsInParent().getMaxY())){
                    getEscudo().getChildren().remove(i);
                    pane.getChildren().remove(bala);
                    eliminar = true;
                    return true;
                }   
            }      
        }
        return false ; 
    }
    
}
