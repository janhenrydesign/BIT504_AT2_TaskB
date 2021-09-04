import java.awt.Color;

public class Ball extends Sprite {

	private static final Color BALL_COLOUR = Color.white;
	private static final int BALL_WIDTH = 25;
	private static final int BALL_HEIGHT = 25;

	public Ball(int panelWidth, int panelHeight) {
		setWidth(BALL_WIDTH);
		setHeight(BALL_HEIGHT);
		setColour(BALL_COLOUR);
		// set initial starting position
		setInitialPosition(panelWidth / 2 - (getWidth() / 2), panelHeight / 2 - (getHeight() / 2));
		// Call resetToInitialPosition() from Sprite class
		resetToInitialPosition();
	}
}
