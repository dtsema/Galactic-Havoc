package game;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {
	static Boolean leftKey = false, rightKey = false, upKey = false, aKey = false, sKey = false, spaceBar = false;
	static int up = 0;
	private Handler handler;
	
	public KeyInput(Handler handler){
		this.handler = handler;
	}
	
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		
		movePlayerLR(key);
		
		aAction(key);
			
		if (key == KeyEvent.VK_UP){
			upKey = true;
		}
		if (key == KeyEvent.VK_SPACE){
			spaceBar = true;
		}
		if (key == KeyEvent.VK_S){
			if (sKey==false)sKey = true;
			else sKey = false;
		}
		if(key == KeyEvent.VK_ESCAPE) System.exit(1);
	}
	
	private void movePlayerLR(int key){
		for(int i = 0; i < handler.object.size(); i++){
			GameObject tempObject = handler.object.get(i);
				if(tempObject.getId() == ID.Player){
					if (key == KeyEvent.VK_LEFT){
						leftKey = true;
					}
					if (key ==KeyEvent.VK_RIGHT){
						rightKey = true;
					}
				}
			}

	}
	
	private void aAction(int key){
		if (key == KeyEvent.VK_A){	
			if (aKey==false){
				aKey = true;
			}
			else {
				aKey = false;
			}
		}
	}
			
		
	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		
		stopPlayerMovement(key);
		
		if (key == KeyEvent.VK_SPACE){
			spaceBar = false;
		}
		if (key == KeyEvent.VK_UP){
			upKey = false;
		}		
	}
	
	private void stopPlayerMovement(int key){
		for(int i = 0; i < handler.object.size(); i++){
			GameObject tempObject = handler.object.get(i);
			if(tempObject.getId() == ID.Player){
				if (key == KeyEvent.VK_LEFT){
					leftKey = false;
				}
				if (key ==KeyEvent.VK_RIGHT){
					rightKey = false;
				}	
			}
		}
	}
}
