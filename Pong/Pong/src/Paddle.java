import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Paddle extends Rectangle {
	//dimensions of game
	public static final int GAME_WIDTH = 800; 
	public static final int GAME_HEIGHT = 600;

	private int currentSpeed = 0; //current speed of the paddle, initially not moving
	private int defaultSpeed = 15; //default speed of the paddle
	private static int paddleWidth = 30, paddleHeight = 150; // dimensions of the paddle
	private int score = 0; //score for the player controlling the paddle
	private boolean isRight; //true if it is the right paddle, false if it is the left

	
	public Paddle(int x, int y, boolean isRight) { //constructor for paddle class
		//x, y are the initial positions of the paddle
		//isRight keeps track whether it is the left or right paddle
		super(x, y, paddleWidth, paddleHeight); //create the rectangle object 
		this.isRight = isRight; 
	}
	
	//method to increase player score
	public void increaseScore() {
		score++;
	}
	//method to return player score 
	public int getScore () {
		return score; 
	}

	//draw the paddle
	public void draw (Graphics g) {
		
		 if(this.isRight == true) { //draw the right sprite
			BufferedImage rightPlayer = null;
	      try {
	          rightPlayer = ImageIO.read(new File("src\\images", "rightSprite.png")); //read the image
	         
	      } catch (IOException e) {
	      } 
	      g.drawImage(rightPlayer, x, y, null); //draw the image
		 }
		 else { //draw the left sprite
			 BufferedImage leftPlayer = null;
		      try {
		          leftPlayer = ImageIO.read(new File("src\\images", "leftSprite.png")); //read the image
		      } catch (IOException e) {
		      } 
		      g.drawImage(leftPlayer, x, y, null);//draw the image
		 }
		 
		 String playerScore = Integer.toString(score);
			Font font = new Font("SansSerif", Font.PLAIN, 30); //set font and font size
			g.setFont(font);
			if(isRight) {
				g.setColor(Color.green); //player 1 is displayed in green
				g.drawString("Player 1 (Controls: W,S)", 40, 50);
				g.drawString("First to 7 wins!", 310, 130);
				g.drawString("Score:  " + playerScore, 40, 80); //draw score
			}
			else {
				g.setColor(Color.red); //player 2 is displayed in red
				g.drawString("Player 2 (Controls: ↑,↓)", 450, 50);
				g.drawString("Score:  " +  playerScore, 450, 80); //draw score
			}
	}
	
	public void displayWin(Graphics g) {
		Font font = new Font("SansSerif", Font.PLAIN, 100); //set font and font size
		g.setFont(font);
		if(isRight==true) {
			g.drawString("Player 1 wins!!", 100, 330);
		}
		else {
			g.drawString("Player 2 wins!!", 100, 330);
		}
	}
	public void move ()
	{
		y += currentSpeed; //move the paddle depending on the speed and direction 
		
		//stops the paddle from going out of bounds 
		if(y + height>= GAME_HEIGHT) { 
			y = GAME_HEIGHT - height; 
		}
		else if (y<=0) { 
			y = 0; 
		}
	}
	
	public void setDirection(int direction) { //set the direction of the paddle's movement
		if(direction == 1) { 
			currentSpeed = -defaultSpeed; //direction of 1 means going up 
		}
		else if(direction == -1)
			currentSpeed = defaultSpeed; //direction of -1 means going down
		else 
			currentSpeed = 0; //direction of 0 means not moving
	} 


}
