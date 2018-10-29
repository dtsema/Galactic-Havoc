package game;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import game.Game.STATE;

public class MenuButton extends GameObject implements MouseListener {
	Handler handler;
	String buttonText;
	BufferedImage button;
	BufferedImage buttonPressed;
	int XposButton = 410;
	int yChoice;
	int yPosButton;
	int listenerPosX;
	int listenerPosY;
	int listenerBoxX = 417;
	int listenerBoxY = 110;
	Game game;
	int on = -1;
	
	private boolean pressed = false;
	public static boolean delete = false;
	
	int top = 180, mid = 300, bot = 420 + x;
	
	public MenuButton(String buttonText, int yChoice, Game game, ID id, int y, int x, Handler handler){
		super(x,y,id);
		this.game=game;
		game.addMouseListener(this);
		pressed = false;
		this.buttonText = buttonText;
		this.handler=handler;
		int[] Ypos = {top, mid, bot};
		this.yChoice=yChoice;
		yPosButton = Ypos[yChoice];
		listenerPosX = XposButton + 20;
		listenerPosY = yPosButton + 10;

	}
	
	public void tick() {
		if (pressed){	
			if (buttonText == "     Start"){
				Game.State= STATE.GAME;
			}
			if (buttonText == "Back"){
				Game.State = STATE.MENU;
			}
			if (buttonText == "  Hiscores"){
				Game.State = STATE.SCORES;
			}
			if (buttonText == "Exit"){
				System.exit(1);
			}
			if (Game.State==STATE.GAMEOVER){
				if (buttonText == "Restart"){
					handler.removeGameObjects();
					resetShop();
					resetHealth();
					resetPlayerPosition();
					resetUpgrades();
					resetWave();
					resetAnimations();
					
					Game.State=STATE.GAME;
				}
			}
		
		}
	}
	
	private void resetShop(){
		Game.shopHandler.resetShop();
		Game.shopHandler.addObject(new ShopButton("shipUpgrade1","Ultra Cannon (+Damage) Cost: 100", Game.shopHandler, game, ID.ShopButton, 0, 0, 20, 0, 0));
		
	}
	
	private void resetHealth(){
		HealthBar.score=0;
		HealthBar.HEALTH=100;
		HealthBar.HEALTHMAX=100;
		HealthBar.dead=false;
	}
	
	private void resetPlayerPosition(){
		Game.player.setX(500);
		Game.player.setY(500);
	}
	
	private void resetUpgrades(){
		ShopButton.gem=false;
		Player.basic=true;
		Player.upgrade1 = false;
		Player.upgrade2 = false;
		Player.upgrade3 = false;
		Player.upgrade4 = false;
		Player.upgrade5 = false;
		Player.upgrade6 = false;
		Player.upgrade7 = false;
		Player.reloadSpeed=19;
		Player.damage=40;
		Player.orb=0;
		Wave.energyOrb=false;
	}
	
	private void resetWave(){
		Game.waveNumber=1;
		handler.addObject(new Wave(0, 0, ID.Wave, handler));
	}
	
	private void resetAnimations(){
		handler.addObject(new Animation(Game.player.getX(), 400, ID.Animation));
	}
	
	public Graphics drawButton(Graphics g){
			if (pressed == false){
			g.drawImage(Game.button, XposButton, yPosButton, null);
		}
		if (pressed == true){
			g.drawImage(Game.buttonPressed, XposButton, yPosButton, null);
			
		}
		return g;
	}
	
	public void render(Graphics g){
		drawButton(g);
		g.setFont(new Font ("Eras Demi ITC", Font.PLAIN, 70));
		g.drawString(buttonText, XposButton+50, yPosButton +90);
	}
	
	public void mousePressed(MouseEvent e) {
		selectOrUnselect(e);
	}
	
	private void selectOrUnselect(MouseEvent e){
		int mx = e.getX();
		int my = e.getY();
		
		if (mx >= listenerPosX && mx <= listenerPosX+listenerBoxX){
			if (my >= listenerPosY && my <= listenerPosY+listenerBoxY){
				on = -1;
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
	
	public void mouseReleased(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		if (mx >= listenerPosX && mx <= listenerPosX+listenerBoxX){
			if (my >= listenerPosY && my <= listenerPosY+listenerBoxY){
				Press(false);
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
	public void mouseClicked(MouseEvent e) {
	}
	
	@Override
	public void mouseEntered(MouseEvent arg0) {
	}
	
	@Override
	public void mouseExited(MouseEvent arg0) {
	}
	
	public void Press(boolean pressed){
		this.pressed=pressed;
	}
}
