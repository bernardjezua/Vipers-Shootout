package game;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.image.Image;

public class Boss extends Sprite { // The Boss class is a subclass of the Sprite class.

	// Constants for boss behavior and appearance
	private static final double MAX_BOSS_SPEED = 1.5;
	private static final double MIN_BOSS_SPEED = 0.3;
	private static final int BOSS_DAMAGE = 50;
	private final static Image BOSS_IMAGE = new Image("spriteChar/witch.png", Boss.BOSS_WIDTH, Boss.BOSS_WIDTH, false, false);
	private final static int BOSS_WIDTH = 100;

	// Boss attributes
	private int health;
	private boolean alive;
	private double speed;
	private boolean initY;
	private boolean collidedWithViper;
	private ArrayList<BossPotion> potions;
	private int bulletType;
	private String name;

	// Attributes that determine initial movement direction of the boss
	private boolean moveRight;
	private boolean moveUp;

	// Class constructor for Boss (Witch)
	Boss(int x, int y, boolean alive) {
		super(x,y,BOSS_IMAGE);
		this.name = "The Witch";
		this.alive = alive;
		this.health = 3000;
		this.moveRight = true;
		this.moveUp = true;
		this.initY = false;
		this.bulletType = 0;
		this.potions = new ArrayList<BossPotion>();

		Random r = new Random();
		this.speed = r.nextDouble()*(MAX_BOSS_SPEED-MIN_BOSS_SPEED) + MIN_BOSS_SPEED;
	}

	// Method that controls the witch's movement behavior
	void move(){
		if (this.moveRight){
			if (this.x <= GameStage.WINDOW_WIDTH-50){ this.x += this.speed; }
			else { this.moveRight = false; }
		}
		if (!this.moveRight){
			if (this.x > 0){ this.x -= this.speed; }
			else { this.moveRight = true; }
		}
		if (this.moveUp){
			if (this.y <= GameStage.WINDOW_HEIGHT-50){ this.y += this.speed; }
			else { this.moveUp = false; }
		}
		if (!this.moveUp){
			if (this.y > 0){ this.y -= this.speed; }
			else { this.moveUp = true; }
		}
	}

	// Checks for collision with the player (Viper) and performs necessary actions.
	void checkCollision(Viper myViper){
		for	(int i = 0; i < myViper.getBullets().size(); i++)	{
			if (this.collidesWith(myViper.getBullets().get(i))){
				this.getHit(myViper.getBullets().get(i).getDamage());
				myViper.getBullets().get(i).vanish();
				System.out.println(myViper.getName() + "'s bullet hit the witch. Current boss health: " + this.getHealth());
			}
			if (this.getHealth() <= 0) { this.die(); }
		}
			
		for	(int i = 0; i < this.getBossPotions().size(); i++)	{
			if (myViper.collidesWith(this.getBossPotions().get(i))){
				myViper.setStrength(-(this.getBossPotions().get(i).getDamage()));;
				this.getBossPotions().get(i).vanish();
				System.out.println(this.name + "'s potion hit the player. Current player strength: " + myViper.getStrength());
			}
			if (myViper.getStrength() <= 0) { myViper.die(); }
		}
			
		if (this.collidesWith(myViper)){
			if (myViper.getShield() == false){
				if (this.collidedWithViper == false){
					myViper.setStrength(-BOSS_DAMAGE);
					System.out.println(myViper.getName() + " hits the witch and took damage. Current player strength: " + myViper.getStrength());
					this.collidedWithViper = true;
					if(myViper.getStrength() <= 0) myViper.die();
				}

			} else {
				System.out.println(myViper.getName() + " hits the witch but did not receive damage due to immortality.");
			}
		}
		if (this.collidesWith(myViper) == false) { this.collidedWithViper = false; }

		}
	
	// Adds an instance of a potion in the array list of BossPotion
	void shoot(){ 
	    this.potions.add(new BossPotion(this.bulletType,this.x-27,this.y+35));
	}
	
	// Inflicts damage on the witch
	private void getHit(int damage){ 
		this.setHealth(this.getHealth() - damage); // Decreases health of the witch
	}

	// Getter and setter methods
	ArrayList<BossPotion> getBossPotions(){ // Gets the array list of BossPotion
		return this.potions;
	}
	
	boolean isAlive() { // Gets the boolean value of witch status, checks if the witch is alive
		return this.alive;
	}
	
	public int getHealth() { // Gets the health of the witch
		return health;
	}
	
	boolean getInitY(){ // Gets the initial Y attribute
		return this.initY;
	}
	
	void die(){ // Sets the witch's status to deceased
		this.alive = false;
		this.vanish();
	}
	
	void setY(int y){ // Sets the y-coordinate
		this.y = y;
	}
	
	void setInitY(boolean y){ // Sets the initial Y attribute
		this.initY = y;
	}

	public void setHealth(int health) { // Sets health of the witch
		this.health = health;
	}

	void setAlive(boolean alive){ // Sets the alive status
		this.alive = alive;
	}	
}
