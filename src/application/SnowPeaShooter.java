package application;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class SnowPeaShooter extends ShooterPlant
{
    public SnowPeaShooter(int x, int y, Group g) throws FileNotFoundException {
        super(x,y,g);
        this.img=new Image(new FileInputStream("src\\application\\images\\SnowPeaShooter1.png"));
        this.imgView=new ImageView(img);
        imgView.setFitWidth(50);
        imgView.setFitHeight(62);
        plantStack=new StackPane();
        this.plantStack.getChildren().add(imgView);
        //this.plantGroup.getChildren().add(plantStack);
    }
    public void refresh() throws FileNotFoundException {
        this.img=new Image(new FileInputStream("src\\application\\images\\SnowPeaShooter1.png"));
        this.imgView=new ImageView(img);
        imgView.setFitWidth(50);
        imgView.setFitHeight(62);
        plantStack=new StackPane();
        this.plantStack.getChildren().add(imgView);
        this.plantGroup.getChildren().add(plantStack);
        this.x=(int)x;
        this.y=(int)y;
        plantStack.setTranslateX(x);
        plantStack.setTranslateY(y);
    }
    @Override
    public void sow(double x,double y) throws FileNotFoundException {
        super.sow(x,y);
        peas=new SnowPea(x,y,plantGroup);
        //peas.advance();

    }
}