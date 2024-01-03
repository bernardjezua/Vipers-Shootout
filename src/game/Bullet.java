package game;

import javafx.scene.image.Image;

public class Bullet extends Sprite { // The Bullet class is a subclass of the Sprite class.
	private int damage;

	// Constants for bullet types and appearance
	public final static int ORDINARY_BULLET = 0;
	public final static int UPGRADED_BULLET = 1;
	private final static int BULLET_WIDTH = 30;
	private final static int BULLET_HEIGHT = 10;
	protected final static double BULLET_SPEED = 10;
	private final static Image ORDINARY_BULLET_IMAGE = new Image("images/bullet.png",BULLET_WIDTH,BULLET_HEIGHT,false,false);
	private final static Image UPGRADED_BULLET_IMAGE = new Image("images/bullet2.png",BULLET_WIDTH,BULLET_HEIGHT,false,false);
	private final static int UPGRADED_BULLET_ATK_BOOST = 100;

	// Creates a new Bullet instance.
	public Bullet(int type, double xPos, double yPos, int strength) {
		super(xPos, yPos, Bullet.ORDINARY_BULLET_IMAGE);
		this.damage = strength;
		if(type == 1) this.loadImage(Bullet.UPGRADED_BULLET_IMAGE);
		if(type == 1) this.damage = this.damage+=UPGRADED_BULLET_ATK_BOOST;
	}

	// Gets the damage value of the bullet.
    int getDamage(){
    	return this.damage;
    }

    // Moves the bullet horizontally across the screen. If the bullet passes the right edge of the screen, it becomes invisible.
	void move(){
		this.x += Bullet.BULLET_SPEED;
		if(this.x >= 800) {
			this.vanish();
		}
	}

}
