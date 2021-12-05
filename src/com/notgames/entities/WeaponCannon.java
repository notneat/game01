package com.notgames.entities;

import java.awt.image.BufferedImage;

public class WeaponCannon extends WeaponHandler {

	public WeaponCannon(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		this.isSMG = true;
		this.fireRate = 3;
	}

}
