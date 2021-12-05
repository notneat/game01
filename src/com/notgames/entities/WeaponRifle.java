package com.notgames.entities;

import java.awt.image.BufferedImage;

public class WeaponRifle extends WeaponHandler {

	public WeaponRifle(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		this.isSMG = true;
		this.fireRate = 3;
	}

}
