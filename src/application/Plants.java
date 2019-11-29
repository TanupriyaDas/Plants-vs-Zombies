package application;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Plants
{
    protected int health;
    public int x;
    public int y;
    protected final int price;
    protected int wait_time;
    protected Image img;
    protected ImageView imgView;
    protected StackPane plantStack;
    protected Group plantGroup;

    public Plants(int x,int y,Group g)
    {
        this.health=70;
        this.x=x;
        this.y=y;
        this.price=0;
        this.wait_time=0;
        this.plantGroup=g;
        //this.img=null;
    }
    public void step() {}
    public int getHealth()
    {
        return this.health;
    }

    public int getXPosition()
    {
        return this.x;
    }

    public int getYPosition()
    {
        return this.y;
    }

    public int getPrice()
    {
        return this.price;
    }

    public int getWait_time()
    {
        return this.wait_time;
    }

    public Image getImg()
    {
        return this.img;
    }

    public void sow(double x,double y) throws FileNotFoundException {
        try {
            if(plantStack==null)
            {
                throw new NullPointerException();
            }
            this.plantGroup.getChildren().add(plantStack);
            this.x=(int)x;
            this.y=(int)y;
            plantStack.setTranslateX(x);
            plantStack.setTranslateY(y);
        } catch (NullPointerException e) {
            System.out.println("plant not seleced");
        }
        catch (IllegalArgumentException e)
        {
            System.out.println("plant not seleced");
        }
    }


    public void shrink()
    {

    }


}
