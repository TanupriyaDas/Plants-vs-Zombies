package application;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.concurrent.TimeUnit;

public class CherryBomb extends BombPlant
{
    public CherryBomb(int x, int y, Group g) throws FileNotFoundException {
        super(x,y,g);
        this.img=new Image(new FileInputStream("src\\application\\images\\cherrybomb.png"));
        this.imgView=new ImageView(img);
        imgView.setFitWidth(56);
        imgView.setFitHeight(68);
        plantStack=new StackPane();
        this.plantStack.getChildren().add(imgView);
        //this.plantGroup.getChildren().add(plantStack);
    }
//    @Override
//    public void step() throws InterruptedException {
//        TimeUnit.SECONDS.sleep(1);
//        plantGroup.getChildren().remove(plantStack);
//
//    }
    public void refresh() throws FileNotFoundException {
        this.img=new Image(new FileInputStream("src\\application\\images\\cherrybomb.jpg"));
        this.imgView=new ImageView(img);
        imgView.setFitWidth(56);
        imgView.setFitHeight(68);
        plantStack=new StackPane();
        this.plantStack.getChildren().add(imgView);
        this.plantGroup.getChildren().add(plantStack);
        this.x=(int)x;
        this.y=(int)y;
        plantStack.setTranslateX(x);
        plantStack.setTranslateY(y);
    }
}