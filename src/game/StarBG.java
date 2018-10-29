package game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class StarBG extends GameObject {
	
	double y;
	int i = new Random().nextInt(4);
	
	public StarBG(int x, int y, ID id) {
		super(x, y, id);
		this.y = y;
	}
	public void tick(){
		scrollDownScreen();
	}
	
	private void scrollDownScreen(){
		if(y<3500){
			if (i ==0){
			y += 0.5;
			}
			else if (i==1){
				y+=0.7;
			}
			else if (i == 2){
				y+=0.6;
			}
			else if (i == 3){
				y+=0.15;
			}
		}
		else{
			y =-100;
		}
	}
	
	public void render(Graphics g){
		g.setColor(Color.WHITE);
		g.drawImage(Game.starlist.get(i), x,(int) y, null);
	}
}