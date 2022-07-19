import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class PlayerBall extends Rectangle{
  //dimensions
  public static final int GAME_WIDTH = 800;
  public static final int GAME_HEIGHT = 600;
  
  public int yDirection; //set the speeds in the x and y directions
  public int xDirection;
  public final int SPEED = 4; //movement speed of ball
  public static final int BALL_DIAMETER = 40; //size of ball

  //constructor creates ball at given location with given dimensions
  public PlayerBall(){
    super(BALL_DIAMETER, BALL_DIAMETER); //creates a rectangle object for the ball
    recenter(); //position the ball at the center 
  }

 
  public void move(){ //method to move the ball
  y += SPEED*yDirection; //move the ball according to the speed and direction of the ball
  x += SPEED*xDirection;
}

  public void recenter() { 
	  yDirection = ((int)(2*Math.random()) == 1) ? 1:-1; //set random direction when ball is placed in the center
	  xDirection = ((int)(2*Math.random()) == 1) ? 1:-1; 
	  x = 380; //re-center the ball 
	  y = 280; 
  }
  

  //draw the ball
  public void draw(Graphics g){
	  BufferedImage soccerBall = null;
      try {
          soccerBall = ImageIO.read(new File("src//images", "ballSprite.png")); //read the image
      } catch (IOException e) { 
      }
    g.drawImage(soccerBall, x, y, null); //draw the image
  }
  
}