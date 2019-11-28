package application;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GameStage1 extends Application {
    public Stage gameStage=new Stage();
    public Group base=new Group();
    public String [] passArgs;
    public Stage menuStage=new Stage();
    public ArrayList<Button> lane1=new ArrayList<Button>();
    public ArrayList<Button> lane2=new ArrayList<Button>();
    public ArrayList<Button> lane3=new ArrayList<Button>();
    public ArrayList<Button> lane4=new ArrayList<Button>();
    public ArrayList<Button> lane5=new ArrayList<Button>();
    public Plants selected=new Plants(0,0,base);
    public ArrayList<Zombies> setZombs=new ArrayList<Zombies>();
    public Scene bgScene;
    public ArrayList<Plants> sowedPlants=new ArrayList<Plants>();
    public TranslateTransition sunDrop=new TranslateTransition();
    private Timer timer;

    @Override
    public void start(Stage primaryStage) throws Exception {
        try{
            if(primaryStage==null)
                throw new NullPointerException();
            gameStage=primaryStage;
        } catch (NullPointerException e) {
            gameStage=new Stage();
        }

        StackPane bg=new StackPane();
        Image bgImage=new Image(new FileInputStream("src\\application\\images\\GameScreen.jpeg"));
        ImageView bgView=new ImageView(bgImage);
        bgView.setFitHeight(545);
        bgView.setFitWidth(712);
        bg.prefHeight(712);
        bg.prefWidth(545);
        bg.getChildren().add(bgView);
        //adding the stackpane to group
        base.getChildren().add(bg);
        ArrayList<Lawnmover> lawnmovers = new ArrayList<Lawnmover>();
        for (int i=0; i<5; i++) {
        	lawnmovers.add(new Lawnmover(110, 140 + 71*i, base));
        }
        setLawn();
        setLawnAction();

        sundrop();
        setZombies(1);


        Label sunPoints=new Label("200");
        sunPoints.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 14));
        StackPane sunPts=new StackPane();
        sunPts.getChildren().add(sunPoints);
        base.getChildren().add(sunPts);
        sunPts.setTranslateX(31);
        sunPts.setTranslateY(74);

        Label timer=new Label("400 secs left");
        timer.setFont(Font.font("verdana",FontWeight.BOLD,FontPosture.REGULAR,15));
        StackPane timerPane=new StackPane();
        timerPane.getChildren().add(timer);
        base.getChildren().add(timerPane);
        timerPane.setTranslateX(575);
        timerPane.setTranslateY(515);

        addPlantCards();


        //Adding button for main menu
        ButtonBar menu=new ButtonBar();
        Button menuButton=new Button("menu\nmenu");
        menuButton.minHeight(94);
        menuButton.minWidth(62);
        menu.getButtons().add(menuButton);
        menu.setOpacity(0);
        Controller c=new Controller();
        menuButton.setOnAction(event -> {
            try {
                c.showMenu(event,passArgs);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

//        menuButton.setOnAction(event -> {
//            Menu.main(passArgs);
//        });

        base.getChildren().add(menu);
        menu.setTranslateX(625);
        menu.setTranslateY(10);

        //adding group to Scene
        try
        {
            bgScene=new Scene(base,712,545);
        }catch (IllegalArgumentException e)
        {
            System.out.println("background exits");
        }

        startTimer();
        gameStage.setScene(bgScene);
        gameStage.show();


    }

    private void updateAnimation() {
        // Update plant animation
        for (Plants plant : sowedPlants) {
            plant.step();
        }
        // Update Zombie animation
        for (Zombies zombie : setZombs) {
            zombie.step();
            //System.out.print('z');
        }
    }

    private void startTimer() {
        this.timer = new java.util.Timer();
        TimerTask timerTask = new TimerTask() {
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        updateAnimation();
                        //System.out.print('q');
                    }
                });
            }
        };

        long frameTimeInMilliseconds = (long)(50);
        this.timer.schedule(timerTask, 0, frameTimeInMilliseconds);
    }


    public void addPlantCards() throws FileNotFoundException {
        //sunflower card
        Image sunFlowerCard=new Image(new FileInputStream("src\\application\\images\\card_sunflower.jpeg"));
        ImageView sunFlowerView=new ImageView(sunFlowerCard);
        sunFlowerView.setFitHeight(71);
        sunFlowerView.setFitWidth(55);
        StackPane sunFlowerPane=new StackPane();
        sunFlowerPane.prefWidth(71);
        sunFlowerPane.prefHeight(55);
        sunFlowerPane.getChildren().add(sunFlowerView);
        base.getChildren().add(sunFlowerPane);
        sunFlowerPane.setTranslateY(1);
        sunFlowerPane.setTranslateX(80);

        //peashooter card
        Image peaShooterCard=new Image(new FileInputStream("src\\application\\images\\card_peashooter.jpeg"));
        ImageView peaShooterView=new ImageView(peaShooterCard);
        peaShooterView.setFitHeight(71);
        peaShooterView.setFitWidth(55);
        StackPane peaShooterPane=new StackPane();
        peaShooterPane.prefWidth(71);
        peaShooterPane.prefHeight(55);
        peaShooterPane.getChildren().add(peaShooterView);
        base.getChildren().add(peaShooterPane);
        peaShooterPane.setTranslateY(1);
        peaShooterPane.setTranslateX(135);

        //snowPeaShooter card
        Image snowPeaShooterCard=new Image(new FileInputStream("src\\application\\images\\card_cherrybomb.jpeg"));
        ImageView snowPeaShooterView=new ImageView(snowPeaShooterCard);
        snowPeaShooterView.setFitHeight(71);
        snowPeaShooterView.setFitWidth(55);
        StackPane snowPeaShooterPane=new StackPane();
        snowPeaShooterPane.prefWidth(71);
        snowPeaShooterPane.prefHeight(55);
        snowPeaShooterPane.getChildren().add(snowPeaShooterView);
        base.getChildren().add(snowPeaShooterPane);
        snowPeaShooterPane.setTranslateY(1);
        snowPeaShooterPane.setTranslateX(243);

        //cherryBomb card
        Image cherryBombCard=new Image(new FileInputStream("src\\application\\images\\card_freezepeashooter.jpeg"));
        ImageView cherryBombView=new ImageView(cherryBombCard);
        cherryBombView.setFitHeight(71);
        cherryBombView.setFitWidth(55);
        StackPane cherryBombPane=new StackPane();
        cherryBombPane.prefWidth(71);
        cherryBombPane.prefHeight(55);
        cherryBombPane.getChildren().add(cherryBombView);
        base.getChildren().add(cherryBombPane);
        cherryBombPane.setTranslateY(1);
        cherryBombPane.setTranslateX(189);

        //walnut card
        Image walnutCard=new Image(new FileInputStream("src\\application\\images\\card_wallnut.jpeg"));
        ImageView walnutView=new ImageView(walnutCard);
        walnutView.setFitHeight(71);
        walnutView.setFitWidth(55);
        StackPane walnutPane=new StackPane();
        walnutPane.prefWidth(71);
        walnutPane.prefHeight(55);
        walnutPane.getChildren().add(walnutView);
        base.getChildren().add(walnutPane);
        walnutPane.setTranslateY(1);
        walnutPane.setTranslateX(298);

        Button sunFlowerButton=new Button("sunn\nsunn\nsunn");
        sunFlowerButton.minWidth(45);
        sunFlowerButton.minHeight(71);
        sunFlowerButton.setOpacity(0);
        sunFlowerButton.setOnAction(event -> {
            try {
                Plants newPlant=new Sunflower(1,1,base);
                selected=newPlant;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
        Button peaShooterButton=new Button("peaa\npeaa\npeaa");
        peaShooterButton.minHeight(71);
        peaShooterButton.minWidth(45);
        peaShooterButton.setOpacity(0);
        peaShooterButton.setOnAction(event -> {
            try {
                Plants newPlant=new PeaShooter(1,1,base);
                selected=newPlant;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
        Button snowPeaShooterButton=new Button("snow\nsnow\nsnow");
        snowPeaShooterButton.minWidth(45);
        snowPeaShooterButton.minHeight(71);
        snowPeaShooterButton.setOpacity(0);
        snowPeaShooterButton.setOnAction(event -> {
            try {
                Plants newPlant=new SnowPeaShooter(1,1,base);
                selected=newPlant;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
        Button cherryBombButton=new Button("cherr\ncher\ncher");
        cherryBombButton.minHeight(71);
        cherryBombButton.minWidth(45);
        cherryBombButton.setOpacity(0);
        cherryBombButton.setOnAction(event -> {
            try {
                Plants newPlant=new CherryBomb(1,1,base);
                selected=newPlant;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
        Button walnutButton=new Button("wallll\nwall\nwall");
        walnutButton.minWidth(45);
        walnutButton.minHeight(71);
        walnutButton.setOpacity(0);
        walnutButton.setOnAction(event -> {
            try {
                Plants newPlant=new Walnut(1,1,base);
                selected=newPlant;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

        base.getChildren().addAll(sunFlowerButton,peaShooterButton,snowPeaShooterButton,cherryBombButton,walnutButton);
        sunFlowerButton.setTranslateX(80);
        sunFlowerButton.setTranslateY(1);
        peaShooterButton.setTranslateX(135);
        peaShooterButton.setTranslateY(1);
        snowPeaShooterButton.setTranslateX(189);
        snowPeaShooterButton.setTranslateY(1);
        cherryBombButton.setTranslateX(246);
        cherryBombButton.setTranslateY(1);
        walnutButton.setTranslateX(300);
        walnutButton.setTranslateY(1);
    }

    public void sundrop() throws FileNotFoundException {
        System.out.println(" sun being formed");
        Image sun=new Image(new FileInputStream("src\\application\\images\\sun2.png"));
        ImageView sunView=new ImageView(sun);
        sunView.setFitWidth(40);
        sunView.setFitHeight(40);
        sunView.setOpacity(1);
        StackPane sunStack=new StackPane(sunView);
        base.getChildren().add(sunStack);
        sunStack.setTranslateX(500);
        sunStack.setTranslateY(50);
        System.out.println(" sun being formed");
        sunDrop.setDuration(Duration.millis(5000));
        sunDrop.setNode(sunStack);
        sunDrop.setFromY(50);
        sunDrop.setToY(500);
        sunDrop.setCycleCount(25);
        sunDrop.setAutoReverse(false);
        sunDrop.play();
    }

    public void setLawnAction()
    {
        int i;
        for( i=0;i<9;i++)
        {
            int finalI = i;
            lane1.get(i).setOnAction(event -> {
                try {
                    selected.sow(lane1.get(finalI).getTranslateX(),lane1.get(finalI).getTranslateY());
                    sowedPlants.add(selected);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            });
            int finalI1 = i;
            lane2.get(i).setOnAction(event -> {
                try {
                    selected.sow(lane2.get(finalI1).getTranslateX(),lane2.get(finalI1).getTranslateY());
                    sowedPlants.add(selected);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            });
            int finalI2 = i;
            lane3.get(i).setOnAction(event -> {
                try {
                    selected.sow(lane3.get(finalI2).getTranslateX(),lane3.get(finalI2).getTranslateY());
                    sowedPlants.add(selected);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            });
            int finalI3 = i;
            lane4.get(i).setOnAction(event -> {
                try {
                    selected.sow(lane4.get(finalI3).getTranslateX(),lane4.get(finalI3).getTranslateY());
                    sowedPlants.add(selected);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            });
            int finalI4 = i;
            lane5.get(i).setOnAction(event -> {
                try {
                    selected.sow(lane5.get(finalI4).getTranslateX(),lane5.get(finalI4).getTranslateY());
                    sowedPlants.add(selected);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            });

        }
    }

    public void setLawn()
    {
        for(int i=0;i<9;i++)
        {
            lane1.add(new Button("plann\nplann\nplann"));
            lane2.add(new Button("plann\nplann\nplann"));
            lane3.add(new Button("plann\nplann\nplann"));
            lane4.add(new Button("plann\nplann\nplann"));
            lane5.add(new Button("plann\nplann\nplann"));

            try
            {
                lane1.get(i).minHeight(68);
                lane1.get(i).minWidth(56);
                base.getChildren().add(lane1.get(i));
                lane1.get(i).setTranslateX(180+i*56);
                lane1.get(i).setTranslateY(145);
                lane1.get(i).setOpacity(0);

                lane2.get(i).minHeight(68);
                lane2.get(i).minWidth(56);
                base.getChildren().add(lane2.get(i));
                lane2.get(i).setTranslateX(180+i*56);
                lane2.get(i).setTranslateY(215);
                lane2.get(i).setOpacity(0);

                lane3.get(i).minHeight(68);
                lane3.get(i).minWidth(56);
                base.getChildren().add(lane3.get(i));
                lane3.get(i).setTranslateX(180+i*56);
                lane3.get(i).setTranslateY(285);
                lane3.get(i).setOpacity(0);

                lane4.get(i).minHeight(68);
                lane4.get(i).minWidth(56);
                base.getChildren().add(lane4.get(i));
                lane4.get(i).setTranslateX(180+i*56);
                lane4.get(i).setTranslateY(355);
                lane4.get(i).setOpacity(0);

                lane5.get(i).minHeight(68);
                lane5.get(i).minWidth(56);
                base.getChildren().add(lane5.get(i));
                lane5.get(i).setTranslateX(180+i*56);
                lane5.get(i).setTranslateY(425);
                lane5.get(i).setOpacity(0);
            }catch (IllegalArgumentException e)
            {
                System.out.println("Current stage");
            }


        }
    }

    public void setZombies(int level) throws FileNotFoundException {
        // for any level i, we will have 4i ordinary, 2i cone, i bucket zombies
        Random rand = new Random();
        int screenEnd = 700;
        for(int i=0; i<level; i++) {
            Zombies z1=new OrdinaryZombie(screenEnd + rand.nextInt(1000),355,base);
            Zombies z2=new OrdinaryZombie(screenEnd + rand.nextInt(1000),425,base);
            Zombies z3=new OrdinaryZombie(screenEnd + rand.nextInt(1000),285,base);
            Zombies z4=new OrdinaryZombie(screenEnd + rand.nextInt(1000),145,base);
            setZombs.add(z1);setZombs.add(z2);setZombs.add(z3);setZombs.add(z4);

            //z1.advance();z2.advance();z3.advance();z4.advance();
        }
        for(int i=0; i<level; i++) {
            Zombies z1=new ConeZombie(screenEnd + rand.nextInt(1000),355,base);
            Zombies z2=new ConeZombie(screenEnd + rand.nextInt(1000),425,base);
            setZombs.add(z1);setZombs.add(z2);
            //z1.advance();z2.advance();
        }
        for(int i=0; i<level; i++) {
            Zombies z1=new BucketZombie(screenEnd + rand.nextInt(1000),425,base);
            setZombs.add(z1);
            //z1.advance();
        }
    }


    public void main(String [] args)
    {
        passArgs=args;
        launch(passArgs);
    }
}
