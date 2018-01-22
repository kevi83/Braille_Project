package enamel;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class ScenarioWriter extends Application{

	//Declaring UI Components
	TextArea text = new TextArea();
	Label textLabel = new Label("Please enter the spoken script");
	Button publish = new Button("Publish");
	Label answer = new Label("Answer");
	Label spacing = new Label();
	final ToggleGroup group = new ToggleGroup();
	RadioButton rb1 = new RadioButton("A");
	RadioButton rb2 = new RadioButton("B");
	RadioButton rb3 = new RadioButton("C");
	RadioButton rb4 = new RadioButton("D");
	
	@SuppressWarnings("static-access")
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		//Window Title
		primaryStage.setTitle("New Scenario");
		
		
		//Adding components to GUI (comp, row, column)
		GridPane layout = new GridPane();
		layout.setHgap(10);
		layout.setVgap(10);

		layout.setConstraints(textLabel, 0, 0);
		layout.setConstraints(text, 0, 1);
		layout.setConstraints(publish, 0, 2);
		
		//Answer selection
		layout.setConstraints(spacing, 0, 3);
		layout.setConstraints(answer, 0, 4);
		layout.setConstraints(rb1, 0, 5);
		layout.setConstraints(rb2, 0, 6);
		layout.setConstraints(rb3, 0, 7);
		layout.setConstraints(rb4, 0, 8);

		layout.getChildren().addAll(textLabel, text, publish, spacing, answer, rb1, rb2, rb3, rb4);
				
		//Window size
		Scene scene = new Scene(layout, 500, 450);

		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
	public static void main(String[] args) {
		
		//Inherited method from Application that launches GUI
		launch(args);
	}

}
