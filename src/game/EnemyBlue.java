package game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.util.concurrent.ThreadLocalRandom;

public class EnemyBlue extends GameObject implements ImageObserver{
	public int health = 800;
	Timers reload;
	Handler handler;
	int distanceA;
	public static boolean shot = false;
	
	public EnemyBlue(int x, int y, ID id, Handler handler){
		super(x, y, id);
		this.handler= handler;
		reload = new Timers(100);
		distanceA = ThreadLocalRandom.current().nextInt(-60, 100);
		velocityY = 2;
		velocityX = 1;
		
	}
	
	public void tick(){
		enterScreen();
		
		shootMissle();
		
		followPlayer();
	}
	
	private void shootMissle(){
		if (reload.cD()){
			reload.setCount(200);
			handler.addObject(new Missle(x +47,100, ID.Missle, handler, 20));
			shot = true;
		}
	}
	
	private void followPlayer(){
		if (x>=0 && x<=1200){
			x += ((Game.player.x + distanceA ) - x) * velocityX / (60);
		}
	}
	
	private void enterScreen(){
		if(y<50){
			y+=velocityY;
		}
	}
	
	public void render(Graphics g){
		g.drawImage(Game.enemyTwo, x, y, this);
	}
	
	public void setHealth(int health){
		this.health = health;
	}
	
	public int getHealth(){
		return this.health;
	}
	
	public Rectangle getBounds(){
		return new Rectangle(x+3, y, 93, 95);
	}
	
	@Override
	public boolean imageUpdate(Image arg0, int arg1, int arg2, int arg3, int arg4, int arg5) {
		return false;
	}
}
