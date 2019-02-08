
import java.io.File;

import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;

import javafx.scene.layout.StackPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class gui extends Application {

	Button button;

	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage primaryStage) {
		primaryStage.setTitle("JavaFX App");

		MenuBar menuBar = new MenuBar();

		VBox vBox = new VBox(menuBar);
		Menu menu = new Menu("File");
		MenuItem menuItem1 = new MenuItem("Open");

		menu.getItems().add(menuItem1);

		menuBar.getMenus().add(menu);
		menuItem1.setOnAction(e -> {
			/*
			 * FileChooser filechooser = new FileChooser();
			 * filechooser.setTitle("choose file"); String string =
			 * filechooser.showOpenDialog(null).getAbsolutePath();
			 * System.out.println(string);
			 */
			DirectoryChooser directoryChooser = new DirectoryChooser();
			directoryChooser.setTitle("Open Resource File");
			File selectedDirectory = directoryChooser.showDialog(null);
			if(selectedDirectory == null){
			     //No Directory selected
			}else{
			     System.out.println(selectedDirectory.getAbsolutePath());
			}
		});
		Scene scene = new Scene(vBox, 960, 600);

		primaryStage.setScene(scene);
		primaryStage.show();
	}

}