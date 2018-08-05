package enamel;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

/**
 * This class is heavily based on the example code found here:
 * 
 * http://www.codejava.net/coding/capture-and-record-sound-into-wav-file-with-java-sound-api
 * 
 * @author Connor
 *
 */
public class SoundRecorder {
	
	private long recordTime;
	File file;
	AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;
	TargetDataLine line;
	
	public SoundRecorder(String fileName, long recordTime) {
		file = new File(fileName + ".wav");
		this.recordTime = recordTime;
	}
	
	public SoundRecorder(String fileName) {
		this(fileName, 60000);
	}
	
	public void start() {
		
		try {
			AudioFormat format = new AudioFormat(16000, 8, 2, true, true);
			DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
			
			if(!AudioSystem.isLineSupported(info)) {
				System.out.println("Line not supported");
				System.exit(0);
			}
			
			line = (TargetDataLine) AudioSystem.getLine(info);
			line.open(format);
			line.start();
			
			System.out.println("Start capturing");
			
			AudioInputStream ais = new AudioInputStream(line);
			
			System.out.println("Start Recording");
			
			AudioSystem.write(ais, fileType, file);
		}
		
		catch (LineUnavailableException ex) {
			ex.printStackTrace();
		}
		
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public void setRecordTime(long time) {
		recordTime = time;
	}
	
	public void finish() {
		line.stop();
		line.close();
		System.out.println("Finished");
	}
	
	public void record() {
		
		final SoundRecorder recorder = new SoundRecorder("test");
		
		Thread stopper = new Thread(new Runnable() {
			public void run() {
				try {
					Thread.sleep(recorder.recordTime);
				}
				catch (InterruptedException ex) {
					ex.printStackTrace();
				}
				recorder.finish();
			}
		});
		
		stopper.start();
		
		recorder.start();
	}

}
