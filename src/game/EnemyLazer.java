package game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;

public class EnemyLazer extends GameObject implements ImageObserver {
	public static boolean hit = false;
	Handler handler;
	
	public EnemyLazer(int x, int y, ID id, Handler handler){
		super(x, y, id);
		this.handler = handler;
		velocityY = 10;
	}
	
	public void tick(){
		shoot();
	}
	
	public void render(Graphics g){
		g.drawImage(Game.enemyLazer, x, y, this);
	}
	
	public void shoot(){
		collision();
		if(y<600){
			y=velocityY+y;
		}
		else{
			velocityY = 0;
			handler.removeObject(this);
		}
	}
	
	private void collision(){
		for (int i = 0; i <handler.object.size(); i++){
			GameObject tempObject = handler.object.get(i);
			if(tempObject.getId() == ID.Player){
				if(getBounds().intersects(tempObject.getBounds())){
					HealthBar.HEALTH-=10;
					handler.removeObject(this);
				}
			}
		}
	}
	
	public Rectangle getBounds(){
		return new Rectangle(x+223, y+360, 13, 135);
	}
	
	@Override
	public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
		return false;
	
	}
}
