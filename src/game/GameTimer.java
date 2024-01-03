package game;

import java.util.ArrayList;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class GameTimer extends AnimationTimer {

	private GraphicsContext gc;
	private Scene theScene;
	private Viper myViper;
	private Boss witch;

	private long startSlimeSpawn;
	private long startPowerUpSpawn;
	private long Time;
	private long DeletePowerUp;

	private ArrayList<Slime> slimes;
	private ArrayList<Item> powerups;

	private boolean successfulBossKill;
	private int spawnSlimeCounter;
	private int enemiesKilled;
	private int secondCounter;
	private int gameCounter;
	private int attackIncCounter;
	private int immortalityCounter;
	private int speedIncCounter;
	private int powerUpTotalCounter;
	private long witchShootTimer;
	private String gameCounterText;

	// Constants
	private final static int INITIAL_VIPER_XPOS = 50;
	private final static int INITIAL_VIPER_YPOS = 100;
	private final static int INITIAL_BOSS_XPOS = GameStage.WINDOW_WIDTH;
	private final static int INITIAL_BOSS_YPOS = GameStage.WINDOW_HEIGHT-100;
	private final static int INITIAL_NUM_SLIME = 7;
	private final static int NUM_POWERUPS = 4;
	private final static int SPAWN_NUM_SLIME = 3;
	private final static int SPAWN_SLIME_DELAY = 5;
	private final static int SPAWN_POWERUP_DELAY = 10;
	private final static int SPAWN_BOSS_DELAY = 30;

	// Creates a new instance of the GameTimer.
	GameTimer (GraphicsContext gc, Scene theScene){
		this.gc = gc;
		this.theScene = theScene;
		this.myViper = new Viper("Player",INITIAL_VIPER_XPOS,INITIAL_VIPER_YPOS);
		this.startSlimeSpawn = this.startPowerUpSpawn = this.Time = System.nanoTime(); // Instances of time and spawn timers are equal to its nanotime
		
		// Instantiate the ArrayList of Slime
		this.slimes = new ArrayList<Slime>();
		this.powerups = new ArrayList<Item>();
		this.spawnSlimeCounter = 0;
		this.enemiesKilled = 0;
		this.gameCounter = 60;
		this.gameCounterText = "1:00";
		this.witch = new Boss(INITIAL_BOSS_XPOS,INITIAL_BOSS_YPOS,false);
		this.successfulBossKill = false;

		// Call method to handle key click event
		this.handleKeyPressEvent();
	}

	// Method is called repeatedly by the JavaFX framework to update and render the game
	public void handle(long currentTime) {
		seconds(currentTime); // currentTime = in nanoseconds
		
		// Clears canvas
		this.gc.clearRect(0, 0, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
		
		this.spawnSlime(currentTime);
		this.spawnPowerUps(currentTime);
		this.myViper.move();

		// Call the methods to move the slimes
		this.moveSlime();

		// Renders viper
		this.myViper.render(this.gc);

		// Renders the Power Ups
		this.renderPowerUps();

		// Renders the slimes
		this.renderSlime();

		// Renders the bullets
		this.renderBullets();
		
		// Spawns the witch
		if (secondCounter == SPAWN_BOSS_DELAY) {
			Random r = new Random();
			if(this.witch.getInitY() == false) {
				this.witch.setY(r.nextInt(GameStage.WINDOW_HEIGHT-100));
				this.witch.setInitY(true);
				this.witch.setAlive(true);
			}
		}
		
		if (secondCounter >= SPAWN_BOSS_DELAY){
			if (this.witch.isAlive()) {
				this.witch.render(this.gc);
				this.witchShoot(currentTime);

			// Calls the methods to move the witch 
			this.moveBoss();
			}
			if ((this.witch.isAlive() == false) && this.successfulBossKill == false) {
				this.successfulBossKill = true;
				this.enemiesKilled++;
				System.out.println("Successfully killed the witch. Enemies killed: " + this.enemiesKilled);
			}
		}
		
		deletePowerUps(currentTime);
		returnStats();
		this.drawTime(); // draws the status bar in the top left corner

        if(!this.myViper.isAlive()) { // checks if viper is not alive
        	this.stop();
        	this.drawGameOver();
        }
        
        if(this.gameCounter == 0) { // checks if game counter runs out
        	this.stop();
        	this.drawGameOver();
        }
	}
	
	// Method that updates the game timer text and second counter (for checking stats)
	private void seconds(long currentTime){
		// Condition that checks if the time converted from nanoseconds to seconds elapses 1.00s
		// To check the instance of time in seconds, currentTime is divided by 1,000,000,000 (1 billion), 1 nanosecond = 1^e-9 second
		if (((currentTime-this.Time) / 1000000000.0) >= 1){ 
			this.secondCounter++;
			this.gameCounter = 60 - this.secondCounter; // 60 - running time
			if (gameCounter <= 9) { // updates game counter text (single digit / double digit count)
				this.gameCounterText = "0:0" + gameCounter; // single digit
			} else {
				this.gameCounterText = "0:" + gameCounter; // double digit
			}
			this.Time = System.nanoTime(); // updates time or resets back to 0
		}
	}
	
	// Draw game stats including time, score, player's strength, and witch health
	private void drawTime(){
		this.gc.setFont(GameStage.DESC_FONT);
		this.gc.setFill(GameStage.FONT_COLOR);
		if (witch.isAlive()) {
			this.gc.fillText("Time: " + gameCounterText + "\t\tScore: " + enemiesKilled + "   \t\tStrength: " + myViper.getStrength() 
			+ " \t\tPower Ups Collected: " + powerUpTotalCounter + " \t\tBoss HP: " + witch.getHealth(), 20, 20);
		} else {
			this.gc.fillText("Time: " + gameCounterText + "\t\tScore: " + enemiesKilled + "   \t\tStrength: " + myViper.getStrength()
			+ "  \t\tPower Ups Collected: " + powerUpTotalCounter, 20, 20);
		}
	}

	// Methods for handling game play mechanics
	// Draws the game over screen with score, result, and console messages
	private void drawGameOver(){ 
		this.gc.setFont(Font.font("Verdana", FontWeight.BOLD, 50));
		this.gc.setFill(Color.WHITE);
		this.gc.fillText("GAME OVER!", (GameStage.WINDOW_WIDTH/4), (GameStage.WINDOW_HEIGHT/2));
		if (!this.myViper.isAlive()) { // Draws YOU LOSE when instance of Viper dies
			this.gc.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
			if (this.enemiesKilled >= 10) { // adjusts depending on the score
				this.gc.fillText("YOU LOSE!", (GameStage.WINDOW_WIDTH/4)+80, (GameStage.WINDOW_HEIGHT/2)+40);
				this.gc.fillText("Score: " + enemiesKilled, (GameStage.WINDOW_WIDTH/4)+87, (GameStage.WINDOW_HEIGHT/2)+80);
				System.out.println(myViper.getName() + " LOSES! | Final Score: " + enemiesKilled);
			} else {
				this.gc.fillText("YOU LOSE!", (GameStage.WINDOW_WIDTH/4)+80, (GameStage.WINDOW_HEIGHT/2)+40);
				this.gc.fillText("Score: " + enemiesKilled, (GameStage.WINDOW_WIDTH/4)+97, (GameStage.WINDOW_HEIGHT/2)+80);
				System.out.println(myViper.getName() + " LOSES! | Final Score: " + enemiesKilled);
			}
	    }
		else if (this.secondCounter == 60) {
	    	this.gc.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
			if (this.enemiesKilled >= 10) { // adjusts depending on the score
				this.gc.fillText("YOU WIN!", (GameStage.WINDOW_WIDTH/4)+82, (GameStage.WINDOW_HEIGHT/2)+40);
				this.gc.fillText("Score: " + enemiesKilled, (GameStage.WINDOW_WIDTH/4)+86, (GameStage.WINDOW_HEIGHT/2)+80);
				System.out.println(myViper.getName() + " WINS! | Final Score: " + enemiesKilled);
			} else {
				this.gc.fillText("YOU WIN!", (GameStage.WINDOW_WIDTH/4)+80, (GameStage.WINDOW_HEIGHT/2)+40);
				this.gc.fillText("Score: " + enemiesKilled, (GameStage.WINDOW_WIDTH/4)+95, (GameStage.WINDOW_HEIGHT/2)+80);
				System.out.println(myViper.getName() + " WINS! | Final Score: " + enemiesKilled);
			}
	    }
	}

	// Render/draw the slimes on the canvas
	private void renderSlime() {
		for (Slime slime : this.slimes){
			slime.render(this.gc);
		}
		moveSlime();
	}

	// Render/draw the bullets and boss potions on the canvas
	private void renderBullets() {
		for (Bullet b : this.myViper.getBullets())
	       	b.render( this.gc );
		for (BossPotion bb : this.witch.getBossPotions())
	       	bb.render( this.gc );
		moveBullets();

	}

	// Render/draw the power-ups on the canvas
	private void renderPowerUps() {
		for (Item p : this.powerups)
		    p.render( this.gc );
	}

	// Instantiates three slimes at a random x and y locations
	private void generateSlime(int numberOfSlime){
		Random r = new Random(); // randomize number for speed in y
		for (int i=0; i < numberOfSlime;i++) { // loops through each instance of slime
			int x = GameStage.WINDOW_WIDTH-50;
			int y = r.nextInt(GameStage.WINDOW_HEIGHT-70);
			this.slimes.add(new Slime(x, 10+y)); // adds an instance of slime in the array list of slimes, adds more to renderSlime() method
		}
	}
	
	// Spawn slimes at specific intervals and locations
	private void spawnSlime(long currentTime){
		double spawnElapsedTime = (currentTime - this.startSlimeSpawn) / 1000000000.0; // convert nanoseconds to seconds
		if (this.spawnSlimeCounter == 0){ // checks if initial number of slime has not been generated
		   	this.generateSlime(INITIAL_NUM_SLIME); // initial number of slime = 7
		   	this.spawnSlimeCounter++; // increments counter of spawned slime, false if condition
		} else if (spawnElapsedTime > GameTimer.SPAWN_SLIME_DELAY) { // checks if spawnElapsedTime is greater than 5 seconds 
	        this.generateSlime(SPAWN_NUM_SLIME); // spawn number of slime = 3
	        this.startSlimeSpawn = System.nanoTime(); // resets slime spawn timer to its nanoTime (0 to compare again until 5)
	        this.spawnSlimeCounter++;
	    }
	}

	private void generatePowerUps(int noOfPowerUps){
		Random r = new Random();
		for (int i=0; i < noOfPowerUps; i++) { // generates 0 to 4 power ups at random
			int x = r.nextInt((GameStage.WINDOW_WIDTH/2)-50);
			int y = r.nextInt(GameStage.WINDOW_HEIGHT-50);
			if (i==0) { // adds a random power up to the array list of power ups to be rendered
				this.powerups.add(new AttackInc(x, y)); 
			}
			if (i==1) {
				this.powerups.add(new Immortality(x, y));
			}
			if (i==2) {
				this.powerups.add(new Regen(x, y));
			}
			if (i==3) {
				this.powerups.add(new SpeedInc(x, y));
			}
		}
	}

	// Spawn power-ups at specific intervals
	private void spawnPowerUps(long currentTime){
		double spawnElapsedTime = (currentTime - this.startPowerUpSpawn) / 1000000000.0; // same with spawn slime in checking
		// spawn powerups
		if (spawnElapsedTime > GameTimer.SPAWN_POWERUP_DELAY) { // ten seconds delay
	       	this.generatePowerUps(GameTimer.NUM_POWERUPS); // generates power ups
	        this.startPowerUpSpawn = System.nanoTime(); // resets time to compare again until 10 seconds elapsed
	        this.DeletePowerUp = System.nanoTime(); // updates time to check 
	    }
		collectPowerUps(currentTime); // check if collides with viper or not
	}
	
	// Collect and apply effects of power-ups, such as attack increase, immortality, regeneration, and speed increase
	private void collectPowerUps(long currentTime){
	    for(int i = 0; i < this.powerups.size(); i++){
	        Item p = this.powerups.get(i);
	        if (p.isVisible()){
	            p.checkCollision(this.myViper);
	            
	            if (p.collidesWith(this.myViper)) {
	            	this.powerUpTotalCounter++; // increments total counter of power ups collected
	            }
	            
	            if (p.getName() == "Attack Increase") {
	                this.attackIncCounter = this.secondCounter; // updates attack increase counter to compare if 3 seconds have lapsed
	            } if (p.getName() == "Immortality") {
	                this.immortalityCounter = this.secondCounter; // updates immortality counter to compare if 5 seconds have lapsed
	            } if (p.getName() == "Speed Increase") {
	                this.speedIncCounter = this.secondCounter; // updates speed increase counter to compare if 3 seconds have lapsed
	            }
	        }
	        if (p.isVisible() == false) this.powerups.remove(i); // removes power up in the list of power ups
	    }
	}
	
	// Delete expired power-ups after a certain duration (5 seconds)
	private void deletePowerUps(long currentTime){
		for(int i = 0; i < this.powerups.size(); i++){
			Item p = this.powerups.get(i); // gets all objects in powerups array
			if (((currentTime-this.DeletePowerUp) / 1000000000.0) >= 5){ // checks if 5 seconds have elapsed
				if(p.visible) p.vanish(); // power up disappears
				this.powerups.remove(i); // removed from the array list
			}
		}
	}

	// Return player stats after the effect of power-ups wears off
	private void returnStats(){
	    if (this.secondCounter - this.attackIncCounter == 3) { // (current runtime - when the power up was applied) == time
	    	myViper.revertBulletType(); // returns to basic bullet form
	    }

	    if (this.secondCounter - this.immortalityCounter == 5) {
	        myViper.loadImage(Viper.VIPER_IMAGE); // shows different sprite appearance 
	        myViper.setShield(false); // sets immunity as false
	    }

	    if (this.secondCounter - this.speedIncCounter == 3) {
	    	myViper.setSpeed(Viper.INITIAL_SPEED); // returns to initial speed
	    }
	}

	// Listen and handle key press events for controlling the player character
	private void handleKeyPressEvent() {
		theScene.setOnKeyPressed(new EventHandler<KeyEvent>(){
			public void handle(KeyEvent e){
            	KeyCode code = e.getCode();
                moveMyViper(code);
			}

		});

		theScene.setOnKeyReleased(new EventHandler<KeyEvent>(){
		    public void handle(KeyEvent e){
		        KeyCode code = e.getCode();
		        stopMyViper(code);
		    }
		});
    }

	// Move the player character (Viper) based on the key pressed
	private void moveMyViper(KeyCode ke) {
		if(ke==KeyCode.UP) this.myViper.setDY(-myViper.getSpeed());

		if(ke==KeyCode.LEFT) this.myViper.setDX(-myViper.getSpeed());

		if(ke==KeyCode.DOWN) this.myViper.setDY(myViper.getSpeed());

		if(ke==KeyCode.RIGHT) this.myViper.setDX(myViper.getSpeed());

		if(ke==KeyCode.SPACE) this.myViper.shoot();
   	}

	// Stop the movement of the player character (Viper)
	private void stopMyViper(KeyCode ke) {
        if (ke == KeyCode.UP) myViper.setDY(0);

        if (ke == KeyCode.LEFT) myViper.setDX(0);

        if (ke == KeyCode.DOWN) myViper.setDY(0);

        if (ke == KeyCode.RIGHT) myViper.setDX(0);
    }

	// Moves the witch and handle collisions
	private void moveBoss(){
		Boss witch = this.witch;
		if (witch.isVisible()){
			witch.move();
			witch.checkCollision(this.myViper);
		}
	}
	
	// Move bullets and witch potions, handle visibility and removal
	private void moveBullets(){
		ArrayList<Bullet> bList = this.myViper.getBullets(); // bullet list array from viper's array list
		for(int i = 0; i < bList.size(); i++){
			Bullet b = bList.get(i);
			if (b.isVisible())
				b.move(); // moves left to right
			else bList.remove(i); // removes bullet from array list of viper's bullets (not being able to render)
		}

		ArrayList<BossPotion> bbList = this.witch.getBossPotions();
		for(int i = 0; i < bbList.size(); i++){
			BossPotion bb = bbList.get(i);
			if (bb.isVisible()) bb.move();
			else bbList.remove(i);
		}
	}
	
	// Moves the slimes, check collisions, and update score
	private void moveSlime(){
		// Loop through the slime array list
		for(int i = 0; i < this.slimes.size(); i++){
			Slime a = this.slimes.get(i);
			if (a.isVisible()){ // conditional statement to check if slime collides with viper
				a.move();
				a.checkCollision(this.myViper); // calls checkCollision in slime class, go to line 53
			}
			else {
				this.slimes.remove(i);
				this.enemiesKilled++;
				System.out.println(myViper.getName() + " destroyed a slime. Enemies Killed: " + this.enemiesKilled);
			}
		}
	}

	// Makes the witch throw potions at a regular interval
	private void witchShoot(long currentTime){
		if(((currentTime-this.witchShootTimer) / 1000000000.0) >= 1.5) {
			this.witch.shoot();
			this.witchShootTimer = System.nanoTime();
		}

	}
}

