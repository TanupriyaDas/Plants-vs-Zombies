package application;

import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;

public class Pea implements Serializable
{
    public final double originalPos;
    public double posX;
    public double myLane;
    protected transient Image peaImg;
    protected transient ImageView peaView;
    protected transient StackPane peaStack = new StackPane();
    protected transient Group peaGroup;
    protected int damage;
    public Pea(double x,double y,Group g) throws FileNotFoundException {
        this.posX=x+50;
        this.originalPos=posX;
        this.myLane=y;
        this.peaGroup=g;
        this.peaImg = new Image(new FileInputStream("src\\application\\images\\Pea.jpeg"));
        this.peaView=new ImageView(peaImg);
        this.peaStack.getChildren().add(peaView);
        this.peaGroup.getChildren().add(peaStack);
        peaStack.setTranslateY(myLane+10);
        peaStack.setTranslateX(posX);
        this.damage=10;
    }
    public void refresh() throws FileNotFoundException {
        peaStack = new StackPane();
        this.peaImg = new Image(new FileInputStream("src\\application\\images\\Pea.jpeg"));
        this.peaView=new ImageView(peaImg);
        this.peaStack.getChildren().add(peaView);
        this.peaGroup.getChildren().add(peaStack);
        peaStack.setTranslateY(myLane+10);
        peaStack.setTranslateX(posX);
    }
    public void advance()
    {
        TranslateTransition t=new TranslateTransition();
        t.setDuration(Duration.millis(1000));
        t.setNode(peaStack);
        t.setByX(posX+200);
        t.setCycleCount(1000);
        t.setAutoReverse(false);
        t.play();
    }
    public void step(){
        this.posX += 10;

        peaStack.setTranslateX(posX);
    }

    public double getPosX()
    {
        return this.posX;
    }

    public double getMyLane()
    {
        return this.myLane;
    }

    public Image getPeaImg()
    {
        return this.peaImg;
    }

    public int getDamage()
    {
        return this.damage;
    }
}
