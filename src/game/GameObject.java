
package game;

import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class GameObject {
	protected int velocityX, velocityY;
	protected int x, y;
	protected ID id;
	public int health;
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public ID getId() {
		return id;
	}

	public void setId(ID id) {
		this.id = id;
	}

	public int getVelX() {
		return velocityX;
	}

	public void setVelX(int velX) {
		this.velocityX = velX;
	}

	public int getVelY() {
		return velocityY;
	}

	public void setVelY(int velY) {
		this.velocityY = velY;
	}
	

	public void setHealth(int health){
		
	}
	public int getHealth(){
		return 0;
	}
	
	
	public GameObject(int x, int y, ID id){
		this.x=x;
		this.y=y;
		this.id=id;
	}
	
	public void tick(){
	}
	
	public void render(Graphics g){
	}

	public Rectangle getBounds(){
		return new Rectangle();
	}
	
}
