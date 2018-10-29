package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Animation extends GameObject {
	public static boolean showEnergyOrbText;
	public static BufferedImage missleAnimation[];
	private BufferedImage energyOrbAnimation[], titleAnimation[],
	gemAnimation[], asteroidExplosionAnimation[], missleExplosionAnimation[];
	private double frameExplosion, frameMissleExplosion, 
	playerY, playerY1, alpha = 1;
	private int frameEnergyOrb, frameGem, frameTitle, missleX, asteroidX;
	private boolean touchedByAsteroid, touchedByMissle, createFadeOn;
	public boolean explosionTime = false;
	Timers energyOrbSpeed, gemSpeed, time4;
	
	
	public Animation(int x, int y, ID id) {
		super(x, y, id);
		
		titleAnimation = loadImages(70, "titleAnimation", "title");
		
		asteroidExplosionAnimation = loadImages(48, "explode", "explosion");
		
		missleAnimation = loadImages(8, "missle", "missle");
		
		energyOrbAnimation = loadImages(25, "energyOrb", "orb");
		
		gemAnimation = loadImages(6, "gem", "gem");
		
		missleExplosionAnimation = asteroidExplosionAnimation.clone();
		
		energyOrbSpeed = new Timers(20);
		gemSpeed = new Timers(20);
		time4 = new Timers(20);
		
	}
	
	private BufferedImage[] loadImages(int imageAmount, String folderName, String fileName){
		BufferedImage[] images = new BufferedImage[imageAmount];
		for (int i = 0; i < images.length; i++){
			
			try {
				images[i] = ImageIO.read( ResourceLoader.load(folderName + "/"+ fileName + i +".png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return images;
	}
	
	public void render(Graphics g){
		
		if (Game.State == Game.STATE.MENU){
				drawTitleAnimation(g, 100);
		}
		if (Game.State==Game.STATE.GAME){
			if (ShopButton.gem)drawGemAnimation(g, Game.player.x);
			if (Player.touchedByAsteroid){
				touchedByAsteroid = true;
			}
			if (touchedByAsteroid){
				drawAsteroidExplosionAnimation(g);
				Player.touchedByAsteroid=false;
			}
			if (Player.touchedByMissle){
				touchedByMissle = true;
			}
			if (touchedByMissle){
				drawMissleExplosionAnimation(g);
				Player.touchedByMissle=false;
			}
			if (showEnergyOrbText)createTextFade(g);
			if (Wave.energyOrb)	{
				drawEnergyOrbAnimation(g, 20);
			}
		}
	}

	private void drawGemAnimation(Graphics g, int x){
		g.drawImage(gemAnimation[frameGem], x+149, 540, null);
		if(frameGem<gemAnimation.length && gemSpeed.cD()){
			frameGem++;	
			gemSpeed.setCount(70);
		}
		else if (frameGem == 5) frameGem = 0;
	}

	public void drawAsteroidExplosionAnimation(Graphics g){
		if (frameExplosion >= 1 && frameExplosion<= 1.5){
			asteroidX = Player.astTouchedX ;
			playerY1 = Game.player.getY();
		}
		if (frameExplosion>-1){
			playerY1= playerY1+0.8;
			frameExplosion= frameExplosion + 0.25;	
		}
		if(frameExplosion>=asteroidExplosionAnimation.length){
			frameExplosion=0;
			touchedByAsteroid = false;
		}
		if(frameExplosion>-1){
			g.drawImage(asteroidExplosionAnimation[(int) frameExplosion],asteroidX, (int) playerY1, null);
		}
	}
	
	public void drawMissleExplosionAnimation(Graphics g){
		if (frameMissleExplosion >= 1 && frameMissleExplosion<= 1.5){
			missleX = Player.misTouchedX ;
			playerY = Game.player.getY();
		}
		if (frameMissleExplosion>-1){
			playerY= playerY+0.8;
			frameMissleExplosion= frameMissleExplosion + 0.25;	
		}
		if(frameMissleExplosion>=missleExplosionAnimation.length){
			frameMissleExplosion=0;
			touchedByMissle = false;
		}
		if(frameMissleExplosion>-1){
			g.drawImage(missleExplosionAnimation[(int) frameMissleExplosion], missleX, (int) playerY, null);
		}	
	}
		
	private Graphics createTextFade(Graphics g){
		if(alpha<=200 && alpha>=-1 && createFadeOn == false){
			alpha = alpha +0.3;
			if (alpha>=200){
				createFadeOn = true;	
			}
		}
		else if (alpha>2){
			alpha= alpha-0.3;
		}	
		if (alpha>=0){ 
			Color myColour = new Color(255, 255, 255, (int) alpha);
			g.setFont(new Font("Eras Demi ITC", Font.PLAIN, 20)); 
			g.setColor(myColour);
		}
		g.drawString("You found an energy orb, press space to use it! ", 40, 590);
		
		return g;
	}
	
	private void drawEnergyOrbAnimation(Graphics g, int animSpeed){
		g.drawImage(energyOrbAnimation[frameEnergyOrb], 50, 600, null);
		if(frameEnergyOrb<energyOrbAnimation.length && energyOrbSpeed.cD()){
			frameEnergyOrb++;	
			energyOrbSpeed.setCount(20);
		}
		else if (frameEnergyOrb == 24) frameEnergyOrb = 0;	
	}
	private void drawTitleAnimation(Graphics g, int x){
		
		if (frameTitle>=70)frameTitle=0;
		g.drawImage(titleAnimation[frameTitle], x+150, -50, null);
		if(frameTitle<titleAnimation.length &&time4.cD()){
		
			frameTitle++;	
			time4.setCount(15);
		}
	}
}
