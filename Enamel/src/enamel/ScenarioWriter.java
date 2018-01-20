package enamel;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class ScenarioWriter extends Application{

	//Declaring UI Components
	TextArea text = new TextArea();
	Label textLabel = new Label("Please enter the spoken script");
	Button publish = new Button("Publish");
	
	@SuppressWarnings("static-access")
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		//Window Title
		primaryStage.setTitle("New Scenario");
		
		//Adding components to GUI (comp, row, column)
		GridPane layout = new GridPane();
		layout.setConstraints(textLabel, 0, 0);
		layout.setConstraints(text, 0, 1);
		layout.setConstraints(publish, 0, 2);
		layout.getChildren().addAll(textLabel, text, publish);
		
		//Window size
		Scene scene = new Scene(layout, 400, 400);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		
		//Inherited method from Application that launches GUI
		launch();
	}

}
