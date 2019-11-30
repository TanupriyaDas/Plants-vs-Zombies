package application;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class SnowPea extends Pea
{
    private int speedDamage;
    public SnowPea(double x, double y, Group g) throws FileNotFoundException
    {
        super(x,y,g);
        this.speedDamage=10;
        this.peaStack.getChildren().remove(this.peaView);
        this.peaGroup.getChildren().remove(this.peaStack);
        this.peaImg=new Image(new FileInputStream("src\\application\\images\\freezepea.jpeg"));
        this.peaView=new ImageView(peaImg);
        this.peaStack.getChildren().add(peaView);
        this.peaGroup.getChildren().add(peaStack);
    }
    public void refresh() throws FileNotFoundException {
        peaStack = new StackPane();
        this.peaStack.getChildren().remove(this.peaView);
        this.peaGroup.getChildren().remove(this.peaStack);
        this.peaImg=new Image(new FileInputStream("src\\application\\images\\freezepea.jpeg"));
        this.peaView=new ImageView(peaImg);
        this.peaStack.getChildren().add(peaView);
        this.peaGroup.getChildren().add(peaStack);
    }
    public int getSpeedDamage()
    {
        return this.speedDamage;
    }
}