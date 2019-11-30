package application;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class OrdinaryZombie extends Zombies
{
    public OrdinaryZombie(int x, int y, Group g) throws FileNotFoundException
    {
        super(x,y,g);
        this.health=10;
        this.zombImg=new Image(new FileInputStream("src\\application\\images\\zombie_normal.gif"));
        this.zombView=new ImageView(zombImg);
        zombView.setFitHeight(90);
        zombView.setFitWidth(69);
        this.sta.getChildren().add(zombView);
        this.g.getChildren().add(sta);
        sta.setTranslateX(posX);
        sta.setTranslateY(lane-55);
    }
    @Override
    public void refresh() throws FileNotFoundException {
        this.zombImg=new Image(new FileInputStream("src\\application\\images\\zombie_normal.gif"));
        this.zombView=new ImageView(zombImg);
        zombView.setFitHeight(90);
        zombView.setFitWidth(69);
        this.sta=new StackPane();
        this.sta.getChildren().add(zombView);
        this.g.getChildren().add(sta);
        sta.setTranslateX(posX);
        sta.setTranslateY(lane-55);
    }
}
