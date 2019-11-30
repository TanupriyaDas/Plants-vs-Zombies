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
	public static int fileCounter = 1;
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
	public void returnFromMenu(ActionEvent event) throws Exception {
		Stage window1 = (Stage)((Node)event.getSource()).getScene().getWindow();
		window1.close();

		try {
			game = deserialize(0);
			game.rStart(); //TODO see if we can use a new window
		}
		catch(Exception e) {
			switchToGameStage(event);
		}
	}
	public void resume(ActionEvent event) throws IOException {
		Parent GameStageParent = FXMLLoader.load(getClass().getResource("ResumeChoose.fxml"));
		Scene GameStageScene = new Scene(GameStageParent);
		window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.setScene(GameStageScene);
		window.show();
	}
	public void resume1(ActionEvent event) throws Exception {
		Stage window1 = (Stage)((Node)event.getSource()).getScene().getWindow();
		window1.close();

		try {
			game = deserialize(1);
			game.rStart(); //TODO see if we can use a new window
		}
		catch(Exception e) {
			switchToGameStage(event);
		}
	}
	public void resume3(ActionEvent event) throws Exception {
		Stage window1 = (Stage)((Node)event.getSource()).getScene().getWindow();
		window1.close();

		try {
			game = deserialize(3);
			game.rStart(); //TODO see if we can use a new window
		}
		catch(Exception e) {
			switchToGameStage(event);
		}
	}
	public void resume2(ActionEvent event) throws Exception {
		Stage window1 = (Stage)((Node)event.getSource()).getScene().getWindow();
		window1.close();

		try {
			game = deserialize(2);
			game.rStart(); //TODO see if we can use a new window
		}
		catch(Exception e) {
			switchToGameStage(event);
		}
	}
	private static void serialize(GameStage1 g) throws IOException {
		ObjectOutputStream out = null;

		Files.deleteIfExists(Paths.get(Integer.toString(fileCounter)+".txt"));
		try {
			out = new ObjectOutputStream (
					new FileOutputStream(Integer.toString(fileCounter)+".txt"));
			out.writeObject(g);
		} finally {
			out.close();
		}
		fileCounter++;
		if(fileCounter>3) {fileCounter=1;}
//		Files.deleteIfExists(Paths.get("lwarr.txt"));
//		try {
//			out = new ObjectOutputStream (
//					new FileOutputStream("lwarr.txt"));
//			out.writeObject(g.lawnmovers);
//		} finally {
//			out.close();
//		}
	}
	private static GameStage1 deserialize(int c) throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream in = null;
		try {
			in = new ObjectInputStream (
					new FileInputStream(Integer.toString(c)+".txt"));
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
		ObjectOutputStream out = null;

		Files.deleteIfExists(Paths.get("0.txt"));
		try {
			out = new ObjectOutputStream (
					new FileOutputStream("0.txt"));
			out.writeObject(this.game);
		} finally {
			out.close();
		}
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
		//System.exit(0);
	}

	public static void main(String [] args)
	{
		passArgs=args;
		launch(passArgs);

	}
	public void exit() {
		System.exit(0);
	}
	public void setLevel1(ActionEvent event) throws Exception  { GameStage1.level = 1; switchToGameStage(event); }
	public void setLevel2(ActionEvent event) throws Exception  { GameStage1.level = 2; switchToGameStage(event); }
	public void setLevel3(ActionEvent event) throws Exception  { GameStage1.level = 3; switchToGameStage(event); }
	public void setLevel4(ActionEvent event) throws Exception  { GameStage1.level = 4; switchToGameStage(event); }
	public void setLevel5(ActionEvent event) throws Exception  { GameStage1.level = 5; switchToGameStage(event); }

	@Override
	public void start(Stage primaryStage) throws Exception {

	}
}