package game;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Random;

public class planetBG extends GameObject {
	Handler handler;
	private int i  = new Random().nextInt(4);
	double y;
	ArrayList<Image> imgs;
	
	public planetBG(int x, int y, ID id, Handler handler, ArrayList<Image> imgs) {
		super(x, y, id);
		this.handler = handler;
		this.imgs = imgs;
		this.y = y;
	}
	public void tick(){
		scrollPlanetDown();
	}
	
	private void scrollPlanetDown(){
		if(y<3500){
			y += 0.5;
		}
		else{
			handler.removeObject(this);	
		}
	}
	
	public void render(Graphics g){
		g.drawImage(imgs.get(i), x, (int) y, null);
	}
}