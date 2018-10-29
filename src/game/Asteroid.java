package game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.Random;

public class Asteroid extends GameObject implements ImageObserver{
	private int health;
	private int i;
	private Random r;
	private ArrayList<Image> imgs;
	
	public Asteroid(int x, int y, ID id, ArrayList<Image> imgs){
		super(x, y, id);
		this.imgs = imgs;
		randomizeAsteroidImg();
		velocityY = 2;
		health = 100;
	}
	
	
	private void randomizeAsteroidImg(){
		r = new Random();
		i = r.nextInt(4);
	}
	
	public void tick(){
		x+=velocityX;
		y+=velocityY;
	}
	
	public void render(Graphics g){
		g.drawImage(imgs.get(i), x, y-650, this);
	}
	
	public Rectangle getBounds(){
		return new Rectangle(x, y-650, 88, 85);
	}
	public int getHealth(){
		return this.health;
	}
	public void setHealth(int health){
		this.health = health;
	}
	
	@Override
	public boolean imageUpdate(Image arg0, int arg1, int arg2, int arg3, int arg4, int arg5) {
		return false;
	}


}
