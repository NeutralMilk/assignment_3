package ie.dit;

import java.util.ArrayList;

import ddf.minim.Minim;
import processing.core.*;

public class Sub extends PApplet
{
	//PImage image;
	Main main;
	PVector pos;
	PImage s;
	float charX;
	float charY;
	float easing;
	float speed = 50;
	float theta = 0.0f;
	boolean spawn = false;
	boolean placed = false;
	
	public Sub(Main _main)
	{
		main = _main;	
		s = main.loadImage("2.png");
		charX = 0;
		charY = main.height/2-main.width/100;
		pos = new PVector(charX, charY);
		easing = .7f;
	}

	public void update()
	{
		if(spawn == true)
		{
			if(main.mousePressed)
			{
				float targetX = main.mouseX;
			    float dx = targetX - pos.x;
			    pos.x += dx * easing;
			    
			    //jumping
			    
			    float targetY = main.mouseY;
			    float dy = targetY - pos.y;
			    pos.y += dy * easing;
			}//end if
		}//end if
	}
	
	public void render()
	{
		
		if(spawn == true)
		{
			main.imageMode(CENTER);
		    main.image(s, main.mouseX, main.mouseY);
		}//end if
		
		if(spawn == false)
		{
			main.imageMode(CENTER);
		    main.image(s, pos.x, pos.y);
		}//end if
	}
}