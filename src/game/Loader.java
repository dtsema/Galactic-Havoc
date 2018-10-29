package game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class Loader extends GameObject {
	private BufferedImage gear[];
	private int frameGear = 0;
	Timers load;
	
	public Loader(int x, int y, ID id) {
		super(x, y, id);
		load = new Timers(600);
		gear = new BufferedImage[6];
		
		for (int i = 0; i < gear.length; i++){
			try {
				gear[i] = ImageIO.read( new File("gear\\"+i+".png"));
			
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
		
	public void render(Graphics g){
		drawGear(g,520, 200);
	}
	public void drawGear(Graphics g, int x, int y){
		if (frameGear>=6)frameGear=0;
		g.drawImage(gear[frameGear], x, y, null);
		if(frameGear<gear.length &&load.cD()){
			frameGear++;	
			load.setCount(20);
		}
	}	
}