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
        this.img=new Image(new FileInputStream("src\\application\\images\\snowPeaShooter.jpg"));
        this.imgView=new ImageView(img);
        imgView.setFitWidth(56);
        imgView.setFitHeight(68);
        plantStack=new StackPane();
        this.plantStack.getChildren().add(imgView);
        //this.plantGroup.getChildren().add(plantStack);
    }

    @Override
    public void sow(double x,double y) throws FileNotFoundException {
        super.sow(x,y);
        peas=new SnowPea(x,y,plantGroup);
        //peas.advance();

    }
}