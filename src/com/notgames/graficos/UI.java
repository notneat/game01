package com.notgames.graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.notgames.entities.Entity;
import com.notgames.entities.WeaponHandler;
import com.notgames.main.Game;

public class UI {
	
	public static boolean showHitbox;
	
	Font GameFont;
	
	private int LPposX = 0;
	private int LPposY = 0;
	
	private int AposX = 0;
	private int AposY = 0;
	
	public UI() {
		
	}
	
	public void render(Graphics g) {
		g.setColor(new Color(42,43,42));
		g.fillRect(172,143,60,11);
		g.setColor(new Color(153,104,136));
		g.fillRect(172,143,(int)(Game.player.health/Game.player.maxHealth*60),11);
		g.setColor(Color.white);
		String healthBarString = (int)Game.player.health+"/"+(int)Game.player.maxHealth;
		g.setFont(new Font("GameFont",Font.TRUETYPE_FONT,10));
		g.drawString(healthBarString, 181 + (20 - g.getFontMetrics().stringWidth(healthBarString)) / 152,152);

		if(Game.player.LifePackAmount > 0) {
			LPposX = 18;
			LPposY = 143;
			g.setColor(Color.white);
			g.setFont(new Font("GameFont",Font.TRUETYPE_FONT,10));
			if(Game.player.LifePackAmount >= 10) {
				LPposX = 24;
			}
			if(Game.player.LifePackAmount >= 1) {
				g.drawImage(Entity.LIFEPACK_EN,LPposX,LPposY,null);
				g.drawString(Game.player.LifePackAmount+"",15,155);
			}
			

		}
		if(Game.player.ammo > 0) {
			AposX = 78;
			AposY = 143;
			g.setColor(Color.white);
			g.setFont(new Font("GameFont",Font.TRUETYPE_FONT,10));
			if(Game.player.ammo >= 10) {
				AposX = 84;
			}
			if(Game.player.ammo >= 1) {
				g.drawImage(Entity.AMMO_EN,AposX,AposY,null);
				g.drawString(Game.player.ammo+"",75,155);
			}
		}
		
		if(Game.player.equippedSMG) {
			g.drawImage(WeaponHandler.SMG_RIGHT,6,1,null);

		} else if(Game.player.equippedRifle) {
			g.drawImage(WeaponHandler.RIFLE_RIGHT,6,1,null);

		} else if(Game.player.equippedCannon) {
			g.drawImage(WeaponHandler.CANNON_RIGHT,6,1,null);
		}
				
		if(showHitbox == true) {
			g.setColor(Color.white);
			g.setFont(new Font("GameFont",Font.TRUETYPE_FONT,10));
			g.drawString("Show hitboxes: TRUE",5,10);
		}
	}
	
}
