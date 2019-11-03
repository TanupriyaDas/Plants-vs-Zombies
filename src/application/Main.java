package application;
	
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			StackPane root = new StackPane();
			VBox menuPane = new VBox();
			Scene scene = new Scene(root,400,400);
			Button save = new Button("SAVE");
			Button exit = new Button("EXIT");
			Popup popup = new Popup();
			menuPane.getChildren().add(save);
			menuPane.getChildren().add(exit);
			popup.getContent().add(menuPane);
			popup.setAutoHide(true);
			Button menu = new Button("MENU");
			menu.setOnAction((ActionEvent event) -> {
				if(!popup.isShowing())
					popup.show(primaryStage);
			});
			root.getChildren().add(menu);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
