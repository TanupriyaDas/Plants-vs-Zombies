package application;

import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class Zombies
{
    protected int health;
    protected int posX;
    protected double speed;
    protected int lane;
    protected boolean isMoving;
    protected Image zombImg;
    protected ImageView zombView;
    protected Group g;
    protected StackPane sta;
    public TranslateTransition zombMove=new TranslateTransition();

    public Zombies(int x,int y,Group g)
    {
        this.health=20;
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
        this.posX -= 2;
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
