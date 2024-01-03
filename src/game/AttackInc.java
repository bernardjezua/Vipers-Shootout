package game;

import javafx.scene.image.Image;

public class AttackInc extends Item { // The AttackInc class is a subclass of the Item class.
	// AttackInc inherits properties and behaviors from the Item class.
	
	final static Image ATTACK_INC_IMAGE = new Image("images/attack.png",POWERUP_WIDTH,POWERUP_WIDTH,false,false);
	
	public AttackInc(double xPos, double yPos) {
		super(xPos, yPos, ATTACK_INC_IMAGE); 
		this.name = "Attack Increase"; // Sets the name of the power-up.
	}

	// Checks if player (Viper) collides with AttackInc.
	@Override
	void checkCollision(Viper myViper) { 
		if(this.collidesWith(myViper)){
			System.out.println(myViper.getName() + " has collected " + this.name + ".");
			this.vanish(); // Vanishes if the condition is true
			myViper.upgradeBulletType(); // Player's bullet changes from yellow to red to indicate attack increase
		}
	}

}
