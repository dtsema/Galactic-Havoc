package game;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class PurchaseButton extends GameObject implements MouseListener {
	String buttonText;
	Timers timer;
	public static boolean pressed = false;
	
	public PurchaseButton(String buttonText, Game game, ID id, int y, int x){
		super(x,y,id);
		game.addMouseListener(this);
		this.buttonText = buttonText;
		timer = new Timers(0);
	}
	
	public void render(Graphics g){
		drawButton(g);
		g.setFont(new Font ("Eras Demi ITC", Font.PLAIN, 20));
		g.drawString(buttonText, 610, 575);
		upgradeStats();
	}

	private Graphics drawButton(Graphics g){
		if (pressed == false){
			g.drawImage(Game.buttonBuy, 560 ,550 , null);
		}
		if (pressed == true){
			if (timer.cD())pressed = false;
			g.drawImage(Game.buttonBuyPressed, 560, 550, null);
		}
		return g;
	}
	
	private void upgradeStats(){
	
		if (pressed && ShopButton.upgrade){
			Player.damage += Game.dmgUp;
			
			if (Game.HP < HealthBar.HEALTHMAX){
				HealthBar.HEALTHMAX += Game.hpUp;
				Game.HP = HealthBar.HEALTHMAX;
			}
			else if (Game.HP >= HealthBar.HEALTHMAX) {
				HealthBar.HEALTHMAX += Game.hpUp;
				Game.HP = HealthBar.HEALTHMAX;
				HealthBar.HEALTH = Game.HP;
			}
			
			Player.reloadSpeed -= Game.reloadUp;
			Game.reloadUp=0;
			ShopButton.deleteOldShopButton=true;
			pressed=false;
		}
	}

	public void mouseClicked(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		
		if (mx >= 565 && mx <= 692){
			if (my >= 553 && my <= 586){
					timer.setCount(50);	
					pressed = true;
			}
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e) {	
	}
	
	@Override
	public void mouseEntered(MouseEvent arg0) {
	}
	
	@Override
	public void mouseExited(MouseEvent arg0) {
	}
	
	@Override
	public void mouseReleased(MouseEvent arg0) {		
	}
}
