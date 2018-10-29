package game;

import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class runMusic { 

    private Position curPosition;

    private final int EXTERNAL_BUFFER_SIZE = 524288; // 128Kb 

    enum Position { 
        LEFT, RIGHT, NORMAL
    };

    public runMusic() { 
        curPosition = Position.NORMAL;
    } 

    public runMusic(Position p) { 
        curPosition = p;
    } 
    
    public void play() throws UnsupportedAudioFileException, IOException{
    	URL url = Game.class.getResource("/music/music.wav");
    	AudioInputStream ais = AudioSystem.getAudioInputStream(url);
    	
	    AudioFormat format = ais.getFormat();
	    SourceDataLine auline = null;
	    DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
	
	    try { 
	        auline = (SourceDataLine) AudioSystem.getLine(info);
	        auline.open(format);
	    } catch (LineUnavailableException e) { 
	        e.printStackTrace();
	        return;
	    } catch (Exception e) { 
	        e.printStackTrace();
	        return;
	    } 
	
	    if (auline.isControlSupported(FloatControl.Type.PAN)) { 
	        FloatControl pan = (FloatControl) auline
	                .getControl(FloatControl.Type.PAN);
	        if (curPosition == Position.RIGHT) 
	            pan.setValue(1.0f);
	        else if (curPosition == Position.LEFT) 
	            pan.setValue(-1.0f);
	    } 
	
	    auline.start();
	    int nBytesRead = 0;
	    byte[] abData = new byte[EXTERNAL_BUFFER_SIZE];
	
	    try { 
	        while (nBytesRead != -1) { 
	            nBytesRead = ais.read(abData, 0, abData.length);
	            if (nBytesRead >= 0) 
	                auline.write(abData, 0, nBytesRead);
	            
	        } 
	    } catch (IOException e) { 
	        e.printStackTrace();
	        return;
	    } finally { 
	    	Music.done=true;
	        auline.drain();
	        auline.close();
	    	} 
	
	    } 
}