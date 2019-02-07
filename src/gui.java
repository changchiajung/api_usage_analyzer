import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class gui extends Application{
	
	Button button;
	
	public static void main(String[] args) {
		launch(args);
	}
	public void start(Stage primaryStage) throws Exception{
		primaryStage.setTitle("Title of Demo");
		
		button = new Button();
		button.setText("Click");
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				System.out.println("click button");
			}});
		
		StackPane layout = new StackPane();
		layout.getChildren().add(button);
		
		Scene scene = new Scene(layout, 300, 250);
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
}