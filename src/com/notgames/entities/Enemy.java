package com.notgames.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import com.notgames.graficos.UI;
import com.notgames.main.Game;
import com.notgames.world.Camera;
import com.notgames.world.World;

public class Enemy extends Entity{

	private double spd = 0.08;
	
	private int maskX = 2,maskY = 2,maskW = 10,maskH = 12;
	
	private int rightDir = 0, leftDir = 1, upDir = 2, downDir = 3, nullDir = 4;
	private int dir = nullDir;
	private int curAnimation = 0, curFrames = 0, targetFrames = 120;
	
	private BufferedImage[] rightEnemy;
	private BufferedImage[] leftEnemy;
	private BufferedImage[] upEnemy;
	private BufferedImage[] downEnemy;
	
	public Enemy(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		rightEnemy = new BufferedImage[2];
		leftEnemy = new BufferedImage[2];
		upEnemy = new BufferedImage[2];
		downEnemy = new BufferedImage[2];
		
		rightEnemy[0] = Game.ghost1Spritesheet.getSprite(0,0,16,16);
		rightEnemy[1] = Game.ghost1Spritesheet.getSprite(0,0,16,16);
		
		leftEnemy[0] = Game.ghost1Spritesheet.getSprite(16,0,16,16);
		leftEnemy[1] = Game.ghost1Spritesheet.getSprite(16,0,16,16);
		
		upEnemy[0] = Game.ghost1Spritesheet.getSprite(16,16,16,16);
		upEnemy[1] = Game.ghost1Spritesheet.getSprite(16,16,16,16);
		
		downEnemy[0] = Game.ghost1Spritesheet.getSprite(0,16,16,16);
		downEnemy[1] = Game.ghost1Spritesheet.getSprite(0,16,16,16);
	
	}
	
	public void tick() {
		if(isCollidingWithPlayer() == false) {
		if((int)x < Game.player.getX() && World.isFree((int)(x+spd),this.getY())
				&& !isColliding((int)(x+spd),this.getY())) {
			dir = rightDir;
			x+=spd;
		} else if ((int)x > Game.player.getX() && World.isFree((int)(x-spd),this.getY())
				&& !isColliding((int)(x-spd),this.getY())) {
			dir = leftDir;
			x-=spd;
		}
		if((int)y < Game.player.getY() && World.isFree(this.getX(),(int)(y+spd))
				&& !isColliding(this.getX(),(int)(y+spd))) {
			dir = downDir;
			y+=spd;
		} else if((int)y > Game.player.getY() && World.isFree(this.getX(),(int)(y-spd))
				&& !isColliding(this.getX(),(int)(y-spd))) {
			dir = upDir;
			y-=spd;
			}
		}
		else {
			if(Game.rand.nextInt(100) < 10) {
			Game.player.health-=Game.rand.nextInt(3);
			Game.player.isDamaged = true;
		}
	}
			curFrames++; 
				if(curFrames == targetFrames) {
					curFrames = 0;
					curAnimation++;
					if(curAnimation == rightEnemy.length) {
						curAnimation = 0;
				}
			}
	}
	
	public boolean isCollidingWithPlayer() {
		Rectangle enemyCurrent = new Rectangle(this.getX()+maskX,this.getY()+maskY,maskW,maskH);
		Rectangle player = new Rectangle(Game.player.getX(),Game.player.getY(),16,16);
		
		return enemyCurrent.intersects(player);
	}
	
	public boolean isColliding(int xNext,int yNext) {
		Rectangle enemyCurrent = new Rectangle(xNext+maskX,yNext+maskY,maskW,maskH);
		for(int i = 0; i < Game.enemies.size(); i++) {
			Enemy e = Game.enemies.get(i);
			if(e == this)
				continue;
			Rectangle targetEnemy = new Rectangle(e.getX()+maskX,e.getY()+maskY,maskW,maskH);
			if(enemyCurrent.intersects(targetEnemy)) {
				return true;
			}
		}
		return false;
		}
	
	public boolean playerCalledSpawn(KeyEvent e) {
			System.out.println("Enemy Spawned");
			Enemy en = new Enemy(16, 16, 16, 16, Entity.ENEMY_EN);
			Game.entities.add(en);
			Game.enemies.add(en);
			return false; 
		}
	
	public void render(Graphics g) {
		if(dir == rightDir) {
			g.drawImage(rightEnemy[curAnimation],this.getX()-Camera.x,this.getY()-Camera.y,null);
		} else if(dir == leftDir) {
			g.drawImage(leftEnemy[curAnimation],this.getX()-Camera.x,this.getY()-Camera.y,null);
		}
		if(dir == upDir) {
			g.drawImage(upEnemy[curAnimation],this.getX()-Camera.x,this.getY()-Camera.y,null);
		} else if(dir == downDir) {
			g.drawImage(downEnemy[curAnimation],this.getX()-Camera.x,this.getY()-Camera.y,null);
		}
		if(UI.showHitbox == true) {
			g.setColor(Color.blue);
			g.fillRect(this.getX()+maskX-Camera.x,this.getY()+maskY-Camera.y,maskW,maskH);
		}
	}
}
