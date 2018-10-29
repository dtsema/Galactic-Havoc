package game;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import javax.imageio.ImageIO;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 5794099463789882206L;
	public static final int WIDTH = 1300, HEIGHT = 700;
	public static enum STATE{
		MENU,
		GAME, 
		SHOP,
		GAMEOVER, 
		SCORES
	};
	public static STATE State = STATE.MENU;
	static Game game;
	Music music;
	private Thread thread, musicThread;
	public static Handler shopHandler;
	Handler gameHandler, menuHandler, gameOverHandler, scoresHandler, loadHandler, asteroidHandler;
	Animation anim;
	private HealthBar hud;
	public static Player player;
	private Wave wave;
	public static int posX = 500, posY = 500;
	public static Timers timeTilWave, load;
	Timers loadMenuTimer;
	private Random random;
	public static boolean upgrade, newGame, running, createNewWave, loader = true;
	boolean on;
	public static int i, waveNumber = 1, dmgUp, hpUp, reloadUp,
			HP = HealthBar.HEALTH, asteroidCoolDown = 100, enemyHealth = 200;
	public String scores;
	public static ArrayList<Lazer> lazerlist;
	public ArrayList<Image> asteroids;
	public static Image enemyTwo;
	public static ArrayList<Image> starlist, planetlist;
	public ArrayList<Asteroid> enemylist;
	private String[] hiscores;
	private ArrayList<Integer> hiscoresInt;
	public static BufferedImage button, buttonPressed, buttonBuy, buttonBuyPressed;
	public static Image lazer1, lazer2, enemyLazer, shopMenu, menuBack, title, 
	hiscoresImg, star, star1, star2, star3, planet, planet1, planet2, planet3, 
	shipBasic, shipUpgrade1, shipUpgrade2, shipUpgrade3, shipUpgrade4, shipUpgrade5, 
	shipUpgrade6, shipUpgrade7, shipUpgrade8, wrench;

	public Game(){
		game = this;
		new Window(WIDTH, HEIGHT, "Galactic Havoc", this);
		
		createLoader();
		
		createHandlers();
		
		addMenusMovingStarsBG();
		
		addGameMovingStarsBG();
		
		createPlayer();
		
		loadBackgroundsAndTitle();
		
		addMenuGameAnimations();
		
		loadEnemyTwo();
		
		createButtons();
		
		loadPlanets();
		
		loadAsteroids();
		
		loadUpgrades();
		
		loadLazers();
		
		loadStars();
		
		loadHealthWrench();
		
		getStoredScores();
		
		addWaveCounter();
		
		createHealthBar();
		
		addKeyListener();
	}
	
	private void createLoader(){
		menuHandler = new Handler();
		loadMenuTimer = new Timers(400);
		loadHandler = new Handler();
		load = new Timers(15000);
		loadHandler.addObject(new Loader(100, 100, ID.Loader));
	}
	
	private void createHandlers(){
		scoresHandler = new Handler();
		gameHandler = new Handler();
		shopHandler = new Handler();
		gameOverHandler = new Handler();
	}
	
	private void addMenusMovingStarsBG(){
		random = new Random();
		
		for (int i = 0; i<80; i++){
			StarBG newStarLeft = new StarBG(random.nextInt(320),  random.nextInt(HEIGHT*5), ID.Star);
			StarBG newStarRight = new StarBG(ThreadLocalRandom.current().nextInt(960,WIDTH),  random.nextInt(HEIGHT*5), ID.Star);	
			menuHandler.addObject(newStarLeft);
			menuHandler.addObject(newStarRight);
			scoresHandler.addObject(newStarLeft);
			scoresHandler.addObject(newStarRight);
		}
	}
	
	private void addGameMovingStarsBG(){
	
		for(int i = 0; i<100; i++){
			gameHandler.addObject(new StarBG(random.nextInt(WIDTH), random.nextInt(HEIGHT*5), ID.Star));
			}
	}
	
	private void createPlayer(){
		player = new Player(posX, posY, ID.Player, gameHandler);
		gameHandler.addObject(player);
	}
	
	private void loadBackgroundsAndTitle(){
		try {
			menuBack = ImageIO.read( new File("menu\\menu.png"));
			shopMenu = ImageIO.read( new File("menu\\shopMenu.png"));
			title = ImageIO.read( new File("titleAnimation\\title.png"));
			hiscoresImg = ImageIO.read( new File("menu\\hiscores.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	private void addMenuGameAnimations(){
		anim = new Animation(player.getX(), 400, ID.Animation);
		gameHandler.addObject(anim);
		menuHandler.addObject(anim);
	}
	
	private void loadEnemyTwo(){
		try{
			enemyTwo = ImageIO.read( new File("enemyShip\\ship2.png"));
		}
		catch ( IOException exc ){   exc.printStackTrace();
		}
	}
	
	private void createButtons(){
		
		try{
		    button = ImageIO.read( new File("button\\defButton.png"));
		    buttonPressed = ImageIO.read( new File("button\\defButtonPressed.png")); 
		    
		    buttonBuy = ImageIO.read( new File("button\\defButtonBuy.png"));
		    buttonBuyPressed = ImageIO.read( new File("button\\defButtonPressedBuy.png"));
		   
		    
		}
		catch ( IOException exc ){   exc.printStackTrace();
		}
		
		menuHandler.addObject(new MenuButton("     Start", 0, this, ID.MenuButton, 0, 0, gameHandler));
		
		menuHandler.addObject(new MenuButton("  Hiscores", 1, this, ID.MenuButton, 0, 0, gameHandler));
		
		scoresHandler.addObject(new MenuButton("Back", 2, this, ID.MenuButton, 0, 60, gameHandler));
		
		gameOverHandler.addObject(new MenuButton("Restart", 1, this, ID.MenuButton,0,0, gameHandler));
		
		shopHandler.addObject(new ShopButton("shipUpgrade1","Ultra Cannon (+Damage) Cost: 100", shopHandler, game, ID.ShopButton, 0, 0, 20, 0, 0));
		
		shopHandler.addObject(new PurchaseButton("Buy", this, ID.PurchaseButton, 0, 0));
	}
	
	private void loadPlanets(){
		try{
			planet = ImageIO.read( new File("planets\\0.png"));
			planet1 = ImageIO.read( new File("planets\\1.png"));
			planet2 = ImageIO.read( new File("planets\\2.png"));
			planet3 = ImageIO.read( new File("planets\\3.png"));
		}catch(Exception e){
			e.printStackTrace();
		}
		planetlist = new ArrayList<Image>();
		planetlist.add(planet);
		planetlist.add(planet1);
		planetlist.add(planet2);
		planetlist.add(planet3);
	}
	
	private void loadAsteroids(){
		asteroids = new ArrayList<>();
		try{	
			Image asteroid = ImageIO.read( new File("C:\\Users\\Daniel\\workspace\\Galactic Havoc\\asteroid\\asteroid1.png"));
			Image asteroid1 = ImageIO.read( new File("C:\\Users\\Daniel\\workspace\\Galactic Havoc\\asteroid\\asteroid2.png"));
			Image asteroid2 = ImageIO.read( new File("C:\\Users\\Daniel\\workspace\\Galactic Havoc\\asteroid\\asteroid3.png"));
			Image asteroid3 = ImageIO.read( new File("C:\\Users\\Daniel\\workspace\\Galactic Havoc\\asteroid\\asteroid4.png"));
			
			asteroids.add(asteroid);
			asteroids.add(asteroid1);
			asteroids.add(asteroid2);
			asteroids.add(asteroid3);
		}catch (IOException exc){ 
			exc.printStackTrace();
		}
	}
	
	private void loadUpgrades(){
		try{
			shipBasic = ImageIO.read( new File("ship\\shipBasic.png"));
			shipUpgrade1 = ImageIO.read( new File("ship\\shipUpgrade1.png"));
			shipUpgrade2 = ImageIO.read( new File("ship\\shipUpgrade2.png"));
			shipUpgrade3 = ImageIO.read( new File("ship\\shipUpgrade3.png"));
			shipUpgrade4 = ImageIO.read( new File("ship\\shipUpgrade4.png"));
			shipUpgrade5 = ImageIO.read( new File("ship\\shipUpgrade5.png"));
			shipUpgrade6 = ImageIO.read( new File("ship\\shipUpgrade6.png"));
			shipUpgrade7 = ImageIO.read( new File("ship\\shipUpgrade7.png"));
			shipUpgrade8 = ImageIO.read( new File("ship\\shipUpgrade8.png"));
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void loadLazers(){
		try{
			lazer1 = ImageIO.read( new File("lazer\\lazer2.png"));
			lazer2 = ImageIO.read( new File("lazer\\lazer2.png"));
			enemyLazer = ImageIO.read( new File("lazer\\enemyLazer1.png"));  
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void loadStars(){
		try {
			star = ImageIO.read( new File("stars\\4.png"));
			star1 = ImageIO.read( new File("stars\\10.png"));
			star2 = ImageIO.read( new File("stars\\5.png"));
			star3 = ImageIO.read( new File("stars\\12.png"));
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		starlist = new ArrayList<Image>();
		starlist.add(star);
		starlist.add(star1);
		starlist.add(star2);
		starlist.add(star3);
		
	}
	
	private void loadHealthWrench(){
		try{
		    wrench = ImageIO.read( new File("health\\wrench.png"));
		}
		catch ( IOException exc ){   exc.printStackTrace();
		}
	}
	
	private void getStoredScores(){
		try {
			
	scores = readTextDoc();
		} catch (IOException e) {
		
			e.printStackTrace();
		}
		hiscores = new String[] {};
		hiscoresInt = new ArrayList<Integer>();
		hiscores = scores.split(System.lineSeparator());
		
		
		for (int i = 0; i < hiscores.length; i++){
			hiscoresInt.add(Integer.parseInt(hiscores[i]));
			
		}
		
		 for (int i = 0; i < hiscoresInt.size()-1; i++)
	            for (int j = 0; j < hiscoresInt.size()-i-1; j++)
	                if (hiscoresInt.get(j) < hiscoresInt.get(j+1))
	                {
	                   
	                    int temp = hiscoresInt.get(j);
	                    hiscoresInt.set(j, hiscoresInt.get(j+1));
	                    hiscoresInt.set(j+1, temp);
	                }
	}
	
	private static String readTextDoc() throws IOException{
		String everything = null;
		try(BufferedReader br = new BufferedReader(new FileReader("scores.txt"))) {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
			everything = sb.toString();
		}
		return everything;
	}
	
	private void addWaveCounter(){
		wave = new Wave(0, 0, ID.Wave, gameHandler);
		gameHandler.addObject(wave);
		timeTilWave = new Timers(300);
	}
	
	private void createHealthBar(){
		hud = new HealthBar(0, 0, ID.HUD);
		gameHandler.addObject(hud);
	}
	
	private void addKeyListener(){
		this.addKeyListener(new KeyInput(gameHandler));
	}
	
	public synchronized void start(){
		thread = new Thread(this);
		musicThread = new Music();
	
		thread.start();
		musicThread.start();
		running= true;
	}
	
	public synchronized void stop(){
		try{
			thread.join();
			running = false;
		}catch(Exception e){
			e.printStackTrace();
		}	
	}
	
	public void run(){
		this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        
        while(running){
        	long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >=1){
            	tick();
                delta--;
            }
            if(running){
            	render();
            }
            if(System.currentTimeMillis() - timer > 1000){
            	timer += 1000;                        
            }
        }
        stop();
	}
	
	private void tick(){
		if (Music.done){
			musicThread= new Music();
			musicThread.start();
			Music.done=false;	
		}
		
		if (State == STATE.GAME && KeyInput.sKey){
			State=STATE.SHOP;
		}
		else if (State == STATE.SHOP && !KeyInput.sKey){
			State=STATE.GAME;	
		}
		if (State == STATE.MENU){
		
			if (loadMenuTimer.cD()){
				System.out.println("down");
				menuHandler.tick();
				}
		}
		if (State == STATE.SHOP){
			 shopHandler.tick();	 
		}
		if (State== STATE.GAMEOVER){
			gameOverHandler.tick();
		} 
		if (State == STATE.SCORES){
			 scoresHandler.tick();
		 }
		if (State == STATE.GAME){
			gameHandler.tick();
			if (HealthBar.dead){
				gameHandler.removeGameObjects();
				updateScores();	
				State = STATE.GAMEOVER;
			}
			spawnAsteroids();
			
			removeEnemy(ID.Asteroid, 1);
			removeEnemy(ID.EnemyPurple, 5);
			removeEnemy(ID.EnemyBlue, 10);
		}
	}
	
	private void removeEnemy(ID id, int points){
		
		for (int i = 0; i<gameHandler.object.size(); i++){
			GameObject temp = gameHandler.object.get(i);
			
			if(temp.getId()==id && temp.getHealth()<1){
				gameHandler.removeObject(temp);
				HealthBar.score+= points;	
			}
			if(temp.getId()==id && temp.y > 1320){
				gameHandler.removeObject(temp);	
			}
		}
	}

	private void updateScores(){
		if (HealthBar.score > hiscoresInt.get(hiscoresInt.size()-1)){
			File log = new File("scores.txt");
			try{
				BufferedReader reader = new BufferedReader(new FileReader(log));
				removeLine(reader, log, Integer.toString(hiscoresInt.get(hiscoresInt.size()-1)));
	
				FileWriter writer = new FileWriter(log, true);
				BufferedWriter reader1 = new BufferedWriter(writer);

				reader1.write(Integer.toString(HealthBar.score));
				reader1.close();
			}catch (IOException E){	
			}
		}
	}
	
	private void spawnAsteroids(){
		if (asteroidCoolDown == 0){
			gameHandler.addObject(new Asteroid(random.nextInt(1100), 550, ID.Asteroid, asteroids));
	
			asteroidCoolDown = 100;
		}
		else asteroidCoolDown--;
	}
	
	public static void removeLine(BufferedReader br , File f,  String Line) throws IOException{
	    File temp = new File("temp.txt");
	    BufferedWriter bw = new BufferedWriter(new FileWriter(temp));
	    String removeID = Line;
	    String currentLine;
	    boolean off = false;
	    while((currentLine = br.readLine()) != null){
	        String trimmedLine = currentLine.trim();
	        if(trimmedLine.equals(removeID) && !off){
	            currentLine = "";
	            off = true;
	        }
	        if (!currentLine.isEmpty()){
	        bw.write(currentLine + System.getProperty("line.separator"));
	        }
	    }
	    bw.close();
	    br.close();
	}
	
	private void render(){
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null){
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		if (State == STATE.MENU){
			if (!load.cD()){
				loadHandler.render(g);
			}
			if (load.cD()){
				g.drawImage(menuBack, 380, 20, null);
				drawMenu(g);
				menuHandler.render(g);
			}
		}
		else if (State == STATE.GAME){
			gameHandler.render(g);
		}
		else if (State == STATE.SHOP){
			drawShop(g);
			shopHandler.render(g);
		}
		else if (State == STATE.GAMEOVER){
			drawGameOver(g);
			gameOverHandler.render(g);
		}
		else if (State == STATE.SCORES){
			drawScores(g);
			scoresHandler.render(g);
		}
		g.dispose();
		bs.show();
	}
	
	private void drawMenu(Graphics g){
		g.setColor(Color.white);
		g.setFont(new Font("Eras Demi ITC", Font.PLAIN, 90)); 			
		g.setFont(new Font("Eras Demi ITC", Font.PLAIN, 20)); 
	
		g.drawString("'up' = shoot", 564, 460);
		g.drawString("'a'= AutoShoot", 583, 490);
		g.drawString("'<- & ->' = movement", 534, 520);
		g.drawString("'s' = Shop", 580, 550);
		g.drawString("'spacebar' = use energy orb", 505, 580);
	}
	
	private void drawShop(Graphics g){
		g.drawImage(shopMenu, 330, 100, null);
		g.setColor(Color.white);
		g.setFont(new Font("Eras Demi ITC", Font.PLAIN, 90)); 
		g.drawString("Shop", 520, 70);
	}
	
	private void drawGameOver(Graphics g){
		g.setColor(Color.WHITE);
		g.setFont(new Font("Eras Demi ITC", Font.PLAIN, 90)); 
		g.drawString("GAME OVER", 100, 100);
		g.drawString("You Scored " + HealthBar.score, 100, 200);
	}
	
	private void drawScores(Graphics g){
		g.drawImage(hiscoresImg, 330, 110, null);
		g.setColor(Color.white);
		g.setFont(new Font("Eras Demi ITC", Font.PLAIN, 90));
		g.drawString("Hiscores", 460, 90);
		
		g.setFont(new Font("Eras Demi ITC", Font.PLAIN, 40));
		g.drawString(Integer.toString(hiscoresInt.get(0)), 590, 190);
		g.setFont(new Font("Eras Demi ITC", Font.PLAIN, 30));
		g.drawString(Integer.toString(hiscoresInt.get(1)), 590, 220);
		g.drawString(Integer.toString(hiscoresInt.get(2)), 590, 250);
		g.drawString(Integer.toString(hiscoresInt.get(3)), 590, 280);
		g.drawString(Integer.toString(hiscoresInt.get(4)), 590, 310);
		g.drawString(Integer.toString(hiscoresInt.get(5)), 590, 340);
		g.drawString(Integer.toString(hiscoresInt.get(6)), 590, 370);
		g.drawString(Integer.toString(hiscoresInt.get(7)), 590, 400);
		g.drawString(Integer.toString(hiscoresInt.get(8)), 590, 430);
		g.drawString(Integer.toString(hiscoresInt.get(9)), 590, 460);
	}
	
	public static void main(String args[]){
		new Game();
	}
}
