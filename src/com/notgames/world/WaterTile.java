package com.notgames.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.notgames.main.Game;

public class WaterTile extends Tile {
	
	public BufferedImage[] waterAnimation;
	
	public int curAnimation = 0, curFrames = 0, targetFrames = 80;

	public WaterTile(int x, int y, BufferedImage sprite) {
		super(x, y, sprite);
		this.isWall = true;
				
		waterAnimation = new BufferedImage[2];
		
		waterAnimation[0] = Game.tileSpritesheet.getSprite(16,16,16,16);
		waterAnimation[1] = Game.tileSpritesheet.getSprite(0,32,16,16);
		
	}
	
	public void render(Graphics g) {
		curFrames++; 
		if(curFrames == targetFrames) {
			curFrames = 0;
			curAnimation++;
			if(curAnimation == waterAnimation.length) {
				curAnimation = 0;
			}
		}
		g.drawImage(waterAnimation[curAnimation],this.getX()-Camera.x,this.getY()-Camera.y,null);
	}
}
