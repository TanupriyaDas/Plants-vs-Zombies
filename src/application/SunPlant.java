package application;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class SunPlant extends Plants
{
    protected Image star;
    protected int sunGenerationTime=1000;
    protected StackPane starStack=new StackPane();
    protected ImageView starView;
    public SunPlant(int x, int y, Group g)
    {
        super(x,y,g);
        try
        {
            this.star=new Image(new FileInputStream("src\\application\\images\\Sun.jpeg"));
            this.starView=new ImageView(star);
            this.starStack.getChildren().add(starView);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        this.sunGenerationTime=10;
    }

    public void step()
    {
        if(sunGenerationTime==0)
            this.plantGroup.getChildren().add(starStack);
        else
            sunGenerationTime--;
        starStack.setTranslateX(x);
        starStack.setTranslateY(y);

    }
}
