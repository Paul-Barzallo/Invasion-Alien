/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ventana;

import Dibujos.Flecha;
import Dibujos.Nave;
import TDAs.Constante;
import TDAs.Jugador;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Darnex
 */
public class Eleccion {
    private Nave nave;
    private TextField nombre ; 
    private Polygon trianguloIzq1;
    private Polygon trianguloIzq2;
    private Polygon trianguloDer1;
    private Polygon trianguloDer2;
    private Button iniciar ;
    private Button regresar;
    private String estiloBoton;
    private Jugador j1 ;
    private VBox eleccion;
    private Color[] colores;
    private String estiloColor1;
    private String estiloColor2;
    private int countC1 = 21;
    private int countC2 = 22;
    private AudioClip clip;
    private StackPane menu;
    private ImageView fondo; 
    
    
    public Eleccion(StackPane menu,ImageView fondo){
        this.menu=menu;
        this.fondo=fondo;
        estiloBoton = Constante.ESTILO_BOTON;
        colores = new Color[23];
        colores[0] = Color.rgb(122, 122, 122);
        colores[1] = Color.rgb(175, 174, 174);
        colores[2] = Color.rgb(216, 47, 47);
        colores[3] = Color.rgb(173, 30, 8);
        colores[4] = Color.rgb(137, 50, 22);
        colores[5] = Color.rgb(137, 70, 22);
        colores[6] = Color.rgb(219, 102, 0);
        colores[7] = Color.rgb(255, 132, 25);
        colores[8] = Color.rgb(255, 187, 40);
        colores[9] = Color.rgb(244, 199, 0);
        colores[10] = Color.rgb(208, 232, 0);
        colores[11] = Color.rgb(182, 255, 0);
        colores[12] = Color.rgb(10, 255, 59);
        colores[13] = Color.rgb(0, 232, 116);
        colores[14] = Color.rgb(59, 226, 207);
        colores[15] = Color.rgb(65, 146, 193);
        colores[16] = Color.rgb(36, 75, 165);
        colores[17] = Color.rgb(24, 29, 163);
        colores[18] = Color.rgb(87, 22, 191);
        colores[19] = Color.rgb(108, 14, 163);
        colores[19] = Color.rgb(108, 14, 163);
        colores[20] = Color.rgb(234, 229, 218);
        colores[21] = Constante.COLOR_NAVE_LADOS;
        colores[22] = Constante.COLOR_NAVE_CENTRO;
             
        //Sonido
        String clipURL = getClass().getResource("/Audio/beep.wav").toString();
        clip = new AudioClip(clipURL);
        
        //Nombre
        Text t1 = new Text("Nombre:");
        t1.setFont(Constante.LETRA_BOTON);
        t1.setFill(Constante.COLOR_TITULO);
        nombre = new TextField();
        nombre.setStyle(estiloBoton);
        nombre.setFont(Constante.LETRA_BOTON);
        nombre.setCenterShape(true);
        HBox hbNombre = new HBox(t1, nombre);
        hbNombre.setAlignment(Pos.CENTER);

        // Boton iniciar
        iniciar = new Button("Iniciar");
        iniciar.setOnAction(new Iniciar());
        iniciar.setFont(Constante.LETRA_BOTON);
        iniciar.setStyle(estiloBoton);
        iniciar.addEventHandler(MouseEvent.MOUSE_ENTERED, new TocarBoton(true));
        iniciar.addEventHandler(MouseEvent.MOUSE_EXITED, new TocarBoton(false));
        
        
        //Boton regresar
        regresar=new Button("Regresar");
        regresar.setOnAction(new RegresarMenu());
        regresar.setFont(Constante.LETRA_BOTON);
        regresar.setStyle(estiloBoton);
        regresar.addEventHandler(MouseEvent.MOUSE_ENTERED, new TocarBoton(true));
        regresar.addEventHandler(MouseEvent.MOUSE_EXITED, new TocarBoton(false));
        
        //Nave
        nave = new Nave (0,0, Constante.FACTOR*3, Constante.COLOR_NAVE_LADOS, Constante.COLOR_NAVE_CENTRO);
        
        //Color 1
        estiloColor1 = "rgb("+colores[countC1].getRed()*250+","+colores[countC1].getGreen()*250+","
                            +colores[countC1].getBlue()*250+")";
        Text t2 = new Text("Color 1:");
        t2.setFont(Constante.LETRA_BOTON);
        t2.setFill(Constante.COLOR_TITULO);
        trianguloIzq1 = new Flecha();
        trianguloIzq1.setOnMouseClicked(new CambiarColor1(true, true));
        trianguloDer1 = new Flecha();
        trianguloDer1.setRotate(180);
        trianguloDer1.setOnMouseClicked(new CambiarColor1(false, true));
        HBox hbColor1 = new HBox(t2, trianguloIzq1, trianguloDer1);
        hbColor1.setAlignment(Pos.CENTER);
        hbColor1.setSpacing(Constante.XPANTALLA/80);
        
        //Color 2
        estiloColor2 = "rgb("+colores[countC2].getRed()*250+","+colores[countC2].getGreen()*250+","
                            +colores[countC2].getBlue()*250+")";
        Text t3 = new Text("Color 2:");
        t3.setFont(Constante.LETRA_BOTON);
        t3.setFill(Constante.COLOR_TITULO);
        trianguloIzq2 = new Flecha();
        trianguloIzq2.setOnMouseClicked(new CambiarColor1(true, false));
        trianguloDer2 = new Flecha();
        trianguloDer2.setRotate(180);
        trianguloDer2.setOnMouseClicked(new CambiarColor1(false, false));
        HBox hbColor2 = new HBox(t3, trianguloIzq2, trianguloDer2);
        hbColor2.setAlignment(Pos.CENTER);
        hbColor2.setSpacing(Constante.XPANTALLA/80);
        
        //Union
        eleccion = new VBox(hbNombre, nave.getNave(), hbColor1, hbColor2, iniciar, regresar);
        eleccion.setAlignment(Pos.CENTER);
        eleccion.setSpacing(Constante.XPANTALLA/30);
        menu.getChildren().clear();
        menu.getChildren().addAll(fondo, eleccion);
        
    }

    public VBox getEleccion() {
        return eleccion;
    }
    
    /**
     * Clase privada interna que permite regresar al menú al presionr un botón
     */
    private class RegresarMenu implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent event) {
            menu.getChildren().clear();
            Menu m=new Menu(fondo);
            menu.getChildren().add(m.getMenu());
        }
        
    }
    
    private class TocarBoton implements EventHandler<MouseEvent> {
        private DropShadow sombra;
        
        public TocarBoton(boolean entrar){
            if(entrar)
                sombra = Constante.SOMBRA_BOTONES;
            else
                sombra = null;
        }
        @Override
        public void handle(MouseEvent e) {
            ((Button) e.getSource()).setEffect(sombra);
        }
    }    
    
    private  class Iniciar implements  EventHandler<ActionEvent> {
        private boolean bandera = true ;
        @Override
        public void handle(ActionEvent event) {
            clip.play();
            j1 = new Jugador(); 
            j1.setNombre(nombre.getText());
            
            while(bandera){
              if(!nombre.getText().equals("")){
                    Juego juegoC = new Juego(eleccion.getScene(), fondo, j1, 1, 500, 50*Constante.FACTOR);
                    Node fond = eleccion.getScene().getRoot().getChildrenUnmodifiable().get(0);
                    StackPane juego = new StackPane(fond,juegoC.getRoot());
                    eleccion.getScene().setRoot(juego); 
                    bandera = false ;
                }else
                  break;
           }
            
        }
    }
    
    private class CambiarColor1 implements EventHandler<MouseEvent> {
        private boolean izq;
        private boolean bc1;
        
        public CambiarColor1(boolean izq, boolean bc1){
            this.izq = izq;
            this.bc1 = bc1;
        }
        
        @Override
        public void handle(MouseEvent event) {
            clip.play();
            if(bc1){
                if(countC1>21 && !izq)
                    countC1=0;
                else if(countC1<1 && izq)
                    countC1 = 22;
                else if(!izq)
                    countC1++;
                else if(izq)
                    countC1--;
                nave.setC1(colores[countC1]);
                Constante.COLOR_NAVE_LADOS = colores[countC1];
            }
            else{
                if(countC2>21 && !izq)
                    countC2=0;
                else if(countC2<1 && izq)
                    countC2 = 22;
                else if(!izq)
                    countC2++;
                else if(izq)
                    countC2--;
                nave.setC2(colores[countC2]);
                Constante.COLOR_NAVE_CENTRO = colores[countC2];
            }
        }
    }
}
