package game;

import javafx.scene.image.Image;

class BossPotion extends Bullet { // The BossPotion class is a subclass of the Bullet class.
	
	// Constants for BossPotion behavior and appearance
	private final static int BOSS_POTION_WIDTH = 30;
	private final static int BOSS_POTION_HEIGHT = 30;
	private final static double BOSS_POTION_SPEED = 5;
	private final static Image BOSS_POTION_IMAGE = new Image("images/bossPotion.png",BOSS_POTION_WIDTH,BOSS_POTION_HEIGHT,false,false);
	private final static int BOSS_POTION_DAMAGE = 30;
	
	// Creates a new BossPotion instance.
	BossPotion(int type, double xPos, double yPos) {
		super(type, xPos, yPos, BOSS_POTION_DAMAGE); // method overloading
		this.loadImage(BOSS_POTION_IMAGE);
	}
	
    // Controls the movement behavior of the BossPotion.
	// The BossPotion moves towards the left side of the scene. If it goes beyond the left boundary, it becomes invisible.
	void move(){
		this.x -= BossPotion.BOSS_POTION_SPEED;
		if (this.x <= 0) {  // if this bullet passes through the right of the scene, set visible to false
			this.vanish();
		}
	}
}
