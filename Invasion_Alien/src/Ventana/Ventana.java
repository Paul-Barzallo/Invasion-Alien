package Ventana;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Luis
 */
public class Ventana extends Application {
    private Stage stage;
    
    @Override
    public void start(Stage stage) {
        this.stage = stage;
        //Fondo del juego
        Random rd = new Random();
        int numeroFondo = rd.nextInt(12);
        ImageView fondo = new ImageView(new Image("Fondos/Fondo"+numeroFondo+".jpg"));
        
        StackPane root = new Menu(fondo).getMenu();
        
        Scene escena = new Scene(root);
        

        Thread t = new Thread(new Music());
        t.start();
        
        //stage.setFullScreenExitKeyCombination (KeyCombination.NO_MATCH);
        stage.setFullScreen(true);
        stage.setScene(escena);
        stage.show();
        
    }
 
    private class Music implements  Runnable{
        private boolean parar = false;
        private AudioClip clip;
        @Override
        public void run() {
            while(stage.isShowing()){
                Platform.runLater(() -> {
                    int n = new Random().nextInt(3);
                    String musicURL = getClass().getResource("/Audio/music"+n+".mp3").toString();
                    if(!parar){
                        clip = new AudioClip(musicURL);
                        clip.play(0.2);
                    }
                    parar = clip.isPlaying();
                });
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ex) {}
            }
        }
    }
}
