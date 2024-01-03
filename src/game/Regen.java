package game;

import javafx.scene.image.Image;

public class Regen extends Item { // The Regen class is a subclass of the Item class.
	
	final static Image REGEN_IMAGE = new Image("images/regen.png",POWERUP_WIDTH,POWERUP_WIDTH,false,false);
	private static final int ADDED_STRENGTH = 50;

	// Creates a new instance of the Regen power-up.
	public Regen(double xPos, double yPos) {
		super(xPos, yPos, REGEN_IMAGE);
		this.name = "Regeneration"; // Sets the name of the power-up.
	}

	// Checks for collision between the Regen power-up and the player's character.
	// If collision occurs, the regeneration effect is applied to the player.
	@Override
	void checkCollision(Viper myViper) {
		if (this.collidesWith(myViper)) {
			System.out.println(myViper.getName() + " has collected " + this.name + "."); 
			this.vanish(); // Regen power-up disappears
			myViper.setStrength(Regen.ADDED_STRENGTH); // Increases the player's strength
		}

	}

}
