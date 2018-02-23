package enamel;
//coping code from V-player -micah

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class AudioPlayer extends Player {
	// voice and voiceManager from ScenarioParser used for audio speaking - micah 
	private Voice voice;
	private VoiceManager vm;
	// ArrayList of strings used to hold the state of the buttons, 
	//if they are skip buttons, repeat buttons or inactive. - micah
	private List<String> buttonState = new ArrayList<String>();
	
	
	private JFrame frame;
	private GridLayout cellGrid = new GridLayout(4, 2);
	LinkedList<JPanel> panelList = new LinkedList<JPanel>();
	LinkedList<JButton> buttonList = new LinkedList<JButton>();
	JPanel southPanel = new JPanel();
	JPanel centerPanel = new JPanel();
	JRadioButton[] pins = new JRadioButton[8];
	int[] pinIndex = {0, 2, 4, 1, 3, 5, 6, 7};
	private boolean displayed = false;
	//private String state = "";

	
	public AudioPlayer(int brailleCellNumber, int buttonNumber)
	{
		super(brailleCellNumber, buttonNumber);
		
	// initializing voice - micah
		 vm = VoiceManager.getInstance();
	        voice = vm.getVoice ("kevin16");
	        voice.allocate();
	        
		SwingUtilities.invokeLater(new Runnable() {
			//@Override
			public void run() {
				frame = new JFrame();
				frame.setTitle("Simulator");
				frame.setBounds(100, 100, 627, 459);
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				frame.getContentPane().setLayout(new BorderLayout());

				for (int i = 0; i < brailleCellNumber; i++) {

					JPanel panel = new JPanel(cellGrid);
					for (int j = 0; j < 8; j++) {
						JRadioButton radioButton = new JRadioButton();
						radioButton.setEnabled(false);
						radioButton.setSize(25, 25);
						radioButton.getAccessibleContext().setAccessibleName("Cell " + (j + 1));

						pins[j] = radioButton;

						panel.add(radioButton);
						panel.repaint();
					}
					
					panel.setVisible(true);

					panelList.add(panel);
					panel.setSize(50, 50);
					panel.setBorder(BorderFactory.createLineBorder(Color.black));
					centerPanel.add(panel);

					if (i == (brailleCellNumber - 1))
						frame.getContentPane().add(centerPanel, BorderLayout.CENTER);
				}

				for (int i = 0; i < buttonNumber; i++) {
					JButton button = new JButton("" + (i + 1));

					buttonList.add(button);
					southPanel.add(button);
					buttonState.add("inactive"); // Initalizing the state of every button. - micah
				}

				frame.getContentPane().add(southPanel, BorderLayout.SOUTH);
				
				// deleted conner's test button - micah
				
				frame.repaint();
				frame.setVisible(true);
			}
		});
		
	}
	// copied from V-player -micah
	public void setButton(int buttonNumber)
	{
		
		if (!displayed) {
			if (buttonNumber > 0)
				this.buttonNumber = buttonNumber;
			else
				throw new IllegalArgumentException("Non-positive integer entered.");
		}
		
	}
	
	
	// copied from V-player -micah
	public void setCell(int cellNumber)
	{
		if (!displayed) {
			if (cellNumber > 0)
				this.brailleCellNumber = cellNumber;
			else
				throw new IllegalArgumentException("Non-positive integer entered.");
		}
		
	}
	
	//copied from V-player
	public JButton getButton(int index) {
		if (index >= this.buttonNumber || index < 0) {
			throw new IllegalArgumentException("Invalid button index.");
		}
		return this.buttonList.get(index);
	}

	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		// refresh is always called when the pins change, so audio description 
		//needs to be given of the state of the pins. - micah
		// I decided to remove audio update and make it it's own method. - micah
		
		//speak("There are " + Integer.toString(brailleList.size()) + 
		//			" braille cells on this device.");
		
		for (BrailleCell s : brailleList) {
			for (int i = 0; i < s.getNumberOfPins(); i++) {
				pins[pinIndex[i]].setSelected(s.getPinState(i));
			
			}
		}
	}

	@Override
	public void addSkipButtonListener(int index, String param, ScenarioParser sp) {
		// TODO Auto-generated method stub
		buttonState.set(index, "a Skip Button");
		
		// if the next line is not /~skip-button: then the currnet line is the 
		// last call to addskipbuttonlistener, therefore give audio update on what 
		// changed - micah
		// also, /~skip-button: should not be the last line of the file. - micah
		if(sp.nextLineChecker.length() > 14) {
			if( (sp.nextLineChecker.substring(0, 14).equals("/~skip-button:")) == false) {
				//speak("         ");
				update();// this might casue problems, but i don't think it will - micah
			}
		}
		else {
			update();
		}
		
		
		buttonList.get(index).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (sp.userInput) {
					sp.skip(param);
					//logger.log(Level.INFO, "Button {0} was pressed", index+1);
					sp.userInput = false;
				}
			}
		});
		
	}

	@Override
	public void removeButtonListener(int index) {
		// TODO Auto-generated method stub
		buttonState.set(index, "inactive");
		
		if (index >= this.buttonNumber || index < 0) {
            throw new IllegalArgumentException("Invalid index.");
        }
		ActionListener[] aList = getButton(index).getActionListeners();
		if (aList.length > 0) {
			for (int x = 0; x < aList.length; x++) {
				getButton(index).removeActionListener(getButton(index).getActionListeners()[x]);
			}
		}
	
	}

	@Override
	public void addRepeatButtonListener(int index, ScenarioParser sp) {
		// TODO Auto-generated method stub
		buttonState.set(index, "a Repeat Button");
		
		getButton(index).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// This if statement is used to allow the user to press the
				// button
				// after all the text has been read and that the program is
				// expecting
				// a user input.
				if (sp.userInput) {
					repeat++;
					logger.log(Level.INFO, "Repeat Button was pressed.");
					logger.log(Level.INFO, "Repeat Button was pressed {0} times", repeat);
					sp.repeatText();
				}
			}
		});
	}
	
	/*
	 * This method speaks out loud using the TTS methods created by FreeTTS.
	 * copied from ScenarioParser - micah
	 */
	private void speak(String text) {
		
			voice.speak(text);
		
	}
	
	// to be called for an audio update of the gui
	private void update() {
		for (BrailleCell s : brailleList) {
			
			speak("For the braille cell in the " + 
					Integer.toString(brailleList.indexOf(s) + 1) + 
						" position, it's pins are in this state.");
			
			for (int i = 0; i < s.getNumberOfPins(); i++) {
				if(s.getPinState(i)) {
					speak("The pin in the " + Integer.toString(i + 1) + " position is up.");
				}
				else {
					speak("The pin in the " + Integer.toString(i + 1) + " position is down.");
				}
			}
		}
		int counter = 1;
		for (String s: buttonState) {
			
			speak("Button " + Integer.toString(counter) +" is a " + s);
			counter++;
		}
		
	}
	

}
