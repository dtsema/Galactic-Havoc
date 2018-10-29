package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

public class Wave extends GameObject {
	
	double alpha = 1;
	boolean on = false;
	public static boolean energyOrb=false;
	Handler handler;
	EnemyPurple enemyPurple;
	EnemyBlue enemyBlue;
	ArrayList<GameObject> wave, enemies;
	Timers enemySpawnTimer;
	Random random;
	int enemyQuantMax = Game.waveNumber*2;
	
	public Wave(int x, int y, ID id, Handler handler){
		super(x, y, id);
		this.handler=handler;
		enemySpawnTimer = new Timers(100);
		random = new Random();
		increaseEnemyHealthEveryTenWaves();
		spawnEnergyOrbEveryTenWaves();

		wave= new ArrayList<GameObject>();
		enemies = new ArrayList<GameObject>();
		createEnemies();
		
	}
	
	private void increaseEnemyHealthEveryTenWaves(){
		if(Game.waveNumber%10==0){
			Game.enemyHealth=(int) ((Game.enemyHealth*0.1)+Game.enemyHealth);
		}
	}
	
	private void spawnEnergyOrbEveryTenWaves(){
		if (Game.waveNumber%10==0){
		energyOrb=true;
		Player.orb++;
		}
	}
	
	private void createEnemies(){
		for (int i = 0; i<=random.nextInt(enemyQuantMax+3); i++){
			wave.add(new EnemyPurple(random.nextInt(1100), -100, ID.EnemyPurple, handler, Game.enemyHealth));
			if (random.nextInt(5)==2){
				wave.add(new EnemyBlue(random.nextInt(1100), -100, ID.EnemyBlue, handler));
			}
			if (random.nextInt(11)==2){
				handler.addObject(new HealthPack(random.nextInt(1100), -100, ID.HealthPack));
			}
		}
	}
	
	public void tick(){
		
		addEnergyOrb();
		
		addEnemies();
		
		removeDeadEnemies();
		
		nextWaveWhenFinished();
	}
	
	private void addEnergyOrb(){
		if (Player.orb==1){
			Animation.showEnergyOrbText=true;
		}
		else if (Player.orb>1 || Player.orb<1){
			Animation.showEnergyOrbText=false;
		}
		if (KeyInput.spaceBar && Wave.energyOrb){
			Player.orb++;
			energyOrb=false;
		}
	}
	
	private void addEnemies(){
		if (!wave.isEmpty() && enemySpawnTimer.cD()){
			handler.addObject(wave.get(0));
			enemies.add(wave.get(0));
			wave.remove(0);
			enemySpawnTimer.setCount(100);	
		}
	}
	
	private void removeDeadEnemies(){
		for (int i = 0; i< enemies.size(); i++){
			if (enemies.get(i).getHealth()<1) enemies.remove(i);
		}
	}
	
	private void nextWaveWhenFinished(){
		if(wave.isEmpty() && enemies.isEmpty()){
			Game.waveNumber++;
			Game.createNewWave= true;
			Game.timeTilWave.setCount(300);
			handler.removeObject(this);
		}
	}
	
	public void render(Graphics g){
		createWaveText(g, Game.waveNumber);
	}
	
	private Graphics createWaveText(Graphics g, int wave){
		if(alpha<=200 && alpha>=-1 && on == false){
			alpha = alpha +0.3;
			if (alpha>=200){
				on = true;	
			}}
			else if (alpha>2){
			alpha= alpha-0.3;
		}	
		
		if (alpha>=0){ 
			
			Color myColour = new Color(255, 255, 255, (int) alpha);
			g.setFont(new Font("TimesRoman", Font.PLAIN, 60)); 
			g.setColor(myColour);}
			g.drawString("Wave " + wave, 1050, 630);
		
		return g;
	}
}
