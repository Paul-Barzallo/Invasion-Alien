/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ventana;

import Dibujos.Alien;
import Dibujos.Escudo;
import Dibujos.AlienGris;
import Dibujos.AlienVerde;
import Dibujos.AlienRojo;
import Dibujos.BolaEnergia;
import Dibujos.HUD;
import Dibujos.Municion;
import Dibujos.Nave;
import TDAs.Constante;
import TDAs.HistorialPuntaje;
import TDAs.Jugador;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Random;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * @author Luis
 * @author JoseAndresEscobar19 
 * @author Darnexb
 */
public class Juego {
    //Atributos de los diferentes nodos que se emplean en la construccion del juego 
    private final StackPane root;
    private double factor;
    private double x,y;  
    private final Pane aliens;
    private final Pane escudos;
    private final Pane naveEspacial ;
    private final Pane disparos;
    private final HUD hud;
    private final Pane pHud;
    private Municion municion;
    private BolaEnergia bolaEnergia ; 
    private final Nave nave ;
    private final ArrayList<Alien> arrayAliens ; // array de aliens que se utiliza para cada nivel y para identificar cada instancia 
    private final ArrayList<Escudo> arrayEscudos ;// array de escudos que se utila en todo el juego 
    private int nMisiles;// numero de misiles que emplea el jugador
    private Jugador jugador ;
    private HistorialPuntaje puntajes;
    private double movimiento = 0;
    private long esperaDisparo = 0;
    private long esperaMisil = 0;
    
    private int nivel = 1;
    private int tiempo = 500;
    private int vel=Constante.VELOCIDAD_DISPAROALIENS;
    private int cant=Constante.CANTIDAD_DISPAROALIENS;
    private double disEscudos = 45*factor;
    // Atributos que controlan el movimiento del juego asi como la pausa y los niveles 
    private boolean rightPressed = false;
    private boolean leftPressed = false;
    private boolean spacePressed = false;
    private boolean mPressed = false;
    private boolean finDejuego=false;
    private boolean pause = false;
    private boolean pausar = false;
    
    private final String puntosMAX;
    // atributos del audio del juego 
    private final AudioClip laser;
    private final AudioClip bola;
    
    private Scene scene;
    private Thread tAliens;
    private Thread tDisparos;
    private Thread tMisiles;
    
    private ImageView font;
    /**
     * Constructor de un juego lo necesario para su ejecucion
     * @param scene
     * @param fondo
     * @param jugador
     * @param nivel
     * @param tiempo
     * @param disEscudos 
     */
    public Juego(Scene scene, ImageView fondo, Jugador jugador, int nivel, int tiempo, double disEscudos){
        this.font = fondo;
        this.scene = scene;
        this.nivel = nivel;
        this.tiempo = tiempo;
        this.disEscudos = disEscudos;
        this.jugador = jugador;
        nMisiles=0;
        this.x = Constante.XPANTALLA;
        this.y = Constante.YPANTALLA;
        
        puntajes = new HistorialPuntaje() ;
         try {
                FileInputStream archivo = new FileInputStream ("puntajes.txt");
                ObjectInputStream objetoEntrada = new ObjectInputStream (archivo);

                 puntajes = (HistorialPuntaje) objetoEntrada.readObject();
                 puntajes.ordenar();
            } catch (FileNotFoundException ex) {
                System.out.println(ex.getMessage());
            } catch (ClassNotFoundException | IOException ex) {
                System.out.println(ex.getMessage());
            }
        
        //SONIDOS
        String laserURL = getClass().getResource("/Audio/laser.wav").toString();
        laser = new AudioClip(laserURL);
        String bolaURL = getClass().getResource("/Audio/misil.wav").toString();
        bola = new AudioClip(bolaURL);
        
        //ALIENS
        factor = Constante.FACTOR;
        aliens = new Pane();
        arrayAliens = crearAliens();
        puntosMAX = ""+(arrayAliens.size())*1000;  

        //ESCUDOS
        escudos = new Pane();
        arrayEscudos = new ArrayList<>();
        crearEscudos(disEscudos);
        
        //NAVE
        naveEspacial = new Pane();
        nave = new Nave ((x/2)-5*factor,y-20*factor, factor, Constante.COLOR_NAVE_LADOS, Constante.COLOR_NAVE_CENTRO);
        naveEspacial.getChildren().add(nave.getNave());
        
        //DISPAROS
        disparos = new Pane();
        disparos.getChildren().add(new Rectangle(Constante.XPANTALLA-10, Constante.YPANTALLA-10, 100, 100));
        
        //HUD
        hud = new HUD(jugador.getVidas(), jugador.getPuntaje(), nivel);
        pHud = hud.getHud();

        //UNION
        root = new StackPane(pHud,naveEspacial,escudos, aliens, disparos);

        //hilos
        tDisparos = new Thread(new runDisparar());
        tDisparos.start();
        tMisiles = new Thread(new runMover());
        tMisiles.start();
        tAliens = new Thread(new MovAlien());
        tAliens.start();
        
        //Eventos de teclado
        this.scene.setOnKeyPressed(new moveHandle(true));
        this.scene.setOnKeyReleased(new moveHandle(false));
    }
    /**
     * Retorna el nodo raiz que se usa en el Scene 
     * @return 
     */
    public StackPane getRoot() {
        return root;
    }
    
    /**
     * Manejo de los keyEvents dentro del juego 
     */
    private class moveHandle implements EventHandler<KeyEvent>{
        private final boolean pres;
        
        public moveHandle(boolean presionar){
            pres = presionar;
        }
        
        @Override
        public void handle(KeyEvent event) {
            // movimiento a la derecha 
            if(event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.D || rightPressed) {
                rightPressed = pres;
            }
            // movimiento a la izquierta
            if(event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.A || leftPressed){
                leftPressed = pres;
            }
            // disparo de misiles
            if(event.getCode()==KeyCode.M){
                mPressed = pres;
            }
            // disparo de municiones normales
            if(event.getCode()==KeyCode.SPACE){
                spacePressed = pres;
            }
            // salir del juego 
            if(event.getCode() == KeyCode.ESCAPE) {  
                puntajes.getArrJugador().add(jugador);
                try {                   
                    FileOutputStream archivoSalida = new FileOutputStream ("puntajes.txt");
                    ObjectOutputStream objetoSalida = new ObjectOutputStream (archivoSalida);
                    objetoSalida.writeObject(puntajes);
                    archivoSalida.flush();
                    archivoSalida.close();     
                }catch (IOException ex) {}
                finDejuego = true;
                scene.setRoot(new Menu(font).getMenu());
                System.out.println(tAliens.isAlive());
                System.out.println(tDisparos.isAlive());
                System.out.println(tMisiles.isAlive());
            } 
            if (event.getCode() == KeyCode.P && pres) {
                pausar = !pausar;
                if(pausar)
                    pausa(true);   
                else
                    pausa(false);
            }
        }    
    }
    /**
     * Metodo privado para el control del movimuento de la nave 
     * @param derecha 
     */
    private void movimiento(boolean derecha){
        if(derecha && !finDejuego) {
            if(naveEspacial.getTranslateX() < (Constante.XPANTALLA-10*Constante.FACTOR)/2){
                movimiento += Constante.MOVIMIENTO_NAVE;
                naveEspacial.setTranslateX(movimiento);
            }
        }
        else if(!derecha && !finDejuego){
            if(naveEspacial.getTranslateX() > -(Constante.XPANTALLA-10*Constante.FACTOR)/2){
                movimiento -= Constante.MOVIMIENTO_NAVE;
                naveEspacial.setTranslateX(movimiento);
            }
        }
    }
    /**
     * metodo privado que controla el disparo de la nave terricola mediante el uso 
     * de los Threads
     */
    private void disparar(){
        if(esperaDisparo+4<=new GregorianCalendar().getTimeInMillis()/100 && !finDejuego){
            laser.play();
            municion=new Municion(nave.getPosX()+naveEspacial.getTranslateX(), nave.getPosY()-5*Constante.FACTOR
                    , disparos, arrayAliens, arrayEscudos, aliens, escudos, hud,nave);
            Group bala=municion.getBala();
            disparos.getChildren().add(bala);
            Thread t1 = new Thread(municion);
            t1.start();
            jugador.setPuntaje(1000+Integer.parseInt(municion.getPuntos().getText()));
            jugador.setVidas(hud.getVidas().getChildren().size());
            esperaDisparo = new GregorianCalendar().getTimeInMillis()/100; 
        }
    }
    /**
     * Metodo privado que controla el disparo de los misiles usando Threads
     */
    private void misil(){
        if(esperaMisil+20<=new GregorianCalendar().getTimeInMillis()/100 && !finDejuego && nMisiles<Constante.NUMERO_MISIL){
            bola.play();
            hud.restarMisiles();
            bolaEnergia = new BolaEnergia(nave.getPosX()+naveEspacial.getTranslateX(),nave.getPosY()/*-5*Constante.FACTOR*/,
                    factor,disparos,arrayAliens,arrayEscudos,aliens,hud);
            Group bola = bolaEnergia.getBolaEnergia();
            disparos.getChildren().add(bola);
            jugador.setPuntaje(1000+jugador.getPuntaje());
            Thread t2  = new Thread(bolaEnergia);
            t2.start();
            nMisiles++;
            esperaMisil = new GregorianCalendar().getTimeInMillis()/100;
        }
    }
    /**
     * Metodo que controla el traspaso de un nivel a otro al igual que la parte 
     * grafica del mismo 
     */
    public void nivelSuperado(){
        if(!finDejuego){
            Rectangle r = new Rectangle(0,0,x,y);
            r.setFill(Color.DIMGREY);
            r.setOpacity(0.6);
            Text tNivel = new Text("NIVEL "+nivel+" SUPERADO");
            tNivel.setFont(Constante.LETRA_HUD);
            tNivel.setFill(Constante.COLOR_TITULO);
            root.getChildren().addAll(r, tNivel);
            jugador.setPuntaje(jugador.getPuntaje()+500);
            hud.sumarPuntosNivelSuperado();
        }
    }
    /**
     * Metodo que controla cuando un jugador pierde la partida, controla la parte
     * grafica 
     */
    public void gameOver(){
        if(finDejuego){
            Rectangle r = new Rectangle(0,0,x,y);
            r.setFill(Color.DIMGREY);
            r.setOpacity(0.6);
            Text tfinal = new Text("GAME OVER");
            tfinal.setFont(Constante.LETRA_TITULO);
            tfinal.setFill(Constante.COLOR_TITULO);
            root.getChildren().addAll(r, tfinal);
        }
    }    
    /**
     * controla la parte grafica cuando se pausa el juego 
     * @param pausar 
     */
    public void pausa(boolean pausar){
        if(pausar){
            Rectangle r = new Rectangle(0,0,x,y);
            r.setFill(Color.DIMGREY);
            r.setOpacity(0.5);
            Text tNivel = new Text("PAUSA");
            tNivel.setFont(Constante.LETRA_HUD);
            tNivel.setFill(Constante.COLOR_TITULO);
            root.getChildren().addAll(r, tNivel);
            pause = true;
        }else{
            root.getChildren().remove(root.getChildren().size()-1);
            root.getChildren().remove(root.getChildren().size()-1);
            pause = false;
        }
    }
    /**
     * Metodo que permite la creacion del arrayList de aliens que se va a usar 
     * en cada uno de los niveles, se crean de manera aleatoria 
     * @return 
     */
    private ArrayList crearAliens(){
        ArrayList<Alien> array = new ArrayList<>() ;
        Random rd = new Random();
        for(double e=y/40; e<y/50+11*4; e+=11){
            for(double i=factor; i<6*x/10; i+=factor*20){
                int tipoAlien = rd.nextInt(3);
                switch (tipoAlien) {
                    case 0:
                        AlienGris marciano1 = new AlienGris(6*factor+i,e*factor,factor);
                        aliens.getChildren().add(marciano1.getAlien());
                        marciano1.setPosicionX(6*factor+i);
                        marciano1.setPosicionY(e*factor);
                        marciano1.setVidas(1);
                        array.add(marciano1);
                        break;
                    case 1:
                        AlienVerde marciano2 = new AlienVerde(5*factor+i,e*factor,factor);
                        aliens.getChildren().add(marciano2.getAlien());
                        marciano2.setPosicionX(5*factor+i);
                        marciano2.setPosicionY(e*factor);
                        marciano2.setVidas(3);
                        array.add(marciano2);
                        break;
                    case 2:
                        AlienRojo marciano3 = new AlienRojo(4*factor+i,e*factor,factor);
                        aliens.getChildren().add(marciano3.getAlien());
                        marciano3.setPosicionX(4*factor+i);
                        marciano3.setPosicionY(e*factor);
                        marciano3.setVidas(5);
                        array.add(marciano3);
                        break;
                }
            }
        }
        return array;
    }
    /**
     * Metodo que permite la creacion de los escudos en pantalla 
     * @param distancia 
     */
    private void crearEscudos(double distancia){
        for(double i=0; i<x-21*factor-distancia; i+=distancia){
            Escudo escudo = new Escudo(0, 0, factor);
            escudos.getChildren().add(escudo.getEscudo());
            escudo.getEscudo().setLayoutX(i);
            escudo.getEscudo().setLayoutY(y-35*factor);
        }
        Group tope = (Group)escudos.getChildren().get(escudos.getChildren().size()-1);
        double ladosEscudos = (x-tope.getLayoutX()-20*factor)/2;
        int tamaño = escudos.getChildren().size();
        escudos.getChildren().clear();
        for(int i=0; i<tamaño; i++){
            Escudo escudo = new Escudo(ladosEscudos+i*distancia, y-35*factor, factor);
            escudos.getChildren().add(escudo.getEscudo());
            arrayEscudos.add(escudo); 
        }
    }
    /**
     * Metodo que maneja la parte grafica y la animacion mediante Threads de las 
     * balas que disparan los aliens, estas se generan de manera aleatoria y su frecuencia 
     * de disparo aumenta con el nivel 
     */
    private class disparoAliens implements Runnable{
        
        private boolean stop;
        private Rectangle r=new Rectangle(5, 20);
        private int bajar=0;
        private boolean estado=true;
        private Group r1=new Group();
        /**
         * Constructor de la Clase interna que recibe como parametros las posiciones y el panel 
         * @param aliens
         * @param x
         * @param y 
         */
        public disparoAliens(Pane aliens, double x, double y) {
            int re=(int)(Math.random()*256);
            int gr=(int)(Math.random()*256);
            int bl=(int)(Math.random()*256);
            r.setFill(Color.rgb(re, gr, bl));
            r.setX(x);
            r.setY(y);
            r1.getChildren().add(r);
            aliens.getChildren().add(r1);
        }
        
        @Override
        public void run() {
            stop=true;
            while(estado && !finDejuego){
                if(!pause){
                    Platform.runLater(() -> {
                        r1.setTranslateY(bajar);
                        bajar+=1;
                        for (int i = arrayEscudos.size()-1; i  >= 0; i--) {
                            if(arrayEscudos.get(i).DeterminarPosicionRango(r1.getBoundsInParent().getMinX(),r1.getBoundsInParent().getMaxX(), r1.getBoundsInParent().getMinY(), disparos, r1)){
                                estado=false;
                                aliens.getChildren().remove(r1);
                                break;
                            }
                        }
                        if(nave.DeterminarPosicionRango(r1.getBoundsInParent().getMinX(), r1.getBoundsInParent().getMaxX(),r1.getBoundsInParent().getMinY(),movimiento) && stop){
                            jugador.restarVida();
                            hud.restarVidas(stop);
                            aliens.getChildren().remove(r1);
                            stop=false;
                            if(jugador.getVidas()==0){
                                finDejuego=true;
                            }
                            estado=false;
                        }

                        if (r1.getBoundsInParent().getMinY() >= y){
                            aliens.getChildren().remove(r1);
                            estado = false;
                        }
                    });
                }
                    try {
                        Thread.sleep(vel);////-------------------> VELOCIDAD DE DISPARO
                    } catch (InterruptedException ex) {}   
            }
        }
    }  
    /**
     * Clase privada que maneja el movimiento de los aliens internamente con el uso 
     * de Threads 
     */
    private class MovAlien implements  Runnable{
        private double mover = 0;
        private int multiplicador = 1;
        private int bajar = 0; 
        private int c=10;

        @Override
        public void run() {
            while (!arrayAliens.isEmpty() && !finDejuego){
                
                if(bajar>400.0)
                    finDejuego=true;
                
                if(!pause){
                    Platform.runLater(() -> {
                        if(mover>Constante.XPANTALLA/2.8){
                            multiplicador = -1;
                            bajar += Constante.YPANTALLA/25;
                        }else if(mover<0){
                            multiplicador = 1;
                            bajar += Constante.YPANTALLA/25;
                        }
                        double suma = Constante.XPANTALLA/70;
                        mover += multiplicador*suma;
                        for(int i=0; i<arrayAliens.size(); i++){
                            Group alien = arrayAliens.get(i).getAlien();
                            alien.setLayoutX(mover);
                            alien.setLayoutY(bajar);
                            c++;
                        }
                        if (c%cant==0) {
                            int r=(int) (Math.random() * arrayAliens.size());
                            double x1 = arrayAliens.get(r).getPosicionX()+mover;
                            double y1 = arrayAliens.get(r).getPosicionY()+bajar;
                            disparoAliens d = new disparoAliens(aliens, x1, y1);
                            Thread t=new Thread(d);
                            t.start();
                        }
                    });
                }
                    try {
                        Thread.sleep(tiempo);
                    }catch (InterruptedException ex) {}
            }
            if(nivel<3 && !finDejuego){
                Platform.runLater(() -> {
                    nivelSuperado();
                });
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {}
                Platform.runLater(() -> {
                    nivel++;
                    cant--;
                    vel++;
                    tiempo -= 150;
                    disEscudos += 7*factor;
                    Node fond = root.getScene().getRoot().getChildrenUnmodifiable().get(0);
                    StackPane sp = new StackPane(fond, new Juego(scene, font, jugador, nivel, tiempo, disEscudos).getRoot());
                    root.getScene().setRoot(sp);
                });
            }
            else if (finDejuego){
                Platform.runLater(() -> {
                    gameOver();
                });
                try {
                    Thread.sleep(2500);
                } catch (InterruptedException ex) {}
                System.out.println(font);
                scene.setRoot(new Menu(font).getMenu());
            }
               
            
        }
    }
    /**
     * Clase privada que controla el movimiento de la nave en un thread aparte 
     */
    private class runMover implements Runnable{
        @Override
        public void run() {
            while(!finDejuego){
                if(!pause){
                    Platform.runLater(() -> {
                        if(rightPressed)
                            movimiento(true);
                        if(leftPressed)
                            movimiento(false);
                    });
                }
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException ex) {}
            }
        }
    }
    /**
     * Clase privada que controla en un thread aparte el disparo para que se pueda 
     * mover y dispara al mismo tiempo 
     */
    private class runDisparar implements Runnable{
        @Override
        public void run() {
            while(!finDejuego){
                if(!pause){
                    Platform.runLater(() -> {
                        if(spacePressed)
                            disparar();
                        if(mPressed)
                            misil();
                    });
                }
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException ex) {}
            }
        }
    }
}