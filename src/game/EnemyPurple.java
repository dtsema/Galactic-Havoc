package game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import javax.imageio.ImageIO;

public class EnemyPurple extends GameObject implements ImageObserver{
	private Image img;
	Handler handler;
	int random = new Random().nextInt(2);
	Timers lazerReloadTimer;

	public EnemyPurple(int x, int y, ID id, Handler handler, int health){
		super(x, y, id);
		this.handler=handler;
		this.health=health;
		loadImage();
		velocityY = 2;
		velocityX = 4;
		lazerReloadTimer = new Timers(50);
	}
	
	private void loadImage(){
		try{
		img = ImageIO.read(new File("enemyShip\\ship.png"));
		}catch (IOException exc){
			exc.printStackTrace();
		}
	}
	
	public void tick(){
		shootLazer(120, 150);
		
		moveLeftOrRight();
		
		changeDirectionsOnEdge();
	}
	
	public void render(Graphics g){
		g.drawImage(img, x, y, this);
	}

	
	private void shootLazer(int randomMin, int randomMax){
		if (lazerReloadTimer.cD()){
			lazerReloadTimer.setCount(ThreadLocalRandom.current().nextInt(randomMin,randomMax));
			handler.addObject(new EnemyLazer(x-180,-250, ID.EnemyLazer, handler));
		}
	}
	
	private void moveLeftOrRight(){
		if(y == 50 && random == 0){
			x += velocityX;
		}
		else if (y == 50 && random == 1){
			x -= velocityX;
		}
	}
	
	private void changeDirectionsOnEdge(){
		if(x <= 0 || x >= 1200){
			velocityX *=-1;
		}
		if(y < 50){
			y+=velocityY;
		}
	}
	
	public Rectangle getBounds(){
		return new Rectangle(x+3, y, 93, 95);
	}
	
	public void setHealth(int health){
		this.health = health;
	}
	public int getHealth(){
		return this.health;
	}
	public int getX(){
		return this.x;
	}
	public int getY(){
		return this.y;
	}
	@Override
	public boolean imageUpdate(Image arg0, int arg1, int arg2, int arg3, int arg4, int arg5) {
		return false;
	}
}
