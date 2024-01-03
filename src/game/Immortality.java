package game;

import javafx.scene.image.Image;

public class Immortality extends Item { // The Immortality class is a subclass of the Item class.
	
	final static Image IMMORTALITY_IMAGE = new Image("images/shield.png",POWERUP_WIDTH,POWERUP_WIDTH,false,false);
	
	// Creates a new instance of the Immortality power-up.
	public Immortality(double xPos, double yPos) {
		super(xPos, yPos, IMMORTALITY_IMAGE);
		this.name = "Immortality"; // Sets the name of the power-up.
	}

	// Checks collision between the immortality power-up and the player's character (Viper).
    // If collision occurs, the power-up is collected, the player's character gains temporary immunity to damage, and its appearance is changed.
	@Override
	void checkCollision(Viper myViper) {
		if(this.collidesWith(myViper)){
			System.out.println(myViper.getName() + " has collected " + this.name + ".");
			this.vanish(); // Makes the power-up disappear
			myViper.setShield(true); // Sets shielded as true and grants temporary immunity to damage
			myViper.loadImage(Viper.UPGRADED_VIPER_IMAGE); // Changes Viper's appearance
		}
	}

}
