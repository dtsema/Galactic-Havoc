package game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;

public class Lazer extends GameObject implements ImageObserver {
	public static boolean hit = false;
	public int velocityY = 20, velocityX = 15;
	Handler handler;
	
	public Lazer(int x, int y, ID id, Handler handler){
		super(x, y, id);
		this.handler = handler;
	}
	
	public void tick(){
		shoot();
	}
	
	public void shoot(){
		if (y>-100){
			y-=velocityY;
			collision();
		}
		else {
			handler.removeObject(this);
		}
	}
	
	private void collision(){
		for (int i = 0; i <handler.object.size(); i++){
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ID.Asteroid || tempObject.getId() == ID.EnemyPurple|| tempObject.getId() == ID.EnemyBlue){
				if(getBounds().intersects(tempObject.getBounds())){
						hit = true;
						int a = tempObject.getHealth();
						tempObject.setHealth(a-=Player.damage);
						handler.removeObject(this);
				}
			}
		}
	}
	
	public void render(Graphics g){
		g.drawImage(Game.lazer1, x-28, y, this);
		g.drawImage(Game.lazer2, x+92, y, this);
	}
	
	public Rectangle getBounds(){
		return new Rectangle(x+195, y+85, 130, 50);
	}
	
	@Override
	public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
		// TODO Auto-generated method stub
		return false;
	}
}
