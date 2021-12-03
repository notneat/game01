package com.notgames.graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;

import com.notgames.main.Game;

public class UI {
	
	public static boolean showHitbox;
	
	Font GameFont;
	
	public UI() {
		try {
			GameFont = Font.createFont(Font.TRUETYPE_FONT,new File("GameFont.ttf"));
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT,new File("GameFont.ttf")));
				}
		catch (IOException | FontFormatException e) {
			e.printStackTrace();
		}
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
			g.setColor(Color.white);
			g.setFont(new Font("GameFont",Font.TRUETYPE_FONT,10));
			if(Game.player.LifePackAmount >= 1) {
				g.drawString(Game.player.LifePackAmount+" Heart",15,155);
			}
			if(Game.player.LifePackAmount > 1) {
				g.drawString(Game.player.LifePackAmount+" Hearts",15,155);
			}

		}
		if(Game.player.ammo > 0) {
			g.setColor(Color.white);
			g.setFont(new Font("GameFont",Font.TRUETYPE_FONT,10));
			if(Game.player.ammo >= 1) {
				g.drawString(Game.player.ammo+" Bullet",75,155);
			}
			if(Game.player.ammo > 1) {
				g.drawString(Game.player.ammo+" Bullets",75,155);
			}
		}
		
		if(showHitbox == true) {
			g.setColor(Color.white);
			g.setFont(new Font("GameFont",Font.TRUETYPE_FONT,10));
			g.drawString("Show hitboxes: TRUE",5,10);
		}
	}
	
}
