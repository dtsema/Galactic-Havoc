package game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;

public class Player extends GameObject implements ImageObserver {
	Handler handler;
	Timers reload, orbReset;
	public static int reloadSpeed, colBoxXPos, colBoxYPos, colBoxX, colBoxY,
	astTouchedX, misTouchedX, orb = 0, damage = 40;
	int lazerXOffset;
	public static boolean basic = true, touchedByAsteroid, touchedByMissle, upgrade1 = false, upgrade2 = false, upgrade3 = false,
			upgrade4 = false, upgrade5 = false, upgrade6 = false, upgrade7 = false, on = false;
	int x;
	private Image img = null;
	
	
	public Player(int x, int y, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		this.x=x;
		reloadSpeed = 19;
		reload = new Timers(reloadSpeed);
		orbReset = new Timers(0);
		velocityX = 15;	
	}
	
	public void tick(){
		upgradeShipIfPurchased();
		collision();
		shootLazer();
		useEnergyOrb();
		movement(-1, KeyInput.leftKey);
		movement(1, KeyInput.rightKey);
	}
	
	private void upgradeShipIfPurchased(){
		if (basic){
			setCollisionBox(x, y, 145, 130, 185);
			img = Game.shipBasic;
		}
		if (upgrade1){
			img = Game.shipUpgrade1;	
		}
		if (upgrade2){
			img = Game.shipUpgrade2;
		}
		if (upgrade3){
			setCollisionBox(x+20, y, 140, 160, 170);
			img = Game.shipUpgrade3;
		}
		if (upgrade4){
			setCollisionBox(x+10, y+40, 160, 160, 170);
			y = 480;
			img = Game.shipUpgrade4;			
		}
		if (upgrade5){
			setCollisionBox(x, y, 200, 200, 160);
			y = 440;
			img = Game.shipUpgrade5;
		}
		if (upgrade6){
			setCollisionBox(x, y, 200, 200, 158);
			y = 440;
			img = Game.shipUpgrade6;
		}
		if (upgrade7){
			setCollisionBox(x+60, y+40, 200, 200, 100);
			y = 400;
			img = Game.shipUpgrade8;			
		}
	}
	
	private void collision(){
		for (int i = 0; i <handler.object.size(); i++){
			GameObject tempObject = handler.object.get(i);
			
			asteroidCollision(tempObject);
			missleCollision(tempObject);
			healthPackCollision(tempObject);
			}
		}	
	
	
	private void asteroidCollision(GameObject tempObject){
		if(tempObject.getId() == ID.Asteroid){
			
			if(getBounds().intersects(tempObject.getBounds())){
				astTouchedX=tempObject.getX();
				touchedByAsteroid= true;
				HealthBar.HEALTH-=2;
				handler.removeObject(tempObject);
			}			
		}
	}
	
	private void missleCollision(GameObject tempObject){
		if(tempObject.getId() == ID.Missle ){
			
			if(getBounds().intersects(tempObject.getBounds())){
				misTouchedX= tempObject.getX();
				touchedByMissle= true;
				handler.removeObject(tempObject);
				}			
		}
	}
	
	private void healthPackCollision(GameObject tempObject){
		if (tempObject.getId() == ID.HealthPack){
			if (getBounds().intersects(tempObject.getBounds())){
				if (HealthBar.HEALTHMAX/2+ HealthBar.HEALTH > HealthBar.HEALTHMAX) HealthBar.HEALTH = HealthBar.HEALTHMAX;
				
				else {
					HealthBar.HEALTH+= HealthBar.HEALTHMAX/2;
				}
				handler.removeObject(tempObject);
			}
		}
	}

	private void shootLazer(){
		if (KeyInput.upKey && !KeyInput.aKey) on = true;
		if (on && reload.cD()){
			on = false;
			reload.setCount(reloadSpeed);
			handler.addObject(new Lazer(x-lazerXOffset, y-150, ID.Laz, handler));
		}
		if (KeyInput.aKey && reload.cD() && !KeyInput.upKey){

			reload.setCount(reloadSpeed);
			handler.addObject(new Lazer(x-lazerXOffset, y-150, ID.Laz, handler));
		}
	}
	
	private void useEnergyOrb(){
		if (Wave.energyOrb && KeyInput.spaceBar){
			reloadSpeed = 0;
			orbReset.setCount(1000);
		}
		if(orbReset.cD() && reloadSpeed == 0){
			reloadSpeed = 19;
		}
	}
	
	private void movement(int a, boolean key){
		if (key == true){
			setBounds();
			setSpeed(a);
		}
	}	
	
	private void setBounds(){
		if (basic || upgrade1 || upgrade2)lockBounds(10, 1140, 10, 1140);
		else if (upgrade3) lockBounds(-10, 1120, -10, 1120);
		else if (upgrade4) lockBounds(-5, 1120, -5, 1120);
		else if (upgrade7) lockBounds(-70, 1050, -70, 1050);
		else if (upgrade6) lockBounds(-15, 1100, -15, 1100);
		else if (upgrade5) lockBounds(-15, 1110, -15, 1120);
	}
	
	private void lockBounds(int xLeft, int xRight, int xBoxLeft, int xBoxRight){
		if (x < xLeft){
			x = xLeft;
		}
		if (x > xRight){
			x = xRight;
		}
		if (colBoxXPos < xBoxLeft){
			colBoxXPos = xBoxLeft;
		}
		if (colBoxXPos > xBoxRight){
			colBoxXPos = xBoxRight;
		}
	}
	
	private void setSpeed(int a){
		if (!ShopButton.gem){
			x+=(a*velocityX);
			colBoxXPos+=(a*velocityX);
		}
		else if (ShopButton.gem){
			x+=(a*(velocityX+10));
			colBoxXPos+=(a*(velocityX+10));
		}
	}
	
	private void setCollisionBox(int x, int y, int sizeX, int sizeY, int lazerOffset){
		colBoxXPos =x;
		colBoxYPos = y;
		colBoxX=sizeX;
		colBoxY=sizeY;
		lazerXOffset=lazerOffset;
	}
	
	public void render(Graphics g){
		g.drawImage(img, x, y, this);	
	}
	
	public Rectangle getBounds(){
		return new Rectangle(colBoxXPos, colBoxYPos, colBoxX, colBoxY);
	}

	@Override
	public boolean imageUpdate(Image arg0, int arg1, int arg2, int arg3, int arg4, int arg5) {
		return false;
	}
}
