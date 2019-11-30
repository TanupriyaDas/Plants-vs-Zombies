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
import java.util.*;
import java.util.concurrent.TimeUnit;

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
    public ArrayList<Lawnmover> lawnmovers = new ArrayList<Lawnmover>();
    private Timer timer;
    public static int level=1;
    public Label timing,sunPoints;
    public int timeleft=0;
    private static Random r=new Random();
    private static int sunPoint=500;
    private ImageView [] plantCards=new ImageView[5];

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
        //ArrayList<Lawnmover> lawnmovers = new ArrayList<Lawnmover>();
        for (int i=0; i<5; i++) {
        	lawnmovers.add(new Lawnmover(110, 140 + 71*i, base));
        }
        //System.out.println(lawnmovers.size());
        setLawn();
        setLawnAction();

        //sundrop();
        setZombies(level);


        sunPoints=new Label("50");
        sunPoints.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 14));
        StackPane sunPts=new StackPane();
        sunPts.getChildren().add(sunPoints);
        base.getChildren().add(sunPts);
        sunPts.setTranslateX(31);
        sunPts.setTranslateY(74);

        timing=new Label("400 sec left");
        timing.setFont(Font.font("verdana",FontWeight.BOLD,FontPosture.REGULAR,15));
        StackPane timerPane=new StackPane();
        timerPane.getChildren().add(timing);
        base.getChildren().add(timerPane);
        timerPane.setTranslateX(575);
        timerPane.setTranslateY(515);

        addPlantCards();
        //sundrop();


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

    private void collider() {
        ArrayList<Plants> ptbd = new ArrayList<Plants>();
        ArrayList<Zombies> ztbd = new ArrayList<Zombies>();
        ArrayList<Lawnmover> ltbd = new ArrayList<Lawnmover>();
        Iterator<Plants> plant = sowedPlants.iterator();
        Iterator<Lawnmover> lw = lawnmovers.iterator();
        Iterator<Zombies> zombie = setZombs.iterator();
        //System.out.print(lw.hasNext());
        while(lw.hasNext()) {

            Lawnmover l = lw.next();
            zombie = setZombs.iterator();
            //System.out.println("lw "+l.posX+" "+l.posY);
            while(zombie.hasNext()) {
                Zombies z = zombie.next();
                // System.out.print(l.posX+" "+l.posY+" ");
                //System.out.println("lw "+l.posX+" "+l.posY);
                //System.out.println(Math.abs(z.lane-l.posY));
                if(Math.abs(z.posX-l.posX)<10 && Math.abs(z.lane-l.posY)<10) {
                    l.runover();
                    Iterator<Zombies> zombie1 = setZombs.iterator();
                    while(zombie1.hasNext()){
                        Zombies z1 = zombie1.next();
                        if(Math.abs(z1.lane-l.posY)<10 && z1.posX<715){
                            z1.zombView.setOpacity(0);
                            ztbd.add(z1);
                        }
                    }
                    ltbd.add(l);
//                    System.out.println("lw "+l.posX+" "+l.posY);
//                    System.out.println("z "+z.posX+" "+z.lane);
                    //System.out.print('l');
                }
            }
        }

        plant = sowedPlants.iterator();
        while(plant.hasNext()) {
            Plants p = plant.next();
            zombie = setZombs.iterator();
            while(zombie.hasNext()) {

                Zombies z = zombie.next();
                //handle if plant reach zombie
                if(Math.abs(p.x-z.posX)<10 && Math.abs(p.y-z.lane)<35) {
                    //System.out.println(p.x + "--" + p.y);
                    //System.out.println(z.posX + " " + z.lane);
                    z.step = 0;
                    p.health -= 1;
                    if(p.health<=0) {
                        p.imgView.setOpacity(0);
                        if(p instanceof ShooterPlant) {
                            Pea pea = ((ShooterPlant) p).peas;
                            pea.peaView.setOpacity(0);
                        }
                        sowedPlants.remove(p);
                        z.step = 2;
                        ptbd.add(p);
                    }
                }

                //handle if player win
                if(setZombs.isEmpty()) {
                    //TODO raise new player win exception
                }
                if(p instanceof ShooterPlant) {
                    Pea pea = ((ShooterPlant)p).peas;
                    // handle if pea goes out of screen
                    if(pea.posX>715) {
                        pea.posX=pea.originalPos;
                        pea.peaStack.setTranslateX(pea.posX);
                        pea.peaView.setOpacity(1);
                    }

                    // handle if hit pea hit zombie
                    if(Math.abs((pea.posX-z.posX))<10 && Math.abs(pea.myLane-z.lane)<20) {
                        System.out.print('h');
                        z.health-=4;
                        pea.peaView.setOpacity(0);
                        if(z.health<=0) {
                            z.zombView.setOpacity(0);
                            ((Iterator) zombie).remove();
                            ztbd.add(z);
                        }
                    }



                }
            }
        }
        plant = ptbd.iterator();
        while(plant.hasNext()) { sowedPlants.remove(plant.next()); }
        lw = ltbd.iterator();
        while(lw.hasNext()) { lawnmovers.remove(lw.next()); };
        zombie = ztbd.iterator();
        while(zombie.hasNext()) { setZombs.remove(zombie.next()); };
    }

    private static int sunCounter=100;
    private void updateAnimation() throws FileNotFoundException, LeveloverException, InterruptedException {
        collider();
        if(setZombs.size()==0) {
            // level over
            throw new LeveloverException();
        }
        // Update plant animation
        for (Plants plant : sowedPlants) {
            plant.step();
            if(plant instanceof CherryBomb)
            {
                TimeUnit.SECONDS.sleep(1);
                base.getChildren().remove(plant.plantStack);
                sowedPlants.remove(plant);

            }
        }
        // Update Zombie animation
        for (Zombies zombie : setZombs) {
            zombie.step();
            //System.out.print('z');
        }
            sunCounter--;
            if (sunCounter==0) {
                int a=r.nextInt(100);
                //System.out.println(a);
                sundrop(a,50,1);
                sunCounter=300;
            }

            switch (level)
            {
                case 1:
                    if(sunPoint>=50)
                        plantCards[0].setOpacity(1);
                    else
                        plantCards[0].setOpacity(0.5);
                    if(sunPoint>=100)
                        plantCards[1].setOpacity(1);
                    else
                        plantCards[1].setOpacity(0.5);
                    plantCards[2].setOpacity(0);
                    plantCards[3].setOpacity(0);
                    plantCards[4].setOpacity(0);
                    break;
                case 2:
                    if(sunPoint>=50)
                        plantCards[0].setOpacity(1);
                    else
                        plantCards[0].setOpacity(0.5);
                    if(sunPoint>=100)
                        plantCards[1].setOpacity(1);
                    else
                        plantCards[1].setOpacity(0.5);
                    if(sunPoint>=125)
                        plantCards[2].setOpacity(1);
                    else
                        plantCards[2].setOpacity(0.5);
                    plantCards[3].setOpacity(0);
                    plantCards[4].setOpacity(0);
                    break;
                case 3:
                    if(sunPoint>=50)
                        plantCards[0].setOpacity(1);
                    else
                        plantCards[0].setOpacity(0.5);
                    if(sunPoint>=100)
                        plantCards[1].setOpacity(1);
                    else
                        plantCards[1].setOpacity(0.5);
                    if(sunPoint>=125)
                        plantCards[2].setOpacity(1);
                    else
                        plantCards[2].setOpacity(0.5);
                    if(sunPoint>=150)
                        plantCards[3].setOpacity(1);
                    else
                        plantCards[3].setOpacity(0.5);
                    plantCards[4].setOpacity(0);
                    break;
                default:
                    if(sunPoint>=50)
                        plantCards[0].setOpacity(1);
                    else
                        plantCards[0].setOpacity(0.5);
                    if(sunPoint>=100)
                        plantCards[1].setOpacity(1);
                    else
                        plantCards[1].setOpacity(0.5);
                    if(sunPoint>=125)
                        plantCards[2].setOpacity(1);
                    else
                        plantCards[2].setOpacity(0.5);
                    if(sunPoint>=150)
                        plantCards[3].setOpacity(1);
                    else
                        plantCards[3].setOpacity(0.5);
                    if(sunPoint>=50)
                        plantCards[4].setOpacity(1);
                    else
                        plantCards[4].setOpacity(0.5);

            }


        System.out.println("update animation"+timeleft);

        timing.setText((2000- ++timeleft)/10+"secs left");
    }

    private void startTimer() {
        this.timer = new java.util.Timer();
        TimerTask timerTask = new TimerTask() {
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        try {
                            updateAnimation();
                        } catch (FileNotFoundException | LeveloverException | InterruptedException e) {
                            level++;
                            if(level>5) {
                                //TODO show you win screen
                            }
                            try {
                                setZombs.clear();
                                sowedPlants.clear();
                                lawnmovers.clear();
                                //start(gameStage);

                            } catch (Exception e1) {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            }
                        }
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
        plantCards[0]=sunFlowerView;

        if(sunPoint<50)
        {
            sunFlowerView.setOpacity(0.5);
        }
        else
        {
            sunFlowerView.setOpacity(1);
        }

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
        plantCards[1]=peaShooterView;

        if(sunPoint<100)
        {
            peaShooterView.setOpacity(0.5);
        }
        else
        {
            peaShooterView.setOpacity(1);
        }

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
        plantCards[2]=snowPeaShooterView;

        if(sunPoint<125)
        {
            snowPeaShooterView.setOpacity(0.5);
        }
        else
        {
            snowPeaShooterView.setOpacity(1);
        }

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
        plantCards[3]=cherryBombView;

        if(sunPoint<150)
        {
            cherryBombView.setOpacity(0.5);
        }
        else
        {
            cherryBombView.setOpacity(1);
        }

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
        plantCards[4]=walnutView;

        if(sunPoint<50)
        {
            walnutView.setOpacity(0.5);
        }
        else
        {
            walnutView.setOpacity(1);
        }

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
                if(sunPoint>=50 && level>=1) {
                    Plants newPlant = new Sunflower(1, 1, base);
                    selected = newPlant;
                }
                else
                    selected=null;
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
                if(sunPoint>=100 && level>=1) {
                    Plants newPlant = new PeaShooter(1, 1, base);
                    selected = newPlant;
                }
                else selected=null;
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
                if(sunPoint>=125 && level>=2) {
                    Plants newPlant = new SnowPeaShooter(1, 1, base);
                    selected = newPlant;
                }
                else selected=null;
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
                if(sunPoint>=150 && level>=3) {
                    Plants newPlant = new CherryBomb(1, 1, base);
                    //System.out.println("Cherry Bomb is selected");
                    selected = newPlant;
                }
                else selected=null;
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
                if(sunPoint>=50 && level>=4) {
                    Plants newPlant = new Walnut(1, 1, base);
                    //System.out.println("walnut chosen");
                    selected = newPlant;
                }
                else selected=null;
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

    public void sunProduce(int x,int y) throws FileNotFoundException
    {
        Image sun=new Image(new FileInputStream("src\\application\\images\\sun2.png"));
        ImageView sunView=new ImageView(sun);
        sunView.setFitWidth(40);
        sunView.setFitHeight(40);
        sunView.setOpacity(1);
        StackPane sunStack=new StackPane(sunView);
        Button sunCollect=new Button();
        sunCollect.setMinSize(40,40);
        sunCollect.setOpacity(0);
        sunStack.getChildren().add(sunCollect);
        base.getChildren().add(sunStack);
        sunCollect.setOnAction(event -> {
            sunPoint+=25;
            sunPoints.setText(sunPoint+"");
            base.getChildren().remove(sunStack);
        });
        sunStack.setTranslateX(x);
        sunStack.setTranslateY(y);
    }

    public void sundrop(int x,int y,int cycle) throws FileNotFoundException {
       // System.out.println(" sun being formed");
        Image sun=new Image(new FileInputStream("src\\application\\images\\sun2.png"));
        ImageView sunView=new ImageView(sun);
        sunView.setFitWidth(40);
        sunView.setFitHeight(40);
        sunView.setOpacity(1);
        StackPane sunStack=new StackPane(sunView);
        Button sunCollect=new Button();
        sunCollect.setMinSize(40,40);

        sunCollect.setOpacity(0);
        sunStack.getChildren().add(sunCollect);
        base.getChildren().add(sunStack);
        sunCollect.setOnAction(event -> {
            sunPoint+=25;
            sunPoints.setText(sunPoint+"");
            base.getChildren().remove(sunStack);
        });
        sunStack.setTranslateX(x);
        sunStack.setTranslateY(y);
        System.out.println(" sun being formed");
        sunDrop.setDuration(Duration.millis(5000));
        sunDrop.setNode(sunStack);
        sunDrop.setFromY(y);
        sunDrop.setToY(500);
        sunDrop.setCycleCount(cycle);
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
                    //System.out.println("plant"+lane1.get(finalI).getTranslateX()+" "+lane1.get(finalI).getTranslateY());
                    selected.sow(lane1.get(finalI).getTranslateX(),lane1.get(finalI).getTranslateY());
                    if(selected instanceof Sunflower)
                    {
                        sunPoint-=50;
                        sunPoints.setText(sunPoint+"");
                        sunProduce((int)lane1.get(finalI).getTranslateX(),(int)lane1.get(finalI).getTranslateY());
                        //sundrop((int)lane1.get(finalI).getTranslateX(),(int)lane1.get(finalI).getTranslateY(),10);
                    }
                    else if(selected instanceof CherryBomb)
                    {
                        sunPoint-=150;
                        sunPoints.setText(sunPoint+"");
                        ArrayList<Zombies> ztbd=new ArrayList<Zombies>();
                        Iterator<Zombies> zombie=setZombs.iterator();
                        System.out.println(zombie.hasNext());
                        while(zombie.hasNext())
                        {
                            Zombies z=zombie.next();
                            System.out.println(Math.abs((int)lane1.get(finalI).getTranslateX()-z.posX));
                            if(Math.abs((int)lane1.get(finalI).getTranslateX()-z.posX)<400 && Math.abs(lane1.get(finalI).getTranslateY()-z.lane)<400)
                            {
                                System.out.println("some scope of hope");
                                z.zombView.setOpacity(0);
                                ztbd.add(z);
                            }
                        }
                        zombie=ztbd.iterator();
                        while (zombie.hasNext())
                        {
                            setZombs.remove(zombie.next());
                            //System.out.println("something has stopped");
                        }
                        ztbd.clear();
//                        TimeUnit.SECONDS.sleep(1);
//                        base.getChildren().remove(selected.plantStack);
                    }
                    else if(selected instanceof PeaShooter)
                    {
                        sunPoint-=100;
                        sunPoints.setText(sunPoint+"");
                    }
                    else if(selected instanceof SnowPeaShooter)
                    {
                        sunPoint-=125;
                        sunPoints.setText(sunPoint+"");
                    }
                    else if(selected instanceof Walnut)
                    {
                        sunPoint-=50;
                        sunPoints.setText(sunPoint+"");
                    }
                    if(!(selected instanceof CherryBomb)) {
                        sowedPlants.add(selected);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            });
            int finalI1 = i;
            lane2.get(i).setOnAction(event -> {
                try {
                    //System.out.println("plant"+lane2.get(finalI1).getTranslateX()+" "+lane2.get(finalI1).getTranslateY());
                    selected.sow(lane2.get(finalI1).getTranslateX(),lane2.get(finalI1).getTranslateY());
                    if(selected instanceof Sunflower)
                    {
                        sunProduce((int)lane2.get(finalI1).getTranslateX(),(int)lane2.get(finalI1).getTranslateY());
                        sunPoint-=50;
                        sunPoints.setText(sunPoint+"");
                        //sundrop((int)lane2.get(finalI1).getTranslateX(),(int)lane2.get(finalI1).getTranslateY(),10);
                    }
                    else if(selected instanceof CherryBomb)
                    {
                        sunPoint-=150;
                        sunPoints.setText(sunPoint+"");
                        ArrayList<Zombies> ztbd=new ArrayList<Zombies>();
                        Iterator<Zombies> zombie=setZombs.iterator();
                        System.out.println(zombie.hasNext());
                        while(zombie.hasNext())
                        {
                            Zombies z=zombie.next();
                            System.out.println(Math.abs((int)lane2.get(finalI1).getTranslateX()-z.posX));
                            if(Math.abs((int)lane2.get(finalI1).getTranslateX()-z.posX)<100 && Math.abs(lane2.get(finalI1).getTranslateY()-z.lane)<100)
                            {
                                System.out.println("some scope of hope");
                                z.zombView.setOpacity(0);
                                ztbd.add(z);
                            }
                        }
                        zombie=ztbd.iterator();
                        while (zombie.hasNext())
                        {
                            setZombs.remove(zombie.next());
                        }
                        ztbd.clear();
//                        TimeUnit.SECONDS.sleep(1);
//                        base.getChildren().remove(selected.plantStack);

                    }
                    else if(selected instanceof PeaShooter)
                    {
                        sunPoint-=100;
                        sunPoints.setText(sunPoint+"");
                    }
                    else if(selected instanceof SnowPeaShooter)
                    {
                        sunPoint-=125;
                        sunPoints.setText(sunPoint+"");
                    }
                    else if(selected instanceof Walnut)
                    {
                        sunPoint-=50;
                        sunPoints.setText(sunPoint+"");
                    }
                    if(!(selected instanceof CherryBomb)) {
                        sowedPlants.add(selected);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            });
            int finalI2 = i;
            lane3.get(i).setOnAction(event -> {
                try {
                    //System.out.println("plant"+lane3.get(finalI2).getTranslateX()+" "+lane3.get(finalI2).getTranslateY());
                    selected.sow(lane3.get(finalI2).getTranslateX(),lane3.get(finalI2).getTranslateY());
                    if(selected instanceof Sunflower)
                    {
                        sunPoint-=50;
                        sunPoints.setText(sunPoint+"");
                        sunProduce((int)lane3.get(finalI2).getTranslateX(),(int)lane3.get(finalI2).getTranslateY());
                        //sundrop((int)lane3.get(finalI2).getTranslateX(),(int)lane3.get(finalI2).getTranslateY(),10);
                    }
                    else if(selected instanceof CherryBomb)
                    {
                        sunPoint-=150;
                        sunPoints.setText(sunPoint+"");
                        ArrayList<Zombies> ztbd=new ArrayList<Zombies>();
                        Iterator<Zombies> zombie=setZombs.iterator();
                        System.out.println(zombie.hasNext());
                        while(zombie.hasNext())
                        {
                            Zombies z=zombie.next();
                            System.out.println(Math.abs((int)lane3.get(finalI2).getTranslateX()-z.posX));
                            if(Math.abs((int)lane3.get(finalI2).getTranslateX()-z.posX)<100 && Math.abs(lane3.get(finalI2).getTranslateY()-z.lane)<100)
                            {
                                System.out.println("some scope of hope");
                                z.zombView.setOpacity(0);
                                ztbd.add(z);
                            }
                        }
                        zombie=ztbd.iterator();
                        while (zombie.hasNext())
                        {
                            setZombs.remove(zombie.next());
                        }
                        ztbd.clear();
//                        TimeUnit.SECONDS.sleep(1);
//                        base.getChildren().remove(selected.plantStack);
                    }
                    else if(selected instanceof PeaShooter)
                    {
                        sunPoint-=100;
                        sunPoints.setText(sunPoint+"");
                    }
                    else if(selected instanceof SnowPeaShooter)
                    {
                        sunPoint-=125;
                        sunPoints.setText(sunPoint+"");
                    }
                    else if(selected instanceof Walnut)
                    {
                        sunPoint-=50;
                        sunPoints.setText(sunPoint+"");
                    }
                    if(!(selected instanceof CherryBomb)) {
                        sowedPlants.add(selected);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            });
            int finalI3 = i;
            lane4.get(i).setOnAction(event -> {
                try {
                    //System.out.println("plant"+lane4.get(finalI3).getTranslateX()+" "+lane4.get(finalI3).getTranslateY());
                    selected.sow(lane4.get(finalI3).getTranslateX(),lane4.get(finalI3).getTranslateY());
                    if(selected instanceof Sunflower)
                    {
                        sunPoint-=50;
                        sunPoints.setText(sunPoint+"");
                        sunProduce((int)lane4.get(finalI3).getTranslateX(),(int)lane4.get(finalI3).getTranslateY());
                        //sundrop((int)lane4.get(finalI3).getTranslateX(),(int)lane4.get(finalI3).getTranslateY(),10);
                    }
                    else if(selected instanceof CherryBomb)
                    {
                        sunPoint-=150;
                        sunPoints.setText(sunPoint+"");
                        ArrayList<Zombies> ztbd=new ArrayList<Zombies>();
                        Iterator<Zombies> zombie=setZombs.iterator();
                        System.out.println(zombie.hasNext());
                        while(zombie.hasNext())
                        {
                            Zombies z=zombie.next();
                            System.out.println(Math.abs((int)lane4.get(finalI3).getTranslateX()-z.posX));
                            if(Math.abs((int)lane4.get(finalI3).getTranslateX()-z.posX)<100 && Math.abs(lane4.get(finalI3).getTranslateY()-z.lane)<100)
                            {
                                System.out.println("some scope of hope");
                                z.zombView.setOpacity(0);
                                ztbd.add(z);
                            }
                        }
                        zombie=ztbd.iterator();
                        while (zombie.hasNext())
                        {
                            setZombs.remove(zombie.next());
                        }
                        ztbd.clear();
//                        TimeUnit.SECONDS.sleep(1);
//                        base.getChildren().remove(selected.plantStack);

                    }
                    else if(selected instanceof PeaShooter)
                    {
                        sunPoint-=100;
                        sunPoints.setText(sunPoint+"");
                    }
                    else if(selected instanceof SnowPeaShooter)
                    {
                        sunPoint-=125;
                        sunPoints.setText(sunPoint+"");
                    }
                    else if(selected instanceof Walnut)
                    {
                        sunPoint-=50;
                        sunPoints.setText(sunPoint+"");
                    }
                    if(!(selected instanceof CherryBomb)) {
                        sowedPlants.add(selected);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            });
            int finalI4 = i;
            lane5.get(i).setOnAction(event -> {
                try {
                    //System.out.println("plant"+lane5.get(finalI4).getTranslateX()+" "+lane5.get(finalI4).getTranslateY());
                    selected.sow(lane5.get(finalI4).getTranslateX(),lane5.get(finalI4).getTranslateY());
                    if(selected instanceof Sunflower)
                    {
                        sunPoint-=50;
                        sunPoints.setText(sunPoint+"");
                        sunProduce((int)lane5.get(finalI4).getTranslateX(),(int)lane5.get(finalI4).getTranslateY());
                        //sundrop((int)lane5.get(finalI4).getTranslateX(),(int)lane5.get(finalI4).getTranslateY(),10);
                    }
                    else if(selected instanceof CherryBomb)
                    {
                        sunPoint-=150;
                        sunPoints.setText(sunPoint+"");
                        ArrayList<Zombies> ztbd=new ArrayList<Zombies>();
                        Iterator<Zombies> zombie=setZombs.iterator();
                        System.out.println(zombie.hasNext());
                        while(zombie.hasNext())
                        {
                            Zombies z=zombie.next();
                            System.out.println(Math.abs((int)lane5.get(finalI4).getTranslateX()-z.posX));
                            if(Math.abs((int)lane5.get(finalI4).getTranslateX()-z.posX)<100 && Math.abs(lane5.get(finalI4).getTranslateY()-z.lane)<100)
                            {
                                System.out.println("some scope of hope");
                                z.zombView.setOpacity(0);
                                ztbd.add(z);
                            }
                        }
                        zombie=ztbd.iterator();
                        while (zombie.hasNext())
                        {
                            setZombs.remove(zombie.next());
                        }
                        ztbd.clear();
//                        TimeUnit.SECONDS.sleep(2);
//                        base.getChildren().remove(selected.plantStack);

                    }
                    else if(selected instanceof PeaShooter)
                    {
                        sunPoint-=100;
                        sunPoints.setText(sunPoint+"");
                    }
                    else if(selected instanceof SnowPeaShooter)
                    {
                        sunPoint-=125;
                        sunPoints.setText(sunPoint+"");
                    }
                    else if(selected instanceof Walnut)
                    {
                        sunPoint-=50;
                        sunPoints.setText(sunPoint+"");
                    }
                    if(!(selected instanceof CherryBomb)) {
                        sowedPlants.add(selected);
                    }
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
        int screenEnd = 1000;
        if(level==0){
            Zombies z1=new OrdinaryZombie(screenEnd + rand.nextInt(1000),355,base);
            setZombs.add(z1);
            return;
        }
        for(int i=0; i<level; i++) {
            Zombies z1=new OrdinaryZombie(screenEnd*(i+1) + rand.nextInt(1000),355,base);
            Zombies z2=new OrdinaryZombie(screenEnd*(i+1) + rand.nextInt(1000),425,base);
            Zombies z3=new OrdinaryZombie(screenEnd*(i+1) + rand.nextInt(1000),285,base);
            Zombies z4=new OrdinaryZombie(screenEnd*(i+1) + rand.nextInt(1000),145,base);
            setZombs.add(z1);setZombs.add(z2);setZombs.add(z3);setZombs.add(z4);

            //z1.advance();z2.advance();z3.advance();z4.advance();
        }
        for(int i=0; i<level; i++) {
            Zombies z1=new ConeZombie(screenEnd*(i+1) + rand.nextInt(1000),355,base);
            Zombies z2=new ConeZombie(screenEnd*(i+1) + rand.nextInt(1000),425,base);
            setZombs.add(z1);setZombs.add(z2);
            //z1.advance();z2.advance();
        }
        for(int i=0; i<level; i++) {
            Zombies z1=new BucketZombie(screenEnd*(i+1) + rand.nextInt(1000),215,base);
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
