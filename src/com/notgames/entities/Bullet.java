package com.notgames.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.notgames.main.Game;
import com.notgames.world.Camera;

public class Bullet extends Entity{

    private double dx;
    private double dy;
    private double spd = 0.5;
    
    private int life = 10000, curLife = 0;

    public Bullet(int x, int y, int width, int height, BufferedImage sprite, double dx, double dy) {
        super(x, y, width, height, sprite);
        this.dx = dx;
        this.dy = dy;
        
    }
    
    public void tick() {
    	curLife++;
        if(curLife == life) {
        	Game.bullets.remove(this);
        	return;
        }
        x+=dx*spd;
        y+=dy*spd;
    }

    public void render(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillOval(this.getX() - Camera.x,this.getY() - Camera.y,3,3);
    }

}
