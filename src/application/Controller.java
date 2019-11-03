package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Controller extends Application {
	public GameStage1 game=new GameStage1();
	public Stage window = new Stage();
	public static String [] passArgs;
	public void switchToGameStage(ActionEvent event) throws Exception {

		Stage window1 = (Stage)((Node)event.getSource()).getScene().getWindow();
		window1.close();
		game.start(window);
//		Parent GameStageParent = FXMLLoader.load(getClass().getResource("GameStage.fxml"));
//		Scene GameStageScene = new Scene(GameStageParent);
//		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
//		window.setScene(GameStageScene);
//		window.show();
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
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.setScene(GameStageScene);
		window.show();
	}
	public void showChooseLevelScreen(ActionEvent event) throws IOException {
		Parent GameStageParent = FXMLLoader.load(getClass().getResource("ChooseLevel.fxml"));
		Scene GameStageScene = new Scene(GameStageParent);
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.setScene(GameStageScene);
		window.show();
	}
	public void save() {
		
	}

	public static void main(String [] args)
	{
		passArgs=args;
		launch(passArgs);

	}
	public void exit() {
		System.exit(0);
	}
	public void setLevel1() { }
	public void setLevel2() { }
	public void setLevel3() { }
	public void setLevel4() { }
	public void setLevel5() { }

	@Override
	public void start(Stage primaryStage) throws Exception {

	}
}
