/* GameFrame class establishes the frame (window) for the game
It is a child of JFrame because JFrame manages frames
Runs the constructor in GamePanel class

*/ 

import java.awt.*;
import javax.swing.*;

public class GameFrame extends JFrame{

  GamePanel panel;

  public GameFrame(){
    panel = new GamePanel(); //run GamePanel constructor
    this.add(panel);
    this.setTitle("Pong by Adib Raed"); //add title
    this.setResizable(false); //frame can't change size
    this.setBackground(Color.white); //sets the background to white
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //X button will stop program execution
    this.pack();//makes components fit in window 
    this.setVisible(true); //makes window visible to user
    this.setLocationRelativeTo(null);//set window in middle of screen
  }
  
}