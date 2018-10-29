package game;

import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import game.Game.STATE;

public class Handler {
	LinkedList<GameObject> object = new LinkedList<GameObject>();
	Random r = new Random();
	private static int planetSpawnCooldown = 100;
	
	public void removeGameObjects(){
		ID[] ids = {ID.EnemyPurple, ID.EnemyBlue, ID.Asteroid, ID.Missle, 
				ID.EnemyLazer, ID.Wave,ID.HealthPack, ID.Animation};
		
		for (int i = 0; i<object.size(); i++){
			GameObject temp = object.get(i);
			
			for (ID id : ids){
				if (temp.id == id){
					removeObject(temp);
				}
			}
		}
	}
	
	public void removeObject(GameObject object){
		this.object.remove(object);
	}
	
	public void resetShop(){
		for (int i = 0; i<object.size(); i++){
			GameObject temp = object.get(i);
			if (temp.id == ID.ShopButton){
				removeObject(temp);
			}
		}
	}
	
	public void tick(){
		tickGameObjects();
	
		createWave();
		
		spawnPlanet();
	}
	
	private void tickGameObjects(){
		for(int i = 0; i<object.size(); i++){
			GameObject tempObject = object.get(i);
			tempObject.tick();
		}
	}

	private void createWave(){
		if (Game.State == STATE.GAME){
			if (Game.waveNumber!=1 && Game.createNewWave && Game.timeTilWave.cD()){
				this.addObject(new Wave(0, 0, ID.Wave, this));
				Game.createNewWave= false;
			}
		}
	}
	
	private void spawnPlanet(){
		if (Game.State == STATE.GAME){
			if (planetSpawnCooldown == 0){
				this.addObject(new planetBG(r.nextInt(Game.WIDTH), -400, ID.planetBG, this, Game.planetlist));
				planetSpawnCooldown = ThreadLocalRandom.current().nextInt(100, 5000);
			}
		else planetSpawnCooldown--;
		}
	}
	
	public void addObject(GameObject object){
		this.object.add(object);
	}
	
	public void render(Graphics g){
		
		for(int i = 0; i < object.size(); i++){
			GameObject tempObject = object.get(i);
			
			placePlanetsOnBottom(tempObject, i);
			tempObject.render(g);
		}
	}
	
	private void placePlanetsOnBottom(GameObject tempObject, int i){
		if (tempObject.getId()==ID.planetBG){
			object.remove(i);
			object.addFirst(tempObject);
		}
	}
	
	public boolean contains(GameObject object){
		boolean contains = false;
		if (this.object.contains(object)){
			contains = true;
		}
		return contains;
	}
}
