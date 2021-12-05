package com.notgames.entities;

import java.awt.image.BufferedImage;

public class WeaponSMG extends WeaponHandler {

	public WeaponSMG(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		this.isSMG = true;
		this.fireRate = 3;
	}

}
