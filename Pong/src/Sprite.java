import java.awt.Color;
import java.awt.Rectangle;

public class Sprite {
	private int xPosition, yPosition;
	private int xVelocity, yVelocity;
	private int width, height;
	private Color colour;
	private int initialXPosition, initialYPosition;

	// Getter methods
	public int getxPosition() {
		return xPosition;
	}
	public int getyPosition() {
		return yPosition;
	}
	public int getxVelocity() {
		return xVelocity;
	}
	public int getyVelocity() {
		return yVelocity;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public Color getColour() {
		return colour;
	}
	public Rectangle getRectangle() {
		//call in methods
		return new Rectangle(getxPosition(), getyPosition(), getWidth(), getHeight());
	}

	// Setter methods
	public void setXPosition(int xPosition) {
		this.xPosition = xPosition;
	}
	public void setYPosition(int yPosition) {
		this.yPosition = yPosition;
	}
	public void setXVelocity(int xVelocity) {
		this.xVelocity = xVelocity;
	}
	public void setYVelocity(int yVelocity) {
		this.yVelocity = yVelocity;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public void setColour(Color colour) {
		this.colour = colour;
	}
	public void setXPosition(int newX, int panelWidth) {
	     xPosition = newX;
	  // check if new x position is outside of the screen boundary. Move inside boundary if not
	     if(xPosition < 0) {
	    	 xPosition = 0;
	     } else if (xPosition + width > panelWidth) {
	    	 xPosition = panelWidth - width;
	     }
	}
	// check y position and move into screen boundary if needed.
	public void setYPosition(int newY, int panelHeight) {
	     yPosition = newY;
	     if(yPosition < 0) {
	    	 yPosition = 0;
	     } else if (yPosition + height > panelHeight) {
	    	 yPosition = panelHeight - height;
	     }
	}
	public void setInitialPosition(int initialX, int initialY) {
		initialXPosition = initialX;
		initialYPosition = initialY;
	}
	public void resetToInitialPosition() {
		setXPosition(initialXPosition);
		setYPosition(initialYPosition);
	}
	
	
	
}
