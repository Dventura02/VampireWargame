/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PROYECTO;

import java.io.File;
import java.io.IOException;
import javafx.stage.Stage;
import java.net.URL;
import java.sql.Time;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sun.audio.AudioPlayer;
import proyecto.LogicaVampire;
import proyecto.HombreLobo;
import proyecto.Muerte;
import proyecto.Vampiro;
import proyecto.Ficha;

/**
 *
 * @author jose
 */
public class FXMLDocumentController implements Initializable {

    public LogicaVampire play = new LogicaVampire();

    @FXML
    private GridPane tablero = new GridPane();
    private ImageView imageview;
    private Image img2 = new Image(getClass().getResourceAsStream("yes.png"));
    private Image piso = new Image(getClass().getResourceAsStream("floor.png"));
    private Media sonido = new Media(getClass().getResource("sound.mp3").toString());
    private Media son = new Media(getClass().getResource("stage.mp3").toString());
    private File files = new File("access.txt");
    private File files2 = new File("special.txt");
    private File files3 = new File("mover.txt");
    private File close = new File("close.txt");
    //faltan los zombies
    //private Image zombieazul = new Image(getClass().getResourceAsStream("zombie.png"));
    private Image vampiroazul = new Image(getClass().getResourceAsStream("vampiroazul.png"));
    private Image muerteazul = new Image(getClass().getResourceAsStream("muerteazul.png"));
    private Image loboazul = new Image(getClass().getResourceAsStream("loboazul.png"));
    //private Image zombierojo = new Image(getClass().getResourceAsStream("zombie.png"));
    private Image vampirorojo = new Image(getClass().getResourceAsStream("vampirorojo.png"));
    private Image muerterojo = new Image(getClass().getResourceAsStream("muerteroja.png"));
    private Image loborojo = new Image(getClass().getResourceAsStream("loborojo.png"));

    private ImageView blank = new ImageView(img2);
    private Button[][] botones = new Button[6][6];
    private int doctor, who, batman, superman, posx, posy;
    public Button ult = new Button("", ((Node) blank));
    @FXML
    Label ataque, tipo, escudo, vida, ruleta, turno;
    @FXML
    Button fin, save;

    @FXML
    private void handleButtonAction(ActionEvent event) {

        System.out.println("You clicked me!");
    }

    public void check(int a, int b) {
        if (botones[a][b].getGraphic() == null) {
            System.out.println("3");
        }
    }

    public String returntardis() {
        try {
            return play.tablero[who][doctor].getColor();
        } catch (Exception e) {
            return "";
        }

    }
    boolean limon = false;

    private void IncTurno() {

        //move eso^^^^^^
        if (play.turno % 2 == 0) {
            if (play.contarRojas() <= 2) {
                if (!limon) {
                    Ruletamaster();
                    limon = true;
                    return;
                }
                limon = false;
                play.turno += 1;
            } else if (play.contarRojas() <= 4) {
                if (!limon) {
                    Ruletamaster();
                    limon = true;
                    return;
                }
                limon = false;
                play.turno += 1;
            } else {
                play.turno += 1;
            }
        } else {
            if (play.contarAzules() <= 2) {
                if (!limon) {
                    Ruletamaster();
                    limon = true;
                    return;
                }
                limon = false;
                play.turno += 1;
            } else if (play.contarAzules() <= 4) {
                if (!limon) {
                    Ruletamaster();
                    limon = true;
                    return;
                }
                limon = false;
                play.turno += 1;
            } else {
                play.turno += 1;
            }

        }

        if (play.turno % 2 == 0) {
            turno.setText("2");
        } else {
            turno.setText("1");

        }
        Ruletamaster();
    }

    public void Menu() throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Ataques.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("ABC");
        stage.setScene(new Scene(root1));
        stage.showAndWait();

    }

    public int getX(Button b) {
        for (int i = 0; i < botones.length; i++) {
            for (int j = 0; j < botones[i].length; j++) {
                if (botones[i][j] == b) {
                    doctor = i;
                    who = j;
                }
            }
        }
        return doctor;
    }

    public int getY(Button b) {
        for (int i = 0; i < botones.length; i++) {
            for (int j = 0; j < botones[i].length; j++) {
                if (botones[i][j] == b) {
                    doctor = i;
                    who = j;
                }
            }
        }
        if (b instanceof Button) {
            System.out.println("posicion: " + doctor + " " + who);
        }

        return who;
    }

    private void playMedia(Media m) {
        if (m != null) {
            MediaPlayer mp = new MediaPlayer(m);

            mp.play();
        }
    }

    private void terminar() throws IOException {
        Stage stage2 = (Stage) botones[0][0].getScene().getWindow();
        File close = new File("close.txt");
        close.createNewFile();
        stage2.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fin.setOnAction(surrender);
        save.setOnAction(Guardar);
        playMedia(son);
        tablero.getColumnConstraints().add(new ColumnConstraints(20)); // column 0 is 100 wide
        tablero.getRowConstraints().add(new RowConstraints(-60));

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {

                botones[i][j] = new Button("", blank);
                play.tablero[i][j] = new Vampiro("");
                play.tablero[i][j].setTest("X");
                tablero.add(botones[i][j], i, j);
                botones[i][j].setOnAction(mover);
                if (i % 2 == 0 && j % 2 == 0) {
                    botones[i][j].setStyle("-fx-base: #008B8B");
                } else {

                }
                if ((i == 1 || i == 3 || i == 5) && j % 2 != 0) {
                    botones[i][j].setStyle("-fx-base:  #008B8B");
                } else {

                }
            }

        }
        turno.setText("1");
        play.tablero[0][0] = new HombreLobo("rojo");
        play.tablero[5][0] = new HombreLobo("azul");
        play.tablero[0][3] = new Muerte("rojo");
        play.tablero[5][3] = new Muerte("azul");

        play.tablero[0][1] = new Vampiro("rojo");
        play.tablero[5][1] = new Vampiro("azul");
        play.tablero[0][4] = new Vampiro("rojo");
        play.tablero[5][4] = new Vampiro("azul");

        play.tablero[0][2] = new Muerte("rojo");
        play.tablero[5][2] = new Muerte("azul");
        play.tablero[0][5] = new HombreLobo("rojo");
        play.tablero[5][5] = new HombreLobo("azul");

        botones[0][0].setGraphic(play.tablero[0][0].getIcon());
        botones[0][5].setGraphic(play.tablero[5][0].getIcon());
        botones[3][0].setGraphic(play.tablero[0][3].getIcon());
        botones[3][5].setGraphic(play.tablero[5][3].getIcon());

        botones[1][0].setGraphic(play.tablero[0][1].getIcon());
        botones[1][5].setGraphic(play.tablero[5][1].getIcon());
        botones[4][0].setGraphic(play.tablero[0][4].getIcon());
        botones[4][5].setGraphic(play.tablero[5][4].getIcon());

        botones[2][0].setGraphic(play.tablero[0][2].getIcon());
        botones[2][5].setGraphic(play.tablero[5][2].getIcon());
        botones[5][0].setGraphic(play.tablero[0][5].getIcon());
        botones[5][5].setGraphic(play.tablero[5][5].getIcon());

        for (int i = 0; i < 6; i++) {
            botones[i][0].setId("Peon:" + i);
            botones[i][5].setId("Peon2:" + i);
        }
        System.out.println("Turno" + play.turno);
        Ruletamaster();
        //botones[0][5].setGraphic(peon);
    }
    public Ficha f = new Vampiro("");

    public void Ruletamaster() {
        switch (play.Ruleta()) {
            case 1:
                f = new HombreLobo("");
                System.out.println("Ruleta:Lobo");
                ruleta.setText("Lobo");
                break;
            case 2:
                f = new Vampiro("");
                System.out.println("Ruleta: Vampiro");
                ruleta.setText("Vampiro");
                break;
            case 3:
                f = new Muerte("");
                System.out.println("Ruleta: Muerte");
                ruleta.setText("Muerte");
                break;
            default:
                f = new Vampiro("");
                f.setTest("Vampiro");
                ruleta.setText("-------");
                break;
        }
    }

    EventHandler surrender = new EventHandler() {
        @Override
        public void handle(Event event) {
            try {
                play.turno += 1;
                System.out.print("Jugador");
                if (play.turno % 2 == 0) {
                    System.out.println(" 1");
                } else {
                    System.out.println(" 2");
                }
                System.out.println("Se rindio");
                System.out.print("Gano Jugador");
                play.turno += 1;
                if (play.turno % 2 == 0) {
                    System.out.println(" 1");
                } else {
                    System.out.println(" 2");
                }

                terminar();
            } catch (IOException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };
    EventHandler Guardar = new EventHandler() {
        @Override
        public void handle(Event event) {
            System.out.println("Digamos que se guardo");
            try {
                terminar();
            } catch (IOException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };

    EventHandler mover = new EventHandler() {
        @Override
        public void handle(Event event) {

            Object source = event.getTarget();
            Button tardis = (Button) source;
            play.Print();
            doctor = getX(tardis);
            who = getY(tardis);
            boolean limpiarult = false;
            boolean sonic = true;
            System.out.println("Aqui dougs");
            System.out.println(superman + " " + batman);
            System.out.println("azules " + play.contarAzules());
            System.out.println("rojos " + play.contarRojas());
            if (play.tablero[who][doctor].getTest().equals("X")) {
                tipo.setText("------");
                ataque.setText("------");
                vida.setText("------");
                escudo.setText("------");
            } else {
                switch (play.tablero[who][doctor].getTest()) {
                    case "V":
                        tipo.setText("Vampichoco");
                        break;
                    case "M":
                        tipo.setText("Muerte");
                        break;
                    case "Z":
                        tipo.setText("Zombie");
                        break;
                    case "H":
                        tipo.setText("Hombre Lobo");
                        break;
                }
                ataque.setText("" + play.tablero[who][doctor].getAtaque());
                vida.setText("" + play.tablero[who][doctor].getVida());
                escudo.setText("" + play.tablero[who][doctor].getEscudo());
            }

            System.out.println("entro");
            if (ult.getGraphic() != ((Node) blank)) {
                if (play.tablero[superman][batman].getTest().equals(f.getTest())) {

                    try {
                        if (play.turno % 2 != 0 && play.tablero[superman][batman].getColor() == "azul" || play.turno % 2 == 0 && play.tablero[superman][batman].getColor() == "rojo") {

                            if (play.tablero[superman][batman].getColor().equals(returntardis())) {

                            } else {
                                Menu();
                                play.Print();
                                sonic = true;
                            }

                        }
                        if (close.exists()) {
                            close.delete();
                            ult = new Button("", blank);
                            System.out.println("yay");
                            sonic = false;
                        }

                    } catch (Exception ex) {
                        Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    playMedia(sonido);
                    posx = batman - doctor;
                    posy = superman - who;

                    if (play.tablero[who][doctor].getTest().equals("X")) {
                        if (files2.exists() && play.tablero[superman][batman] instanceof HombreLobo) {

                            play.tablero[superman][batman].ataqueEspecial(play, botones, tardis, superman, batman);
                            IncTurno();
                            files2.delete();
                        }

                        if (files2.exists() && play.tablero[superman][batman] instanceof Muerte) {

                            play.tablero[superman][batman].ataqueEspecial(play, botones, tardis, superman, batman);
                            IncTurno();
                            files2.delete();
                        }

                        if (files3.exists()) {

                            if (play.tablero[superman][batman].mover(play, botones, tardis, superman, batman)) {

                                ult = new Button("", blank);
                                IncTurno();

                            }
                            files3.delete();
                        }

                        return;
                    }

                    if (botones[doctor][who].getGraphic() != ((Node) blank)) {

                        if (play.tablero[who][doctor].getColor().equals(play.tablero[superman][batman].getColor())) {

                        } else {

                            if (files.exists()) {

                                limpiarult = true;
                                IncTurno();
                                files.delete();
                                botones[doctor][who].disarm();

                                //Aqui alex ataque
                                int ataque = play.tablero[superman][batman].getAtaque();

                                int life = play.tablero[who][doctor].getVida();
                                int escudo = play.tablero[who][doctor].getEscudo();

                                if (ataque > escudo) {
                                    ataque = ataque - escudo;
                                    escudo = 0;
                                } else {
                                    escudo = escudo - ataque;
                                    ataque = 0;
                                }

                                life = life - ataque;

                                //setear las nuevas vida y escudo
                                play.tablero[who][doctor].setEscudo(escudo);
                                play.tablero[who][doctor].setVida(life);

                                System.out.println("Vida despues: " + life);
                                System.out.println("Escudo despues: " + escudo);
                                System.out.println("Ataque despues: " + ataque);
                                System.out.println("--------------");
                                System.out.println("Escudo seteado: " + play.tablero[who][doctor].getEscudo());
                                System.out.println("Vida seteado: " + play.tablero[who][doctor].getVida());

                                if (play.tablero[who][doctor].getVida() <= 0) {
                                    botones[doctor][who].setGraphic((Node) blank);
                                    play.tablero[who][doctor] = play.tablero[who][doctor] = new Vampiro("");
                                    play.tablero[who][doctor].setTest("X");
                                }

                            }

                            if (files2.exists() && !(play.tablero[superman][batman] instanceof HombreLobo)) {
                                limpiarult = true;
                                IncTurno();
                                files2.delete();
                                System.out.println("Ataque SPECIAL");
                                //if(play.tablero[who][doctor].getTest().equals("H")){
                                play.tablero[superman][batman].ataqueEspecial(play, botones, tardis, superman, batman);
                                if (play.tablero[who][doctor].getVida() <= 0) {
                                    botones[doctor][who].setGraphic((Node) blank);
                                }

                                //Aqui special attack alex
                            }

                        }
                    }

                    //parte del special del lobo
                }
            }
            ult = tardis;
            batman = doctor;
            superman = who;

            if (limpiarult) {
                limpiarult = false;
                ult = new Button("", (Node) blank);
                batman = 0;
                superman = 0;
            }
            int contazul = 0;
            int contarojo = 0;
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 6; j++) {
                    if (botones[i][j].getGraphic() != ((Node) blank)) {
                        if (play.tablero[j][i].getColor().equals("azul")) {
                            contazul++;
                        } else if (play.tablero[j][i].getColor().equals("rojo")) {
                            contarojo++;
                        }
                    }
                }
            }
            if (contazul == 0) {
                System.out.println("Gano el equipo rojo!");
                try {
                    terminar();
                } catch (IOException ex) {
                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (contarojo == 0) {
                System.out.println("Gano el equipo azul!");
                try {
                    terminar();
                } catch (IOException ex) {
                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }

    };
}
