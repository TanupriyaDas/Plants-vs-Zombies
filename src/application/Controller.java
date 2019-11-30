package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Controller extends Application {
	public static GameStage1 game = new GameStage1();
	public Stage window = new Stage();
	public static String [] passArgs;
	private Button returning=new Button();
	private int level;
	public void switchToGameStage(ActionEvent event) throws Exception {

		Stage window1 = (Stage)((Node)event.getSource()).getScene().getWindow();
		window1.close();

		game.start(window);
		//game.main(passArgs);
//		Parent GameStageParent = FXMLLoader.load(getClass().getResource("GameStage.fxml"));
//		Scene GameStageScene = new Scene(GameStageParent);
//		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
//		window.setScene(GameStageScene);
//		window.show();
	}
	public void resume(ActionEvent event) throws IOException {
		Stage window1 = (Stage)((Node)event.getSource()).getScene().getWindow();
		window1.close();

		try {
			game = deserialize();
			game.rStart(); //TODO see if we can use a new window
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	private static void serialize(GameStage1 g) throws IOException {
		ObjectOutputStream out = null;

		Files.deleteIfExists(Paths.get("game.txt"));
		try {
			out = new ObjectOutputStream (
					new FileOutputStream("game.txt"));
			out.writeObject(g);
		} finally {
			out.close();
		}
//		Files.deleteIfExists(Paths.get("lwarr.txt"));
//		try {
//			out = new ObjectOutputStream (
//					new FileOutputStream("lwarr.txt"));
//			out.writeObject(g.lawnmovers);
//		} finally {
//			out.close();
//		}
	}
	private static GameStage1 deserialize() throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream in = null;
		try {
			in = new ObjectInputStream (
					new FileInputStream("game.txt"));
			GameStage1 game = (GameStage1) in.readObject();
//			ObjectInputStream in1 = null;
//			try {
//				in1 = new ObjectInputStream (
//						new FileInputStream("lwarr.txt"));
//				game.lawnmovers = (ArrayList<Lawnmover>) in1.readObject();
//			} finally {
//				in1.close();
//			}

			return game;
		} finally {
			in.close();
		}
	}

	public void switchToWelconeScreen(ActionEvent event) throws IOException {
		Parent GameStageParent = FXMLLoader.load(getClass().getResource("WelcomePage.fxml"));
		Scene GameStageScene = new Scene(GameStageParent);
		window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.setScene(GameStageScene);
		window.show();
	}
	public void showMenu(ActionEvent event , String[] args) throws IOException {
		passArgs=args;
		Parent GameStageParent = FXMLLoader.load(getClass().getResource("Menu.fxml"));
		Scene GameStageScene = new Scene(GameStageParent);
		Stage window1 = (Stage)((Node)event.getSource()).getScene().getWindow();
		window1.setScene(GameStageScene);
		window1.show();
	}


	public void showChooseLevelScreen(ActionEvent event) throws IOException {
		Parent GameStageParent = FXMLLoader.load(getClass().getResource("ChooseLevel.fxml"));
		Scene GameStageScene = new Scene(GameStageParent);
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.setScene(GameStageScene);
		window.show();
	}
	public void save(ActionEvent event) throws IOException {
		try {
			System.out.println(this.game.lawnmovers.size());
			serialize(this.game);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.exit(0);
	}

	public static void main(String [] args)
	{
		passArgs=args;
		launch(passArgs);

	}
	public void exit() {
		System.exit(0);
	}
	public void setLevel1() { GameStage1.level = 1;}
	public void setLevel2() { GameStage1.level = 2;}
	public void setLevel3() { GameStage1.level = 3; }
	public void setLevel4() { GameStage1.level = 4;}
	public void setLevel5() { GameStage1.level = 5;}

	@Override
	public void start(Stage primaryStage) throws Exception {

	}
}
