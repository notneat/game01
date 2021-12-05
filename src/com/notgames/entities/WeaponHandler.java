package com.notgames.entities;

import java.awt.image.BufferedImage;

import com.notgames.main.Game;

public class WeaponHandler extends Entity{
	
	public boolean isCannon = false;
	public boolean isSMG = false;
	public boolean isRifle = false;
	
	public static boolean ableToFire = true;
	
	public double fireRate = 0;
	public static double fireRateLimit = 0;
	
	public static BufferedImage SMG_RIGHT = Game.weaponSpritesheet.getSprite(0,32,16,16);
	public static BufferedImage SMG_LEFT = Game.weaponSpritesheet.getSprite(16,32,16,16);
	public static BufferedImage RIFLE_RIGHT = Game.weaponSpritesheet.getSprite(0,64,16,16);
	public static BufferedImage RIFLE_LEFT = Game.weaponSpritesheet.getSprite(16,64,16,16);
	public static BufferedImage CANNON_RIGHT = Game.weaponSpritesheet.getSprite(0,80,16,16);
	public static BufferedImage CANNON_LEFT = Game.weaponSpritesheet.getSprite(16,80,16,16);
	public static BufferedImage WEAPON_FRONT = Game.weaponSpritesheet.getSprite(0,96,16,16);

	public WeaponHandler(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
	}

}
