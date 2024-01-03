package game;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.image.Image;

public class Viper extends Sprite{ // The Viper is a subclass of the Sprite class.
	
	private String name;
	private boolean shielded;
	private boolean alive;
	private ArrayList<Bullet> bullets;
	private int bulletType;
	private int speed;
	private int strength;

	final static Image VIPER_IMAGE = new Image("spriteChar/viper.png",Viper.VIPER_WIDTH,Viper.VIPER_HEIGHT,false,false);
	final static Image UPGRADED_VIPER_IMAGE = new Image("spriteChar/viperUp.png",Viper.VIPER_WIDTH,Viper.VIPER_HEIGHT,false,false);
	final static int INITIAL_SPEED = 3;
	private final static int VIPER_HEIGHT = 60;
	private final static int VIPER_WIDTH = 65;

	// Creates a new instance of Viper
	Viper(String name, int x, int y){
		super(x,y,Viper.VIPER_IMAGE);
		this.name = name;
		Random r = new Random();
		this.strength = r.nextInt(51)+100;
		this.alive = true;
		this.bullets = new ArrayList<Bullet>();
		this.bulletType = 0;
		this.speed = INITIAL_SPEED;
		this.shielded = false;
	}

	// Checks if the Viper character is alive
	boolean isAlive(){
		if(this.alive) return true;
		return false;
	}

	// Retrieves the name of the Viper character
	String getName(){
		return this.name;
	}

	// Sets the Viper character as deceased
	void die(){
	   	this.alive = false;
	}

	// Retrieves the list of bullets fired by Viper
	ArrayList<Bullet> getBullets() {
		return this.bullets;
	}

	// Fires a bullet from Viper's position (specifically from the gun pointing at the slimes)
	void shoot(){
        this.bullets.add(new Bullet(this.bulletType,this.x+63,this.y+24,this.strength)); 
        // x and y are adjusted to match the gun pointing at the viper image
	}
	
	// Moves the Viper character according to the current direction and movement is constrained within the game window
	void move() {
		if (this.x+this.dx >= 0 && this.x+this.dx <= GameStage.WINDOW_WIDTH-this.width)
			this.x += this.dx;
		if (this.y+this.dy >= 10 && this.y+this.dy <= GameStage.WINDOW_HEIGHT-this.height)
			this.y += this.dy;
	}
	
	// Getters and setters
	int getSpeed(){
		return this.speed;
	}

	int getStrength(){
		return this.strength;
	}
	
	boolean getShield(){
		return this.shielded;
	}
	
	void setStrength(int addedStrength){
		this.strength = this.strength + addedStrength;
	}
	
	void setSpeed(int newSpeed) {
		this.speed = newSpeed;
	}
	
	void setShield(boolean s){
		this.shielded = s;
	}

	// Modifies bullet type to normal or upgraded
	void revertBulletType(){
		this.bulletType = 0;
	}
	
	void upgradeBulletType(){
		this.bulletType = 1;
	}

}
