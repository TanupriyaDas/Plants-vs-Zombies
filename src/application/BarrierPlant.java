package application;

import javafx.scene.Group;
import javafx.scene.image.Image;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class BarrierPlant extends Plants
{
    private int barrierTime;
    public BarrierPlant(int x, int y, Group g) throws FileNotFoundException
    {
        super(x,y,g);
        this.barrierTime=100;
        this.health+=barrierTime;
        //this.img=new Image(new FileInputStream("walnut_full_life.gif"));
    }

    public int getBarrierTime()
    {
        return this.barrierTime;
    }

}
