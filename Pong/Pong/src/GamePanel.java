import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable, KeyListener{

  //dimensions of window
  public static final int GAME_WIDTH = 800;
  public static final int GAME_HEIGHT = 600;
 
  public Thread gameThread; //thread to help our program do multiple things at once
  public Image image;
  public Graphics graphics;
  public PlayerBall ball = new PlayerBall(); //create a new ball object

  //create and initialize the left and right paddle objects
  Paddle leftPaddle = new Paddle (25, 200, false); 
  Paddle rightPaddle = new Paddle (750, 200, true); 
  
  
  boolean leftPaddleWin = false; 
  boolean rightPaddleWin = false; 

  public GamePanel(){
    this.setFocusable(true); //make everything in this class appear on the screen
    this.addKeyListener(this); //start listening for keyboard input
    this.setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));

    //make this class run at the same time as other classes
    gameThread = new Thread(this); 
    gameThread.start();
  }

 
  public void paint(Graphics g){
    //are "double buffering" to create an image off screen
    image = createImage(GAME_WIDTH, GAME_HEIGHT); //draw off screen
    graphics = image.getGraphics(); 
    draw(graphics); //update the positions of everything on the screen 
    g.drawImage(image, 0, 0, this); //redraw everything on the screen

  }
  
  
  public void draw(Graphics g){
	  //draw background
	  BufferedImage soccerField = null;
      try {
          soccerField = ImageIO.read(new File("src\\images", "soccerField.png")); //read the picture of the soccer field
      } catch (IOException e) {
      }

      g.drawImage(soccerField, 0, 0, null); //draw the background
      ball.draw(g); //draw the ball
      
      //if none of the players have won yet, see if they have won
      if(leftPaddleWin==false && rightPaddleWin==false && leftPaddle.getScore() >= 7) {
    	  leftPaddleWin = true; 
      }
      else if(leftPaddleWin==false && rightPaddleWin==false && rightPaddle.getScore() >= 7) {
    	  rightPaddleWin = true; 
      }
      //display winning screen if any player has won
      if(leftPaddleWin==true) {
    	  leftPaddle.displayWin(g);
      }
      else if (rightPaddleWin==true) {
    	  rightPaddle.displayWin(g);
      }
      
      //play the game if no winner has been decided
      else {
    	  leftPaddle.draw(g); //draw the left paddle
    	  rightPaddle.draw(g); //draw the right paddle
    	  leftPaddle.move();
    	  rightPaddle.move();
      }
	  
	 
  }
  
  
  public void run(){
	    //the CPU runs our game code too quickly, so the following lines of code "force" the computer to get stuck in a loop for short intervals between calling other methods to update the screen. 
	    long lastTime = System.nanoTime();
	    double amountOfTicks = 60;
	    double ns = 1000000000/amountOfTicks;
	    double delta = 0;
	    long now;

	    while(true){ //this is the infinite game loop
	      now = System.nanoTime();
	      delta = delta + (now-lastTime)/ns;
	      lastTime = now;

	      //only move objects around and update screen if enough time has passed
	      if(delta >= 1){
	    	if(leftPaddleWin==false && rightPaddleWin==false) //if no winner has been decided
	    		 ball.move(); //move the ball
	        checkCollisions(); //check if ball is out of bounds or bouncing off of paddles
	        repaint(); 
	        delta--;
	      }
	    }
	  }
  
public void checkCollisions() {
	  
	  if(ball.y<=0 || ball.y+ ball.BALL_DIAMETER>=GAME_HEIGHT) { //handles collision with ceiling or floor
		  ball.yDirection *=-1; //bounce off of ceiling or floor
	  }
	  if(ball.x<=0) { //if player 1 (the left paddle) scores a point, increase their score
		  leftPaddle.increaseScore();  
		  ball.recenter(); //reposition the ball to the center
	  }
	  else if (ball.x+ ball.BALL_DIAMETER>=GAME_WIDTH) {  //if player 2 (the right paddle) scores a point, increase their score
		  rightPaddle.increaseScore();
		  ball.recenter(); //reposition the ball to the center
	  }
	  
	  if(ball.intersects(leftPaddle) || ball.intersects(rightPaddle)) { //bounce off paddles
		  ball.xDirection *=-1;
	  }
  }
  

  public void keyPressed(KeyEvent e){ //if keyboard button is pressed
    if(e.getKeyChar() == 'w'){ //if 'w' pressed
    	leftPaddle.setDirection(1); //player 1 will move up
    }
    if(e.getKeyChar() == 's'){ //if 's' pressed
    	leftPaddle.setDirection(-1); //player 1 will move down
    }
    if(e.getKeyCode() == KeyEvent.VK_UP){ //if 'up arrow' pressed
    	rightPaddle.setDirection(1); //player 2 will move up
    }
    if(e.getKeyCode() == KeyEvent.VK_DOWN){
    	rightPaddle.setDirection(-1); //player 2 will move down
    }
    
  }

  //need to include what happens when key is released
  public void keyReleased(KeyEvent e){
	  if(e.getKeyChar() == 'w'){ //if 'w' released
	    	leftPaddle.setDirection(0); //stop moving
	    }
	    if(e.getKeyChar() == 's'){ //if 's' released
	    	leftPaddle.setDirection(0); //stop moving
	    }
	    if(e.getKeyCode() == KeyEvent.VK_UP){ //if 'up arrow' released
	    	rightPaddle.setDirection(0); //stop moving
	    }
	    if(e.getKeyCode() == KeyEvent.VK_DOWN){ //if 'down arrow' released
	    	rightPaddle.setDirection(0); //stop moving
	    }
  }

  //need to include even though not used
  public void keyTyped(KeyEvent e){

  }
  
}