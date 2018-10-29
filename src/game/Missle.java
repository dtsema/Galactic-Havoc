package game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;

public class Missle extends GameObject implements ImageObserver {
	public static boolean hit = false;
	Handler handler;
	missleExplosion explosion;
	private int damage;
	Animation missle;
	int velocityY;
	
	public Missle(int x, int y, ID id, Handler handler, Integer damage){
		super(x, y, id);
		this.handler = handler;
		velocityY = 4;
		this.damage=damage;
		explosion = new missleExplosion(Animation.missleAnimation);
	}
	
	public void tick(){
		collision();
		shoot();
	}
	
	private void collision(){
		for (int i = 0; i <handler.object.size(); i++){
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ID.Player){
				if(getBounds().intersects(tempObject.getBounds())){
						HealthBar.HEALTH-=damage;
						EnemyBlue.shot = false;
				}
			}
		}
	}
	
	public void shoot(){
		if(y<900){
			y=velocityY+y;
			collision();
		}
		else{
			hit=true;
			velocityY = 0;
			handler.removeObject(this);
		}
	}

	public void render(Graphics g){
		explosion.drawMissleAnimation(g, 15, x, y);
	}
	
	public Rectangle getBounds(){
		return new Rectangle(x, y, 20, 100);
	}
	
	@Override
	public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void setHealth(int health) {
		// TODO Auto-generated method stub
		
	}
	public int getX(){
		return this.x;
	}
	@Override
	public int getHealth() {
		// TODO Auto-generated method stub
		return 0;
	}
	
}