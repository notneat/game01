package com.notgames.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.notgames.main.Game;
import com.notgames.world.Camera;

public class Entity {
	
	
	public static BufferedImage LIFEPACK_EN = Game.itemSpritesheet.getSprite(16,0,16,16);
	public static BufferedImage WEAPON_EN = Game.weaponSpritesheet.getSprite(0,16,16,16);
	public static BufferedImage AMMO_EN = Game.itemSpritesheet.getSprite(0,0,16,16);
	public static BufferedImage ENEMY_EN = Game.ghost1Spritesheet.getSprite(0,0,16,16);
	public static BufferedImage WEAPON_RIGHT = Game.weaponSpritesheet.getSprite(0,16,16,16);
	public static BufferedImage WEAPON_LEFT = Game.weaponSpritesheet.getSprite(16,16,16,16);
	public static BufferedImage WEAPON_FRONT = Game.weaponSpritesheet.getSprite(0,96,16,16);

	
	protected double x;
	protected double y;
	protected int width;
	protected int height;
	
	private BufferedImage sprite;
	
	public int maskX,maskY,maskW,maskH;
	
	public Entity(int x,int y,int width,int height, BufferedImage sprite) {
		
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sprite = sprite;
		
		this.maskX = 0;
		this.maskY = 0;
		this.maskW = width;
		this.maskH = height;
		
	}
	
	public void setMask(int maskX,int maskY,int maskW,int maskH) {
		this.maskX = maskX;
		this.maskY = maskY;
		this.maskW = maskW;
		this.maskH = maskH;
	}
	
	public int getX() {
		return (int)this.x;
	}
	
	public int getY() {
		return (int)this.y;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public void setX(int newX) {
        this.x = newX;
    }

    public void setY(int newY) {
        this.y = newY;
    }
	
	public void tick() {
		
	}
	
	public static boolean isColliding(Entity e1, Entity e2) {
		Rectangle e1Mask = new Rectangle(e1.getX() + e1.maskX,e1.getY() + e1.maskY,e1.maskW,e1.maskH);
		Rectangle e2Mask = new Rectangle(e2.getX() + e2.maskX,e2.getY() + e2.maskY,e2.maskW,e2.maskH);
		
		return e1Mask.intersects(e2Mask);
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite,this.getX() - Camera.x,this.getY() - Camera.y,null);
	}
}