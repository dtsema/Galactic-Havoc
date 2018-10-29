package game;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ShopButton extends GameObject implements MouseListener {
	Handler handler;
	String buttonText, upgradeType;
	BufferedImage button, buttonPressed;
	int listenerPosX = 430, listenerPosY = 310, listenerBoxX = 417, listenerBoxY = 110, dmgPlus, hpPlus,
			yPosButton = 300, XposButton = 410, on=-1;
	Game game;
	private boolean pressed = false;
	public static boolean deleteOldShopButton = false, upgrade=false, gem;
	
	public ShopButton(String upgradeType, String buttonText, Handler handler, Game game,
			ID id, int y, int x, int dmgPlus, int hpPlus, int reloadUp){
		super(x,y,id);
		this.game = game;
		game.addMouseListener(this);
		Game.reloadUp= reloadUp;
		Game.dmgUp=dmgPlus;
		Game.hpUp=hpPlus;
		this.buttonText = buttonText;
		this.upgradeType=upgradeType;
		this.handler=handler;
		listenerPosX = 430;
		listenerPosY = 310;
		
		try{
		    button = ImageIO.read( new File("button\\defButton.png"));
		    buttonPressed = ImageIO.read( new File("button\\defButtonPressed.png"));
		}
		catch (IOException exc){   exc.printStackTrace();
		}
	}
	
	public void tick() {
		upgradeShip();
	}
	
	private void upgradeShip(){
		if (upgradeType == "shipUpgrade1" && pressed  && HealthBar.score>= 100){
			upgrade=true;
			if (deleteOldShopButton){
				Player.basic=false;
				Player.upgrade1= true;
				deleteOldShopButton = false;
				upgrade=false;
				
				handler.addObject(new ShopButton("shipUpgrade2", "+Health Cost: 300", handler, game, ID.ShopButton, 0, 0, 0, 20, 0));
				handler.removeObject(this);
			}
		}
			
		if (upgradeType == "shipUpgrade2" && pressed  && HealthBar.score>= 300){
			upgrade=true;
			if (deleteOldShopButton){
				Player.upgrade1=false;
				Player.upgrade2= true;
				
				deleteOldShopButton = false;
				upgrade=false;
				
					handler.addObject(new ShopButton("shipUpgrade3", "+Reload Cost: 700", handler, game, ID.ShopButton, 0, 0, 0, 0, 3));
				
				handler.removeObject(this);
			}
		}
			
		if (upgradeType == "shipUpgrade3" && pressed  && HealthBar.score>= 700){
			upgrade=true;
			if (deleteOldShopButton){
				Player.upgrade2=false;
				Player.upgrade3= true;
				deleteOldShopButton = false;
				upgrade=false;
				
				handler.addObject(new ShopButton("shipUpgrade4", "+Damage Cost: 1300", handler, game, ID.ShopButton, 0, 0, 10, 0, 0));
				handler.removeObject(this);
			}
		}
				
		if (upgradeType == "shipUpgrade4" && pressed  && HealthBar.score>= 1300){
			upgrade=true;
			if (deleteOldShopButton){
				Player.upgrade3=false;
				Player.upgrade4= true;
				deleteOldShopButton = false;
				upgrade=false;
		
				handler.addObject(new ShopButton("shipUpgrade5", "+Health Cost: 1800", handler, game, ID.ShopButton, 0, 0, 0, 30, 0));		
				handler.removeObject(this);
			}
		}
			
		if (upgradeType == "shipUpgrade5" && pressed  && HealthBar.score>= 1800){
			upgrade=true;
			if (deleteOldShopButton){
				Player.upgrade4=false;
				Player.upgrade5= true;
				deleteOldShopButton = false;
				upgrade=false;
				
				handler.addObject(new ShopButton("shipUpgrade6", "+Reload Cost: 2600", handler, game, ID.ShopButton, 0, 0, 0, 0, 10));
				handler.removeObject(this);
			}
		}
			
		if (upgradeType == "shipUpgrade6" && pressed  && HealthBar.score>= 2600){
			upgrade=true;
			if (deleteOldShopButton){
				Player.upgrade5=false;
				Player.upgrade6= true;
				deleteOldShopButton = false;
				upgrade=false;
				
				handler.addObject(new ShopButton("shipUpgrade7", "+HP +Damage Cost: 4000", handler, game, ID.ShopButton, 0, 0, 100, 10, 0));
				handler.removeObject(this);
			}
		}
		if (upgradeType == "shipUpgrade7" && pressed  && HealthBar.score>= 4000){
			upgrade=true;
			if (deleteOldShopButton){
				Player.upgrade6=false;
				Player.upgrade7= true;
				deleteOldShopButton = false;
				upgrade=false;
				
				handler.addObject(new ShopButton("shipUpgrade8", "+Speed Cost: 6000", handler, game, ID.ShopButton, 0, 0, 0, 0, 0));
				handler.removeObject(this);
			}
		}
		if (upgradeType == "shipUpgrade8" && pressed  && HealthBar.score>= 6000){
			upgrade=true;
			if (deleteOldShopButton){
				ShopButton.gem = true;
				deleteOldShopButton = false;
				upgrade=false;
				
				handler.removeObject(this);
			}
		}
	}
	
	public void render(Graphics g){
		drawButton(g);
		g.setFont(new Font ("Eras Demi ITC", Font.PLAIN, 20));
		g.drawString(buttonText, XposButton+50, yPosButton +50);
	}
	
	public Graphics drawButton(Graphics g){
			if (pressed == false){
			g.drawImage(button, XposButton, yPosButton, null);
		}
		if (pressed == true){
			g.drawImage(buttonPressed, XposButton, yPosButton, null);
		}
		return g;
	}
	
	public void mouseClicked(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
				
		if (mx >= listenerPosX && mx <= listenerPosX+listenerBoxX){
			if (my >= listenerPosY && my <= listenerPosY+listenerBoxY){
				on = on *-1;
				
				if (on==-1){
					pressed = false;
				}
				else if (on ==1){
					pressed = true;	
				}	
			}
		}
	}
	
	public boolean getPressed(){
		return this.pressed;
	}
	public void setPressed(boolean pressed){
		this.pressed=pressed;
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
