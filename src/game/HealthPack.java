package game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;

public class HealthPack extends GameObject implements ImageObserver{

	public HealthPack(int x, int y, ID id){
		super(x, y, id);
		velocityY = 2;
	}

	public void tick(){
		x+=velocityX;
		y+=velocityY;
	}
	
	public void render(Graphics g){
		g.drawImage(Game.wrench, x, y-650, this);
	}
	
	public Rectangle getBounds(){
		return new Rectangle(x, y-650, 50, 50);
	}
	
	@Override
	public boolean imageUpdate(Image arg0, int arg1, int arg2, int arg3, int arg4, int arg5) {
		// TODO Auto-generated method stub
		return false;
	}

}
