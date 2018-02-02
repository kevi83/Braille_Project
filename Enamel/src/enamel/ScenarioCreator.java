package enamel;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ScenarioCreator extends Application {

	@SuppressWarnings("static-access")

	@Override
	public void start(Stage primaryStage) throws Exception {

		// Adding components to GUI (comp, column, row)
				
		GridPane layout = new GridPane();
		layout.setHgap(5);
		layout.setVgap(5); 

		// Window Title
		primaryStage.setTitle("New Scenario");
		
		// File menu
		Menu fileMenu = new Menu("File");
		
		// Menu Items
		fileMenu.getItems().add(new MenuItem("New Project"));
		fileMenu.getItems().add(new MenuItem("Edit Project"));
		fileMenu.getItems().add(new MenuItem("Save Project"));

		// Main menu bar
		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().addAll(fileMenu);
		layout.add(menuBar, 0, 0);
		
		// Story text area
		Text story = new Text(" Story");
		story.setFont(Font.font("Arial", FontWeight.BOLD, 10));
		layout.add(story, 0, 1);
		
		TextArea storyText = new TextArea();
		storyText.setPrefHeight(250);
		storyText.setPrefWidth(600);
		layout.add(storyText, 0, 2);
		
		// Correct text area
		Text correct = new Text(" Correct");
		correct.setFont(Font.font("Arial", FontWeight.BOLD, 10));
		layout.add(correct, 0, 3);
		
		TextArea correctText = new TextArea();
		correctText.setPrefHeight(100);
		correctText.setPrefWidth(600);
		layout.add(correctText, 0, 4);
		
		// Incorrect text area
		Text incorrect = new Text(" Incorrect");
		incorrect.setFont(Font.font("Arial", FontWeight.BOLD, 10));
		layout.add(incorrect, 0, 5);
		
		TextArea incorrectText = new TextArea();
		incorrectText.setPrefHeight(100);
		incorrectText.setPrefWidth(600);
		layout.add(incorrectText, 0, 6);
		
		ComboBox<String> dropDown = new ComboBox<String>();
		dropDown.setEditable(true);
		dropDown.getItems();
		dropDown.setPrefWidth(150);
		String value = (String) dropDown.getValue();
		layout.add(dropDown, 1, 1, 4, 1);
		
		// Braille text field
		Text braille = new Text("Braille");
		braille.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 10));
		layout.add(braille, 2, 3);
		
		TextField brailleText = new TextField();
		brailleText.setPrefWidth(50);
		layout.add(brailleText, 3, 3);

		// answer text field
		Text answer = new Text("Answer");
		answer.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 10));
		layout.add(answer, 2, 4);
		
		TextField answerText = new TextField();
		answerText.setPrefWidth(50);
		layout.add(answerText, 3, 4);
		
		// publish button
		Button publish = new Button();
		publish.setText("Publish");
		layout.add(publish, 3, 6);
		
		// Scene
		Scene scene = new Scene(layout, 800, 600);
		primaryStage.setScene(scene);
		primaryStage.show();
		
		// Show grid lines - set to true only for testing 
		layout.setGridLinesVisible(false);
		
	}

	public static void main(String[] args) {

		// Inherited method from Application that launches GUI
		launch(args);
	}

}
