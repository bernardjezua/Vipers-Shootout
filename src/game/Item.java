package game;

import javafx.scene.image.Image;

abstract class Item extends Sprite { // Serves as a base class for various types of collectible items in the game
	
	protected String name;
	public final static int POWERUP_WIDTH = 30;
	
	// Creates a new instance of an Item.
	public Item(double xPos, double yPos, Image image) {
		super(xPos, yPos, image);
	}
	
	// Abstract method to check collision between the item and a Viper (player character).
	abstract void checkCollision(Viper viper);
	
	// Returns the name of the item.
	String getName(){
		return this.name;
	};

}
