package com.notgames.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.notgames.main.Game;

public class Tile {

	public static BufferedImage TILE_FLOOR = Game.tileSpritesheet.getSprite(0,0,16,16);
	public static BufferedImage TILE_STONEWALL = Game.tileSpritesheet.getSprite(16,0,16,16);
	public static BufferedImage TILE_SANDFLOOR = Game.tileSpritesheet.getSprite(0,16,16,16);
	public static BufferedImage TILE_WATER = Game.tileSpritesheet.getSprite(0,32,16,16);
	
	private BufferedImage sprite;
	private int x,y;
	
	public boolean isWall = false;
	
	public Tile(int x, int y, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
	}
	
	public int getX() {
		return (int)this.x;
	}
	
	public int getY() {
		return (int)this.y;
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite,x - Camera.x,y - Camera.y,null);
	}
}
