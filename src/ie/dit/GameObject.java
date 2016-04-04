package ie.dit;

import processing.core.*;

public abstract class GameObject extends PApplet
{
   //fields
	PVector pos;
	PVector mouseBox;
	Main main;
	PImage unit = new PImage();
	float easing;
	int move;
	float angle = 0;
	boolean validTile;
	boolean madeMove;
	int initialHealth;
    int currentHealth;
	int clicks;
	boolean colourChange = false;
	boolean nextTurn = false;

    GameObject(Main _main)
    {
    	main = _main;
    }
  
    public abstract void update();  
    public abstract void render();
}