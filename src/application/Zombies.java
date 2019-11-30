package application;

import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.FileNotFoundException;
import java.io.Serializable;

public class Zombies implements Serializable
{
    protected int health;
    public int posX;
    protected double speed;
    protected int lane;
    protected boolean isMoving;
    protected transient Image zombImg;
    protected transient ImageView zombView;
    protected transient Group g;
    protected transient StackPane sta;
    public int step =2;
    public transient TranslateTransition zombMove=new TranslateTransition();

    public void refresh() throws FileNotFoundException {
        System.out.println("We're fucked");
    }
    public Zombies(int x,int y,Group g)
    {
        this.health=10;
        this.posX=x;
        this.speed=10;
        this.lane=y;
        this.isMoving=false;
        this.sta=new StackPane();
        this.g=g;
    }

    public void advance()
    {
        zombMove.setDuration(Duration.millis(10000));
        zombMove.setNode(sta);
        zombMove.setFromX(posX);
        zombMove.setToX(180);
        zombMove.setCycleCount(50);
        zombMove.setAutoReverse(false);
        zombMove.play();


    }

    public void step()
    {
        this.posX -= step;
        sta.setTranslateX(posX);
        //System.out.print('s');
    }
    public int getHealth()
    {
        return this.health;
    }

    public int getPosX()
    {
        return this.posX;
    }

    public Image getZombImg()
    {
        return zombImg;
    }

    public double getSpeed()
    {
        return this.speed;
    }

    public int getLane()
    {
        return this.lane;
    }

    public void setPosX(int p)
    {
        this.posX=p;
    }

    public void setLane(int l)
    {
        this.lane=l;
    }

    public void setMoving(boolean m)
    {
        this.isMoving=m;
    }
}
