package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class Lawnmover {
	private int posX;
	private int posY;
	private Group g;
	private ImageView imgView;
	private StackPane sta;
	private Image img;
	public Lawnmover(int posX, int posY, Group g) throws FileNotFoundException {
		super();
		this.posX = posX;
		this.posY = posY;
		this.g = g;
		this.img=new Image(new FileInputStream("src\\application\\images\\lawnmover.png"));
		this.imgView = new ImageView(this.img);
//		this.imgView.setFitHeight(102);
		this.imgView.setFitWidth(70);
		this.imgView.setPreserveRatio(true);
		this.sta=new StackPane();
		this.sta.getChildren().add(this.imgView);
        this.g.getChildren().add(sta);
        sta.setTranslateX(posX);
        sta.setTranslateY(posY);
	}
	
}
