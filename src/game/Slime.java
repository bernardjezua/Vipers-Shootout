package game;

import java.util.Random;

import javafx.scene.image.Image;

public class Slime extends Sprite { // The Slime class is a subclass of the Sprite class.
	
	// Constants and attributes specific to the Slime entity
	public static final double MAX_SLIME_SPEED = 1.5;
	public static final double MIN_SLIME_SPEED = 0.5;
	public final static Image SLIME_IMAGE = new Image("spriteChar/slime.png",Slime.SLIME_WIDTH,Slime.SLIME_WIDTH,false,false);
	public final static int SLIME_WIDTH = 50;
	private boolean alive;
	
	// Attributes controlling the slime's behavior
	private boolean moveRight;
	private double speed;
	private int damage;
	private final static int INITIAL_DAMAGE = 30;

	// Creates a new instance of the Slime entity at the specified position.
	Slime(int x, int y){
		super(x,y,SLIME_IMAGE);
		this.alive = true;
		Random r = new Random();
		this.speed = r.nextDouble()*(MAX_SLIME_SPEED-MIN_SLIME_SPEED) + MIN_SLIME_SPEED;
		this.moveRight = true;
		this.damage = r.nextInt(11)+INITIAL_DAMAGE;
	}

	// Moves the Slime horizontally across the screen, changing its x position.
	void move(){
		if (this.moveRight){
			if (this.x < GameStage.WINDOW_WIDTH-50){
				this.x += this.speed;
			}
			else{
				this.moveRight = false;
			}
		}
		if (!this.moveRight){
			if (this.x > 0){
				this.x -= this.speed;
			}
			else{
				this.moveRight = true;
			}
		}
	}
	
	// Checks for collisions between the Slime and the player's character (Viper).
	void checkCollision(Viper myViper){
		for	(int i = 0; i < myViper.getBullets().size(); i++){  // loops through each viper's bullet and its size
			if (this.collidesWith(myViper.getBullets().get(i))){ // checks if 
				this.getHit(myViper.getBullets().get(i).getDamage());
				myViper.getBullets().get(i).vanish();
			}
		}

		if (this.collidesWith(myViper)) {
			if (myViper.getShield() == false) {
				myViper.setStrength(-this.damage);
			} else{
				System.out.println(myViper.getName() + " hits a slime but did not receive damage due to immortality.");
			}
			this.vanish();
			if(myViper.getStrength() <= 0){
				myViper.die();
				System.out.println(myViper.getName() + " died.");
			}
		}
	}
	
	// Getter method that checks if the Slime is alive
	public boolean isAlive() {
		return this.alive;
	}

	// Sets the alive status of the slime as false/dead
	public void die(){
    	this.alive = false;
    }

	// Instance of slime vanishes when hit by Viper's bullet
	private void getHit(int damage){
		this.vanish();
	}
	
	// Sets the damage value of the Slime.
	void setDamage(int newDamage){
		this.damage = newDamage;
	}
}
