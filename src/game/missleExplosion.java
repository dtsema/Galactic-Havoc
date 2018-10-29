package game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class missleExplosion {
	Timers interval;
	int frameMissle = 0;
	BufferedImage[] missleAnimation;
	public missleExplosion(BufferedImage[] missleAnimation){
		this.missleAnimation= missleAnimation;
		interval = new Timers(15);
	}
	
	public void drawMissleAnimation(Graphics g, int animSpeed, int x, int y){
		if(frameMissle>=missleAnimation.length){
			frameMissle = 4;
		}
			if(frameMissle>-1){	
				g.drawImage(missleAnimation[frameMissle], x, y, null);
			}
		if (interval.cD()){
			frameMissle++;
			interval.setCount(animSpeed);
		}
	}
}
