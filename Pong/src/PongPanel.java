import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.Font;

public class PongPanel extends JPanel implements ActionListener, KeyListener {
	
	private final static Color BLUE_BACKGROUND = new Color(0,0,77);
	private final static Color BACKGROUND_COLOR = BLUE_BACKGROUND;
	private final static Color DOTTED_LINE_COLOR = Color.WHITE;
	private final static int TIMER_DELAY = 5;
	private final static int BALL_MOVEMENT_SPEED = 3;
	private final static int POINTS_TO_WIN = 5;
	private final static int SCORE_FONT_SIZE = 30;
	private final static String SCORE_FONT_FAMILY = "SansSerif";
	private final static int SCORE_TEXT_X = 100;
	private final static int SCORE_TEXT_Y = 100;
	private final static int WINNER_TEXT_X = 150;
	private final static int WINNER_TEXT_Y = 100;
	private final static int WINNER_FONT_SIZE = 30;
	private final static String WINNER_FONT_FAMILY = "SansSerif";
	private final static String WINNER_TEXT = "WINNER!";
	
	int player1Score = 0, player2Score = 0;
	Player gameWinner;
	
	public PongPanel() {
		
		setBackground(BACKGROUND_COLOR);
		// Add timer to game to create loop for movement and animation of objects
		Timer timer = new Timer(TIMER_DELAY,this);
		timer.start();
		
		addKeyListener(this);
		setFocusable(true);
	}
	
	/* Draw ball to the screen
	 * Create gameState variable
	 * Create Ball variable
	*/	
		GameState gameState = GameState.INITIALISING;
		Ball ball;
		
	// Create paddles
		Paddle paddle1, paddle2;
		
		// create ball object
		public void createObjects() {
			ball = new Ball(getWidth(), getHeight());
			paddle1 = new Paddle(Player.One, getWidth(), getHeight());
			paddle2 = new Paddle(Player.Two, getWidth(), getHeight());
		}
	
	/*
	 *  Good practice to create new method 'update()', which will contain the logic
	 *  for each frame update in the game, instead of using the actionPerformed() method
	 *  below. This keeps the code relevant to the method it is in - makes sense to place
	 *  code relating to our updating of the our game.
	 */
	private void update() {
		switch(gameState) {
		case INITIALISING: {
			createObjects();
			gameState = GameState.PLAYING;
			ball.setXVelocity(BALL_MOVEMENT_SPEED);
			ball.setYVelocity(BALL_MOVEMENT_SPEED);
			break;
		}
		case PLAYING: {
			moveObject(paddle1);
			moveObject(paddle2);
			moveObject(ball); // Move ball
			checkWallBounce();	// check for wall bounce
			checkPaddleBounce(); // check for paddle bounce
			checkWin(); // check for game win
			break;
		}
		case GAMEOVER: {
			break;
		}
		}
	}
	// Create paintSprite() method
	private void paintSprite(Graphics g, Sprite sprite) {
		g.setColor(sprite.getColour());
		g.fillRect(sprite.getxPosition(), sprite.getyPosition(), sprite.getWidth(), sprite.getHeight());
	}
	// draw middle dotted line down centre of screen. Call this method from paintComponent()
	private void paintDottedLine(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,
				0, new float[]{9}, 0);
		g2d.setStroke(dashed);
		g2d.setPaint(Color.WHITE);
		//draw line down centre of screen - calc half width and get height
		g2d.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight());
		g2d.dispose();
	}
	private void moveObject(Sprite obj) {
		obj.setXPosition(obj.getxPosition() + obj.getxVelocity(),getWidth());
		obj.setYPosition(obj.getyPosition() + obj.getyVelocity(),getHeight());
	}
	private void checkWallBounce() {
		if(ball.getxPosition() <= 0) {
			//Hit left side of screen
			ball.setXVelocity(-ball.getxVelocity());
			addScore(Player.Two);
			resetBall();
		} else if (ball.getxPosition() >= getWidth() - ball.getWidth()) {
			//Hit right side of screen
			ball.setXVelocity(-ball.getxVelocity());
			addScore(Player.One);
			resetBall();
		}
		if(ball.getyPosition() <= 0 || ball.getyPosition() >= getHeight() - ball.getHeight()) {
			// Hit top or bottom of screen
			ball.setYVelocity(-ball.getyVelocity());
			
		}
	}
	private void checkPaddleBounce() {
		if(ball.getxVelocity() < 0 && ball.getRectangle().intersects(paddle1.getRectangle())) {
			ball.setXVelocity(BALL_MOVEMENT_SPEED);
		}
		if(ball.getxVelocity() > 0 && ball.getRectangle().intersects(paddle2.getRectangle())) {
			ball.setXVelocity(-BALL_MOVEMENT_SPEED);
		}
	}
	
	private void resetBall() {
		ball.resetToInitialPosition();
	}
	private void addScore(Player player) {
		if(player == Player.One) {
			player1Score++;
		} else if (player == Player.Two) {
			player2Score++;
		}
	}
	private void checkWin() {
		if(player1Score >= POINTS_TO_WIN) {
			gameWinner = Player.One;
			gameState = GameState.GAMEOVER;
		} else if (player2Score >= POINTS_TO_WIN) {
			gameWinner = Player.Two;
			gameState = GameState.GAMEOVER;
		}
	}
	private void paintScores(Graphics g) {
		Font scoreFont = new Font(SCORE_FONT_FAMILY, Font.BOLD, SCORE_FONT_SIZE);
		String leftScore = Integer.toString(player1Score);
		String rightScore = Integer.toString(player2Score);
		g.setFont(scoreFont);
		// draw a string of text to screen at given positions
		// two separate strings, so need two separate calls of drawString()
		g.drawString(leftScore, SCORE_TEXT_X, SCORE_TEXT_Y);
		g.drawString(rightScore, getWidth()-SCORE_TEXT_X, SCORE_TEXT_Y);
	}
	private void paintWinner(Graphics g) {
		if(gameWinner != null) {
			Font winnerFont = new Font(WINNER_FONT_FAMILY, Font.BOLD | Font.ITALIC, WINNER_FONT_SIZE);
			g.setFont(winnerFont);
			int xPosition = getWidth() / 2;
			if(gameWinner == Player.One) {
				xPosition -= WINNER_TEXT_X;
			} else if(gameWinner == Player.Two) {
				xPosition += WINNER_TEXT_X;
			}
			g.drawString(WINNER_TEXT, xPosition, WINNER_TEXT_Y);
		}	
	}
	@Override
	public void keyTyped(KeyEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent event) {
		if(event.getKeyCode() == KeyEvent.VK_UP) {
			paddle2.setYVelocity(-3);
		}else if (event.getKeyCode() == KeyEvent.VK_DOWN) {
			paddle2.setYVelocity(3);	
		}
		if(event.getKeyCode() == KeyEvent.VK_W) {
			paddle1.setYVelocity(-3);
		}else if (event.getKeyCode() == KeyEvent.VK_S) {
			paddle1.setYVelocity(3);
		}	
	}

	@Override
	public void keyReleased(KeyEvent event) {
		if(event.getKeyCode() == KeyEvent.VK_UP || event.getKeyCode() == KeyEvent.VK_DOWN) {
			paddle2.setYVelocity(0);
		}
		if(event.getKeyCode() == KeyEvent.VK_W || event.getKeyCode() == KeyEvent.VK_S) {
			paddle1.setYVelocity(0);
		}
	}
	// standard practice in video games to update and repaint
	@Override
	public void actionPerformed(ActionEvent event) {
		update();
		repaint();
		
	}
	/*
	 * Add paintComponent() method and draw a white rectangle on the screen of 20x100px
	 */
	@Override
	public void paintComponent (Graphics g) {
		super.paintComponent(g);
		// call the dotted line method here
		paintDottedLine(g);
		/* These are removed after changes submitted to github
		 * g.setColor(DOTTED_LINE_COLOR);
		g.fillRect(20,20,100,100); */
		// call paintSprite method for the ball
		if(gameState != GameState.INITIALISING) {
			paintSprite(g, ball);
			paintSprite(g, paddle1);
			paintSprite(g, paddle2);
			paintScores(g);
			paintWinner(g);
		}
	}

	
}
