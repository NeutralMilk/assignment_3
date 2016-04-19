package ie.dit;

import java.util.ArrayList;

import ddf.minim.Minim;
import processing.core.*;

public class Sub extends GameObject
{
    public Sub(Main _main)
    {
        super(_main);
        unit = main.loadImage("2.png");

        //this scales the units to fit any screen size
        int w = unit.width * main.width/2560;
        int h = unit.height * main.height/1440;
        unit.resize(w,h);
        pos = new PVector();
        mouseBox = new PVector();
        easing = .7f;
        madeMove = false;
        initialHealth = 30;
        currentHealth = 30;
        clicks = 0;
    }

    public void update()
    {
        if (move == 0) {
            float targetX = main.mouseX;
            float dx = targetX - pos.x;
            pos.x += dx * easing;

            float targetY = main.mouseY;
            float dy = targetY - pos.y;
            pos.y += dy * easing;
        }//end if 0

        if (move == 1)
        {
            for (int i = 0; i < w; i++)
            {
                for (int j = 0; j < h; j++)
                {
                    if (pos.x < (main.cPosX[i] + main.width / w + 1) && pos.x > main.cPosX[i] && pos.y < (main.cPosY[j] + main.height / hplus1 + 1) && pos.y > main.cPosY[j])
                    {
                        main.visited[i][j]= true;

                        pos.x = main.cPosX[i] + main.width / 64;
                        pos.y = main.cPosY[j] + main.height / 36;

                        break;
                    }//end if
                }//end for
            }//end for
        }//end if 1

        if (move == 2)
        {
            for (int i = 0; i < w; i++)
            {
                for (int j = 0; j < h; j++)
                {
                    if (pos.x < (main.cPosX[i] + main.width / w + 1) && pos.x > main.cPosX[i] && pos.y < (main.cPosY[j] + main.height / hplus1 + 1) && pos.y > main.cPosY[j])
                    {
                        pos.x = main.cPosX[i] + main.width / 64;
                        pos.y = main.cPosY[j] + main.height / 36;
                    }//end if

                    if (main.mouseX < (main.cPosX[i] + main.width / w) && main.mouseX > main.cPosX[i] && main.mouseY < (main.cPosY[j] + main.height / hplus1) && main.mouseY > main.cPosY[j])
                    {
                        mouseBox.x = main.cPosX[i] + main.width / 64;
                        mouseBox.y = main.cPosY[j] + main.height / 36;
                    }//end if

                    if (main.mousePressed && main.release2 && validTiles())
                    {
                        if(enemy == true)
                        {
                            currentHealth -= (int)random(20,30);
                            main.enemyUnits.get(enemyIndex).currentHealth -= (int)(random(25,35));
                            enemy = false;
                            clicks++;
                        }

                        else
                        {
                            pos.x = mouseBox.x;
                            pos.y = mouseBox.y;

                            validTile = false;
                            madeMove = true;

                            //only move once per turn
                        }//end else

                        if(clicks > 0)
                        {
                            move = 1;
                            nextTurn = false;
                            //main.active = false;
                        }//end if
                        main.release2 = false;
                    }//end if
                    if(pos.dist(mouseBox) < main.width/6)
                    {
                        main.battlefield.colourGreen = true;
                        main.fog.colourGreen = true;
                    }
                    else
                    {
                        main.battlefield.colourRed = true;
                        main.fog.colourRed = true;
                    }
                }//end for
            }//end for
        }//end if 2
    }//end update()

    //this function checks to see if the mouse is within two boxes up, down, left or right of the unit
    public boolean validTiles()
    {

        //the subs can travel 1/6 of the map each turn
        if(pos.dist(mouseBox) < main.width/6)
        {
            validTile = checkPos(mouseBox);
        }
        return validTile;
    }//end validTiles()

    boolean enemy= false;
    //checks the position of every other ship
    public boolean checkPos(PVector mouse)
    {
        boolean valid = true;
        for(int i = 0; i < main.units.size(); i++)
        {
            //change the position to the centre
            mouse = main.centerPos(mouse);

            GameObject go = main.units.get(i);
            float size = main.battlefield.size/2;

            //if the position is the same as any unit other than itself then you cannot place it
            if(mouse.x == go.pos.x && mouse.y == go.pos.y)
            {
                valid = false;
            }//end if
        }//end for

        for(int i = 0; i < main.enemyUnits.size(); i++)
        {
            //change the position to the centre
            mouse = main.centerPos(mouse);

            GameObject go = main.enemyUnits.get(i);
            float size = main.battlefield.size/2;

            //if the position is the same as any unit other than itself then you cannot place it
            if(mouse.x == go.pos.x && mouse.y == go.pos.y)
            {
                enemy = true;
                enemyIndex = i;
            }//end if
        }//end for
        return valid;
    }//end checkPos

    public void render()
    {
        switch(move)
        {
            case 0:
            {
                main.pushMatrix();
                main.translate(main.mouseX, main.mouseY);
                main.imageMode(CENTER);
                main.image(unit, 0, 0);
                main.popMatrix();
                break;
            }//end case 0

            case 1:
            {
                main.pushMatrix();
                main.translate(pos.x, pos.y);
                main.imageMode(CENTER);
                main.image(unit, 0, 0);
                main.popMatrix();
                break;
            }//end case 1

            case 2:
            {
                main.pushMatrix();
                angle = atan2(-(pos.x - mouseBox.x), -(pos.y - mouseBox.y));
                main.translate(pos.x, pos.y);
                main.rotate(-angle+PI);
                main.imageMode(CENTER);
                main.image(unit, 0, 0);
                main.popMatrix();
                break;

            }//end case 2
        }//end switch
    }//end render

    public void mouseReleased()
    {
        println("This works");
    }
}