package enamel;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
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
		layout.setPadding(new Insets(0, 10, 10, 10));

		// File menu
		Menu fileMenu = new Menu("File");

		// Menu Items
		fileMenu.getItems().add(new MenuItem("New Project"));
		fileMenu.getItems().add(new MenuItem("Edit Project"));
		fileMenu.getItems().add(new MenuItem("Save Project"));

		// Main menu bar
		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().addAll(fileMenu);
		layout.add(menuBar, 0, 0, 8, 1);

		// Story text area
		Text story = new Text(" Story");
		story.setFont(Font.font("Arial", FontWeight.BOLD, 10));
		layout.add(story, 0, 1, 6, 1);

		TextArea storyText = new TextArea();
		storyText.setPrefHeight(250);
		storyText.setPrefWidth(600);
		layout.add(storyText, 0, 2, 8, 4);

		// Correct text area
		Text correct = new Text(" Correct");
		correct.setFont(Font.font("Arial", FontWeight.BOLD, 10));
		layout.add(correct, 0, 7);

		TextArea correctText = new TextArea();
		correctText.setPrefHeight(100);
		correctText.setPrefWidth(600);
		layout.add(correctText, 0, 8, 8, 4);

		// Incorrect text area
		Text incorrect = new Text(" Incorrect");
		incorrect.setFont(Font.font("Arial", FontWeight.BOLD, 10));
		layout.add(incorrect, 0, 13);

		TextArea incorrectText = new TextArea();
		incorrectText.setPrefHeight(100);
		incorrectText.setPrefWidth(600);
		layout.add(incorrectText, 0, 14, 8, 4);

		// drop down menu
		ComboBox<String> dropDown = new ComboBox<String>();
		dropDown.setEditable(true);
		dropDown.getItems();
		dropDown.setPrefWidth(230);
		String value = (String) dropDown.getValue();
		layout.add(dropDown, 9, 0, 5, 1);

		// Braille text field
		Text braille = new Text("Braille");
		braille.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 10));
		layout.add(braille, 1, 6);

		TextField brailleText = new TextField();
		brailleText.setPrefWidth(50);
		layout.add(brailleText, 0, 6);

		// answer text field
		Text answer = new Text("Answer");
		answer.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 10));
		layout.add(answer, 3, 6);

		TextField answerText = new TextField();
		answerText.setPrefWidth(50);
		layout.add(answerText, 2 , 6);
		
		// sound button
		Button sound = new Button("Sound");
		layout.add(sound, 5, 6);
		
		// publish button
		Button publish = new Button("Publish");
		layout.add(publish, 0, 18);

		// Scene
		Scene scene = new Scene(layout, 750, 600);
		primaryStage.setTitle("Scenario Creator");
		primaryStage.setScene(scene);
		primaryStage.show();

		layout.setGridLinesVisible(false);

	}

	public static void main(String[] args) {

		// Inherited method from Application that lunches GUI
		launch(args);
	}

}
