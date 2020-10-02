//		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
package application;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
public class Main extends Application {
	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);

	}
	@Override
	public void start(Stage primaryStage) {
		BoardGame game=new BoardGame();				//create the board of the game
		AnchorPane mainBoard=new AnchorPane();		//create the layout of the window
		BorderPane bottom=new BorderPane();			//create the layout of the bottom
		StackPane up=new StackPane();				//create the layout of the information button
		primaryStage.setTitle("Puzzle Game");	    // Set window's title
		bottom.setCenter(game.getBottom());
		bottom.setBottom(game.getText_btm());
		BorderPane.setAlignment(game.getText_btm(), Pos.CENTER);

		up.getChildren().add(game.getUp());
		mainBoard.getChildren().add(game.getGridPane());
		mainBoard.getChildren().add(bottom);
		mainBoard.getChildren().add(up);
		AnchorPane.setTopAnchor(game.getGridPane(), 50.0);
		AnchorPane.setRightAnchor(game.getGridPane(), 80.0);
		AnchorPane.setTopAnchor(bottom,350.0);
		AnchorPane.setRightAnchor(bottom, 150.0); 
		primaryStage.setScene(new Scene(mainBoard, 400, 400));
		primaryStage.show();
	}
}
