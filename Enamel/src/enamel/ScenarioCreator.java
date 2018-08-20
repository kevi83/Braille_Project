package enamel;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.AccessibleRole;
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
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
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
import java.util.logging.*;

import com.oracle.tools.packager.Log;

public class ScenarioCreator extends Application {

	Printer printer;
	ArrayList<Block> blockList = new ArrayList<>();
	HashMap<String, Block> blockMap = new HashMap<String, Block>();
	GridPane layout, layout1, layout2, layout3, layout4, layout5, layout6, layout7, layout8, layout9, layout10,
			layout11, layout12, layout13, layout14, layout15, layout16, layout17, layout18, layout19;
	Scene scene, scene1, scene2, scene3, scene4, scene5, scene6, scene7, scene8, scene9, scene10, scene11, scene12,
			scene13, scene14, scene15, scene16, scene17, scene18, scene19;
	Button createButton, testButton, sound, saveButton, scenarioMenuButton, clearSectionButton, errorMessageButton,
			okayStart, warningOkay, warningCancel, soundRecord, soundImport, soundExit, answerOkay, brailleOkay,
			emptyNameButton, buttonsUsedWindowOkay, emptyStoryOkay, noSectionSavedOkay, saveOkayButton,
			scenarioSavedOkay, clearSectionButtonOkay, clearSectionButtonCancel, soundErrorButton, soundNameOkay,
			soundNameCancel, nameSoundErrorButton, newScenarioButton, loadScenarioButton, saveScenarioButton,
			cancelScenarioButton, testScenarioButton;
	Stage scenarioCreator, errorWindow, brailleCellsUsedWindow, soundWindow, notANumberWindow, brailleWindow,
			emptyNameWindow, buttonsUsedWindow, emptyStoryWindow, noSectionsSavedWindow, saveWindow,
			scenarioSavedWindow, warningWindow, playerSelectionWindow, scenarioMenuWindow, soundErrorWindow,
			clearSectionWarning, nameSoundFileWindow, nameSoundErrorWindow, scenarioWindow;
	Text startWindowText, sectionName, answerButtonsUsedText, correct, story, braille, answer, incorrect,
			scenarioNameText, nameBrailleAnswer, brailleCellsText, answerButtonsText, blank1, errorMessage, warningText,
			soundMessage, answerIsNumber, brailleEntry, emptyName, buttonsUsedError, emptyStoryText, noSectionsSaved,
			saveConfirmed, playerSelectionText, projectSavedConfirmed, clearSectionText, soundErrorText, soundNameText,
			soundNameErrorText;
	Label nameSectionLabel, answerButtonsUsedFieldLabel, storyLabel, brailleLabel, answerLabel, correctLabel,
			playerLabel, incorrectLabel, scenarioNameFieldLabel, brailleCellsUsedLabel, answerButtonsUsedLabel;
	Menu scenarioMenu, sectionMenu, goToMenu, soundMenu;
	MenuItem newProject, loadProject, saveProject, testProject, saveSection, clearSection, goToSectionName,
			goToAnswerButtonsUsed, goToStory, goToBraille, goToAnswer, goToCorrect, goToIncorrect, addSound;
	MenuBar menuBar;
	DropShadow borderGlow;
	TextField nameSectionField, answerButtonsUsedField, brailleText, answerText, scenarioNameField, brailleCellsField,
			answerButtonsField, soundNameField;
	TextArea storyText, correctText, incorrectText;
	ObservableList<String> comboBoxList;
	ComboBox<String> comboBox;
	RadioButton audioButton, visualButton;
	private Stage recordWindow;
	private Scene recordScene;
	private Button record;
	private GridPane recordLayout;
	private Button exitButton;
	private boolean recording;
	private final static Logger LOGR = Logger.getLogger(ScenarioCreator.class.getName());

	/*
	 * ---[ GUI for start Window / primary stage
	 * 
	 * Create edit test
	 * 
	 * 
	 * Adding components to GUI (component, column, row, column span, row span)
	 *
	 */

	private void createPrimaryStage() {
		layout1 = new GridPane();
		layout1.setHgap(10);
		layout1.setVgap(10);
		layout1.setPadding(new Insets(5, 15, 10, 15));
		scene1 = new Scene(layout1, 550, 200);

		startWindowText = new Text("                       Welcome to Scenario Creator");
		startWindowText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		startWindowText.setFill(Color.WHITE);
		layout1.add(startWindowText, 0, 2, 3, 1);
		createButton = new Button("Create New Scenario");
		createButton.setMinSize(150, 60);
		createButton.setStyle("-fx-base: #87ceeb;"); // sky blue
		createButton.setAccessibleRoleDescription("Create New Scenario button");
		createButton.setAccessibleText("Welcome to Scenario Creator, press enter to start creating a new scenario");
		layout1.add(createButton, 0, 6);
		testButton = new Button("Test Scenario");
		testButton.setMinSize(150, 60);
		testButton.setStyle("-fx-base: #87ceeb;"); // sky blue
		testButton.setAccessibleRoleDescription("Test Scenario button");
		testButton.setAccessibleText("To test a previously saved scenario, press enter");
		layout1.add(testButton, 3, 6);

		// GUI for scenario Creator
		scenarioCreator = new Stage();
		layout = new GridPane();
		layout.setHgap(10);
		layout.setVgap(5);
		layout.setPadding(new Insets(0, 5, 5, 5));

		scene = new Scene(layout, 850, 655);
		scenarioCreator.setScene(scene);
		scenarioCreator.setTitle("Scenario Creator");
		scene.setFill(Color.TRANSPARENT);
		layout.setBackground(
				new Background(new BackgroundFill(Color.gray(0.05, 0.8), CornerRadii.EMPTY, Insets.EMPTY)));
	}

	/*
	 * ---[ Scenario menu
	 * 
	 * new load save test
	 * 
	 */

	private void createScenarioMenu() {
		scenarioMenu = new Menu("Scenario");

		newProject = new MenuItem("New Scenario");
		loadProject = new MenuItem("Load Scenario");
		saveProject = new MenuItem("Save Scenario");
		testProject = new MenuItem("Test Scenario");
		scenarioMenu.getItems().add(newProject);
		scenarioMenu.getItems().add(loadProject);
		scenarioMenu.getItems().add(saveProject);
		scenarioMenu.getItems().add(testProject);
	}

	/*
	 * ---[ Section menu
	 * 
	 * save / clear
	 * 
	 */

	private void createSectionMenu() {
		sectionMenu = new Menu("Section");

		saveSection = new MenuItem("Save Section");
		clearSection = new MenuItem("Clear Section");
		sectionMenu.getItems().add(saveSection);
		sectionMenu.getItems().add(clearSection);
	}

	/*
	 * ---[ Go to Menu
	 * 
	 * Short cuts used to jump to text field or area
	 * 
	 */

	private void createGotoMenu() {

		goToMenu = new Menu("Go To");

		goToSectionName = new MenuItem("Section Name");
		goToAnswerButtonsUsed = new MenuItem("Answer Buttons Used");
		goToStory = new MenuItem("Story");
		goToBraille = new MenuItem("Braille");
		goToAnswer = new MenuItem("Answer");
		goToCorrect = new MenuItem("Correct");
		goToIncorrect = new MenuItem("Incorrect");
		goToMenu.getItems().add(goToSectionName);
		goToMenu.getItems().add(goToAnswerButtonsUsed);
		goToMenu.getItems().add(goToStory);
		goToMenu.getItems().add(goToBraille);
		goToMenu.getItems().add(goToAnswer);
		goToMenu.getItems().add(goToCorrect);
		goToMenu.getItems().add(goToIncorrect);
	}

	/*
	 * ---[ Sound menu
	 * 
	 * record import sound
	 * 
	 */

	private void createSoundMenu() {

		soundMenu = new Menu("Sound");
		addSound = new MenuItem("Add Sound");
		soundMenu.getItems().add(addSound);
	}

	/*
	 * ---[ menu bar
	 * 
	 * Scenario Section Go to Sound
	 * 
	 */
	private void createMenuBar() {

		menuBar = new MenuBar();
		menuBar.getMenus().addAll(scenarioMenu, sectionMenu, goToMenu, soundMenu);
		menuBar.setOpacity(0.7);
		layout.add(menuBar, 0, 0, 8, 1);
	}

	/*
	 * ---[ story text area
	 * 
	 */

	private void storyTextSetup() {
		story = new Text(" Story");
		story.setFont(Font.font("Arial", FontWeight.BOLD, 13));
		story.setFill(Color.WHITE);
		layout.add(story, 0, 2, 6, 1);

		storyText = new TextArea();
		storyText.setAccessibleRole(AccessibleRole.TOOLTIP);
		storyText.setAccessibleRoleDescription(
				"Story Text Area. Remember to let the learner know what buttons they can use on the braille cell. Press control tab to skip to next field");
		storyText.setPrefHeight(250);
		storyText.setPrefWidth(500);
		storyText.setOpacity(0.9);
		storyText.setWrapText(true);
		storyText.setEffect(borderGlow);
		layout.add(storyText, 0, 3, 8, 4);
	}

	// black text used for spacing purposes on the GUI

	private void blank() {
		blank1 = new Text("                                         ");
		blank1.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 10));
		layout.add(blank1, 6, 7);
	}

	/*
	 * ---[ save section button
	 * 
	 */

	private void saveSectionSetup() {

		saveButton = new Button("Save Section");
		saveButton.setAccessibleRoleDescription("Save button");
		saveButton.setAccessibleText("Press enter to save current section");
		saveButton.setStyle("-fx-base: #87ceeb;"); // sky blue
		layout.add(saveButton, 0, 19);
	}

	/*
	 * --- [correct / Incorrect text area
	 * 
	 */

	private void correctIncorrectSetup() {

		// Correct text area
		correct = new Text(" Correct");
		correct.setFont(Font.font("Arial", FontWeight.BOLD, 13));
		correct.setFill(Color.WHITE);
		layout.add(correct, 0, 8);
		correctText = new TextArea();
		correctText.setAccessibleRole(AccessibleRole.TOOLTIP);
		correctText.setAccessibleRoleDescription(
				"Correct Response text field. Write what you want played when the learner chooses the correct response. Press control tab to skip to next field");
		correctText.setPrefHeight(100);
		correctText.setPrefWidth(500);
		correctText.setOpacity(0.95);
		correctText.setWrapText(true);
		correctText.setEffect(borderGlow);
		layout.add(correctText, 0, 9, 8, 4);

		// Incorrect text area
		incorrect = new Text(" Incorrect");
		incorrect.setFont(Font.font("Arial", FontWeight.BOLD, 13));
		incorrect.setFill(Color.WHITE);
		layout.add(incorrect, 0, 14);

		incorrectText = new TextArea();
		incorrectText.setAccessibleRole(AccessibleRole.TOOLTIP);
		incorrectText.setAccessibleRoleDescription(
				"Incorrect Response text field. Write what you want played when the learner chooses the wrong response. Press control tab to skip to next field");
		incorrectText.setPrefHeight(100);
		incorrectText.setPrefWidth(500);
		incorrectText.setOpacity(0.95);
		incorrectText.setWrapText(true);
		incorrectText.setEffect(borderGlow);
		layout.add(incorrectText, 0, 15, 8, 3);
	}

	/*
	 * ---[ clear section button
	 * 
	 */

	private void clearSectionSetup() {

		clearSectionButton = new Button("Clear Section");
		clearSectionButton.setAccessibleRoleDescription("Clear Section button");
		clearSectionButton.setAccessibleText("Press enter to clear the section");
		clearSectionButton.setStyle("-fx-base: #87ceeb;"); // sky blue
		layout.add(clearSectionButton, 7, 19);
	}

	/*
	 * ---[ add sound button
	 * 
	 * opens (soundWindow) - allows user to record or import sound
	 * 
	 */

	private void soundButtonSetup() {

		sound = new Button("  Add Sound   ");
		sound.setAccessibleRoleDescription("Sound button");
		sound.setAccessibleText("Press enter to go to the Add Sound Menu");
		sound.setStyle("-fx-base: #87ceeb;"); // sky blue
		layout.add(sound, 7, 7);

		// sound button events
		sound.setOnMouseClicked(e -> {
			soundWindow.show();
		});

		sound.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				soundWindow.show();
			}
		});

		sound.setOnKeyPressed(e -> {
			new KeyCodeCombination(KeyCode.TAB, KeyCodeCombination.CONTROL_DOWN);
			correctText.requestFocus();
		});
	}

	/*
	 * ---[ answer text field
	 * 
	 * indicates the button number that corresponds to the correct answer
	 * 
	 */

	private void answerSetup() {

		answer = new Text("Answer");
		answer.setFont(Font.font("Arial", FontWeight.BOLD, 11.5));
		answer.setFill(Color.WHITE);
		layout.add(answer, 4, 7);

		answerText = new TextField();
		answerText.setAccessibleRole(AccessibleRole.TOOLTIP);
		answerText.setAccessibleRoleDescription(
				"Answer button field. Enter the button number learners should press for the correct response."
						+ " Make sure that the number is less than or equal to the number of answer buttons used");
		answerText.setPrefWidth(50);
		layout.add(answerText, 3, 7);

	}

	/*
	 * ---[ braille text field
	 * 
	 * indicates the letter / word that the braille cell should display
	 * 
	 */

	private void brailleInputSetup() {

		braille = new Text("Braille");
		braille.setFont(Font.font("Arial", FontWeight.BOLD, 11.5));
		braille.setFill(Color.GHOSTWHITE);
		layout.add(braille, 2, 7);

		brailleText = new TextField();
		brailleText.setAccessibleRole(AccessibleRole.TOOLTIP);
		brailleText.setAccessibleRoleDescription(
				"Braille text field. Enter the letter you want displayed on the braille cell");
		brailleText.setPrefWidth(40);
		layout.add(brailleText, 0, 7, 2, 1);

	}

	/*
	 * ---[ Section Name and Buttons used text field
	 * 
	 */

	private void sectionNameAndButtonsUsed() {

		// Section Name / Block title
		nameSectionField = new TextField();
		nameSectionField.setAccessibleRole(AccessibleRole.TOOLTIP);
		nameSectionField.setAccessibleRoleDescription(
				"Section name text field. Please enter a title for this section of your scenario.");
		nameSectionField.setPrefWidth(30);
		layout.add(nameSectionField, 0, 1);

		sectionName = new Text("Section Name");
		sectionName.setFont(Font.font("Arial", FontWeight.BOLD, 11.5));
		sectionName.setFill(Color.GHOSTWHITE);
		layout.add(sectionName, 1, 1, 2, 1);

		// number of answer buttons used
		answerButtonsUsedField = new TextField();
		answerButtonsUsedField.setAccessibleRole(AccessibleRole.TOOLTIP);
		answerButtonsUsedField.setAccessibleRoleDescription(
				"Answer Buttons Used number field. Please enter the number of buttons you want to use for this section. Make sure the number is less than or equal to the number of answer buttons available");
		answerButtonsUsedField.setPrefWidth(30);
		layout.add(answerButtonsUsedField, 3, 1);
		answerButtonsUsedText = new Text("Answer Buttons Used");
		answerButtonsUsedText.setFont(Font.font("Arial", FontWeight.BOLD, 11.5));
		answerButtonsUsedText.setFill(Color.GHOSTWHITE);
		layout.add(answerButtonsUsedText, 4, 1);

	}

	/*
	 * ---[ border glow for GUI
	 * 
	 * makes things prettier in the GUI
	 * 
	 */

	private void borderGlowSetup() {
		borderGlow = new DropShadow();
		int depth = 40;
		borderGlow.setColor(Color.LIGHTBLUE);
		borderGlow.setWidth(depth);
		borderGlow.setHeight(depth);
		borderGlow.setOffsetX(0f);
		borderGlow.setOffsetY(0f);
	}

	/*
	 * <GUI Test scenario, select visual or audio player>
	 * 
	 */

	private void visualOrAudioPlayerGUI() {

		playerSelectionWindow = new Stage();
		layout9 = new GridPane();
		layout9.setHgap(10);
		layout9.setVgap(10);
		layout9.setPadding(new Insets(0, 5, 5, 5));
		layout9.setBackground(
				new Background(new BackgroundFill(Color.gray(0.6, 0.8), CornerRadii.EMPTY, Insets.EMPTY)));

		scene9 = new Scene(layout9);
		playerSelectionWindow.setScene(scene9);
		playerSelectionWindow.setTitle("Player selection");
		playerSelectionText = new Text("		  Would you like to test with visual player or audio player?");
		layout9.add(playerSelectionText, 0, 1, 3, 1);

		playerLabel = new Label("Would you like to test with visual player or audio player?");
		playerLabel.setLabelFor(playerSelectionText);
		playerLabel.setVisible(false);
		layout9.add(playerLabel, 0, 1);

		final ToggleGroup group = new ToggleGroup();
		visualButton = new RadioButton("Visual Player");
		visualButton.setToggleGroup(group);
		visualButton.setAccessibleText("Press enter to select visual player");
		audioButton = new RadioButton("Audio Player");
		audioButton.setToggleGroup(group);
		audioButton.setAccessibleText("Press enter to select audio player");
		layout9.add(visualButton, 0, 2);
		layout9.add(audioButton, 1, 2);
	}

	/*
	 * <GUI New project : warning unsaved progress will be lost>
	 * 
	 * 
	 */

	private void newProjectWarningGUI() {

		warningWindow = new Stage();
		layout10 = new GridPane();
		layout10.setHgap(10);
		layout10.setVgap(10);
		layout10.setPadding(new Insets(5, 5, 5, 5));

		scene10 = new Scene(layout10);
		warningWindow.setScene(scene10);
		warningWindow.setTitle("Warning");
		warningText = new Text(
				"	       Are you sure you want to start a new project?\n			any unsaved projects will be lost");
		warningText.setFill(Color.WHITE);
		layout10.add(warningText, 0, 0, 2, 2);
		warningOkay = new Button("Okay");
		warningOkay.setStyle("-fx-base: #87ceeb;"); // sky blue
		warningOkay.setAccessibleRoleDescription("Okay button");
		warningOkay.setAccessibleText(
				"Are you sure you want to start a new project? any unsaved projects will be lost, press enter to continue");
		layout10.add(warningOkay, 0, 4);
		warningCancel = new Button("Cancel");
		warningCancel.setStyle("-fx-base: #ffffff;");
		warningCancel.setAccessibleRoleDescription("Cancel button");
		warningCancel.setAccessibleText("Press enter to return to main window");
		layout10.add(warningCancel, 2, 4);
		layout10.setBackground(
				new Background(new BackgroundFill(Color.gray(0.3, 0.8), CornerRadii.EMPTY, Insets.EMPTY)));
	}

	/*
	 * <GUI Confirm Section Saved>
	 * 
	 */

	private void savingSectionGUI() {

		saveWindow = new Stage();
		layout7 = new GridPane();
		layout7.setHgap(10);
		layout7.setVgap(10);
		layout7.setPadding(new Insets(0, 5, 5, 5));

		scene7 = new Scene(layout7);
		saveWindow.setScene(scene7);
		saveWindow.setTitle("Saved");
		saveConfirmed = new Text("This section has been saved");
		saveConfirmed.setFill(Color.WHITE);
		layout7.add(saveConfirmed, 0, 0);
		saveOkayButton = new Button("Okay");
		saveOkayButton.setStyle("-fx-base: #87ceeb;"); // sky blue
		layout7.add(saveOkayButton, 1, 1);
		layout7.setBackground(
				new Background(new BackgroundFill(Color.gray(0.3, 0.8), CornerRadii.EMPTY, Insets.EMPTY)));
		saveOkayButton.setAccessibleText("This section has been saved, press enter to return to main window");

		// action events
		saveOkayButton.setOnAction(e1 -> {
			saveWindow.close();
		});
		saveOkayButton.setOnKeyPressed(e2 -> {
			if (e2.getCode() == KeyCode.ENTER) {
				saveWindow.close();
			}
		});
	}

	/*
	 * <GUI Confirm Scenario Saved>
	 * 
	 */

	private void savingScenarioGUI() {

		// confirm save scenario
		scenarioSavedWindow = new Stage();
		layout13 = new GridPane();
		layout13.setHgap(10);
		layout13.setVgap(10);
		layout13.setPadding(new Insets(0, 5, 5, 5));

		scene13 = new Scene(layout13);
		scenarioSavedWindow.setScene(scene13);
		scenarioSavedWindow.setTitle("Saved");
		projectSavedConfirmed = new Text("This scenario has been saved");
		projectSavedConfirmed.setFill(Color.WHITE);
		layout13.add(projectSavedConfirmed, 0, 0);
		scenarioSavedOkay = new Button("Okay");
		scenarioSavedOkay.setStyle("-fx-base: #87ceeb;"); // sky blue
		layout13.add(scenarioSavedOkay, 1, 1);
		layout13.setBackground(
				new Background(new BackgroundFill(Color.gray(0.3, 0.8), CornerRadii.EMPTY, Insets.EMPTY)));
		scenarioSavedOkay.setAccessibleText("This scenario has been saved, press enter to return to main window");

		// action events
		scenarioSavedOkay.setOnAction(e1 -> {
			scenarioSavedWindow.close();
			LOGR.info("Scenario Saved");
		});
		scenarioSavedOkay.setOnKeyPressed(e2 -> {
			if (e2.getCode() == KeyCode.ENTER) {
				scenarioSavedWindow.close();
				LOGR.info("Scenario Saved");
			}
		});
	}

	/*
	 * <GUI Quick Access Scenario Menu>
	 * 
	 */

	private void scenarioMenuGUI() {

		// quick access to scenario menu
		scenarioWindow = new Stage();
		layout19 = new GridPane();
		layout19.setHgap(10);
		layout19.setVgap(10);
		layout19.setPadding(new Insets(10, 5, 5, 5));

		scene19 = new Scene(layout19, 210, 200);
		scenarioWindow.setScene(scene19);
		scenarioWindow.setTitle("Scenario Window");

		newScenarioButton = new Button("New Scenario");
		newScenarioButton.setStyle("-fx-base: #87ceeb;"); // sky blue
		newScenarioButton.setAccessibleText("Press enter to create a new scenario");
		layout19.add(newScenarioButton, 5, 0);

		loadScenarioButton = new Button("Load Scenario");
		loadScenarioButton.setStyle("-fx-base: #87ceeb;"); // sky blue
		loadScenarioButton.setAccessibleText("Press enter to load a saved scenario");
		layout19.add(loadScenarioButton, 5, 1);

		saveScenarioButton = new Button("Save Scenario");
		saveScenarioButton.setStyle("-fx-base: #87ceeb;"); // sky blue
		saveScenarioButton.setAccessibleText("Press enter to save your scenario");
		layout19.add(saveScenarioButton, 5, 2);

		testScenarioButton = new Button("Test Scenario");
		testScenarioButton.setStyle("-fx-base: #87ceeb;"); // sky blue
		newScenarioButton.setAccessibleText("Press enter to test a saved scenario");
		layout19.add(testScenarioButton, 5, 3);

		cancelScenarioButton = new Button("Cancel");
		cancelScenarioButton.setStyle("-fx-base: #87ceeb;"); // sky blue
		cancelScenarioButton.setAccessibleText("Press enter to go back to scenario creator");
		layout19.add(cancelScenarioButton, 5, 4);

		layout19.setBackground(
				new Background(new BackgroundFill(Color.gray(0.3, 0.8), CornerRadii.EMPTY, Insets.EMPTY)));

		newScenarioButton.setOnAction(e -> {
			warningWindow.show();
			scenarioWindow.close();
		});

		newScenarioButton.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				warningWindow.show();
				scenarioWindow.close();
			}
		});

		/*
		 * <GUI Load Scenario>
		 * 
		 */
		loadScenarioButton.setOnAction(e -> {

		});

		loadScenarioButton.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {

			}
		});

		/*
		 * .Save Scenario
		 * 
		 */

		saveScenarioButton.setOnAction(e -> {
			if (blockList.size() == 0) {
				noSectionsSavedWindow.show();
			} else {
				// send blocklist to printer == save txt file
				try {
					printer = new Printer(scenarioNameField.getText() + ".txt",
							Integer.parseInt(brailleCellsField.getText()),
							Integer.parseInt(answerButtonsField.getText()));
					printer.addBlockList(blockList);
					scenarioSavedWindow.show();
					printer.print();
					LOGR.info("Project Saved");
				} catch (IOException e3) {
					LOGR.warning("Failed or interrupted I/O operations");
				} catch (OddSpecialCharacterException e3) {
					LOGR.warning("Odd special character exception");
				} catch (InvalidBlockException e3) {
					LOGR.warning("Invalid input passed to Printer");
				} catch (InvalidCellException e1) {
					// TODO Auto-generated catch block
					LOGR.warning("Invalid input passed to Braille Interpreter");
				}
			}
			scenarioWindow.close();

		});

		saveScenarioButton.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				if (blockList.size() == 0) {
					noSectionsSavedWindow.show();
				} else {
					// send blocklist to printer == save txt file
					try {
						printer = new Printer(scenarioNameField.getText() + ".txt",
								Integer.parseInt(brailleCellsField.getText()),
								Integer.parseInt(answerButtonsField.getText()));
						printer.addBlockList(blockList);
						scenarioSavedWindow.show();
						printer.print();
						LOGR.info("Project Saved");
					} catch (IOException e3) {
						LOGR.warning("Failed or interrupted I/O operations");
					} catch (OddSpecialCharacterException e3) {
						LOGR.warning("Odd special character exception");
					} catch (InvalidBlockException e3) {
						LOGR.warning("Invalid input passed to Printer");
					} catch (InvalidCellException e1) {
						LOGR.warning("Invalid input passed to Braille Interpreter");
					}
				}
			}
			scenarioWindow.close();

		});

		/*
		 * .test scenario
		 * 
		 */

		testScenarioButton.setOnAction(e -> {
			runTest(brailleCellsUsedWindow, playerSelectionWindow, visualButton, audioButton);
			scenarioWindow.close();
		});

		testScenarioButton.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				runTest(brailleCellsUsedWindow, playerSelectionWindow, visualButton, audioButton);
				scenarioWindow.close();
			}
		});

		cancelScenarioButton.setOnAction(e -> {
			scenarioWindow.close();

		});

		cancelScenarioButton.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				scenarioWindow.close();
			}
		});

	}

	/*
	 * <GUI Warning : Clear section -> unsaved progress lost
	 * 
	 */

	private void clearSectionWarningGUI() {

		clearSectionWarning = new Stage();
		layout15 = new GridPane();
		layout15.setHgap(10);
		layout15.setVgap(10);
		layout15.setPadding(new Insets(0, 5, 5, 5));

		scene15 = new Scene(layout15);
		clearSectionWarning.setScene(scene15);
		clearSectionWarning.setTitle("Warning");
		clearSectionText = new Text(
				"                 Selecting okay will clear all text fields, all unsaved progress will be lost");
		clearSectionText.setFill(Color.WHITE);
		layout15.add(clearSectionText, 0, 0);
		clearSectionButtonOkay = new Button("Okay");
		clearSectionButtonOkay.setStyle("-fx-base: #87ceeb;"); // sky blue
		clearSectionButtonOkay.setAccessibleRoleDescription("Okay Button");
		clearSectionButtonOkay.setAccessibleText(
				"Selecting okay will clear all text fields, all unsaved progress will be lost, press enter to clear all fields and return to previous screen");
		layout15.add(clearSectionButtonOkay, 0, 1);
		clearSectionButtonCancel = new Button("Cancel");
		clearSectionButtonCancel.setAccessibleRoleDescription("Cancel Button");

		clearSectionButtonCancel.setStyle("-fx-base: #ffffff;");
		clearSectionButtonCancel.setAccessibleText("Press enter to return to previous screen");
		layout15.add(clearSectionButtonCancel, 1, 1);
		layout15.setBackground(
				new Background(new BackgroundFill(Color.gray(0.3, 0.8), CornerRadii.EMPTY, Insets.EMPTY)));

		// action events
		clearSectionButtonOkay.setOnAction(e1 -> {
			clearSection();
			LOGR.info("Section cleared / new section created");
		});

		clearSectionButtonOkay.setOnKeyPressed(e2 -> {
			if (e2.getCode() == KeyCode.ENTER) {
				clearSection();
				LOGR.info("Section cleared / new section created");
			}
		});

		clearSectionButtonCancel.setOnAction(e1 -> {
			clearSectionWarning.close();
		});
		clearSectionButtonCancel.setOnKeyPressed(e2 -> {
			if (e2.getCode() == KeyCode.ENTER) {
				clearSectionWarning.close();
			}
		});
	}

	/*
	 * <GUI Warning : Save Scenario -> but no section saved
	 * 
	 */

	private void noSectionErrorGUI() {

		noSectionsSavedWindow = new Stage();
		layout14 = new GridPane();
		layout14.setHgap(10);
		layout14.setVgap(10);
		layout14.setPadding(new Insets(8, 5, 5, 5));

		scene14 = new Scene(layout14);
		noSectionsSavedWindow.setScene(scene14);
		noSectionsSavedWindow.setTitle("Error");
		noSectionsSaved = new Text(
				"   Scenario can only be saved after you have created and saved at least one section");
		noSectionsSaved.setFill(Color.WHITE);
		layout14.add(noSectionsSaved, 0, 0, 2, 1);
		noSectionSavedOkay = new Button("Okay");
		noSectionSavedOkay.setStyle("-fx-base: #87ceeb;"); // sky blue
		noSectionSavedOkay.setAccessibleRoleDescription("Okay button");
		noSectionSavedOkay.setAccessibleText(
				"Scenario can only be saved after you have created and saved at least one section, press enter to go back to main window and start writing");
		layout14.add(noSectionSavedOkay, 2, 1);
		layout14.setBackground(
				new Background(new BackgroundFill(Color.gray(0.3, 0.8), CornerRadii.EMPTY, Insets.EMPTY)));

		// action event
		noSectionSavedOkay.setOnAction(e -> {
			noSectionsSavedWindow.close();
		});
		noSectionSavedOkay.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				noSectionsSavedWindow.close();
			}
		});
	}

	/*
	 * <GUI Warning : Story Field is Empty
	 * 
	 * 
	 */

	private void storyEmptyErrorGUI() {

		emptyStoryWindow = new Stage();
		layout5 = new GridPane();
		layout5.setHgap(10);
		layout5.setVgap(10);
		layout5.setPadding(new Insets(5, 5, 5, 5));

		scene5 = new Scene(layout5);
		emptyStoryWindow.setScene(scene5);
		emptyStoryWindow.setTitle("Error");
		emptyStoryText = new Text("The Story field needs to contain more than 15 characters.");
		emptyStoryText.setFill(Color.WHITE);
		layout5.add(emptyStoryText, 0, 0, 2, 1);
		emptyStoryOkay = new Button("Okay");
		emptyStoryOkay.setStyle("-fx-base: #87ceeb;"); // sky blue
		emptyStoryOkay.setAccessibleRoleDescription("Okay button");
		emptyStoryOkay.setAccessibleText(
				"Section can not be saved. The Story field has less than 15 characters. Come on, make it exciting! Use your words.");
		layout5.add(emptyStoryOkay, 2, 1);
		layout5.setBackground(
				new Background(new BackgroundFill(Color.gray(0.3, 0.8), CornerRadii.EMPTY, Insets.EMPTY)));

		// action event
		emptyStoryOkay.setOnAction(e -> {
			emptyStoryWindow.close();
		});
		emptyStoryOkay.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				emptyStoryWindow.close();
			}
		});
	}

	/*
	 * <GUI warning : answer button and answer buttons used fields are filled out
	 * incorrectly
	 * 
	 * 
	 */

	private void buttonsUsedErrorGUI() {

		buttonsUsedWindow = new Stage();
		layout8 = new GridPane();
		layout8.setHgap(10);
		layout8.setVgap(10);
		layout8.setPadding(new Insets(0, 5, 5, 5));

		scene8 = new Scene(layout8);
		buttonsUsedWindow.setScene(scene8);
		buttonsUsedWindow.setTitle("Error");
		buttonsUsedError = new Text("The Answer Buttons Used and Answer fields need to contain a number");
		buttonsUsedError.setFill(Color.WHITE);
		layout8.add(buttonsUsedError, 0, 0);
		layout8.setBackground(
				new Background(new BackgroundFill(Color.gray(0.3, 0.8), CornerRadii.EMPTY, Insets.EMPTY)));

		buttonsUsedWindowOkay = new Button("Okay");
		buttonsUsedWindowOkay.setStyle("-fx-base: #87ceeb;"); // sky blue
		layout8.add(buttonsUsedWindowOkay, 1, 1);
		buttonsUsedWindowOkay.setAccessibleText(
				"Section can not be saved. The Answer Buttons Used and Answer fields need to contain a number. press enter to return to main window and fill out those fields!");

		// action events
		buttonsUsedWindowOkay.setOnAction(e1 -> {
			buttonsUsedWindow.close();
		});
		buttonsUsedWindowOkay.setOnKeyPressed(e2 -> {
			if (e2.getCode() == KeyCode.ENTER) {
				buttonsUsedWindow.close();
			}
		});
	}

	/*
	 * <GUI Warning : section name field is empty
	 * 
	 */

	private void sectionEmptyErrorGUI() {

		emptyNameWindow = new Stage();
		layout6 = new GridPane();
		layout6.setHgap(10);
		layout6.setVgap(10);
		layout6.setPadding(new Insets(0, 5, 5, 5));

		scene6 = new Scene(layout6);
		emptyNameWindow.setScene(scene6);
		emptyNameWindow.setTitle("Error");
		emptyName = new Text("The section needs a name");
		emptyName.setFill(Color.WHITE);
		layout6.add(emptyName, 0, 0);
		emptyNameButton = new Button("Okay");
		emptyNameButton.setStyle("-fx-base: #87ceeb;"); // sky blue
		layout6.add(emptyNameButton, 1, 1);
		layout6.setBackground(
				new Background(new BackgroundFill(Color.gray(0.3, 0.8), CornerRadii.EMPTY, Insets.EMPTY)));
		emptyNameButton.setAccessibleRoleDescription("Okay button");
		emptyNameButton.setAccessibleText(
				"Section can not be saved. The section needs a name, press enter to return to main window and "
						+ "fill out the section name field.");

		// action event
		emptyNameButton.setOnAction(e1 -> {
			emptyNameWindow.close();
		});
		emptyNameButton.setOnKeyPressed(e2 -> {
			if (e2.getCode() == KeyCode.ENTER) {
				emptyNameWindow.close();
			}
		});
	}

	/*
	 * <GUI warning : braille field troubles...
	 * 
	 * 
	 */

	private void brailleFieldErrorGUI() {

		brailleWindow = new Stage();
		layout4 = new GridPane();
		layout4.setHgap(10);
		layout4.setVgap(10);
		layout4.setPadding(new Insets(5, 5, 5, 5));

		scene4 = new Scene(layout4);
		brailleWindow.setScene(scene4);
		brailleWindow.setTitle("Error");
		brailleEntry = new Text("The braille field can not be empty and can only contain a letter");
		brailleEntry.setFill(Color.WHITE);
		layout4.add(brailleEntry, 0, 0, 2, 1);
		brailleOkay = new Button("Okay");
		brailleOkay.setStyle("-fx-base: #87ceeb;"); // sky blue
		brailleOkay.setAccessibleRoleDescription("Okay button");
		brailleOkay.setAccessibleText(
				"Section can not be saved. The braille field can not be empty and can only contain a letter, press enter to return to main window");
		layout4.add(brailleOkay, 2, 1);
		layout4.setBackground(
				new Background(new BackgroundFill(Color.gray(0.3, 0.8), CornerRadii.EMPTY, Insets.EMPTY)));

		// action events
		brailleOkay.setOnAction(e1 -> {
			brailleWindow.close();
		});
		brailleOkay.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				brailleWindow.close();
			}
		});
	}

	/*
	 * <GUI warning : sound import error
	 * 
	 * 
	 */

	private void soundErrorWindowGUI() {
		soundErrorWindow = new Stage();
		layout16 = new GridPane();
		layout16.setHgap(10);
		layout16.setVgap(10);
		layout16.setPadding(new Insets(5, 5, 5, 5));
		layout16.setBackground(
				new Background(new BackgroundFill(Color.gray(0.3, 0.8), CornerRadii.EMPTY, Insets.EMPTY)));

		scene16 = new Scene(layout16);
		soundErrorWindow.setScene(scene16);
		soundErrorWindow.setTitle("Sound Import Error");
		soundErrorText = new Text("The imported sound file needs to be in .wav format");
		soundErrorText.setFill(Color.WHITE);
		layout16.add(soundErrorText, 0, 0, 2, 1);
		soundErrorButton = new Button("Okay");
		soundErrorButton.setStyle("-fx-base: #87ceeb;"); // sky blue
		soundErrorButton.setAccessibleRoleDescription("Okay button");
		soundErrorButton.setAccessibleText(
				"The imported sound file needs to be in .wav format, press enter to return to previous window, and choose a groovy tune.");
		layout16.add(soundErrorButton, 2, 1);

		// action button for answer okay
		soundErrorButton.setOnAction(e -> {
			soundErrorWindow.close();
			copySoundFile();
		});
		soundErrorButton.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				soundErrorWindow.close();
				copySoundFile();
			}
		});
	}

	/*
	 * <GUI warning : record sound needs name
	 * 
	 */

	private void nameSoundErrorGUI() {

		nameSoundErrorWindow = new Stage();
		layout18 = new GridPane();
		layout18.setHgap(10);
		layout18.setVgap(10);
		layout18.setPadding(new Insets(0, 5, 5, 5));

		scene18 = new Scene(layout18);
		nameSoundErrorWindow.setScene(scene18);
		nameSoundErrorWindow.setTitle("Error");
		soundNameErrorText = new Text("Sound file can not be saved unless it has a name");
		soundNameErrorText.setFill(Color.WHITE);
		layout18.add(soundNameErrorText, 0, 0);
		nameSoundErrorButton = new Button("Okay");
		nameSoundErrorButton.setStyle("-fx-base: #87ceeb;"); // sky blue
		layout18.add(nameSoundErrorButton, 1, 1);
		layout18.setBackground(
				new Background(new BackgroundFill(Color.gray(0.3, 0.8), CornerRadii.EMPTY, Insets.EMPTY)));
		nameSoundErrorButton.setAccessibleRoleDescription("Okay button");
		nameSoundErrorButton.setAccessibleText(
				"Sound file can not be saved unless it has a name, press enter to return to previous window, and name that funky sound");

		// action event
		nameSoundErrorButton.setOnAction(e1 -> {
			nameSoundErrorWindow.close();
			nameSoundFileWindow.show();
		});
		nameSoundErrorButton.setOnKeyPressed(e2 -> {
			if (e2.getCode() == KeyCode.ENTER) {
				nameSoundErrorWindow.close();
				nameSoundFileWindow.show();
			}
		});
	}

	/*
	 * <GUI warning : incorrect input, answer and answer buttons used field
	 * 
	 * 
	 */
	private void notANumberWindowGUI() {
		notANumberWindow = new Stage();
		layout3 = new GridPane();
		layout3.setHgap(10);
		layout3.setVgap(10);
		layout3.setPadding(new Insets(5, 5, 5, 5));
		layout3.setBackground(
				new Background(new BackgroundFill(Color.gray(0.3, 0.8), CornerRadii.EMPTY, Insets.EMPTY)));

		scene3 = new Scene(layout3);
		notANumberWindow.setScene(scene3);
		notANumberWindow.setTitle("Error");
		answerIsNumber = new Text(
				"The answer field needs to contain a number that is less than the number of answer buttons available");
		answerIsNumber.setFill(Color.WHITE);
		layout3.add(answerIsNumber, 0, 0, 2, 1);
		answerOkay = new Button("Okay");
		answerOkay.setStyle("-fx-base: #87ceeb;"); // sky blue
		answerOkay.setAccessibleRoleDescription("Okay button");
		answerOkay.setAccessibleText(
				"Section can not be saved. The answer field needs to contain a number that is less than the number of answer buttons available press enter to return to main window");
		layout3.add(answerOkay, 2, 1);

		// action button for answer okay
		answerOkay.setOnAction(e -> {
			notANumberWindow.close();
		});
		answerOkay.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				notANumberWindow.close();
			}
		});
	}

	/**
	 * <GUI Warning - starting window missing fields
	 * 
	 */

	private void nameOrBrailleFieldEmptyWindow() {

		errorWindow = new Stage();
		layout12 = new GridPane();
		layout12.setHgap(10);
		layout12.setVgap(10);
		layout12.setPadding(new Insets(5, 5, 5, 5));
		layout12.setBackground(
				new Background(new BackgroundFill(Color.gray(0.4, 0.8), CornerRadii.EMPTY, Insets.EMPTY)));

		scene12 = new Scene(layout12);
		errorWindow.setScene(scene12);
		errorWindow.setTitle("Error");
		errorMessage = new Text(
				"You need to have a scenario name, at least one braille cell and one answer button to start creating a scenario");
		errorMessage.setFill(Color.WHITE);
		layout12.add(errorMessage, 0, 0, 2, 1);
		errorMessageButton = new Button("Okay");
		errorMessageButton.setStyle("-fx-base: #87ceeb;"); // sky blue
		errorMessageButton.setAccessibleRoleDescription("Okay button");
		errorMessageButton.setAccessibleText(
				"Make sure you have given your scenario a name, that the braille cell field contains a number higher than one "
						+ "and that the answer buttons available field contains a number higher than one press enter to return to previous window");
		layout12.add(errorMessageButton, 2, 1);

		// action event
		errorMessageButton.setOnAction(e1 -> {
			errorWindow.close();
		});
		errorMessageButton.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				errorWindow.close();
			}
		});
	}

	/*
	 * <GUI Add sound
	 * 
	 */

	private void soundGUISetup() {

		soundWindow = new Stage();
		soundWindow.setTitle("Sound Menu");
		layout2 = new GridPane();
		layout2.setHgap(10);
		layout2.setVgap(10);
		layout2.setPadding(new Insets(20, 20, 20, 20));
		layout2.setBackground(
				new Background(new BackgroundFill(Color.gray(0.5, 0.8), CornerRadii.EMPTY, Insets.EMPTY)));

		scene2 = new Scene(layout2);
		soundWindow.setScene(scene2);
		soundRecord = new Button("Record sound");
		soundRecord.setMinSize(150, 70);
		soundRecord.setStyle("-fx-base: #87ceeb;"); // sky blue
		soundRecord.setAccessibleRoleDescription("Record sound button");
		soundRecord.setAccessibleText("Press enter to record your own sound");
		layout2.add(soundRecord, 0, 1);
		soundImport = new Button("Import sound");
		soundImport.setMinSize(150, 70);
		soundImport.setStyle("-fx-base: #87ceeb;"); // sky blue
		soundImport.setAccessibleRoleDescription("Import sound button");
		soundImport.setAccessibleText("Press enter to import a sound file");
		layout2.add(soundImport, 3, 1);
		soundExit = new Button("Exit");
		soundExit.setStyle("-fx-base: #ffffff"); // sky blue
		soundExit.setAccessibleRoleDescription("Exit sound window button");
		soundExit.setAccessibleText("Press enter to exit sound window");
		layout2.add(soundExit, 6, 1);

		// action button for answer okay
		soundExit.setOnAction(e -> {
			soundWindow.close();
		});
		soundExit.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				soundWindow.close();
			}
		});

		// Action Listener for soundImport
		soundImport.setOnAction(e -> {
			copySoundFile();
			soundWindow.close();
		});

		soundImport.setOnKeyReleased(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				copySoundFile();
				soundWindow.close();
			}
		});

		soundRecord.setOnAction(e -> {
			soundWindow.close();
			nameSoundFileWindow.show();
		});

		soundRecord.setOnKeyReleased(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				soundWindow.close();
				nameSoundFileWindow.show();
			}

		});

	}

	/**
	 * <GUI name sound file
	 * 
	 * 
	 */

	private void nameSoundFileGUI() {

		nameSoundFileWindow = new Stage();
		nameSoundFileWindow.setTitle("Name Sound File");
		layout17 = new GridPane();
		layout17.setHgap(10);
		layout17.setVgap(10);
		layout17.setPadding(new Insets(20, 20, 20, 20));
		layout17.setBackground(
				new Background(new BackgroundFill(Color.gray(0.5, 0.8), CornerRadii.EMPTY, Insets.EMPTY)));

		scene17 = new Scene(layout17);
		nameSoundFileWindow.setScene(scene17);
		soundNameText = new Text("Name your sound file");
		soundNameText.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		soundNameText.setFill(Color.GHOSTWHITE);
		layout17.add(soundNameText, 0, 0);

		soundNameField = new TextField();
		soundNameField.setAccessibleRole(AccessibleRole.TOOLTIP);
		soundNameField.setAccessibleRoleDescription("Name sound file text field, please name your sound file");
		soundNameField.setPrefWidth(70);
		layout17.add(soundNameField, 0, 1, 2, 1);

		soundNameOkay = new Button("Okay");
		soundNameOkay.setStyle("-fx-base: #87ceeb;"); // sky blue
		soundNameOkay.setAccessibleRoleDescription("Okay button");
		soundNameOkay.setAccessibleText("Press enter to confirm sound file name");
		layout17.add(soundNameOkay, 0, 2);

		soundNameCancel = new Button("Exit");
		soundNameCancel.setStyle("-fx-base: #ffffff"); // white
		soundNameCancel.setAccessibleRoleDescription("Exit button");
		soundNameCancel.setAccessibleText("Press enter to return to main window");
		layout17.add(soundNameCancel, 1, 2);

		// action events
		soundNameOkay.setOnAction(e -> {
			if (soundNameField.getText().length() == 0) {
				nameSoundErrorWindow.show();
			} else {
				recordWindow.show();
				nameSoundFileWindow.close();
			}
		});

		soundNameOkay.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				if (soundNameField.getText().length() == 0) {
					nameSoundErrorWindow.show();
				} else {
					recordWindow.show();
					nameSoundFileWindow.close();
				}
			}
		});

		soundNameCancel.setOnAction(e -> {
			nameSoundFileWindow.close();
		});

		soundNameCancel.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				nameSoundFileWindow.close();
			}
		});

	}

	/**
	 * <GUI record sound window
	 * 
	 * 
	 */

	private void recordSoundGUI() {
		recordWindow = new Stage();
		recordWindow.setTitle("Record");
		recordLayout = new GridPane();
		recordLayout.setHgap(10);
		recordLayout.setVgap(10);
		recordLayout.setPadding(new Insets(20, 20, 20, 20));
		recordLayout.setBackground(
				new Background(new BackgroundFill(Color.gray(0.5, 0.8), CornerRadii.EMPTY, Insets.EMPTY)));
		recordScene = new Scene(recordLayout);
		recordWindow.setScene(recordScene);
		record = new Button("Start Recording");
		record.setMinSize(150, 70);
		record.setStyle("-fx-base: #87ceeb;"); // sky blue
		record.setAccessibleRoleDescription("Recording sound button");
		record.setAccessibleText("Press enter to start recording sound. Press enter again to stop recording");
		recordLayout.add(record, 0, 1);
		exitButton = new Button("Exit");
		exitButton.setStyle("-fx-base: #ffffff"); // white
		exitButton.setAccessibleRoleDescription("Exit sound window button");
		exitButton.setAccessibleText("Press enter to exit sound window");

		recordLayout.add(exitButton, 3, 1);
		exitButton.setOnAction(e -> {
			recordWindow.close();
		});

		exitButton.setOnKeyReleased(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				recordWindow.close();
			}
		});

		// Name of file needs to go in this constructor
		String nameOfSoundFile = soundNameField.getText();
		SoundRecorder recorder = new SoundRecorder(nameOfSoundFile);

		record.setOnAction(e -> {
			recorder.record();
			LOGR.info("Sound file recorded");
		});

		record.setOnKeyReleased(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				recorder.record();
				LOGR.info("Sound file recorded");
			}
		});
	}

	/*
	 * ----------<[other GUIs
	 * 
	 * 
	 */

	/**
	 * add sound Credit for most of this method:
	 * 
	 * https://www.journaldev.com/861/java-copy-file
	 */
	private void copySoundFile() {
		soundWindow.close();
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Import Sound File");
		File source = fileChooser.showOpenDialog(scenarioCreator);

		if (source.getName().contains(".wav")) {
			File dest = new File("./AudioFiles/" + source.getName());
			InputStream is = null;
			OutputStream os = null;
			try {
				is = new FileInputStream(source);
				os = new FileOutputStream(dest);
				byte[] buffer = new byte[1024];
				int length;
				while ((length = is.read(buffer)) > 0) {
					os.write(buffer, 0, length);
					LOGR.info("Sound file imported");
				}
			} catch (IOException e1) {
				LOGR.warning("Failed or interrupted I/O operation");
			} finally {
				try {
					is.close();
					os.close();
				} catch (IOException e1) {
					LOGR.warning("Failed or interrupted I/O operation");
				}
			}
		} else {
			soundErrorWindow.show();
			LOGR.warning("Sound import failed, file was not of the format .wav");
		}

	}

	/**
	 * <GUI Starting window
	 * 
	 * 
	 * enter scenario name number of braille cells available number of answer
	 * buttons available
	 * 
	 * 
	 */

	private void setupScenarioGUI(Stage primaryStage) {

		brailleCellsUsedWindow = new Stage();
		layout11 = new GridPane();
		layout11.setHgap(10);
		layout11.setVgap(10);
		layout11.setPadding(new Insets(5, 5, 10, 5));

		scene11 = new Scene(layout11);
		brailleCellsUsedWindow.setScene(scene11);
		brailleCellsUsedWindow.setTitle("Scenario Setup");
		scenarioNameField = new TextField();
		scenarioNameField.setAccessibleRole(AccessibleRole.TOOLTIP);
		scenarioNameField.setAccessibleRoleDescription("Scenario name Field");
		scenarioNameText = new Text("Scenario Name");
		nameBrailleAnswer = new Text(
				"Enter the name of your scenario, the number of Braille Cells and Answer Buttons available");
		layout11.add(nameBrailleAnswer, 0, 0, 2, 1);
		brailleCellsField = new TextField();
		brailleCellsField.setAccessibleRole(AccessibleRole.TOOLTIP);
		brailleCellsField.setAccessibleRoleDescription("braille cells available Field, please enter a number");
		brailleCellsText = new Text("Braille Cells Available");
		answerButtonsField = new TextField();
		answerButtonsField.setAccessibleRole(AccessibleRole.TOOLTIP);
		answerButtonsField.setAccessibleRoleDescription("Answer buttons available field, please enter a number");
		answerButtonsText = new Text("Answer Buttons Available");
		layout11.add(scenarioNameField, 0, 1);
		layout11.add(scenarioNameText, 1, 1);
		layout11.add(brailleCellsField, 0, 2);
		layout11.add(brailleCellsText, 1, 2);
		layout11.add(answerButtonsField, 0, 3);
		layout11.add(answerButtonsText, 1, 3);
		layout11.setBackground(
				new Background(new BackgroundFill(Color.gray(0.5, 0.8), CornerRadii.EMPTY, Insets.EMPTY)));

		scenarioNameFieldLabel = new Label("Enter the \nname of \nyour scenario");
		scenarioNameFieldLabel.setLabelFor(scenarioNameField);
		scenarioNameFieldLabel.setVisible(false);
		layout11.add(scenarioNameFieldLabel, 0, 1);

		brailleCellsUsedLabel = new Label("Enter the \nnumber of \nBraille cells available");
		brailleCellsUsedLabel.setLabelFor(brailleCellsField);
		brailleCellsUsedLabel.setVisible(false);
		layout11.add(brailleCellsUsedLabel, 0, 2);

		answerButtonsUsedLabel = new Label("Enter the \nnumber of \nanswer buttons available");
		answerButtonsUsedLabel.setLabelFor(answerButtonsField);
		answerButtonsUsedLabel.setVisible(false);
		layout11.add(answerButtonsUsedLabel, 0, 3);

		okayStart = new Button("Create");
		okayStart.setStyle("-fx-base: #87ceeb;"); // sky blue
		okayStart.setAccessibleRoleDescription("Okay button");
		okayStart.setAccessibleText("Press enter to start creating a scenario");
		layout11.add(okayStart, 2, 3);

		okayStart.setOnAction(e -> {

			nameNewScenario(scenarioCreator, errorWindow, brailleCellsUsedWindow, scenarioNameField, brailleCellsField,
					answerButtonsField);
		});

		okayStart.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {

				nameNewScenario(scenarioCreator, errorWindow, brailleCellsUsedWindow, scenarioNameField,
						brailleCellsField, answerButtonsField);
			}
		});

		// action listeners

		createButton.setOnAction(e1 -> {
			brailleCellsUsedWindow.show();
			primaryStage.close();
		});
		createButton.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				brailleCellsUsedWindow.show();
				primaryStage.close();
			}
		});
	}

	/*
	 * ---[ scenario menu button
	 */

	private void scenarioMenuButton() {
		// manage scenario button
		scenarioMenuButton = new Button("Scenario Menu");
		scenarioMenuButton.setAccessibleRoleDescription("Scenario Menu");
		scenarioMenuButton.setAccessibleText("Press enter to go to the scenario menu");
		scenarioMenuButton.setStyle("-fx-base: #FFFFFF;"); // white
		layout.add(scenarioMenuButton, 7, 1);

		scenarioMenuButton.setOnAction(e1 -> {
			scenarioWindow.show();
		});
		createButton.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				scenarioWindow.show();
			}
		});
	}

	/*
	 * .comboBox
	 * 
	 */

	private void createSectionComboBox() {
		// ComboBox (drop down menu)
		comboBoxList = FXCollections.observableArrayList();
		comboBox = new ComboBox<String>(comboBoxList);
		comboBox.setPrefWidth(200);
		comboBox.setPromptText("Select a section");
		comboBoxList.add(0, "New Section");
		layout.add(comboBox, 9, 0, 5, 1);
	}

	private void comboBoxOpen() {
		comboBox.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.DOWN) {
				comboBox.show();
			}
		});
	}

	/*
	 * main method
	 */

	public static void main(String[] args) {

		// ---[ logger

		FileHandler fh;
		try {
			fh = new FileHandler("The log of doom", true);
			fh.setLevel(Level.FINE);
			LOGR.addHandler(fh);
		} catch (SecurityException | IOException e) {
			LOGR.severe("Security Violation");
		}

		// Inherited method from Application that lunches GUI - JavaFx
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		createPrimaryStage();
		createScenarioMenu();
		createSectionMenu();
		createGotoMenu();
		createSoundMenu();
		createMenuBar();
		borderGlowSetup();
		sectionNameAndButtonsUsed();
		storyTextSetup();
		brailleInputSetup();
		answerSetup();
		soundButtonSetup();
		correctIncorrectSetup();
		saveSectionSetup();
		clearSectionSetup();
		blank();
		createSectionComboBox();
		scenarioMenuButton();
		comboBoxOpen();

		/*
		 *
		 * --------------------<{Other Scenes and Action Events
		 * 
		 */

		setupScenarioGUI(primaryStage);
		soundGUISetup();
		nameSoundFileGUI();
		recordSoundGUI();
		nameSoundErrorGUI();
		scenarioMenuGUI();
		visualOrAudioPlayerGUI();

		// Scene
		primaryStage.setTitle("Welcome");
		primaryStage.setScene(scene1);
		scene1.setFill(Color.TRANSPARENT);
		primaryStage.show();
		layout1.setBackground(
				new Background(new BackgroundFill(Color.gray(0.05, 0.6), CornerRadii.EMPTY, Insets.EMPTY)));

		// -----------------------<<[warnings

		/**
		 * GUI for errors
		 * 
		 * 
		 * 
		 */
		nameOrBrailleFieldEmptyWindow();
		notANumberWindowGUI();
		brailleFieldErrorGUI();
		sectionEmptyErrorGUI();
		buttonsUsedErrorGUI();
		storyEmptyErrorGUI();
		noSectionErrorGUI();
		clearSectionWarningGUI();
		soundErrorWindowGUI();

		/////////////////////////////////////////////////////////////////////////////////////////

		savingScenarioGUI();
		newProjectWarningGUI();
		savingSectionGUI();

		// ---------<<[ other GUIs

		/*
		 * -----<<[save section button
		 * 
		 * 
		 * 
		 */

		saveButton.setOnMouseClicked(e -> {

			saveSection(nameSectionField, answerButtonsUsedField, storyText, brailleText, answerText, correctText,
					incorrectText, comboBoxList, comboBox, brailleCellsField, answerButtonsField, notANumberWindow,
					brailleWindow, emptyNameWindow, buttonsUsedWindow, emptyStoryWindow, saveWindow);
		});

		saveButton.setOnKeyPressed(e -> {

			if (e.getCode() == KeyCode.ENTER) {

				saveSection(nameSectionField, answerButtonsUsedField, storyText, brailleText, answerText, correctText,
						incorrectText, comboBoxList, comboBox, brailleCellsField, answerButtonsField, notANumberWindow,
						brailleWindow, emptyNameWindow, buttonsUsedWindow, emptyStoryWindow, saveWindow);
			}
		});

		saveButton.setOnKeyPressed(e -> {
			new KeyCodeCombination(KeyCode.TAB, KeyCodeCombination.CONTROL_DOWN);
			clearSectionButton.requestFocus();
		});

		/**
		 * clear section button
		 * 
		 * 
		 * 
		 */
		clearSectionButton.setOnMouseClicked(e -> {
			clearSectionWarning.show();
		});

		clearSectionButton.setOnKeyPressed(e -> {

			if (e.getCode() == KeyCode.ENTER) {
				clearSectionWarning.show();
			}
		});

		clearSectionButton.setOnKeyPressed(e -> {
			new KeyCodeCombination(KeyCode.TAB, KeyCodeCombination.CONTROL_DOWN);
			comboBox.requestFocus();
		});

		/**
		 * return selected comboBox value
		 *
		 *
		 *
		 */
		comboBox.getSelectionModel().selectedIndexProperty().addListener(e -> {

			if (comboBox.getValue() == "New Section") {

				clearSectionWarning.show();

			} else {

				for (int j = 0; j < blockList.size(); j++) {
					if (comboBox.getValue() == blockList.get(j).name) {
						nameSectionField.setText((blockList.get(j).name));
						storyText.setText(blockList.get(j).story);
						correctText.setText(blockList.get(j).correctResponse);
						incorrectText.setText(blockList.get(j).wrongResponse);
						brailleText.setText(blockList.get(j).cells);
						answerText.setText(Integer.toString(blockList.get(j).answer));
						answerButtonsUsedField.setText(Integer.toString(blockList.get(j).buttonsUsed));

					}
				}

			}

		});

		comboBox.setOnKeyPressed(e -> {
			new KeyCodeCombination(KeyCode.TAB, KeyCodeCombination.CONTROL_DOWN);
			scenarioMenuButton.requestFocus();
		});

		/**
		 * scenario menu button
		 */
		scenarioMenuButton.setOnKeyPressed(e -> {
			new KeyCodeCombination(KeyCode.TAB, KeyCodeCombination.CONTROL_DOWN);
			nameSectionField.requestFocus();
		});

		/*
		 * ---------<{scenario menu action
		 * 
		 * 
		 */

		// File Menu Selection : new project
		newProject.setOnAction(e -> {
			warningWindow.show();
		});

		// hot key new project
		newProject.setAccelerator(
				new KeyCodeCombination(KeyCode.N, KeyCodeCombination.CONTROL_DOWN, KeyCodeCombination.ALT_DOWN));

		// warning window okay button pressed
		warningOkay.setOnAction(e -> {
			scenarioCreator.close();
			warningWindow.close();
			brailleCellsUsedWindow.show();
			scenarioNameField.clear();
			brailleCellsField.clear();
			answerButtonsField.clear();
			clearSection();
		});

		warningOkay.setOnKeyPressed(e1 -> {
			if (e1.getCode() == KeyCode.ENTER) {
				scenarioCreator.close();
				warningWindow.close();
				brailleCellsUsedWindow.show();
				scenarioNameField.clear();
				brailleCellsField.clear();
				answerButtonsField.clear();
				clearSection();
			}
		});

		warningCancel.setOnAction(e2 -> {
			warningWindow.close();
		});
		warningCancel.setOnKeyPressed(e3 -> {
			if (e3.getCode() == KeyCode.ENTER) {
				warningWindow.close();
			}

		});

		// File menu selection : save project
		saveProject.setOnAction(e -> {

			if (blockList.size() == 0) {
				noSectionsSavedWindow.show();
			} else {
				// send blocklist to printer == save txt file
				try {
					printer = new Printer(scenarioNameField.getText() + ".txt",
							Integer.parseInt(brailleCellsField.getText()),
							Integer.parseInt(answerButtonsField.getText()));
					printer.addBlockList(blockList);
					scenarioSavedWindow.show();
					printer.print();
					LOGR.info("Project Saved");
				} catch (IOException e3) {
					LOGR.warning("Failed or interrupted I/O operations");
				} catch (OddSpecialCharacterException e3) {
					LOGR.warning("Odd special character exception");
				} catch (InvalidBlockException e3) {
					LOGR.warning("Invalid input passed to Printer");
				} catch (InvalidCellException e4) {
					// TODO Auto-generated catch block
					LOGR.warning("Invalid input passed to Braille Interpreter");
				}
			}
		});

		// hot key save project
		saveProject.setAccelerator(
				new KeyCodeCombination(KeyCode.S, KeyCodeCombination.CONTROL_DOWN, KeyCodeCombination.ALT_DOWN));

		// File menu selection : load project

		loadProject.setOnAction(e -> {

		});

		// file menu selection : test project

		testProject.setOnAction(e -> {
			runTest(brailleCellsUsedWindow, playerSelectionWindow, visualButton, audioButton);
		});

		testProject.setAccelerator(
				new KeyCodeCombination(KeyCode.T, KeyCodeCombination.CONTROL_DOWN, KeyCodeCombination.ALT_DOWN));

		/*
		 * ------<<[section menu actions
		 * 
		 * 
		 */

		// --
		saveSection.setOnAction(e -> {
			saveSection(nameSectionField, answerButtonsUsedField, storyText, brailleText, answerText, correctText,
					incorrectText, comboBoxList, comboBox, brailleCellsField, answerButtonsField, notANumberWindow,
					brailleWindow, emptyNameWindow, buttonsUsedWindow, emptyStoryWindow, saveWindow);
			LOGR.info("Section saved");
		});

		saveSection.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));

		//
		clearSection.setOnAction(e -> {
			clearSectionWarning.show();
		});

		// hot key clear
		clearSection.setAccelerator(new KeyCodeCombination(KeyCode.C, KeyCodeCombination.CONTROL_DOWN));

		/*
		 * ------<<[go to menu
		 *
		 */

		goToSectionName.setOnAction(e -> {
			nameSectionField.requestFocus();
		});

		goToSectionName.setAccelerator(new KeyCodeCombination(KeyCode.DIGIT1, KeyCodeCombination.CONTROL_DOWN));

		// --
		goToAnswerButtonsUsed.setOnAction(e -> {
			answerButtonsUsedField.requestFocus();
		});

		goToAnswerButtonsUsed.setAccelerator(new KeyCodeCombination(KeyCode.DIGIT2, KeyCodeCombination.CONTROL_DOWN));

		// --
		goToStory.setOnAction(e -> {
			storyText.requestFocus();
		});
		goToStory.setAccelerator(new KeyCodeCombination(KeyCode.DIGIT3, KeyCodeCombination.CONTROL_DOWN));

		// --
		goToBraille.setOnAction(e -> {
			brailleText.requestFocus();
		});
		goToBraille.setAccelerator(new KeyCodeCombination(KeyCode.DIGIT4, KeyCodeCombination.CONTROL_DOWN));

		// --
		goToAnswer.setOnAction(e -> {
			answerText.requestFocus();
		});
		goToAnswer.setAccelerator(new KeyCodeCombination(KeyCode.DIGIT5, KeyCodeCombination.CONTROL_DOWN));

		// --
		goToCorrect.setOnAction(e -> {
			correctText.requestFocus();
		});

		goToCorrect.setAccelerator(new KeyCodeCombination(KeyCode.DIGIT6, KeyCodeCombination.CONTROL_DOWN));

		// --
		goToIncorrect.setOnAction(e -> {
			incorrectText.requestFocus();
		});
		goToIncorrect.setAccelerator(new KeyCodeCombination(KeyCode.DIGIT7, KeyCodeCombination.CONTROL_DOWN));

		/*
		 * ------<<[sound menu
		 * 
		 *
		 */

		addSound.setOnAction(e -> {
			soundWindow.show();
		});

		addSound.setAccelerator(
				new KeyCodeCombination(KeyCode.S, KeyCodeCombination.CONTROL_DOWN, KeyCodeCombination.SHIFT_DOWN));

		/*
		 * .test button action on starting window
		 * 
		 */

		// starting window -> choose audio or visual player
		testButton.setOnAction(e1 -> {
			runTest(primaryStage, playerSelectionWindow, visualButton, audioButton);
		});

		testButton.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				runTest(primaryStage, playerSelectionWindow, visualButton, audioButton);
			}
		});

		// Set true to help see how nodes are aligned
		layout.setGridLinesVisible(false);
	}

	// ------------------------<<<{methods}

	/**
	 * test scenario from opening window
	 * 
	 * 
	 */

	private void runTest(Stage primaryStage, Stage playerSelectionWindow, RadioButton visualButton,
			RadioButton audioButton) {

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
	}

	/**
	 * .Creating New Scenario
	 * 
	 * opening window Scenario name braille cells used answer buttons available
	 * 
	 */

	private void nameNewScenario(Stage scenarioCreator, Stage errorWindow, Stage brailleCellsUsedWindow,
			TextField scenarioNameField, TextField brailleCellsField, TextField answerButtonsField) {

		if (scenarioNameField.getText().isEmpty() || brailleCellsField.getText().isEmpty()
				|| answerButtonsField.getText().isEmpty() || !brailleCellsField.getText().matches("[0-9]+")
				|| !answerButtonsField.getText().matches("[0-9]+") || Integer.parseInt(brailleCellsField.getText()) == 0
				|| Integer.parseInt(answerButtonsField.getText()) == 0) {
			errorWindow.show();
		} else {
			try {
				scenarioCreator.show();
				brailleCellsUsedWindow.close();
				LOGR.info("New scenario created");
			} catch (NumberFormatException e3) {
				errorWindow.show();
				LOGR.warning("Invalid input for the number of braille cells and answer buttons available");
			}
		}
	}

	/**
	 * load fields from combo box
	 * 
	 * 
	 */

	private void fillFields(TextField nameSectionField, TextArea storyText, TextField brailleText, TextField answerText,
			TextArea correctText, TextArea incorrectText, TextField answerButtonsField) {
		blockList.get(blockList.indexOf((blockMap.get(nameSectionField.getText())))).story = storyText.getText();
		blockList.get(blockList.indexOf((blockMap.get(nameSectionField.getText())))).correctResponse = correctText
				.getText();
		blockList.get(blockList.indexOf((blockMap.get(nameSectionField.getText())))).wrongResponse = incorrectText
				.getText();
		blockList.get(blockList.indexOf((blockMap.get(nameSectionField.getText())))).cells = brailleText.getText();
		blockList.get(blockList.indexOf((blockMap.get(nameSectionField.getText())))).answer = Integer
				.parseInt(answerText.getText());
		blockList.get(blockList.indexOf((blockMap.get(nameSectionField.getText())))).buttonsUsed = Integer
				.parseInt(answerButtonsField.getText());
	}

	/**
	 * .clear section
	 * 
	 * 
	 */

	private void clearSection() {
		nameSectionField.clear();
		storyText.clear();
		correctText.clear();
		incorrectText.clear();
		brailleText.clear();
		answerText.clear();
		answerButtonsUsedField.clear();
		clearSectionWarning.close();
		nameSectionField.requestFocus();
	}

	/*
	 * .Save section
	 * 
	 * 
	 * 
	 */

	private void saveSection(TextField nameSectionField, TextField answerButtonsUsedField, TextArea storyText,
			TextField brailleText, TextField answerText, TextArea correctText, TextArea incorrectText,
			ObservableList<String> comboBoxList, ComboBox<String> comboBox, TextField brailleCellsField,
			TextField answerButtonsField, Stage notANumberWindow, Stage brailleWindow, Stage emptyNameWindow,
			Stage buttonsUsedWindow, Stage emptyStoryWindow, Stage saveWindow) {

		// Check that all required fields are filled out properly
		if (nameSectionField.getText().equals("")) {
			emptyNameWindow.show();
		} else if (brailleText.getText().length() == 0
				|| brailleText.getText().length() > Integer.parseInt(brailleCellsField.getText())
				|| !brailleText.getText().matches("[A-z]+")) {
			brailleWindow.show();
		} else if (answerText.getText().isEmpty() || answerButtonsUsedField.getText().isEmpty()
				|| !answerText.getText().matches("[0+9]+") || !(answerButtonsUsedField.getText().matches("[0+9]+"))
				|| Integer.parseInt(answerButtonsUsedField.getText()) > Integer
						.parseInt(answerButtonsField.getText())) {
			buttonsUsedWindow.show();
		} else if (Integer.parseInt(answerText.getText()) > Integer.parseInt(answerButtonsUsedField.getText())
				|| Integer.parseInt(answerText.getText()) > Integer.parseInt(answerButtonsField.getText())) {
			notANumberWindow.show();
		} else if (storyText.getText().length() < 14) {
			emptyStoryWindow.show();
		} else {

			String blockName = nameSectionField.getText();

			if (blockMap.containsKey(blockName)) {
				fillFields(nameSectionField, storyText, brailleText, answerText, correctText, incorrectText,
						answerButtonsField);
				saveWindow.show();
				LOGR.info("Section Saved");
			} else {
				// save text to block
				Block blockText;

				try {
					blockText = new Block(blockName, storyText.getText(), correctText.getText(),
							incorrectText.getText(), Integer.parseInt(answerText.getText()), brailleText.getText(),
							Integer.parseInt(answerButtonsUsedField.getText()));
					blockList.add(blockText);
					blockMap.put(blockName, blockText);

					// save name to comboBox
					comboBoxList.add(blockName);
					comboBox.setItems(comboBoxList);

				} catch (NumberFormatException e2) {
					LOGR.warning("Invalid input for the answer text field");
				} catch (InvalidBlockException e2) {
					LOGR.warning("Invalid input passed to Block");
				}
			}

		}

	}

}