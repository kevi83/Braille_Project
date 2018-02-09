package enamel;

import java.io.IOException;
import java.util.ArrayList;
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
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ScenarioCreator extends Application {
	
	Printer printer;
	ArrayList <Block> blockList;

	@Override
	public void start(Stage primaryStage) throws Exception {

		
		// Adding components to GUI (comp, column, row)
		
		GridPane layout = new GridPane();
		layout.setHgap(10);
		layout.setVgap(5);
		layout.setPadding(new Insets(0, 10, 10, 10));
		
		// File menu
		Menu fileMenu = new Menu("File");

		// Menu Items
		fileMenu.getItems().add(new MenuItem("New Project"));
		fileMenu.getItems().add(new MenuItem("Load Project"));
		fileMenu.getItems().add(new MenuItem("Save Project"));

		// Main menu bar
		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().addAll(fileMenu);
		layout.add(menuBar, 0, 0, 8, 1);

		// Story text area
		Text story = new Text(" Story");
		story.setFont(Font.font("Arial", FontWeight.BOLD, 10));
		
		layout.add(story, 0, 1, 6, 1);
		
		// gotta figure this out
		story.accessibleTextProperty().set("Help I am in trouble");

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
		layout.add(braille, 2, 6);

		TextField brailleText = new TextField();
		brailleText.setPrefWidth(50);
		layout.add(brailleText, 0, 6, 2, 1);

		// blank text field for spacing
		Text blank = new Text("             ");
		blank.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 10));
		layout.add(blank, 3, 6);
		
		// answer text field
		Text answer = new Text("Answer");
		answer.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 10));
		layout.add(answer, 5, 6);

		TextField answerText = new TextField();
		answerText.setPrefWidth(30);
		layout.add(answerText, 4, 6);
		
		// blank text field for spacing
		Text blank1 = new Text("           ");
		blank1.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 10));
		layout.add(blank1, 6, 6);
		
		// sound button
		Button sound = new Button("Sound");
		layout.add(sound, 7, 6);
		
		// publish button
		Button publish = new Button("Publish");
		layout.add(publish, 0, 18);
		
		publish.setOnMouseClicked(e -> {
		Block blockText = new Block(storyText.getText(), correctText.getText(), incorrectText.getText(), 
					Integer.parseInt(answerText.getText()), brailleText.getText().charAt(0));
		publish.setText("Saved!");
		
		// more to do 
		// user needs to be able to enter their own file name
		
		try {
			printer = new Printer("file.txt");
			printer.addBlock(blockText);
			printer.print();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		});

		// Scene
		Scene scene = new Scene(layout, 750, 600);
		primaryStage.setTitle("Scenario Creator");
		primaryStage.setScene(scene);
		primaryStage.show();
				
		layout.setGridLinesVisible(false);

		
		// blocks should be saved to block list
		// user can name blocks
		// textField.setText(block.premise) 
		// publish button - update method [fields to block] 
							// block1, pull existing block
							// new block, pull empty block
		// current block
		
	}

	public static void main(String[] args) {

		// Inherited method from Application that lunches GUI
		launch(args);
	}

}
