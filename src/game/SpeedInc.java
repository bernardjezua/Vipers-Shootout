package game;

import javafx.scene.image.Image;

public class SpeedInc extends Item{ // The SpeedInc class is a subclass of the Item class.
	
	// Constants and attributes specific to the Speed Increase power-up
	final static Image SPEED_INC_IMAGE = new Image("images/speed.png",POWERUP_WIDTH,POWERUP_WIDTH,false,false);
	private final static int CHANGE_SPEED = 5; 
	
	// Creates a new instance of the Speed Increase power-up.
	public SpeedInc(double xPos, double yPos) {
		super(xPos, yPos, SPEED_INC_IMAGE);
		this.name = "Speed Increase"; // Sets the name of the power-up.
	}

	// Checks for collision between the Speed Increase power-up and the player's character (Viper).
	// If collision occurs, the speed boost effect is applied to the player.
	@Override
	void checkCollision(Viper myViper) {
		if (this.collidesWith(myViper)){
			System.out.println(myViper.getName() + " has collected " + this.name + ".");
			this.vanish(); // Speed Increase power-up disappear
			myViper.setSpeed(SpeedInc.CHANGE_SPEED); // Increases the player's speed
		}

	}

}
