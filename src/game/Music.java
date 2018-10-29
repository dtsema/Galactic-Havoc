package game;

import java.io.IOException;

import javax.sound.sampled.UnsupportedAudioFileException;

public class Music extends Thread { 
	runMusic run;
  public static boolean done = false;

  public void run() {
	  run = new runMusic();
	  try {
		run.play();
	} catch (UnsupportedAudioFileException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
   }
}