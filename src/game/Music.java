package game;

public class Music extends Thread { 
	runMusic run;
  public static boolean done = false;

  public void run() {
	  run = new runMusic();
	  run.play();
   }
}