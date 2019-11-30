package application;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ConeZombie extends Zombies
{
    public ConeZombie(int x, int y, Group g) throws FileNotFoundException {
        super(x,y,g);
        this.health=20;
        this.zombImg=new Image(new FileInputStream("src\\application\\images\\Conehead.gif"));
        this.zombView=new ImageView(zombImg);
        zombView.setFitHeight(102);
        zombView.setFitWidth(84);

        this.sta.getChildren().add(zombView);
        this.g.getChildren().add(sta);
        sta.setTranslateX(posX);
        sta.setTranslateY(lane-55);
    }
    @Override
    public void refresh() throws FileNotFoundException {
        this.sta=new StackPane();
        this.zombImg=new Image(new FileInputStream("src\\application\\images\\Conehead.gif"));
        this.zombView=new ImageView(zombImg);
        zombView.setFitHeight(102);
        zombView.setFitWidth(84);
        this.sta.getChildren().add(zombView);
        this.g.getChildren().add(sta);
        sta.setTranslateX(posX);
        sta.setTranslateY(lane-55);
    }
}
