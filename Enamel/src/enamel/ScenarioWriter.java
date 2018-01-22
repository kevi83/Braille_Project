package enamel;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.Node;
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
	
	//Answer radio buttons
	final ToggleGroup group = new ToggleGroup();
	RadioButton rb1 = new RadioButton("A");
	RadioButton rb2 = new RadioButton("B");
	RadioButton rb3 = new RadioButton("C");
	RadioButton rb4 = new RadioButton("D");
	RadioButton rButton1 = new RadioButton("Pin 1");
	RadioButton rButton2 = new RadioButton("Pin 2");
	RadioButton rButton3 = new RadioButton("Pin 3");
	RadioButton rButton4 = new RadioButton("Pin 4");
	RadioButton rButton5 = new RadioButton("Pin 5");
	RadioButton rButton6 = new RadioButton("Pin 6");
	RadioButton rButton7 = new RadioButton("Pin 7");
	RadioButton rButton8 = new RadioButton("Pin 8");
	
	@SuppressWarnings("static-access")
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		//Window Title
		primaryStage.setTitle("New Scenario");
		
		//Adding components to GUI (comp, row, column)
		GridPane layout = new GridPane();
		
		//Spacing between components
		layout.setHgap(4);
		layout.setVgap(4);
		
		//placement in layout
		layout.setConstraints(textLabel, 0, 0);
		layout.setConstraints(text, 0, 1);
		layout.setConstraints(publish, 0, 9);
		
		// adding my radio buttons to the stage - micah
		layout.setConstraints(rButton1, 2, 3);
		layout.setConstraints(rButton2, 3, 3);
		layout.setConstraints(rButton3, 2, 4);
		layout.setConstraints(rButton4, 3, 4);
		layout.setConstraints(rButton5, 2, 5);
		layout.setConstraints(rButton6, 3, 5);
		layout.setConstraints(rButton7, 2, 6);
		layout.setConstraints(rButton8, 3, 6);
		//layout.setConstraints(space, 1, 1);
		//layout.setConstraints(cellButt, 2, 2);
		
		
		
		rb1.setToggleGroup(group);
		rb2.setToggleGroup(group);
		rb3.setToggleGroup(group);
		rb4.setToggleGroup(group);
		
		layout.setConstraints(answer, 0, 2);
		layout.setConstraints(rb1, 0, 3);
		layout.setConstraints(rb2, 0, 4);
		layout.setConstraints(rb3, 0, 5);
		layout.setConstraints(rb4, 0, 6);
		layout.setConstraints(spacing, 0, 7);
		
		
		
		
		rButton1.setAccessibleRoleDescription("This is the first "
				+ "pin on the braille cell");
		
		layout.getChildren().addAll(textLabel, text, publish, rButton1, rButton2,
				rButton3, rButton4, rButton5, rButton6, rButton7, rButton8, spacing, answer, rb1, rb2, rb3, rb4 );
		
		//Window size  --- I extended the scene so that the text area would be full size. -micah
		Scene scene = new Scene(layout, 600, 400);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		
		//Inherited method from Application that launches GUI
		launch();
	}

}
