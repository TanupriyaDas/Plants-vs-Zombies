package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;

import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class Lawnmover implements Serializable {
	public int posX;
	public int posY;
	public transient Group g;
	private transient ImageView imgView;
	private transient StackPane sta;
	private transient Image img;
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
	public void runover(){
		TranslateTransition t=new TranslateTransition();
		t.setDuration(Duration.millis(1000));
		t.setNode(sta);
		t.setByX(posX+700);
		t.setCycleCount(1);
		t.setAutoReverse(false);
		t.play();
	}
	public void refresh() throws FileNotFoundException {
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
