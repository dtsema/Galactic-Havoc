package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class HealthBar extends GameObject {
	
	public static boolean dead = false;
	public static int HEALTHMAX = 100;
	public static int HEALTH = 100;
	public static int score = 0;
	public String scores;
	
	public HealthBar(int x, int y, ID id) {
		super(x, y, id);
	}

	public void tick(){
		if (HEALTH < 0){
			HEALTH = 0;
			dead = true;
		}
		if (HEALTH>Game.HP){
			HEALTH = Game.HP;
		}
	}

	public void render(Graphics g){
		g.setColor(Color.gray);
		g.fillRect(12, 12, HEALTHMAX*2, 30);
		g.setColor(Color.green);
		g.fillRect(12, 12, HEALTH*2, 30);
		scores = Integer.toString(score);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 50)); 
		g.drawString(scores, 12, 80);
	}
}
