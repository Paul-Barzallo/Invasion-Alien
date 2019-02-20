/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TDAs;

import java.awt.Dimension;
import java.awt.Toolkit;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Clase que contiene todos las constantes que se van a utilizar a lo 
 * largo del desarrollo del programa 
 * @author Darnex
 */
public class Constante {
    public static final int NUMERO_MISIL=10;
    public static final int PUNTOSXALIEN=1000;
    private static final  Dimension PANTALLA = Toolkit.getDefaultToolkit().getScreenSize();
    public static final int VELOCIDAD_DISPAROALIENS=3;
    public static final int CANTIDAD_DISPAROALIENS=3;
    public static final double XPANTALLA = PANTALLA.width;
    public static final double YPANTALLA = PANTALLA.height;
    public static final double FACTOR = (int)Constante.XPANTALLA/310;
    public static final double ESPACIO_BOTONES = Constante.XPANTALLA/60;
    public static final double MOVIMIENTO_NAVE = Constante.XPANTALLA/150;
    public static final int TAMANIOBALA = 30 ;
    public static final double TAMANIONAVE = 20*Constante.FACTOR ;
    public static final double MOVER = Constante.XPANTALLA/300;
    public static final Color COLOR_ALIEN1 = Color.rgb(178, 176, 166);
    public static final Color COLOR_ALIEN2 = Color.RED;
    public static final Color COLOR_ALIEN3 = Color.CHARTREUSE;
    public static Color COLOR_NAVE_LADOS = Color.rgb(64, 10, 114);
    public static Color COLOR_NAVE_CENTRO = Color.ORANGERED;
    public static final Color COLOR_VIDA = Color.rgb(219, 71, 8);
    public static final Color COLOR_LINEA = Color.rgb(226, 152, 13);
    public static final Color COLOR_ESCUDO = Color.DARKORANGE;
    public static final Color COLOR_TITULO = Color.rgb(12, 249, 75);
    public static final Color COLOR_HUD = Color.rgb(242, 211, 12);
    public static final DropShadow SOMBRA_BOTONES = new DropShadow(20, Color.LIME);
    public static final Font LETRA_BOTON = new Font("Bauhaus 93", Constante.XPANTALLA/50);
    public static final Font LETRA_TITULO = Font.font("Cooper Black", FontWeight.BOLD, Constante.XPANTALLA/10);
    public static final Font LETRA_HUD = Font.font("Bauhaus 93", FontWeight.BOLD, Constante.FACTOR*9);
    public static final String ESTILO_TABLA  = "-fx-border-radius: 40;"
                                            + "-fx-background-radius: 40;"
                                            + "-fx-border-color: rgb(0, 255, 242);"
                                            + "-fx-border-width: 10;"
                                            + "-fx-background-color: rgb(163, 216, 226);"
                                            + "-fx-text-fill: rgb(39, 86, 80);";
                                            
    public static final String ESTILO_BOTON = "-fx-border-radius: 20;"
                                            + "-fx-background-radius: 20;"
                                            + "-fx-border-color: rgb(0, 255, 242);"
                                            + "-fx-border-width: 4;"
                                            + "-fx-background-color: rgb(163, 216, 226);"
                                            + "-fx-text-fill: rgb(39, 86, 80);"
                                            + "-fx-pref-width: 300px";
}
