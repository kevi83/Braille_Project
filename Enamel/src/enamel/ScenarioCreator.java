package enamel;

import java.io.IOException;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ScenarioCreator extends Application {

	Printer printer;
	ArrayList<Block> blockList = new ArrayList<>();

	@Override
	public void start(Stage primaryStage) throws Exception {

		// Adding components to GUI (component, column, row)
		GridPane layout = new GridPane();
		layout.setHgap(10);
		layout.setVgap(5);
		layout.setPadding(new Insets(0, 5, 5, 5));

		// File menu
		Menu fileMenu = new Menu("File");

		// Menu Items
		MenuItem newProject = new MenuItem("New Project");
		MenuItem loadProject = new MenuItem("Load Project");
		MenuItem saveProject = new MenuItem("Save Project");

		fileMenu.getItems().add(newProject);
		fileMenu.getItems().add(loadProject);
		fileMenu.getItems().add(saveProject);

		// Main menu bar
		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().addAll(fileMenu);
		menuBar.setOpacity(0.6);
		layout.add(menuBar, 0, 0, 8, 1);

		// Story text area
		Text story = new Text(" Story");
		story.setFont(Font.font("Arial", FontWeight.BOLD, 13));
		story.setFill(Color.WHITE);
		layout.add(story, 0, 1, 6, 1);

		// gotta figure this out
		story.accessibleTextProperty().set("Help I am in trouble");

		TextArea storyText = new TextArea();
		storyText.setPrefHeight(250);
		storyText.setPrefWidth(600);
		storyText.setOpacity(0.65);
		layout.add(storyText, 0, 2, 8, 4);

		// Correct text area
		Text correct = new Text(" Correct");
		correct.setFont(Font.font("Arial", FontWeight.BOLD, 13));
		correct.setFill(Color.WHITE);
		layout.add(correct, 0, 7);

		TextArea correctText = new TextArea();
		correctText.setPrefHeight(100);
		correctText.setPrefWidth(600);
		correctText.setOpacity(0.65);
		layout.add(correctText, 0, 8, 8, 4);

		// Incorrect text area
		Text incorrect = new Text(" Incorrect");
		incorrect.setFont(Font.font("Arial", FontWeight.BOLD, 13));
		incorrect.setFill(Color.WHITE);
		layout.add(incorrect, 0, 13);

		TextArea incorrectText = new TextArea();
		incorrectText.setPrefHeight(100);
		incorrectText.setPrefWidth(600);
		incorrectText.setOpacity(0.65);
		layout.add(incorrectText, 0, 14, 8, 3);

		// Braille text field
		Text braille = new Text("Braille");
		braille.setFont(Font.font("Arial", FontWeight.BOLD, 11.5));
		braille.setFill(Color.GHOSTWHITE);
		layout.add(braille, 2, 6);

		TextField brailleText = new TextField();
		brailleText.setPrefWidth(40);
		layout.add(brailleText, 0, 6, 2, 1);

		// blank text field for spacing
		Text blank = new Text("             ");
		blank.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 10));
		layout.add(blank, 3, 6);

		// answer text field
		Text answer = new Text("Answer");
		answer.setFont(Font.font("Arial", FontWeight.BOLD, 11.5));
		answer.setFill(Color.WHITE);
		layout.add(answer, 5, 6);

		TextField answerText = new TextField();
		answerText.setPrefWidth(50);
		layout.add(answerText, 4, 6);

		// blank text field for spacing
		Text blank1 = new Text("                                  " + "                                               "
				+ "                                               ");
		blank1.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 10));
		layout.add(blank1, 6, 6);

		// sound button
		Button sound = new Button("Sound");
		layout.add(sound, 7, 6);
		
		// pop up after hitting sound
		Stage soundWindow = new Stage();
		GridPane layout2 = new GridPane();
		layout2.setHgap(10);
		layout2.setVgap(10);
		layout2.setPadding(new Insets(0, 5, 5, 5));

		// GUI for sound Window
		Scene scene2 = new Scene(layout2);
		soundWindow.setScene(scene2);
		soundWindow.setTitle("Add Sound");
		Text soundMessage = new Text("Sorry, the sound option is currently\n"
				+ "not available for this version");
		layout2.add(soundMessage, 0, 0, 2, 1);
		Button okay = new Button("Okay");
		layout2.add(okay, 2, 1);
		
		sound.setOnMouseClicked(e -> {
			soundWindow.show();
		});
		
		okay.setOnMouseClicked(e -> {
			soundWindow.close();
		});
		
		// ComboBox (drop down menu)
		ObservableList<String> comboBoxList = FXCollections.observableArrayList();
		ComboBox<String> comboBox = new ComboBox<String>(comboBoxList);
		comboBox.setPrefWidth(200);
		comboBox.setEditable(true);
		comboBox.setPromptText("Select a block");
		comboBoxList.add(0, "New Block");
		layout.add(comboBox, 9, 0, 5, 1);

		// return selected comboBox value
		comboBox.getSelectionModel().selectedIndexProperty().addListener(e -> {
			if (comboBox.getValue() == "New Block") {
				storyText.clear();
				correctText.clear();
				incorrectText.clear();
				brailleText.clear();
				answerText.clear();
			} else {
				for (int i = 0; i < blockList.size(); i++) {
					if (comboBox.getValue() == blockList.get(i).name) {
						storyText.setText(blockList.get(i).premise);
						correctText.setText(blockList.get(i).correctResponse);
						incorrectText.setText(blockList.get(i).wrongResponse);
						brailleText.setText(Character.toString(blockList.get(i).letter));
						answerText.setText(Integer.toString(blockList.get(i).answer));
					}
				}
			}
		});

		// pop up after hitting publish, window to save name of file
		Stage nameBlockWindow = new Stage();
		GridPane layout1 = new GridPane();
		layout1.setHgap(10);
		layout1.setVgap(10);
		layout1.setPadding(new Insets(0, 5, 5, 5));

		// GUI for Dialog Window
		Scene scene1 = new Scene(layout1);
		nameBlockWindow.setScene(scene1);
		nameBlockWindow.setTitle("Block name");
		Text nameBlock = new Text("Enter Name for the Block");
		layout1.add(nameBlock, 0, 0);
		TextField nameField = new TextField();
		layout1.add(nameField, 0, 1);
		Button save = new Button("Save");
		layout1.add(save, 0, 2);

		// save button action [pop up dialog box]
		save.setOnMouseClicked(e -> {

			// get name of file from user input
			String blockName = nameField.getText();

			// save name to comboBox
			comboBoxList.add(blockName);
			comboBox.setItems(comboBoxList);

			// save text to block
			Block blockText = new Block(blockName, storyText.getText(), correctText.getText(), incorrectText.getText(),
					Integer.parseInt(answerText.getText()), brailleText.getText().charAt(0));

			blockList.add(blockText);

			// send block to printer
			try {
				printer = new Printer(blockName + ".txt");
				printer.addBlock(blockText);
				printer.print();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			nameBlockWindow.close();
		});

		// publish button
		Button publish = new Button("Publish");
		layout.add(publish, 0, 18);

		publish.setOnMouseClicked(e -> {
			
			try {
				int x = Integer.parseInt(answerText.getText());
				nameBlockWindow.show();
			
			} catch(NumberFormatException e2) {
				
				// pop up when answer field is not a number
				Stage notANumber = new Stage();
				GridPane layout3 = new GridPane();
				layout3.setHgap(10);
				layout3.setVgap(10);
				layout3.setPadding(new Insets(5, 5, 5, 5));

				// GUI for answer field not being a number Window
				Scene scene3 = new Scene(layout3);
				notANumber.setScene(scene3);
				Text answerIsNumber = new Text("Answer field needs to contain a number");
				layout3.add(answerIsNumber, 0, 0, 2, 1);
				Button okay1 = new Button("Okay");
				layout3.add(okay1, 2, 1);
				notANumber.show();
				
				okay1.setOnAction(e1 -> {
					notANumber.close();
				});
			}
			
		});

		// Scene
		Scene scene = new Scene(layout, 900, 640);
		primaryStage.setTitle("Scenario Creator");
		primaryStage.setScene(scene);
		primaryStage.setOpacity(0.8);;
		scene.setFill(Color.TRANSPARENT);
		primaryStage.show();
		layout.setBackground(new Background(new BackgroundFill(Color.gray(0.1, 0.8), CornerRadii.EMPTY, Insets.EMPTY)));
		
		// Set true to help see how nodes are aligned
		layout.setGridLinesVisible(false);

	}

	public static void main(String[] args) {

		// Inherited method from Application that lunches GUI
		launch(args);
	}

}
