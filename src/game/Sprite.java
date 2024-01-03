package game;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class Sprite { // Serves as a base for objects in the game (Boss, Bullet, Item, Slime, and Viper)
	
	protected Image img;
	protected double x, y, dx, dy;
	protected boolean visible;
	protected double width;
	protected double height;

	// Creates a new instance of a Sprite.
	public Sprite(double xPos, double yPos, Image image){
		this.x = xPos;
		this.y = yPos;
		this.loadImage(image);
		this.visible = true;
	}

	// Loads the provided image to be associated with the sprite.
	protected void loadImage(Image img){
		try {
			this.img = img;
	        this.setSize();
		} catch(Exception e) {}
	}

	// Renders the sprite by drawing its image
	void render(GraphicsContext gc){
		gc.drawImage(this.img, this.x, this.y);

    }

	// Method to set the object's width and height properties
	private void setSize(){
		this.width = this.img.getWidth();
	    this.height = this.img.getHeight();
	}
	// Checks if this sprite collides with another sprite by comparing their bounding rectangles.
	public boolean collidesWith(Sprite rect2){
		Rectangle2D rectangle1 = this.getBounds();
		Rectangle2D rectangle2 = rect2.getBounds();
		return rectangle1.intersects(rectangle2);
	}
	
	// Returns the bounding rectangle of the sprite's image.
	private Rectangle2D getBounds(){
		return new Rectangle2D(this.x, this.y, this.width, this.height);
	}

	// Getters
	Image getImage(){
		return this.img;
	}

	public double getX() {
    	return this.x;
	}

	public double getY() {
    	return this.y;
	}

	public double getDX(){
		return this.dx;
	}
	public double getDY(){
		return this.dy;
	}

	public boolean getVisible(){
		return visible;
	}
	public boolean isVisible(){
		if(visible) return true;
		return false;
	}

	// Setters
	public void setDX(int dx){
		this.dx = dx;
	}

	public void setDY(int dy){
		this.dy = dy;
	}

	public void setWidth(double val){
		this.width = val;
	}

	public void setHeight(double val){
		this.height = val;
	}

	public void setVisible(boolean value){
		this.visible = value;
	}

	public void vanish(){
		this.visible = false;
	}

}
