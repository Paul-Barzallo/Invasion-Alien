/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ventana;

import TDAs.Constante;
import TDAs.HistorialPuntaje;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Random;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.text.Text;
import TDAs.Jugador;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 *
 * @author JoseAndresEscobar19
 */
public class Menu {
    private final StackPane menu;
    private final double x,y;
    private final String estiloBoton;
    private final ImageView fondo;
    private final VBox botones;
    private final Text titulo ;
    private HistorialPuntaje hPuntajes ; 
    private final Pane ventanaPuntajes ;
    private final VBox puntaje ; 
    private final AudioClip clip;
    private TableView<Jugador> tablaJugadores ;
    private int count = 0;
    private final Button jugar;
    private final Button instrucciones;
    private final Button opciones;
    private final Button salir;

    
    //private Juego juegoC;
    
    public Menu(ImageView fondo){
        this.x = Constante.XPANTALLA;
        this.y = Constante.YPANTALLA;
        
        //Sonido
        String clipURL = getClass().getResource("/Audio/beep.wav").toString();
        clip = new AudioClip(clipURL);
        
        this.fondo = fondo;
        
        //Tabla de puntajes        
        ventanaPuntajes = new Pane();
        puntaje = new VBox();
        /// 
        try {
                FileInputStream archivo = new FileInputStream ("puntajes.txt");
                ObjectInputStream objetoEntrada = new ObjectInputStream (archivo);

                 hPuntajes = (HistorialPuntaje) objetoEntrada.readObject();
                 hPuntajes.ordenar();
                 hPuntajes.escogerMejores();
            } catch (FileNotFoundException ex) {
                System.out.println(ex.getMessage());
            } catch (ClassNotFoundException | IOException ex) {
                System.out.println(ex.getMessage());
            }
        
        if(hPuntajes != null){
            for (int i = 0; i < hPuntajes.getArrJugador().size(); i++) {
                for (int j = 0; j < hPuntajes.getArrJugador().size(); j++) {
                    if (hPuntajes.getArrJugador().get(i).getNombre().equals(hPuntajes.getArrJugador().get(j).getNombre())) {
                        hPuntajes.getArrJugador().remove(i);
                    }

                }
            }
        }
        
        //
        estiloBoton = Constante.ESTILO_BOTON;
        
        // Boton de jugar
        jugar = new Button("Jugar");
        jugar.setFont(Constante.LETRA_BOTON);
        jugar.setStyle(estiloBoton);
        
        // botton de instrucciones 
        instrucciones = new Button("Puntajes");
        instrucciones.setFont(Constante.LETRA_BOTON);
        instrucciones.setStyle(estiloBoton);
        
        // boton de opciones 
        opciones = new Button("Instrucciones");
        opciones.setFont(Constante.LETRA_BOTON);
        opciones.setStyle(estiloBoton);
        
        //boton de salida
        salir = new Button("Salir");
        salir.setFont(Constante.LETRA_BOTON);
        salir.setStyle(estiloBoton);
        
        // titulo principal 
        titulo = new Text("INVASION ALIEN");
        titulo.setFont(Constante.LETRA_TITULO);
        titulo.setFill(Constante.COLOR_TITULO);
        titulo.setScaleX(0.1);
        titulo.setScaleY(0.1);

        jugar.setOpacity(0);
        instrucciones.setOpacity(0);
        opciones.setOpacity(0);
        salir.setOpacity(0);
        
        //Layout de inicio
        botones = new VBox(titulo, jugar, instrucciones, opciones, salir);
        botones.setAlignment(Pos.CENTER);
        botones.setSpacing(Constante.ESPACIO_BOTONES);
         
        menu = new StackPane(fondo, botones);
        
        Thread efectTitulo = new Thread(new EfectTitulo());
        efectTitulo.start();
    }

    
    /**
     * Devuelve el menu un stackPane con el fondo y los botones del inicio 
     * @return menu
     */
    public StackPane getMenu() {
        return menu;
    }

    
    /**
     * Clase privada que permite salir del juego cuando un botón es presionado.
     */
    private class SalirJuego implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent event) {
            System.exit(0);
        }
        
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
    
    /**
     * Clase privada que inicia creando un nuevo juego 
     */
    private class Jugar implements EventHandler<ActionEvent> {
       
        @Override
        public void handle(ActionEvent event) {
            clip.play();
            Eleccion elec = new Eleccion(menu, fondo);
            //StackPane stPane = new StackPane(fondo, elec.getEleccion());
            //menu.getScene().setRoot(stPane);
        }
    }
    /**
     * Clase privada que maneja los efectos de sombra sobre los botones 
     */
    private class TocarBoton implements EventHandler<MouseEvent> {
        private final DropShadow sombra;
        
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
    
    /**
     * Clase privada para mostar el puntaje de los mejores jugadores cuando el 
     * boton asignado es presionado
     */
    private class mostrarPuntajes implements EventHandler<ActionEvent> {
        Text titulo ; 
        @Override
        public void handle(ActionEvent event) { 
            TableColumn<Jugador , String> nombreCol = new TableColumn<>("Nombre");
            nombreCol.setMinWidth(200);
            nombreCol.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        
            TableColumn<Jugador , String> puntajeCol = new TableColumn<>("Puntaje");
            puntajeCol.setMinWidth(200);
            puntajeCol.setCellValueFactory(new PropertyValueFactory<>("puntaje"));
            titulo = new Text("MEJORES PUNTAJES");
            titulo.setFont(Font.font("Cooper Black", FontWeight.BOLD, Constante.XPANTALLA/50));
            titulo.setFill(Constante.COLOR_TITULO);
            tablaJugadores = new TableView<>();
            tablaJugadores.setItems(hPuntajes.getJugador());
            tablaJugadores.getColumns().addAll(nombreCol,puntajeCol);
            tablaJugadores.setOpacity(0.90);
            tablaJugadores.getStylesheets().add(getClass().getResource("CssTable.css").toExternalForm());
            puntaje.getChildren().addAll(titulo, tablaJugadores);      
            puntaje.setTranslateX(6*x/15);
            puntaje.setTranslateY(4*y/15);
            ventanaPuntajes.getChildren().add(puntaje);
            
            BorderPane pun=new BorderPane();
            HBox bot=new HBox();
            Button regresar=new Button("Regresar");
            regresar.setFont(Constante.LETRA_BOTON);
            regresar.setStyle(estiloBoton);
            regresar.addEventHandler(MouseEvent.MOUSE_ENTERED, new TocarBoton(true));
            regresar.addEventHandler(MouseEvent.MOUSE_EXITED, new TocarBoton(false));
            regresar.setOnAction(new RegresarMenu());
            bot.getChildren().add(regresar);
            bot.setAlignment(Pos.CENTER);
            bot.setPadding(new Insets(30));
            pun.setCenter(ventanaPuntajes);
            pun.setBottom(bot);
            menu.getChildren().clear();
            menu.getChildren().addAll(fondo, pun);
        }
    }
    
    
    /**
     * Clase privada usada para el efecto del titulo principal en el menú principal
     */
    private class EfectTitulo implements Runnable{

        @Override
        public void run() {
            for(int i=1; i<100; i++){
                Platform.runLater(() -> {
                    count++;
                    titulo.setScaleX(0.01+count*0.01);
                    titulo.setScaleY(0.01+count*0.01);
                    titulo.setRotate(274.5+4.5*count);
                });
                try {
                    Thread.sleep(20);
                } catch (InterruptedException ex) {}
            }
            Platform.runLater(() -> {
                jugar.setOpacity(1);
                instrucciones.setOpacity(1);
                opciones.setOpacity(1);
                salir.setOpacity(1);
        
                jugar.setOnAction(new Jugar());
                jugar.addEventHandler(MouseEvent.MOUSE_ENTERED, new TocarBoton(true));
                jugar.addEventHandler(MouseEvent.MOUSE_EXITED, new TocarBoton(false));
                instrucciones.setOnAction(new mostrarPuntajes());
                instrucciones.addEventHandler(MouseEvent.MOUSE_ENTERED, new TocarBoton(true));
                instrucciones.addEventHandler(MouseEvent.MOUSE_EXITED, new TocarBoton(false));
                
                opciones.addEventHandler(MouseEvent.MOUSE_ENTERED, new TocarBoton(true));
                opciones.addEventHandler(MouseEvent.MOUSE_EXITED, new TocarBoton(false));
                salir.setOnAction(new SalirJuego());
                salir.addEventHandler(MouseEvent.MOUSE_ENTERED, new TocarBoton(true));
                salir.addEventHandler(MouseEvent.MOUSE_EXITED, new TocarBoton(false));
            });
        }   
    }
}
