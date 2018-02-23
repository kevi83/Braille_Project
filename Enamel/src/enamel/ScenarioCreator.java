package enamel;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ScenarioCreator extends Application {

	Printer printer;
	ArrayList<Block> blockList = new ArrayList<>();
	HashMap<String, Block> blockMap = new HashMap<String, Block>();

	@Override
	public void start(Stage primaryStage) throws Exception {

		// Adding components to GUI (component, column, row, column span, row span)

		
		// GUI for start Window / primary stage
		GridPane layout1 = new GridPane();
		layout1.setHgap(10);
		layout1.setVgap(10);
		layout1.setPadding(new Insets(5, 5, 5, 5));
		Scene scene1 = new Scene(layout1, 550, 200);

		Text startWindowText = new Text("                       Welcome to Scenario Creator");
		startWindowText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		startWindowText.setFill(Color.WHITE);
		layout1.add(startWindowText, 0, 2, 3, 1);
		Button createButton = new Button("Create New Scenario");
		createButton.setMinSize(150, 60);
		createButton.setAccessibleRoleDescription("Create new scenario button");
		createButton.setAccessibleText("Welcome to scenario creator, To create a new scenario press enter");
		layout1.add(createButton, 0, 6);
		Button testButton = new Button("Test Scenario");
		testButton.setMinSize(150, 60);
		testButton.setAccessibleRoleDescription("Test Scenario button");
		testButton.setAccessibleText("To test a scenario press enter");
		layout1.add(testButton, 3, 6);

		
		// GUI for scenario Creator
		Stage scenarioCreator = new Stage();
		GridPane layout = new GridPane();
		layout.setHgap(10);
		layout.setVgap(5);
		layout.setPadding(new Insets(0, 5, 5, 5));

		Scene scene = new Scene(layout, 1000, 655);
		scenarioCreator.setScene(scene);
		scenarioCreator.setTitle("Scenario Creator");
		scene.setFill(Color.TRANSPARENT);
		layout.setBackground(
				new Background(new BackgroundFill(Color.gray(0.05, 0.8), CornerRadii.EMPTY, Insets.EMPTY)));

		
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
		menuBar.setOpacity(0.7);
		layout.add(menuBar, 0, 0, 8, 1);

		// border glow to make things look fancy
		int depth = 40;
		DropShadow borderGlow = new DropShadow();
		borderGlow.setColor(Color.LIGHTBLUE);
		borderGlow.setWidth(depth);
		borderGlow.setHeight(depth);
		borderGlow.setOffsetX(0f);
		borderGlow.setOffsetY(0f);

		// Block title
		TextField nameSectionField = new TextField();
		nameSectionField.setPrefWidth(30);
		layout.add(nameSectionField, 0, 1);

		Text sectionName = new Text("Section Name");
		sectionName.setFont(Font.font("Arial", FontWeight.BOLD, 11.5));
		sectionName.setFill(Color.GHOSTWHITE);
		layout.add(sectionName, 1, 1, 2, 1);

		// accessibility for braille text field
		Label nameSectionLabel = new Label("Enter the name\n for this section \n of your story");
		nameSectionLabel.setLabelFor(nameSectionField);
		nameSectionLabel.setVisible(false);
		layout.add(nameSectionLabel, 0, 1);

		// Story text area
		Text story = new Text(" Story");
		story.setFont(Font.font("Arial", FontWeight.BOLD, 13));
		story.setFill(Color.WHITE);
		layout.add(story, 0, 2, 6, 1);

		TextArea storyText = new TextArea();
		storyText.setPrefHeight(250);
		storyText.setPrefWidth(600);
		storyText.setOpacity(0.9);
		storyText.setWrapText(true);
		storyText.setEffect(borderGlow);
		layout.add(storyText, 0, 3, 8, 4);

		storyText.setAccessibleRoleDescription("this be the story text area");
		Label storyLabel = new Label("Enter your story here");
		storyLabel.setLabelFor(storyText);
		storyLabel.setVisible(false);
		layout.add(storyLabel, 0, 3, 8, 4);

		// Braille text field
		Text braille = new Text("Braille");
		braille.setFont(Font.font("Arial", FontWeight.BOLD, 11.5));
		braille.setFill(Color.GHOSTWHITE);
		layout.add(braille, 2, 7);

		TextField brailleText = new TextField();
		brailleText.setPrefWidth(40);
		layout.add(brailleText, 0, 7, 2, 1);

		// accessibility for braille text field
		Label brailleLabel = new Label("Enter the letter \n you want displayed \n on the braille cell");
		brailleLabel.setLabelFor(brailleText);
		brailleLabel.setVisible(false);
		layout.add(brailleLabel, 2, 7);

		// answer text field
		Text answer = new Text("Answer");
		answer.setFont(Font.font("Arial", FontWeight.BOLD, 11.5));
		answer.setFill(Color.WHITE);
		layout.add(answer, 5, 7);

		TextField answerText = new TextField();
		answerText.setPrefWidth(50);
		layout.add(answerText, 4, 7);

		// accessibility for answer field
		Label answerLabel = new Label("Enter the button\nnumber users should\npress for the\ncorrect response");
		answerLabel.setLabelFor(answerText);
		answerLabel.setVisible(false);
		layout.add(answerLabel, 5, 7);

		// sound button
		Button sound = new Button("Sound");
		sound.setAccessibleRoleDescription("Sound button");
		sound.setAccessibleText("Sound option is currently not available in this version");
		layout.add(sound, 7, 7);

		// Correct text area
		Text correct = new Text(" Correct");
		correct.setFont(Font.font("Arial", FontWeight.BOLD, 13));
		correct.setFill(Color.WHITE);
		layout.add(correct, 0, 8);

		TextArea correctText = new TextArea();
		correctText.setPrefHeight(100);
		correctText.setPrefWidth(600);
		correctText.setOpacity(0.95);
		correctText.setWrapText(true);
		correctText.setEffect(borderGlow);
		layout.add(correctText, 0, 9, 8, 4);

		// accessibility for correct text area
		Label correctLabel = new Label("Enter what happens \n when the right \n answer is triggered");
		correctLabel.setLabelFor(correctText);
		correctLabel.setVisible(false);
		layout.add(correctLabel, 0, 9, 8, 4);

		// Incorrect text area
		Text incorrect = new Text(" Incorrect");
		incorrect.setFont(Font.font("Arial", FontWeight.BOLD, 13));
		incorrect.setFill(Color.WHITE);
		layout.add(incorrect, 0, 14);

		TextArea incorrectText = new TextArea();
		incorrectText.setPrefHeight(100);
		incorrectText.setPrefWidth(600);
		incorrectText.setOpacity(0.95);
		incorrectText.setWrapText(true);
		incorrectText.setEffect(borderGlow);
		layout.add(incorrectText, 0, 15, 8, 3);

		// accessibility for incorrect text area
		Label incorrectLabel = new Label("Enter what happens \n when the wrong\n answer is triggered");
		incorrectLabel.setLabelFor(incorrectText);
		incorrectLabel.setVisible(false);
		layout.add(incorrectLabel, 0, 8, 8, 4);

		// blank text field for spacing
		Text blank1 = new Text(
				"                                  " + "                                               ");
		blank1.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 10));
		layout.add(blank1, 6, 7);

		// save button
		Button saveButton = new Button("Save Section");
		saveButton.setAccessibleRoleDescription("Save button");
		saveButton.setAccessibleText("Press enter to save section");
		layout.add(saveButton, 0, 19);

		// ComboBox (drop down menu)
		ObservableList<String> comboBoxList = FXCollections.observableArrayList();
		ComboBox<String> comboBox = new ComboBox<String>(comboBoxList);
		comboBox.setPrefWidth(200);
		comboBox.setEditable(true);
		comboBox.setPromptText("Select a section");
		comboBoxList.add(0, "New Section");

		layout.add(comboBox, 9, 0, 5, 1);

		//////////////// Action Events ////////////////////////////

		// starting window action events
		createButton.setOnAction(e1 -> {
			scenarioCreator.show();
			primaryStage.close();
		});
		createButton.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				scenarioCreator.show();
				primaryStage.close();
			}
		});

		// combo box open with enter
		comboBox.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				comboBox.show();
			}
		});

		// GUI for Sound button
		Stage soundWindow = new Stage();
		soundWindow.setTitle("Sound Menu");
		GridPane layout2 = new GridPane();
		layout2.setHgap(10);
		layout2.setVgap(10);
		layout2.setPadding(new Insets(0, 5, 5, 5));

		Scene scene2 = new Scene(layout2);
		soundWindow.setScene(scene2);
		Text soundMessage = new Text("Sorry, the sound option is currently\n" + "not available for this version");
		layout2.add(soundMessage, 0, 0, 2, 1);
		Button soundOkay = new Button("Okay");
		soundOkay.setAccessibleRoleDescription("okay button");
		soundOkay.setAccessibleText(
				"Sorry, the sound option is currently not available for this version, press enter to go back to main window");
		layout2.add(soundOkay, 2, 1);

		// sound button events
		sound.setOnMouseClicked(e -> {
			soundWindow.show();

		});
		soundOkay.setOnMouseClicked(e -> {
			soundWindow.close();
		});

		sound.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				soundWindow.show();
			}
		});
		soundOkay.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				soundWindow.close();
			}
		});

		// GUI for answer field not containing a number
		Stage notANumberWindow = new Stage();
		GridPane layout3 = new GridPane();
		layout3.setHgap(10);
		layout3.setVgap(10);
		layout3.setPadding(new Insets(5, 5, 5, 5));

		Scene scene3 = new Scene(layout3);
		notANumberWindow.setScene(scene3);
		notANumberWindow.setTitle("Error");
		Text answerIsNumber = new Text("Answer field needs to contain a number " + "between 1 and 4 inclusive");
		layout3.add(answerIsNumber, 0, 0, 2, 1);
		Button answerOkay = new Button("Okay");
		answerOkay.setAccessibleRoleDescription("Okay button");
		answerOkay.setAccessibleText(
				"The answer field needs to contain a number between 1 and 4 inclusive, press enter to return to main window");
		layout3.add(answerOkay, 2, 1);

		answerOkay.setOnAction(e -> {
			notANumberWindow.close();
		});
		answerOkay.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				notANumberWindow.close();
			}
		});

		// GUI for incorrect Braille cell entry
		Stage brailleWindow = new Stage();
		GridPane layout4 = new GridPane();
		layout4.setHgap(10);
		layout4.setVgap(10);
		layout4.setPadding(new Insets(5, 5, 5, 5));

		Scene scene4 = new Scene(layout4);
		brailleWindow.setScene(scene4);
		brailleWindow.setTitle("Error");
		Text brailleEntry = new Text("Braille field must contain one letter");
		layout4.add(brailleEntry, 0, 0, 2, 1);
		Button brailleOkay = new Button("Okay");
		brailleOkay.setAccessibleRoleDescription("Okay button");
		brailleOkay
				.setAccessibleText("Braille field must contain one letter only, press enter to return to main window");
		layout4.add(brailleOkay, 2, 1);

		brailleOkay.setOnAction(e1 -> {
			brailleWindow.close();
		});
		brailleOkay.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				brailleWindow.close();
			}
		});

		// pop up window : Story field is empty
		Stage emptyStoryWindow = new Stage();
		GridPane layout5 = new GridPane();
		layout5.setHgap(10);
		layout5.setVgap(10);
		layout5.setPadding(new Insets(5, 5, 5, 5));

		Scene scene5 = new Scene(layout5);
		emptyStoryWindow.setScene(scene5);
		emptyStoryWindow.setTitle("Story field is empty");
		Text emptyStoryText = new Text("Section can not be saved\nif the story field is empty");
		layout5.add(emptyStoryText, 0, 0, 2, 1);
		Button emptyStoryOkay = new Button("Okay");
		emptyStoryOkay.setAccessibleRoleDescription("Okay button");
		emptyStoryOkay.setAccessibleText(
				"Section can not be saved if the story field is empty, press enter to go back to main window");
		layout5.add(emptyStoryOkay, 2, 1);

		emptyStoryOkay.setOnAction(e -> {
			emptyStoryWindow.close();
		});
		emptyStoryOkay.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				emptyStoryWindow.close();
			}
		});

		// pop up window : when user tries to save a section with name field empty
		Stage emptyNameWindow = new Stage();
		GridPane layout6 = new GridPane();
		layout6.setHgap(10);
		layout6.setVgap(10);
		layout6.setPadding(new Insets(0, 5, 5, 5));

		Scene scene6 = new Scene(layout6);
		emptyNameWindow.setScene(scene6);
		emptyNameWindow.setTitle("Name is empty");
		Text emptyName = new Text("Section can not be saved unless it has a name");
		layout6.add(emptyName, 0, 0);
		Button emptyNameButton = new Button("Okay");
		layout6.add(emptyNameButton, 1, 1);
		emptyNameButton.setAccessibleText(
				"Section can not be saved unless it has a name, press enter to go back to main window");

		emptyNameButton.setOnAction(e1 -> {
			emptyNameWindow.close();
		});
		emptyNameButton.setOnKeyPressed(e2 -> {
			if (e2.getCode() == KeyCode.ENTER) {
				emptyNameWindow.close();
			}
		});

		// pop up window : to confirm that block has been saved
		Stage saveWindow = new Stage();
		GridPane layout7 = new GridPane();
		layout7.setHgap(10);
		layout7.setVgap(10);
		layout7.setPadding(new Insets(0, 5, 5, 5));

		Scene scene7 = new Scene(layout7);
		saveWindow.setScene(scene7);
		saveWindow.setTitle("Save");
		Text saveConfirmed = new Text("This section has been saved");
		layout7.add(saveConfirmed, 0, 0);
		Button saveOkayButton = new Button("Okay");
		layout7.add(saveOkayButton, 1, 1);
		saveOkayButton.setAccessibleText("This section has been saved, press enter to return to main window");

		saveOkayButton.setOnAction(e1 -> {
			saveWindow.close();
		});
		saveOkayButton.setOnKeyPressed(e2 -> {
			if (e2.getCode() == KeyCode.ENTER) {
				saveWindow.close();
			}
		});

		// pop up window : name entire scenario (blocklist)
		Stage nameWindow = new Stage();
		GridPane layout8 = new GridPane();
		layout8.setHgap(10);
		layout8.setVgap(10);
		layout8.setPadding(new Insets(0, 5, 5, 5));

		Scene scene8 = new Scene(layout8);
		nameWindow.setScene(scene8);
		nameWindow.setTitle("Story Name");
		Text nameScenario = new Text("Enter name for the scenario");
		layout8.add(nameScenario, 0, 0);
		TextField nameScenarioText = new TextField();
		layout8.add(nameScenarioText, 0, 1);

		Label nameLabel = new Label("Enter the name\nof the scenario");
		nameLabel.setLabelFor(nameScenarioText);
		nameLabel.setVisible(false);
		layout8.add(nameLabel, 0, 1);

		Button nameSaveButton = new Button("Save");
		layout8.add(nameSaveButton, 1, 1);
		nameSaveButton.setAccessibleText("Press enter to save scenario and return to main window");
		
		
		// pop up window - warning users that if they select new project all unsaved projects will be lost
		Stage warningWindow = new Stage();
		GridPane layout10 = new GridPane();
		layout10.setHgap(10);
		layout10.setVgap(10);
		layout10.setPadding(new Insets(5, 5, 5, 5));

		Scene scene10 = new Scene(layout10);
		warningWindow.setScene(scene10);
		warningWindow.setTitle("Warning");
		Text warningText = new Text("	       Are you sure you want to start a new project?\n			any unsaved projects will be lost");
		layout10.add(warningText, 0, 0, 2, 2);
		Button warningOkay = new Button("Okay");
		warningOkay.setAccessibleRoleDescription("Okay button");
		warningOkay
				.setAccessibleText("Are you sure you want to start a new project? any unsaved projects will be lost, press enter to continue");
		layout10.add(warningOkay, 0, 4);
		Button warningCancel = new Button("Cancel");
		warningCancel.setAccessibleRoleDescription("Cancel button");
		warningCancel
				.setAccessibleText("Press enter to return to main window");
		layout10.add(warningCancel, 2, 4);
		

		// Pop up window : Choose between audio / visual player for testing
		Stage playerSelectionWindow = new Stage();
		GridPane layout9 = new GridPane();
		layout9.setHgap(10);
		layout9.setVgap(10);
		layout9.setPadding(new Insets(0, 5, 5, 5));

		Scene scene9 = new Scene(layout9);
		playerSelectionWindow.setScene(scene9);
		playerSelectionWindow.setTitle("Player selection");
		Text playerSelectionText = new Text("       Would you like to test with\n     visual player or audio player?");
		layout9.add(playerSelectionText, 0, 1, 3, 1);

		Label playerLabel = new Label("Would you like to test with visual player or audio player?");
		playerLabel.setLabelFor(playerSelectionText);
		playerLabel.setVisible(false);
		layout9.add(nameLabel, 0, 1);

		final ToggleGroup group = new ToggleGroup();
		RadioButton visualButton = new RadioButton("Visual Player");
		visualButton.setToggleGroup(group);
		visualButton.setAccessibleText("Press enter to select visual player");
		RadioButton audioButton = new RadioButton("Audio Player");
		audioButton.setToggleGroup(group);
		audioButton.setAccessibleText("Press enter to select audio player");
		layout9.add(visualButton, 0, 2);
		layout9.add(audioButton, 1, 2);


		// save button
		// Check if name field is empty
		// check story field is not empty
		// check answer field contains number
		// check braille field contains a blank or one char

		saveButton.setOnMouseClicked(e -> {

			try {
				int x = Integer.parseInt(answerText.getText());
				if (x >= 1 && x <= 4) {
					if (brailleText.getText().length() == 1) {
						if (brailleText.getText().matches("[A-z]")) {
							if (storyText.getText().length() != 0) {
								if (nameSectionField.getText().length() == 0) {

									emptyNameWindow.show();
								} else {

									saveWindow.show();
									// get name of file from user input
									String blockName = nameSectionField.getText();

									if (blockMap.containsKey(blockName)) {
										blockList.get(blockList.indexOf(
												(blockMap.get(nameSectionField.getText())))).premise = storyText
														.getText();
										blockList.get(blockList.indexOf((blockMap
												.get(nameSectionField.getText())))).correctResponse = correctText
														.getText();
										blockList.get(blockList.indexOf((blockMap
												.get(nameSectionField.getText())))).wrongResponse = incorrectText
														.getText();
										blockList.get(blockList.indexOf(
												(blockMap.get(nameSectionField.getText())))).letter = brailleText
														.getText().charAt(0);
										blockList.get(blockList
												.indexOf((blockMap.get(nameSectionField.getText())))).answer = Integer
														.parseInt(answerText.getText());
									} else {

										// save name to comboBox
										comboBoxList.add(blockName);
										comboBox.setItems(comboBoxList);

										// save text to block

										int buttonsUsed = 4;
										if (blockName.equals("") || storyText.equals("")
												|| Integer.parseInt(answerText.getText()) > buttonsUsed) {
											try {
												throw new InvalidBlockException();
											} catch (InvalidBlockException e2) {
												// TODO Auto-generated catch block
												e2.printStackTrace();
											}
										} else {
											Block blockText;
											try {
												blockText = new Block(blockName, storyText.getText(),
														correctText.getText(), incorrectText.getText(),
														Integer.parseInt(answerText.getText()),
														brailleText.getText().charAt(0));
												blockList.add(blockText);
												blockMap.put(blockName, blockText);
											} catch (NumberFormatException e2) {
												// TODO Auto-generated catch block
												e2.printStackTrace();
											} catch (InvalidBlockException e2) {
												// TODO Auto-generated catch block
												e2.printStackTrace();
											}

										}

									}
								}
							} else {
								emptyStoryWindow.show();
							}
						} else {
							brailleWindow.show();
						}
					} else {
						brailleWindow.show();
					}

				} else {
					notANumberWindow.show();
				}

			} catch (NumberFormatException e2) {
				notANumberWindow.show();
			}

		});

		saveButton.setOnKeyPressed(e -> {

			if (e.getCode() == KeyCode.ENTER) {

				try {
					int x = Integer.parseInt(answerText.getText());
					if (x >= 1 && x <= 4) {
						if (brailleText.getText().length() == 1) {
							if (brailleText.getText().matches("[A-z]"))
								if (storyText.getText().length() != 0) {
									if (nameSectionField.getText().length() == 0) {
										emptyNameWindow.show();
									} else {

										saveWindow.show();
										// get name of file from user input
										String blockName = nameSectionField.getText();

										if (blockMap.containsKey(blockName)) {
											blockList.get(blockList.indexOf(
													(blockMap.get(nameSectionField.getText())))).premise = storyText
															.getText();
											blockList.get(blockList.indexOf((blockMap
													.get(nameSectionField.getText())))).correctResponse = correctText
															.getText();
											blockList.get(blockList.indexOf((blockMap
													.get(nameSectionField.getText())))).wrongResponse = incorrectText
															.getText();
											blockList.get(blockList.indexOf(
													(blockMap.get(nameSectionField.getText())))).letter = brailleText
															.getText().charAt(0);
											blockList.get(blockList.indexOf(
													(blockMap.get(nameSectionField.getText())))).answer = Integer
															.parseInt(answerText.getText());
										} else {

											// save name to comboBox
											comboBoxList.add(blockName);
											comboBox.setItems(comboBoxList);

											// save text to block

											int buttonsUsed = 4;
											if (blockName.equals("") || storyText.equals("")
													|| Integer.parseInt(answerText.getText()) > buttonsUsed) {
												try {
													throw new InvalidBlockException();
												} catch (InvalidBlockException e2) {
													// TODO Auto-generated catch block
													e2.printStackTrace();
												}
											} else {
												Block blockText;
												try {
													blockText = new Block(blockName, storyText.getText(),
															correctText.getText(), incorrectText.getText(),
															Integer.parseInt(answerText.getText()),
															brailleText.getText().charAt(0));
													blockList.add(blockText);
													blockMap.put(blockName, blockText);
												} catch (NumberFormatException e2) {
													// TODO Auto-generated catch block
													e2.printStackTrace();
												} catch (InvalidBlockException e2) {
													// TODO Auto-generated catch block
													e2.printStackTrace();
												}

											}

											// send blocklist to printer
											try {
												printer = new Printer(blockName + ".txt");
												try {
													printer.addBlockList(blockList);
												} catch (InvalidCellException e2) {
													e2.printStackTrace();
												}
												printer.print();
											} catch (IOException e1) {
												e1.printStackTrace();
											}
										}
									}
								} else {
									emptyStoryWindow.show();
								}
							else {
								brailleWindow.show();
							}
						} else {
							brailleWindow.show();
						}

					} else {
						notANumberWindow.show();
					}

				} catch (NumberFormatException e2) {
					notANumberWindow.show();
				}
			}
		});

		// Scene
		primaryStage.setTitle("Welcome");
		primaryStage.setScene(scene1);
		scene1.setFill(Color.TRANSPARENT);
		primaryStage.show();
		layout1.setBackground(
				new Background(new BackgroundFill(Color.gray(0.05, 0.6), CornerRadii.EMPTY, Insets.EMPTY)));

		// return selected comboBox value
		comboBox.getSelectionModel().selectedIndexProperty().addListener(e -> {

			if (comboBox.getValue() == "New Section") {

				nameSectionField.clear();
				storyText.clear();
				correctText.clear();
				incorrectText.clear();
				brailleText.clear();
				answerText.clear();

			} else {

				for (int j = 0; j < blockList.size(); j++) {
					if (comboBox.getValue() == blockList.get(j).name) {
						nameSectionField.setText((blockList.get(j).name));
						storyText.setText(blockList.get(j).premise);
						correctText.setText(blockList.get(j).correctResponse);
						incorrectText.setText(blockList.get(j).wrongResponse);
						brailleText.setText(Character.toString(blockList.get(j).letter));
						answerText.setText(Integer.toString(blockList.get(j).answer));
					}
				}

			}

		});

		
		// File Menu Selection : new project
		newProject.setOnAction(e -> {
			
			warningWindow.show();
			
			});
		
		
		warningOkay.setOnAction(e1 -> {
			nameSectionField.clear();
			storyText.clear();
			correctText.clear();
			incorrectText.clear();
			brailleText.clear();
			answerText.clear();
			comboBox.getItems().removeAll(comboBoxList);
			comboBox.setPromptText("Select a section");
			comboBoxList.add(0, "New Section");
			warningWindow.close();
		});
		warningOkay.setOnKeyPressed(e -> {
			nameSectionField.clear();
			storyText.clear();
			correctText.clear();
			incorrectText.clear();
			brailleText.clear();
			answerText.clear();
			comboBox.getItems().removeAll(comboBoxList);
			comboBox.setPromptText("Select a section");
			comboBoxList.add(0, "New Section");
			if (e.getCode() == KeyCode.ENTER) {
				warningWindow.close();
			}
		});
		
		warningCancel.setOnAction(e1 -> {
			warningWindow.close();
		});
		warningCancel.setOnKeyPressed(e2 -> {
			if (e2.getCode() == KeyCode.ENTER) {
				warningWindow.close();
			}

	
		});		
		
		// File menu selection : save project
		saveProject.setOnAction(e -> {

			nameWindow.show();

		});

		loadProject.setOnAction(e -> {

		});

		nameSaveButton.setOnAction(e1 -> {

			String blockListName = nameScenarioText.getText();

			if (blockListName.length() == 0) {
				return;
			} else {
				// send blocklist to printer == save txt file
				try {

					printer = new Printer(blockListName + ".txt");
					try {
						printer.addBlockList(blockList);
					} catch (InvalidCellException e2) {
						e2.printStackTrace();
					}
					printer.print();
				} catch (IOException e3) {
					e3.printStackTrace();
				}

				nameWindow.close();
			}
		});
		nameSaveButton.setOnKeyPressed(e2 -> {
			if (e2.getCode() == KeyCode.ENTER) {

				String blockListName = nameScenarioText.getText();

				if (blockListName.length() == 0) {
					return;
				} else {
					// send blocklist to printer == save txt file
					try {

						printer = new Printer(blockListName + ".txt");
						try {
							printer.addBlockList(blockList);
						} catch (InvalidCellException e3) {
							e3.printStackTrace();
						}
						printer.print();
					} catch (IOException e3) {
						e3.printStackTrace();
					}

					nameWindow.close();
				}
			}
		});
		
		
		// starting window -> choose audio or visual player
		testButton.setOnAction(e1 -> {
			primaryStage.close();
			playerSelectionWindow.show();
			
			visualButton.setOnAction(e2 -> {
				playerSelectionWindow.close();
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Open Scenario File");
				File file = fileChooser.showOpenDialog(primaryStage);
				ScenarioParser s = new ScenarioParser(true);
				s.setScenarioFile(file.getAbsolutePath());
			});
			audioButton.setOnAction(e3 -> {
				playerSelectionWindow.close();
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Open Scenario File");
				File file = fileChooser.showOpenDialog(primaryStage);
				ScenarioParser s = new ScenarioParser(false);
				s.setScenarioFile(file.getAbsolutePath());
			});
		});

		testButton.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				primaryStage.close();
				playerSelectionWindow.show();

				visualButton.setOnKeyPressed(e1 -> {
					if (e1.getCode() == KeyCode.ENTER) {
						playerSelectionWindow.close();
						FileChooser fileChooser = new FileChooser();
						fileChooser.setTitle("Open Scenario File");
						File file = fileChooser.showOpenDialog(primaryStage);
						ScenarioParser s = new ScenarioParser(true);
						s.setScenarioFile(file.getAbsolutePath());
					}
				});
				audioButton.setOnKeyPressed(e2 -> {
					if (e2.getCode() == KeyCode.ENTER) {
						playerSelectionWindow.close();
						FileChooser fileChooser = new FileChooser();
						fileChooser.setTitle("Open Scenario File");
						File file = fileChooser.showOpenDialog(primaryStage);
						ScenarioParser s = new ScenarioParser(false);
						s.setScenarioFile(file.getAbsolutePath());
					}

				});
			}
		});

		// Set true to help see how nodes are aligned
		layout.setGridLinesVisible(false);

	}

	public static void main(String[] args) {

		// Inherited method from Application that lunches GUI
		launch(args);
	}

}
