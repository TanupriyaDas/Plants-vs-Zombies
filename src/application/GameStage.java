package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class GameStage extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Group root = FXMLLoader.load(getClass().getResource("GameStage.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);

//			Button b=new Button("Click me");
//			StackPane newStack=new StackPane();
//			newStack.getChildren().add(b);
//			root.getChildrenUnmodifiable().add(newStack);

			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
